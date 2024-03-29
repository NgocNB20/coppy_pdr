package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupPopularityDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupPopularityEntity;
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
public class GoodsGroupPopularityRegistLogicTest {

    @Autowired
    GoodsGroupPopularityRegistLogic goodsGroupPopularityRegistLogic;

    @MockBean
    GoodsGroupPopularityDao goodsGroupPopularityDao;

    @Test
    @Order(1)
    public void execute() {
        GoodsGroupPopularityEntity goodsGroupPopularityEntity = new GoodsGroupPopularityEntity();
        goodsGroupPopularityEntity.setGoodsGroupSeq(0);
        goodsGroupPopularityEntity.setPopularityCount(0);
        goodsGroupPopularityEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        goodsGroupPopularityEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        doReturn(1).when(goodsGroupPopularityDao).insert(any(GoodsGroupPopularityEntity.class));
        int result = goodsGroupPopularityRegistLogic.execute(goodsGroupPopularityEntity);

        verify(goodsGroupPopularityDao, times(1)).insert(any(GoodsGroupPopularityEntity.class));
        Assertions.assertEquals(1, result);
    }
}
