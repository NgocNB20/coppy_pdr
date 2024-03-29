package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;

/**
 * Logic/Service移行：お問い合わせ分類取得ロジック
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquiryGroupGetLogicTest {

    @Autowired
    InquiryGroupGetLogic inquiryGroupGetLogic;

    @MockBean
    private InquiryGroupDao inquiryGroupDao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer inquiryGroupSeq = 2;
        Integer shopSeq = 1;
        InquiryGroupEntity result = new InquiryGroupEntity();

        // モック設定
        doReturn(result).when(inquiryGroupDao).getEntityByShopSeq(any(Integer.class), any(Integer.class));

        // 試験実行
        InquiryGroupEntity actual = inquiryGroupGetLogic.execute(inquiryGroupSeq, shopSeq);

        // 戻り値及び呼び出し検証
        verify(inquiryGroupDao, times(1)).getEntityByShopSeq(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }
}
