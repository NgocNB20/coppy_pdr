/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;

/**
 * アイコンDto取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface GoodsInformationIconDtoGetLogic {

    /**
     * アイコンSEQ、ショップSEQと一致するアイコンDto取得
     *
     * @param iconSeq アイコンSEQ
     * @param shopSeq ショップSEQ
     * @return アイコンDto
     */
    GoodsInformationIconDto execute(Integer iconSeq, Integer shopSeq);
}
