package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchResultDto;
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
public class StockSearchResultGetLogicTest {

    @Autowired
    StockSearchResultGetLogic stockSearchResultGetLogic;

    @Test
    @Order(1)
    public void execute() {
        StockSearchForDaoConditionDto dto = new StockSearchForDaoConditionDto();
        dto.setShopSeq(0);
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
        dto.setJanCode("");
        dto.setMultiCode("");
        dto.setSearchMultiCode("");
        dto.setMultiCodeList(new ArrayList<String>());
        dto.setIndividualDeliveryType(HTypeIndividualDeliveryType.OFF);
        dto.setMinPrice(new BigDecimal("0"));
        dto.setMaxPrice(new BigDecimal("0"));
        dto.setGoodsOpenStatus(HTypeOpenDeleteStatus.OPEN);
        dto.setOpenStartTimeFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setOpenStartTimeTo(new Timestamp(new java.util.Date().getTime()));
        dto.setOpenEndTimeFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setOpenEndTimeTo(new Timestamp(new java.util.Date().getTime()));
        dto.setDeleteStatusDsp(false);
        dto.setSaleStatus(HTypeGoodsSaleStatus.NO_SALE);
        dto.setSaleStartTimeFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleStartTimeTo(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleEndTimeFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setSaleEndTimeTo(new Timestamp(new java.util.Date().getTime()));
        dto.setDeliveryMethod("");
        dto.setStockTypeFlag("");
        dto.setOrderPointStockBelow(false);
        dto.setMinStockCount(new BigDecimal("0"));
        dto.setMaxStockCount(new BigDecimal("0"));
        dto.setSupplementTimeFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setSupplementTimeTo(new Timestamp(new java.util.Date().getTime()));
        dto.setMinMail(new BigDecimal("0"));
        dto.setMaxMail(new BigDecimal("0"));
        dto.setSearchType(0);
        dto.setSiteType(HTypeSiteType.FRONT_PC);
        dto.setSite("1");
        dto.setCategoryPath("");

        List<StockSearchResultDto> stockSearchResultDtoList = stockSearchResultGetLogic.execute(dto);
        Assertions.assertEquals(0, stockSearchResultDtoList.size());
    }
}
