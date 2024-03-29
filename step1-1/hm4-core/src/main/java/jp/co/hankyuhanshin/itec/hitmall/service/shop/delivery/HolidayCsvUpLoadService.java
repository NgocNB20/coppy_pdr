/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;

import java.io.File;

/**
 * 休日Csvアップロードサービス<br/>
 *
 * @author Author: ogawa
 */
public interface HolidayCsvUpLoadService {

    /**
     * 休日Csv登録処理<br/>
     *
     * @param uploadedFile      アップロードファイル
     * @param validLimit        バリデータエラー限界値(行数)
     * @param year              年
     * @param deliveryMethodSeq 配送方法SEQ
     * @return Csvアップロード結果
     */
    CsvUploadResult execute(File uploadedFile, int validLimit, Integer year, Integer deliveryMethodSeq);

}
