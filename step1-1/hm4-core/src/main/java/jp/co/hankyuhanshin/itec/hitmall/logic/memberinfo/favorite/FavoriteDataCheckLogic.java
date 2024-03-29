/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite;

import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;

import java.util.List;

/**
 * お気に入りデータチェックロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
public interface FavoriteDataCheckLogic {

    /**
     * お気に入り最大登録件数を超えた場合<br/>
     * <code>MSGCD_FAVORITE_GOODS_MAX_COUNT_FAIL</code>
     */
    public static final String MSGCD_FAVORITE_GOODS_MAX_COUNT_FAIL = "LMF000401";

    /**
     * ロジック実行<br/>
     *
     * @param favoriteEntity お気に入りエンティティ
     */
    void execute(FavoriteEntity favoriteEntity);

    /**
     * お気に入り取得
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsDtoList  商品Dtoリスト
     * @return お気に入りリスト
     */
    List<FavoriteEntity> getFavoriteEntityListForGoods(Integer memberInfoSeq, List<GoodsDto> goodsDtoList);

    /**
     * お気に入り取得
     *
     * @param memberInfoSeq 会員SEQ
     * @param cartDto       カート情報
     * @return お気に入りリスト
     */
    List<FavoriteEntity> getFavoriteEntityListForCart(Integer memberInfoSeq, CartDto cartDto);
}
