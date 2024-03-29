package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.AllDeliveryMethodListGetByShopSeqLogic;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AllDeliveryMethodListGetServiceTest {

    @Autowired
    AllDeliveryMethodListGetService allDeliveryMethodListGetService;

    @MockBean
    AllDeliveryMethodListGetByShopSeqLogic deliveryMethodListGetLogic;

    @Test
    @Order(1)
    public void execute() {
        // 想定値設定
        List<DeliveryMethodEntity> result = new ArrayList<>();
        DeliveryMethodEntity entity = new DeliveryMethodEntity();
        entity.setDeliveryMethodSeq(1);

        result.add(entity);

        // モック設定
        doReturn(result).when(deliveryMethodListGetLogic).execute(any(Integer.class));

        // 試験実行
        List<DeliveryMethodEntity> actual = allDeliveryMethodListGetService.execute();

        // 戻り値及び呼び出し検証
        verify(deliveryMethodListGetLogic, times(1)).execute(any(Integer.class));
        Assertions.assertEquals(result, actual);
    }

}
