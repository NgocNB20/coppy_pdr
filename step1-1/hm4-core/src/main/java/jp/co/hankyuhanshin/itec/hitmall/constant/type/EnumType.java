/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.constant.type;

/**
 * enum区分インタフェース<br/>
 *
 * @author kaneda
 */
public interface EnumType {

    /**
     * @return ラベル
     */
    String getLabel();

    /**
     * @return 区分値
     */
    String getValue();
}
