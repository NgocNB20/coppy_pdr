/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category.bundledupload;

import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadError;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvValidationResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.InvalidDetail;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * カテゴリ一括アップロードHelper<br/>
 *
 * @author kamei
 */
// 2023-renew categoryCSV from here
@Component
public class CategoryBundledUploadHelper {

    /**
     * アップロードサービス処理後のページ反映<br/>
     *
     * @param categoryBundledUploadModel 一括登録ページ
     * @param csvUploadResult アップロード結果
     */
    public void toPageForCsvUploadResultDto(CategoryBundledUploadModel categoryBundledUploadModel,
                                            CsvUploadResult csvUploadResult) {

        // CSVバリデータエラーの場合
        if (csvUploadResult.isInValid()) {
            categoryBundledUploadModel.setErrorResultItems(new ArrayList<CategoryBundledUploadModelItem>());
            CsvValidationResult csvValidationResult = csvUploadResult.getCsvValidationResult();
            for (InvalidDetail detail : csvValidationResult.getInvalidDetailList()) {
                categoryBundledUploadModel.getErrorResultItems()
                                          .add(createCompletePageItem(detail.getRow(), detail.getColumnLabel(),
                                                                      detail.getMessage()
                                                                     ));
            }
            categoryBundledUploadModel.setValidLimitFlg(csvUploadResult.isValidLimitFlg());
            return;
        }

        // CSVデータエラーの場合
        if (csvUploadResult.isError()) {
            categoryBundledUploadModel.setErrorResultItems(new ArrayList<CategoryBundledUploadModelItem>());
            for (CsvUploadError csvUploadError : csvUploadResult.getCsvUploadErrorlList()) {
                categoryBundledUploadModel.getErrorResultItems()
                                          .add(createCompletePageItem(csvUploadError.getRow(), null,
                                                                      csvUploadError.getMessage()
                                                                     ));
            }
            return;
        }

        // 正常終了した場合
        categoryBundledUploadModel.setUploadCount(csvUploadResult.getRecordCount() - 1);
    }

    /**
     * 完了ページアイテムを生成<br/>
     *
     * @param row 行番号
     * @param columnName カラム名
     * @param message エラー内容
     * @return item
     */
    protected CategoryBundledUploadModelItem createCompletePageItem(Integer row, String columnName, String message) {

        CategoryBundledUploadModelItem item = ApplicationContextUtility.getBean(CategoryBundledUploadModelItem.class);
        item.setRow(row);
        item.setColumnName(columnName);
        item.setMessage(message);
        return item;
    }
}
// 2023-renew categoryCSV to here
