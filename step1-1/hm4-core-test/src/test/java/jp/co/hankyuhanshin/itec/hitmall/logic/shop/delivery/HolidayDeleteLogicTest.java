package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.HolidayDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.HolidayEntity;
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

import java.sql.Timestamp;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HolidayDeleteLogicTest {

    @Autowired
    HolidayDeleteLogic holidayDeleteLogic;

    @MockBean
    HolidayDao holidayDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        int result = 1;

        // モック設定
        doReturn(result).when(holidayDao).deleteHolidayByYear(any(HolidayEntity.class));

        // 試験実行
        HolidayEntity holidayDeleteEntity = new HolidayEntity();
        holidayDeleteEntity.setDeliveryMethodSeq(0);
        holidayDeleteEntity.setDate(new Date());
        holidayDeleteEntity.setYear(0);
        holidayDeleteEntity.setName("");
        holidayDeleteEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));

        int actual = holidayDeleteLogic.execute(holidayDeleteEntity);

        // 戻り値及び呼び出し検証
        verify(holidayDao, times(1)).deleteHolidayByYear(any(HolidayEntity.class));
        Assertions.assertEquals(result, actual);
    }
}
