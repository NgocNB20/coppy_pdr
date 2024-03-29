package jp.co.hankyuhanshin.itec.hitmall.dao.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryDaoTest {

    @Autowired
    CategoryDao categoryDao;

    @Test
    @Order(1)
    public void insert() {
        CategoryEntity entity = makeCategoryEntity(999);
        int result = categoryDao.insert(entity);
        Assertions.assertNotNull(categoryDao.getEntity(999));

        result = categoryDao.delete(entity);
        Assertions.assertNull(categoryDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void getCategoryList() {
        List<Integer> categorySeqList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            categorySeqList.add(i);
            CategoryEntity entity = makeCategoryEntity(i);
            int result = categoryDao.insert(entity);
        }

        CategorySearchForDaoConditionDto dto = new CategorySearchForDaoConditionDto();
        dto.setShopSeq(999);
        dto.setCategorySeqList(categorySeqList);
        dto.setCategoryId("99");
        dto.setOpenStatus(HTypeOpenStatus.NO_OPEN);
        dto.setSiteType(HTypeSiteType.FRONT_PC);

        List<CategoryEntity> categoryEntityList = categoryDao.getCategoryList(dto);
        Assertions.assertEquals(categorySeqList.size(), categoryEntityList.size());

        for (int i : categorySeqList) {
            int result = categoryDao.delete(categoryDao.getEntity(i));
        }
    }

    @Test
    @Order(4)
    public void getSearchCategoryList() {
        CategorySearchForDaoConditionDto dto = new CategorySearchForDaoConditionDto();
        dto.setShopSeq(999);
        dto.setCategoryId("1");
        dto.setCategorySeqList(new ArrayList<Integer>());
        dto.setMaxHierarchical(99);
        dto.setSiteType(HTypeSiteType.FRONT_PC);
        dto.setOpenStatus(HTypeOpenStatus.NO_OPEN);
        dto.setNotInCategoryIdList(new ArrayList<String>());
        List<CategoryDetailsDto> categoryDetailsDtoList = categoryDao.getSearchCategoryList(dto, "99");
    }

    private CategoryEntity makeCategoryEntity(int categorySeq) {
        CategoryEntity entity = new CategoryEntity();
        entity.setCategorySeq(categorySeq);
        entity.setShopSeq(999);
        entity.setCategoryId("99");
        entity.setCategoryName("99");
        entity.setCategoryOpenStatusPC(HTypeOpenStatus.NO_OPEN);
        entity.setCategoryOpenStatusMB(HTypeOpenStatus.NO_OPEN);
        entity.setCategoryType(HTypeCategoryType.NORMAL);
        entity.setCategorySeqPath("1");
        entity.setOrderDisplay(1);
        entity.setRootCategorySeq(1);
        entity.setCategoryLevel(1);
        entity.setCategoryPath("1");
        entity.setManualDisplayFlag(HTypeManualDisplayFlag.OFF);
        entity.setVersionNo(1);
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        entity.setSiteMapFlag(HTypeSiteMapFlag.OFF);
        return entity;
    }
}
