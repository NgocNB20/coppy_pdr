package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
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

import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupStockStatusGetLogicTest {

    @Autowired
    GoodsGroupStockStatusGetLogic goodsGroupStockStatusGetLogic;

    @Test
    @Order(1)
    public void execute() {
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        GoodsDto dto = new GoodsDto();
        dto.setGoodsEntity(new GoodsEntity());
        dto.setStockDto(new StockDto());
        dto.setDeleteFlg("");
        dto.setStockStatusPc(HTypeStockStatusType.NO_SALE);
        goodsDtoList.add(dto);

        HTypeStockStatusType hTypeStockStatusType = goodsGroupStockStatusGetLogic.execute(goodsDtoList);
        Assertions.assertNull(hTypeStockStatusType);
    }
}
