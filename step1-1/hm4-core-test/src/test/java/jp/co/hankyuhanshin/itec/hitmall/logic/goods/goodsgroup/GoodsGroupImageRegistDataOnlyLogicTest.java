package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

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
public class GoodsGroupImageRegistDataOnlyLogicTest {

    @Autowired
    GoodsGroupImageRegistDataOnlyLogic goodsGroupImageRegistDataOnlyLogic;

    @MockBean
    GoodsGroupImageDao goodsGroupImageDao;

    @Test
    @Order(1)
    public void execute() {
        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();
        GoodsGroupImageEntity entity = new GoodsGroupImageEntity();
        entity.setGoodsGroupSeq(999);
        entity.setImageFileName("test");
        goodsGroupImageEntityList.add(entity);

        doReturn(goodsGroupImageEntityList).when(goodsGroupImageDao)
                                           .getGoodsGroupImageListByGoodsGroupSeq(any(Integer.class));

        Map<String, Object> map = goodsGroupImageRegistDataOnlyLogic.execute(999, "999", goodsGroupImageEntityList);

        verify(goodsGroupImageDao, times(1)).getGoodsGroupImageListByGoodsGroupSeq(any(Integer.class));

        Assertions.assertEquals(4, map.size());
    }
}
