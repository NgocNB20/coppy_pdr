/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.conveni;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUseConveni;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * コンビニ名称クラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class ConvenienceEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * コンビニSEQ（必須）
     */
    private Integer conveniSeq;

    /**
     * 支払コード（必須）
     */
    private String payCode;

    /**
     * コンビニコード（必須）
     */
    private String conveniCode;

    /**
     * 支払名称（必須）
     */
    private String payName;

    /**
     * コンビニ名称（必須）
     */
    private String conveniName;

    /**
     * ショップSEQ（必須）
     */
    private Integer shopSeq;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;

    private Integer displayOrder;

    /**
     * 利用するコンビニ
     */
    private HTypeUseConveni useFlag;
}
