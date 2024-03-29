package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.addressbook.AddressBookDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookGetEntityByCustomerNoLogicTest {

    @Autowired
    AddressBookGetEntityByCustomerNoLogic logic;

    @MockBean
    AddressBookDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer customerNo = 1;
        AddressBookEntity entity = new AddressBookEntity();

        // モック設定
        doReturn(entity).when(dao).getEntityByCustomerNo(any(Integer.class));

        // 試験実行
        AddressBookEntity actual = logic.execute(customerNo);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getEntityByCustomerNo(any(Integer.class));
        assertThat(actual).isEqualTo(entity);
    }

}
