package jp.co.hankyuhanshin.itec.hitmall.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultForOrderRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.GoodsSearchResultListForOrderRegistGetLogic;

/**
 * Logic/Service移行：新規受注登録用商品管理機能詳細リスト取得サービス
 * 作成日：2021/03/24
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsSearchResultListForOrderRegistGetServiceTest {

    @Autowired
    GoodsSearchResultListForOrderRegistGetService service;

    @MockBean
    private GoodsSearchResultListForOrderRegistGetLogic inquiryGroupGetLogic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        GoodsSearchForBackDaoConditionDto searchCondition = new GoodsSearchForBackDaoConditionDto();
        List<GoodsSearchResultForOrderRegistDto> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(inquiryGroupGetLogic).execute(any(GoodsSearchForBackDaoConditionDto.class));

        // 試験実行
        List<GoodsSearchResultForOrderRegistDto> actual = service.execute(searchCondition);

        // 戻り値及び呼び出し検証
        verify(inquiryGroupGetLogic, times(1)).execute(any(GoodsSearchForBackDaoConditionDto.class));
        assertThat(actual).isEqualTo(result);
    }
}
