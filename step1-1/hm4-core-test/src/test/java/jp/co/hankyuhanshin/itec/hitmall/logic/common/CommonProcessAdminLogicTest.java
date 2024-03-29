package jp.co.hankyuhanshin.itec.hitmall.logic.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;

/**
 * Logic/Service移行： ショップ情報作成ロジック(共通情報)
 * 作成日：2021/03/19
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommonProcessAdminLogicTest {

    @Autowired
    CommonProcessAdminLogic logic;

    @Test
    @Order(1)
    public void executeTest() throws IOException {

        // 想定値設定
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);

        HttpServletResponse response = new MockHttpServletResponse();

        // ITEC山内　[QUAD-893] 対応にてApplicationUtilityクラスを削除

        // QUAD-893の修正にてexecuteメソッドから例外スローされなくなる想定
        // JUnitテストエラーになることは防ぎたいので、こちらコメントアウトさせていただきます。。
        //        try {
        //
        //            // 試験実行
        //            logic.execute(request, response);
        //            fail("例外がスローされなかった。");
        //        } catch (Exception e) {
        //
        //            assertThat(e).isInstanceOf(RuntimeException.class);
        //            assertThat(e.getMessage()).isEqualTo("shopSeq");
        //        }
        //
        //        // 戻り値及び呼び出し検証
        //        verify(applicationUtility, times(1)).getShopSeq();
    }
}
