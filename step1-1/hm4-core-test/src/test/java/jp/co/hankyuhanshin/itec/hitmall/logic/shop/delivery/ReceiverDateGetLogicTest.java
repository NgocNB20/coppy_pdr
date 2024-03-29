package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import java.util.Date;
import java.util.HashMap;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverDateDesignationFlag;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.HolidayDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.ReceiverDateDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReceiverDateGetLogicTest {

    @Autowired
    ReceiverDateGetLogic receiverDateGetLogic;

    @MockBean
    HolidayDao holidayDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        int holiday = 0;

        // モック設定
        doReturn(holiday).when(holidayDao).getCountByDate(any(Date.class), any(Integer.class));

        // 試験実行
        ReceiverDateDto receiverDateDto = new ReceiverDateDto();
        receiverDateDto.setDateMap(new HashMap<>());
        receiverDateDto.setReceiverDateDesignationFlag(HTypeReceiverDateDesignationFlag.OFF);
        receiverDateDto.setShortestDeliveryDateToRegist(new Timestamp(new java.util.Date().getTime()));

        Timestamp actual = receiverDateGetLogic.createReceiverDateList(receiverDateDto, 1, 1, 1);

        // 戻り値及び呼び出し検証
        verify(holidayDao, times(1)).getCountByDate(any(Date.class), any(Integer.class));
        Assertions.assertNotNull(actual);
    }
}
