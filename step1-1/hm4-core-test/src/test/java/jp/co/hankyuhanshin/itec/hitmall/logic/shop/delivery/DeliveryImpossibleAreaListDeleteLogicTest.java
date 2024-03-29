package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryImpossibleAreaListDeleteLogicTest {

    @Autowired
    DeliveryImpossibleAreaListDeleteLogic deliveryImpossibleAreaListDeleteLogic;

    @MockBean
    DeliveryImpossibleAreaDao deliveryImpossibleAreaDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        int result = 1;

        // モック設定
        doReturn(result).when(deliveryImpossibleAreaDao).delete(any(DeliveryImpossibleAreaEntity.class));

        // 試験実行
        List<DeliveryImpossibleAreaEntity> entityList = new ArrayList<>();
        DeliveryImpossibleAreaEntity deliveryImpossibleAreaEntity = new DeliveryImpossibleAreaEntity();
        deliveryImpossibleAreaEntity.setDeliveryMethodSeq(1);
        deliveryImpossibleAreaEntity.setZipCode("1");
        deliveryImpossibleAreaEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        deliveryImpossibleAreaEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        entityList.add(deliveryImpossibleAreaEntity);

        int actual = deliveryImpossibleAreaListDeleteLogic.execute(entityList);

        // 戻り値及び呼び出し検証
        verify(deliveryImpossibleAreaDao, times(1)).delete(any(DeliveryImpossibleAreaEntity.class));
        Assertions.assertEquals(result, actual);
    }
}
