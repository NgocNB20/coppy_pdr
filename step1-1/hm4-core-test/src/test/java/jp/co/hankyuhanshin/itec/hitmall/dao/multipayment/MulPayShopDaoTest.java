package jp.co.hankyuhanshin.itec.hitmall.dao.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayShopEntity;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MulPayShopDaoTest {

    @Autowired
    MulPayShopDao mulPayShopDao;

    @Test
    @Order(1)
    public void getEntityByShopId() {
        MulPayShopEntity entity = mulPayShopDao.getEntityByShopId("tshop00001282");
        Assertions.assertNotNull(entity);
    }

    @Test
    @Order(2)
    public void getEntityByShopSeq() {
        MulPayShopEntity entity = mulPayShopDao.getEntityByShopSeq(1001);
        Assertions.assertNotNull(entity);
    }
}
