/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.order;

import jp.co.hankyuhanshin.itec.hitmall.front.base.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 受注サマリDao用検索条件Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class OrderSummarySearchForDaoConditionDto extends AbstractConditionDto {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;
}
