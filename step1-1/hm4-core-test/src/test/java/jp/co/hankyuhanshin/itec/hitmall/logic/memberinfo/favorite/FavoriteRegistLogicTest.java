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
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;

/**
 * Logic/Service移行：お気に入り情報登録ロジック
 * 作成日：2021/03/12
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoriteRegistLogicTest {

    @Autowired
    FavoriteRegistLogic logic;

    @MockBean
    private FavoriteDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setMemberInfoSeq(1);
        favoriteEntity.setGoodsSeq(1);
        int result = 1;

        // モック設定
        doReturn(result).when(dao).insert(any(FavoriteEntity.class));

        // 試験実行
        int actual = logic.execute(favoriteEntity);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).insert(any(FavoriteEntity.class));
        assertThat(actual).isEqualTo(result);
    }

}
