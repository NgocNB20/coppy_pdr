package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
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

import java.math.BigDecimal;
import java.util.List;

/**
 * Logic/Service移行：カート管理のリハーサル
 * 作成日：2021/02/26
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartGoodsAddLogicTest {

    @Autowired
    CartGoodsAddLogic cartGoodsAddLogic;

    @Test
    @Order(1)
    public void executeTest() {

        List<CheckMessageDto> checkMessage =
                        cartGoodsAddLogic.execute("77-55-7039", BigDecimal.TEN, null, "1", "1", "1", "1");
        Assertions.assertEquals(1, checkMessage.size());
    }

    @Test
    @Order(1)
    public void executeTestSaleType2() {

        List<CheckMessageDto> checkMessage =
                        cartGoodsAddLogic.execute("77-55-7039", BigDecimal.TEN, null, "1", "1", "1", "2");
        Assertions.assertEquals("PDR-0008-004-A-", checkMessage.get(0).getMessageId());
    }
}
