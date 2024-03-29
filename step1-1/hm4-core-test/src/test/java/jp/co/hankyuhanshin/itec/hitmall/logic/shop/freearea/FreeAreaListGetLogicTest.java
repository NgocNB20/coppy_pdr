package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;

import java.sql.Timestamp;
import java.util.ArrayList;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.freearea.FreeAreaSearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FreeAreaListGetLogicTest {

    @Autowired
    FreeAreaListGetLogic freeAreaListGetLogic;

    @MockBean
    FreeAreaDao freeAreaDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        List<FreeAreaEntity> result = new ArrayList<>();

        // モック設定
        //        doReturn(result).when(freeAreaDao).getFreeAreaDtoExceptBodyList(any(FreeAreaSearchDaoConditionDto.class), any(SelectOptions.class));

        // 試験実行
        FreeAreaSearchDaoConditionDto conditionDto = new FreeAreaSearchDaoConditionDto();
        conditionDto.setShopSeq(12);
        conditionDto.setFreeAreaKey("1");
        conditionDto.setFreeAreaTitle("1");
        conditionDto.setOpenStartTimeFrom(new Timestamp(new java.util.Date().getTime()));
        conditionDto.setOpenStartTimeTo(new Timestamp(new java.util.Date().getTime()));
        conditionDto.setBaseDate(new Timestamp(new java.util.Date().getTime()));
        conditionDto.setOpenStatusList(new ArrayList<String>());
        conditionDto.setDateType("1");
        conditionDto.setTargetDate("1");
        conditionDto.setTargetTime("1");
        conditionDto.setSiteMapFlag(HTypeSiteMapFlag.OFF);

        List<FreeAreaEntity> actual = freeAreaListGetLogic.execute(conditionDto);

        // 戻り値及び呼び出し検証
        //        verify(freeAreaDao, times(1)).getFreeAreaDtoExceptBodyList(any(FreeAreaSearchDaoConditionDto.class), any(SelectOptions.class));
        Assertions.assertEquals(result, actual);
    }
}
