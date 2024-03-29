/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon;

/**
 * 商品インフォメーションアイコン　行ロック
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface GoodsInformationIconLockLogic {

    /* メッセージ LSM0008 */
    /**
     * 商品インフォメーションアイコンロック失敗エラー<br/>
     * <code>MSGCD_GOODSINFORMATIONICON_LOCK_FAIL</code>
     */
    public static final String MSGCD_GOODSINFORMATIONICON_LOCK_FAIL = "LSM000801";

    /**
     * 商品インフォメーションアイコンテーブル内の指定アイコンSEQと一致する行を行ロックする
     *
     * @param iconSeq アイコンSEQ
     */
    void execute(Integer iconSeq);
}
