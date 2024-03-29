package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.password;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateDefaultFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.StringTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailRegistLogic;
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

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PasswordResetSendMailLogicTest {

    @Autowired
    PasswordResetSendMailLogic passwordResetSendMailLogic;

    @MockBean
    MemberInfoGetLogic memberInfoGetLogic;

    @MockBean
    ConfirmMailRegistLogic confirmMailRegistLogic;

    @MockBean
    MailTemplateGetLogic mailTemplateGetLogic;

    @MockBean
    StringTransformHelper transformer;

    @Test
    @Order(1)
    public void execute() {

        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoUniqueId("1001memberinfomail");
        memberInfoEntity.setMemberInfoMail("memberInfoMail");
        memberInfoEntity.setMemberInfoTel("9999999");
        memberInfoEntity.setShopSeq(1001);
        memberInfoEntity.setMemberInfoSeq(999);

        MailTemplateEntity mailTemplateEntity = new MailTemplateEntity();
        mailTemplateEntity.setMailTemplateSeq(1010);
        mailTemplateEntity.setShopSeq(1001);
        mailTemplateEntity.setMailTemplateName("受注決済督促");
        mailTemplateEntity.setMailTemplateType(HTypeMailTemplateType.SETTLEMENT_REMINDER);
        mailTemplateEntity.setMailTemplateDefaultFlag(HTypeMailTemplateDefaultFlag.ON);
        mailTemplateEntity.setMailTemplateSubject("【HIT-MALL Ver.4 公式オンラインショップ】お支払い期限のご案内");
        mailTemplateEntity.setMailTemplateFromAddress("info+stg-hm4@e4-granada.itechh.ne.jp");
        mailTemplateEntity.setMailTemplateBccAddress("hitmall-pkg-process+stg-hm4@e4-granada.itechh.ne.jp");

        Map<String, String> mailContentsMap = new HashMap<>();
        mailContentsMap.put("VALUE", "6877af786bd286eb63172908b91cb439");

        boolean expect = true;

        doReturn(memberInfoEntity).when(memberInfoGetLogic).execute(any(String.class));
        doReturn(1).when(confirmMailRegistLogic).execute(any(ConfirmMailEntity.class));
        doReturn(mailTemplateEntity).when(this.mailTemplateGetLogic)
                                    .execute(any(Integer.class), any(HTypeMailTemplateType.class));
        doReturn(mailContentsMap).when(transformer).toValueMap((Object) null);

        boolean actual = passwordResetSendMailLogic.execute("memberInfoMail", "9999999");

        Assertions.assertEquals(expect, actual);
    }
}
