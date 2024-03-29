package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;

import java.math.BigDecimal;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupUpdateLogicTest {

    @Autowired
    GoodsGroupUpdateLogic goodsGroupUpdateLogic;

    @MockBean
    GoodsGroupDao goodsGroupDao;

    @Test
    @Order(1)
    public void execute() {
        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();
        goodsGroupEntity.setGoodsGroupSeq(0);
        goodsGroupEntity.setGoodsGroupCode("");
        goodsGroupEntity.setGoodsGroupName("");
        goodsGroupEntity.setWhatsnewDate(new Timestamp(new java.util.Date().getTime()));
        goodsGroupEntity.setGoodsOpenStatusPC(HTypeOpenDeleteStatus.NO_OPEN);
        goodsGroupEntity.setOpenStartTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsGroupEntity.setOpenEndTimePC(new Timestamp(new java.util.Date().getTime()));
        goodsGroupEntity.setGoodsTaxType(HTypeGoodsTaxType.IN_TAX);
        goodsGroupEntity.setTaxRate(new BigDecimal("0"));
        goodsGroupEntity.setAlcoholFlag(HTypeAlcoholFlag.NOT_ALCOHOL);
        goodsGroupEntity.setSnsLinkFlag(HTypeSnsLinkFlag.OFF);
        goodsGroupEntity.setShopSeq(0);
        goodsGroupEntity.setVersionNo(0);
        goodsGroupEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        goodsGroupEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        doReturn(1).when(goodsGroupDao).update(any(GoodsGroupEntity.class));
        int result = goodsGroupUpdateLogic.execute(goodsGroupEntity);

        verify(goodsGroupDao, times(1)).update(any(GoodsGroupEntity.class));
        Assertions.assertEquals(1, result);
    }
}
