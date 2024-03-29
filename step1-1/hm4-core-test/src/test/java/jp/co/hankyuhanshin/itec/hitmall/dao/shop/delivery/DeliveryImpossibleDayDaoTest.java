package jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryImpossibleDayDaoTest {

    @Autowired
    DeliveryImpossibleDayDao deliveryImpossibleDayDao;

    @Test
    @Order(1)
    public void insert() {
        DeliveryImpossibleDayEntity entity = makeDeliveryImpossibleDayEntity();
        int result = deliveryImpossibleDayDao.insert(entity);
        DeliveryImpossibleDaySearchForDaoConditionDto dto = new DeliveryImpossibleDaySearchForDaoConditionDto();
        dto.setYear(2021);
        dto.setDeliveryMethodSeq(99);
        List<DeliveryImpossibleDayEntity> entityList =
                        deliveryImpossibleDayDao.getSearchDeliveryImpossibleDayList(dto, SelectOptions.get());
        Assertions.assertEquals(1, entityList.size());

        for (DeliveryImpossibleDayEntity del : entityList) {
            result = deliveryImpossibleDayDao.delete(del);
        }

        List<DeliveryImpossibleDayEntity> entityList2 =
                        deliveryImpossibleDayDao.getSearchDeliveryImpossibleDayList(dto, SelectOptions.get());
        Assertions.assertEquals(0, entityList2.size());
    }

    private DeliveryImpossibleDayEntity makeDeliveryImpossibleDayEntity() {
        DeliveryImpossibleDayEntity entity = new DeliveryImpossibleDayEntity();
        entity.setDeliveryMethodSeq(99);
        entity.setDate(new Date());
        entity.setYear(2021);
        entity.setReason("1");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
