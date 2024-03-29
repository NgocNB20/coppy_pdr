package jp.co.hankyuhanshin.itec.hitmall.logic.order;

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
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultForOrderRegistDto;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsSearchResultListForOrderRegistGetLogicTest {

    @Autowired
    GoodsSearchResultListForOrderRegistGetLogic logic;

    @MockBean
    private GoodsDao dao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        GoodsSearchForBackDaoConditionDto searchCondition = new GoodsSearchForBackDaoConditionDto();
        List<GoodsSearchResultForOrderRegistDto> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(dao)
                        .getSearchGoodsForOrderRegist(any(GoodsSearchForBackDaoConditionDto.class),
                                                      SelectOptions.get()
                                                     );

        // 試験実行
        List<GoodsSearchResultForOrderRegistDto> actual = logic.execute(searchCondition);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getSearchGoodsForOrderRegist(
                        any(GoodsSearchForBackDaoConditionDto.class), SelectOptions.get());
        assertThat(actual).isEqualTo(result);
    }
}
