/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.freearea.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.UploadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVMultipartFile;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUkFeedInfoSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * フリーエリア登録・更新画面
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FreeareaRegistUpdateModel extends AbstractModel {

    /**
     * 正規表現エラー
     */
    public static final String MSGCD_REGULAR_EXPRESSION_ERR = "{ASF000201W}";

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public FreeareaRegistUpdateModel() {
        super();
    }

    /**
     * 変更前フリーエリアエンティティ
     */
    private FreeAreaEntity originalFreeAreaEntity;

    /**
     * 変更箇所リスト
     */
    private List<String> modifiedList;

    /**
     * 表示対象会員リスト
     */
    private List<Integer> viewableMemberList;

    /**
     * 表示対象会員件数
     */
    private int viewableMemberCount;

    /**
     * アップロードファイル
     */
    @HVMultipartFile(fileExtension = {"csv"}, groups = {UploadGroup.class})
    private MultipartFile uploadFile;

    /**
     * フリーエリアエンティティ
     */
    private FreeAreaEntity freeAreaEntity;

    /**
     * バリデーション限度数
     */
    private Integer validLimit;

    /**
     * CSVレコード件数限度<br/>
     */
    private Integer recordLimit;

    /**
     * キー
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Pattern(regexp = "^[\\w]+$", message = MSGCD_REGULAR_EXPRESSION_ERR, groups = {ConfirmGroup.class})
    @Length(min = 1, max = 50, groups = {ConfirmGroup.class})
    private String freeAreaKey;

    /**
     * 公開開始日時（日付）
     */
    @HCDate
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVDate(groups = {ConfirmGroup.class})
    private String openStartDate;

    /**
     * 公開開始日時（時刻）
     */
    @HCDate(pattern = "HH:mm:ss")
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    private String openStartTime;

    /**
     * タイトル
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 100, groups = {ConfirmGroup.class})
    private String freeAreaTitle;

    /**
     * 本文PC
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    // 2023-renew No19 from here
    @Length(min = 0, max = 200000, groups = {ConfirmGroup.class})
    // 2023-renew No19 to here
    private String freeAreaBodyPc;

    /**
     * サイトマップ出力
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = {ConfirmGroup.class})
    @HVItems(target = HTypeSiteMapFlag.class, groups = {ConfirmGroup.class})
    private String siteMapFlag;

    /**
     * サイトマップ出力アイテム
     */
    private Map<String, String> siteMapFlagItems;

    /**
     * 対象商品
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HCHankaku
    private String targetGoods;

    /**
     * フリーエリアSEQ
     */
    private Integer freeAreaSeq;

    /**
     * フリーエリアSEQ(画面用)
     */
    private Integer scFreeAreaSeq;

    // 2023-renew No36-1, No61,67,95 from here
    /**
     * フィード情報送信
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeUkFeedInfoSendFlag.class, groups = {ConfirmGroup.class})
    private String ukFeedInfoSendFlag;

    /**
     * フィード情報送信アイテム
     */
    private Map<String, String> ukFeedInfoSendFlagItems;

    /**
     * 遷移先URL
     */
    @Length(min = 0, max = 100, groups = {ConfirmGroup.class})
    private String ukTransitionUrl;

    /**
     * コンテンツ画像URL
     */
    @Length(min = 0, max = 100, groups = {ConfirmGroup.class})
    private String ukContentImageUrl;

    /**
     * 検索キーワード
     */
    @Length(min = 0, max = 1000, groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String ukSearchKeyword;

    /**
     * コンテンツ画像（HTML表示用）
     */
    private String contentsImage;
    // 2023-renew No36-1, No61,67,95 to here

    /**
     * 前画面が確認画面であるかを判断するフラグ<br/>
     * true:確認画面
     */
    private boolean fromConfirm;

    /**
     * 不正操作を判断するためのフラグ
     */
    private boolean normality;

    /**
     * @return true = 更新フロー
     */
    public boolean isUpdatePage() {
        if (originalFreeAreaEntity == null || originalFreeAreaEntity.getFreeAreaSeq() == null) {
            return false;
        }
        return true;
    }

    /************************************
     ** 登録画面：特集ページ用URL
     ************************************/

    /**
     * @return the specialPageUrlPc
     */
    public String getSpecialPageUrlPcRegist() {
        if (originalFreeAreaEntity == null) {
            return null;
        }
        String url = PropertiesUtil.getSystemPropertiesValue("special.url.pc");
        url = MessageFormat.format(url, originalFreeAreaEntity.getFreeAreaKey());
        return url;
    }

    /**
     * @return the contentsPageUrlPc
     */
    public String getContentsPageUrlPcRegist() {
        if (originalFreeAreaEntity == null) {
            return null;
        }
        String url = PropertiesUtil.getSystemPropertiesValue("contents.url.pc");
        url = MessageFormat.format(url, originalFreeAreaEntity.getFreeAreaKey());
        return url;
    }

    /**
     * @return the topicPageUrlPc
     */
    public String getTopicPageUrlPcRegist() {
        if (originalFreeAreaEntity == null) {
            return null;
        }
        String url = PropertiesUtil.getSystemPropertiesValue("topic.url.pc");
        url = MessageFormat.format(url, originalFreeAreaEntity.getFreeAreaKey());
        return url;
    }

    /************************************
     ** 確認画面：特集ページ用URL
     ************************************/

    /**
     * @return the specialPageUrlPc
     */
    public String getSpecialPageUrlPc() {
        if (StringUtils.isEmpty(freeAreaKey)) {
            return null;
        }
        String url = PropertiesUtil.getSystemPropertiesValue("special.url.pc");
        url = MessageFormat.format(url, freeAreaKey);
        return url;
    }

    /**
     * @return the contentsPageUrlPc
     */
    public String getContentsPageUrlPc() {
        if (StringUtils.isEmpty(freeAreaKey)) {
            return null;
        }
        String url = PropertiesUtil.getSystemPropertiesValue("contents.url.pc");
        url = MessageFormat.format(url, freeAreaKey);
        return url;
    }

    /**
     * @return the topicPageUrlPc
     */
    public String getTopicPageUrlPc() {
        if (StringUtils.isEmpty(freeAreaKey)) {
            return null;
        }
        String url = PropertiesUtil.getSystemPropertiesValue("topic.url.pc");
        url = MessageFormat.format(url, freeAreaKey);
        return url;
    }
    // 2023-renew No36-1, No61,67,95 from here

    /************************************
     ** 登録画面：UK設定用URL
     ************************************/
    /**
     * @return true.. コンテンツ画像URL入力あり
     */
    public boolean isUkContentImage() {
        return !StringUtils.isEmpty(ukContentImageUrl);
    }

    /**
     * @return the transitionUrl
     */
    public String getTransitionUrl() {
        return PropertiesUtil.getSystemPropertiesValue("transition.url");
    }

    /**
     * @return the contentsImageSrc
     */
    public String getContentsImageSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.path.contents") + "/";
    }

    /**
     * @return contentsImageUrl
     */
    public String getContentsImageUrl() {
        return PropertiesUtil.getSystemPropertiesValue("real.images.path.contents");
    }
    // 2023-renew No36-1, No61,67,95 to here
}
