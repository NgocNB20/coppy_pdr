package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.addressbook.AddressBookSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookListGetLogic;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookListGetServiceTest {

    @Autowired
    AddressBookListGetService addressBookListGetService;

    @MockBean
    AddressBookListGetLogic addressBookListGetLogic;

    @Test
    @Order(1)
    public void execute() {
        List<AddressBookEntity> result = new ArrayList<>();
        AddressBookSearchForDaoConditionDto addressBookConditionDto = new AddressBookSearchForDaoConditionDto();
        addressBookConditionDto.setPageInfo(new PageInfo());
        addressBookConditionDto.getPageInfo().setPnum("1");
        addressBookConditionDto.getPageInfo().setupSelectOptions();

        doReturn(result).when(addressBookListGetLogic).execute((AddressBookSearchForDaoConditionDto) any(Object.class));
        List<AddressBookEntity> actual = addressBookListGetService.execute(addressBookConditionDto);
        verify(addressBookListGetLogic, times(1)).execute((AddressBookSearchForDaoConditionDto) any(Object.class));
        assertThat(actual).isEqualTo(result);
    }
}
