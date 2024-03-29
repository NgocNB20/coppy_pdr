package jp.co.hankyuhanshin.itec.hitmall.service.shop.top;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.top.TopInitialaizeInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.top.GoodsRankingEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.top.TopInitialaizeInfoClickRankingListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.top.TopInitialaizeInfoOrderRankingListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.ShopGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingService;
import org.junit.jupiter.api.Assertions;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TopInitialaizeInfoGetServiceTest {

    @Autowired
    TopInitialaizeInfoGetService topInitialaizeInfoGetService;

    @MockBean
    TopInitialaizeInfoOrderRankingListGetLogic topInitialaizeInfoOrderRankingListGetLogic;

    @MockBean
    TopInitialaizeInfoClickRankingListGetLogic topInitialaizeInfoClickRankingListGetLogic;

    @MockBean
    ShopGetService shopInfoGetService;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        TopInitialaizeInfoDto result = new TopInitialaizeInfoDto();

        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setShopSeq(123);

        GoodsRankingEntity goodsRankingEntity = new GoodsRankingEntity();
        goodsRankingEntity.setShopSeq(123);

        List<GoodsRankingEntity> clickRankingList = new ArrayList<>();
        clickRankingList.add(goodsRankingEntity);

        List<GoodsRankingEntity> orderRankingList = new ArrayList<>();
        orderRankingList.add(goodsRankingEntity);

        result.setShopEntity(shopEntity);
        result.setClickRankingList(clickRankingList);
        result.setOrderRankingList(orderRankingList);

        // モック設定
        doReturn(shopEntity).when(shopInfoGetService).execute();
        doReturn(clickRankingList).when(topInitialaizeInfoClickRankingListGetLogic).execute(any(Integer.class));
        doReturn(orderRankingList).when(topInitialaizeInfoOrderRankingListGetLogic).execute(any(Integer.class));

        // 試験実行
        TopInitialaizeInfoDto actual = topInitialaizeInfoGetService.execute();

        // 戻り値及び呼び出し検証
        verify(shopInfoGetService, times(1)).execute();
        Assertions.assertEquals(result, actual);
    }
}
