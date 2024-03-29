/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.multipayment;

import com.gmo_pg.g_pay.client.input.SecureTranInput;
import org.springframework.stereotype.Component;

/**
 * HitMall用 XxxInput DTO
 *
 * @author tm27400
 */
@Component
public class HmSecureTranInput extends SecureTranInput {

    /**
     * シリアル
     */
    private static final long serialVersionUID = 1L;

    // HmPaymentClientInput は実装しない
}
