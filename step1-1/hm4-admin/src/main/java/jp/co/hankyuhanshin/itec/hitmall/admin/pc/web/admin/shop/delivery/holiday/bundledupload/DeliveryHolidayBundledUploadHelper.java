/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.holiday.bundledupload;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadError;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvValidationResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.InvalidDetail;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 一括アップロード<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class DeliveryHolidayBundledUploadHelper {

    /**
     * サービス処理後のページへ反映<br/>
     *
     * @param uploadPage      アップロードファイル
     * @param csvUploadResult Csvアップロード結果
     */
    public void toPageForCsvUploadResultDto(DeliveryHolidayBundledUploadModel uploadPage,
                                            CsvUploadResult csvUploadResult) {

        /* エラーリストのクリア */
        uploadPage.setResultItems(null);

        /* CSVバリデータエラーの場合 */
        if (csvUploadResult.isInValid()) {
            uploadPage.setResultItems(new ArrayList<DeliveryHolidayBundledUploadFinishModelItem>());
            CsvValidationResult csvValidationResult = csvUploadResult.getCsvValidationResult();
            for (InvalidDetail detail : csvValidationResult.getInvalidDetailList()) {
                uploadPage.getResultItems()
                          .add(cretaeUploadFinishPageItem(detail.getRow(), detail.getColumnLabel(),
                                                          detail.getMessage()
                                                         ));
            }
            uploadPage.setValidLimitFlg(csvUploadResult.isValidLimitFlg());
            return;
        }

        /* CSVデータエラーの場合 */
        if (csvUploadResult.isError()) {
            uploadPage.setResultItems(new ArrayList<DeliveryHolidayBundledUploadFinishModelItem>());
            for (CsvUploadError csvUploadError : csvUploadResult.getCsvUploadErrorlList()) {
                uploadPage.getResultItems()
                          .add(cretaeUploadFinishPageItem(csvUploadError.getRow(), null, csvUploadError.getMessage()));
            }
            return;
        }

        /* 正常終了した場合 */

        // 正常完了(ヘッダー行分を-1している)
        uploadPage.setUploadCount(csvUploadResult.getRecordCount() - 1);
    }

    /**
     * 完了ページのItemを生成<br/>
     *
     * @param row        行番号
     * @param columnName カラム名
     * @param message    内容
     * @return Item
     */
    protected DeliveryHolidayBundledUploadFinishModelItem cretaeUploadFinishPageItem(Integer row,
                                                                                     String columnName,
                                                                                     String message) {
        DeliveryHolidayBundledUploadFinishModelItem item =
                        ApplicationContextUtility.getBean(DeliveryHolidayBundledUploadFinishModelItem.class);
        item.setRow(row);
        item.setColumnName(columnName);
        item.setMessage(message);
        return item;
    }

    /**
     * 検索結果をUploadPageに反映します
     *
     * @param uploadPage   UploadPage
     * @param resultEntity DeliveryMethodEntity
     */
    public void convertToRegistPageForResult(DeliveryHolidayBundledUploadModel uploadPage,
                                             DeliveryMethodEntity resultEntity) {
        uploadPage.setDeliveryMethodName(resultEntity.getDeliveryMethodName());
        uploadPage.setDeliveryMethodType(resultEntity.getDeliveryMethodType());
        uploadPage.setOpenStatusPC(resultEntity.getOpenStatusPC());
    }

}
