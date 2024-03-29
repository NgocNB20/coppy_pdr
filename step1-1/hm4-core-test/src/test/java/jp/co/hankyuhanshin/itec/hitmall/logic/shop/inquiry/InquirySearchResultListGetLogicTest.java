package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

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
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchResultDto;

/**
 * Logic/Service移行：問い合わせ検索結果リスト取得
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquirySearchResultListGetLogicTest {

    @Autowired
    InquirySearchResultListGetLogic logic;

    @MockBean
    private InquiryDao inquiryDao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        InquirySearchDaoConditionDto conditionDto = new InquirySearchDaoConditionDto();
        List<InquirySearchResultDto> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(inquiryDao)
                        .getSearchInquiryForBackList(any(InquirySearchDaoConditionDto.class), any(SelectOptions.class));

        // 試験実行
        List<InquirySearchResultDto> actual = logic.execute(conditionDto);

        // 戻り値及び呼び出し検証
        verify(inquiryDao, times(1)).getSearchInquiryForBackList(
                        any(InquirySearchDaoConditionDto.class), any(SelectOptions.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(2)
    public void executeFrontTest() {

        // 想定値設定
        InquirySearchDaoConditionDto conditionDto = new InquirySearchDaoConditionDto();
        List<InquirySearchResultDto> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(inquiryDao)
                        .getSearchInquiryForFrontList(any(InquirySearchDaoConditionDto.class),
                                                      conditionDto.getPageInfo().getSelectOptions()
                                                     );

        // 試験実行
        List<InquirySearchResultDto> actual = logic.executeFront(conditionDto);

        // 戻り値及び呼び出し検証
        verify(inquiryDao, times(1)).getSearchInquiryForFrontList(
                        any(InquirySearchDaoConditionDto.class), conditionDto.getPageInfo().getSelectOptions());
        assertThat(actual).isEqualTo(result);
    }
}
