/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.news.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateCombo;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.ObjectUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * ニュース登録更新画面
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@HVRDateCombo(dateLeftTarget = "newsOpenStartDatePC", timeLeftTarget = "newsOpenStartTimePC",
              dateRightTarget = "newsOpenEndDatePC", timeRightTarget = "newsOpenEndTimePC",
              groups = {ConfirmGroup.class})
public class NewsRegistUpdateModel extends AbstractModel {

    /**
     * 正規表現エラー
     */
    public static final String MSGCD_REGULAR_EXPRESSION_ERR = "AYD000207W";

    /**
     * ニュース取得失敗エラー<br/>
     * <code>MSGCD_NEWS_GET_FAIL</code>
     */
    public static final String MSGCD_NEWS_GET_FAIL = "SSN000201";

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public NewsRegistUpdateModel() {
        super();
    }

    /**
     * 変更箇所リスト
     */
    private List<String> modifiedList;

    /**
     * 変更前ニュースエンティティ
     */
    private NewsEntity originalNewsEntity;

    /**
     * 表示対象会員リスト
     */
    private List<Integer> viewableMemberList;

    /**
     * 表示対象会員件数
     */
    private int viewableMemberCount;

    /**
     * バリデーション限度数
     */
    private Integer validLimit;

    /**
     * CSVレコード件数限度<br/>
     */
    private Integer recordLimit;

    /**
     * ニュースSEQ
     */
    private Integer newsSeq;

    /**
     * ニュースSEQ(画面用)
     */
    private Integer scNewsSeq;

    /**
     * ニュース日付(年月日)
     */
    @HCDate
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVDate(groups = {ConfirmGroup.class})
    private String newsDate;

    /**
     * ニュース日時(時刻)
     */
    @HCDate(pattern = "HH:mm:ss")
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    private String newsTime;

    /**
     * タイトル(PC)
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 200, groups = {ConfirmGroup.class})
    private String titlePC;

    /**
     * 本文(PC)
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE, groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 5000, groups = {ConfirmGroup.class})
    private String newsBodyPC;

    /**
     * 詳細(PC)
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE, groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 20000, groups = {ConfirmGroup.class})
    private String newsNotePC;

    /**
     * URL(PC)
     */
    private String newsUrlPC;

    /**
     * ニュース詳細種別
     */
    private String newsDetailsType;

    /**
     * タイトル(スマートフォン)
     */
    //    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    //    @Length(min = 0, max = 200, groups = {ConfirmGroup.class})
    private String titleSP;

    /**
     * 本文(スマートフォン)
     */
    //    @HVBothSideSpace(groups = {ConfirmGroup.class})
    //    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    //    @Length(min = 0, max = 5000, groups = {ConfirmGroup.class})
    private String newsBodySP;

    /**
     * 詳細(スマートフォン)
     */
    //    @HVBothSideSpace(groups = {ConfirmGroup.class})
    //    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    //    @Length(min = 0, max = 20000, groups = {ConfirmGroup.class})
    private String newsNoteSP;

    /**
     * URL(スマートフォン)
     */
    private String newsUrlSP;

    /**
     * ニュース公開状態(PC/スマートフォン)
     */
    @NotEmpty(groups = {ConfirmGroup.class}, message = "{HRequiredValidator.REQUIRED_detail}")
    @HVItems(target = HTypeOpenStatus.class, groups = {ConfirmGroup.class})
    private String newsOpenStatusPC;

    /**
     * ニュース公開開始日時(PC/スマートフォン 年月日)
     */
    @HCDate
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    private String newsOpenStartDatePC;

    /**
     * ニュース公開開始日時(PC/スマートフォン 時刻)
     */
    @HCDate(pattern = "HH:mm:ss")
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    private String newsOpenStartTimePC;

    /**
     * ニュース公開終了日時(PC/スマートフォン 年月日)
     */
    @HCDate
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    private String newsOpenEndDatePC;

    /**
     * ニュース公開終了日時(PC/スマートフォン 時刻)
     */
    @HCDate(pattern = "HH:mm:ss")
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    private String newsOpenEndTimePC;

    /**
     * タイトル(モバイル)
     */
    //    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    //    @Length(min = 0, max = 200, groups = {ConfirmGroup.class})
    private String titleMB;

