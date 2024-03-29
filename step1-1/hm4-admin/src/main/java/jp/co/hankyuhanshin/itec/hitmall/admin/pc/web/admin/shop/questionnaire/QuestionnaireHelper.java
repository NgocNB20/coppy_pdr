/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchForBackDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * アンケート検索画面Helper<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class QuestionnaireHelper {

    /**
     * 変換ユーティリティクラス
     */
    public final ConversionUtility conversionUtility;

    /**
     * 日付関連ユーティリティクラス
     */
    public final DateUtility dateUtility;

    /**
     * コンストラクタ
     *
     * @param conversionUtility 変換ユーティリティクラス
     * @param dateUtility       日付関連ユーティリティクラス
     */
    @Autowired
    public QuestionnaireHelper(ConversionUtility conversionUtility, DateUtility dateUtility) {
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
    }

    /**
     * 検索条件を画面に反映
     * 再検索用
     *
     * @param conditionDto       検索条件Dto
     * @param questionnaireModel 検索ページ
     */
    public void toPageForLoad(QuestionnaireSearchForBackDto conditionDto, QuestionnaireModel questionnaireModel) {

        /** アンケートSEQ*/
        if (conditionDto.getQuestionnaireSeq() != null) {
            questionnaireModel.setSearchQuestionnaireSeq(conditionDto.getQuestionnaireSeq().toString());
        }

        /** アンケートキー*/
        questionnaireModel.setSearchQuestionnaireKey(conditionDto.getQuestionnaireKey());

        /**アンケート名称 */
        questionnaireModel.setSearchName(conditionDto.getName());

        /**回答数（From）*/
        if (conditionDto.getReplyCountFrom() != null) {
            questionnaireModel.setSearchReplyCountFrom(conditionDto.getReplyCountFrom().toString());
        }

        /**回答数（To）*/
        if (conditionDto.getReplyCountTo() != null) {
            questionnaireModel.setSearchReplyCountTo(conditionDto.getReplyCountTo().toString());
        }

        /**サイトマップ出力 */
        questionnaireModel.setSearchSiteMapFlag(EnumTypeUtil.getValue(conditionDto.getSiteMapFlag()));

        /**管理メモ */
        questionnaireModel.setSearchMemo(conditionDto.getMemo());

        /**アンケート公開状態*/
        questionnaireModel.setSearchOpenStatus(EnumTypeUtil.getValue(conditionDto.getOpenStatus()));

        /**アンケート公開開始日時(From)*/
        questionnaireModel.setSearchOpenStartTimeFrom(conversionUtility.toYmd(conditionDto.getOpenStartTimeFrom()));

        /**アンケート公開開始日時(To)*/
        questionnaireModel.setSearchOpenStartTimeTo(conversionUtility.toYmd(conditionDto.getOpenStartTimeTo()));

        /**アンケート公開終了日時(From)*/
        questionnaireModel.setSearchOpenEndTimeFrom(conversionUtility.toYmd(conditionDto.getOpenEndTimeFrom()));

        /**アンケート公開終了日時(To)*/
        questionnaireModel.setSearchOpenEndTimeTo(conversionUtility.toYmd(conditionDto.getOpenEndTimeTo()));

        // PDR Migrate Customization from here
        // サイトマップ出力
        questionnaireModel.setSearchSiteMapFlag(EnumTypeUtil.getValue(conditionDto.getSiteMapFlag()));
        // PDR Migrate Customization to here
    }

    /**
     * 画面入力値から検索条件Dtoを作成する
     *
     * @param questionnaireModel 検索画面のページ情報
     * @return アンケート検索条件Dto(管理者用)
     */
    public QuestionnaireSearchForBackDto toQuestionnaireSearchForBackDaoConditionDtoForSearch(QuestionnaireModel questionnaireModel) {

        // 検索条件Dtoクラス 取得
        QuestionnaireSearchForBackDto conditionDto =
                        ApplicationContextUtility.getBean(QuestionnaireSearchForBackDto.class);

        /**ページ情報 limit offset order*/
        conditionDto.setPageInfo(questionnaireModel.getPageInfo());

        /** アンケートSEQ*/
        if (!StringUtils.isEmpty(questionnaireModel.getSearchQuestionnaireSeq())) {
            conditionDto.setQuestionnaireSeq(Integer.parseInt(questionnaireModel.getSearchQuestionnaireSeq()));
        }

        /** アンケートキー*/
        conditionDto.setQuestionnaireKey(questionnaireModel.getSearchQuestionnaireKey());

        /**アンケート名称 */
        conditionDto.setName(questionnaireModel.getSearchName());

        /**回答数（From）*/
        if (!StringUtils.isEmpty(questionnaireModel.getSearchReplyCountFrom())) {
            conditionDto.setReplyCountFrom(Integer.parseInt(questionnaireModel.getSearchReplyCountFrom()));
        }

        /**回答数（To）*/
        if (!StringUtils.isEmpty(questionnaireModel.getSearchReplyCountTo())) {
            conditionDto.setReplyCountTo(Integer.parseInt(questionnaireModel.getSearchReplyCountTo()));
        }

        /**サイトマップ出力*/
        conditionDto.setSiteMapFlag(EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class,
                                                                  questionnaireModel.getSearchSiteMapFlag()
                                                                 ));

        /**管理メモ */
        conditionDto.setMemo(questionnaireModel.getSearchMemo());

        /**アンケート公開状態*/
        conditionDto.setOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                 questionnaireModel.getSearchOpenStatus()
                                                                ));

        /**アンケート公開開始日時(From)*/
        conditionDto.setOpenStartTimeFrom(
                        conversionUtility.toTimeStampWithDefaultHms(questionnaireModel.getSearchOpenStartTimeFrom(),
                                                                    null, ConversionUtility.DEFAULT_START_TIME
                                                                   ));

        /**アンケート公開開始日時(To)*/
        if (questionnaireModel.getSearchOpenStartTimeTo() != null) {
            conditionDto.setOpenStartTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStampWithDefaultHms(questionnaireModel.getSearchOpenStartTimeTo(),
                                                                        null, ConversionUtility.DEFAULT_START_TIME
                                                                       )));
        }

        /**アンケート公開終了日時(From)*/
        conditionDto.setOpenEndTimeFrom(
                        conversionUtility.toTimeStampWithDefaultHms(questionnaireModel.getSearchOpenEndTimeFrom(), null,
                                                                    ConversionUtility.DEFAULT_END_TIME
                                                                   ));

        /**アンケート公開終了日時(To)*/
        if (questionnaireModel.getSearchOpenEndTimeTo() != null) {
            conditionDto.setOpenEndTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStampWithDefaultHms(questionnaireModel.getSearchOpenEndTimeTo(),
                                                                        null, ConversionUtility.DEFAULT_END_TIME
                                                                       )));
        }

        // PDR Migrate Customization from here
        conditionDto.setSiteMapFlag(EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class,
                                                                  questionnaireModel.getSearchSiteMapFlag()
                                                                 ));
        // PDR Migrate Customization to here

        return conditionDto;
    }

    /**
     * 検索結果をページに反映<br/>
     *
     * @param questionnaireResultList       アンケートエンティティリスト
     * @param questionnaireModel            アンケート検索ページ
     * @param questionnaireSearchForBackDto the questionnaire search for back dto
     */
    public void toPageForSearch(Timestamp maxRegistTime,
                                List<QuestionnaireSearchResultDto> questionnaireResultList,
                                QuestionnaireModel questionnaireModel,
                                QuestionnaireSearchForBackDto questionnaireSearchForBackDto) {

        /**回答集計日時*/
        questionnaireModel.setRegistTime(maxRegistTime);
        // オフセット + 1をNoにセット
        int index = questionnaireSearchForBackDto.getPageInfo().getOffset() + 1;
        List<QuestionnaireModelItem> resultItemList = new ArrayList<>();

        // 現在時刻取得
        Timestamp currentTime = dateUtility.getCurrentTime();

        for (QuestionnaireSearchResultDto dto : questionnaireResultList) {

            QuestionnaireModelItem questionnaireModelItem =
                            ApplicationContextUtility.getBean(QuestionnaireModelItem.class);

            /**No*/
            questionnaireModelItem.setResultNo(index++);

            /** アンケートSEQ*/
            questionnaireModelItem.setQuestionnaireSeq(dto.getQuestionnaireSeq());

            /** アンケートSEQ*/
            questionnaireModelItem.setQuestionnaireKey(dto.getQuestionnaireKey());

            /**アンケート名称 */
            questionnaireModelItem.setName(dto.getName());

            /**アンケート公開状態PC */
            questionnaireModelItem.setOpenStatusPc(dto.getOpenStatusPc().getValue());

            /** アンケート公開開始日時*/
            questionnaireModelItem.setOpenStartTime(dto.getOpenStartTime());

            /** アンケート公開終了日時 */
            questionnaireModelItem.setOpenEndTime(dto.getOpenEndTime());

            // PDR Migrate Customization from here
            /**サイトマップ出力 */
            questionnaireModelItem.setSiteMapFlag(dto.getSiteMapFlag());
            // PDR Migrate Customization to here

            /**回答数*/
            questionnaireModelItem.setReplyCount(dto.getReplyCount());

            /**アンケート表示状態*/

            // アンケート表示状態をアンケート表示状態(PC)とアンケート表示状態(モバイル)から取得
            boolean displayStatusPc = getDisplayStatusPc(dto, currentTime);
            questionnaireModelItem.setDisplayStatus(displayStatusPc);

            resultItemList.add(questionnaireModelItem);
        }

        questionnaireModel.setResultItems(resultItemList);
    }

    /**
     * アンケート表示ステータス(PC)取得処理<br/>
     * ・アンケート公開ステータス(PC)が非公開の場合、「非表示：０」
     * ・アンケート公開ステータス(PC)が公開中で、公開開始日が未来の場合、「非表示：０」
     * ・アンケート公開ステータス(PC)が公開中で、公開終了日が過去の場合、「非表示：０」
     * ・上記以外の場合、「表示中：１」
     *
     * @param dto         アンケート検索結果Dto
     * @param currentTime 現在時刻
     * @return アンケート表示ステータス 「非表示：０」、「表示中：１」
     */
    protected boolean getDisplayStatusPc(QuestionnaireSearchResultDto dto, Timestamp currentTime) {

        // アンケート公開ステータス(PC)取得
        HTypeOpenDeleteStatus openStatusPc = dto.getOpenStatusPc();
        // アンケート公開開始時間(PC)取得
        Timestamp openStartTime = dto.getOpenStartTime();
        // アンケート公開終了時間(PC)取得
        Timestamp openEndTime = dto.getOpenEndTime();

        return getDisplayStatus(currentTime, openStatusPc, openStartTime, openEndTime);
    }

    /**
     * アンケート表示ステータス取得処理<br/>
     * ・アンケート公開ステータスが非公開の場合、「非表示：０」
     * ・アンケート公開ステータスが公開中で、公開開始日が未来の場合、「非表示：０」
     * ・アンケート公開ステータスが公開中で、公開終了日が過去の場合、「非表示：０」
     * ・上記以外の場合、「表示中：１」
     *
     * @param currentTime   現在時刻
     * @param openStatus    アンケート公開ステータス
     * @param openStartTime アンケート公開開始日
     * @param openEndTime   アンケート公開終了日
     * @return アンケート表示ステータス 「非表示：０」、「表示中：１」
     */
    protected boolean getDisplayStatus(Timestamp currentTime,
                                       HTypeOpenDeleteStatus openStatus,
                                       Timestamp openStartTime,
                                       Timestamp openEndTime) {
        if (HTypeOpenDeleteStatus.NO_OPEN.equals(openStatus)) {
            // 公開ステータスが非公開の場合、「非表示：０」
            return false;
        }
        return dateUtility.isOpen(openStartTime, openEndTime, currentTime);
    }

}
