package jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
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

import java.sql.Timestamp;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookDaoTest {

    @Autowired
    AddressBookDao addressBookDao;

    @Test
    @Order(1)
    public void insert() {
        AddressBookEntity entity = makeAddressBookEntity();

        int result = addressBookDao.insert(entity);
        System.out.println("result: " + result);
        Assertions.assertNotNull(addressBookDao.getAddressBookByName(999, "AddressBookName"));
    }

    @Test
    @Order(1)
    public void delete() {
        AddressBookEntity entity = addressBookDao.getAddressBookByName(999, "AddressBookName");

        int result = addressBookDao.delete(entity);
        Assertions.assertNull(addressBookDao.getEntity(999, 999));
    }

    private AddressBookEntity makeAddressBookEntity() {
        AddressBookEntity entity = new AddressBookEntity();
        entity.setMemberInfoSeq(999);
        entity.setAddressBookName("AddressBookName");
        entity.setAddressBookLastName("AddressBookLastName");
        entity.setAddressBookFirstName("AddressBookFirstName");
        entity.setAddressBookLastKana("AddressBookLastKana");
        entity.setAddressBookFirstKana("AddressBookFirstKana");
        entity.setAddressBookTel("AddressBookTel");
        entity.setAddressBookZipCode("1000001");
        entity.setAddressBookPrefecture("Prefecture");
        entity.setAddressBookAddress1("AddressBookAddress1");
        entity.setAddressBookAddress2("AddressBookAddress2");
        entity.setAddressBookAddress3("AddressBookAddress3");
        entity.setRegistTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));
        entity.setUpdateTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));

        return entity;
    }
}
