package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.loginadvisability.LoginAdvisabilityDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginAdvisabilityGetLogicTest {

    @Autowired
    LoginAdvisabilityGetLogic loginAdvisabilityGetLogic;

    @MockBean
    LoginAdvisabilityDao loginAdvisabilityDao;

    @Test
    @Order(1)
    public void execute() {

        LoginAdvisabilityEntity entity = new LoginAdvisabilityEntity();
        entity.setLoginAdvisabilityseq(123);

        doReturn(entity).when(loginAdvisabilityDao)
                        .getEntityByLoginAdvisability(any(String.class), any(String.class), any(String.class),
                                                      any(String.class), any(String.class)
                                                     );

        LoginAdvisabilityEntity loginAdvisabilityEntityResult = new LoginAdvisabilityEntity();
        loginAdvisabilityEntityResult.setLoginAdvisabilityseq(123);

        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoSeq(123);
        memberInfoEntity.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);
        memberInfoEntity.setApproveStatus(HTypeApproveStatus.ON);
        memberInfoEntity.setOnlineLoginAdvisability(HTypeOnlineLoginAdvisability.REGULAR_ACCOUNT);
        memberInfoEntity.setMemberListType(HTypeMemberListType.ONLINE_GENERAL_CUSTOMER);
        memberInfoEntity.setAccountingType(HTypeAccountingType.CUSTOMER);

        LoginAdvisabilityEntity result =
                        loginAdvisabilityGetLogic.getLoginAdvisabilityPdrEntityByMemberInfo(memberInfoEntity);

        verify(loginAdvisabilityDao, times(1)).getEntityByLoginAdvisability("0", "1", "1", "1", "1");
        Assertions.assertEquals(loginAdvisabilityEntityResult, result);
    }
}
