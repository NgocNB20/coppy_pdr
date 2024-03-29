package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

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

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookListDeleteLogic;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookListDeleteServiceTest {

    @Autowired
    AddressBookListDeleteService addressBookListDeleteService;

    @MockBean
    AddressBookListDeleteLogic addressBookListDeleteLogic;

    @Test
    @Order(1)
    public void execute() {
        int result = 1;
        int memberInfoSeq = 1;
        List<Integer> addressBookSeqList = new ArrayList<>();
        doReturn(result).when(addressBookListDeleteLogic)
                        .execute(any(Integer.class), (List<Integer>) any(Object.class));

        int actual = addressBookListDeleteLogic.execute(memberInfoSeq, addressBookSeqList);

        verify(addressBookListDeleteLogic, times(1)).execute(any(Integer.class), (List<Integer>) any(Object.class));
        assertThat(actual).isEqualTo(result);
    }
}
