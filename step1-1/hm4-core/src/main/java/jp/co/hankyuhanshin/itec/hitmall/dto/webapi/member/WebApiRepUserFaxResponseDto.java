// 2023-renew No85-1 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 【要望No85-1】 新規登録時の情報入力フロー検討<br/>
 *
 * <pre>
 * WEB-API連携レスポンスDTOクラス
 * 会員FAX情報更新
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
public class WebApiRepUserFaxResponseDto extends AbstractWebApiResponseDto {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;
}
// 2023-renew No85-1 to here
