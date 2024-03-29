package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import java.util.ArrayList;
import java.util.List;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
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
public class CategoryDetailsGetLogicTest {

    @Autowired
    CategoryDetailsGetLogic categoryDetailsGetLogic;

    @Test
    @Order(1)
    public void execute() {
        CategorySearchForDaoConditionDto conditionDto = new CategorySearchForDaoConditionDto();
        conditionDto.setShopSeq(999);
        conditionDto.setCategoryId("1");
        conditionDto.setCategorySeqList(new ArrayList<Integer>());
        conditionDto.setMaxHierarchical(0);
        conditionDto.setSiteType(HTypeSiteType.FRONT_PC);
        conditionDto.setOpenStatus(HTypeOpenStatus.NO_OPEN);
        conditionDto.setNotInCategoryIdList(new ArrayList<String>());

        CategoryDetailsDto categoryDetailsDto = categoryDetailsGetLogic.execute(conditionDto);
        Assertions.assertNull(categoryDetailsDto);
    }

    @Test
    @Order(2)
    public void getCategoryDetailsDtoList() {
        List<String> categoryIdList = new ArrayList<>();
        categoryIdList.add("1");
        List<CategoryDetailsDto> categoryDetailsDtoList =
                        categoryDetailsGetLogic.getCategoryDetailsDtoList(categoryIdList);
        Assertions.assertEquals(0, categoryDetailsDtoList.size());
    }
}
