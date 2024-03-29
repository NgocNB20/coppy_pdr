package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodCommissionType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodPriceCommissionFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodUpdateLogic;

/**
 * Logic/Service移行：決済方法更新
 * 作成日：2021/03/24
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettlementMethodUpdateServiceTest {

    @Autowired
    SettlementMethodUpdateService service;

    @MockBean
    private SettlementMethodGetLogic settlementMethodGetLogic;

    @MockBean
    private SettlementMethodUpdateLogic settlementMethodUpdateLogic;

    @MockBean
    private SettlementMethodCheckLogic settlementMethodCheckLogic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        SettlementMethodEntity settlementMethodEntity = new SettlementMethodEntity();
        settlementMethodEntity.setSettlementMethodPriceCommissionFlag(HTypeSettlementMethodPriceCommissionFlag.FLAT);
        settlementMethodEntity.setMaxPurchasedPrice(BigDecimal.ONE);
        settlementMethodEntity.setEqualsCommission(BigDecimal.ONE);
        settlementMethodEntity.setSettlementMethodCommissionType(HTypeSettlementMethodCommissionType.EACH_AMOUNT_YEN);
        settlementMethodEntity.setSettlementMethodType(HTypeSettlementMethodType.CREDIT);
        settlementMethodEntity.setBillType(HTypeBillType.POST_CLAIM);
        settlementMethodEntity.setDeliveryMethodSeq(1);
        settlementMethodEntity.setRegistTime(Timestamp.valueOf("2021-03-25 12:12:12"));
        settlementMethodEntity.setSettlementMethodSeq(1);

        SettlementMethodPriceCommissionEntity entity = new SettlementMethodPriceCommissionEntity();
        List<SettlementMethodPriceCommissionEntity> list = new ArrayList<SettlementMethodPriceCommissionEntity>();
        list.add(entity);

        SettlementMethodDto settlementMethodDto = new SettlementMethodDto();
        settlementMethodDto.setSettlementMethodEntity(settlementMethodEntity);
        settlementMethodDto.setSettlementMethodPriceCommissionEntityList(list);

        int result = 1;

        // モック設定
        doReturn(settlementMethodEntity).when(settlementMethodGetLogic).execute(any(Integer.class));
        doReturn(result).when(settlementMethodUpdateLogic).execute(any(SettlementMethodEntity.class));
        doNothing().when(settlementMethodCheckLogic)
                   .execute(any(SettlementMethodEntity.class), any(SettlementMethodEntity.class));

        // 試験実行
        int actual = service.execute(settlementMethodDto);

        // 戻り値及び呼び出し検証
        verify(settlementMethodGetLogic, times(1)).execute(any(Integer.class));
        verify(settlementMethodUpdateLogic, times(1)).execute(any(SettlementMethodEntity.class));
        verify(settlementMethodCheckLogic, times(1)).execute(
                        any(SettlementMethodEntity.class), any(SettlementMethodEntity.class));
        assertThat(actual).isEqualTo(result);
    }
}
