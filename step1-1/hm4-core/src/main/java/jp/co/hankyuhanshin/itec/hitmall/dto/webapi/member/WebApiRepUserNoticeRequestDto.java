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
 * PDR#429 1.7次　基幹リニューアル対応　【要望No.6,7,8】　フロント会員更新追加<br/>
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * 会員お知らせ情報更新
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
// PDR Migrate Customization from here
@Component
@Scope("prototype")
// PDR Migrate Customization to here
public class WebApiRepUserNoticeRequestDto extends AbstractWebApiRequestDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 顧客番号 */
    private Integer customerNo;

    /** Eメールによる情報提供 */
    private String mailPermitFlag;

    /** 金属メール希望フラグ */
    private String metalPermitFlag;
    // PDR Migrate Customization to here
    // 2023-renew No85-1 from here
    /** 休診曜日 */
    private String nonConsultationDay;

    /** 診療内容 */
    private String treatmentType;

    /** その他診療項目 */
    private String treatmentTypeMemo;
    // 2023-renew No85-1 to here
}
