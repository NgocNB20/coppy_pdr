/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvReaderOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.HolidayCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.HolidayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.HolidayDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.HolidayGetByYearAndDateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.HolidayRegistUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.HolidayCsvUpLoadService;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvReaderUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadError;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvValidationResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.InvalidDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 休日Csvアップロードサービス<br/>
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Service
public class HolidayCsvUpLoadServiceImpl extends AbstractShopService implements HolidayCsvUpLoadService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HolidayCsvUpLoadServiceImpl.class);

    /**
     * 休日情報登録ロジック<br/>
     */
    private final HolidayRegistUpdateLogic holidayRegistLogic;

    /**
     * 休日情報削除ロジック<br/>
     */
    private final HolidayDeleteLogic holidayDeleteLogic;

    /**
     * 休日情報取得ロジック<br/>
     */
    private final HolidayGetByYearAndDateLogic holidayGetByYearAndDateLogic;

    /**
     * CSV読み込みのユーティリティ<br/>
     */
    private final CsvReaderUtil csvReaderUtil;

    @Autowired
    public HolidayCsvUpLoadServiceImpl(HolidayRegistUpdateLogic holidayRegistLogic,
                                       HolidayDeleteLogic holidayDeleteLogic,
                                       HolidayGetByYearAndDateLogic holidayGetByYearAndDateLogic) {
        this.holidayRegistLogic = holidayRegistLogic;
        this.holidayDeleteLogic = holidayDeleteLogic;
        this.holidayGetByYearAndDateLogic = holidayGetByYearAndDateLogic;
        this.csvReaderUtil = new CsvReaderUtil();
    }

    /**
     * 休日Csv登録処理<br/>
     *
     * @param uploadedFile      アップロードファイル
     * @param validLimit        バリデータエラー限界値(行数)
     * @param year              年
     * @param deliveryMethodSeq 配送方法SEQ
     * @return Csvアップロード結果
     */
    @Override
    public CsvUploadResult execute(File uploadedFile, int validLimit, Integer year, Integer deliveryMethodSeq) {

        // CSV読み込みオプションを設定する
        CsvReaderOptionDto csvReaderOptionDto = new CsvReaderOptionDto();
        csvReaderOptionDto.setValidLimit(validLimit);

        // Csvアップロード結果Dto作成
        CsvUploadResult csvUploadResult = new CsvUploadResult();

        /* Csvファイルを読み込み */
        List<HolidayCsvDto> holidayCsvDtoList;
        try {
            holidayCsvDtoList = (List<HolidayCsvDto>) csvReaderUtil.readCsv(uploadedFile, HolidayCsvDto.class,
                                                                            csvUploadResult, csvReaderOptionDto
                                                                           );
        } catch (Exception e) {
            // CSV読み込みで有り得ない例外が発生した場合
            LOGGER.error("例外処理が発生しました", e);
            csvReaderUtil.createUnexpectedExceptionMsg(csvUploadResult);
            return csvUploadResult;
        }

        /* CSV読み込みによるチェック エラーの場合 終了 */
        if (csvUploadResult.getErrorCount() > 0) {
            return csvUploadResult;
        }

        /* 入力チェック エラーの場合 終了 */
        if (validate(holidayCsvDtoList, validLimit, csvUploadResult, deliveryMethodSeq)) {
            return csvUploadResult;
        }

        /* 登録処理 エラーの場合 終了 */
        if (registProcess(holidayCsvDtoList, year, deliveryMethodSeq, csvUploadResult)) {
            return csvUploadResult;
        }

        // 正常終了時は、処理件数を返す
        return csvUploadResult;
    }

    /**
     * 登録処理<br/>
     *
     * @param holidayCsvDtoList CsvDtoリスト
     * @param year              年
     * @param deliveryMethodSeq 配送方法SEQ
     * @param csvUploadResult   Csvアップロード結果
     * @return エラーの有無 true=エラー、false=エラーなし
     */
    protected boolean registProcess(List<HolidayCsvDto> holidayCsvDtoList,
                                    Integer year,
                                    Integer deliveryMethodSeq,
                                    CsvUploadResult csvUploadResult) {

        // これから登録する年のデータを削除
        HolidayEntity holidayDeleteEntity = new HolidayEntity();
        holidayDeleteEntity.setYear(year);
        holidayDeleteEntity.setDeliveryMethodSeq(deliveryMethodSeq);
        delete(holidayDeleteEntity);

        /* 処理件数が気になるので1件づつ処理 */

        // 日付重複チェック用セット
        Set<Date> hashSet = new HashSet<>();
        // CSV のレコードを DTO に変換する
        int recodeCount = 2; // CSVファイルの行数にあわす為、2始まり
        for (HolidayCsvDto holidayCsvDto : holidayCsvDtoList) {

            // HolidayCsvDtoから登録用のHolidayEntityに変換する
            HolidayEntity holidayEntity = createHolidayEntity(holidayCsvDto);

            // 年を設定
            holidayEntity.setYear(year);

            /* 登録処理 */
            if (regist(holidayEntity, recodeCount, csvUploadResult, hashSet)) {
                return true;
            }

            recodeCount++;
        }
        return false;
    }

    /**
     * 削除処理<br/>
     *
     * @param holidayDeleteEntity 休日エンティティ
     * @return 削除件数
     */
    protected int delete(HolidayEntity holidayDeleteEntity) {
        // 休日情報
        return holidayDeleteLogic.execute(holidayDeleteEntity);
    }

    /**
     * 登録処理<br/>
     *
     * @param holidayEntity   休日エンティティ
     * @param recodeCount     レコード数
     * @param csvUploadResult Csvアップロード結果Dto
     * @param hashSet         Csv日付リスト
     * @return エラーの有無 true=エラー、false=エラーなし
     */
    protected boolean regist(HolidayEntity holidayEntity,
                             int recodeCount,
                             CsvUploadResult csvUploadResult,
                             Set<Date> hashSet) {

        List<CsvUploadError> csvUploadErrorList = new ArrayList<>();

        // 年月日の年と、選択された年が異なる場合エラー
        LocalDate localDate = holidayEntity.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int holidayEntityYear = localDate.getYear();
        if (holidayEntityYear != holidayEntity.getYear()) {
            csvUploadErrorList.add(createCsvUploadError(recodeCount, "SYH000203E", new Object[] {"年", "年月日"}));
            csvUploadResult.setCsvUploadErrorlList(csvUploadErrorList);
            return true;
        }

        // 年月日の重複チェック
        if (!hashSet.add(holidayEntity.getDate())) {
            // エラーメッセージを作成・セット
            csvUploadErrorList.add(createCsvUploadError(recodeCount, "SYH000201E", null));
            csvUploadResult.setCsvUploadErrorlList(csvUploadErrorList);
            return true;
        }

        // 休日情報登録
        if (holidayRegistLogic.execute(holidayEntity) != 1) {
            // エラーメッセージを作成・セット
            csvUploadErrorList.add(createCsvUploadError(recodeCount, "SYH000202E", null));
            csvUploadResult.setCsvUploadErrorlList(csvUploadErrorList);
            return true;
        }

        return false;
    }

    /**
     * CsvアップロードエラーDto作成<br/>
     *
     * @param recodeCount レコード数
     * @param messageCode メッセージコード
     * @param args        引数
     * @return CsvアップロードエラーDto
     */
    protected CsvUploadError createCsvUploadError(Integer recodeCount, String messageCode, Object[] args) {
        CsvUploadError csvUploadError = new CsvUploadError();
        csvUploadError.setRow(recodeCount);
        csvUploadError.setMessageCode(messageCode);
        csvUploadError.setArgs(args);
        csvUploadError.setMessage(AppLevelFacesMessageUtil.getAllMessage(messageCode, args).getMessage());
        return csvUploadError;
    }

    /**
     * Csvから休日エンティティ作成<br/>
     *
     * @param holidayCsvDto CsvDto
     * @return 休日エンティティ
     */
    protected HolidayEntity createHolidayEntity(HolidayCsvDto holidayCsvDto) {
        HolidayEntity holidayEntity = new HolidayEntity();
        holidayEntity.setDate(holidayCsvDto.getDate());
        holidayEntity.setName(holidayCsvDto.getName());
        holidayEntity.setDeliveryMethodSeq(holidayCsvDto.getDeliveryMethodSeq());
        return holidayEntity;
    }

    /**
     * csvValidationResult<br/>
     *
     * @param holidayCsvDtoList CsvDtoリスト
     * @param validLimit        バリデート限界値
     * @param csvUploadResult   Csvアップロード結果
     * @param deliveryMethodSeq 配送方法SEQ
     * @return バリデーション結果Dto
     */
    protected boolean validate(List<HolidayCsvDto> holidayCsvDtoList,
                               int validLimit,
                               CsvUploadResult csvUploadResult,
                               Integer deliveryMethodSeq) {

        // 処理件数はヘッダー行分を含めて1からカウント
        int recordCounter = 1;
        // バリデータエラーカウンタ
        int errorCounter = 0;

        // Csvバリデータ結果
        CsvValidationResult csvValidationResult = new CsvValidationResult();

        for (HolidayCsvDto csvDto : holidayCsvDtoList) {
            recordCounter++;

            // バリデータエラー行数限界値の場合 終了
            if (validLimit != -1 && errorCounter >= validLimit) {
                csvUploadResult.setValidLimitFlg(true); // more表示フラグON
                break;
            }

            // 配送方法SEQのチェック
            // ---------------------------------------------------------------------------------------
            // 現行HIT-MALL3の実装方法だと、以下のケースならhelper.getValidationResult()が空だと思う
            // ・CSV読み込みでエラー発生しない ⇒ バリデーション結果（ValidationResult）が空のハズ
            // ・配送方法SEQの妥当性チェックでエラーが発生する ⇒ ValidationResultにエラー内容を登録しないといけない
            //
            // ValidationResultが空のため、csvUploadResultにセットするとしても意味がない
            // Controllerで使われる csvUploadResult.isInValid() チェックで判定結果がfalseになるハズ
            // ---------------------------------------------------------------------------------------
            if (!csvDto.getDeliveryMethodSeq().equals(deliveryMethodSeq)) {
                errorCounter++;
                // 検証NG詳細リストにエラー内容を追加する
                csvValidationResult.getDetailList()
                                   .add(new InvalidDetail(recordCounter, "配送方法SEQ",
                                                          AppLevelFacesMessageUtil.getAllMessage(
                                                                          CsvReaderUtil.UNEXPECTED_VALUE_MSG_CD, null)
                                                                                  .getMessage()
                                   ));
            }
        }

        // エラーあり
        if (errorCounter != 0) {
            csvUploadResult.setCsvValidationResult(csvValidationResult);
            csvUploadResult.setRecordCount(1); // ヘッダー行の1をセット
            csvUploadResult.setErrorCount(errorCounter);
            return true;
        }

        // エラーなし
        return false;
    }

}
