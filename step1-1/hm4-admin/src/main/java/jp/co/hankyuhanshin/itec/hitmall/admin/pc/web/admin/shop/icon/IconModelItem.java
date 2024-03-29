/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.icon;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@Scope("prototype")
public class IconModelItem implements Serializable {

    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /************************************
     **  検索結果項目
     ************************************/
    /**
     * 表示順ラベル
     */
    private String orderDisplayRadioLabel;

    /**
     * 表示順値
     */
    private String orderDisplayRadioValue;

    /**
     * アイコンSEQ
     */
    private Integer iconSeq;

    /**
     * アイコン名
     */
    private String iconName;

    /**
     * カラーコード
     */
    private String colorCode;

}
