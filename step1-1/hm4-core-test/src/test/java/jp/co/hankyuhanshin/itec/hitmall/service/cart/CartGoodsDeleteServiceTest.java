package jp.co.hankyuhanshin.itec.hitmall.service.cart;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
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

import java.util.ArrayList;
import java.util.List;

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
public class CartGoodsDeleteServiceTest {

    @Autowired
    CartGoodsDeleteService cartGoodsDeleteService;

    @Test
    @Order(1)
    public void executeTest() {
        List<Integer> cartGoodsSeqList = new ArrayList<>();
        cartGoodsSeqList.add(1);

        // テストデータは以下のように準備しておく
        // TBL名：cartgoods（カート商品）
        // cartseq: 1
        // memberinfoseq: 1
        // goodsseq: 1
        // cartgoodscount: 1
        cartGoodsDeleteService.execute(cartGoodsSeqList);
    }
}
