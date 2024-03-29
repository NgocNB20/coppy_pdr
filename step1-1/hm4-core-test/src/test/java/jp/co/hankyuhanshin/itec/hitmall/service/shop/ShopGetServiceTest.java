package jp.co.hankyuhanshin.itec.hitmall.service.shop;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.ShopGetLogic;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShopGetServiceTest {

    @Autowired
    ShopGetService shopGetService;

    @MockBean
    ShopGetLogic shopGetLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        ShopEntity result = new ShopEntity();
        result.setMetaKeyword("test");

        // モック設定
        doReturn(result).when(shopGetLogic).execute(any(Integer.class), any(), any());

        // 試験実行
        ShopEntity actual = shopGetService.execute();

        // 戻り値及び呼び出し検証
        verify(shopGetLogic, times(1)).execute(any(Integer.class), any(), any());
        Assertions.assertEquals(result, actual);
    }
}
