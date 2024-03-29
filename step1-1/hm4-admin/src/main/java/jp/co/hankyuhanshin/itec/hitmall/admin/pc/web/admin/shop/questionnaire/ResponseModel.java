package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateGreaterEqual;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

/**
 * アンケート回答出力ページクラス。<br />
 * <pre>
 * 回答出力の検索項目を表示する。
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@HVRItems(target = "site", comparison = "siteItems")
@HVRItems(target = "searchOpenStatus", comparison = "searchOpenStatusItems")
@HVRItems(target = "searchDeviceTypeArray", comparison = "searchDeviceTypeArrayItems")
@HVRDateGreaterEqual(target = "searchOpenStartTimeTo", comparison = "searchOpenStartTimeFrom",
                     groups = {AllDownloadGroup.class})
@HVRDateGreaterEqual(target = "searchOpenEndTimeTo", comparison = "searchOpenEndTimeFrom",
                     groups = {AllDownloadGroup.class})
@HVRDateGreaterEqual(target = "searchRegistTimeTo", comparison = "searchRegistTimeFrom",
                     groups = {AllDownloadGroup.class})
public class ResponseModel extends AbstractModel {

    /**
     * アンケートSEQ
     */
    private Integer questionnaireSeq;
    /************************************
     **  検索条件
     ************************************/

    /**
     * サイト
     */
    private String site;

    /**
     * サイトアイテム
     */

    private List<Map<String, String>> siteItems;

    /**
     * アンケートSEQ
     */
    @HVNumber(groups = {AllDownloadGroup.class})
    @Digits(integer = 8, fraction = 0, groups = {AllDownloadGroup.class})
    @HCHankaku
    private String searchQuestionnaireSeq;

    /**
     * アンケートキー
     */
    @Length(min = 0, max = ValidatorConstants.LENGTH_QUESTIONNAIRE_KEY_MAXIMUM, groups = {AllDownloadGroup.class})
    @Pattern(regexp = ValidatorConstants.REGEX_QUESTIONNAIREKEY, groups = {AllDownloadGroup.class})
    private String searchQuestionnaireKey;

    /**
     * アンケート名称
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {AllDownloadGroup.class})
    @Length(min = 0, max = ValidatorConstants.LENGTH_QUESTIONNAIRE_NAME_MAXIMUM, groups = {AllDownloadGroup.class})
    private String searchName;

    /**
     * アンケート公開状態
     */
    @HVItems(target = HTypeOpenStatus.class, groups = {AllDownloadGroup.class})
    private String searchOpenStatus;

    /**
     * アンケート公開開始日時(From)
     */
    @HVDate(groups = {AllDownloadGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchOpenStartTimeFrom;

    /**
     * アンケート公開開始日時(To)
     */
    @HVDate(groups = {AllDownloadGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchOpenStartTimeTo;

    /**
     * アンケート公開終了日時(From)
     */
    @HVDate(groups = {AllDownloadGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchOpenEndTimeFrom;

    /**
     * アンケート公開終了日時(To)
     */
    @HVDate(groups = {AllDownloadGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchOpenEndTimeTo;

    /**
     * 受付デバイス
     */
    private String[] searchDeviceTypeArray;

    /**
     * 受付日時(From)
     */
    @HVDate(groups = {AllDownloadGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchRegistTimeFrom;

    /**
     * 受付日時(To)
     */
    @HVDate(groups = {AllDownloadGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchRegistTimeTo;

    /**
     * 受注番号
     */
    @Pattern(regexp = ValidatorConstants.REGEX_ORDER_CODE, groups = {AllDownloadGroup.class})
    @Length(min = 0, max = ValidatorConstants.LENGTH_ORDER_CODE_MAXIMUM, groups = {AllDownloadGroup.class})
    private String searchOrderCode;

    /**
     * 会員SEQ
     */
    @HVNumber(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Digits(integer = 8, fraction = 0, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String searchMemberInfoSeq;

    /**
     * 会員ID
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String searchMemberInfoId;

    /************************************
     **  プルダウンリスト
     ************************************/
    /**
     * アンケート公開状態
     */
    private Map<String, String> searchOpenStatusItems;

    /**
     * 受付デバイス
     */
    private Map<String, String> searchDeviceTypeArrayItems;
}
