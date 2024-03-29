package jp.co.hankyuhanshin.itec.hitmall.logic.shop;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.ShopDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;

/**
 * Logic/Service移行：ショップ情報取得
 * 作成日：2021/03/18
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShopGetLogicTest {

    @Autowired
    ShopGetLogic logic;

    @MockBean
    private ShopDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer shopSeq = 1;
        HTypeSiteType siteType = HTypeSiteType.BACK;
        HTypeOpenStatus openStatus = HTypeOpenStatus.NO_OPEN;

        ShopEntity result = new ShopEntity();

        // モック設定
        doReturn(result).when(dao)
                        .getEntityBySiteTypeStatus(any(Integer.class), any(HTypeSiteType.class),
                                                   any(HTypeOpenStatus.class)
                                                  );

        // 試験実行
        ShopEntity actual = logic.execute(shopSeq, siteType, openStatus);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getEntityBySiteTypeStatus(
                        any(Integer.class), any(HTypeSiteType.class), any(HTypeOpenStatus.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(2)
    public void executeUseCache() {

        // 想定値設定
        Integer shopSeq = 1;
        ShopEntity result = new ShopEntity();

        // モック設定
        doReturn(result).when(dao).getEntity(any(Integer.class));

        // 試験実行
        ShopEntity actual = logic.executeUseCache(shopSeq);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getEntity(any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }
}
