package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry;

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
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupGetLogic;

/**
 * Logic/Service移行：お問い合わせ分類取得
 * 作成日：2021/03/09
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquiryGroupGetServiceTest {

    @Autowired
    InquiryGroupGetService service;

    @MockBean
    private InquiryGroupGetLogic inquiryGroupGetLogic;

    @Test
    @Order(1)
    public void execute1Test() {

        // 想定値設定
        Integer inquiryGroupSeq = 1;
        InquiryGroupEntity inquiryGroupEntity = new InquiryGroupEntity();
        inquiryGroupEntity.setInquiryGroupName("ABC");

        // モック設定
        doReturn(inquiryGroupEntity).when(inquiryGroupGetLogic).execute(any(Integer.class), any(Integer.class));

        // 試験実行
        InquiryGroupEntity actual = service.execute(inquiryGroupSeq);

        // 戻り値及び呼び出し検証
        verify(inquiryGroupGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(inquiryGroupEntity);
    }
}
