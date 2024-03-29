/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order;

import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * WEB-API連携レスポンスDTOクラス
 * 出荷予定日取得
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
// 2023-renew No14 from here
public class WebApiGetShipmentDateResponseDto extends AbstractWebApiResponseDto {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 出荷予定日取得 詳細情報 */
    private List<WebApiGetShipmentDateResponseDetailDto> info;

    /**
     * 出荷予定日を「商品コード + | + 配達指定日」をキーにしたMAPで返却します。
     * @return 出荷予定日MAP
     */
    public Map<String, Timestamp> getShipmentDateMap() {
        if (this.info == null || this.info.size() == 0) {
            return new HashMap<>();
        }
        Map<String, Timestamp> map = new HashMap<>();
        OrderUtility orderUtility = ApplicationContextUtility.getBean(OrderUtility.class);
        for (WebApiGetShipmentDateResponseDetailDto dto : this.info) {
            map.put(
                            orderUtility.getShipmentDateMapKey(dto.getGoodsCode(), dto.getDeliveryDesignatedDay()),
                            dto.getShipmentDate()
                   );
        }
        return map;
    }

}
// 2023-renew No14 to here
