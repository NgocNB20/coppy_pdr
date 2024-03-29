/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.addattribute;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAddressType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * PDR#11 08_データ連携（顧客情報）会員情報の項目追加・変更<br/>
 * 会員追加属性CSVDTOクラス<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Entity
@Data
@Component
@Scope("prototype")
public class MemberAddAttributeCsvDto implements Serializable {

    // PDR Migrate Customization from here
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 会員SEQ（必須） */
    @CsvColumn(order = 10, columnLabel = "会員SEQ")
    private Integer memberInfoSeq;

    /** 会員状態（必須） */
    @CsvColumn(order = 20, columnLabel = "会員状態", enumOutputType = "value")
    private HTypeMemberInfoStatus memberInfoStatus;

    /** 会員状態ラベル（必須） */
    @CsvColumn(order = 30, columnLabel = "会員状態名称", enumOutputType = "label")
    private HTypeMemberInfoStatus memberInfoStatusLabel;

    /** 会員サイト種別（必須） */
    @CsvColumn(order = 40, columnLabel = "受付サイト", enumOutputType = "value")
    private HTypeSiteType memberInfoSiteType;

    /** 会員サイト種別ラベル（必須） */
    @CsvColumn(order = 50, columnLabel = "受付サイト名称", enumOutputType = "label")
    private HTypeSiteType memberInfoSiteTypeLabel;

    /** 会員ID */
    @CsvColumn(order = 60, columnLabel = "会員ID")
    private String memberInfoId;

    /** 最終ログイン日時 */
    @CsvColumn(order = 70, columnLabel = "最終ログイン日時", dateFormat = "yyyy/MM/dd HH:mm:ss")
    private Timestamp lastLoginTime;

    /** 会員氏名(姓)（必須） */
    @CsvColumn(order = 80, columnLabel = "会員姓")
    private String memberInfoLastName;

    /** 会員氏名(名） */
    @CsvColumn(order = 90, columnLabel = "会員名")
    private String memberInfoFirstName;

    /** 会員フリガナ(姓)（必須） */
    @CsvColumn(order = 100, columnLabel = "会員フリガナ姓")
    private String memberInfoLastKana;

    /** 会員フリガナ(名) */
    @CsvColumn(order = 110, columnLabel = "会員フリガナ名")
    private String memberInfoFirstKana;

    /** 会員性別 */
    @CsvColumn(order = 120, columnLabel = "性別", enumOutputType = "value")
    private HTypeSex memberInfoSex;

    /** 会員性別ラベル */
    @CsvColumn(order = 130, columnLabel = "性別名称", enumOutputType = "label")
    private HTypeSex memberInfoSexLabel;

    /** 会員生年月日 */
    @CsvColumn(order = 140, columnLabel = "生年月日")
    private Timestamp memberInfoBirthday;

    /** 会員TEL */
    @CsvColumn(order = 150, columnLabel = "電話番号")
    private String memberInfoTel;

    /** 会員連絡先TEL */
    @CsvColumn(order = 160, columnLabel = "連絡先電話番号")
    private String memberInfoContactTel;

    /** 会員FAX（必須） */
    @CsvColumn(order = 170, columnLabel = "FAX")
    private String memberInfoFax;

    /** 会員住所-郵便番号（必須） */
    @CsvColumn(order = 180, columnLabel = "郵便番号")
    private String memberInfoZipCode;

    /** 会員住所-都道府県（必須） */
    @CsvColumn(order = 190, columnLabel = "都道府県")
    private String memberInfoPrefecture;

    /** 会員住所-市区郡（必須） */
    @CsvColumn(order = 200, columnLabel = "市区郡")
    private String memberInfoAddress1;

    /** 会員住所-町村・番地 */
    @CsvColumn(order = 210, columnLabel = "町村・番地")
    private String memberInfoAddress2;

    /** 会員住所-マンション・建物名 */
    @CsvColumn(order = 220, columnLabel = "マンション・建物名")
    private String memberInfoAddress3;

    /** 会員メールアドレス */
    @CsvColumn(order = 230, columnLabel = "会員メールアドレス")
    private String memberInfoMail;

    /** メールアドレス種別 */
    @CsvColumn(order = 240, columnLabel = "メールアドレス種別", enumOutputType = "value")
    private HTypeAddressType addressType;

    /** メールアドレス種別 */
    @CsvColumn(order = 250, columnLabel = "メールアドレス種別名称", enumOutputType = "label")
    private HTypeAddressType addressTypeLabel;

    /** 案内メール希望 */
    @CsvColumn(order = 260, columnLabel = "案内メール希望", enumOutputType = "value")
    private HTypeSendMailPermitFlag sendMailPermitFlag;

    /** 案内メール希望ラベル */
    @CsvColumn(order = 270, columnLabel = "案内メール希望名称", enumOutputType = "label")
    private HTypeSendMailPermitFlag sendMailPermitFlagLabel;

    /**
     * 会員追加属性・会員追加属性選択結果<br />
     * 会員追加属性をラベル<br />
     * 会員追加属性選択結果をカラム値に<br />
     */
    @CsvColumn(order = 280, columnLabel = "")
    private String memberAddAttributeForCsv;

    /** 入会日Ymd */
    @CsvColumn(order = 290, columnLabel = "入会日")
    private String admissionYmd;

    /** 退会日Ymd */
    @CsvColumn(order = 300, columnLabel = "退会日")
    private String secessionYmd;

    /** 登録日時（必須） */
    @CsvColumn(order = 310, columnLabel = "登録日時", dateFormat = "yyyy/MM/dd HH:mm:ss")
    private Timestamp registTime;

    /** 更新日時（必須） */
    @CsvColumn(order = 320, columnLabel = "更新日時", dateFormat = "yyyy/MM/dd HH:mm:ss")
    private Timestamp updateTime;
    // PDR Migrate Customization to here
}
