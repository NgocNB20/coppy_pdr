package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAllocationDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRequisitionType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderInfoMasterDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionResponseBundleDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionResponseDiscountInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionResponsePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetPromotionInformationLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderPromotionInformationLogicTest {

    @Autowired
    OrderPromotionInformationLogic orderPromotionInformationLogic;

    @MockBean
    /** WEB-API連携クラス プロモーション */ private WebApiGetPromotionInformationLogic webApiGetPromotionInformationLogic;

    @MockBean
    private DateUtility dateUtility;

    @MockBean
    /** 商品詳細情報MAP取得 */ private GoodsDetailsGetByCodeLogic goodsDetailsGetByCodeLogic;

    @Test
    @Order(1)

    public void execute() {

        // 想定値設定
        List<ReceiveOrderDto> receiveOrderDtoList = new ArrayList<>();
        ReceiveOrderDto receiveOrderDto = new ReceiveOrderDto();
        OrderInfoMasterDto orderInfoMasterDto = new OrderInfoMasterDto();
        Map<Integer, GoodsDetailsDto> goodsMaster = new HashMap<>();
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setGoodsSeq(10137467);
        goodsDetailsDto.setGoodsGroupSeq(0);
        goodsDetailsDto.setShopSeq(0);
        goodsDetailsDto.setGoodsCode("");
        goodsDetailsDto.setGoodsTaxType(HTypeGoodsTaxType.IN_TAX);
        goodsDetailsDto.setTaxRate(new BigDecimal("0.1"));
        goodsDetailsDto.setAlcoholFlag(HTypeAlcoholFlag.NOT_ALCOHOL);
        goodsDetailsDto.setGoodsPriceInTax(new BigDecimal("0"));
        goodsDetailsDto.setGoodsPrice(new BigDecimal("100"));
        goodsDetailsDto.setDeliveryType("");
        goodsDetailsDto.setSaleStatusPC(HTypeGoodsSaleStatus.NO_SALE);
        goodsDetailsDto.setSaleStartTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setSaleEndTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setUnitManagementFlag(HTypeUnitManagementFlag.ON);
        goodsDetailsDto.setStockManagementFlag(HTypeStockManagementFlag.ON);
        goodsDetailsDto.setIndividualDeliveryType(HTypeIndividualDeliveryType.ON);
        goodsDetailsDto.setPurchasedMax(new BigDecimal("0"));
        goodsDetailsDto.setOrderDisplay(0);
        goodsDetailsDto.setUnitValue1("");
        goodsDetailsDto.setUnitValue2("");
        goodsDetailsDto.setJanCode("");
        goodsDetailsDto.setSalesPossibleStock(new BigDecimal("0"));
        goodsDetailsDto.setRealStock(new BigDecimal("0"));
        goodsDetailsDto.setOrderReserveStock(new BigDecimal("0"));
        goodsDetailsDto.setRemainderFewStock(new BigDecimal("0"));
        goodsDetailsDto.setOrderPointStock(new BigDecimal("0"));
        goodsDetailsDto.setSafetyStock(new BigDecimal("0"));
        goodsDetailsDto.setGoodsGroupCode("");
        goodsDetailsDto.setWhatsnewDate(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setGoodsOpenStatusPC(HTypeOpenDeleteStatus.NO_OPEN);
        goodsDetailsDto.setOpenStartTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setOpenEndTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setGoodsGroupName("");
        goodsDetailsDto.setUnitTitle1("");
        goodsDetailsDto.setUnitTitle2("");
        goodsDetailsDto.setGoodsGroupImageEntityList(new ArrayList<GoodsGroupImageEntity>());
        goodsDetailsDto.setSnsLinkFlag(HTypeSnsLinkFlag.OFF);
        goodsDetailsDto.setMetaDescription("");
        goodsDetailsDto.setStockStatusPc(HTypeStockStatusType.NO_SALE);
        goodsDetailsDto.setGoodsIconList(new ArrayList<GoodsInformationIconDetailsDto>());
        goodsDetailsDto.setGoodsManagementCode("55-7039");
        goodsMaster.put(10137467, goodsDetailsDto);
        orderInfoMasterDto.setGoodsMaster(goodsMaster);
        receiveOrderDto.setMasterDto(orderInfoMasterDto);
        SettlementMethodEntity entity = new SettlementMethodEntity();
        entity.setSettlementMethodType(HTypeSettlementMethodType.CREDIT);
        entity.setSettlementMethodSeq(1);
        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setOrderSeq(1);
        orderSummaryEntity.setShopSeq(1);
        orderSummaryEntity.setMemberInfoSeq(1);
        List<OrderGoodsEntity> listOrderGoodsEntity = new ArrayList<>();
        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setOrderGoodsVersionNo(1);
        orderGoodsEntity.setGoodsCode("77-55-7039");
        orderGoodsEntity.setGoodsSeq(10137467);
        listOrderGoodsEntity.add(orderGoodsEntity);
        OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
        orderDeliveryEntity.setReceiverAddress1("沖縄県1");
        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
        orderDeliveryDto.setOrderGoodsEntityList(listOrderGoodsEntity);
        orderDeliveryDto.setOrderDeliveryEntity(orderDeliveryEntity);
        orderDeliveryDto.setRequisitionType(HTypeRequisitionType.INCLUDE);
        orderDeliveryDto.setCustomerNo(10);
        OrderTempDto orderTempDto = new OrderTempDto();
        orderTempDto.setExpire("A");
        orderTempDto.setSecurityCode("B");
        orderTempDto.setRegistCredit(true);
        orderTempDto.setUseRegistCardFlg(true);
        orderTempDto.setToken("AAAA");

        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        MulPayBillEntity mulPayBillEntity = new MulPayBillEntity();
        mulPayBillEntity.setForward("GG");
        OrderBillEntity orderBillEntity = new OrderBillEntity();

        OrderSettlementEntity orderSettlementEntity = new OrderSettlementEntity();
        orderSettlementEntity.setSettlementMethodType(HTypeSettlementMethodType.RECEIPT_PAYMENT);
        orderSettlementEntity.setBillType(HTypeBillType.POST_CLAIM);
        receiveOrderDto.setSettlementMethodEntity(entity);
        receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        receiveOrderDto.setOrderDeliveryDto(orderDeliveryDto);
        receiveOrderDto.setOrderSettlementEntity(orderSettlementEntity);
        receiveOrderDto.setPeriodicOrderFlag(true);
        receiveOrderDto.setNextSettlementMethodEntity(entity);
        receiveOrderDto.setOrderTempDto(orderTempDto);
        receiveOrderDto.setOrderPersonEntity(orderPersonEntity);
        receiveOrderDto.setMulPayBillEntity(mulPayBillEntity);
        receiveOrderDto.setOrderBillEntity(orderBillEntity);
        receiveOrderDto.setAllocationDeliveryType(HTypeAllocationDeliveryType.DELIVER_NOW);

        receiveOrderDtoList.add(receiveOrderDto);

        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();
        WebApiGetPromotionInformationResponseDto resDto = new WebApiGetPromotionInformationResponseDto();
        List<WebApiGetPromotionResponseDetailDto> info = new ArrayList<>();
        WebApiGetPromotionResponseDetailDto webApiGetPromotionResponseDetailDto =
                        new WebApiGetPromotionResponseDetailDto();
        List<WebApiGetPromotionResponsePriceDto> priceInfo = new ArrayList<>();
        WebApiGetPromotionResponsePriceDto webApiGetPromotionResponsePriceDto =
                        new WebApiGetPromotionResponsePriceDto();
        webApiGetPromotionResponsePriceDto.setPromotionCode("aa");
        webApiGetPromotionResponsePriceDto.setDiscountPrice(BigDecimal.valueOf(10));
        webApiGetPromotionResponsePriceDto.setTaxPrice(BigDecimal.valueOf(10));
        webApiGetPromotionResponsePriceDto.setShippingPrice(BigDecimal.valueOf(10));
        webApiGetPromotionResponsePriceDto.setShipmentDate(dateUtility.getCurrentDate());
        webApiGetPromotionResponsePriceDto.setStockDate(dateUtility.getCurrentDate());
        priceInfo.add(webApiGetPromotionResponsePriceDto);
        webApiGetPromotionResponseDetailDto.setPriceInfo(priceInfo);
        List<WebApiGetPromotionResponseBundleDto> bundleInfo = new ArrayList<>();
        WebApiGetPromotionResponseBundleDto webApiGetPromotionResponseBundleDto =
                        new WebApiGetPromotionResponseBundleDto();
        webApiGetPromotionResponseBundleDto.setBundleGoodsCode("aa");
        webApiGetPromotionResponseBundleDto.setShipmentDate(dateUtility.getCurrentDate());
        webApiGetPromotionResponseBundleDto.setStockDate(dateUtility.getCurrentDate());
        webApiGetPromotionResponseBundleDto.setBundleGoodsCount("10");
        bundleInfo.add(webApiGetPromotionResponseBundleDto);
        webApiGetPromotionResponseDetailDto.setBundleInfo(bundleInfo);
        List<WebApiGetPromotionResponseDiscountInfoDto> discountInfo = new ArrayList<>();
        WebApiGetPromotionResponseDiscountInfoDto webApiGetPromotionResponseDiscountInfoDto =
                        new WebApiGetPromotionResponseDiscountInfoDto();
        webApiGetPromotionResponseDiscountInfoDto.setDiscountRate("10");
        webApiGetPromotionResponseDiscountInfoDto.setOrderSerial("11");
        webApiGetPromotionResponseDiscountInfoDto.setUnitDiscountPrice(BigDecimal.valueOf(10));
        discountInfo.add(webApiGetPromotionResponseDiscountInfoDto);
        webApiGetPromotionResponseDetailDto.setDiscountInfo(discountInfo);
        webApiGetPromotionResponseDetailDto.setPromApplyFlag(0);
        webApiGetPromotionResponseDetailDto.setCouponApplyFlag(0);
        info.add(webApiGetPromotionResponseDetailDto);
        AbstractWebApiResponseResultDto abstractWebApiResponseResultDto = new AbstractWebApiResponseResultDto();
        abstractWebApiResponseResultDto.setMessage("ok");
        abstractWebApiResponseResultDto.setStatus("0");
        resDto.setResult(abstractWebApiResponseResultDto);
        resDto.setInfo(info);

        // モック設定
        doReturn(resDto).when(webApiGetPromotionInformationLogic).execute(any(AbstractWebApiRequestDto.class));
        doReturn(goodsDetailsDto).when(goodsDetailsGetByCodeLogic).execute(any(), any(), any(), any());
        //        doReturn("BundledErrorCode").when(propertiesUtil).getSystemPropertiesValue("dummy.goods.code");

        // 試験実行
        orderPromotionInformationLogic.execute(receiveOrderDtoList, checkMessageDtoList);

        // 戻り値及び呼び出し検証
        verify(webApiGetPromotionInformationLogic, times(1)).execute(any(AbstractWebApiRequestDto.class));
        verify(goodsDetailsGetByCodeLogic, times(1)).execute(any(), any(), any(), any());
        //        verify(propertiesUtil, times(1)).getSystemPropertiesValue("dummy.goods.code");
    }
}
