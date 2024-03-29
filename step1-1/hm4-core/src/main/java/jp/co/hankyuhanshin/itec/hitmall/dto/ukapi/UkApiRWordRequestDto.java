/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.ukapi;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * UK-API連携 関連ワードリクエストDto
 * @author tt32117
 */
@Data
@Component
@Scope("prototype")
public class UkApiRWordRequestDto extends AbstractUkApiRequestDto {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 検索キーワード */
    private String kw;

    /** 検索結果数 */
    private Integer rows;
}
