package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;
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
public class GoodsSearchResultListGetLogicTest {

    @Autowired
    GoodsSearchResultListGetLogic goodsSearchResultListGetLogic;

    @Test
    @Order(1)
    public void execute() {
        GoodsSearchForBackDaoConditionDto dto = new GoodsSearchForBackDaoConditionDto();
        dto.setSite("0");
        dto.setShopSeq(0);
        List<String> viewMemberRank = new ArrayList<>();
        viewMemberRank.add("1");
        List<String> setMemberRankPrice = new ArrayList<>();
        setMemberRankPrice.add("1");
        dto.setKeywordLikeCondition1("");
        dto.setKeywordLikeCondition2("");
        dto.setKeywordLikeCondition3("");
        dto.setKeywordLikeCondition4("");
        dto.setKeywordLikeCondition5("");
        dto.setKeywordLikeCondition6("");
        dto.setKeywordLikeCondition7("");
        dto.setKeywordLikeCondition8("");
        dto.setKeywordLikeCondition9("");
        dto.setKeywordLikeCondition10("");
        dto.setCategoryId("");
        dto.setGoodsGroupCode("");
        dto.setGoodsCode("");
        dto.setGoodsGroupName("");
        dto.setMultiCode("");
        dto.setSearchMultiCode("1");
        List<String> multiCodeList = new ArrayList<>();
        multiCodeList.add("1");
        dto.setMultiCodeList(multiCodeList);
        dto.setIndividualDeliveryType(HTypeIndividualDeliveryType.ON);
        dto.setMinPrice(new BigDecimal("0"));
        dto.setMaxPrice(new BigDecimal("0"));
        dto.setGoodsOpenStatus(HTypeOpenDeleteStatus.NO_OPEN);
        dto.setGoodsOpenStartTimeFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setGoodsOpenStartTimeTo(new Timestamp(new java.util.Date().getTime()));
        dto.setGoodsOpenEndTimeFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setGoodsOpenEndTimeTo(new Timestamp(new java.util.Date().getTime()));
        dto.setDeleteStatusDsp(false);
        dto.setSaleStatus(HTypeGoodsSaleStatus.NO_SALE);
        dto.setSaleStartTimeFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleStartTimeTo(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleEndTimeFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleEndTimeTo(new Timestamp(new java.util.Date().getTime()));
        dto.setRegistTimeFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setRegistTimeTo(new Timestamp(new java.util.Date().getTime()));
        dto.setUpdateTimeFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setUpdateTimeTo(new Timestamp(new java.util.Date().getTime()));
        dto.setSiteType(HTypeSiteType.FRONT_PC);
        dto.setCategoryPath("");
        dto.setSnsLinkFlag(HTypeSnsLinkFlag.OFF);
        dto.setRelationGoodsSearchFlag(false);

        List<GoodsSearchResultDto> dtoList = goodsSearchResultListGetLogic.execute(dto);
        Assertions.assertEquals(0, dtoList.size());
    }
}
