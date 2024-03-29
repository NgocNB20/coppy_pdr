package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

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
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookRegistLogic;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookRegistServiceTest {

    @Autowired
    AddressBookRegistService addressBookRegistService;

    @MockBean
    AddressBookRegistLogic addressBookRegistLogic;

    @Test
    @Order(1)
    public void execute() {
        int result = 1;
        AddressBookEntity addressBookEntity = new AddressBookEntity();
        doReturn(result).when(addressBookRegistLogic).execute((AddressBookEntity) any(Object.class));
        int actual = addressBookRegistService.execute(addressBookEntity);
        verify(addressBookRegistLogic, times(1)).execute((AddressBookEntity) any(Object.class));
        assertThat(actual).isEqualTo(result);
    }
}
