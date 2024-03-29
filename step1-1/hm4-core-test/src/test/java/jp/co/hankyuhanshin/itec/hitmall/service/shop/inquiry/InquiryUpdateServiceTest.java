package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryUpdateLogic;

/**
 * Logic/Service移行:問い合わせ更新サービス
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquiryUpdateServiceTest {

    @Autowired
    InquiryUpdateService service;

    @MockBean
    private InquiryUpdateLogic inquiryUpdateLogic;

    @MockBean
    private InquiryGetService inquiryGetService;

    @Test
    @Order(1)
    public void executeMemberRegistTest() {

    }

    @Test
    @Order(2)
    public void executeMemberRegistReleaseTest() {

        // 想定値設定
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setShopSeq(1);
        inquiryEntity.setInquirySeq(1);
        inquiryEntity.setInquiryCode("ABC");
        inquiryEntity.setInquiryLastName("NAME");
        inquiryEntity.setInquiryTime(Timestamp.valueOf("2021-03-10 12:12:12"));
        inquiryEntity.setInquiryStatus(HTypeInquiryStatus.SENDING);
        inquiryEntity.setInquiryGroupSeq(1);
        int result = 1;

        // モック設定
        doReturn(result).when(inquiryUpdateLogic).execute(any(InquiryEntity.class));

        // 試験実行
        int actual = service.executeMemberRegistRelease(inquiryEntity);

        // 戻り値及び呼び出し検証
        verify(inquiryUpdateLogic, times(1)).execute(any(InquiryEntity.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(3)
    public void executeMemoRegistTest() {

        // 想定値設定
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setShopSeq(1);
        inquiryEntity.setInquirySeq(1);
        inquiryEntity.setInquiryCode("ABC");
        inquiryEntity.setInquiryLastName("NAME");
        inquiryEntity.setInquiryTime(Timestamp.valueOf("2021-03-10 12:12:12"));
        inquiryEntity.setInquiryStatus(HTypeInquiryStatus.SENDING);
        inquiryEntity.setInquiryGroupSeq(1);
        int result = 1;

        // モック設定
        doReturn(result).when(inquiryUpdateLogic).execute(any(InquiryEntity.class));

        // 試験実行
        int actual = service.executeMemoRegist(inquiryEntity);

        // 戻り値及び呼び出し検証
        verify(inquiryUpdateLogic, times(1)).execute(any(InquiryEntity.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(4)
    public void executeStatusRegistTest() {

        // 想定値設定
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquiryStatus(HTypeInquiryStatus.SENDING);
        int result = 1;

        // モック設定
        doReturn(result).when(inquiryUpdateLogic).execute(any(InquiryEntity.class));

        // 試験実行
        int actual = service.executeStatusRegist(inquiryEntity);

        // 戻り値及び呼び出し検証
        verify(inquiryUpdateLogic, times(1)).execute(any(InquiryEntity.class));
        assertThat(actual).isEqualTo(result);
    }
}
