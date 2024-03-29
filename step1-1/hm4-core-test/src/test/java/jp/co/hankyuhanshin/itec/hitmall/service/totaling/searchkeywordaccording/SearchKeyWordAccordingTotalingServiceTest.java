package jp.co.hankyuhanshin.itec.hitmall.service.totaling.searchkeywordaccording;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingListGetLogic;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchKeyWordAccordingTotalingServiceTest {

    @Autowired
    SearchKeyWordAccordingTotalingService searchKeyWordAccordingTotalingService;

    @MockBean
    SearchKeyWordAccordingTotalingListGetLogic searchKeyWordAccordingTotalingListGetLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        List<AccessSearchKeywordTotalDto> result = new ArrayList<>();
        AccessSearchKeywordTotalDto dto = new AccessSearchKeywordTotalDto();
        dto.setSearchCount(123);

        result.add(dto);

        // モック設定
        doReturn(result).when(searchKeyWordAccordingTotalingListGetLogic)
                        .execute(any(AccessSearchKeywordTotalSearchForConditionDto.class));

        // 試験実行
        AccessSearchKeywordTotalSearchForConditionDto conditionDto =
                        new AccessSearchKeywordTotalSearchForConditionDto();
        List<AccessSearchKeywordTotalDto> actual = searchKeyWordAccordingTotalingService.execute(conditionDto);

        // 戻り値及び呼び出し検証
        verify(searchKeyWordAccordingTotalingListGetLogic, times(1)).execute(
                        any(AccessSearchKeywordTotalSearchForConditionDto.class));
        Assertions.assertEquals(result, actual);
    }
}
