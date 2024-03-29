package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

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

import java.math.BigDecimal;
import java.sql.Timestamp;

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
public class CartGoodsUpdateLogicTest {

    @Autowired
    CartGoodsUpdateLogic cartGoodsUpdateLogic;

    @Test
    @Order(1)
    public void executeTest() {
        CartGoodsEntity cartGoodsEntity = new CartGoodsEntity();
        cartGoodsEntity.setCartSeq(1);
        cartGoodsEntity.setAccessUid("");
        cartGoodsEntity.setMemberInfoSeq(1);
        cartGoodsEntity.setGoodsSeq(1);
        cartGoodsEntity.setCartGoodsCount(new BigDecimal("10"));
        cartGoodsEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        cartGoodsEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        cartGoodsEntity.setShopSeq(0);

        int result = cartGoodsUpdateLogic.execute(cartGoodsEntity);

        // テストデータは以下のように準備しておく
        // TBL名：cartgoods（カート商品）
        // cartseq: 1
        // memberinfoseq: 1
        // goodsseq: 1
        // cartgoodscount: 1
        Assertions.assertEquals(result, 1);
    }
}
