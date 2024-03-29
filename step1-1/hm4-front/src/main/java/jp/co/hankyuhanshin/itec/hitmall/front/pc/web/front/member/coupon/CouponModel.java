/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon;

import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * クーポン一覧 Modelクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
// 2023-renew No24 from here
public class CouponModel extends AbstractModel {

    /**
     * クーポンコード（入力）
     */
    @NotEmpty(message = "{PDR-0436-013-A-E}")
    @Pattern(regexp = "[0-9a-zA-Z]+", message = "{PDR-0436-012-A-E}")
    @Length(max = 10, message = "{PDR-0436-003-A-E}")
    private String couponCodeAdd;

    /**
     * クーポンコード（選択中）
     */
    private String couponCode;

    /**
     * 取得済みクーポン一覧
     */
    private List<CouponModelItem> couponItemList;

    /**
     * ログイン会員ID（cookie用）
     */
    private String loginUserId;

}
// 2023-renew No24 to here
