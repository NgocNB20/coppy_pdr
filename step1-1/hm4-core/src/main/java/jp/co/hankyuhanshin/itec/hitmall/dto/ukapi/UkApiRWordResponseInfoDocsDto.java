/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.ukapi;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import net.arnx.jsonic.JSONHint;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * UK-API連携 関連ワードDocsレスポンスDto
 * @author tt32117
 */
@Data
@Component
@Scope("prototype")
public class UkApiRWordResponseInfoDocsDto implements Serializable {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 関連ワード */
    @JSONHint(ordinal = 10, name = "item")
    private List<UkApiRWordResponseInfoItemDto> item;
}
