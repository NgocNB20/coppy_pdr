/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.validation.group.QuestionnaireSearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateGreaterEqual;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRNumberGreaterEqual;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchForBackDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchResultDto;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * アンケート検索モデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@HVRDateGreaterEqual(target = "searchOpenStartTimeTo", comparison = "searchOpenStartTimeFrom",
                     groups = {QuestionnaireSearchGroup.class})
@HVRDateGreaterEqual(target = "searchOpenEndTimeTo", comparison = "searchOpenEndTimeFrom",
                     groups = {QuestionnaireSearchGroup.class})
@HVRNumberGreaterEqual(target = "searchReplyCountTo", comparison = "searchReplyCountFrom",
                       groups = {QuestionnaireSearchGroup.class})
public class QuestionnaireModel extends AbstractModel {

    /**
     * 検索一覧
     */
    @Valid
    private List<QuestionnaireModelItem> resultItems;

    /**
     * ページ番号
     */
    private String pageNumber;

    /**
     * 最大表示件数
     */
    private int limit;

    /**
     * ソート項目
     */
    private String orderField;

    /**
     * ソート条件
     */
    private boolean orderAsc;

    /**
     * 検索結果総件数
     */
    private int totalCount;

    /**
     * 検索条件保持
     */
    private QuestionnaireSearchForBackDto questionnaireSearchForBackDaoConditionDto;

    /**
     * 検索結果保持
     */
    private QuestionnaireSearchResultDto questionnaireSearchResultDaoConditionDto;

    /**
     * 入金登録及び出荷登録結果メッセージリスト
     */
    private List<CheckMessageDto> checkMessageItems;

    /**
     * @param resultItems 受注一覧検索結果画面情報
     */
    public void setResultItems(List<QuestionnaireModelItem> resultItems) {
        this.resultItems = resultItems;
    }

    /************************************
     **  検索条件
     ************************************/

    /**
     * アンケートSEQ
     */
    @HVNumber(groups = {QuestionnaireSearchGroup.class})
    @Digits(integer = 8, fraction = 0, groups = {QuestionnaireSearchGroup.class})
    @HCHankaku
    private String searchQuestionnaireSeq;

    /**
     * アンケートキー
     */
    @Length(max = ValidatorConstants.LENGTH_QUESTIONNAIRE_KEY_MAXIMUM, groups = {QuestionnaireSearchGroup.class})
    @Pattern(regexp = ValidatorConstants.REGEX_QUESTIONNAIREKEY,
             message = ValidatorConstants.MSGCD_QUESTIONNAIREKEY_INVALID, groups = {QuestionnaireSearchGroup.class})
    private String searchQuestionnaireKey;

    /**
     * アンケート名称
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {QuestionnaireSearchGroup.class})
    @Length(max = ValidatorConstants.LENGTH_QUESTIONNAIRE_NAME_MAXIMUM, groups = {QuestionnaireSearchGroup.class})
    private String searchName;

    /**
     * 回答数（From）
     */
    @HVNumber(groups = {QuestionnaireSearchGroup.class})
    @Digits(integer = 8, fraction = 0, groups = {QuestionnaireSearchGroup.class})
    @HCHankaku
    private String searchReplyCountFrom;

    /**
     * 回答数（To）
     */
    @HVNumber(groups = {QuestionnaireSearchGroup.class})
    @Digits(integer = 8, fraction = 0, groups = {QuestionnaireSearchGroup.class})
    @HCHankaku
    private String searchReplyCountTo;

    /**
     * 回答集計日時
     */
    @HVDate(groups = {QuestionnaireSearchGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH_HMS)
    private Timestamp registTime;

    /**
     * サイトマップ出力
     */
    private String searchSiteMapFlag;

    /**
     * 管理メモ
     */
    @Length(max = ValidatorConstants.LENGTH_MANAGEMENT_MEMO_MAXIMUM)
    @HVSpecialCharacter(allowPunctuation = true)
    private String searchMemo;

    /**
     * アンケート公開状態
     */
    private String searchOpenStatus;

    /**
     * アンケート公開開始日時(From)
     */
    @HCDate
    @HVDate(groups = {QuestionnaireSearchGroup.class, DisplayChangeGroup.class})
    private String searchOpenStartTimeFrom;

    /**
     * アンケート公開開始日時(To)
     */
    @HCDate
    @HVDate(groups = {QuestionnaireSearchGroup.class, DisplayChangeGroup.class})
    private String searchOpenStartTimeTo;

    /**
     * アンケート公開終了日時(From)
     */
    @HCDate
    @HVDate(groups = {QuestionnaireSearchGroup.class, DisplayChangeGroup.class})
    private String searchOpenEndTimeFrom;

    /**
     * アンケート公開終了日時(To)
     */
    @HCDate
    @HVDate(groups = {QuestionnaireSearchGroup.class, DisplayChangeGroup.class})
    private String searchOpenEndTimeTo;

    /************************************
     **  プルダウンリスト
     ************************************/
    /**
     * アンケート公開状態
     */
    private Map<String, String> searchOpenStatusItems;

    /**
     * サイトマップ出力
     */
    private Map<String, String> searchSiteMapFlagItems;

    /************************************
     **  ソート項目
     ************************************/
    /**
     * ソート：アンケートSEQ
     */
    private String questionnaireSeqSort;

    /**
     * ソート：アンケートキー
     */
    private String questionnaireKeySort;

    /**
     * ソート：アンケート名称
     */
    private String nameSort;

    /**
     * ソート：アンケート公開開始日時
     */
    private String openStartTimeSort;

    /**
     * ソート：アンケート公開終了日時
     */
    private String openEndTimeSort;

    /**
     * ソート：サイトマップ出力
     */
    private String siteMapFlagSort;

    /**
     * ソート：回答数
     */
    private String replyCountSort;

    /************************************
     **  検索結果表示項目
     ************************************/

    private Integer resultNo;

    private Integer questionnaireSeq;

    private String questionnaireKey;

    private String name;

    private HTypeOpenDeleteStatus openStatusPc;

    private Integer replyCount;

    /*
     * 画面表示項目以外
     */
    private String displayStatusPc;

    private boolean displayStatus;

    /* 削除対象アンケートSEQ */
    private Integer deleteQuestionnaireSeq;

    /**
     * 詳細画面、登録更新画面へのリンクに出力するアンケートSEQ。<br />
     * <pre>
     * 詳細画面、登録更新画面htmlで seqパラメータを参照するために必要。
     * </pre>
     *
     * @return アンケートSEQ
     */
    public Integer getSeq() {
        return questionnaireSeq;
    }

    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultItems() != null;
    }

    /**
     * @return true=新規登録, false=修正
     */
    public boolean isRegistTime() {
        return registTime != null;
    }
}
