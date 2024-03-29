package jp.co.hankyuhanshin.itec.hitmall.logic.totaling.searchkeywordaccording;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

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
import jp.co.hankyuhanshin.itec.hitmall.dao.totaling.TotalingDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;

/**
 * Logic/Service移行：検索キーワード集計CSV出力Logic
 * 作成日：2021/03/18
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchKeyWordAccordingTotalingListCsvOutputLogicTest {

    @Autowired
    SearchKeyWordAccordingTotalingListCsvOutputLogic logic;

    @MockBean
    private TotalingDao dao;

    //    @Test
    //    @Order(1)
    //    public void executeTest() {
    //
    //        // 想定値設定
    //        AccessSearchKeywordTotalSearchForConditionDto conditionDto = new AccessSearchKeywordTotalSearchForConditionDto();
    //        AccessSearchKeywordTotalDto dto = new AccessSearchKeywordTotalDto();
    //        List<AccessSearchKeywordTotalDto> resultList = new ArrayList<>();
    //        resultList.add(dto);
    //
    //        int result = 1;
    //
    //        // モック設定
    //        doReturn(resultList).when(dao).getAccessSearchKeywordTotalList(any(AccessSearchKeywordTotalSearchForConditionDto.class), any(SelectOptions.class));
    //
    //        // 試験実行
    //        int actual = logic.execute(conditionDto);
    //
    //        // 戻り値及び呼び出し検証
    //        verify(dao, times(1)).getAccessSearchKeywordTotalList(any(AccessSearchKeywordTotalSearchForConditionDto.class), any(SelectOptions.class));
    //        assertThat(actual).isEqualTo(result);
    //    }

}
