package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryDao;

/**
 * Logic/Service移行：新規問い合わせSEQ取得ロジック
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewInquirySeqGetLogicTest {

    @Autowired
    NewInquirySeqGetLogic logic;

    @MockBean
    private InquiryDao inquiryDao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer result = 1;

        // モック設定
        doReturn(result).when(inquiryDao).getInquirySeqNextVal();

        // 試験実行
        Integer actual = logic.execute();

        // 戻り値及び呼び出し検証
        verify(inquiryDao, times(1)).getInquirySeqNextVal();
        assertThat(actual).isEqualTo(result);
    }
}
