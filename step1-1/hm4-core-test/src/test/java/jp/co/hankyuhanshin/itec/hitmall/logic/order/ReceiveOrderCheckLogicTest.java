package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.settlement.OrderSettlementDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReceiveOrderCheckLogicTest {

    @Autowired
    ReceiveOrderCheckLogic logic;

    @MockBean
    private OrderSettlementDao dao;

    /**
     * 請求金額算出処理
     */
    @MockBean
    private BillPriceCalculateLogic billPriceCalculateLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        OrderBillEntity orderBillEntity = new OrderBillEntity();
        orderBillEntity.setPaymentTimeLimitDate(Timestamp.valueOf("2021-03-21 12:12:12"));
        orderBillEntity.setCancelableDate(Timestamp.valueOf("2021-03-21 12:12:12"));
        OrderSettlementEntity orderSettlementEntity = new OrderSettlementEntity();
        orderSettlementEntity.setSettlementMethodSeq(null);

        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        orderPersonEntity.setMemberInfoSeq(1);
        orderPersonEntity.setPrefectureType(HTypePrefectureType.AICHI);
        orderPersonEntity.setOrderSex(HTypeOrderSex.FEMALE);
        orderPersonEntity.setOrderAgeType(HTypeOrderAgeType.AGE_10TO19);
        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setGoodsSeq(1);
        List<OrderGoodsEntity> listOrderGoodsEntity = new ArrayList<>();
        listOrderGoodsEntity.add(orderGoodsEntity);
        OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
        orderDeliveryEntity.setDeliveryMethodSeq(1);
        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
        orderDeliveryDto.setOrderDeliveryEntity(orderDeliveryEntity);
        orderDeliveryDto.setOrderGoodsEntityList(listOrderGoodsEntity);

        ReceiveOrderDto dto = new ReceiveOrderDto();
        dto.setOrderSettlementEntity(orderSettlementEntity);
        dto.setOrderBillEntity(orderBillEntity);
        dto.setOrderPersonEntity(orderPersonEntity);
        dto.setOrderDeliveryDto(orderDeliveryDto);

        ReceiveOrderDto baseDto = new ReceiveOrderDto();
        baseDto.setOrderSettlementEntity(orderSettlementEntity);
        baseDto.setOrderBillEntity(orderBillEntity);
        baseDto.setOrderPersonEntity(orderPersonEntity);
        baseDto.setOrderDeliveryDto(orderDeliveryDto);

        List<CheckMessageDto> calculateMsgList = new ArrayList<>();

        OrderMessageDto result = new OrderMessageDto();
        result.setOrderGoodsMessageMapMap(new HashMap<Integer, Map<Integer, List<CheckMessageDto>>>());
        result.setOrderMessageList(new ArrayList<CheckMessageDto>());

        // モック設定
        doReturn(calculateMsgList).when(billPriceCalculateLogic)
                                  .execute(any(ReceiveOrderDto.class), any(Boolean.class), any(Boolean.class));

        // 試験実行
        OrderMessageDto actual = logic.execute(dto, baseDto);

        // 戻り値及び呼び出し検証
        verify(billPriceCalculateLogic, times(1)).execute(
                        any(ReceiveOrderDto.class), any(Boolean.class), any(Boolean.class));
        assertThat(actual).isEqualTo(result);
    }
}
