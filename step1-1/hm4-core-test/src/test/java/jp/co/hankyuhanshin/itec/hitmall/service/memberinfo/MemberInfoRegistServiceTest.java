package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsMergeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailDeleteLogic;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoRegistServiceTest {

    @Autowired
    MemberInfoRegistService memberInfoRegistService;

    @MockBean
    ConfirmMailDeleteLogic confirmMailDeleteLogic;

    @MockBean
    MemberInfoGetLogic memberInfoGetLogic;

    @MockBean
    MemberInfoUpdateLogic memberInfoUpdateLogic;

    @MockBean
    MemberInfoRegistLogic memberInfoRegistLogic;

    @MockBean
    CartGoodsMergeLogic cartGoodsMergeLogic;

    @MockBean
    MemberInfoDao memberInfoDao;

    @MockBean
    PasswordEncoder encoder;

    @MockBean
    MemberInfoDataCheckLogic memberInfoDataCheckLogic;

    @Test
    @Order(1)
    public void executeNo1() {

        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        String confirmMailPassword = "password";

        doReturn(0).when(confirmMailDeleteLogic).execute(anyString());
        doReturn(new MemberInfoEntity()).when(memberInfoGetLogic).executeForProvisional(anyString(), anyString());
        doReturn(1).when(memberInfoUpdateLogic).execute(any(MemberInfoEntity.class));
        doReturn(1).when(memberInfoDao).getMemberInfoSeqNextVal();
        doReturn(1).when(cartGoodsMergeLogic).execute(anyInt(), anyInt(), anyString(), anyInt());
        doReturn("abc").when(encoder).encode(anyString());
        doReturn(1).when(memberInfoRegistLogic).execute(any());

        memberInfoRegistService.execute(memberInfoEntity, confirmMailPassword);

        verify(confirmMailDeleteLogic, times(1)).execute(anyString());
    }

    @Test
    @Order(2)
    public void executeNo2() {
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        boolean cartMerge = false;

        doReturn(new MemberInfoEntity()).when(memberInfoGetLogic).executeForProvisional(anyString(), anyString());
        doReturn(1).when(memberInfoUpdateLogic).execute(any(MemberInfoEntity.class));
        doReturn(1).when(memberInfoDao).getMemberInfoSeqNextVal();
        doReturn("abc").when(encoder).encode(anyString());
        doReturn(1).when(memberInfoRegistLogic).execute(any());

        memberInfoRegistService.executeHm(memberInfoEntity, cartMerge);
    }

    @Test
    @Order(3)
    public void executeNo3() {
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        boolean onlineFlg = false;

        doReturn("abc").when(encoder).encode(anyString());
        doReturn(1).when(memberInfoRegistLogic).execute(any(MemberInfoEntity.class));
        doNothing().when(memberInfoDataCheckLogic).execute(any(MemberInfoEntity.class));

        memberInfoRegistService.execute(memberInfoEntity, onlineFlg);
    }
}
