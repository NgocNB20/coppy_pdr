/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;

import java.io.File;

/**
 * カテゴリーCSVアップロードサービス
 *
 * @author kamei
 */
// 2023-renew categoryCSV from here
public interface CategoryCsvUpLoadService {
    /** 追加モード：カテゴリーID重複のエラーメッセージ */
    String MSGCD_REGISTED_CATEGORYID = "SGC001101";

    /** 更新モード：カテゴリーIDが存在しない場合のエラーメッセージ */
    String MSGCD_NONE_CATEGORYID = "SGC001102";

    /** 上位カテゴリーが存在しないエラー */
    String MSGCD_NONE_HIGHER_CATEGORY = "SGC001103";

    /** システムエラー時のエラーメッセージ */
    String MSGCD_FAIL_REGIST_UPDATE = "SGC001104";

    /** 公開開始日時＞公開終了日時の場合エラー */
    String MSGCD_OPEN_TIME_ERROR = "SGC001106";

    /** カテゴリー階層が12階層以上になっている場合のエラーメッセージ */
    String MSGCD_OVER_CATEGORYSEQPATH = "SGC001107";

    /** トップカテゴリーの処理がスキップされた旨を通知するメッセージ */
    String MSGCD_TOP_CATEGORY_SKIPPED = "SGC001110";

    /**
     * CSVアップロード実行<br/>
     *
     * @param uploadedFile  アップロードファイル
     * @param validLimit    バリデータチェック限度値
     * @param registFlg     登録／更新フラグ
     * @return CsvUploadResult  CSVアップロード結果
     */
    CsvUploadResult execute(File uploadedFile, int validLimit, boolean registFlg);
}
// 2023-renew categoryCSV to here
