package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupMapGetByCodeLogicTest {

    @Autowired
    GoodsGroupMapGetByCodeLogic goodsGroupMapGetByCodeLogic;

    @MockBean
    GoodsGroupDao goodsGroupDao;

    @Test
    @Order(1)
    public void execute() {
        List<String> goodsGroupCodeList = new ArrayList<>();
        goodsGroupCodeList.add("999");

        List<GoodsGroupEntity> goodsGroupEntityList = new ArrayList<>();
        goodsGroupEntityList.add(new GoodsGroupEntity());

        doReturn(goodsGroupEntityList).when(goodsGroupDao).getGoodsGroupByCodeList(any(Integer.class), any(List.class));

        Map<String, GoodsGroupEntity> map = goodsGroupMapGetByCodeLogic.execute(999, goodsGroupCodeList);

        verify(goodsGroupDao, times(1)).getGoodsGroupByCodeList(any(Integer.class), any(List.class));
        Assertions.assertEquals(1, map.size());
    }
}
