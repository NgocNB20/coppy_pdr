package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.index.OrderIndexDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderMarkMailSendingLogicTest {

    @Autowired
    OrderMarkMailSendingLogic logic;

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
     * 受注サマリDao
     */
    @MockBean
    private OrderSummaryDao orderSummaryDao;

    /**
     * 受注インデックスDao
     */
    @MockBean
    private OrderIndexDao orderIndexDao;

    @Test
    @Order(1)
    public void markMailSending() {

        // 想定値設定
        Integer orderSeq = 1;
        HTypeSend reminderSentFlag = HTypeSend.SENT;
        HTypeSend expiredSentFlag = HTypeSend.SENT;
        Integer orderVersionNo = 1;

        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setOrderVersionNo(1);

        OrderIndexEntity orderIndexEntity = new OrderIndexEntity();
        String processPersonName = "NAME";

        Integer result = 1;

        // モック設定
        doReturn(orderSummaryEntity).when(orderSummaryDao).getEntityForUpdatePrimaryKey(any(Integer.class));
        doReturn(result).when(orderSummaryDao).update(any(OrderSummaryEntity.class));
        doReturn(orderIndexEntity).when(orderIndexDao).getEntity(any(Integer.class), any(Integer.class));
        doReturn(result).when(orderIndexDao).update(any(OrderIndexEntity.class));
        doReturn(processPersonName).when(commonInfoUtility).getAdministratorName(any(CommonInfo.class));

        // 試験実行
        Integer actual = logic.markMailSending(orderSeq, reminderSentFlag, expiredSentFlag, orderVersionNo);

        // 戻り値及び呼び出し検証
        verify(orderSummaryDao, times(1)).getEntityForUpdatePrimaryKey(any(Integer.class));
        verify(orderSummaryDao, times(1)).update(any(OrderSummaryEntity.class));
        verify(orderIndexDao, times(1)).getEntity(any(Integer.class), any(Integer.class));
        verify(orderIndexDao, times(1)).update(any(OrderIndexEntity.class));
        verify(orderIndexDao, times(0)).insert(any(OrderIndexEntity.class));
        verify(commonInfoUtility, times(1)).getAdministratorName(any(CommonInfo.class));
        assertThat(actual).isEqualTo(result);
    }
}
