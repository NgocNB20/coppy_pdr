package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
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

/**
 * Logic/Service移行：カート管理のリハーサル
 * 作成日：2021/03/01
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartGoodsRegistLogicTest {

    @Autowired
    CartGoodsRegistLogic cartGoodsRegistLogic;

    @Test
    @Order(1)
    public void executeTest() {
        CartGoodsEntity cartGoodsEntity = new CartGoodsEntity();
        cartGoodsEntity.setCartSeq(0);
        cartGoodsEntity.setAccessUid("");
        cartGoodsEntity.setMemberInfoSeq(0);
        cartGoodsEntity.setGoodsSeq(0);
        cartGoodsEntity.setCartGoodsCount(new BigDecimal("0"));
        cartGoodsEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        cartGoodsEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        cartGoodsEntity.setShopSeq(0);

        int result = cartGoodsRegistLogic.execute(cartGoodsEntity);

        // 前提：cartgoods（カート商品）TBLにデータが1件もない状態
        Assertions.assertEquals(result, 1);
    }
}
