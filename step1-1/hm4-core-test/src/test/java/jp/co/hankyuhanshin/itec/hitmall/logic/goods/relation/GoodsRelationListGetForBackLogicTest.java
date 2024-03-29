package jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation;

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
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsRelationDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;

/**
 * Logic/Service移行<br/>
 * 関連商品リスト取得（バック用）<br/>
 * 対象商品の関連商品リストを取得する。<br/>
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsRelationListGetForBackLogicTest {

    @Autowired
    GoodsRelationListGetForBackLogic logic;

    @MockBean
    private GoodsRelationDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer goodsGroupSeq = 1;
        List<GoodsRelationEntity> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(dao).getGoodsRelationListByGoodsGroupSeq(any(Integer.class));

        // 試験実行
        List<GoodsRelationEntity> actual = logic.execute(goodsGroupSeq);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getGoodsRelationListByGoodsGroupSeq(any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }
}
