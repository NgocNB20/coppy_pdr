package jp.co.hankyuhanshin.itec.hitmall.dao.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFrontDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;
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
public class CardBrandDaoTest {

    @Autowired
    CardBrandDaoMock cardBrandDaoMock;

    @Autowired
    CardBrandDao cardBrandDao;

    @Test
    @Order(1)
    public void insert() {
        CardBrandEntity entity = makeCardBrandEntity();
        int result = cardBrandDao.insert(entity);
        Assertions.assertNotNull(cardBrandDao.getEntityByCardBrandCode("1test2"));

        result = cardBrandDaoMock.delete(cardBrandDao.getEntityByCardBrandCode("1test2"));
    }

    private CardBrandEntity makeCardBrandEntity() {
        CardBrandEntity entity = new CardBrandEntity();
        entity.setCardBrandSeq(99);
        entity.setCardBrandCode("1test2");
        entity.setCardBrandName("1");
        entity.setCardBrandDisplayPc("1");
        entity.setCardBrandDisplayMb("1");
        entity.setOrderDisplay(1);
        entity.setInstallment("1");
        entity.setBounusSingle("1");
        entity.setBounusInstallment("1");
        entity.setRevolving("1");
        entity.setInstallmentCounts("1");
        entity.setFrontDisplayFlag(HTypeFrontDisplayFlag.ON);
        return entity;
    }
}
