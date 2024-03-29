/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvReaderOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryCsvUploadResult;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDisplayGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryCsvUpLoadService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryModifyService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryTableLockService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CategoryUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.CheckMessageUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvReaderUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadError;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * カテゴリCsvアップロード実装<br/>
 *
 * @author kamei
 */
// 2023-renew categoryCSV from here
@Service
public class CategoryCsvUpLoadServiceImpl extends AbstractShopService implements CategoryCsvUpLoadService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryCsvUpLoadServiceImpl.class);

    /** checkMessageUtility */
    public CheckMessageUtility checkMessageUtility;

    /** CategoryUtility */
    public CategoryUtility categoryUtility;

    /** カテゴリー登録サービス */
    public CategoryRegistService categoryRegistService;

    /** カテゴリー更新サービス */
    public CategoryModifyService categoryModifyService;

    /** カテゴリー情報取得ロジック */
    public CategoryGetLogic categoryGetLogic;

    /** カテゴリ表示情報取得ロジック */
    public CategoryDisplayGetLogic categoryDisplayGetLogic;

    /** カテゴリテーブルロックサービス */
    public CategoryTableLockService categoryTableLockService;

    /**
     * CSV読み込みのユーティリティ<br/>
     */
    private final CsvReaderUtil csvReaderUtil;

    /**
     * コンストラクタ
     *
     * @param checkMessageUtility
     * @param categoryUtility
     * @param categoryRegistService
     * @param categoryModifyService
     * @param categoryGetLogic
     * @param categoryDisplayGetLogic
     * @param categoryTableLockService
     */
    @Autowired
    public CategoryCsvUpLoadServiceImpl(CheckMessageUtility checkMessageUtility,
                                        CategoryUtility categoryUtility,
                                        CategoryRegistService categoryRegistService,
                                        CategoryModifyService categoryModifyService,
                                        CategoryGetLogic categoryGetLogic,
                                        CategoryDisplayGetLogic categoryDisplayGetLogic,
                                        CategoryTableLockService categoryTableLockService) {
        this.checkMessageUtility = checkMessageUtility;
        this.categoryUtility = categoryUtility;
        this.categoryRegistService = categoryRegistService;
        this.categoryModifyService = categoryModifyService;
        this.categoryGetLogic = categoryGetLogic;
        this.categoryDisplayGetLogic = categoryDisplayGetLogic;
        this.categoryTableLockService = categoryTableLockService;
        this.csvReaderUtil = new CsvReaderUtil();
    }

    /**
     * カテゴリ一括登録（アップロード）実行<br/>
     *
     * @param uploadedFile  アップロードファイル
     * @param validLimit    バリデータエラー限界値
     * @param registFlg     登録フラグ（true:登録、false:更新）
     * @return CsvUploadResult Csvアップロード結果
     */
    @Override
    public CsvUploadResult execute(File uploadedFile, int validLimit, boolean registFlg) {

        // CSV読み込みオプションを設定する
        CsvReaderOptionDto csvReaderOptionDto = new CsvReaderOptionDto();
        csvReaderOptionDto.setValidLimit(validLimit);

        // Csvアップロード結果Dto作成
        CategoryCsvUploadResult categoryCsvUploadResult = new CategoryCsvUploadResult();

        // CSVファイルを読み込み
        List<CategoryCsvDto> categoryCsvDtoList;

        try {
            categoryCsvDtoList = (List<CategoryCsvDto>) csvReaderUtil.readCsv(uploadedFile, CategoryCsvDto.class,
                                                                              categoryCsvUploadResult,
                                                                              csvReaderOptionDto
                                                                             );
        } catch (Exception e) {
            // CSV読み込みで有り得ない例外が発生した場合
            LOGGER.error("例外処理が発生しました", e);
            csvReaderUtil.createUnexpectedExceptionMsg(categoryCsvUploadResult);
            return categoryCsvUploadResult;
        }

        /* CSV読み込みによるチェック エラーの場合 終了 */
        if (categoryCsvUploadResult.getErrorCount() > 0) {
            return categoryCsvUploadResult;
        }

        // 入力チェック実施後、エラーの場合ここで処理終了
        if (validate(categoryCsvDtoList, validLimit, categoryCsvUploadResult)) {
            return categoryCsvUploadResult;
        }

        // 登録処理にてエラー発生した場合ここで処理終了
        if (registProcess(categoryCsvDtoList, registFlg, categoryCsvUploadResult)) {
            return categoryCsvUploadResult;
        }

        // 正常終了時は、処理件数を返す
        return categoryCsvUploadResult;

    }

    /**
     * カテゴリ情報登録／更新処理<br/>
     *
     * @param CategoryCsvDtoList    CSVアップロードヘルパー
     * @param registFlg 登録／更新フラグ
     * @param csvUploadResult   CSVアップロード結果
     * @return true:処理失敗　false:処理成功
     */
    protected boolean registProcess(List<CategoryCsvDto> CategoryCsvDtoList,
                                    boolean registFlg,
                                    CategoryCsvUploadResult csvUploadResult) {
        // トップカテゴリーのIDを取得する
        String topCategoryId = categoryUtility.getCategoryIdTop();

        // CSVレコードからDto変換
        int recordCount = 2; // CSVファイルの行数にあわす為、2始まり

        for (CategoryCsvDto categoryCsvDto : CategoryCsvDtoList) {

            // データ部0件の対応 基本的にバリデータエラーとなるはずだが・・・
            if (categoryCsvDto == null) {
                recordCount++;
                continue;
            }

            // トップカテゴリーが処理対象になった場合
            if (topCategoryId.equals(categoryCsvDto.getCategoryId())) {

                // 1度だけ非エラーメッセージとして情報を追加
                // このメッセージは正常終了時のみ表示される想定。
                if (!csvUploadResult.isTopCategoryUpdateAlerted()) {
                    csvUploadResult.addMessage(recordCount, MSGCD_TOP_CATEGORY_SKIPPED, categoryCsvDto.getCategoryId());
                    csvUploadResult.setTopCategoryUpdateAlerted(true);
                }

                recordCount++;
                continue;
            }

            // 相関チェック
            if (!compare(categoryCsvDto, recordCount, csvUploadResult)) {
                return true;
            }

            // 登録処理
            if (registFlg) {

                /* 登録処理 */
                if (regist(categoryCsvDto, categoryCsvDto.getParentCategoryId(), recordCount, csvUploadResult)) {
                    return true;
                }
            } else {

                /* 更新処理 */
                if (update(categoryCsvDto, recordCount, csvUploadResult)) {
                    return true;
                }
            }
            recordCount++;
        }

        return false;
    }

    /**
     * Csvバリデータチェック<br/>
     *
     * @param CategoryCsvDtoList    Csvアップロードヘルパー
     * @param validLimit    バリデータ限界値
     * @param csvUploadResult   Csvアップロード結果
     * @return true:エラー有り、false:エラー無し
     */
    protected boolean validate(List<CategoryCsvDto> CategoryCsvDtoList,
                               int validLimit,
                               CsvUploadResult csvUploadResult) {

        // バリデータエラーカウンタ
        int errorCounter = 0;

        // CSV のレコードを DTO に変換する
        int i = 0;
        for (CategoryCsvDto csvDto : CategoryCsvDtoList) {
            i = i + 1;

            // バリデータエラー行数限界値の場合 終了
            if (errorCounter >= validLimit) {
                // more表示フラグON
                csvUploadResult.setValidLimitFlg(true);
                break;
            }

            if (csvDto == null) {
                errorCounter++;
            }
        }

        // エラーあり
        if (errorCounter != 0) {
            // ヘッダー行の1をセット
            csvUploadResult.setRecordCount(1);
            return true;
        }

        // エラーなし
        return false;
    }

    /**
     * 項目の相関チェック<br/>
     *
     * @param categoryCsvDto   カテゴリDto
     * @param recodeCount   レコード行番号
     * @param csvUploadResult   CSVアップロード結果
     * @return true:比較OK、false:比較失敗
     */
    protected boolean compare(CategoryCsvDto categoryCsvDto, int recodeCount, CsvUploadResult csvUploadResult) {
        boolean flg = true;

        // 公開開始日時PCが公開終了日時PCよりも後だとエラー
        if (categoryCsvDto.getCategoryOpenStartTimePC() != null && categoryCsvDto.getCategoryOpenEndTimePC() != null
            && categoryCsvDto.getCategoryOpenStartTimePC().after(categoryCsvDto.getCategoryOpenEndTimePC())) {
            setErrorMessage(csvUploadResult, recodeCount, MSGCD_OPEN_TIME_ERROR);
            flg = false;
        }

        return flg;
    }

    /**
     * カテゴリ情報登録<br/>
     *
     * @param categoryCsvDto   カテゴリDto
     * @param parentCategoryId  親カテゴリID
     * @param recodeCount   レコード行番号
     * @param csvUploadResult   CSVアップロード結果
     * @return true:登録失敗、false:登録成功
     */
    protected boolean regist(CategoryCsvDto categoryCsvDto,
                             String parentCategoryId,
                             int recodeCount,
                             CsvUploadResult csvUploadResult) {

        // カテゴリテーブルロック実行
        categoryTableLockService.execute();

        // 既にカテゴリが登録済みかチェック
        CategoryDto categoryDto = getCategoryRegistedInfo(categoryCsvDto.getCategoryId());
        if (categoryDto.getCategoryEntity() != null) {
            setErrorMessage(csvUploadResult, recodeCount, MSGCD_REGISTED_CATEGORYID);
            return true;
        }

        // 親ＩＤの存在チェック
        CategoryDto parentCategoryDto = getCategoryRegistedInfo(categoryCsvDto.getParentCategoryId());
        if (parentCategoryDto.getCategoryEntity() == null) {
            setErrorMessage(csvUploadResult, recodeCount, MSGCD_NONE_HIGHER_CATEGORY);
            return true;
        }

        // 登録先階層チェック（11階層上限）
        if (parentCategoryDto.getCategoryEntity().getCategorySeqPath().length() + 8 > 100) {
            setErrorMessage(csvUploadResult, recodeCount, MSGCD_OVER_CATEGORYSEQPATH);
            return true;
        }

        // 登録カテゴリ情報をセット
        categoryDto = createCategoryDto(categoryCsvDto);

        // カテゴリー情報登録
        int result = categoryRegistService.execute(categoryDto, parentCategoryId);
        if (result != 1) {
            // エラーメッセージを作成・セット
            setErrorMessage(csvUploadResult, recodeCount, MSGCD_FAIL_REGIST_UPDATE);
            return true;
        }

        return false;
    }

    /**
     * カテゴリ情報更新<br/>
     *
     * @param categoryCsvDto   カテゴリDto
     * @param recodeCount   レコード行番号
     * @param csvUploadResult   CSVアップロード結果
     * @return true:更新失敗、false:更新成功
     */
    protected boolean update(CategoryCsvDto categoryCsvDto, int recodeCount, CsvUploadResult csvUploadResult) {

        // カテゴリテーブルロック実行
        categoryTableLockService.execute();

        // カテゴリが存在するのかチェック
        CategoryDto categoryDto = getCategoryRegistedInfo(categoryCsvDto.getCategoryId());
        if (categoryDto.getCategoryEntity() == null) {
            setErrorMessage(csvUploadResult, recodeCount, MSGCD_NONE_CATEGORYID);
            return true;
        }

        // 更新情報をセット
        categoryDto.getCategoryEntity().setCategoryType(categoryCsvDto.getCategoryType());
        categoryDto.getCategoryEntity().setManualDisplayFlag(categoryCsvDto.getManualDisplayFlag());
        categoryDto.getCategoryEntity().setCategoryName(categoryCsvDto.getCategoryName());
        categoryDto.getCategoryEntity().setCategoryOpenStatusPC(categoryCsvDto.getCategoryOpenStatusPC());
        categoryDto.getCategoryEntity().setCategoryOpenStartTimePC(categoryCsvDto.getCategoryOpenStartTimePC());
        categoryDto.getCategoryEntity().setCategoryOpenEndTimePC(categoryCsvDto.getCategoryOpenEndTimePC());
        categoryDto.getCategoryEntity().setSiteMapFlag(categoryCsvDto.getSiteMapFlag());
        categoryDto.getCategoryDisplayEntity().setCategoryNamePC(categoryCsvDto.getCategoryNamePC());
        categoryDto.getCategoryDisplayEntity().setMetaDescription(categoryCsvDto.getMetaDescription());
        categoryDto.getCategoryDisplayEntity().setMetaKeyword(categoryCsvDto.getMetaKeyword());
        categoryDto.getCategoryDisplayEntity().setFreeTextPC(categoryCsvDto.getFreeTextPC());
        categoryDto.getCategoryDisplayEntity().setCategoryImagePC(categoryCsvDto.getCategoryImagePC());

        // カテゴリー情報更新
        int result;
        result = categoryModifyService.execute(categoryDto);
        if (result != 1) {
            // エラーメッセージを作成・セット
            setErrorMessage(csvUploadResult, recodeCount, MSGCD_FAIL_REGIST_UPDATE);
            return true;
        }

        return false;
    }

    /**
     * 登録済みカテゴリ情報取得<br/>
     *
     * @param categoryId    カテゴリID
     * @return categoryDto カテゴリDto
     */
    protected CategoryDto getCategoryRegistedInfo(String categoryId) {

        // ショップSEQを取得し設定（無条件で設定）
        Integer shopSeq = 1001;

        // カテゴリー情報取得
        CategoryEntity categoryEntity = ApplicationContextUtility.getBean(CategoryEntity.class);
        CategoryDisplayEntity categoryDisplayEntity = ApplicationContextUtility.getBean(CategoryDisplayEntity.class);
        categoryEntity = categoryGetLogic.execute(shopSeq, categoryId);
        if (categoryEntity != null) {
            categoryDisplayEntity = categoryDisplayGetLogic.execute(categoryEntity.getCategorySeq());
        }

        CategoryDto categoryDto = ApplicationContextUtility.getBean(CategoryDto.class);

        categoryDto.setCategoryEntity(categoryEntity);
        categoryDto.setCategoryDisplayEntity(categoryDisplayEntity);

        return categoryDto;

    }

    /**
     * 登録/更新処理エラーメッセージセット<br/>
     *
     * @param csvUploadResult   CSVアップロード結果
     * @param recodeCount       レコード件数
     * @param errorCode エラーコード
     * @return csvUploadResult
     */
    protected CsvUploadResult setErrorMessage(CsvUploadResult csvUploadResult, int recodeCount, String errorCode) {

        List<CsvUploadError> csvUploadErrorList = csvUploadResult.getCsvUploadErrorlList();

        if (csvUploadErrorList == null || csvUploadErrorList.size() == 0) {
            csvUploadErrorList = new ArrayList<>();
        }
        csvUploadErrorList.add(createCsvUploadError(recodeCount, errorCode, null));
        csvUploadResult.setCsvUploadErrorlList(csvUploadErrorList);

        return csvUploadResult;

    }

    /**
     * CSVアップロードエラーDto<br/>
     *
     * @param recodeCount   レコード行数
     * @param messageCode   メッセージコード
     * @param args          引数
     * @return CSVアップロードエラー
     */
    protected CsvUploadError createCsvUploadError(Integer recodeCount, String messageCode, Object[] args) {
        CsvUploadError csvUploadError = new CsvUploadError();
        csvUploadError.setRow(recodeCount);
        csvUploadError.setMessageCode(messageCode);
        csvUploadError.setArgs(args);
        csvUploadError.setMessage(checkMessageUtility.getMessage(messageCode, args));
        return csvUploadError;
    }

    /**
     * カテゴリDTO作成<br/>
     *
     * @param categoryCsvDto    カテゴリCSVDto
     * @return カテゴリDTO
     */
    protected CategoryDto createCategoryDto(CategoryCsvDto categoryCsvDto) {

        CategoryDto categoryDto = ApplicationContextUtility.getBean(CategoryDto.class);
        CategoryEntity categoryEntity = ApplicationContextUtility.getBean(CategoryEntity.class);
        CategoryDisplayEntity categoryDisplayEntity = ApplicationContextUtility.getBean(CategoryDisplayEntity.class);

        // カテゴリエンティティ作成
        categoryEntity.setCategoryId(categoryCsvDto.getCategoryId());
        categoryEntity.setCategoryName(categoryCsvDto.getCategoryName());
        categoryEntity.setCategoryType(categoryCsvDto.getCategoryType());
        categoryEntity.setManualDisplayFlag(categoryCsvDto.getManualDisplayFlag());
        categoryEntity.setCategoryOpenStatusPC(categoryCsvDto.getCategoryOpenStatusPC());
        categoryEntity.setCategoryOpenStartTimePC(categoryCsvDto.getCategoryOpenStartTimePC());
        categoryEntity.setCategoryOpenEndTimePC(categoryCsvDto.getCategoryOpenEndTimePC());
        categoryEntity.setSiteMapFlag(categoryCsvDto.getSiteMapFlag());

        // カテゴリ表示エンティティ作成
        categoryDisplayEntity.setCategoryNamePC(categoryCsvDto.getCategoryNamePC());
        categoryDisplayEntity.setMetaDescription(categoryCsvDto.getMetaDescription());
        categoryDisplayEntity.setMetaKeyword(categoryCsvDto.getMetaKeyword());
        categoryDisplayEntity.setFreeTextPC(categoryCsvDto.getFreeTextPC());
        categoryDisplayEntity.setCategoryImagePC(categoryCsvDto.getCategoryImagePC());

        categoryDto.setCategoryEntity(categoryEntity);
        categoryDto.setCategoryDisplayEntity(categoryDisplayEntity);

        return categoryDto;
    }

}
// 2023-renew categoryCSV to here
