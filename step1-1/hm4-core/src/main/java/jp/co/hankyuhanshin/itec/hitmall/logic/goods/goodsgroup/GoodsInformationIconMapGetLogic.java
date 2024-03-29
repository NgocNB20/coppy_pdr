/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;

import java.util.List;
import java.util.Map;

/**
 * 商品アイコン情報Mapを取得する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface GoodsInformationIconMapGetLogic {

    /**
     * 商品インフォメーションアイコンMap取得<br/>
     * 商品インフォメーションアイコン情報DTOのMapを取得する<br/>
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @param siteType          サイト区分
     * @return 商品インフォメーションアイコンDTOMap
     */
    Map<Integer, List<GoodsInformationIconDetailsDto>> execute(List<Integer> goodsGroupSeqList, HTypeSiteType siteType);

}
