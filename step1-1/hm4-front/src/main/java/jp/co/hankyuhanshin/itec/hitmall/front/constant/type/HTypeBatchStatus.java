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
 * バッチタスク状態：列挙型
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeBatchStatus implements EnumType {

    /**
     * 処理起動中
     */
    STARTING("処理起動中", "STARTING"),
    /**
     * 処理中（旧HIT-MALLでは「UNDER_PROCESSING」）
     */
    STARTED("処理中", "STARTED"),
    /**
     * 正常終了（旧HIT-MALLでは「NORMAL_TERMINATION」）
     */
    COMPLETED("正常終了", "COMPLETED"),
    /**
     * 異常終了（旧HIT-MALLでは「ABNORMAL_TERMINATION」）
     */
    FAILED("異常終了", "FAILED"),
    /**
     * 停止中
     */
    STOPPING("停止中", "STOPPING"),
    /**
     * 停止済み
     */
    STOPPED("停止済み", "STOPPED"),
    /**
     * リスタート対象外
     */
    ABANDONED("リスタート対象外", "ABANDONED"),
    /**
     * その他
     */
    UNKNOWN("その他", "UNKNOWN");

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
    public static HTypeBatchStatus of(String value) {

        HTypeBatchStatus hType = EnumTypeUtil.getEnumFromValue(HTypeBatchStatus.class, value);
        return hType;
    }
}
