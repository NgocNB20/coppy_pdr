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
 * アドレス帳インポートAPIリクエストDTO
 *
 * @author st75001
 */
@Data
@Component
@Scope("prototype")
public class CuenoteApiAddressImportRequestDto implements Serializable {

    /** serialVersionUID */
    public static final long serialVersionUID = 1L;

    /** メールアドレス */
    @JSONHint(name = "email")
    public String email;

    /** 事業所名 */
    @JSONHint(name = "office_name")
    public String officeName;

    /** 商品情報 */
    @JSONHint(name = "goods_info")
    public String goodsInfo;
}