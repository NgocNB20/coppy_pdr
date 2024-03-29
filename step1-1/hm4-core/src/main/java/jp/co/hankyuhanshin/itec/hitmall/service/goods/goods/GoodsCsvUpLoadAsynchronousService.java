/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadType;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;

import java.io.File;

/**
 * 商品一括アップロードインターフェース
 * 商品データCSVファイル、商品画像の一括アップロードを行う
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
public interface GoodsCsvUpLoadAsynchronousService {

    /**
     * 関連商品登録件数超過
     */
    public static final String RELATIONGOODS_OVER_FAIL = "SGG000401";

    /**
     * 関連商品で指定された商品グループが存在しない
     */
    public static final String RELATIONGOODS_NONE_FAIL = "SGG000402";

    /**
     * カテゴリ設定で指定されたカテゴリが存在しない
     */
    public static final String CATEGORY_NONE_FAIL = "SGG000403";

    /**
     * 会員ランク表示設定で指定された会員ランクにゲストが含まれている
     */
    public static final String MSGCD_GUIEST_POINTING_AT_GUEST = "PKG-3794-001-S-";

    /**
     * 会員ランク表示設定で指定された会員ランクが存在しない
     */
    public static final String MEMBER_RANL_NONE_FAIL = "PKG-3549-002-S-";

    /**
     * 関連商品に自商品を登録
     */
    public static final String RELATIONGOODS_MYSELF_FAIL = "SGG000406";

    /**
     * 関連商品重複
     */
    public static final String RELATIONGOODS_REPETITION_FAIL = "SGG000407";

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品
     */
    public static final String GOODSTOGETHERBUYGROUP_REPETITION_FAIL = "PDR-2023RENEW-21-001-";

    /**
     * よく一緒に購入されるに自商品を登録
     */
    public static final String GOODSTOGETHERBUYGROUP_MYSELF_FAIL = "PDR-2023RENEW-21-002-";

    /**
     * よく一緒に購入されるに自商品登録件数超過
     */
    public static final String GOODSTOGETHERBUYGROUP_OVER_FAIL = "PDR-2023RENEW-21-003-";

    /**
     * よく一緒に購入されるに自商品で指定された商品グループが存在しない
     */
    public static final String GOODSTOGETHERBUYGROUP_NONE_FAIL = "PDR-2023RENEW-21-004-";
    // 2023-renew No21 to here

    /**
     * カテゴリ重複
     */
    public static final String CATEGORY_REPETITION_FAIL = "SGG000408";

    /**
     * 会員ランク重複
     */
    public static final String MEMBER_RANK_REPETITION_FAIL = "PKG-3549-003-S-";

    /**
     * 更新対象規格なしエラー（更新モードで対象規格なし）
     */
    public static final String UPDATEGOODS_NONE_FAIL = "SGG000409";

    /**
     * 登録規格が既に存在するエラー（追加モードで対象規格あり）
     */
    public static final String REGISTGOODS_EXIST_FAIL = "SGG000410";

    /**
     * 更新対象規格が削除されているエラー（更新モードで販売状態=削除）
     */
    public static final String UPDATEGOODS_DELETED_FAIL = "SGG000411";

    /**
     * 規格表示順重複 エラー
     */
    public static final String OREDERDISPLAY_DUPLICATE_FAIL = "SGG000412";

    /**
     * 商品管理番号を連続して設定していない（例：110,110,111,112,110） エラー
     */
    public static final String SGP001086 = "SGP001086";

    /**
     * 同一商品番号を複数設定 エラー
     */
    public static final String SGP001087 = "SGP001087";

    /**
     * 値が不正です。別の値を入力してください。
     */
    public static final String CSVUPLOAD002E = "CSV-UPLOAD-002-E";

    // 2023-renew No76 from here
    /**
     * 商品ごとの規格登録数上限値
     */
    public static final Integer LIMIT_GOODS_COUNT = 200;
    /**
     * 商品ごとの規格登録数上限を超過した場合エラー
     */
    public static final String MSGCD_OVER_GOODS_COUNT = "PDR-2023RENEW-76-002-";
    // 2023-renew No76 to here

    /**
     * 商品一括アップロード処理実行
     *
     * @param uploadedCsvFile 商品CSVデータアップロードファイル
     * @param uploadType      アップロード種別
     * @param siteType サイト種別
     * @return CsvUploadResult CSVアップロード結果Dto
     */
    CsvUploadResult execute(File uploadedCsvFile,
                            HTypeUploadType uploadType,
                            String administratorName,
                            HTypeSiteType siteType);

}
