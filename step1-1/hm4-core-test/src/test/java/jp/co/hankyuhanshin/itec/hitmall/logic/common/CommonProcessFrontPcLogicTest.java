package jp.co.hankyuhanshin.itec.hitmall.logic.common;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.utility.AccessDeviceUtility;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.Mockito.doReturn;

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
public class CommonProcessFrontPcLogicTest {

    @Autowired
    CommonProcessFrontPcLogic logic;

    @MockBean
    public HttpServletRequest request;

    @MockBean
    public HttpServletResponse response;

    @MockBean
    AccessDeviceUtility accessDeviceUtility;

    @Test
    @Order(1)
    public void executeTest() throws IOException {

        BigDecimal result = BigDecimal.ZERO;

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
        //            assertThat(e.getMessage()).isNullOrEmpty();
        //        }
        //
        //        // 戻り値及び呼び出し検証
        //        verify(applicationUtility, times(1)).getShopSeq();
        doReturn("test").when(accessDeviceUtility).getUserAgent(request);
        doReturn(HTypeDeviceType.PC).when(accessDeviceUtility).getDeviceType(request);

        CommonInfo commonInfo = logic.execute(request, response);
        Assertions.assertEquals(result, commonInfo.getCommonInfoBase().getCartGoodsSumCount());
    }
}
