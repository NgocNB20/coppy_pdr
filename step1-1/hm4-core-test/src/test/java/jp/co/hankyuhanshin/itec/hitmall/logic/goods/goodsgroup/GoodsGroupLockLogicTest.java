package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupLockLogicTest {

    @Autowired
    GoodsGroupLockLogic goodsGroupLockLogic;

    @MockBean
    GoodsGroupDao goodsGroupDao;

    @Test
    @Order(1)
    public void execute() {
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        goodsGroupSeqList.add(999);

        List<GoodsGroupEntity> goodsGroupEntityList = new ArrayList<>();
        goodsGroupEntityList.add(new GoodsGroupEntity());
        doReturn(goodsGroupEntityList).when(goodsGroupDao)
                                      .getGoodsGroupBySeqForUpdate(any(List.class), any(Integer.class));
        goodsGroupLockLogic.execute(goodsGroupSeqList, 1);

        verify(goodsGroupDao, times(1)).getGoodsGroupBySeqForUpdate(any(List.class), any(Integer.class));
    }
}
