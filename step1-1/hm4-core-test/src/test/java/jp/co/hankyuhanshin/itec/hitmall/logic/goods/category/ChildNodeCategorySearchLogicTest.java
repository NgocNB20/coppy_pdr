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
public class ChildNodeCategorySearchLogicTest {

    @Autowired
    ChildNodeCategorySearchLogic childNodeCategorySearchLogic;

    @Test
    @Order(1)
    public void execute() {
        CategorySearchForDaoConditionDto conditionDto = new CategorySearchForDaoConditionDto();
        conditionDto.setShopSeq(0);
        conditionDto.setCategoryId("");
        List<Integer> categorySeqList = new ArrayList<>();
        categorySeqList.add(999);
        conditionDto.setCategorySeqList(categorySeqList);
        conditionDto.setMaxHierarchical(0);
        conditionDto.setSiteType(HTypeSiteType.FRONT_PC);
        conditionDto.setOpenStatus(HTypeOpenStatus.NO_OPEN);
        conditionDto.setNotInCategoryIdList(new ArrayList<>());

        List<CategoryDetailsDto> categoryDetailsDtoList = childNodeCategorySearchLogic.execute(conditionDto, "1");
        Assertions.assertEquals(1, categoryDetailsDtoList.size());
    }
}
