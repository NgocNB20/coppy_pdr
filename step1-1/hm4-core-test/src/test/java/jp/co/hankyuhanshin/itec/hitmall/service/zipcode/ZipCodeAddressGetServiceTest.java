package jp.co.hankyuhanshin.itec.hitmall.service.zipcode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.zipcode.ZipCodeAddressGetLogic;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ZipCodeAddressGetServiceTest {

    @Autowired
    ZipCodeAddressGetService service;

    @MockBean
    private ZipCodeAddressGetLogic logic;

    @Test
    @Order(1)
    public void executeTest() {

        ZipCodeAddressDto dto = new ZipCodeAddressDto();
        dto.setZipCode("8800000");

        doReturn(dto).when(logic).execute(any(String.class));

        ZipCodeAddressDto actual = service.execute("8800000");

        verify(logic, times(1)).execute(any(String.class));
        assertThat(actual).isEqualTo(dto);
    }
}
