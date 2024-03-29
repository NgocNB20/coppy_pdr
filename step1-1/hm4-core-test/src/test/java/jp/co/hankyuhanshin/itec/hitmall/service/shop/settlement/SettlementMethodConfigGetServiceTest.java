package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement;

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
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;

/**
 * Logic/Service移行：決済方法詳細設定取得
 * 作成日：2021/03/24
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettlementMethodConfigGetServiceTest {

    @Autowired
    SettlementMethodConfigGetService service;

    @MockBean
    private SettlementMethodGetLogic settlementMethodGetLogic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer settlementMethodSeq = 1;
        SettlementMethodEntity settlementMethodEntity = null;
        SettlementMethodDto result = null;

        // モック設定
        doReturn(settlementMethodEntity).when(settlementMethodGetLogic).execute(any(Integer.class), any(Integer.class));

        // 試験実行
        SettlementMethodDto actual = service.execute(settlementMethodSeq);

        // 戻り値及び呼び出し検証
        verify(settlementMethodGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }
}
