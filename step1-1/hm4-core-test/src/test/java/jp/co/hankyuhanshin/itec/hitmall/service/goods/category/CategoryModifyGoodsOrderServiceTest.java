package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGoodsTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryModifyGoodsLogic;

/**
 * Logic/Service移行：カテゴリ内商品の並び順変更
 * 作成日：2021/03/22
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryModifyGoodsOrderServiceTest {

    @Autowired
    CategoryModifyGoodsOrderService service;

    /**
     * カテゴリ登録商品テーブルロック取得
     */
    @MockBean
    private CategoryGoodsTableLockLogic categoryGoodsTableLockLogic;

    /**
     * カテゴリ登録商品情報取得
     */
    @MockBean
    private CategoryGetGoodsOrderListLogic categoryGetGoodsOrderListLogic;

    /**
     * カテゴリ商品修正
     */
    @MockBean
    private CategoryModifyGoodsLogic categoryModifyGoodsLogic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer categorySeq = 1;
        Integer fromOrderDisplay = 1;
        Integer toOrderDisplay = 1;
        CategoryGoodsEntity categoryGoodsEntity = new CategoryGoodsEntity();
        categoryGoodsEntity.setCategorySeq(1);
        CategoryGoodsEntity categoryGoodsEntity2 = new CategoryGoodsEntity();
        categoryGoodsEntity2.setCategorySeq(1);
        List<CategoryGoodsEntity> list = new ArrayList<>();
        list.add(categoryGoodsEntity);
        list.add(categoryGoodsEntity2);
        int result = 2;

        // モック設定
        doNothing().when(categoryGoodsTableLockLogic).execute();
        doReturn(list).when(categoryGetGoodsOrderListLogic).execute(1, null, 1, 1, false);
        doReturn(1).when(categoryModifyGoodsLogic).execute(any(CategoryGoodsEntity.class));

        // 試験実行
        int actual = service.execute(categorySeq, fromOrderDisplay, toOrderDisplay);

        // 戻り値及び呼び出し検証
        verify(categoryGoodsTableLockLogic, times(1)).execute();
        verify(categoryGetGoodsOrderListLogic, times(1)).execute(1, null, 1, 1, false);
        verify(categoryModifyGoodsLogic, times(2)).execute(any(CategoryGoodsEntity.class));
        assertThat(actual).isEqualTo(result);
    }
}
