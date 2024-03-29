/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * 会員情報登録
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiAddUserInformationRequestDto extends AbstractWebApiRequestDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 顧客番号 */
    private Integer customerNo;

    /** メールアドレス */
    private String mailAddress;

    /** 事業所名 */
    private String officeName;

    /** 事業所名フリガナ */
    private String officeKana;

    /** 代表者名 */
    private String representative;

    /** 顧客区分 */
    private Integer businessType;

    /** 電話番号 */
    private String tel;

    /** FAX番号 */
    private String fax;

    /** 郵便番号 */
    private String zipCode;

    /** 住所1 */
    private String city;

    /** 住所2 */
    private String address;

    /** 住所3 */
    private String building;

    /** 住所4 */
    private String other1;

    /** 住所5 */
    private String other2;

    /** 休診曜日 */
    private String nonConsultationDay;

    /** Eメールによる情報提供 */
    private Integer mailPermitFlag;

    /** FAXによる情報提供 */
    private Integer faxPermitFlag;

    /** DMによる情報提供 */
    private Integer sendDirectMailFlag;

    /** 歯科専売品販売区分 */
    private Integer dentalMonopolySalesType;

    /** 医療機器販売区分 */
    private Integer medicalEquipmentSalesType;

    /** 医薬品・注射針販売区分 */
    private Integer drugSalesType;

    /** 名簿区分 */
    private Integer memberListType;

    /** オンライン登録フラグ */
    private Integer onlineRegistFlag;

    /** 診療内容 */
    private String treatmentType;

    /** その他診療内容メモ */
    private String shinryoMemo;

    /** 金属メール希望フラグ */
    private String metalPermitFlag;

    /** 確認書類 */
    private Integer kakuninShoKbn;

    /** 経理区分 */
    private Integer keiriKbn;

    /** オンラインログイン可否 */
    private Integer onlineYesNo;
    // PDR Migrate Customization to here
}
