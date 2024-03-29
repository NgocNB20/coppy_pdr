package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
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
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsInformationIconDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;

/**
 * Logic/Service移行：アイコン表示順更新
 * 作成日：2021/03/12
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsInformationIconListOrderDisplayUpdateLogicTest {

    @Autowired
    GoodsInformationIconListOrderDisplayUpdateLogic logic;

    @MockBean
    private GoodsInformationIconDao dto;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        List<GoodsInformationIconEntity> iconEntityList = new ArrayList<>();
        GoodsInformationIconEntity entity = new GoodsInformationIconEntity();
        entity.setIconSeq(1);
        entity.setShopSeq(2);
        entity.setIconName("BEAR");
        entity.setOrderDisplay(3);
        iconEntityList.add(entity);

        int result = 1;

        // モック設定
        doReturn(result).when(dto)
                        .updateOrderDisplay(any(Integer.class), any(Integer.class), any(Integer.class),
                                            any(Timestamp.class)
                                           );
        doNothing().when(dto).updateLockTableShareModeNowait();

        // 試験実行
        int actual = logic.execute(iconEntityList);

        // 戻り値及び呼び出し検証
        verify(dto, times(1)).updateOrderDisplay(
                        any(Integer.class), any(Integer.class), any(Integer.class), any(Timestamp.class));
        verify(dto, times(1)).updateLockTableShareModeNowait();
        assertThat(actual).isEqualTo(result);
    }
}
