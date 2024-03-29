package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

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
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetGoodsOrderListLogic;

/**
 * Logic/Service移行：カテゴリに紐づくカテゴリ登録商品を取得
 * 作成日：2021/03/22
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryGetGoodsOrderListServiceTest {

    @Autowired
    CategoryGetGoodsOrderListService service;

    @MockBean
    private CategoryGetGoodsOrderListLogic logic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer categorySeq = 1;
        Integer goodsGroupSeq = 1;
        List<CategoryGoodsEntity> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(logic)
                        .execute(any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class),
                                 any(Boolean.class)
                                );

        // 試験実行
        List<CategoryGoodsEntity> actual = service.execute(categorySeq, goodsGroupSeq);

        // 戻り値及び呼び出し検証
        verify(logic, times(1)).execute(1, 1, null, null, true);
        assertThat(actual).isEqualTo(result);
    }
}
