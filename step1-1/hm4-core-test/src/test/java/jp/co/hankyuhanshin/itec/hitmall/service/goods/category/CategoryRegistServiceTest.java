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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDisplayRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetMaxOrderdisplayLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.NewCategorySeqGetLogic;

/**
 * Logic/Service移行：カテゴリ登録
 * 作成日：2021/03/22
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryRegistServiceTest {

    @Autowired
    CategoryRegistService service;

    /**
     * カテゴリSEQ採番
     */
    @MockBean
    private NewCategorySeqGetLogic newCategorySeqGetLogic;

    /**
     * カテゴリ情報取得
     */
    @MockBean
    private CategoryGetLogic categoryGetLogic;

    /**
     * カテゴリ情報取得
     */
    @MockBean
    private CategoryGetMaxOrderdisplayLogic categoryGetMaxOrderdisplayLogic;

    /**
     * カテゴリ登録
     */
    @MockBean
    private CategoryRegistLogic categoryRegistLogic;

    /**
     * カテゴリ表示登録
     */
    @MockBean
    private CategoryDisplayRegistLogic categoryDisplayRegistLogic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        CategoryDisplayEntity categoryDisplayEntity = new CategoryDisplayEntity();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryLevel(1);
        categoryEntity.setCategorySeqPath("PATH");
        categoryEntity.setCategoryPath("PA");
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryEntity(categoryEntity);
        categoryDto.setCategoryDisplayEntity(categoryDisplayEntity);
        String parentCategoryId = "ID";

        int result = 1;

        // モック設定
        doReturn(1).when(newCategorySeqGetLogic).execute();
        doReturn(categoryEntity).when(categoryGetLogic).execute(any(Integer.class), any(String.class));
        doReturn(1).when(categoryGetMaxOrderdisplayLogic)
                   .execute(any(Integer.class), any(String.class), any(Integer.class));
        doReturn(1).when(categoryRegistLogic).execute(any(CategoryEntity.class));
        doReturn(1).when(categoryDisplayRegistLogic).execute(any(CategoryDisplayEntity.class));

        // 試験実行
        int actual = service.execute(categoryDto, parentCategoryId);

        // 戻り値及び呼び出し検証
        verify(newCategorySeqGetLogic, times(1)).execute();
        verify(categoryGetLogic, times(1)).execute(any(Integer.class), any(String.class));
        verify(categoryGetMaxOrderdisplayLogic, times(1)).execute(
                        any(Integer.class), any(String.class), any(Integer.class));
        verify(categoryRegistLogic, times(1)).execute(any(CategoryEntity.class));
        verify(categoryDisplayRegistLogic, times(1)).execute(any(CategoryDisplayEntity.class));
        assertThat(actual).isEqualTo(result);
    }
}
