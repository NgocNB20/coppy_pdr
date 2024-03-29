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
import org.springframework.boot.test.mock.mockito.SpyBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.impl.InquiryGetServiceImpl;

/**
 * Logic/Service移行:問い合わせメール送信サービス
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquirySendMailServiceTest {

    @Autowired
    InquirySendMailService service;

    @MockBean
    private InquiryGetServiceImpl inquiryGetService;

    @MockBean
    private InquiryGroupDao inquiryGroupDao;

    @SpyBean
    private InquiryGetLogic inquiryGetLogic;

    @Test
    @Order(1)
    public void execute1Test() {

        // 想定値設定
        Integer inquirySeq = 99;
        HTypeMailTemplateType mailTemplateType = HTypeMailTemplateType.GENERAL;
        InquiryDetailsDto inquiryDetailsDto = new InquiryDetailsDto();
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquiryMail("test@gmail.com");
        inquiryDetailsDto.setInquiryEntity(inquiryEntity);

        boolean result = true;

        // モック設定
        doReturn(inquiryDetailsDto).when(inquiryGetService).execute(any(Integer.class));

        // 試験実行
        boolean actual = service.execute(inquirySeq, mailTemplateType);

        // 戻り値及び呼び出し検証
        verify(inquiryGetService, times(1)).execute(any(Integer.class));
        verify(inquiryGetService, times(1)).setCommonInfo(any(CommonInfo.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(2)
    public void execute2Test() {

        // 想定値設定
        String inquiryCode = "1";
        HTypeMailTemplateType mailTemplateType = HTypeMailTemplateType.GENERAL;
        InquiryDetailsDto inquiryDetailsDto = new InquiryDetailsDto();
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquiryMail("test@gmail.com");
        inquiryDetailsDto.setInquiryEntity(inquiryEntity);

        InquiryGroupEntity inquiryGroupEntity = new InquiryGroupEntity();
        inquiryGroupEntity.setInquiryGroupName("AAA");

        boolean result = true;

        // モック設定
        doReturn(inquiryGroupEntity).when(inquiryGroupDao).getEntityByShopSeq(any(Integer.class), any(Integer.class));
        doReturn(inquiryDetailsDto).when(inquiryGetLogic).execute(any(Integer.class));

        // 試験実行
        boolean actual = service.execute(inquiryCode, mailTemplateType);

        // 戻り値及び呼び出し検証
        verify(inquiryGroupDao, times(1)).getEntityByShopSeq(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(3)
    public void sendMailOperatorTest() {

        // 想定値設定
        String inquiryCode = "1";
        String orderCode = "1";
        HTypeMailTemplateType mailTemplateType = HTypeMailTemplateType.GENERAL;
        InquiryDetailsDto inquiryDetailsDto = new InquiryDetailsDto();
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquiryMail("test@gmail.com");
        inquiryDetailsDto.setInquiryEntity(inquiryEntity);
        inquiryDetailsDto.setMemberInfoEntity(null);

        boolean result = true;

        // モック設定
        doReturn(inquiryDetailsDto).when(inquiryGetLogic).execute(any(String.class));

    }
}
