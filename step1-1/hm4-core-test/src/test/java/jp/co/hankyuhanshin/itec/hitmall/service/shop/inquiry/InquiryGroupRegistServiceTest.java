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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupRegistLogic;

/**
 * Logic/Service移行：問い合わせ分類登録
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquiryGroupRegistServiceTest {

    @Autowired
    InquiryGroupRegistService service;

    @MockBean
    private InquiryGroupRegistLogic inquiryGroupRegistLogic;

    @Test
    @Order(1)
    public void execute1Test() {

        // 想定値設定
        InquiryGroupEntity entity = new InquiryGroupEntity();
        entity.setInquiryGroupName("ABC");
        entity.setOpenStatus(HTypeOpenDeleteStatus.OPEN);

        int result = 1;

        // モック設定
        doReturn(result).when(inquiryGroupRegistLogic).execute(any(InquiryGroupEntity.class));

        // 試験実行
        int actual = service.execute(entity);

        // 戻り値及び呼び出し検証
        verify(inquiryGroupRegistLogic, times(1)).execute(any(InquiryGroupEntity.class));
        assertThat(actual).isEqualTo(result);
    }
}
