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
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;

/**
 * Logic/Service移行：アイコンDto取得
 * 作成日：2021/03/12
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsInformationIconDtoGetLogicTest {

    @Autowired
    GoodsInformationIconDtoGetLogic logic;

    @MockBean
    private GoodsInformationIconDao goodsInformationIconDao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer iconSeq = 1;
        Integer shopSeq = 2;
        GoodsInformationIconEntity entity = new GoodsInformationIconEntity();

        GoodsInformationIconDto result = new GoodsInformationIconDto();
        result.setGoodsInformationIconEntity(entity);

        // モック設定
        doReturn(entity).when(goodsInformationIconDao).getEntityByShopSeq(any(Integer.class), any(Integer.class));

        // 試験実行
        GoodsInformationIconDto actual = logic.execute(iconSeq, shopSeq);

        // 戻り値及び呼び出し検証
        verify(goodsInformationIconDao, times(1)).getEntityByShopSeq(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }
}
