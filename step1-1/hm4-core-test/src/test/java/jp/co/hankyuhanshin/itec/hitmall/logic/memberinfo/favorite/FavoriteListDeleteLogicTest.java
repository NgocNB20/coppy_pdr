package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite;

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
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.favorite.FavoriteDao;

/**
 * Logic/Service移行：お気に入り情報リスト削除ロジック
 * 作成日：2021/03/12
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoriteListDeleteLogicTest {

    @Autowired
    FavoriteListDeleteLogic logic;

    @MockBean
    private FavoriteDao dao;

    @Test
    @Order(1)
    public void execute1Test() {

        // 想定値設定
        Integer memberInfoSeq = 1;
        Integer[] goodsSeqs = {1, 2, 3};
        int result = 1;

        // モック設定
        doReturn(result).when(dao).deleteList(any(Integer.class), any());

        // 試験実行
        int actual = logic.execute(memberInfoSeq, goodsSeqs);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).deleteList(any(Integer.class), any());
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(2)
    public void execute2Test() {

        // 想定値設定
        Integer memberInfoSeq = 1;
        int result = 1;

        // モック設定
        doReturn(result).when(dao).deleteListByMemberInfoSeq(any(Integer.class));

        // 試験実行
        int actual = logic.execute(memberInfoSeq);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).deleteListByMemberInfoSeq(any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(3)
    public void execute3Test() {

        // 想定値設定
        Integer memberInfoSeq = 1;
        String[] goodsCodes = {"1", "2", "3"};
        int result = 1;

        // モック設定
        doReturn(result).when(dao).deleteListByGoodsCode(any(Integer.class), any());

        // 試験実行
        int actual = logic.execute(memberInfoSeq, goodsCodes);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).deleteListByGoodsCode(any(Integer.class), any());
        assertThat(actual).isEqualTo(result);
    }

}
