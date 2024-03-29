package jp.co.hankyuhanshin.itec.hitmall.logic.order.member;

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

import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SimultaneousOrderExclusionGetLogicTest {

    @Autowired
    SimultaneousOrderExclusionGetLogic simultaneousOrderExclusionGetLogic;

    @MockBean
    SimultaneousOrderExclusionDao simultaneousOrderExclusionDao;

    @Test
    @Order(1)
    public void execute() {
        SimultaneousOrderExclusionEntity result = new SimultaneousOrderExclusionEntity();
        result.setMemberInfoSeq(999);

        doReturn(result).when(simultaneousOrderExclusionDao).getEntity(any(Integer.class));
        SimultaneousOrderExclusionEntity entity = simultaneousOrderExclusionGetLogic.execute(999);

        verify(simultaneousOrderExclusionDao, times(1)).getEntity(any(Integer.class));
        Assertions.assertEquals(result, entity);
    }
}
