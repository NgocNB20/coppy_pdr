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
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;

/**
 * Logic/Service移行：カテゴリ削除
 * 作成日：2021/03/22
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryRemoveServiceTest {

    @Autowired
    CategoryRemoveService service;

    /**
     * カテゴリツリー構造取得
     */
    @MockBean
    private CategoryTreeNodeGetService categoryTreeNodeGetService;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        String categoryId = "ID";
        CategoryDto categoryDto = null;
        int result = 0;

        // モック設定
        doReturn(categoryDto).when(categoryTreeNodeGetService)
                             .execute(any(CategorySearchForDaoConditionDto.class), any());

        // 試験実行
        int actual = service.execute(categoryId);

        // 戻り値及び呼び出し検証
        verify(categoryTreeNodeGetService, times(1)).execute(any(CategorySearchForDaoConditionDto.class), any());
        assertThat(actual).isEqualTo(result);
    }
}
