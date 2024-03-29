package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleDayDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleDayEntity;
import org.junit.jupiter.api.Assertions;
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

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryImpossibleDayGetByYearAndDateLogicTest {

    @Autowired
    DeliveryImpossibleDayGetByYearAndDateLogic deliveryImpossibleDayGetByYearAndDateLogic;

    @MockBean
    DeliveryImpossibleDayDao deliveryImpossibleDayDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        DeliveryImpossibleDayEntity result = new DeliveryImpossibleDayEntity();
        result.setDeliveryMethodSeq(0);
        result.setDate(new Date());
        result.setYear(0);
        result.setReason("");
        result.setRegistTime(new Timestamp(new java.util.Date().getTime()));

        // モック設定
        doReturn(result).when(deliveryImpossibleDayDao)
                        .getDeliveryImpossibleDayByYearAndDate(any(Integer.class), any(Date.class), any(Integer.class));

        // 試験実行
        DeliveryImpossibleDayEntity actual = deliveryImpossibleDayGetByYearAndDateLogic.execute(2021, new Date(), 1);

        // 戻り値及び呼び出し検証
        verify(deliveryImpossibleDayDao, times(1)).getDeliveryImpossibleDayByYearAndDate(
                        any(Integer.class), any(Date.class), any(Integer.class));
        Assertions.assertEquals(result, actual);
    }
}
