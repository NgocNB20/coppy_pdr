package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryGoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryGoodsSearchForDaoConditionDto;
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

import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryGetGoodsListLogicTest {

    @Autowired
    CategoryGetGoodsListLogic categoryGetGoodsListLogic;

    @Test
    @Order(1)
    public void execute() {
        CategoryGoodsSearchForDaoConditionDto conditionDto = new CategoryGoodsSearchForDaoConditionDto();
        conditionDto.setCategorySeq(1);

        List<CategoryGoodsDetailsDto> categoryGoodsDetailsDtoList = categoryGetGoodsListLogic.execute(conditionDto);
        Assertions.assertEquals(0, categoryGoodsDetailsDtoList.size());
    }
}
