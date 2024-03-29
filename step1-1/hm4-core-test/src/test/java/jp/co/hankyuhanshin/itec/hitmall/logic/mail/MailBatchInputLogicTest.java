package jp.co.hankyuhanshin.itec.hitmall.logic.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.batch.BatchJobEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MailBatchInputLogicTest {

    @Autowired
    MailBatchInputInsertLogic mailBatchInputInsertLogic;

    @Autowired
    MailBatchInputSelectLogic mailBatchInputSelectLogic;

    @Test
    @Order(1)
    public void insert() throws JsonProcessingException {
        List<MailDto> mailDtoListInput = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            MailDto mailDto = new MailDto();

            mailDto.setMailTemplateType(HTypeMailTemplateType.SETTLEMENT_EXPIRATION_NOTIFICATION);
            mailDto.setSubject("Test[" + i + "]");
            mailDto.setFrom("test_from@itec.co.jp");
            mailDto.setToList(Collections.singletonList("test_from@itec.co.jp"));
            mailDto.setMailContentsMap(new HashMap<>());
            mailDto.setAttachFileFlag(false);

            mailDtoListInput.add(mailDto);
        }
        int requestCode = mailBatchInputInsertLogic.execute(mailDtoListInput);

        Stream<BatchJobEntity> mailDtoListOutput = mailBatchInputSelectLogic.execute(requestCode);

        Iterator<BatchJobEntity> mailDtoIterator = mailDtoListOutput.iterator();

        while (mailDtoIterator.hasNext()) {
            BatchJobEntity entity = mailDtoIterator.next();
            System.out.println(entity.getRequestData());
        }
    }
}
