/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.group;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;

import java.util.List;

/**
 * 公開商品グループ詳細情報取得<br/>
 * 代表商品SEQを元に、同じ代表商品を持つ公開中の商品情報のリストを取得する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.3 $
 */
public interface OpenGoodsGroupDetailsGetService {

    // SGP0001

    /**
     * 公開商品グループ詳細情報取得<br/>
     * 代表商品SEQを元に、同じ代表商品を持つ公開中の商品情報のリストを取得する。<br/>
     *
     * @param goodsGroupCode 商品グループコード
     * @param goodsCode      商品コード
     * @return 商品グループ情報DTO
     */
    GoodsGroupDto execute(String goodsGroupCode, String goodsCode);

    // 2023-renew No92 from here
    /**
     * 商品グループ詳細情報取得<br/>
     * 商品コードに基づいて商品グループの詳細情報を取得する<br/>
     *
     * @param goodsCodes 商品コードリスト
     * @return  商品グループ情報DTOリスト
     */
    List<GoodsGroupDto> execute(List<String> goodsCodes);
    // 2023-renew No92 to here
}
