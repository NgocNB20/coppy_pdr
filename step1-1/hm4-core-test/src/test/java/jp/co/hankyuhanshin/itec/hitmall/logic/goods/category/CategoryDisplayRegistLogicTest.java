package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import java.sql.Timestamp;

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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryDisplayRegistLogicTest {

    @Autowired
    CategoryDisplayRegistLogic categoryDisplayRegistLogic;

    @Autowired
    CategoryDisplayRemoveLogic categoryDisplayRemoveLogic;

    @Test
    @Order(1)
    public void categoryDisplayRegistLogicExecute() {
        CategoryDisplayEntity categoryDisplayEntity = new CategoryDisplayEntity();
        categoryDisplayEntity.setCategorySeq(10000999);
        categoryDisplayEntity.setCategoryNamePC("");
        categoryDisplayEntity.setCategoryNameMB("");
        categoryDisplayEntity.setCategoryNotePC("");
        categoryDisplayEntity.setCategoryNoteMB("");
        categoryDisplayEntity.setFreeTextPC("");
        categoryDisplayEntity.setFreeTextSP("バ");
        categoryDisplayEntity.setFreeTextMB("");
        categoryDisplayEntity.setMetaDescription("");
        categoryDisplayEntity.setMetaKeyword("");
        categoryDisplayEntity.setCategoryImagePC("");
        categoryDisplayEntity.setCategoryImageSP("");
        categoryDisplayEntity.setCategoryImageMB("");
        categoryDisplayEntity.setRegistTime(Timestamp.valueOf("2021-02-08 1:16:27.0"));
        categoryDisplayEntity.setUpdateTime(Timestamp.valueOf("2021-03-08 1:16:27.0"));

        int result = categoryDisplayRegistLogic.execute(categoryDisplayEntity);
        Assertions.assertEquals(1, result);
    }

    @Test
    @Order(2)
    public void categoryDisplayRemoveLogicExecute() {
        CategoryDisplayEntity categoryDisplayEntity = new CategoryDisplayEntity();
        categoryDisplayEntity.setCategorySeq(10000999);
        int result = categoryDisplayRemoveLogic.execute(categoryDisplayEntity);
        Assertions.assertEquals(1, result);
    }
}
