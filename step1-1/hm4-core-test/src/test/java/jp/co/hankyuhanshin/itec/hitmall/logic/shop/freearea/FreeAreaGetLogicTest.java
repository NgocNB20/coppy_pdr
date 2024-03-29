package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
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
public class FreeAreaGetLogicTest {

    @Autowired
    FreeAreaGetLogic freeAreaGetLogic;

    @MockBean
    FreeAreaDao freeAreaDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        FreeAreaEntity result = new FreeAreaEntity();
        result.setShopSeq(123);

        // モック設定
        doReturn(result).when(freeAreaDao).getFreeAreaByKey(any(Integer.class), any(String.class));

        // 試験実行
        FreeAreaEntity actual = freeAreaGetLogic.execute(99, "1");

        // 戻り値及び呼び出し検証
        verify(freeAreaDao, times(1)).getFreeAreaByKey(any(Integer.class), any(String.class));
        Assertions.assertEquals(result, actual);
    }
}
