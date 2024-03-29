package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsDisplayPriceLogicTest {

    @Autowired
    GoodsDisplayPriceLogic goodsDisplayPriceLogic;

    @Test
    @Order(1)
    public void execute01() {
        List<GoodsDto> goodsList = new ArrayList<>();
        boolean isSaleTimeCheck = false;
        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();

        goodsDisplayPriceLogic.create(goodsList, isSaleTimeCheck, goodsGroupEntity);
    }

    @Test
    @Order(1)
    public void execute02() {
        List<GoodsDto> goodsList = new ArrayList<>();
        GoodsDto goodsDto = new GoodsDto();
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsSeq(999);
        goodsEntity.setGoodsCode("999");
        goodsDto.setGoodsEntity(goodsEntity);
        goodsList.add(goodsDto);
        boolean isSaleTimeCheck = false;
        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();
        goodsGroupEntity.setGoodsGroupCode("999");
        goodsGroupEntity.setGoodsGroupSeq(999);
        goodsGroupEntity.setGroupSalePrice(BigDecimal.TEN);
        goodsGroupEntity.setGroupPrice(BigDecimal.TEN);

        goodsDisplayPriceLogic.create(goodsList, isSaleTimeCheck, goodsGroupEntity);
    }

    @Test
    @Order(1)
    public void execute03() {
        GoodsGroupDto goodsGroupDto = new GoodsGroupDto();
        List<GoodsDto> goodsList = new ArrayList<>();
        GoodsDto goodsDto = new GoodsDto();
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsSeq(999);
        goodsEntity.setGoodsCode("999");
        goodsDto.setGoodsEntity(goodsEntity);
        goodsList.add(goodsDto);

        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();
        goodsGroupEntity.setGoodsGroupCode("999");
        goodsGroupEntity.setGoodsGroupSeq(999);
        goodsGroupEntity.setGroupSalePrice(BigDecimal.TEN);
        goodsGroupEntity.setGroupPrice(BigDecimal.TEN);
        goodsGroupDto.setGoodsDtoList(goodsList);
        goodsGroupDto.setGoodsGroupEntity(goodsGroupEntity);
        boolean isSaleTimeCheck = false;

        goodsDisplayPriceLogic.create(goodsGroupDto, isSaleTimeCheck);
    }

    @Test
    @Order(1)
    public void execute04() {
        GoodsGroupDto goodsGroupDto = new GoodsGroupDto();

        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();
        goodsGroupEntity.setGoodsGroupCode("999");
        goodsGroupEntity.setGoodsGroupSeq(999);
        goodsGroupEntity.setGroupSalePrice(BigDecimal.TEN);
        goodsGroupEntity.setGroupPrice(BigDecimal.TEN);
        goodsGroupDto.setGoodsGroupEntity(goodsGroupEntity);
        boolean isSaleTimeCheck = false;

        goodsDisplayPriceLogic.create(goodsGroupDto, isSaleTimeCheck);
    }
}
