/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.cart;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckResponseDetailDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * カート投入チェックDtoクラス
 * ※PDRカスタマイズで「CartGoodsRegistCheckLogic」に対して追加された引数をまとめたクラス
 *
 * @author ota-s5
 */
@Data
@Component
@Scope("prototype")
// 2023-renew No14 from here
public class CartGoodsRegistCheckDto implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * 取りおきお届け希望日（カート投入対象）
     */
    private Date reserveDeliveryDate;

    /**
     * 取りおきフラグ（カート投入対象）
     */
    private HTypeReserveDeliveryFlag reserveFlag;

    /**
     * 取りおき情報MAP
     */
    private Map<String, WebApiGetReserveResponseDetailDto> reserveMap;

    /**
     * 販売可否判定Map
     */
    private Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckMap;

}
// 2023-renew No14 to here
