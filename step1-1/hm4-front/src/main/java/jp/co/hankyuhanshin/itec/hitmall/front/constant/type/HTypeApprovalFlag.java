/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * PDR#432 【1.7次】基幹リニューアル対応　【要望No.11】　基幹お届け先との同期<br/>
 *
 * <pre>
 * 届け先承認フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeApprovalFlag implements EnumType {
    // PDR Migrate Customization from here
    /** 仮登録 */
    TEMP_MEMBER("仮登録", "0"),

    /** 本登録 */
    MAIN_MEMBER("本登録", "1"),

    /** 取扱中止 */
    SERVICE_STOP("取扱中止", "9");

    /**
     * ラベル<br/>
     */
    private String label;
    /**
     * 区分値<br/>
     */
    private String value;

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeApprovalFlag of(String value) {

        HTypeApprovalFlag hType = EnumTypeUtil.getEnumFromValue(HTypeApprovalFlag.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
