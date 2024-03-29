package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
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
public class CategoryModifyCategoryPathLogicTest {

    @Autowired
    CategoryModifyCategoryPathLogic categoryModifyCategoryPathLogic;

    @Test
    @Order(1)
    public void execute() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategorySeq(10000000);
        categoryEntity.setShopSeq(0);
        categoryEntity.setCategoryId("");
        categoryEntity.setCategoryName("");
        categoryEntity.setCategoryOpenStatusPC(HTypeOpenStatus.NO_OPEN);
        categoryEntity.setCategoryOpenStatusMB(HTypeOpenStatus.NO_OPEN);
        categoryEntity.setCategoryOpenStartTimePC(new Timestamp(new java.util.Date().getTime()));
        categoryEntity.setCategoryOpenEndTimePC(new Timestamp(new java.util.Date().getTime()));
        categoryEntity.setCategoryOpenStartTimeMB(new Timestamp(new java.util.Date().getTime()));
        categoryEntity.setCategoryOpenEndTimeMB(new Timestamp(new java.util.Date().getTime()));
        categoryEntity.setCategoryType(HTypeCategoryType.NORMAL);
        categoryEntity.setCategorySeqPath("10000000");
        categoryEntity.setOrderDisplay(0);
        categoryEntity.setRootCategorySeq(0);
        categoryEntity.setCategoryLevel(0);
        categoryEntity.setCategoryPath("/000");
        categoryEntity.setManualDisplayFlag(HTypeManualDisplayFlag.OFF);
        categoryEntity.setVersionNo(0);
        categoryEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        categoryEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        categoryEntity.setSiteMapFlag(HTypeSiteMapFlag.ON);
        categoryEntity.setOpenGoodsCountPC(0);
        categoryEntity.setOpenGoodsCountMB(0);

        int result = categoryModifyCategoryPathLogic.execute(categoryEntity);
        Assertions.assertEquals(1, result);
    }
}
