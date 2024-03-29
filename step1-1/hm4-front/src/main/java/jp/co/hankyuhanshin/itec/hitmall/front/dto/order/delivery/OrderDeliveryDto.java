/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFrontBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeRequisitionType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.delivery.DeliveryMethodEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 受注配送Dtoクラス
 *
 * @author DtoGenerator
 */
@Data
@Component
@Scope("prototype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDeliveryDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // PDR Migrate Customization from here
    /**
     * 追加種別
     */
    private String addType;
    // PDR Migrate Customization to here

    /**
     * カートポップアップで各お届け先に対して選んだ商品のリスト ※画面用の一時情報
     */
    private List<OrderGoodsEntity> tmpOrderGoodsEntityList;

    /**
     * 受注商品Dtoリスト
     */
    private List<OrderGoodsEntity> orderGoodsEntityList;

    /**
     * 受注配送エンティティ
     */
    private OrderDeliveryEntity orderDeliveryEntity;

    /**
     * 配送方法エンティティ
     */
    private DeliveryMethodEntity deliveryMethodEntity;

    /**
     * 「このお届け先を住所に登録する」フラグ
     */
    private boolean receiverRegistFlg;

    /**
     * 配送DTOリスト
     */
    private List<DeliveryDto> deliveryDtoList;

    /**
     * 前回送料<br/>
     */
    private BigDecimal originalCarriage;

    /**
     * 初回に選択した商品エンティティリスト
     */
    private List<OrderGoodsEntity> firstSelectedOrderGoodsEntityList;

    // PDR Migrate Customization from here
    /**
     * 配送情報取得 詳細情報
     */
    private WebApiGetDeliveryInformationResponseDetailDto deliveryInformationDetailDto;

    /**
     * 今すぐお届け分List
     */
    private List<OrderDeliveryNowDto> orderDeliveryNowDtoList;

    /**
     * セールde予約分List
     */
    private List<OrderReserveDeliveryDto> orderReserveDeliveryDtoList;

    /**
     * 入荷次第お届け分List
     */
    private List<OrderDependingOnReceiptGoodsDto> orderDependingOnReceiptGoodsDtoList;

    /**
     * 顧客区分
     */
    private HTypeFrontBusinessType businessType;

    /**
     * 確認書類
     */
    private HTypeConfDocumentType confDocumentType;

    /**
     * 顧客番号
     */
    private Integer customerNo;

    /**
     * 住所録SEQ
     */
    private Integer addressBookSeq;

    /**
     * 取りおき お届け時期
     */
    private String deliveryDate;

    /**
     * 値引き
     */
    private BigDecimal discountPrice = BigDecimal.ZERO;

    /**
     * 消費税
     */
    private BigDecimal taxPrice = BigDecimal.ZERO;

    /**
     * 消費税合計
     */
    private BigDecimal totalTax;

    /**
     * 請求書種別
     */
    private HTypeRequisitionType requisitionType;

    /**
     * お届け先都道府県コード
     */
    private String cityCd;
    // PDR Migrate Customization to here

    // 2023-renew No14 from here
    /**
     * 出荷予定日MAP（セールde予約分のみ）
     */
    private Map<String, Timestamp> shipmentDateMap;
    // 2023-renew No14 to here

}
