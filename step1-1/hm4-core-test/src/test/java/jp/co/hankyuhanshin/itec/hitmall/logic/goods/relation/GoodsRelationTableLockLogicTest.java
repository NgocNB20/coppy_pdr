package jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation;

import static org.mockito.Mockito.doNothing;
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
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsRelationDao;

/**
 * Logic/Service移行:関連商品テーブルロック取得ロジック
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsRelationTableLockLogicTest {

    @Autowired
    GoodsRelationTableLockLogic logic;

    @MockBean
    private GoodsRelationDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // モック設定
        doNothing().when(dao).updateLockTableShareModeNowait();

        // 試験実行
        logic.execute();

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).updateLockTableShareModeNowait();
    }
}
