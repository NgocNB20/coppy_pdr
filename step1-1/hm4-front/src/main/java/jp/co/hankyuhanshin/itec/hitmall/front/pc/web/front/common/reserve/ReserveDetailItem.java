/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.validation.group.ReserveGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * セールde予約情報 詳細Item
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
// 2023-renew No14 from here
public class ReserveDetailItem extends AbstractModel implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 取りおきお届け希望日(入力)
     */
    @HVDate(groups = {ReserveGroup.class})
    @HCDate
    private String reserveDeliveryDate;

    /**
     * 数量(入力)
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ReserveGroup.class})
    @HCNumber
    private String inputGoodsCount;

}
// 2023-renew No14 to here
