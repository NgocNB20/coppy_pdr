/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;

import java.util.List;

/**
 * アイコン詳細Dtoリスト取得Logic
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface GoodsInformationIconDetailsDtoListGetLogic {

    /**
     * アイコン詳細Dtoリスト取得Logic
     *
     * @param shopSeq ショップSEQ
     * @return アイコン詳細Dtoリスト
     */
    List<GoodsInformationIconDetailsDto> execute(Integer shopSeq);
}
