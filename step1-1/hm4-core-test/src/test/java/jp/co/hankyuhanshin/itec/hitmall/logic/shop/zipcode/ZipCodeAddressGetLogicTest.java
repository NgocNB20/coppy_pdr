package jp.co.hankyuhanshin.itec.hitmall.logic.shop.zipcode;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.zipcode.ZipCodeDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;
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
public class ZipCodeAddressGetLogicTest {

    @Autowired
    ZipCodeAddressGetLogic zipCodeAddressGetLogic;

    @MockBean
    ZipCodeDao zipCodeDao;

    @Test
    @Order(1)
    public void zipCodeAddressList() {
        List<ZipCodeAddressDto> zipCodeList = new ArrayList<>();
        ZipCodeAddressDto dto = new ZipCodeAddressDto();
        dto.setZipCode("8800000");
        //dto.setZipCodeType("0");
        zipCodeList.add(dto);

        doReturn(zipCodeList).when(zipCodeDao).getAddressList(any(String.class));

        List<ZipCodeAddressDto> result = zipCodeAddressGetLogic.getAddressList("8800000");

        verify(zipCodeDao, times(1)).getAddressList(any(String.class));

        Assertions.assertEquals(1, result.size());

    }
}
