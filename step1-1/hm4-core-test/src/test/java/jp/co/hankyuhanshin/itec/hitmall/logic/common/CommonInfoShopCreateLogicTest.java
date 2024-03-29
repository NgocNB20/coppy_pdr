package jp.co.hankyuhanshin.itec.hitmall.logic.common;

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
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfoShop;
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.impl.CommonInfoShopImpl;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.ShopDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;

/**
 * Logic/Service移行： ショップ情報作成ロジック(共通情報)
 * 作成日：2021/03/19
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommonInfoShopCreateLogicTest {

    @Autowired
    CommonInfoShopCreateLogic logic;

    @MockBean
    private ShopDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer shopSeq = 1;
        HTypeSiteType siteType = HTypeSiteType.BACK;
        HTypeOpenStatus openStatus = HTypeOpenStatus.NO_OPEN;
        ShopEntity shopEntity = null;
        CommonInfoShopImpl result = null;

        // モック設定
        doReturn(shopEntity).when(dao)
                            .getEntityBySiteTypeStatusTime(any(Integer.class), any(HTypeSiteType.class),
                                                           any(HTypeOpenStatus.class)
                                                          );

        // 試験実行
        CommonInfoShop actual = logic.execute(shopSeq, siteType, openStatus);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getEntityBySiteTypeStatusTime(
                        any(Integer.class), any(HTypeSiteType.class), any(HTypeOpenStatus.class));
        assertThat(actual).isEqualTo(result);
    }
}
