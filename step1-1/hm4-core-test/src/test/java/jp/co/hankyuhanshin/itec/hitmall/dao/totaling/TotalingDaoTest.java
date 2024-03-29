package jp.co.hankyuhanshin.itec.hitmall.dao.totaling;

import java.sql.Timestamp;
import java.util.ArrayList;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;

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
public class TotalingDaoTest {

    @Autowired
    TotalingDao totalingDao;

    @Test
    @Order(1)
    public void getAccessSearchKeywordTotalList() {
        AccessSearchKeywordTotalSearchForConditionDto dto = new AccessSearchKeywordTotalSearchForConditionDto();
        List<String> orderSiteTypeList = new ArrayList<>();
        orderSiteTypeList.add("1");

        dto.setProcessDateFrom(new Timestamp(new java.util.Date().getTime()));
        dto.setProcessDateTo(new Timestamp(new java.util.Date().getTime()));
        dto.setOrderSiteTypeList(orderSiteTypeList);
        dto.setSearchKeyword("1");
        dto.setSearchResultCountFrom(0);
        dto.setSearchResultCountTo(0);
        dto.setShopSeq(0);
        dto.setOrderByCondition("1");

        List<AccessSearchKeywordTotalDto> result =
                        totalingDao.getAccessSearchKeywordTotalList(dto, dto.getPageInfo().getSelectOptions());
        Assertions.assertEquals(0, result.size());
    }
}
