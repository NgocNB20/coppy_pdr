/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadBottomGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCZenkaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateGreaterEqual;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hmbase.constant.RegularExpressionsConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

/**
 * 会員検索モデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@HVRDateGreaterEqual(target = "endDate", comparison = "startDate",
                     groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
public class MemberInfoModel extends AbstractModel {

    /**
     * 不正操作：AMX000102
     */
    public static final String MSGCD_ILLEGAL_OPERATION = "AMX000102";

    /**
     * CSV選択DL時チェックされているデータがない：AMX000103
     */
    public static final String MSGCD_NOT_SELECTED_DATE = "AMX000103";

    /**
     * 期間プルダウンが選択されていて、日付に入力がない時：AMX000104W
     */
    public static final String MSGCD_PERIOD_REQUIRED = "AMX000104W";

    /**
     * 正規表現エラー：AMX000105W
     */
    public static final String MSGCD_REGULAR_EXPRESSION_ERR = "AMX000105W";

    /**
     * 選択なしメッセージ：AMX000101
     */
    public static final String MSGCD_NO_CHECK = "AMX000101";

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
     * Csv出力限界値<br/>
     * ※デフォルト-1=無制限とする
     */
    private Integer csvLimit;

    /* 検索条件項目 */
    /**
     * 会員氏名<br/>
     */
    @HCZenkaku
    private String memberInfoName;

    /**
     * 会員ID<br/>
     */
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String memberInfoId;

    /**
     * 会員SEQ
     */
    @HVNumber(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Digits(integer = 8, fraction = 0, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String searchMemberInfoSeq;

    /**
     * 性別<br/>
     */
    @HVItems(target = HTypeSexUnnecessaryAnswer.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String memberInfoSex;

    /**
     * 性別選択値<br/>
     */
    private Map<String, String> memberInfoSexItems;

    /**
     * 生年月日<br/>
     */
    @HVDate(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCDate
    private String memberInfoBirthday;

    /**
     * 会員状態<br/>
     */
    @HVItems(target = HTypeMemberInfoStatus.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String memberInfoStatus;

    /**
     * 会員状態選択値<br/>
     */
    private Map<String, String> memberInfoStatusItems;

    /**
     * 会員Tel<br/>
     */
    @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
             message = "{HTelephoneNumberValidator.INVALID_detail}",
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String memberInfoTel;

    /**
     * 郵便番号<br/>
     */
    @Pattern(regexp = "^[0-9]*$", message = "{AMX000105W}",
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String memberInfoZipCode;

    /**
     * 都道府県<br/>
     */
    private String memberInfoPrefecture;

    /**
     * 都道府県アイテム<br/>
     */
    private Map<String, String> memberInfoPrefectureItems;

    /**
     * 会員住所<br/>
     */
    @HCZenkaku
    private String memberInfoAddress;

    /**
     * 期間選択<br/>
     * 動的バリデータあり。
     */
    @HVItems(target = HTypeMemberInfoStatus.class)
    private String periodType;

    /**
     * 期間選択リスト<br/>
     */
    private Map<String, String> periodTypeItems;

    /**
     * 開始時間<br/>
     */
    @HVDate(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCDate
    private String startDate;

    /**
     * 終了時間<br/>
     */
    @HVDate(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCDate
    private String endDate;

    /**
     * 選択された会員SEQ<br/>
     */
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String checkSeqArray;

    /**
     * 最終ログインユーザーエージェント
     */
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String lastLoginUserAgent;

    /**
     * 最終ログインデバイス
     */
    @HVItems(target = HTypeDeviceType.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String[] lastLoginDeviceType;

    /**
     * 最終ログインデバイス選択値
     */
    private Map<String, String> lastLoginDeviceTypeItems;

    /**
     * 本会員フラグ
     */
    private boolean mainMemberFlag;

    /* 検索結果項目 */
    /**
     * 検索一覧<br/>
     */
    private List<MemberInfoResultItem> resultItems;

    /**
     * 全件出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = AllDownloadGroup.class)
    private String memberOutData;

    /**
     * 全件出力タイプアイテム<br />
     */
    private Map<String, String> memberOutDataItems;

    /**
     * 選択出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = DownloadTopGroup.class)
    private String checkedMemberOutData1;

    /**
     * 選択出力タイプアイテム<br />
     */
    private Map<String, String> checkedMemberOutData1Items;

    /**
     * 選択出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = DownloadBottomGroup.class)
    private String checkedMemberOutData2;

    /**
     * 選択出力タイプアイテム<br />
     */
    private Map<String, String> checkedMemberOutData2Items;

    // PDR Migrate Customization from here
    /**
     * 顧客番号<br/>
     */
    @HVNumber(groups = SearchGroup.class)
    @Digits(fraction = 0, integer = 9, groups = SearchGroup.class)
    private String customerNo;

    /**
     * DMによるおトク情報選択値
     */
    private Map<String, String> sendDirectMailFlagItems;

    /**
     * ダイレクトメール送信希望フラグ<br/>
     */
    @HVItems(target = HTypeSendDirectMailFlag.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String sendDirectMailFlag;

    /**
     * FAX配信希望フラグ<br/>
     */
    @HVItems(target = HTypeSendFaxPermitFlag.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String sendFaxPermitFlag;

    /**
     * FAXによるおトク情報選択値
     */
    private Map<String, String> sendFaxPermitFlagItems;

    /**
     * 案内メール希望
     */
    @HVItems(target = HTypeSendMailPermitFlag.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String sendMailPermitFlag;

    /**
     *
     */
    private Map<String, String> sendMailPermitFlagItems;

    /**
     * 顧客区分<br/>
     */
    @HVItems(target = HTypeBusinessType.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String[] businessType;

    /**
     * 顧客区分の選択項目
     */
    private Map<String, String> businessTypeItems;

    /**
     * オンライン登録フラグ<br/>
     */
    @HVItems(target = HTypeOnlineRegistFlag.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String onlineRegistFlag;

    /**
     * オンライン登録状態選択値
     */
    private Map<String, String> onlineRegistFlagItems;

    /**
     * 承認状態<br/>
     */
    @HVItems(target = HTypeApproveStatus.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String approveStatus;

    /**
     * 承認状態選択値
     */
    private Map<String, String> approveStatusItems;

    // PDR Migrate Customization to here

    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultItems() != null;
    }
}
