package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.totaling.searchkeywordaccording;

import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;

/**
 * 検索キーワード集計画面用Helperクラス。
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
public class SearchKeywordAccordingHelper {

    /**
     * pageクラスから検索条件Dtoへ値をコピーします
     *
     * @param model        SearchKeywordAccordingModel
     * @param conditionDto AccessSearchKeywordTotalSearchForConditionDto
     */
    public void convertToAccessSearchKeywordTotalSearchForConditionDtoForSearch(SearchKeywordAccordingModel model,
                                                                                AccessSearchKeywordTotalSearchForConditionDto conditionDto) {

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        if (model.getProcessDateFrom() != null) {
            conditionDto.setProcessDateFrom(conversionUtility.toTimeStamp(model.getProcessDateFrom()));
        }
        if (model.getProcessDateTo() != null) {
            conditionDto.setProcessDateTo(
                            dateUtility.getEndOfDate(conversionUtility.toTimeStamp(model.getProcessDateTo())));
        }

        /* 全角半角変換ユーティリティ */
        ZenHanConversionUtility zenHanConversionUtility =
                        ApplicationContextUtility.getBean(ZenHanConversionUtility.class);

        /* 受付場所 */
        conditionDto.setOrderSiteTypeList(Arrays.asList(model.getOrderSiteTypeList()));

        /* キーワード */
        // 大文字変換
        String keyword = StringUtils.upperCase(model.getKeyword());

        // 全角変換
        conditionDto.setSearchKeyword(zenHanConversionUtility.toZenkaku(keyword));

        /* 検索回数－From */
        conditionDto.setSearchResultCountFrom(conversionUtility.toInteger(model.getSearchResultCountFrom()));

        /* 検索回数－To */
        conditionDto.setSearchResultCountTo(conversionUtility.toInteger(model.getSearchResultCountTo()));

        /* ショップSEQ */
        conditionDto.setShopSeq(model.getShopSeq());

        /* ソート条件 */
        conditionDto.setOrderByCondition(model.getOrderByCondition());

    }

    /**
     * 終日時間を設定します
     *
     * @param time TimeStamp
     */
    protected void setEndTime(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        time.setTime(cal.getTimeInMillis());
    }

    /**
     * 終日時間を設定します
     *
     * @param conditionDto AccessSearchKeywordTotalSearchForConditionDto
     */
    public void setEndTime(AccessSearchKeywordTotalSearchForConditionDto conditionDto) {
        if (conditionDto.getProcessDateTo() != null) {
            setEndTime(conditionDto.getProcessDateTo());
        } else {
            Timestamp clone = (Timestamp) conditionDto.getProcessDateFrom().clone();
            setEndTime(clone);
            conditionDto.setProcessDateTo(clone);
        }
    }
}
