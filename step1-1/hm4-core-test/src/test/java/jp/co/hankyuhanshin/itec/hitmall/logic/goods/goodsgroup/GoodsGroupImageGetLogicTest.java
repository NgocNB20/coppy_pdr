package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupImageDao;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupImageGetLogicTest {

    @Autowired
    GoodsGroupImageGetLogic goodsGroupImageGetLogic;

    @MockBean
    GoodsGroupImageDao goodsGroupImageDao;

    @Test
    @Order(1)
    public void execute() {
        List<Integer> goodsGroupSeqList = new ArrayList<>();

        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();
        GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();
        goodsGroupImageEntity.setGoodsGroupSeq(0);
        goodsGroupImageEntity.setImageTypeVersionNo(0);
        goodsGroupImageEntity.setImageFileName("");
        goodsGroupImageEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        goodsGroupImageEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        goodsGroupImageEntityList.add(goodsGroupImageEntity);

        doReturn(goodsGroupImageEntityList).when(goodsGroupImageDao).getGoodsGroupImageList(any(List.class));

        Map<Integer, List<GoodsGroupImageEntity>> map = goodsGroupImageGetLogic.execute(goodsGroupSeqList);
        verify(goodsGroupImageDao, times(1)).getGoodsGroupImageList(any(List.class));
        Assertions.assertEquals(1, map.size());
    }
}
