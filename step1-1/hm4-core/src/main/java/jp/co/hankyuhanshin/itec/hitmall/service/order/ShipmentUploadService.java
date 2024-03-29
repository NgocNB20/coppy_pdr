/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvValidationResult;

import java.io.File;
import java.util.List;

/**
 * 出荷CSVファイルアップロードサービスインターフェース
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
public interface ShipmentUploadService {

    /**
     * アップロードされたファイルの検証を行う
     *
     * @param csvFile               ファイル
     * @param counts                返却用レコード件数
     * @param maximumErrorDetection 検出するエラーの最大数
     * @return バリデーション結果
     */
    CsvValidationResult execute(File csvFile, List<Integer> counts, int maximumErrorDetection);

}
