package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaResultDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.seasar.doma.jdbc.SelectOptions;
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
public class DeliveryImpossibleAreaListGetLogicTest {

    @Autowired
    DeliveryImpossibleAreaListGetLogic deliveryImpossibleAreaListGetLogic;

    @MockBean
    DeliveryImpossibleAreaDao deliveryImpossibleAreaDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        List<DeliveryImpossibleAreaResultDto> result = new ArrayList<>();
        DeliveryImpossibleAreaResultDto dto = new DeliveryImpossibleAreaResultDto();
        dto.setDeliveryMethodSeq(1);
        dto.setZipcode("5550005");
        dto.setPrefecture("1");
        dto.setCity("1");
        dto.setTown("1");
        dto.setNumbers("1");
        dto.setAddressList("1");

        result.add(dto);

        // モック設定
        doReturn(result).when(deliveryImpossibleAreaDao)
                        .getDeliveryImpossibleAreaListInAddress(any(DeliveryImpossibleAreaConditionDto.class),
                                                                any(SelectOptions.class)
                                                               );

        // 試験実行
        DeliveryImpossibleAreaConditionDto conditionDto = new DeliveryImpossibleAreaConditionDto();
        conditionDto.setDeliveryMethodSeq(1);
        conditionDto.setZipcode("5550005");
        conditionDto.setPrefecture("1");

        List<DeliveryImpossibleAreaResultDto> actual = deliveryImpossibleAreaListGetLogic.execute(conditionDto);

        // 戻り値及び呼び出し検証
        verify(deliveryImpossibleAreaDao, times(1)).getDeliveryImpossibleAreaListInAddress(
                        any(DeliveryImpossibleAreaConditionDto.class), any(SelectOptions.class));
        Assertions.assertEquals(result, actual);
    }
}
