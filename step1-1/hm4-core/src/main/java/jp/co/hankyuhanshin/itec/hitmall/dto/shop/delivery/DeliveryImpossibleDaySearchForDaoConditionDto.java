/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery;

import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * お届け不可日Dao用検索条件Dtoクラス
 *
 * @author DtoGenerator
 */
@Data
@Component
@Scope("prototype")
public class DeliveryImpossibleDaySearchForDaoConditionDto extends AbstractConditionDto {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 年
     */
    private Integer year;

    /**
     * 配送方法SEQ
     */
    private Integer deliveryMethodSeq;
}
