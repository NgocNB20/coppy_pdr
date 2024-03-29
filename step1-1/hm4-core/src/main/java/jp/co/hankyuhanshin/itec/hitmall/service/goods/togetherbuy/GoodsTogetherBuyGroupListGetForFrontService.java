// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;

import java.util.List;

/**
 * よく一緒に購入される商品 グループリストを購入 フロントサービスを利用する
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public interface GoodsTogetherBuyGroupListGetForFrontService {

    /**
     * 商品グループDtoリスト取得
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param limit         最大取得件数
     * @return 商品グループDto
     */
    List<GoodsGroupDto> execute(Integer goodsGroupSeq, int limit);
}
// 2023-renew No21 to here
