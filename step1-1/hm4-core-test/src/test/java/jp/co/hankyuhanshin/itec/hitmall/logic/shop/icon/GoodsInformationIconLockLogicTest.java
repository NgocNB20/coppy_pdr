package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon;

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
 * Logic/Service移行：商品インフォメーションアイコン 行ロック
 * 作成日：2021/03/12
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsInformationIconLockLogicTest {

    @Autowired
    GoodsInformationIconLockLogic logic;

    @MockBean
    private GoodsInformationIconDao dto;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer iconSeq = 1;
        GoodsInformationIconEntity entity = new GoodsInformationIconEntity();
        entity.setIconSeq(1);
        entity.setShopSeq(2);
        entity.setIconName("BEAR");
        entity.setOrderDisplay(3);

        // モック設定
        doReturn(entity).when(dto).getGoodsInformationIconBySeqForUpdate(any(Integer.class));

        // 試験実行
        logic.execute(iconSeq);

        // 戻り値及び呼び出し検証
        verify(dto, times(1)).getGoodsInformationIconBySeqForUpdate(any(Integer.class));
    }
}
