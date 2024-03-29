package jp.co.hankyuhanshin.itec.hitmall.batch.core.dao;

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

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.entity.BatchStepExecutionEntity;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BatchStepExecutionDaoTest {

    @Autowired
    BatchStepExecutionDao batchStepExecutionDao;

    @Test
    @Order(1)
    public void getEntity() {
        BatchStepExecutionEntity entity = batchStepExecutionDao.getEntity(1);
        Assertions.assertEquals(1, entity.getStepExecutionId());
    }

}