    /**
     * 本文(モバイル)
     */
    //    @HVBothSideSpace(groups = {ConfirmGroup.class})
    //    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    //    @Length(min = 0, max = 5000, groups = {ConfirmGroup.class})
    private String newsBodyMB;

    /**
     * 詳細(モバイル)
     */
    //    @HVBothSideSpace(groups = {ConfirmGroup.class})
    //    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    //    @Length(min = 0, max = 20000, groups = {ConfirmGroup.class})
    private String newsNoteMB;

    /**
     * URL(モバイル)
     */
    private String newsUrlMB;

    /**
     * ニュース公開状態(モバイル)
     */
    //    @NotEmpty(groups = {ConfirmGroup.class})
    //    @HVItems(target = HTypeOpenStatus.class, groups = {ConfirmGroup.class})
    private String newsOpenStatusMB;

    /**
     * ニュース公開開始日時(モバイル 年月日)
     */
    //    @HCDate
    //    @HVDate(groups = {ConfirmGroup.class})
    //    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    private String newsOpenStartDateMB;

    /**
     * ニュース公開開始日時(モバイル 時刻)
     */
    //    @HCDate(pattern = "HH:mm:ss")
    //    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    //    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    private String newsOpenStartTimeMB;

    /**
     * ニュース公開終了日時(モバイル 年月日)
     */
    //    @HCDate
    //    @HVDate(groups = {ConfirmGroup.class})
    //    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    private String newsOpenEndDateMB;

    /**
     * ニュース公開終了日時(モバイル 時刻)
     */
    //    @HCDate(pattern = "HH:mm:ss")
    //    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    //    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    private String newsOpenEndTimeMB;

    /**
     * ニュースエンティティ
     */
    private NewsEntity newsEntity;

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
     * 画面表示モード
     */
    private String md;

    /** 遷移元画面 */
    private String fromView;

    /************************************
     ** 選択肢
     ************************************/
    /**
     * ニュース公開状態(PC)
     */
    private Map<String, String> newsOpenStatusPCItems;

    /**
     * ニュース公開状態(MB)
     */
    private Map<String, String> newsOpenStatusMBItems;

    /************************************
     ** 条件判定
     ************************************/

    /**
     * 表示する情報の有無判定
     *
     * @return true=表示する情報が存在する場合
     */
    public boolean isExistData() {

        // ニュースSEQ設定時に該当情報が取得されてなければ
        if (newsSeq != null && (newsEntity == null || !ObjectUtils.equals(newsEntity.getNewsSeq(), newsSeq))) {
            // エラー
            return false;
        }
        return true;
    }

    /**
     * ニュース詳細は選択されたラジオボタンに基づいてnewsUrlPC/newsNotePCの値のクリアを行う
     *
     * @param newsNotePC
     */
    public void setNewsNotePC(String newsNotePC) {
        if ("details".equalsIgnoreCase(this.newsDetailsType)) {
            this.newsUrlPC = null;
        }
        this.newsNotePC = newsNotePC;
    }

    /**
     * ニュース詳細は選択されたラジオボタンに基づいてnewsUrlPC/newsNotePCの値のクリアを行う
     *
     * @param newsUrlPC
     */
    public void setNewsUrlPC(String newsUrlPC) {
        if ("url".equalsIgnoreCase(this.newsDetailsType)) {
            this.newsNotePC = null;
        }
        this.newsUrlPC = newsUrlPC;
    }

    /**
     * ニュースタイトルPC表示チェック<br/>
     *
     * @return true:ニュースタイトルPCあり
     */
    public boolean isViewTitlePC() {
        return titlePC != null && !titlePC.equals("");
    }

    /**
     * ニュースリンクPCチェック(外部リンク)<br/>
     *
     * @return true:リンクあり
     */
    public boolean isNewsLinkPC() {
        return newsUrlPC != null;
    }

    /**
     * ニュースリンクPCチェック(ニュース詳細画面)<br/>
     *
     * @return true:リンクあり
     */
    public boolean isNewsDetailsLinkPC() {
        return newsNotePC != null;
    }

    /**
     * ニュース本文PC表示チェック<br/>
     *
     * @return true:本文PCあり
     */
    public boolean isViewNewsBodyPC() {
        return newsBodyPC != null && !newsBodyPC.equals("");
    }
}
