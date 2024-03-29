/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;

/**
 * 商品詳細情報取得クラス(商品コード)<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface GoodsDetailsGetByCodeLogic {

    // LGG00011

    /**
     * 商品詳細情報取得<br/>
     * 商品詳細情報取得<br/>
     *
     * @param shopSeq         ショップSEQ
     * @param code            商品コード
     * @param siteType        サイト区分
     * @param goodsOpenStatus 公開状態
     * @return 商品詳細DTO
     */
    GoodsDetailsDto execute(Integer shopSeq,
                            String code,
                            HTypeSiteType siteType,
                            HTypeOpenDeleteStatus goodsOpenStatus);
}
