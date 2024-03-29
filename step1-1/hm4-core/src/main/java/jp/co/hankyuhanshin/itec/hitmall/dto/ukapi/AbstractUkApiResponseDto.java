/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.ukapi;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * UK-API連携 レスポンスDTO基底クラス
 * @author tt32117
 */
@Data
public abstract class AbstractUkApiResponseDto implements Serializable {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** レスポンスヘッダー */
    private AbstractUkApiResponseHeaderDto responseHeader;
}
