package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayBillDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayResultDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayShopDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayResultEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayShopEntity;
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
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MulPayNotificationReceiverLogicTest {

    @Autowired
    MulPayNotificationReceiverLogic mulPayNotificationReceiverLogic;

    @MockBean
    MulPayShopDao mulPayShopDao;

    @MockBean
    MulPayBillDao mulPayBillDao;

    @MockBean
    MulPayResultDao mulPayResultDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        MulPayShopEntity mulPayShopEntity = new MulPayShopEntity();
        mulPayShopEntity.setShopSeq(1);

        MulPayBillEntity mulPayBillEntity = new MulPayBillEntity();
        mulPayBillEntity.setAccessId("1");

        // モック設定
        doReturn(mulPayShopEntity).when(mulPayShopDao).getEntityByShopId(any(String.class));
        doReturn(mulPayBillEntity).when(mulPayBillDao)
                                  .getLatestEntityByOrderIdAndAccessId(any(String.class), any(String.class));
        doReturn(0).when(mulPayResultDao)
                   .checkSameNotificationRecord(any(String.class), any(String.class), any(Integer.class),
                                                any(String.class), any(String.class)
                                               );
        doReturn(1).when(mulPayResultDao).insert(any(MulPayResultEntity.class));

        // 試験実行
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("ShopID", "1");
        request.setParameter("OrderID", "1");
        request.setParameter("AccessID", "1");
        request.setParameter("ShopPass", "1");
        request.setParameter("AccessPass", "1");
        request.setParameter("Status", "1");
        request.setParameter("Amount", "1");
        request.setParameter("Tax", "1");
        request.setParameter("TranDate", "1");
        request.setParameter("ErrCode", "0");
        request.setParameter("ErrInfo", "0");
        request.setParameter("PayType", "1");
        request.setParameter("Currency", "jp");
        mulPayNotificationReceiverLogic.execute(request);

        // 戻り値及び呼び出し検証
        verify(mulPayShopDao, times(1)).getEntityByShopId(any(String.class));
        verify(mulPayBillDao, times(1)).getLatestEntityByOrderIdAndAccessId(any(String.class), any(String.class));
        verify(mulPayResultDao, times(1)).checkSameNotificationRecord(
                        any(String.class), any(String.class), any(Integer.class), any(String.class), any(String.class));
        verify(mulPayResultDao, times(1)).insert(any(MulPayResultEntity.class));
    }
}
