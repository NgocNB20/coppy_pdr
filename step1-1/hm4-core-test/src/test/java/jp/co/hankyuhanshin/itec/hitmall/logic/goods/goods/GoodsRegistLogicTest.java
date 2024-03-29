package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsRegistLogicTest {

    @Autowired
    GoodsRegistLogic goodsRegistLogic;

    @MockBean
    GoodsDao goodsDao;

    @Test
    @Order(1)
    public void execute() {
        List<GoodsEntity> goodsEntityList = new ArrayList<>();
        List<GoodsEntity> newGoodsEntityList = new ArrayList<>();
        GoodsEntity entity = new GoodsEntity();
        GoodsEntity newEntity = new GoodsEntity();
        entity.setGoodsSeq(999);
        entity.setGoodsGroupSeq(10034797);
        entity.setGoodsCode("");
        entity.setJanCode("");
        entity.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
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
        entity.setReserveFlag(HTypeReserveFlag.OFF);
        entity.setLandSendFlag(HTypeLandSendFlag.OFF);
        entity.setCoolSendFlag(HTypeCoolSendFlag.OFF);
        entity.setPriceMarkDispFlag(HTypePriceMarkDispFlag.OFF);
        entity.setSalePriceMarkDispFlag(HTypeSalePriceMarkDispFlag.OFF);
        entity.setSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);
        entity.setEmotionPriceType(HTypeEmotionPriceType.NORMAL);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        goodsEntityList.add(entity);

        newEntity.setGoodsSeq(999);
        newEntity.setGoodsGroupSeq(10034797);
        newEntity.setGoodsCode("");
        newEntity.setJanCode("");
        newEntity.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
        newEntity.setSaleStartTimePC(new Timestamp(new java.util.Date().getTime()));
        newEntity.setSaleEndTimePC(new Timestamp(new java.util.Date().getTime()));
        newEntity.setIndividualDeliveryType(HTypeIndividualDeliveryType.OFF);
        newEntity.setUnitValue1("TEST");
        newEntity.setUnitValue2("");
        newEntity.setStockManagementFlag(HTypeStockManagementFlag.ON);
        newEntity.setPurchasedMax(new BigDecimal("0"));
        newEntity.setOrderDisplay(0);
        newEntity.setShopSeq(0);
        newEntity.setVersionNo(0);
        newEntity.setReserveFlag(HTypeReserveFlag.OFF);
        newEntity.setLandSendFlag(HTypeLandSendFlag.OFF);
        newEntity.setCoolSendFlag(HTypeCoolSendFlag.OFF);
        newEntity.setPriceMarkDispFlag(HTypePriceMarkDispFlag.OFF);
        newEntity.setSalePriceMarkDispFlag(HTypeSalePriceMarkDispFlag.OFF);
        newEntity.setSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);
        newEntity.setEmotionPriceType(HTypeEmotionPriceType.NORMAL);
        newEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        newEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        newGoodsEntityList.add(newEntity);

        doReturn(1).when(goodsDao).update(any());
        doReturn(newGoodsEntityList).when(goodsDao).getGoodsListByGoodsGroupSeq(any());
        doReturn(newEntity).when(goodsDao).getEntity(any());

        Map<String, Integer> map = goodsRegistLogic.execute(10034797, goodsEntityList);
        Assertions.assertEquals(2, map.size());
    }

    @Test
    @Order(1)
    public void execute02() {
        List<GoodsEntity> goodsEntityList = new ArrayList<>();
        List<GoodsEntity> newGoodsEntityList = new ArrayList<>();
        GoodsEntity entity = new GoodsEntity();
        GoodsEntity newEntity = new GoodsEntity();
        entity.setGoodsSeq(999);
        entity.setGoodsGroupSeq(10034797);
        entity.setGoodsCode("");
        entity.setJanCode("");
        entity.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
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
        entity.setReserveFlag(HTypeReserveFlag.OFF);
        entity.setLandSendFlag(HTypeLandSendFlag.OFF);
        entity.setCoolSendFlag(HTypeCoolSendFlag.OFF);
        entity.setPriceMarkDispFlag(HTypePriceMarkDispFlag.OFF);
        entity.setSalePriceMarkDispFlag(HTypeSalePriceMarkDispFlag.OFF);
        entity.setSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);
        entity.setEmotionPriceType(HTypeEmotionPriceType.NORMAL);
        entity.setSaleStatusPC(HTypeGoodsSaleStatus.DELETED);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        goodsEntityList.add(entity);

        newEntity.setGoodsSeq(999);
        newEntity.setGoodsGroupSeq(10034797);
        newEntity.setGoodsCode("");
        newEntity.setJanCode("");
        newEntity.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
        newEntity.setSaleStartTimePC(new Timestamp(new java.util.Date().getTime()));
        newEntity.setSaleEndTimePC(new Timestamp(new java.util.Date().getTime()));
        newEntity.setIndividualDeliveryType(HTypeIndividualDeliveryType.OFF);
        newEntity.setUnitValue1("TEST");
        newEntity.setUnitValue2("");
        newEntity.setStockManagementFlag(HTypeStockManagementFlag.ON);
        newEntity.setPurchasedMax(new BigDecimal("0"));
        newEntity.setOrderDisplay(0);
        newEntity.setShopSeq(0);
        newEntity.setVersionNo(0);
        newEntity.setReserveFlag(HTypeReserveFlag.OFF);
        newEntity.setLandSendFlag(HTypeLandSendFlag.OFF);
        newEntity.setCoolSendFlag(HTypeCoolSendFlag.OFF);
        newEntity.setPriceMarkDispFlag(HTypePriceMarkDispFlag.OFF);
        newEntity.setSalePriceMarkDispFlag(HTypeSalePriceMarkDispFlag.OFF);
        newEntity.setSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);
        newEntity.setEmotionPriceType(HTypeEmotionPriceType.NORMAL);
        newEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        newEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        newGoodsEntityList.add(newEntity);

        doReturn(1).when(goodsDao).update(any());
        doReturn(newGoodsEntityList).when(goodsDao).getGoodsListByGoodsGroupSeq(any());
        doReturn(newEntity).when(goodsDao).getEntity(any());

        Map<String, Integer> map = goodsRegistLogic.execute(10034797, goodsEntityList);
        Assertions.assertEquals(2, map.size());
    }

    @Test
    @Order(1)
    public void execute03() {
        List<GoodsEntity> goodsEntityList = new ArrayList<>();
        List<GoodsEntity> newGoodsEntityList = new ArrayList<>();
        GoodsEntity entity = new GoodsEntity();
        GoodsEntity newEntity = new GoodsEntity();
        entity.setGoodsSeq(999);
        entity.setGoodsGroupSeq(10034797);
        entity.setGoodsCode("");
        entity.setJanCode("");
        entity.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
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
        entity.setReserveFlag(HTypeReserveFlag.OFF);
        entity.setLandSendFlag(HTypeLandSendFlag.OFF);
        entity.setCoolSendFlag(HTypeCoolSendFlag.OFF);
        entity.setPriceMarkDispFlag(HTypePriceMarkDispFlag.OFF);
        entity.setSalePriceMarkDispFlag(HTypeSalePriceMarkDispFlag.OFF);
        entity.setSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);
        entity.setEmotionPriceType(HTypeEmotionPriceType.NORMAL_WITH_EMOTION);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        goodsEntityList.add(entity);

        newEntity.setGoodsSeq(999);
        newEntity.setGoodsGroupSeq(10034797);
        newEntity.setGoodsCode("");
        newEntity.setJanCode("");
        newEntity.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
        newEntity.setSaleStartTimePC(new Timestamp(new java.util.Date().getTime()));
        newEntity.setSaleEndTimePC(new Timestamp(new java.util.Date().getTime()));
        newEntity.setIndividualDeliveryType(HTypeIndividualDeliveryType.OFF);
        newEntity.setUnitValue1("TEST");
        newEntity.setUnitValue2("");
        newEntity.setStockManagementFlag(HTypeStockManagementFlag.ON);
        newEntity.setPurchasedMax(new BigDecimal("0"));
        newEntity.setOrderDisplay(0);
        newEntity.setShopSeq(0);
        newEntity.setVersionNo(0);
        newEntity.setReserveFlag(HTypeReserveFlag.OFF);
        newEntity.setLandSendFlag(HTypeLandSendFlag.OFF);
        newEntity.setCoolSendFlag(HTypeCoolSendFlag.OFF);
        newEntity.setPriceMarkDispFlag(HTypePriceMarkDispFlag.OFF);
        newEntity.setSalePriceMarkDispFlag(HTypeSalePriceMarkDispFlag.OFF);
        newEntity.setSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);
        newEntity.setEmotionPriceType(HTypeEmotionPriceType.NORMAL_WITH_EMOTION);
        newEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        newEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        newGoodsEntityList.add(newEntity);

        doReturn(1).when(goodsDao).update(any());
        doReturn(newGoodsEntityList).when(goodsDao).getGoodsListByGoodsGroupSeq(any());
        doReturn(newEntity).when(goodsDao).getEntity(any());
        doReturn(newEntity).when(goodsDao).getGoodsByCode(any(), anyString());

        Map<String, Integer> map = goodsRegistLogic.execute(10034797, goodsEntityList);
        Assertions.assertEquals(2, map.size());
    }
}
