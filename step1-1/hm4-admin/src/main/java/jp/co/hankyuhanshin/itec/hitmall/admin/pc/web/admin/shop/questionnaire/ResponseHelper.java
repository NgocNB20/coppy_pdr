package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyCsvSearchDto;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * アンケート回答出力画面Helperクラス。
 *
 * @author matsuda
 */
@Component
public class ResponseHelper {

    /**
     * 画面入力値から検索条件Dtoを作成する
     *
     * @param responseModel 検索画面のページ情報
     * @return アンケート回答検索条件Dto
     */
    public QuestionnaireReplyCsvSearchDto toQuestionnaireReplySearchForBackDto(ResponseModel responseModel) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 検索条件Dtoクラス 取得
        QuestionnaireReplyCsvSearchDto conditionDto =
                        ApplicationContextUtility.getBean(QuestionnaireReplyCsvSearchDto.class);

        /* 画面入力値から検索条件Dtoを作成  */

        /** ショップSEQ */
        conditionDto.setShopSeq(1001);

        /** サイト */
        conditionDto.setSite("1");

        /** アンケートSEQ */
        if (!StringUtils.isEmpty(responseModel.getSearchQuestionnaireSeq()))
            conditionDto.setQuestionnaireSeq(Integer.parseInt(responseModel.getSearchQuestionnaireSeq()));

        /** アンケートキー */
        conditionDto.setQuestionnaireKey(responseModel.getSearchQuestionnaireKey());

        /** アンケート名称 */
        conditionDto.setName(responseModel.getSearchName());

        /** アンケート公開状態 */
        conditionDto.setOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                 responseModel.getSearchOpenStatus()
                                                                ));

        /** アンケート公開開始日時(From) */
        conditionDto.setOpenStartTimeFrom(conversionUtility.toTimeStamp(responseModel.getSearchOpenStartTimeFrom()));

        /** アンケート公開開始日時(To) */
        if (responseModel.getSearchOpenStartTimeTo() != null) {
            conditionDto.setOpenStartTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(responseModel.getSearchOpenStartTimeTo())));
        }

        /** アンケート公開終了日時(From) */
        conditionDto.setOpenEndTimeFrom(conversionUtility.toTimeStamp(responseModel.getSearchOpenEndTimeFrom()));

        /** アンケート公開終了日時(To) */
        if (responseModel.getSearchOpenEndTimeTo() != null) {
            conditionDto.setOpenEndTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(responseModel.getSearchOpenEndTimeTo())));
        }

        /**  受付デバイス */
        if (responseModel.getSearchDeviceTypeArray() != null && responseModel.getSearchDeviceTypeArray().length > 0) {
            conditionDto.setDeviceTypeList(Arrays.asList(responseModel.getSearchDeviceTypeArray()));
        }

        /** 受付日時(From) */
        conditionDto.setRegistTimeFrom(conversionUtility.toTimeStamp(responseModel.getSearchRegistTimeFrom()));

        /** 受付日時(To) */
        if (responseModel.getSearchRegistTimeTo() != null) {
            conditionDto.setRegistTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(responseModel.getSearchRegistTimeTo())));
        }
        /** 受注番号 */
        conditionDto.setOrderCode(responseModel.getSearchOrderCode());

        /** 会員SEQ */
        if (!StringUtils.isEmpty(responseModel.getSearchMemberInfoSeq())) {
            conditionDto.setMemberInfoSeq(Integer.parseInt(responseModel.getSearchMemberInfoSeq()));
        }

        /** 会員ID */
        conditionDto.setMemberInfoId(responseModel.getSearchMemberInfoId());

        return conditionDto;
    }
}
