/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 商品Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.3 $
 */
@Data
@Component
@Scope("prototype")
public class GoodsDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品エンティティ
     */
    private GoodsEntity goodsEntity;

    /**
     * 在庫DTO
     */
    private StockDto stockDto;

    /**
     * 削除フラグ
     */
    private String deleteFlg;

    /**
     * 在庫状態PC
     */
    private HTypeStockStatusType stockStatusPc;

    /**
     * 商品規格画像登録リスト<br/>
     */
    private GoodsImageEntity unitImage;

}
