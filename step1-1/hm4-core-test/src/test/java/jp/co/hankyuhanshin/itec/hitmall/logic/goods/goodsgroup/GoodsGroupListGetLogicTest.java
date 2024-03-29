package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupSearchForDaoConditionDto;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupListGetLogicTest {

    @Autowired
    GoodsGroupListGetLogic goodsGroupListGetLogic;

    @MockBean
    GoodsGroupDao goodsGroupDao;

    @Test
    @Order(1)
    public void execute() {
        GoodsGroupSearchForDaoConditionDto conditionDto = new GoodsGroupSearchForDaoConditionDto();
        conditionDto.setShopSeq(0);
        conditionDto.setKeywordLikeCondition1("");
        conditionDto.setKeywordLikeCondition2("");
        conditionDto.setKeywordLikeCondition3("");
        conditionDto.setKeywordLikeCondition4("");
        conditionDto.setKeywordLikeCondition5("");
        conditionDto.setKeywordLikeCondition6("");
        conditionDto.setKeywordLikeCondition7("");
        conditionDto.setKeywordLikeCondition8("");
        conditionDto.setKeywordLikeCondition9("");
        conditionDto.setKeywordLikeCondition10("");
        conditionDto.setCategoryId("");
        conditionDto.setGoodsGroupCode("");
        conditionDto.setMinPrice(new BigDecimal("0"));
        conditionDto.setMaxPrice(new BigDecimal("0"));
        conditionDto.setSiteType(HTypeSiteType.FRONT_PC);
        conditionDto.setOpenStatus(HTypeOpenDeleteStatus.NO_OPEN);
        conditionDto.setOutOpenStatus(HTypeOpenDeleteStatus.NO_OPEN);
        conditionDto.setCategoryPath("");
        conditionDto.setStcockExistStatus(new String[] {"test"});
        conditionDto.setSaleId("");
        conditionDto.setAuthenticatedSaleIdList(new ArrayList<String>());
        conditionDto.setSaleGoodsGroupFlag(false);
        conditionDto.setMemberRank(HTypeMemberRank.GUEST);
        conditionDto.setGoodsGroupSeqList(new ArrayList<Integer>());

        PageInfo pageInfo = new PageInfo();

        conditionDto.setPageInfo(pageInfo);

        List<GoodsGroupDetailsDto> goodsGroupDetailsDtoList = new ArrayList<>();
        GoodsGroupDetailsDto dto = new GoodsGroupDetailsDto();
        dto.setGoodsGroupSeq(0);
        dto.setGoodsGroupCode("");
        dto.setWhatsnewDate(new Timestamp(new java.util.Date().getTime()));
        dto.setGoodsOpenStatusPC(HTypeOpenDeleteStatus.NO_OPEN);
        dto.setOpenStartTimePC(new Timestamp(new java.util.Date().getTime()));
        dto.setOpenEndTimePC(new Timestamp(new java.util.Date().getTime()));
        dto.setShopSeq(0);
        dto.setGoodsGroupName("");
        dto.setGoodsTaxType(HTypeGoodsTaxType.IN_TAX);
        dto.setTaxRate(new BigDecimal("0"));
        dto.setVersionNo(0);
        dto.setInformationIconPC("");
        dto.setSearchKeyword("");
        dto.setSearchKeywordEm("");
        dto.setUnitManagementFlag(HTypeUnitManagementFlag.ON);
        dto.setUnitTitle1("");
        dto.setUnitTitle2("");
        dto.setMetaDescription("");
        dto.setMetaKeyword("");
        dto.setDeliverytype("");
        dto.setStockstatusPC(HTypeStockStatusType.NO_SALE);
        goodsGroupDetailsDtoList.add(dto);

        doReturn(goodsGroupDetailsDtoList).when(goodsGroupDao)
                                          .getSearchGoodsGroupDetailsList(
                                                          any(GoodsGroupSearchForDaoConditionDto.class),
                                                          any(SelectOptions.class)
                                                                         );

        List<GoodsGroupDto> goodsGroupDtoList = goodsGroupListGetLogic.execute(conditionDto);
        Assertions.assertEquals(1, goodsGroupDtoList.size());
    }
}
