package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsDaoTest {

    @Autowired
    GoodsDao goodsDao;

    @Test
    @Order(1)
    public void insert() {
        GoodsEntity entity = makeGoodsEntity();
        int result = goodsDao.insert(entity);
        Assertions.assertNotNull(goodsDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void getGoodsDetailsListByMemberRank() {
        List<Integer> goodsSeqList = new ArrayList<>();
        goodsSeqList.add(999);
        List<String> goodsCodeList = new ArrayList<>();
        List<GoodsDetailsDto> goodsDetailsDtoList = goodsDao.getGoodsDetailsList(goodsSeqList);
    }

    @Test
    @Order(3)
    public void delete() {
        int result = goodsDao.delete(goodsDao.getEntity(999));
        Assertions.assertNull(goodsDao.getEntity(999));
    }

    private GoodsEntity makeGoodsEntity() {
        GoodsEntity entity = new GoodsEntity();
        entity.setGoodsSeq(999);
        entity.setGoodsGroupSeq(999);
        entity.setGoodsCode("1");
        entity.setSaleStatusPC(HTypeGoodsSaleStatus.SALE);
        entity.setIndividualDeliveryType(HTypeIndividualDeliveryType.OFF);
        entity.setStockManagementFlag(HTypeStockManagementFlag.ON);
        entity.setPurchasedMax(new BigDecimal(1));
        entity.setShopSeq(999);
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
}
