/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.helper.order;

import jp.co.hankyuhanshin.itec.hitmall.api.order.OrderApi;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * PDR#013 09_データ連携（受注データ）<br/>
 * <p>
 * 注文フロー共通データ変換ヘルパークラス
 *
 * @author kimura
 * @author satoh
 */
@Component
public class OrderConversionHelper {

    // PDR Migrate Customization from here
    /**
     * 桁埋め用:" "(スペース)
     */
    public static final String SPACE = "　";
    // PDR Migrate Customization to here

    /**
     * 注文API
     */
    private final OrderApi orderApi;

    /**
     * コンストラクタ
     *
     * @param orderApi 注文API
     */
    @Autowired
    public OrderConversionHelper(OrderApi orderApi) {
        this.orderApi = orderApi;
    }

    /**
     * カートDtoから注文Dtoへの変換処理
     *
     * @param cartDto         カートDto
     * @param receiveOrderDto 受注Dto
     */
    public void toReceiveOrderDto(CartDto cartDto, ReceiveOrderDto receiveOrderDto) {

        // 受注サマリエンティティの作成
        prepareOrderSummary(cartDto, receiveOrderDto);

        // 受注配送Dto、注文商品エンティティリストを作成
        BigDecimal goodsCostTotal = prepareOrderGoods(cartDto, receiveOrderDto);

        // 受注決済エンティティの作成
        prepareOrderSettlement(cartDto, receiveOrderDto, goodsCostTotal);

        // 受注一時情報の作成
        prepareOrderTmpDto(cartDto, receiveOrderDto);

    }

    /**
     * 受注一時情報の作成
     *
     * @param cartDto         カートDTO
     * @param receiveOrderDto 受注DTO
     * @param customParams    案件用引数
     */
    protected void prepareOrderTmpDto(CartDto cartDto, ReceiveOrderDto receiveOrderDto, Object... customParams) {
        OrderTempDto orderTempDto = receiveOrderDto.getOrderTempDto();
        if (orderTempDto == null) {
            // 受注TEMPDtoが null だったら作成する。
            orderTempDto = ApplicationContextUtility.getBean(OrderTempDto.class);
        }
        orderTempDto.setCartDto(cartDto);
        receiveOrderDto.setOrderTempDto(orderTempDto);
    }

    /**
     * 受注決済エンティティの作成
     *
     * @param cartDto         カートDTO
     * @param receiveOrderDto 受注DTO
     * @param goodsCostTotal  商品金額合計
     * @param customParams    案件用引数
     */
    protected void prepareOrderSettlement(CartDto cartDto,
                                          ReceiveOrderDto receiveOrderDto,
                                          BigDecimal goodsCostTotal,
                                          Object... customParams) {
        // 受注決済エンティティを取得
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        if (orderSettlementEntity == null) {
            // 受注決済エンティティが null だったら作成する。
            orderSettlementEntity = ApplicationContextUtility.getBean(OrderSettlementEntity.class);
        }
        // 商品金額合計（税抜）
        orderSettlementEntity.setGoodsPriceTotal(cartDto.getGoodsTotalPrice());
        // 商品原価合計
        orderSettlementEntity.setGoodsCostTotal(goodsCostTotal);
        // 決済方法種別
        orderSettlementEntity.setSettlementMethodType(cartDto.getSettlementMethodType());
        // 注文Dtoに設定
        receiveOrderDto.setOrderSettlementEntity(orderSettlementEntity);
    }

