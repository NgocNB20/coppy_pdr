package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsDataCheckLogicTest {

    @Autowired
    GoodsDataCheckLogic goodsDataCheckLogic;

    @Test
    @Order(1)
    public void execute() {
        List<GoodsEntity> goodsEntityList = new ArrayList<>();
        GoodsEntity entity = new GoodsEntity();
        entity.setGoodsSeq(0);
        entity.setGoodsGroupSeq(0);
        entity.setGoodsCode("");
        entity.setJanCode("");
        entity.setSaleStatusPC(HTypeGoodsSaleStatus.NO_SALE);
        entity.setSaleStartTimePC(new Timestamp(new java.util.Date().getTime()));
        entity.setSaleEndTimePC(new Timestamp(new java.util.Date().getTime()));
        entity.setIndividualDeliveryType(HTypeIndividualDeliveryType.OFF);
        entity.setUnitValue1("");
        entity.setUnitValue2("");
        entity.setStockManagementFlag(HTypeStockManagementFlag.ON);
        entity.setPurchasedMax(new BigDecimal("0"));
        entity.setOrderDisplay(0);
        entity.setShopSeq(0);
        entity.setVersionNo(0);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        goodsEntityList.add(entity);

        goodsDataCheckLogic.execute(goodsEntityList, 999, "999");
    }
}
