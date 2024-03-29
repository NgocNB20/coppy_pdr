package jp.co.hankyuhanshin.itec.hitmall.logic.order.member;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.member.SimultaneousOrderExclusionDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.member.SimultaneousOrderExclusionEntity;
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
public class SimultaneousOrderExclusionRegistLogicTest {

    @Autowired
    SimultaneousOrderExclusionRegistLogic simultaneousOrderExclusionRegistLogic;

    @MockBean
    SimultaneousOrderExclusionDao simultaneousOrderExclusionDao;

    @Test
    @Order(1)
    public void execute() {
        SimultaneousOrderExclusionEntity simultaneousOrderExclusionEntity = new SimultaneousOrderExclusionEntity();
        simultaneousOrderExclusionEntity.setMemberInfoSeq(0);
        simultaneousOrderExclusionEntity.setVersionNo(0);
        simultaneousOrderExclusionEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        simultaneousOrderExclusionEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        doReturn(1).when(simultaneousOrderExclusionDao).insert(any(SimultaneousOrderExclusionEntity.class));

        int result = simultaneousOrderExclusionRegistLogic.execute(simultaneousOrderExclusionEntity);

        verify(simultaneousOrderExclusionDao, times(1)).insert(any(SimultaneousOrderExclusionEntity.class));
        Assertions.assertEquals(1, result);
    }
}
