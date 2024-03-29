package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;

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
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderBillEmergencyRegistLogicTest {

    @Autowired
    OrderBillEmergencyRegistLogic orderBillEmergencyRegistLogic;

    /**
     * DateUtility
     */
    @MockBean
    private DateUtility dateUtility;

    /**
     * CommonInfoUtility
     */
    @MockBean
    private CommonInfoUtility commonInfoUtility;

    /**
     * 受注インデックス取得ロジック
     */
    @MockBean
    private OrderIndexGetLogic orderIndexGetLogic;

    /**
     * 受注請求取得ロジック
     */
    @MockBean
    private OrderBillGetLogic orderBillGetLogic;

    /**
     * 受注請求登録ロジック
     */
    @MockBean
    private OrderBillRegistLogic orderBillRegistLogic;

    /**
     * 受注インデックス登録ロジック
     */
    @MockBean
    private OrderIndexRegistLogic orderIndexRegistLogic;

    /**
     * 受注サマリー更新ロジック
     */
    @MockBean
    private OrderSummaryUpdateLogic orderSummaryUpdateLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setOrderSeq(2);
        orderSummaryEntity.setOrderVersionNo(1);
        OrderIndexEntity orderIndexEntity = new OrderIndexEntity();
        orderIndexEntity.setOrderSeq(2);
        orderIndexEntity.setOrderBillVersionNo(1);
        orderIndexEntity.setOrderVersionNo(1);
        OrderBillEntity orderBillEntity = new OrderBillEntity();
        orderBillEntity.setOrderBillVersionNo(1);
        String processPersonName = "NAME";
        Timestamp timestamp = Timestamp.valueOf("2021-03-17 12:12:12");

        // モック設定
        doReturn(orderIndexEntity).when(orderIndexGetLogic).execute(any(Integer.class), any(Integer.class));
        doReturn(orderBillEntity).when(orderBillGetLogic).execute(any(Integer.class), any(Integer.class));
        doReturn(1).when(orderBillRegistLogic).execute(any(OrderBillEntity.class));
        doReturn(processPersonName).when(commonInfoUtility).getAdministratorName(any(CommonInfo.class));
        doReturn(timestamp).when(dateUtility).getCurrentTime();
        doReturn(1).when(orderIndexRegistLogic).execute(any(OrderIndexEntity.class));
        doReturn(1).when(orderSummaryUpdateLogic).execute(any(OrderSummaryEntity.class));

        // 試験実行
        orderBillEmergencyRegistLogic.execute(orderSummaryEntity);

        // 戻り値及び呼び出し検証
        verify(orderIndexGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        verify(orderBillGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        verify(orderBillRegistLogic, times(1)).execute(any(OrderBillEntity.class));
        verify(commonInfoUtility, times(1)).getAdministratorName(any(CommonInfo.class));
        verify(dateUtility, times(1)).getCurrentTime();
        verify(orderIndexRegistLogic, times(1)).execute(any(OrderIndexEntity.class));
        verify(orderSummaryUpdateLogic, times(1)).execute(any(OrderSummaryEntity.class));
    }
}
