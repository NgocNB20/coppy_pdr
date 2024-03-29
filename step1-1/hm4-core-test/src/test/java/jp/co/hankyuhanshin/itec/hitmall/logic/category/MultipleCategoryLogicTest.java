package jp.co.hankyuhanshin.itec.hitmall.logic.category;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipleCategory.ajax.MultipleCategoryGoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionGetLogic;
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

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MultipleCategoryLogicTest {

    @Autowired
    MultipleCategoryLogic multipleCategoryLogic;

    @Test
    @Order(1)
    public void execute() {
        Map<String, List<MultipleCategoryGoodsDetailsDto>> result =
                        multipleCategoryLogic.getCategoryMap("1", "1", "1", "1", "1", "1", "1");
        Assertions.assertEquals(0, result.size());
    }
}
