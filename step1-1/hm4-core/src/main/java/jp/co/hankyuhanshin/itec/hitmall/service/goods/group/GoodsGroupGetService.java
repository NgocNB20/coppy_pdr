/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.group;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;

/**
 * 商品グループ取得<br/>
 *
 * @author hirata
 * @version $Revision: 1.4 $
 */
public interface GoodsGroupGetService {

    /**
     * 実行メソッド<br/>
     *
     * @param goodsGroupCode 商品グループコード
     * @param siteType サイト種別
     * @return 商品グループDto
     */
    GoodsGroupDto execute(String goodsGroupCode, HTypeSiteType siteType);
    // SGP0006

}
