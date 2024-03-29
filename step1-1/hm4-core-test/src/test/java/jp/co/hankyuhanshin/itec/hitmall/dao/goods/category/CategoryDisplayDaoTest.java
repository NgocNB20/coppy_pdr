package jp.co.hankyuhanshin.itec.hitmall.dao.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryDisplayDaoTest {

    @Autowired
    CategoryDisplayDao categoryDisplayDao;

    @Test
    @Order(1)
    public void insert() {
        CategoryDisplayEntity entity = makeCategoryDisplayEntity();
        int result = categoryDisplayDao.insert(entity);
        Assertions.assertNotNull(categoryDisplayDao.getEntity(999));

        result = categoryDisplayDao.delete(entity);
        Assertions.assertNull(categoryDisplayDao.getEntity(999));
    }

    private CategoryDisplayEntity makeCategoryDisplayEntity() {
        CategoryDisplayEntity entity = new CategoryDisplayEntity();
        entity.setCategorySeq(999);
        entity.setCategoryNamePC("test");
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
}
