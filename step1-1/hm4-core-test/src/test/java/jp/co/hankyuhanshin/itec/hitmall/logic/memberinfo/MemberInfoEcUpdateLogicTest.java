package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoDataCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
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

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoEcUpdateLogicTest {

    @Autowired
    MemberInfoEcUpdateLogic memberInfoEcUpdateLogic;

    @MockBean
    MemberInfoDataCheckService memberInfoDataCheckService;

    @MockBean
    MemberInfoUpdateService memberInfoUpdateService;

    @MockBean
    DateUtility dateUtility;

    @Test
    @Order(1)
    public void execute() {

        Timestamp ts = new Timestamp(System.currentTimeMillis());

        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoSeq(999);

        doReturn(ts).when(dateUtility).getCurrentTime();
        doNothing().when(memberInfoDataCheckService).execute(any(MemberInfoEntity.class));
        doReturn(1).when(memberInfoUpdateService).execute(any(MemberInfoEntity.class));

        memberInfoEcUpdateLogic.execute(memberInfoEntity);

    }
}
