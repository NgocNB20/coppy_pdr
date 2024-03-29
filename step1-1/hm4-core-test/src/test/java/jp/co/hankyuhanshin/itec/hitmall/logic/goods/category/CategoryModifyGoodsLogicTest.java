package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
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
public class CategoryModifyGoodsLogicTest {

    @Autowired
    CategoryModifyGoodsLogic categoryModifyGoodsLogic;

    @Test
    @Order(1)
    public void execute() {
        CategoryGoodsEntity categoryGoodsEntity = new CategoryGoodsEntity();
        categoryGoodsEntity.setCategorySeq(0);
        categoryGoodsEntity.setGoodsGroupSeq(0);
        categoryGoodsEntity.setOrderDisplay(0);
        categoryGoodsEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        categoryGoodsEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        int result = categoryModifyGoodsLogic.execute(categoryGoodsEntity);
        Assertions.assertEquals(0, result);
    }
}
