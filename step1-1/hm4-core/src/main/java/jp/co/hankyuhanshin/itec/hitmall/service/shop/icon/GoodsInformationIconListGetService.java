/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;

import java.util.List;

/**
 * アイコンリスト取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface GoodsInformationIconListGetService {

    /**
     * ショップSEQを元に全アイコン情報を取得する<br/>
     *
     * @return アイコンDTO
     */
    List<GoodsInformationIconDto> execute();
}