    /**
     * 注文商品エンティティリストを作成
     *
     * <pre>
     * 適用割引をエンティティに追加
     * </pre>
     *
     * @param cartDto         カートDTO
     * @param receiveOrderDto 受注DTO
     * @param customParams    案件用引数
     * @return 商品原価合計
     */
    protected BigDecimal prepareOrderGoods(CartDto cartDto, ReceiveOrderDto receiveOrderDto, Object... customParams) {
        List<OrderGoodsEntity> orderGoodsEntityList = new ArrayList<>();
        BigDecimal goodsCostTotal = BigDecimal.ZERO;

        // 定期受注はHM4廃止済のため、false固定
        receiveOrderDto.setPeriodicOrderFlag(false);

        // カート投入商品数分ループ
        for (CartGoodsDto cartGoodsDto : cartDto.getCartGoodsDtoList()) {
            // カートDtoからカート商品エンティティを取得
            CartGoodsEntity cartGoodsEntity = cartGoodsDto.getCartGoodsEntity();
            // カート商品Dtoから商品詳細Dtoを取得
            GoodsDetailsDto goodsDetailsDto = cartGoodsDto.getGoodsDetailsDto();

            // 注文商品エンティティ作成
            OrderGoodsEntity orderGoodsEntity =
                            toOrderGoodsEntity(goodsDetailsDto, cartGoodsEntity.getCartGoodsCount());
            // 2023-renew No14 from here
            // 適用割引（数量割引適用結果から設定）
            WebApiGetQuantityDiscountResultResponseDetailDto quantityDiscountsResponseDetailDto =
                            cartDto.getQuantityDiscountsResponseDetailMap().get(goodsDetailsDto.getGoodsCode());
            orderGoodsEntity.setDiscountsType(EnumTypeUtil.getEnumFromValue(HTypeDiscountsType.class,
                                                                            quantityDiscountsResponseDetailDto.getSaleType()
                                                                           ));
            // 取りおきフラグ
            orderGoodsEntity.setReserveFlag(cartGoodsEntity.getReserveFlag());
            // 取りおきお届け希望日
            orderGoodsEntity.setReserveDeliveryDate(cartGoodsEntity.getReserveDeliveryDate());
            // 2023-renew No14 to here

            // 注文商品Dtoリストに追加
            orderGoodsEntityList.add(orderGoodsEntity);
        }

        // 受注配送Dtoに注文商品エンティティを設定
        OrderDeliveryDto orderDeliveryDto = ApplicationContextUtility.getBean(OrderDeliveryDto.class);
        orderDeliveryDto.setOrderGoodsEntityList(orderGoodsEntityList);

        // 注文Dtoに注文商品エンティティリストを設定
        receiveOrderDto.setOrderDeliveryDto(orderDeliveryDto);

        // 商品原価はHM4廃止済のため、0のまま返す
        return goodsCostTotal;
    }

    /**
     * 受注サマリエンティティの作成
     *
     * <pre>
     * 受注番号SEQ取得、設定処理を追加
     * </pre>
     *
     * @param cartDto         カートDTO
     * @param receiveOrderDto 受注DTO
     * @param customParams    案件用引数
     */
    protected void prepareOrderSummary(CartDto cartDto, ReceiveOrderDto receiveOrderDto, Object... customParams) {

        // 受注サマリエンティティ取得
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        if (orderSummaryEntity == null) {
            // 受注サマリエンティティが null だったら作成する。
            orderSummaryEntity = ApplicationContextUtility.getBean(OrderSummaryEntity.class);

            // PDR Migrate Customization from here
            // 受注番号SEQを採番
            orderSummaryEntity.setOrderCode(String.valueOf(orderApi.getNewOrderNoSeq().getOrderCode()));
            // PDR Migrate Customization to here
        }

        // 注文Dtoに設定
        receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        receiveOrderDto.getOrderSummaryEntity()
                       .setTaxSeq(receiveOrderDto.getMasterDto()
                                                 .getTaxRateMaster()
                                                 .get(HTypeTaxRateType.STANDARD)
                                                 .getTaxSeq());
    }

