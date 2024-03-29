package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.addressbook.AddressBookDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderInfoMasterDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxRateEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsMapGetLogic;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderCheckLogicTest {

    @Autowired
    OrderCheckLogic orderCheckLogic;

    @MockBean
    private AddressBookDao dao;

    @MockBean
    private GoodsDetailsMapGetLogic goodsDetailsMapGetLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        Integer goodsSeq = 1;
        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
        List<OrderGoodsEntity> listOrderGoodsEntity = new ArrayList<>();
        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setGoodsSeq(goodsSeq);
        listOrderGoodsEntity.add(orderGoodsEntity);
        orderDeliveryDto.setTmpOrderGoodsEntityList(listOrderGoodsEntity);
        OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
        orderDeliveryEntity.setOrderConsecutiveNo(1);
        orderDeliveryDto.setOrderDeliveryEntity(orderDeliveryEntity);
        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        ReceiveOrderDto editOrderDto = new ReceiveOrderDto();
        editOrderDto.setOrderDeliveryDto(orderDeliveryDto);
        editOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        OrderInfoMasterDto orderInfoMasterDto = new OrderInfoMasterDto();
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setPurchasedMax(BigDecimal.TEN);
        Map<Integer, GoodsDetailsDto> goodsMaster = new HashMap<>();
        goodsMaster.put(goodsSeq, goodsDetailsDto);
        orderInfoMasterDto.setGoodsMaster(goodsMaster);
        editOrderDto.setMasterDto(orderInfoMasterDto);
        OrderMessageDto result = new OrderMessageDto();
        result.setOrderGoodsMessageMapMap(new HashMap<Integer, Map<Integer, List<CheckMessageDto>>>());
        result.setOrderMessageList(new ArrayList<CheckMessageDto>());
        Map<Integer, List<CheckMessageDto>> msgMap = new HashMap<>();
        result.getOrderGoodsMessageMapMap().put(1, msgMap);

        // 試験実行
        OrderMessageDto actual = orderCheckLogic.executeForGoodsSetting(editOrderDto);

        // 戻り値及び呼び出し検証
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(2)
    public void execute02() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        // 想定値設定
        Integer goodsSeq = 1;
        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
        List<OrderGoodsEntity> listOrderGoodsEntity = new ArrayList<>();
        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setGoodsSeq(goodsSeq);
        orderGoodsEntity.setTaxRate(BigDecimal.ZERO);
        listOrderGoodsEntity.add(orderGoodsEntity);
        orderDeliveryDto.setTmpOrderGoodsEntityList(listOrderGoodsEntity);
        orderDeliveryDto.setOrderGoodsEntityList(listOrderGoodsEntity);
        OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
        orderDeliveryEntity.setOrderConsecutiveNo(1);
        orderDeliveryDto.setOrderDeliveryEntity(orderDeliveryEntity);
        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setOrderSeq(1);

        ReceiveOrderDto editOrderDto = new ReceiveOrderDto();
        editOrderDto.setOrderDeliveryDto(orderDeliveryDto);
        editOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        orderPersonEntity.setPrefectureType(HTypePrefectureType.HOKKAIDO);
        orderPersonEntity.setOrderSex(HTypeOrderSex.MALE);
        orderPersonEntity.setOrderBirthday(ts);
        editOrderDto.setOrderPersonEntity(orderPersonEntity);
        OrderInfoMasterDto orderInfoMasterDto = new OrderInfoMasterDto();
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setPurchasedMax(BigDecimal.TEN);
        goodsDetailsDto.setGoodsOpenStatusPC(HTypeOpenDeleteStatus.OPEN);
        goodsDetailsDto.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
        Map<Integer, GoodsDetailsDto> goodsMaster = new HashMap<>();
        Map<HTypeTaxRateType, TaxRateEntity> taxRateMaster = new HashMap<>();
        goodsMaster.put(goodsSeq, goodsDetailsDto);
        TaxRateEntity taxRateEntity = new TaxRateEntity();
        taxRateEntity.setTaxSeq(123);
        taxRateEntity.setRate(new BigDecimal(10));
        taxRateMaster.put(HTypeTaxRateType.STANDARD, taxRateEntity);
        taxRateMaster.put(HTypeTaxRateType.REDUCED, taxRateEntity);
        orderInfoMasterDto.setGoodsMaster(goodsMaster);
        orderInfoMasterDto.setTaxRateMaster(taxRateMaster);
        editOrderDto.setMasterDto(orderInfoMasterDto);
        OrderMessageDto result = new OrderMessageDto();
        result.setOrderGoodsMessageMapMap(new HashMap<Integer, Map<Integer, List<CheckMessageDto>>>());
        Map<Integer, List<CheckMessageDto>> msgMap = new HashMap<>();
        result.getOrderGoodsMessageMapMap().put(1, msgMap);
        result.setOrderMessageList(new ArrayList<CheckMessageDto>());

        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = new HashMap<>();
        goodsDetailsDtoMap.put(1, goodsDetailsDto);

        doReturn(goodsDetailsDtoMap).when(goodsDetailsMapGetLogic).execute(any(List.class));

        // 試験実行
        OrderMessageDto actual = orderCheckLogic.execute(editOrderDto);
        // 戻り値及び呼び出し検証
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(3)
    public void execute03() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        // 想定値設定
        Integer goodsSeq = 1;
        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
        List<OrderGoodsEntity> listOrderGoodsEntity = new ArrayList<>();
        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setGoodsSeq(goodsSeq);
        orderGoodsEntity.setTaxRate(BigDecimal.ZERO);
        listOrderGoodsEntity.add(orderGoodsEntity);
        orderDeliveryDto.setTmpOrderGoodsEntityList(listOrderGoodsEntity);
        orderDeliveryDto.setOrderGoodsEntityList(listOrderGoodsEntity);
        OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
        orderDeliveryEntity.setOrderConsecutiveNo(1);
        orderDeliveryDto.setOrderDeliveryEntity(orderDeliveryEntity);
        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setOrderSeq(1);

        ReceiveOrderDto editOrderDto = new ReceiveOrderDto();
        editOrderDto.setOrderDeliveryDto(orderDeliveryDto);
        editOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        orderPersonEntity.setPrefectureType(HTypePrefectureType.HOKKAIDO);
        orderPersonEntity.setOrderSex(HTypeOrderSex.MALE);
        orderPersonEntity.setOrderBirthday(ts);
        editOrderDto.setOrderPersonEntity(orderPersonEntity);
        OrderInfoMasterDto orderInfoMasterDto = new OrderInfoMasterDto();
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setPurchasedMax(BigDecimal.TEN);
        goodsDetailsDto.setGoodsOpenStatusPC(HTypeOpenDeleteStatus.OPEN);
        goodsDetailsDto.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
        Map<Integer, GoodsDetailsDto> goodsMaster = new HashMap<>();
        Map<HTypeTaxRateType, TaxRateEntity> taxRateMaster = new HashMap<>();
        goodsMaster.put(goodsSeq, goodsDetailsDto);
        TaxRateEntity taxRateEntity = new TaxRateEntity();
        taxRateEntity.setTaxSeq(123);
        taxRateEntity.setRate(new BigDecimal(10));
        taxRateMaster.put(HTypeTaxRateType.STANDARD, taxRateEntity);
        taxRateMaster.put(HTypeTaxRateType.REDUCED, taxRateEntity);
        orderInfoMasterDto.setGoodsMaster(goodsMaster);
        orderInfoMasterDto.setTaxRateMaster(taxRateMaster);
        editOrderDto.setMasterDto(orderInfoMasterDto);
        OrderMessageDto result = new OrderMessageDto();
        result.setOrderGoodsMessageMapMap(new HashMap<Integer, Map<Integer, List<CheckMessageDto>>>());
        Map<Integer, List<CheckMessageDto>> msgMap = new HashMap<>();
        result.getOrderGoodsMessageMapMap().put(1, msgMap);
        result.setOrderMessageList(new ArrayList<CheckMessageDto>());

        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = new HashMap<>();
        goodsDetailsDtoMap.put(1, goodsDetailsDto);

        doReturn(goodsDetailsDtoMap).when(goodsDetailsMapGetLogic).execute(any(List.class));

        // 試験実行
        OrderMessageDto actual = orderCheckLogic.execute(editOrderDto, true);
        // 戻り値及び呼び出し検証
        assertThat(actual).isEqualTo(result);
    }
}
