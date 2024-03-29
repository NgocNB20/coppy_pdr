package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
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
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoUpdateLogicTest {

    @Autowired
    MemberInfoUpdateLogic memberInfoUpdateLogic;

    @MockBean
    MemberInfoDao memberInfoDao;

    @Test
    @Order(1)
    public void execute() {
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoSeq(999);

        doReturn(1).when(memberInfoDao).update(any(MemberInfoEntity.class));

        int result = memberInfoUpdateLogic.execute(memberInfoEntity);

        verify(memberInfoDao, times(1)).update(memberInfoEntity);
        Assertions.assertEquals(1, result);
    }
}
