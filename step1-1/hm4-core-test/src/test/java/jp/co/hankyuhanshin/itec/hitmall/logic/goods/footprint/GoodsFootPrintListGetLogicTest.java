package jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint;

import java.util.ArrayList;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.footprint.FootprintSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
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
public class GoodsFootPrintListGetLogicTest {

    @Autowired
    GoodsFootPrintListGetLogic goodsFootPrintListGetLogic;

    @Test
    @Order(1)
    public void execute() {
        FootprintSearchForDaoConditionDto dto = new FootprintSearchForDaoConditionDto();
        dto.setShopSeq(0);
        dto.setAccessUid("");
        dto.setGoodsGroupSeqList(new ArrayList<Integer>());
        List<GoodsGroupDto> goodsGroupDtoList = goodsFootPrintListGetLogic.execute(dto);
        Assertions.assertEquals(0, goodsGroupDtoList.size());
    }
}
