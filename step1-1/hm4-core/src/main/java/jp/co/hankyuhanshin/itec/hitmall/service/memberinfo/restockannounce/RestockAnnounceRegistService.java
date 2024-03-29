// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;

/**
 * 入荷お知らせ情報登録サービス<br/>
 *
 * @author Thang Doan (VJP)
 */
public interface RestockAnnounceRegistService {

    /**
     * 商品不在エラー<br/>
     */
    String MSGCD_GOODS_NOT_EXIST = "SMF000101";

    /**
     * 入荷お知ら商品登録エラー<br/>
     */
    String MSGCD_RESTOCK_ANNOUNCE_GOODS_REGIST_FAIL = "SMF000103";

    /**
     * サービス実行<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsCode     商品コード
     * @param siteType      サイト区分
     * @return 登録件数
     */
    int execute(Integer memberInfoSeq, String goodsCode, HTypeSiteType siteType);
}
// 2023-renew No65 to here
