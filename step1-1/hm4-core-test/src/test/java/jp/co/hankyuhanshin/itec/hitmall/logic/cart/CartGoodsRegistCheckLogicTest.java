package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
public class CartGoodsRegistCheckLogicTest {

    @Autowired
    CartGoodsRegistCheckLogic cartGoodsRegistCheckLogic;

    @Test
    @Order(1)
    public void executeTest() {
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setGoodsSeq(0);
        goodsDetailsDto.setGoodsGroupSeq(0);
        goodsDetailsDto.setShopSeq(0);
        goodsDetailsDto.setGoodsCode("");
        goodsDetailsDto.setGoodsTaxType(HTypeGoodsTaxType.IN_TAX);
        goodsDetailsDto.setTaxRate(new BigDecimal("0"));
        goodsDetailsDto.setAlcoholFlag(HTypeAlcoholFlag.NOT_ALCOHOL);
        goodsDetailsDto.setGoodsPriceInTax(new BigDecimal("0"));
        goodsDetailsDto.setGoodsPrice(new BigDecimal("0"));
        goodsDetailsDto.setDeliveryType("");
        goodsDetailsDto.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
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
        goodsDetailsDto.setSalesPossibleStock(new BigDecimal("1"));
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

        List<CheckMessageDto> errorMessageList =
                        cartGoodsRegistCheckLogic.execute(goodsDetailsDto, new BigDecimal(0), 1, 1, "",
                                                          HTypeSiteType.FRONT_PC
                                                         );

        // checkOpenStatusでエラーが発生
        // "サーバのシステム日時 ＞ カート商品DTO．商品詳細DTO．公開終了日時（ PC/携帯 ） の場合エラー"のチェック

        // checkSaleStatus
        // now.after(saleEndTime) => 販売終了
        Assertions.assertEquals(errorMessageList.size(), 2);
    }

    @Test
    @Order(1)
    public void executeTestFailCheckParam() {
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setGoodsSeq(0);
        goodsDetailsDto.setGoodsGroupSeq(0);
        goodsDetailsDto.setShopSeq(0);
        goodsDetailsDto.setGoodsCode("");
        goodsDetailsDto.setGoodsTaxType(HTypeGoodsTaxType.IN_TAX);
        goodsDetailsDto.setTaxRate(new BigDecimal("0"));
        goodsDetailsDto.setAlcoholFlag(HTypeAlcoholFlag.NOT_ALCOHOL);
        goodsDetailsDto.setGoodsPriceInTax(new BigDecimal("0"));
        goodsDetailsDto.setGoodsPrice(new BigDecimal("0"));
        goodsDetailsDto.setDeliveryType("");
        goodsDetailsDto.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
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
        goodsDetailsDto.setSalesPossibleStock(new BigDecimal("1"));
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

        Throwable throwable = assertThrows(Throwable.class, () -> {
            cartGoodsRegistCheckLogic.execute(
                            goodsDetailsDto, new BigDecimal(0), 1, null, null, HTypeSiteType.FRONT_PC);
        });
        assertEquals(NullPointerException.class, throwable.getClass());
    }

    @Test
    @Order(1)
    public void executeTestCheckParamFail() {
        List<CheckMessageDto> errorMessageList =
                        cartGoodsRegistCheckLogic.execute(null, new BigDecimal(0), 1, 1, "", HTypeSiteType.FRONT_PC);

        // checkParam 商品存在チェック
        // パラメータチェック
        Assertions.assertEquals(errorMessageList.size(), 1);
    }

    @Test
    @Order(2)
    public void executeTest2() {
        CartGoodsForTakeOverDto cartGoodsForTakeOverDto = new CartGoodsForTakeOverDto();
        List<CartGoodsForTakeOverDto> registCartGoodsList = new ArrayList<>();

        registCartGoodsList.add(cartGoodsForTakeOverDto);

        cartGoodsRegistCheckLogic.execute(registCartGoodsList);
    }
}
