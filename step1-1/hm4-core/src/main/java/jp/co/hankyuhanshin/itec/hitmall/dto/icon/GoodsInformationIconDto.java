/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.icon;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * アイコンDTOクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.1 $
 */
@Data
@Component
@Scope("prototype")
public class GoodsInformationIconDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品インフォメーションアイコンエンティティ
     */
    private GoodsInformationIconEntity goodsInformationIconEntity;

}
