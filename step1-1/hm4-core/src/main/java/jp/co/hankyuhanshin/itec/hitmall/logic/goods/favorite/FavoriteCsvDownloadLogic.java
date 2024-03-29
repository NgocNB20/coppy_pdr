/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.favorite;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteCsvDto;

import java.util.List;
import java.util.stream.Stream;

/**
 * お気に入り商品検索CSV一括ダウンロードロジックインターフェース<br/>
 *
 * @author takashima
 */
public interface FavoriteCsvDownloadLogic {
    Stream<FavoriteCsvDto> execute(List<Integer> favoriteSeqList);
}
