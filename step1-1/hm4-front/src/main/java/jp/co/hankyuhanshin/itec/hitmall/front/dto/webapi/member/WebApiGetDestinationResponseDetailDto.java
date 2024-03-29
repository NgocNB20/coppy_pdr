/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * PDR#432 【1.7次】基幹リニューアル対応　【要望No.11】　基幹お届け先との同期<br/>
 *
 * <pre>
 * WEB-API連携レスポンスDTOクラス
 * お届け先参照詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetDestinationResponseDetailDto extends AbstractWebApiResponseDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** お届け先顧客番号 */
    private Integer receiveCustomerNo;

    /** お届け先事業所名 */
    private String officeName;

    /** お届け先事業所名フリガナ */
    private String officeKana;

    /** お届け先代表者名 */
    private String representative;

    /** お届け先顧客区分 */
    private Integer businessType;

    /** お届け先電話番号 */
    private String tel;

    /** お届け先郵便番号 */
    private String zipCode;

    /** お届け先住所１(都道府県・市区町村) */
    private String city;

    /** お届け先住所２(丁目・番地) */
    private String address;

    /** お届け先住所３(建物名・部屋番号) */
    private String building;

    /** お届け先住所４(方書１、２) */
    private String other;

    /** 承認フラグ */
    private String approvalFlag;
    // PDR Migrate Customization to here
}
