package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
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
public class StockResultRegistByStockManagementFlagLogicTest {

    @Autowired
    StockResultRegistByStockManagementFlagLogic stockResultRegistByStockManagementFlagLogic;

    @Test
    @Order(1)
    public void execute() {
        //このテストケースはDBのgoodsとstockのDummyデータの準備が必要である
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsSeq(999);
        goodsEntity.setGoodsGroupSeq(999);
        goodsEntity.setGoodsCode("999");
        goodsEntity.setJanCode("");
        goodsEntity.setSaleStatusPC(HTypeGoodsSaleStatus.NO_SALE);
        goodsEntity.setSaleStartTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsEntity.setSaleEndTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsEntity.setIndividualDeliveryType(HTypeIndividualDeliveryType.ON);
        goodsEntity.setUnitValue1("");
        goodsEntity.setUnitValue2("");
        goodsEntity.setStockManagementFlag(HTypeStockManagementFlag.ON);
        goodsEntity.setPurchasedMax(new BigDecimal("0"));
        goodsEntity.setOrderDisplay(0);
        goodsEntity.setShopSeq(0);
        goodsEntity.setVersionNo(0);
        goodsEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        goodsEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        int result = stockResultRegistByStockManagementFlagLogic.execute(goodsEntity);
        Assertions.assertEquals(1, result);
    }
}
