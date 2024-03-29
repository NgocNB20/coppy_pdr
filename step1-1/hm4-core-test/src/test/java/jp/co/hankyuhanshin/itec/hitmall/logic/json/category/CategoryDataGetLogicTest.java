package jp.co.hankyuhanshin.itec.hitmall.logic.json.category;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipleCategory.ajax.MultipleCategoryGoodsDetailsDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryDataGetLogicTest {
    @Autowired
    CategoryDataGetLogic categoryDataGetLogic;

    @Test
    @Order(1)
    public void execute() {

        CategorySearchForDaoConditionDto conditionDto = new CategorySearchForDaoConditionDto();
        List<CategoryDetailsDto> result = categoryDataGetLogic.execute(conditionDto, 4, 4, HTypeCategoryType.NORMAL);
        Assertions.assertEquals(0, result.size());
    }
}
