/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;

/**
 * お気に入り情報登録サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface FavoriteRegistService {

    /**
     * 商品不在エラー<br/>
     * <code>MSGCD_GOODS_NOT_EXIST</code>
     */
    String MSGCD_GOODS_NOT_EXIST = "SMF000101";

    /**
     * お気に入り商品登録エラー<br/>
     * <code>MSGCD_FAVORITE_GOODS_REGIST_FAIL</code>
     */
    String MSGCD_FAVORITE_GOODS_REGIST_FAIL = "SMF000102";

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
