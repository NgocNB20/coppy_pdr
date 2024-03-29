package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockStatusDisplayConditionDto;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockStatusDisplayGetRealStatusLogicTest {

    @Autowired
    StockStatusDisplayGetRealStatusLogic stockStatusDisplayGetRealStatusLogic;

    @Test
    @Order(1)
    public void execute1() {
        StockStatusDisplayConditionDto dto = new StockStatusDisplayConditionDto();
        dto.setSaleStatus(HTypeGoodsSaleStatus.NO_SALE);
        dto.setSaleStartTime(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleEndTime(new Timestamp(new java.util.Date().getTime()));
        dto.setStockManagementFlag(HTypeStockManagementFlag.ON);
        dto.setRemainderFewStock(new BigDecimal("0"));
        dto.setSalesPossibleStock(new BigDecimal("0"));
        dto.setSaleId("");
        dto.setSaleHoldStartTime(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleHoldEndTime(new Timestamp(new java.util.Date().getTime()));

        HTypeStockStatusType hTypeStockStatusType = stockStatusDisplayGetRealStatusLogic.execute(dto);
        Assertions.assertEquals(HTypeStockStatusType.STOCK_NOSTOCK, hTypeStockStatusType);
    }

    @Test
    @Order(2)
    public void execute2() {
        StockStatusDisplayConditionDto dto = new StockStatusDisplayConditionDto();
        dto.setSaleStatus(HTypeGoodsSaleStatus.NO_SALE);
        dto.setSaleStartTime(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleEndTime(new Timestamp(new java.util.Date().getTime()));
        dto.setStockManagementFlag(HTypeStockManagementFlag.ON);
        dto.setRemainderFewStock(new BigDecimal("0"));
        dto.setSalesPossibleStock(new BigDecimal("0"));
        dto.setSaleId("");
        dto.setSaleHoldStartTime(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleHoldEndTime(new Timestamp(new java.util.Date().getTime()));

        HTypeOpenDeleteStatus openStatus = HTypeOpenDeleteStatus.NO_OPEN;
        Timestamp openStartTime = new Timestamp(new java.util.Date().getTime());
        Timestamp openEndTime = new Timestamp(new java.util.Date().getTime());
        HTypeStockStatusType hTypeStockStatusType =
                        stockStatusDisplayGetRealStatusLogic.execute(dto, openStatus, openStartTime, openEndTime);
        Assertions.assertEquals(HTypeStockStatusType.NO_OPEN, hTypeStockStatusType);
    }

    @Test
    @Order(3)
    public void execute3() {
        StockStatusDisplayConditionDto dto = new StockStatusDisplayConditionDto();
        dto.setSaleStatus(HTypeGoodsSaleStatus.SALE);
        dto.setSaleId(null);
        dto.setSaleStartTime(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleEndTime(new Timestamp(new java.util.Date().getTime()));
        dto.setStockManagementFlag(HTypeStockManagementFlag.ON);
        dto.setRemainderFewStock(new BigDecimal("0"));
        dto.setSalesPossibleStock(new BigDecimal("0"));
        dto.setSaleHoldStartTime(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleHoldEndTime(new Timestamp(new java.util.Date().getTime()));

        HTypeStockStatusType hTypeStockStatusType = stockStatusDisplayGetRealStatusLogic.execute(dto);
        Assertions.assertEquals(HTypeStockStatusType.STOCK_NOSTOCK, hTypeStockStatusType);
    }
}
