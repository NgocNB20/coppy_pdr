/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api;

import lombok.Data;
import net.arnx.jsonic.JSONHint;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Cuenote API
 * メール文書セット複製APIリクエストDTO
 *
 * @author st75001
 */
@Data
@Component
@Scope("prototype")
public class CuenoteApiMailSetRequestDto implements Serializable {

    /** serialVersionUID */
    public static final long serialVersionUID = 1L;

    /** メールテンプレートのメール文書セットＩＤ（必須） */
    @JSONHint(name = "original_mail_id")
    public String originalMailId;

    /** メール文書セット名（必須） */
    @JSONHint(name = "title")
    public String title;

    /** 備考 */
    @JSONHint(name = "comment")
    public String comment;

    /** 使用目的 */
    @JSONHint(name = "purpose")
    public String purpose;
}