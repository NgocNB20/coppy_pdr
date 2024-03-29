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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryGoodsRegistLogicTest {

    @Autowired
    CategoryGoodsRegistLogic categoryGoodsRegistLogic;

    @Autowired
    CategoryGoodsRemoveLogic categoryGoodsRemoveLogic;

    @Test
    @Order(1)
    public void categoryGoodsRegistLogicExecute() {
        List<CategoryGoodsEntity> categoryGoodsEntityList = new ArrayList<>();
        CategoryGoodsEntity entity = new CategoryGoodsEntity();
        entity.setCategorySeq(0);
        entity.setGoodsGroupSeq(0);
        entity.setOrderDisplay(0);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        categoryGoodsEntityList.add(entity);

        Map<String, Integer> map = categoryGoodsRegistLogic.execute(0, categoryGoodsEntityList);
        Assertions.assertEquals(2, map.size());
    }

    @Test
    @Order(2)
    public void categoryGoodsRemoveLogicExecute() {
        int result = categoryGoodsRemoveLogic.execute(0);
        Assertions.assertEquals(1, result);
    }
}
