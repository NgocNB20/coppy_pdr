package jp.co.hankyuhanshin.itec.hitmall.logic.common;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfoCart;
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

import java.math.BigDecimal;

/**
 * Logic/Service移行： カート情報作成Logic(共通情報)
 * 作成日：2021/03/19
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommonInfoCartCreateLogicTest {

    @Autowired
    CommonInfoCartCreateLogic logic;

    @MockBean
    private CartGoodsDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer memberInfoSeq = 1;
        String accessUid = "ID";
        CommonInfoCart result = new CommonInfoCart();

        // モック設定
        doReturn(result).when(dao).getCommonInfoCart(any(Integer.class), any(String.class));

        // 試験実行
        CommonInfoCart actual = logic.execute(memberInfoSeq, accessUid);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getCommonInfoCart(any(Integer.class), any(String.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(1)
    public void executeTest2() {

        // 想定値設定
        Integer memberInfoSeq = 1;
        String accessUid = "ID";
        BigDecimal result = BigDecimal.ZERO;

        // 試験実行
        CommonInfoCart actual = logic.execute(memberInfoSeq, accessUid);

        // 戻り値及び呼び出し検証
        assertThat(actual.getCartGoodsSumPrice()).isEqualTo(result);
    }
}
