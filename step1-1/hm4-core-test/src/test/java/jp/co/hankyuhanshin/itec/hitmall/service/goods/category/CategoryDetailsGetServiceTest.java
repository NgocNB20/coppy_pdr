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
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDetailsGetLogic;

/**
 * Logic/Service移行：カテゴリ情報DTO取得
 * 作成日：2021/03/22
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryDetailsGetServiceTest {

    @Autowired
    CategoryDetailsGetService service;

    @MockBean
    private CategoryDetailsGetLogic logic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        CategorySearchForDaoConditionDto categorySearchForDaoConditionDto = new CategorySearchForDaoConditionDto();
        CategoryDetailsDto result = new CategoryDetailsDto();

        // モック設定
        doReturn(result).when(logic).execute(any(CategorySearchForDaoConditionDto.class));

        // 試験実行
        CategoryDetailsDto actual = service.execute(categorySearchForDaoConditionDto);

        // 戻り値及び呼び出し検証
        verify(logic, times(1)).execute(any(CategorySearchForDaoConditionDto.class));
        assertThat(actual).isEqualTo(result);
    }
}