    /**
     * 会員情報エンティティから注文DTOへの変換処理
     *
     * <pre>
     * 方書1、方書2の処理を追加
     * 今回使用しない項目の処理を削除
     * 顧客番号処理を追加
     * </pre>
     *
     * @param memberInfoEntity 会員情報エンティティ
     * @param receiveOrderDto  受注DTO
     */
    // 受注ご注文主エンティティに設定
    public void toReceiveOrderDto(MemberInfoEntity memberInfoEntity, ReceiveOrderDto receiveOrderDto) {
        OrderPersonEntity orderPersonEntity = receiveOrderDto.getOrderPersonEntity();
        if (orderPersonEntity == null) {
            // 受注ご注文主エンティティ が null だったら作成する。
            orderPersonEntity = ApplicationContextUtility.getBean(OrderPersonEntity.class);
        }

        orderPersonEntity.setOrderLastName(memberInfoEntity.getMemberInfoLastName());
        // PDR Migrate Customization from here
        // 代表者名
        orderPersonEntity.setOrderFirstName(StringUtils.defaultString(memberInfoEntity.getRepresentativeName()));
        // PDR Migrate Customization to here

        orderPersonEntity.setOrderLastKana(memberInfoEntity.getMemberInfoLastKana());
        // PDR Migrate Customization from here
        // 氏名フリガナ 未使用
        orderPersonEntity.setOrderFirstKana(StringUtils.EMPTY);
        // PDR Migrate Customization to here

        orderPersonEntity.setOrderMail(memberInfoEntity.getMemberInfoMail());
        orderPersonEntity.setOrderTel(memberInfoEntity.getMemberInfoTel());
        orderPersonEntity.setOrderContactTel(memberInfoEntity.getMemberInfoContactTel());
        orderPersonEntity.setOrderZipCode(memberInfoEntity.getMemberInfoZipCode());

        // PDR Migrate Customization from here
        // ご注文主住所-都道府県 ： 削除
        // 都道府県種別 ： 固定値で東京を設定
        orderPersonEntity.setPrefectureType(HTypePrefectureType.TOKYO);
        // PDR Migrate Customization to here

        orderPersonEntity.setOrderAddress1(memberInfoEntity.getMemberInfoAddress1());
        orderPersonEntity.setOrderAddress2(memberInfoEntity.getMemberInfoAddress2());
        // PDR Migrate Customization from here
        // 方書が登録されてる場合、それ以降の住所に結合
        orderPersonEntity.setOrderAddress3(StringUtils.defaultString(memberInfoEntity.getMemberInfoAddress3()));
        if (memberInfoEntity.getMemberInfoAddress4() != null) {
            if (memberInfoEntity.getMemberInfoAddress3() != null) {
                orderPersonEntity.setOrderAddress3(
                                orderPersonEntity.getOrderAddress3() + SPACE + StringUtils.defaultString(
                                                memberInfoEntity.getMemberInfoAddress4()));
            } else {
                orderPersonEntity.setOrderAddress3(
                                StringUtils.defaultString((memberInfoEntity.getMemberInfoAddress4())));
            }
        }

        // 顧客番号
        orderPersonEntity.setCustomerNo(memberInfoEntity.getCustomerNo());

        // ご注文主生年月日 削除
        // ご注文主年代 ： 固定値で「0(未解答・その他)」を設定
        orderPersonEntity.setOrderAgeType(HTypeOrderAgeType.UNKNOWN);
        // ご注文主性別 削除
        // PDR Migrate Customization to here

        receiveOrderDto.setOrderPersonEntity(orderPersonEntity);
    }

    /**
     * 会員情報を受注配送情報に設定
     * <pre>
     * 方書1、方書2の処理を追加
     * </pre>
     *
     * @param memberInfo    会員情報
     * @param orderDelivery 受注配送情報
     */
    public void toOrderDeliveryEntity(MemberInfoEntity memberInfo, OrderDeliveryEntity orderDelivery) {
        // PDR Migrate Customization from here
        // 配送方法SEQ ダミーデータ設定
        orderDelivery.setDeliveryMethodSeq(Integer.parseInt(OrderUtility.DUMMY_DELIVERY_METHOD_SEQ));
        // PDR Migrate Customization to here
        orderDelivery.setReceiverLastName(memberInfo.getMemberInfoLastName());
        // PDR Migrate Customization from here
        orderDelivery.setReceiverFirstName(StringUtils.defaultString(memberInfo.getRepresentativeName()));
        orderDelivery.setReceiverFirstKana(StringUtils.EMPTY);
        // PDR Migrate Customization to here
        orderDelivery.setReceiverLastKana(memberInfo.getMemberInfoLastKana());
        orderDelivery.setReceiverFirstKana(memberInfo.getMemberInfoFirstKana());
        orderDelivery.setReceiverTel(memberInfo.getMemberInfoTel());
        orderDelivery.setReceiverZipCode(memberInfo.getMemberInfoZipCode());
        orderDelivery.setReceiverPrefecture(memberInfo.getMemberInfoPrefecture());
        orderDelivery.setReceiverAddress1(memberInfo.getMemberInfoAddress1());
        orderDelivery.setReceiverAddress2(memberInfo.getMemberInfoAddress2());
        // PDR Migrate Customization from here
        // 方書1が登録されてる場合、それ以降の住所に結合
        orderDelivery.setReceiverAddress3(StringUtils.defaultString(memberInfo.getMemberInfoAddress3()));
        if (memberInfo.getMemberInfoAddress4() != null) {
            if (memberInfo.getMemberInfoAddress3() != null) {
                orderDelivery.setReceiverAddress3(
                                orderDelivery.getReceiverAddress3() + SPACE + StringUtils.defaultString(
                                                memberInfo.getMemberInfoAddress4()));
            } else {
                orderDelivery.setReceiverAddress3(StringUtils.defaultString(memberInfo.getMemberInfoAddress4()));
            }
        }
        // PDR Migrate Customization to here
    }

