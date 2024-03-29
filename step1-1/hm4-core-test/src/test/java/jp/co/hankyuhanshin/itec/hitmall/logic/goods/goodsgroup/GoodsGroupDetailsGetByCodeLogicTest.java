package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;

import java.math.BigDecimal;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDisplayDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsStockStatusGetLogic;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupDetailsGetByCodeLogicTest {

    @Autowired
    GoodsGroupDetailsGetByCodeLogic goodsGroupDetailsGetByCodeLogic;

    @MockBean
    GoodsGroupDao goodsGroupDao;

    @MockBean
    GoodsGroupDisplayDao goodsGroupDisplayDao;

    @MockBean
    GoodsDao goodsDao;

    @MockBean
    GoodsStockStatusGetLogic goodsStockStatusGetLogic;

    @MockBean
    GoodsGroupDisplayGetLogic goodsGroupDisplayGetLogic;

    @Test
    @Order(1)
    public void execute() {

        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();
        goodsGroupEntity.setGoodsGroupSeq(999);

        GoodsGroupDisplayEntity goodsGroupDisplayEntity = new GoodsGroupDisplayEntity();
        goodsGroupDisplayEntity.setGoodsGroupSeq(0);
        goodsGroupDisplayEntity.setInformationIconPC("");
        goodsGroupDisplayEntity.setSearchKeyword("");
        goodsGroupDisplayEntity.setSearchKeywordEm("");
        goodsGroupDisplayEntity.setUnitManagementFlag(HTypeUnitManagementFlag.ON);
        goodsGroupDisplayEntity.setUnitTitle1("");
        goodsGroupDisplayEntity.setUnitTitle2("");
        goodsGroupDisplayEntity.setDeliveryType("");
        goodsGroupDisplayEntity.setMetaDescription("");
        goodsGroupDisplayEntity.setMetaKeyword("");
        goodsGroupDisplayEntity.setInformationIconPC("10000");
        goodsGroupDisplayEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        goodsGroupDisplayEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        List<GoodsEntity> goodsEntityList = new ArrayList<>();
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
        goodsEntity.setShopSeq(0);
        goodsEntity.setVersionNo(0);
        goodsEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        goodsEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        goodsEntityList.add(goodsEntity);

        doReturn(goodsGroupEntity).when(goodsGroupDao)
                                  .getGoodsGroupByCode(any(Integer.class), any(String.class), any(String.class),
                                                       any(HTypeSiteType.class), any(HTypeOpenDeleteStatus.class)
                                                      );
        doReturn(goodsGroupDisplayEntity).when(goodsGroupDisplayDao).getEntity(any(Integer.class));
        doReturn(goodsEntityList).when(goodsDao).getSearchGoodsList(any(GoodsSearchForDaoConditionDto.class));
        doReturn(new HashMap<>()).when(goodsStockStatusGetLogic).execute(any(List.class), any(Integer.class));
        doReturn(goodsGroupDisplayEntity).when(goodsGroupDisplayGetLogic).execute(any(Integer.class));

        GoodsGroupDto goodsGroupDto = goodsGroupDetailsGetByCodeLogic.execute(999, "999", "999", HTypeSiteType.BACK,
                                                                              HTypeOpenDeleteStatus.DELETED
                                                                             );
        Assertions.assertNotNull(goodsGroupDto);
    }
}
