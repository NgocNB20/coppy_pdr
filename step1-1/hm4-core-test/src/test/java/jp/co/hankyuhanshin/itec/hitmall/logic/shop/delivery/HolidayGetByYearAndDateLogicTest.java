package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import java.sql.Timestamp;

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
public class HolidayGetByYearAndDateLogicTest {

    @Autowired
    HolidayGetByYearAndDateLogic holidayGetByYearAndDateLogic;

    @MockBean
    HolidayDao holidayDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        HolidayEntity result = new HolidayEntity();
        result.setDeliveryMethodSeq(1);
        result.setDate(new Date());
        result.setYear(1);
        result.setName("1");
        result.setRegistTime(new Timestamp(new java.util.Date().getTime()));

        // モック設定
        doReturn(result).when(holidayDao)
                        .getHolidayByYearAndDate(any(Integer.class), any(Date.class), any(Integer.class));

        // 試験実行
        HolidayEntity actual = holidayGetByYearAndDateLogic.execute(2020, new Date(), 1);

        // 戻り値及び呼び出し検証
        verify(holidayDao, times(1)).getHolidayByYearAndDate(any(Integer.class), any(Date.class), any(Integer.class));
        Assertions.assertEquals(result, actual);
    }
}
