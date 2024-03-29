package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDisplayModifyLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryModifyLogic;

/**
 * Logic/Service移行： カテゴリ修正
 * 作成日：2021/03/22
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryModifyServiceTest {

    @Autowired
    CategoryModifyService service;

    /**
     * カテゴリ修正
     */
    @MockBean
    private CategoryModifyLogic categoryModifyLogic;

    /**
     * カテゴリ表示修正
     */
    @MockBean
    private CategoryDisplayModifyLogic categoryDisplayModigyLogic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        CategoryEntity categoryEntity = new CategoryEntity();
        CategoryDisplayEntity categoryDisplayEntity = new CategoryDisplayEntity();
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryEntity(categoryEntity);
        categoryDto.setCategoryDisplayEntity(categoryDisplayEntity);
        int result = 1;

        // モック設定
        doReturn(1).when(categoryModifyLogic).execute(any(CategoryEntity.class));
        doReturn(1).when(categoryDisplayModigyLogic).execute(any(CategoryDisplayEntity.class));

        // 試験実行
        int actual = service.execute(categoryDto);

        // 戻り値及び呼び出し検証
        verify(categoryModifyLogic, times(1)).execute(any(CategoryEntity.class));
        verify(categoryDisplayModigyLogic, times(1)).execute(any(CategoryDisplayEntity.class));
        assertThat(actual).isEqualTo(result);
    }
}
