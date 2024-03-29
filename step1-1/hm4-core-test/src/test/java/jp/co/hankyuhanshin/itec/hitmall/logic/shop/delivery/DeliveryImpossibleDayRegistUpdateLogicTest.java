package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import java.util.Date;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryImpossibleDayRegistUpdateLogicTest {

    @Autowired
    DeliveryImpossibleDayRegistUpdateLogic deliveryImpossibleDayRegistUpdateLogic;

    @MockBean
    DeliveryImpossibleDayDao deliveryImpossibleDayDao;

    @MockBean
    DeliveryImpossibleDayGetByYearAndDateLogic deliveryImpossibleDayGetByYearAndDateLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        int result = 1;

        // モック設定
        doReturn(null).when(deliveryImpossibleDayGetByYearAndDateLogic)
                      .execute(any(Integer.class), any(Date.class), any(Integer.class));
        doReturn(result).when(deliveryImpossibleDayDao).insert(any(DeliveryImpossibleDayEntity.class));

        // 試験実行
        DeliveryImpossibleDayEntity deliveryImpossibleDayEntity = new DeliveryImpossibleDayEntity();
        deliveryImpossibleDayEntity.setDeliveryMethodSeq(1);
        deliveryImpossibleDayEntity.setDate(new Date());
        deliveryImpossibleDayEntity.setYear(1);
        deliveryImpossibleDayEntity.setReason("1");
        deliveryImpossibleDayEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));

        int actual = deliveryImpossibleDayRegistUpdateLogic.execute(deliveryImpossibleDayEntity);

        // 戻り値及び呼び出し検証
        verify(deliveryImpossibleDayDao, times(1)).insert(any(DeliveryImpossibleDayEntity.class));
        Assertions.assertEquals(result, actual);
    }
}
