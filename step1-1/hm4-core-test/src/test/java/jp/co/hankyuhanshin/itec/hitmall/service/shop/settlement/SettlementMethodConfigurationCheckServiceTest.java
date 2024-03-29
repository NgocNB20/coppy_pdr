package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodCommissionType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodPriceCommissionFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodCheckLogic;

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
public class SettlementMethodConfigurationCheckServiceTest {

    @Autowired
    SettlementMethodConfigurationCheckService service;

    @MockBean
    private SettlementMethodCheckLogic logic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        SettlementMethodEntity settlementMethodEntity = new SettlementMethodEntity();
        settlementMethodEntity.setSettlementMethodPriceCommissionFlag(
                        HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT);
        settlementMethodEntity.setMaxPurchasedPrice(BigDecimal.ONE);
        settlementMethodEntity.setEqualsCommission(BigDecimal.ONE);
        settlementMethodEntity.setSettlementMethodCommissionType(HTypeSettlementMethodCommissionType.EACH_AMOUNT_YEN);
        settlementMethodEntity.setSettlementMethodType(HTypeSettlementMethodType.CREDIT);

        SettlementMethodPriceCommissionEntity entity = new SettlementMethodPriceCommissionEntity();
        List<SettlementMethodPriceCommissionEntity> list = new ArrayList<SettlementMethodPriceCommissionEntity>();
        list.add(entity);

        SettlementMethodDto settlementMethodDto = new SettlementMethodDto();
        settlementMethodDto.setSettlementMethodEntity(settlementMethodEntity);
        settlementMethodDto.setSettlementMethodPriceCommissionEntityList(list);

        // モック設定
        doNothing().when(logic).execute(any(SettlementMethodEntity.class));

        // 試験実行
        service.execute(settlementMethodDto);

        // 戻り値及び呼び出し検証
        verify(logic, times(1)).execute(any(SettlementMethodEntity.class));
    }
}
