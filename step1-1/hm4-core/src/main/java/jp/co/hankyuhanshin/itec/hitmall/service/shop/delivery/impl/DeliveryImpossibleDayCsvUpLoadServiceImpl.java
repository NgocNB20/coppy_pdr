/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvReaderOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleDayCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleDayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleDayDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleDayGetByYearAndDateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleDayRegistUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryImpossibleDayCsvUpLoadService;
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
 * お届け不可日CSVアップロードサービス<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Service
public class DeliveryImpossibleDayCsvUpLoadServiceImpl extends AbstractShopService
                implements DeliveryImpossibleDayCsvUpLoadService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryImpossibleDayCsvUpLoadServiceImpl.class);

    /**
     * メッセージコード：年月日重複エラー
     */
    public static final String MSGCD_DATE_DUPLICATE_ERROR = "HM34-4001-001-L-";

    /**
     * メッセージコード：登録失敗
     */
    public static final String MSGCD_REGIST_FAILURE = "HM34-4001-002-L-";

    /**
     * お届け不可日情報登録ロジック<br/>
     */
    private final DeliveryImpossibleDayRegistUpdateLogic deliveryImpossibleDayRegistLogic;

    /**
     * お届け不可日情報削除ロジック<br/>
     */
    private final DeliveryImpossibleDayDeleteLogic deliveryImpossibleDayDeleteLogic;

    /**
     * お届け不可日情報取得ロジック<br/>
     */
    private final DeliveryImpossibleDayGetByYearAndDateLogic deliveryImpossibleDayGetByYearAndDateLogic;

    /**
     * CSV読み込みのユーティリティ<br/>
     */
    private final CsvReaderUtil csvReaderUtil;

    @Autowired
    public DeliveryImpossibleDayCsvUpLoadServiceImpl(DeliveryImpossibleDayRegistUpdateLogic deliveryImpossibleDayRegistLogic,
                                                     DeliveryImpossibleDayDeleteLogic deliveryImpossibleDayDeleteLogic,
                                                     DeliveryImpossibleDayGetByYearAndDateLogic deliveryImpossibleDayGetByYearAndDateLogic) {
        this.deliveryImpossibleDayRegistLogic = deliveryImpossibleDayRegistLogic;
        this.deliveryImpossibleDayDeleteLogic = deliveryImpossibleDayDeleteLogic;
        this.deliveryImpossibleDayGetByYearAndDateLogic = deliveryImpossibleDayGetByYearAndDateLogic;
        this.csvReaderUtil = new CsvReaderUtil();
    }

    /**
     * お届け不可日CSV登録処理<br/>
     *
     * @param uploadedFile      アップロードファイル
     * @param validLimit        バリデータエラー限界値(行数)
     * @param year              年
     * @param deliveryMethodSeq 配送方法SEQ
     * @return CSVアップロード結果
     */
    @Override
    public CsvUploadResult execute(File uploadedFile, int validLimit, Integer year, Integer deliveryMethodSeq) {

        // CSV読み込みオプションを設定する
        CsvReaderOptionDto csvReaderOptionDto = new CsvReaderOptionDto();
        csvReaderOptionDto.setValidLimit(validLimit);

        // Csvアップロード結果Dto作成
        CsvUploadResult csvUploadResult = new CsvUploadResult();

        /* Csvファイルを読み込み */
        List<DeliveryImpossibleDayCsvDto> deliveryImpossibleDayCsvDtoList;
        try {
            deliveryImpossibleDayCsvDtoList = (List<DeliveryImpossibleDayCsvDto>) csvReaderUtil.readCsv(uploadedFile,
                                                                                                        DeliveryImpossibleDayCsvDto.class,
                                                                                                        csvUploadResult,
                                                                                                        csvReaderOptionDto
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
        if (validate(deliveryImpossibleDayCsvDtoList, validLimit, csvUploadResult, deliveryMethodSeq)) {
            return csvUploadResult;
        }

        /* 登録処理 エラーの場合 終了 */
        if (registProcess(deliveryImpossibleDayCsvDtoList, year, deliveryMethodSeq, csvUploadResult)) {
            return csvUploadResult;
        }

        // 正常終了時は、処理件数を返す
        return csvUploadResult;
    }

    /**
     * 登録処理<br/>
     *
     * @param deliveryImpossibleDayCsvDtoList
     * @param year                            年
     * @param deliveryMethodSeq               配送方法SEQ
     * @param csvUploadResult                 CSVアップロード結果
     * @return エラーの有無 true=エラー、false=エラーなし
     */
    protected boolean registProcess(List<DeliveryImpossibleDayCsvDto> deliveryImpossibleDayCsvDtoList,
                                    Integer year,
                                    Integer deliveryMethodSeq,
                                    CsvUploadResult csvUploadResult) {

        // これから登録する年のデータを削除
        DeliveryImpossibleDayEntity deliveryImpossibleDayDeleteEntity = new DeliveryImpossibleDayEntity();
        deliveryImpossibleDayDeleteEntity.setYear(year);
        deliveryImpossibleDayDeleteEntity.setDeliveryMethodSeq(deliveryMethodSeq);
        delete(deliveryImpossibleDayDeleteEntity);

        /* 処理件数が気になるので1件づつ処理 */

        // 日付重複チェック用セット
        Set<Date> hashSet = new HashSet<>();
        // CSV のレコードを DTO に変換する
        int recodeCount = 2; // CSVファイルの行数にあわす為、2始まり
        for (DeliveryImpossibleDayCsvDto deliveryImpossibleDayCsvDto : deliveryImpossibleDayCsvDtoList) {
            DeliveryImpossibleDayEntity deliveryImpossibleDayEntity =
                            createDeliveryImpossibleDayEntity(deliveryImpossibleDayCsvDto);

            // 年を設定
            deliveryImpossibleDayEntity.setYear(year);

            /* 登録処理 */
            if (regist(deliveryImpossibleDayEntity, recodeCount, csvUploadResult, hashSet)) {
                return true;
            }

            recodeCount++;
        }
        return false;
    }

    /**
     * 削除処理<br/>
     *
     * @param deliveryImpossibleDayDeleteEntity お届け不可日エンティティ
     * @return 削除件数
     */
    protected int delete(DeliveryImpossibleDayEntity deliveryImpossibleDayDeleteEntity) {
        // お届け不可日情報
        return deliveryImpossibleDayDeleteLogic.execute(deliveryImpossibleDayDeleteEntity);
    }

    /**
     * 登録処理<br/>
     *
     * @param deliveryImpossibleDayEntity お届け不可日エンティティ
     * @param recodeCount                 レコード数
     * @param csvUploadResult             Csvアップロード結果Dto
     * @param hashSet                     CSV日付リスト
     * @return エラーの有無 true=エラー、false=エラーなし
     */
    protected boolean regist(DeliveryImpossibleDayEntity deliveryImpossibleDayEntity,
                             int recodeCount,
                             CsvUploadResult csvUploadResult,
                             Set<Date> hashSet) {

        List<CsvUploadError> csvUploadErrorList = new ArrayList<>();

        // 年月日の年と、選択された年が異なる場合エラー
        LocalDate localDate =
                        deliveryImpossibleDayEntity.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int deliveryEntityYear = localDate.getYear();
        if (deliveryEntityYear != deliveryImpossibleDayEntity.getYear()) {
            csvUploadErrorList.add(createCsvUploadError(recodeCount, "SYH000203E", new Object[] {"年", "年月日"}));
            csvUploadResult.setCsvUploadErrorlList(csvUploadErrorList);
            return true;
        }

        // 年月日の重複チェック
        if (!hashSet.add(deliveryImpossibleDayEntity.getDate())) {
            // エラーメッセージを作成・セット
            csvUploadErrorList.add(createCsvUploadError(recodeCount, MSGCD_DATE_DUPLICATE_ERROR, null));
            csvUploadResult.setCsvUploadErrorlList(csvUploadErrorList);
            return true;
        }

        // お届け不可日情報登録
        if (deliveryImpossibleDayRegistLogic.execute(deliveryImpossibleDayEntity) != 1) {
            // エラーメッセージを作成・セット
            csvUploadErrorList.add(createCsvUploadError(recodeCount, MSGCD_REGIST_FAILURE, null));
            csvUploadResult.setCsvUploadErrorlList(csvUploadErrorList);
            return true;
        }
        return false;
    }

    /**
     * CSVアップロードエラーDto作成<br/>
     *
     * @param recodeCount レコード数
     * @param messageCode メッセージコード
     * @param args        引数
     * @return CSVアップロードエラーDto
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
     * CSVからお届け不可日エンティティ作成<br/>
     *
     * @param deliveryImpossibleDayCsvDto CsvDto
     * @return お届け不可日エンティティ
     */
    protected DeliveryImpossibleDayEntity createDeliveryImpossibleDayEntity(DeliveryImpossibleDayCsvDto deliveryImpossibleDayCsvDto) {
        DeliveryImpossibleDayEntity deliveryImpossibleDayEntity = new DeliveryImpossibleDayEntity();
        deliveryImpossibleDayEntity.setDate(deliveryImpossibleDayCsvDto.getDate());
        deliveryImpossibleDayEntity.setReason(deliveryImpossibleDayCsvDto.getReason());
        deliveryImpossibleDayEntity.setDeliveryMethodSeq(deliveryImpossibleDayCsvDto.getDeliveryMethodSeq());
        return deliveryImpossibleDayEntity;
    }

    /**
     * csvValidationResult<br/>
     *
     * @param deliveryImpossibleDayCsvDtoList CSVDtoリスト
     * @param validLimit                      バリデート限界値
     * @param csvUploadResult                 CSVアップロード結果
     * @param deliveryMethodSeq               配送方法SEQ
     * @return バリデーション結果Dto
     */
    protected boolean validate(List<DeliveryImpossibleDayCsvDto> deliveryImpossibleDayCsvDtoList,
                               int validLimit,
                               CsvUploadResult csvUploadResult,
                               Integer deliveryMethodSeq) {

        // 処理件数はヘッダー行分を含めて1からカウント
        int recordCounter = 1;
        // バリデータエラーカウンタ
        int errorCounter = 0;

        // Csvバリデータ結果
        CsvValidationResult csvValidationResult = new CsvValidationResult();

        for (DeliveryImpossibleDayCsvDto csvDto : deliveryImpossibleDayCsvDtoList) {
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
