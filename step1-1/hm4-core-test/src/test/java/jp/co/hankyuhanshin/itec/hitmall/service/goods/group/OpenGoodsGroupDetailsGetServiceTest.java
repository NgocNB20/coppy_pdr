package jp.co.hankyuhanshin.itec.hitmall.service.goods.group;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDetailsGetByCodeLogic;

/**
 * Logic/Service移行：公開商品グループ詳細情報取得
 * 作成日：2021/03/23
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OpenGoodsGroupDetailsGetServiceTest {

    @Autowired
    OpenGoodsGroupDetailsGetService service;

    @MockBean
    private GoodsGroupDetailsGetByCodeLogic logic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        String goodsGroupCode = "CODE";
        String goodsCode = "A";
        GoodsGroupDto result = null;

        // モック設定
        doReturn(result).when(logic)
                        .execute(any(Integer.class), any(String.class), any(String.class), any(HTypeSiteType.class),
                                 any(HTypeOpenDeleteStatus.class)
                                );

        // 試験実行
        GoodsGroupDto actual = service.execute(goodsGroupCode, goodsCode);

        // 戻り値及び呼び出し検証
        verify(logic, times(1)).execute(
                        any(Integer.class), any(String.class), any(String.class), any(HTypeSiteType.class),
                        any(HTypeOpenDeleteStatus.class)
                                       );
        assertThat(actual).isEqualTo(result);
    }
}
