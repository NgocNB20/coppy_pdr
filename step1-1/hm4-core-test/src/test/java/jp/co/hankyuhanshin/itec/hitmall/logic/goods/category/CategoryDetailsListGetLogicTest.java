package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import java.util.ArrayList;

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

import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryDetailsListGetLogicTest {

    @Autowired
    CategoryDetailsListGetLogic categoryDetailsListGetLogic;

    @Test
    @Order(1)
    public void execute() {
        CategorySearchForDaoConditionDto conditionDto = new CategorySearchForDaoConditionDto();
        conditionDto.setShopSeq(1001);
        conditionDto.setCategoryId("top");
        conditionDto.setCategorySeqList(new ArrayList<Integer>());
        conditionDto.setMaxHierarchical(0);
        conditionDto.setSiteType(HTypeSiteType.FRONT_MB);
        conditionDto.setOpenStatus(HTypeOpenStatus.NO_OPEN);
        conditionDto.setNotInCategoryIdList(new ArrayList<String>());

        List<CategoryDetailsDto> categoryDetailsDtoList = categoryDetailsListGetLogic.execute(conditionDto);
        Assertions.assertEquals(0, categoryDetailsDtoList.size());
    }
}
