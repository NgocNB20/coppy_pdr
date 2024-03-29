package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.util.Date;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;

/**
 * Logic/Service移行：会員に紐付く問い合わせの存在チェック
 * 作成日：2021/03/23
 *
 * @author Nguyen Hong Son (VTI VietNam Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoriteRegistServiceTest extends AbstractShopService {

    @Autowired
    FavoriteRegistService service;

    @MockBean
    GoodsDetailsGetByCodeLogic goodsDetailsGetByCodeLogic;

    @MockBean
    FavoriteDataCheckLogic favoriteDataCheckLogic;

    @MockBean
    FavoriteUpdateLogic favoriteUpdateLogic;

    @MockBean
    FavoriteRegistLogic favoriteRegistLogic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        int result = 1;
        Integer shopSeq = 1;
        String goodsCode = "abc";
        HTypeSiteType siteType = getCommonInfo().getCommonInfoBase().getSiteType();
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        FavoriteEntity favoriteEntity = getComponent(FavoriteEntity.class);
        favoriteEntity.setMemberInfoSeq(shopSeq);
        favoriteEntity.setRegistTime(new Timestamp(new Date().getTime()));

        // モック設定
        doReturn(goodsDetailsDto).when(goodsDetailsGetByCodeLogic)
                                 .execute(shopSeq, goodsCode, siteType, HTypeOpenDeleteStatus.OPEN);
        doNothing().when(favoriteDataCheckLogic).execute((FavoriteEntity) any(Object.class));
        doReturn(result).when(favoriteUpdateLogic).execute((FavoriteEntity) any(Object.class));
        if (result == 0) {
            doReturn(result).when(favoriteRegistLogic).execute((FavoriteEntity) any(Object.class));
        }

        // 試験実行
        int actual = service.execute(shopSeq, goodsCode);

        // 戻り値及び呼び出し検証
        verify(goodsDetailsGetByCodeLogic, times(1)).execute(shopSeq, goodsCode, siteType, HTypeOpenDeleteStatus.OPEN);
        verify(favoriteDataCheckLogic, times(1)).execute((FavoriteEntity) any(Object.class));
        verify(favoriteUpdateLogic, times(1)).execute((FavoriteEntity) any(Object.class));
        if (result == 0) {
            verify(favoriteRegistLogic, times(1)).execute((FavoriteEntity) any(Object.class));
        }
        assertThat(actual).isEqualTo(result);
    }
}
