package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
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
public class SettlementMethodListGetLogicTest {

    @Autowired
    SettlementMethodListGetLogic settlementMethodListGetLogic;

    @MockBean
    SettlementMethodDao settlementMethodDao;

    @Test
    @Order(1)
    public void execute() {
        List<Integer> settlementMethodSeqList = new ArrayList<Integer>();
        List<SettlementDetailsDto> settlementDetailsList = new ArrayList<SettlementDetailsDto>();
        SettlementDetailsDto dto = new SettlementDetailsDto();
        dto.setShopSeq(1);

        doReturn(settlementDetailsList).when(settlementMethodDao)
                                       .getsettlementDetailsListForLp(any(List.class), any(Integer.class),
                                                                      (Integer[]) any(Object.class),
                                                                      (List<Integer>) any(Object.class)
                                                                     );

        List<SettlementDto> ex =
                        settlementMethodListGetLogic.execute(settlementMethodSeqList, settlementMethodSeqList, true);

        verify(settlementMethodDao, times(1)).getsettlementDetailsListForLp(
                        any(List.class), any(Integer.class), (Integer[]) any(Object.class), any(List.class));

    }
}
