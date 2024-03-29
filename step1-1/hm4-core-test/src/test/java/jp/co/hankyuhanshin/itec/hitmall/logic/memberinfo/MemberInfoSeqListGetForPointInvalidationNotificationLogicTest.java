package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoSeqListGetForPointInvalidationNotificationLogicTest {

    @Autowired
    MemberInfoSeqListGetForPointInvalidationNotificationLogic memberInfoSeqListGetForPointInvalidationNotificationLogic;

    @MockBean
    MemberInfoDao memberInfoDao;

    @Test
    @Order(1)
    public void execute() {
        List<Integer> seqList = new ArrayList<>();
        seqList.add(123);

        doReturn(seqList).when(memberInfoDao).getSeqListForPointInvalidation(any(Timestamp.class), any(boolean.class));

        List<Integer> result = memberInfoSeqListGetForPointInvalidationNotificationLogic.execute(
                        new Timestamp(new java.util.Date().getTime()));

        verify(memberInfoDao, times(1)).getSeqListForPointInvalidation(any(Timestamp.class), any(boolean.class));
        Assertions.assertEquals(seqList, result);
    }
}
