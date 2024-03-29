package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl.CartGoodsCalculateLogicImpl;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Logic/Service移行：カート管理のリハーサル
 * 作成日：2021/03/01
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartGoodsCalculateLogicTest {

    @Autowired
    CartGoodsCalculateLogic cartGoodsCalculateLogic;

    @Autowired
    CartGoodsCalculateLogicImpl cartGoodsCalculateLogicImpl;

    @Test
    @Order(1)
    public void executeTest() {
        CartGoodsEntity cartGoodsEntity = new CartGoodsEntity();
        cartGoodsEntity.setCartSeq(0);
        cartGoodsEntity.setAccessUid("");
        cartGoodsEntity.setMemberInfoSeq(0);
        cartGoodsEntity.setGoodsSeq(0);
        cartGoodsEntity.setCartGoodsCount(new BigDecimal("2"));
        cartGoodsEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        cartGoodsEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        cartGoodsEntity.setShopSeq(0);

        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setGoodsSeq(0);
        goodsDetailsDto.setGoodsGroupSeq(0);
        goodsDetailsDto.setShopSeq(0);
        goodsDetailsDto.setGoodsCode("");
        goodsDetailsDto.setGoodsTaxType(HTypeGoodsTaxType.IN_TAX);
        goodsDetailsDto.setTaxRate(new BigDecimal("0.1"));
        goodsDetailsDto.setAlcoholFlag(HTypeAlcoholFlag.NOT_ALCOHOL);
        goodsDetailsDto.setGoodsPriceInTax(new BigDecimal("0"));
        goodsDetailsDto.setGoodsPrice(new BigDecimal("100"));
        goodsDetailsDto.setDeliveryType("");
        goodsDetailsDto.setSaleStatusPC(HTypeGoodsSaleStatus.NO_SALE);
        goodsDetailsDto.setSaleStartTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setSaleEndTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setUnitManagementFlag(HTypeUnitManagementFlag.ON);
        goodsDetailsDto.setStockManagementFlag(HTypeStockManagementFlag.ON);
        goodsDetailsDto.setIndividualDeliveryType(HTypeIndividualDeliveryType.ON);
        goodsDetailsDto.setPurchasedMax(new BigDecimal("0"));
        goodsDetailsDto.setOrderDisplay(0);
        goodsDetailsDto.setUnitValue1("");
        goodsDetailsDto.setUnitValue2("");
        goodsDetailsDto.setJanCode("");
        goodsDetailsDto.setSalesPossibleStock(new BigDecimal("0"));
        goodsDetailsDto.setRealStock(new BigDecimal("0"));
        goodsDetailsDto.setOrderReserveStock(new BigDecimal("0"));
        goodsDetailsDto.setRemainderFewStock(new BigDecimal("0"));
        goodsDetailsDto.setOrderPointStock(new BigDecimal("0"));
        goodsDetailsDto.setSafetyStock(new BigDecimal("0"));
        goodsDetailsDto.setGoodsGroupCode("");
        goodsDetailsDto.setWhatsnewDate(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setGoodsOpenStatusPC(HTypeOpenDeleteStatus.NO_OPEN);
        goodsDetailsDto.setOpenStartTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setOpenEndTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setGoodsGroupName("");
        goodsDetailsDto.setUnitTitle1("");
        goodsDetailsDto.setUnitTitle2("");
        goodsDetailsDto.setGoodsGroupImageEntityList(new ArrayList<GoodsGroupImageEntity>());
        goodsDetailsDto.setSnsLinkFlag(HTypeSnsLinkFlag.OFF);
        goodsDetailsDto.setMetaDescription("");
        goodsDetailsDto.setStockStatusPc(HTypeStockStatusType.NO_SALE);
        goodsDetailsDto.setGoodsIconList(new ArrayList<GoodsInformationIconDetailsDto>());

        CartGoodsDto cartGoodsDto = new CartGoodsDto();
        cartGoodsDto.setCartGoodsEntity(cartGoodsEntity);
        cartGoodsDto.setGoodsDetailsDto(goodsDetailsDto);
        cartGoodsDto.setGoodsInformationIconDetailsDtoList(new ArrayList<GoodsInformationIconDetailsDto>());
        cartGoodsDto.setGoodsPriceSubtotal(new BigDecimal("0"));
        cartGoodsDto.setGoodsPriceInTaxSubtotal(new BigDecimal("0"));
        cartGoodsDto.setGoodsPriceChanged(false);

        List<CartGoodsDto> cartGoodsDtoList = new ArrayList<>();
        cartGoodsDtoList.add(cartGoodsDto);

        CartDto cartDto = new CartDto();
        cartDto.setGoodsTotalCount(new BigDecimal("0"));
        cartDto.setGoodsTotalPrice(new BigDecimal("0"));
        cartDto.setGoodsTotalPriceInTax(new BigDecimal("0"));
        cartDto.setCartGoodsDtoList(cartGoodsDtoList);
        cartDto.setSettlementMethodType(HTypeSettlementMethodType.AMAZON_PAYMENT);

        cartGoodsCalculateLogic.execute(cartDto);
    }

    @Test
    @Order(1)
    public void executeTest2() {
        CartDto cartDto = new CartDto();

        List<CartGoodsDto> cartGoodsDtoList = new ArrayList<>();
        CartGoodsEntity cartGoodsEntity = new CartGoodsEntity();

        cartGoodsEntity.setCartSeq(0);
        cartGoodsEntity.setAccessUid("");
        cartGoodsEntity.setMemberInfoSeq(0);
        cartGoodsEntity.setGoodsSeq(0);
        cartGoodsEntity.setCartGoodsCount(new BigDecimal("2"));
        cartGoodsEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        cartGoodsEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        cartGoodsEntity.setShopSeq(0);

        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setGoodsSeq(0);
        goodsDetailsDto.setGoodsGroupSeq(0);
        goodsDetailsDto.setShopSeq(0);
        goodsDetailsDto.setGoodsCode("");
        goodsDetailsDto.setGoodsTaxType(HTypeGoodsTaxType.IN_TAX);
        goodsDetailsDto.setTaxRate(new BigDecimal("0.1"));
        goodsDetailsDto.setAlcoholFlag(HTypeAlcoholFlag.NOT_ALCOHOL);
        goodsDetailsDto.setGoodsPriceInTax(new BigDecimal("0"));
        goodsDetailsDto.setGoodsPrice(new BigDecimal("100"));
        goodsDetailsDto.setDeliveryType("");
        goodsDetailsDto.setSaleStatusPC(HTypeGoodsSaleStatus.NO_SALE);
        goodsDetailsDto.setSaleStartTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setSaleEndTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setUnitManagementFlag(HTypeUnitManagementFlag.ON);
        goodsDetailsDto.setStockManagementFlag(HTypeStockManagementFlag.ON);
        goodsDetailsDto.setIndividualDeliveryType(HTypeIndividualDeliveryType.ON);
        goodsDetailsDto.setPurchasedMax(new BigDecimal("0"));
        goodsDetailsDto.setOrderDisplay(0);
        goodsDetailsDto.setUnitValue1("");
        goodsDetailsDto.setUnitValue2("");
        goodsDetailsDto.setJanCode("");
        goodsDetailsDto.setSalesPossibleStock(new BigDecimal("0"));
        goodsDetailsDto.setRealStock(new BigDecimal("0"));
        goodsDetailsDto.setOrderReserveStock(new BigDecimal("0"));
        goodsDetailsDto.setRemainderFewStock(new BigDecimal("0"));
        goodsDetailsDto.setOrderPointStock(new BigDecimal("0"));
        goodsDetailsDto.setSafetyStock(new BigDecimal("0"));
        goodsDetailsDto.setGoodsGroupCode("");
        goodsDetailsDto.setWhatsnewDate(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setGoodsOpenStatusPC(HTypeOpenDeleteStatus.NO_OPEN);
        goodsDetailsDto.setOpenStartTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setOpenEndTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsDetailsDto.setGoodsGroupName("");
        goodsDetailsDto.setUnitTitle1("");
        goodsDetailsDto.setUnitTitle2("");
        goodsDetailsDto.setGoodsGroupImageEntityList(new ArrayList<GoodsGroupImageEntity>());
        goodsDetailsDto.setSnsLinkFlag(HTypeSnsLinkFlag.OFF);
        goodsDetailsDto.setMetaDescription("");
        goodsDetailsDto.setStockStatusPc(HTypeStockStatusType.NO_SALE);
        goodsDetailsDto.setGoodsIconList(new ArrayList<GoodsInformationIconDetailsDto>());

        CartGoodsDto cartGoodsDto = new CartGoodsDto();
        cartGoodsDto.setCartGoodsEntity(cartGoodsEntity);
        cartGoodsDto.setGoodsDetailsDto(goodsDetailsDto);
        cartGoodsDto.setGoodsInformationIconDetailsDtoList(new ArrayList<GoodsInformationIconDetailsDto>());
        cartGoodsDto.setGoodsPriceSubtotal(new BigDecimal("0"));
        cartGoodsDto.setGoodsPriceInTaxSubtotal(new BigDecimal("0"));
        cartGoodsDto.setGoodsPriceChanged(false);
        cartGoodsDtoList.add(cartGoodsDto);

        cartDto.setCartGoodsDtoList(cartGoodsDtoList);

        CartDto cartDtoResult = cartGoodsCalculateLogicImpl.calculateForAjaxCart(cartDto);

        Assertions.assertEquals(new BigDecimal("2"), cartDtoResult.getGoodsTotalCount());
    }
}
