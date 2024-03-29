package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import java.math.BigDecimal;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
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

import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsListGetLogicTest {

    @Autowired
    GoodsListGetLogic goodsListGetLogic;

    @Test
    @Order(1)
    public void execute1() {
        GoodsSearchForDaoConditionDto dto = new GoodsSearchForDaoConditionDto();
        dto.setShopSeq(999);
        dto.setKeywordLikeCondition("");
        dto.setCategoryId("");
        dto.setGoodsGroupSeq(0);
        dto.setMinPrice(new BigDecimal("0"));
        dto.setMaxPrice(new BigDecimal("0"));
        dto.setSaleStatus(HTypeGoodsSaleStatus.NO_SALE);

        List<GoodsDto> goodsDtoList = goodsListGetLogic.execute(dto);
        Assertions.assertEquals(0, goodsDtoList.size());
    }
}
