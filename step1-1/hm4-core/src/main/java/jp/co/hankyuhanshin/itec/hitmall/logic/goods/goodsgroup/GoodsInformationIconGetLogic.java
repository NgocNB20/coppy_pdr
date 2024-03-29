/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;

import java.util.List;

/**
 * 商品アイコン表示情報リストを取得する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.4 $
 */
public interface GoodsInformationIconGetLogic {

    /**
     * 商品インフォメーションアイコン表示情報リスト取得<br/>
     * 商品インフォメーションアイコン表示情報DTOのリストを取得する<br/>
     *
     * @param informationIconSeqList 対象商品インフォメーションアイコンリスト
     * @return 商品インフォメーションアイコン表示情報DTOリスト
     */
    List<GoodsInformationIconDetailsDto> execute(List<Integer> informationIconSeqList);

}
