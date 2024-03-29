package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookGetLogic;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookGetServiceTest {

    @Autowired
    AddressBookGetService addressBookGetService;

    @MockBean
    AddressBookGetLogic addressBookGetLogic;

    @Test
    @Order(1)
    public void execute() {
        int memberInfoSeq = 1;
        int addressBookSeq = 1;
        AddressBookEntity result = new AddressBookEntity();
        doReturn(result).when(addressBookGetLogic).execute(any(Integer.class), any(Integer.class));
        AddressBookEntity actual = addressBookGetService.execute(memberInfoSeq, addressBookSeq);
        verify(addressBookGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }
}
