/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.icon;

import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * アイコン詳細DTOクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Data
@Component
@Scope("prototype")
public class GoodsInformationIconDetailsDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * アイコンSEQ
     */
    private Integer iconSeq;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * アイコン名
     */
    private String iconName;

    /**
     * カラーコード
     */
    private String colorCode;

    /**
     * 表示順
     */
    private Integer orderDisplay;

}
