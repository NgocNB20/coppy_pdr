/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order.index;

import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 受注インデックスDao用検索条件Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class OrderIndexSearchForDaoConditionDto extends AbstractConditionDto {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSEQ;

    /**
     * 受注コード
     */
    private String orderCode;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;
}
