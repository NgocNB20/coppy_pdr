package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryMethodTypeCarriageDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;
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
public class DeliveryMethodTypeCarriageListGetLogicTest {

    @Autowired
    DeliveryMethodTypeCarriageListGetLogic deliveryMethodTypeCarriageListGetLogic;

    @MockBean
    DeliveryMethodTypeCarriageDao deliveryMethodTypeCarriageDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        List<DeliveryMethodTypeCarriageEntity> result = new ArrayList<>();
        DeliveryMethodTypeCarriageEntity entity = new DeliveryMethodTypeCarriageEntity();
        entity.setDeliveryMethodSeq(0);
        entity.setPrefectureType(HTypePrefectureType.HOKKAIDO);
        entity.setMaxPrice(new BigDecimal("0"));
        entity.setCarriage(new BigDecimal("0"));
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        result.add(entity);

        // モック設定
        doReturn(result).when(deliveryMethodTypeCarriageDao).getEntityList(any(Integer.class));

        // 試験実行
        List<DeliveryMethodTypeCarriageEntity> actual = deliveryMethodTypeCarriageListGetLogic.execute(1);

        // 戻り値及び呼び出し検証
        verify(deliveryMethodTypeCarriageDao, times(1)).getEntityList(any(Integer.class));
        Assertions.assertEquals(result, actual);
    }
}
