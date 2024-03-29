/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteDto;

import java.util.List;

/**
 * お気に入り商品の在庫状態取得ロジック<br/>
 *
 * @author Kaneko　2013/03/01
 */
public interface FavoriteGoodsStockStatusGetLogic {

    /**
     * お気に入り商品の在庫状態の設定。<br/>
     * <pre>
     * 商品の公開状態、公開期間、販売状態、販売期間、在庫数条件に基づいて在庫状態を決定する。
     * 在庫状態判定の詳細は「26_HM3_共通部仕様書_在庫状態表示条件.xls」参照。
     * </pre>
     *
     * @param favoriteDtoList お気に入り商品DTOリスト
     * @return 在庫ステータス
     */
    List<FavoriteDto> execute(List<FavoriteDto> favoriteDtoList);
}
