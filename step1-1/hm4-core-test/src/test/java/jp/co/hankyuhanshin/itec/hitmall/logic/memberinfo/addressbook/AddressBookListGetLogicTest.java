package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook;

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
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.addressbook.AddressBookDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.addressbook.AddressBookSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

/**
 * Logic/Service移行：アドレス帳情報リスト取得ロジック
 * 作成日：2021/03/11
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookListGetLogicTest {

    @Autowired
    AddressBookListGetLogic logic;

    @MockBean
    private AddressBookDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        AddressBookSearchForDaoConditionDto addressBookConditionDto = new AddressBookSearchForDaoConditionDto();
        addressBookConditionDto.setPageInfo(new PageInfo());
        addressBookConditionDto.getPageInfo().setPnum("1");
        addressBookConditionDto.getPageInfo().setupSelectOptions();
        List<AddressBookEntity> result = new ArrayList<AddressBookEntity>();

        // モック設定
        doReturn(result).when(dao)
                        .getSearchAddressBookList(any(AddressBookSearchForDaoConditionDto.class),
                                                  any(SelectOptions.class)
                                                 );

        // 試験実行
        List<AddressBookEntity> actual = logic.execute(addressBookConditionDto);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getSearchAddressBookList(
                        any(AddressBookSearchForDaoConditionDto.class), any(SelectOptions.class));
        assertThat(actual).isEqualTo(result);
    }
}
