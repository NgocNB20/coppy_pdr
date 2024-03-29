package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDisplayDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
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
public class GoodsGroupDisplayRegistLogicTest {

    @Autowired
    GoodsGroupDisplayRegistLogic goodsGroupDisplayRegistLogic;

    @MockBean
    GoodsGroupDisplayDao goodsGroupDisplayDao;

    @Test
    @Order(1)
    public void execute() {
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = new GoodsGroupDisplayEntity();

        doReturn(1).when(goodsGroupDisplayDao).insert(any(GoodsGroupDisplayEntity.class));

        int result = goodsGroupDisplayRegistLogic.execute(goodsGroupDisplayEntity);

        verify(goodsGroupDisplayDao, times(1)).insert(any(GoodsGroupDisplayEntity.class));
        Assertions.assertEquals(1, result);
    }
}
