package jp.co.hankyuhanshin.itec.hitmall.dao.goods.category;

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

import java.sql.Timestamp;
import java.util.ArrayList;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryGoodsDaoTest {

    @Autowired
    CategoryGoodsDao categoryGoodsDao;

    @Test
    @Order(1)
    public void insert() {
        CategoryGoodsEntity entity = makeCategoryGoodsEntity();
        int result = categoryGoodsDao.insert(entity);
        Assertions.assertNotNull(categoryGoodsDao.getCategoryGoodsList(new ArrayList<>(999)));

        result = categoryGoodsDao.deleteByCategorySeq(999);
        Assertions.assertEquals(0, categoryGoodsDao.getCategoryGoodsList(new ArrayList<>(999)).size());
    }

    private CategoryGoodsEntity makeCategoryGoodsEntity() {
        CategoryGoodsEntity entity = new CategoryGoodsEntity();
        entity.setCategorySeq(999);
        entity.setGoodsGroupSeq(999);
        entity.setOrderDisplay(1);
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
}
