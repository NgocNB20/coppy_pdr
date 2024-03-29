/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * 注文登録ロジック<br/>
 *
 * @author ozaki
 * @version $Revision: 1.3 $
 */
public interface OrderRegisterLogic {

    /**
     * 受注情報登録<br/>
     *
     * @param dto 受注DTO
     */
    void execute(ReceiveOrderDto dto,
                 String memberInfoId,
                 HTypeSiteType siteType,
                 HTypeDeviceType deviceType,
                 Integer memberInfoSeq,
                 String userAgent,
                 String administratorLastName,
                 String administratorFirstName);
}
