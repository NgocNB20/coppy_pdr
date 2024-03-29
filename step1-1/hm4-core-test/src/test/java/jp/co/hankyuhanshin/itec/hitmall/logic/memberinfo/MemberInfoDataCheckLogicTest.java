package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoDataCheckLogicTest {

    @Autowired
    MemberInfoDataCheckLogic memberInfoDataCheckLogic;

    @MockBean
    MemberInfoDao memberInfoDao;

    @Test
    @Order(1)
    public void execute() {
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoSeq(0);
        memberInfoEntity.setMemberInfoUniqueId("test");

        doReturn(memberInfoEntity).when(memberInfoDao).getEntityByMemberInfoUniqueId(any(String.class));

        memberInfoDataCheckLogic.execute(memberInfoEntity);

        verify(memberInfoDao, times(1)).getEntityByMemberInfoUniqueId(memberInfoEntity.getMemberInfoUniqueId());
    }

    @Test
    @Order(2)
    public void executeTest() {
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();

        doReturn(memberInfoEntity).when(memberInfoDao)
                                  .getEntityByMailStatus(any(String.class), any(HTypeMemberInfoStatus.class));

        memberInfoDataCheckLogic.execute(memberInfoEntity.getMemberInfoMail(), memberInfoEntity.getMemberInfoStatus());

        verify(memberInfoDao, times(1)).getEntityByMailStatus(
                        memberInfoEntity.getMemberInfoMail(), memberInfoEntity.getMemberInfoStatus());
    }

    @Test
    @Order(3)
    public void executeTest_whenExceptionThrown() {
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoMail("test");
        memberInfoEntity.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);

        doReturn(memberInfoEntity).when(memberInfoDao)
                                  .getEntityByMailStatus(any(String.class), any(HTypeMemberInfoStatus.class));

        AppLevelListException exception = assertThrows(AppLevelListException.class, () -> {
            memberInfoDataCheckLogic.execute(
                            memberInfoEntity.getMemberInfoMail(), memberInfoEntity.getMemberInfoStatus());
        });

        assertTrue(exception.getErrorList().size() > 0);
    }
}
