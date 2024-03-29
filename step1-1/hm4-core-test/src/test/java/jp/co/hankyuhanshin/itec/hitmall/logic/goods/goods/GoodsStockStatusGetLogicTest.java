package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsStockStatusGetLogicTest {

    @Autowired
    GoodsStockStatusGetLogic goodsStockStatusGetLogic;

    @Test
    @Order(1)
    public void execute() {
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        GoodsDto dto = new GoodsDto();
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsSeq(999);
        goodsEntity.setGoodsGroupSeq(999);
        goodsEntity.setGoodsCode("");
        goodsEntity.setJanCode("");
        goodsEntity.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
        goodsEntity.setSaleStartTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsEntity.setSaleEndTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsEntity.setIndividualDeliveryType(HTypeIndividualDeliveryType.OFF);
        goodsEntity.setUnitValue1("");
        goodsEntity.setUnitValue2("");
        goodsEntity.setStockManagementFlag(HTypeStockManagementFlag.ON);
        goodsEntity.setPurchasedMax(new BigDecimal("0"));
        goodsEntity.setOrderDisplay(0);
        goodsEntity.setShopSeq(0);
        goodsEntity.setVersionNo(0);
        goodsEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        goodsEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        dto.setGoodsEntity(goodsEntity);

        StockDto stockDto = new StockDto();
        stockDto.setGoodsSeq(999);
        stockDto.setShopSeq(999);
        stockDto.setSalesPossibleStock(new BigDecimal("0"));
        stockDto.setRealStock(new BigDecimal("0"));
        stockDto.setOrderReserveStock(new BigDecimal("0"));
        stockDto.setRemainderFewStock(new BigDecimal("0"));
        stockDto.setOrderPointStock(new BigDecimal("0"));
        stockDto.setSafetyStock(new BigDecimal("0"));
        stockDto.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        stockDto.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        dto.setStockDto(stockDto);

        dto.setDeleteFlg("");
        dto.setStockStatusPc(HTypeStockStatusType.STOCK_FEW);

        goodsDtoList.add(dto);
        Map<Integer, HTypeStockStatusType> map = goodsStockStatusGetLogic.execute(goodsDtoList, 999);
        Assertions.assertEquals(1, map.size());
    }
}
