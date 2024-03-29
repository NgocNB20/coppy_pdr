package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon;

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
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsInformationIconDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;

/**
 * Logic/Service移行：アイコン削除
 * 作成日：2021/03/12
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsInformationIconDeleteLogicTest {

    @Autowired
    GoodsInformationIconDeleteLogic logic;

    @MockBean
    private GoodsInformationIconDao dto;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        GoodsInformationIconEntity entity = new GoodsInformationIconEntity();
        entity.setIconSeq(1);
        entity.setShopSeq(1);
        entity.setIconName("ICON");
        int result = 10;

        // モック設定
        doReturn(result).when(dto).delete(any(GoodsInformationIconEntity.class));

        // 試験実行
        int actual = logic.execute(entity);

        // 戻り値及び呼び出し検証
        verify(dto, times(1)).delete(any(GoodsInformationIconEntity.class));
        assertThat(actual).isEqualTo(result);
    }
}
