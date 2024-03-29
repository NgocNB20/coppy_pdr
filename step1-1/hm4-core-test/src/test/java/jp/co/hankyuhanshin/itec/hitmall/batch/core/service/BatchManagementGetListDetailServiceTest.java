package jp.co.hankyuhanshin.itec.hitmall.batch.core.service;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementSearchConditionDto;
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

import java.sql.Timestamp;
import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BatchManagementGetListDetailServiceTest {

    @Autowired
    BatchManagementGetListDetailService service;

    @Test
    @Order(1)
    public void execute() {
        BatchManagementSearchConditionDto conditionDto = new BatchManagementSearchConditionDto();
        conditionDto.setBatchName("BATCH_MAIL");
        conditionDto.setCreateTime(Timestamp.valueOf("2021-04-01 11:11:11.0"));
        conditionDto.setEndTime(Timestamp.valueOf("2021-04-30 11:11:11.0"));
        conditionDto.setStatus("COMPLETED");

        List<BatchManagementDetailDto> result = service.execute(conditionDto);
        Assertions.assertNotNull(result);
    }
}
