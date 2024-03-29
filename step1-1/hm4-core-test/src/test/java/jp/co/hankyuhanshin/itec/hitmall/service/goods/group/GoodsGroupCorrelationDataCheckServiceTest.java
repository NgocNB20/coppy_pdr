package jp.co.hankyuhanshin.itec.hitmall.service.goods.group;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxRateEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.tax.TaxGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Logic/Service移行：商品グループDto相関チェックサービス
 * 作成日：2021/03/23
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupCorrelationDataCheckServiceTest {

    @Autowired
    GoodsGroupCorrelationDataCheckService service;

    @MockBean
    private TaxGetLogic taxGetLogic;

    @SuppressWarnings("unchecked")
    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsSeq(0);
        goodsEntity.setGoodsGroupSeq(0);
        goodsEntity.setGoodsCode("");
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
        goodsEntity.setEmotionPriceType(HTypeEmotionPriceType.EMOTIONPRICE);
        goodsEntity.setCoolSendTo("coolSendTo");
        goodsEntity.setCoolSendFlag(HTypeCoolSendFlag.ON);
        goodsEntity.setUnit("unit");
        goodsEntity.setPriceMarkDispFlag(HTypePriceMarkDispFlag.ON);
        goodsEntity.setSalePriceMarkDispFlag(HTypeSalePriceMarkDispFlag.ON);
        goodsEntity.setSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);
        goodsEntity.setShopSeq(0);
        goodsEntity.setVersionNo(0);
        goodsEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        goodsEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        StockDto stockDto = new StockDto();
        stockDto.setGoodsSeq(0);
        stockDto.setShopSeq(1001);
        stockDto.setSalesPossibleStock(new BigDecimal(10));
        stockDto.setRealStock(new BigDecimal(10));
        stockDto.setOrderReserveStock(new BigDecimal(1));
        stockDto.setRemainderFewStock(new BigDecimal(5));
        stockDto.setSupplementCount(new BigDecimal(5));
        stockDto.setOrderPointStock(new BigDecimal(5));
        stockDto.setSafetyStock(new BigDecimal(5));
        stockDto.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        stockDto.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        GoodsDto goodsDto = new GoodsDto();

        goodsDto.setGoodsEntity(goodsEntity);
        goodsDto.setStockDto(stockDto);
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        goodsDtoList.add(goodsDto);
        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();
        goodsGroupEntity.setGoodsGroupName("GoodsGroupName");
        goodsGroupEntity.setWhatsnewDate(new Timestamp(new java.util.Date().getTime()));
        goodsGroupEntity.setOpenStartTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsGroupEntity.setOpenEndTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsGroupEntity.setGoodsPreDiscountPrice("GoodsPreDiscountPrice");
        goodsGroupEntity.setTaxRate(new BigDecimal(10));
        goodsGroupEntity.setShopSeq(1001);
        goodsGroupEntity.setGoodsClassType(HTypeGoodsClassType.FOOD);
        goodsGroupEntity.setDentalMonopolySalesFlg(HTypeDentalMonopolySalesFlg.SALES_ON);
        goodsGroupEntity.setCatalogDisplayOrder(0);
        goodsGroupEntity.setGroupPrice(new BigDecimal(1000));
        goodsGroupEntity.setGroupSalePrice(new BigDecimal(1000));
        goodsGroupEntity.setGroupPriceMarkDispFlag(HTypeGroupPriceMarkDispFlag.ON);
        goodsGroupEntity.setGroupSalePriceMarkDispFlag(HTypeGroupSalePriceMarkDispFlag.ON);
        goodsGroupEntity.setGroupSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);

        GoodsGroupDisplayEntity goodsGroupDisplayEntity = new GoodsGroupDisplayEntity();
        GoodsGroupDto goodsGroupDto = new GoodsGroupDto();
        goodsGroupDto.setGoodsGroupEntity(goodsGroupEntity);
        goodsGroupDto.setGoodsDtoList(goodsDtoList);
        goodsGroupDto.setGoodsGroupDisplayEntity(goodsGroupDisplayEntity);

        GoodsRelationEntity goodsRelationEntity = new GoodsRelationEntity();
        goodsRelationEntity.setGoodsRelationGroupSeq(1);
        List<GoodsRelationEntity> goodsRelationEntityList = new ArrayList<>();
        goodsRelationEntityList.add(goodsRelationEntity);
        int processType = 2;
        Map<String, Object> commonCsvInfoMap = new HashMap<>();

        Map<HTypeTaxRateType, TaxRateEntity> hTypeTaxRateTypeTaxRateEntityMap = new HashMap<>();

        doReturn(hTypeTaxRateTypeTaxRateEntityMap).when(taxGetLogic).getEffectiveTaxRateMap();

        try {
            try (MockedStatic<PropertiesUtil> propertiesUtil = Mockito.mockStatic(PropertiesUtil.class)) {
                propertiesUtil.when(() -> PropertiesUtil.getSystemPropertiesValue("goods.relation.amount"))
                              .thenReturn("10");
                // 試験実行
                service.execute(goodsGroupDto, goodsRelationEntityList, processType, commonCsvInfoMap);
            }
            fail("例外がスローされなかった。");
        } catch (Exception e) {

            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).isNull();
        }

        // 戻り値及び呼び出し検証
        verify(taxGetLogic, times(1)).getEffectiveTaxRateMap();
    }
}
