package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookDataCheckLogic;
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
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookUpdateLogic;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookUpdateServiceTest {

    @Autowired
    AddressBookUpdateService addressBookUpdateService;

    @MockBean
    AddressBookUpdateLogic addressBookUpdateLogic;

    @MockBean
    AddressBookDataCheckLogic addressBookDataCheckLogic;

    @Test
    @Order(1)
    public void execute() {
        int result = 1;
        AddressBookEntity addressBookEntity = new AddressBookEntity();
        doReturn(result).when(addressBookUpdateLogic).execute((AddressBookEntity) any(Object.class));
        doNothing().when(addressBookDataCheckLogic).execute((AddressBookEntity) any(Object.class));

        int actual = addressBookUpdateService.execute(addressBookEntity);

        verify(addressBookUpdateLogic, times(1)).execute((AddressBookEntity) any(Object.class));
        verify(addressBookDataCheckLogic, times(1)).execute((AddressBookEntity) any(Object.class));
        assertThat(actual).isEqualTo(result);
    }
}
