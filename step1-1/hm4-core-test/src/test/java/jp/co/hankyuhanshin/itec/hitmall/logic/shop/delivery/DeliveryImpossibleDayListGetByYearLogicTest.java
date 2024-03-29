package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import java.util.Date;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleDayDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleDaySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleDayEntity;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryImpossibleDayListGetByYearLogicTest {

    @Autowired
    DeliveryImpossibleDayListGetByYearLogic deliveryImpossibleDayListGetByYearLogic;

    @MockBean
    DeliveryImpossibleDayDao deliveryImpossibleDayDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        List<DeliveryImpossibleDayEntity> result = new ArrayList<>();
        DeliveryImpossibleDayEntity entity = new DeliveryImpossibleDayEntity();
        entity.setDeliveryMethodSeq(0);
        entity.setDate(new Date());
        entity.setYear(0);
        entity.setReason("");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));

        result.add(entity);

        // モック設定
        doReturn(result).when(deliveryImpossibleDayDao)
                        .getSearchDeliveryImpossibleDayList(
                                        any(DeliveryImpossibleDaySearchForDaoConditionDto.class),
                                        any(SelectOptions.class)
                                                           );

        // 試験実行
        DeliveryImpossibleDaySearchForDaoConditionDto conditionDto =
                        new DeliveryImpossibleDaySearchForDaoConditionDto();
        conditionDto.setYear(2021);
        conditionDto.setDeliveryMethodSeq(1);

        List<DeliveryImpossibleDayEntity> actual = deliveryImpossibleDayListGetByYearLogic.execute(conditionDto);

        // 戻り値及び呼び出し検証
        verify(deliveryImpossibleDayDao, times(1)).getSearchDeliveryImpossibleDayList(
                        any(DeliveryImpossibleDaySearchForDaoConditionDto.class), any(SelectOptions.class));
        Assertions.assertEquals(result, actual);
    }
}
