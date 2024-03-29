package jp.co.hankyuhanshin.itec.hitmall.batch.core.dao;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.entity.BatchJobExecutionParamsEntity;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BatchJobExecutionParamsDaoTest {

    @Autowired
    BatchJobExecutionParamsDao batchJobExecutionParamsDao;

    @Test
    @Order(1)
    public void getEntity() {
        BatchJobExecutionParamsEntity entity = batchJobExecutionParamsDao.getEntity(10L);
        Assertions.assertEquals(10L, entity.getJobExecutionId());
    }

}