    /**
     * Entityの変換処理
     * <pre>
     * 商品詳細Dto + 注文数量 ⇒ 受注商品Entity
     * </pre>
     *
     * @param goodsDetailsDto 商品詳細Dto
     * @param goodsCount      注文数量
     * @return 受注商品Entity
     */
    public OrderGoodsEntity toOrderGoodsEntity(GoodsDetailsDto goodsDetailsDto, BigDecimal goodsCount) {
        // 受注商品Entityの生成
        OrderGoodsEntity orderGoodsEntity = ApplicationContextUtility.getBean(OrderGoodsEntity.class);

        // 商品SEQ
        orderGoodsEntity.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
        // 商品コード
        orderGoodsEntity.setGoodsCode(goodsDetailsDto.getGoodsCode());
        // 商品グループコード
        orderGoodsEntity.setGoodsGroupCode(goodsDetailsDto.getGoodsGroupCode());
        // 商品グループ表示名
        orderGoodsEntity.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
        // 商品単価（税抜）
        orderGoodsEntity.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
        // 税率
        orderGoodsEntity.setTaxRate(goodsDetailsDto.getTaxRate());
        // 商品消費税種別
        orderGoodsEntity.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType());
        // 注文数量
        orderGoodsEntity.setGoodsCount(goodsCount);
        // 無料配送フラグ
        orderGoodsEntity.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag());
        // 商品個別配送種別
        orderGoodsEntity.setIndividualDeliveryType(goodsDetailsDto.getIndividualDeliveryType());
        // 規格値１
        orderGoodsEntity.setUnitValue1(goodsDetailsDto.getUnitValue1());
        // 規格値２
        orderGoodsEntity.setUnitValue2(goodsDetailsDto.getUnitValue2());
        // JANコード
        orderGoodsEntity.setJanCode(goodsDetailsDto.getJanCode());
        // カタログコード
        orderGoodsEntity.setCatalogCode(goodsDetailsDto.getCatalogCode());
        // 納期
        orderGoodsEntity.setDeliveryType(goodsDetailsDto.getDeliveryType());
        // 受注連携設定01
        orderGoodsEntity.setOrderSetting1(goodsDetailsDto.getOrderSetting1());
        // 受注連携設定02
        orderGoodsEntity.setOrderSetting2(goodsDetailsDto.getOrderSetting2());
        // 受注連携設定03
        orderGoodsEntity.setOrderSetting3(goodsDetailsDto.getOrderSetting3());
        // 受注連携設定04
        orderGoodsEntity.setOrderSetting4(goodsDetailsDto.getOrderSetting4());
        // 受注連携設定05
        orderGoodsEntity.setOrderSetting5(goodsDetailsDto.getOrderSetting5());
        // 受注連携設定06
        orderGoodsEntity.setOrderSetting6(goodsDetailsDto.getOrderSetting6());
        // 受注連携設定07
        orderGoodsEntity.setOrderSetting7(goodsDetailsDto.getOrderSetting7());
        // 受注連携設定08
        orderGoodsEntity.setOrderSetting8(goodsDetailsDto.getOrderSetting8());
        // 受注連携設定09
        orderGoodsEntity.setOrderSetting9(goodsDetailsDto.getOrderSetting9());
        // 受注連携設定10
        orderGoodsEntity.setOrderSetting10(goodsDetailsDto.getOrderSetting10());

        return orderGoodsEntity;
    }

    // PDR Migrate Customization from here

    /**
     * 割引適用結果からorderGoodsEntityを再作成
     * ※今すぐお届け分のみ
     * <pre>
     * 割引適用情報 + 商品詳細Dto ⇒ 受注商品Entity
     * </pre>
     *
     * @param goodsDetailsDto 商品詳細Dto
     * @param responseDto     WEB-API連携取得結果DTO
     * @return 受注商品Entity
     */
    public OrderGoodsEntity toOrderGoodsEntityByDiscountsResult(GoodsDetailsDto goodsDetailsDto,
                                                                WebApiGetDiscountsResponseDetailDto responseDto) {

        // 注文商品エンティティ作成（注文数量はWEB-API連携取得結果から設定する）
        OrderGoodsEntity orderGoodsEntity =
                        toOrderGoodsEntity(goodsDetailsDto, new BigDecimal(responseDto.getQuantity()));

        // 下記項目は、割引適用結果（WEB-API連携取得結果DTO）で書き換える
        // 商品単価（税抜）
        orderGoodsEntity.setGoodsPrice(responseDto.getSalePrice());
        // 適用割引
        orderGoodsEntity.setDiscountsType(
                        EnumTypeUtil.getEnumFromValue(HTypeDiscountsType.class, responseDto.getSaleType()));
        // 2023-renew No14 from here
        // 取りおきフラグ
        orderGoodsEntity.setReserveFlag(HTypeReserveDeliveryFlag.OFF);
        // 取りおきお届け希望日
        orderGoodsEntity.setReserveDeliveryDate(null);
        // 2023-renew No14 to here

        return orderGoodsEntity;
    }

    // PDR Migrate Customization to here
}
