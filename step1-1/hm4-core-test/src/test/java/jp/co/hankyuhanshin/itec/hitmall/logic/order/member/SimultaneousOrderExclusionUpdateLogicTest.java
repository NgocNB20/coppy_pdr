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
public class SimultaneousOrderExclusionUpdateLogicTest {

    @Autowired
    SimultaneousOrderExclusionUpdateLogic simultaneousOrderExclusionUpdateLogic;

    @MockBean
    SimultaneousOrderExclusionDao simultaneousOrderExclusionDao;

    @Test
    @Order(1)
    public void execute() {
        SimultaneousOrderExclusionEntity entity = new SimultaneousOrderExclusionEntity();
        entity.setMemberInfoSeq(0);
        entity.setVersionNo(0);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        doReturn(1).when(simultaneousOrderExclusionDao).update(any(SimultaneousOrderExclusionEntity.class));

        int result = simultaneousOrderExclusionUpdateLogic.execute(entity);

        verify(simultaneousOrderExclusionDao, times(1)).update(any(SimultaneousOrderExclusionEntity.class));
        Assertions.assertEquals(1, result);
    }
}
