package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;

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

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconDtoGetLogic;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsInformationIconGetServiceTest {

    @Autowired
    GoodsInformationIconGetService goodsInformationIconGetService;

    @MockBean
    GoodsInformationIconDtoGetLogic goodsInformationIconDtoGetLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        GoodsInformationIconDto result = new GoodsInformationIconDto();

        GoodsInformationIconEntity entity = new GoodsInformationIconEntity();
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        result.setGoodsInformationIconEntity(entity);

        // モック設定
        doReturn(result).when(goodsInformationIconDtoGetLogic).execute(any(Integer.class), any(Integer.class));

        // 試験実行
        GoodsInformationIconDto actual = goodsInformationIconGetService.execute(123);

        // 戻り値及び呼び出し検証
        verify(goodsInformationIconDtoGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        Assertions.assertEquals(result, actual);
    }
}
