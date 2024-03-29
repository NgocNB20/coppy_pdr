// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 注文履歴 Model
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class BaseShipModel extends AbstractModel {

    /** お届け予定日 未定用 */
    public static final String RECEIVEDATE_PENDING = "未定";

    /** 注文履歴詳細情報リスト */
    private List<OrderHistoryOrderItem> orderHistoryOrderItems;

    /** 注文履歴詳細情報リストのインデックス */
    private int orderHistoryOrderIndex;

    // 2024-renew No06 from here
    /** 注文履歴詳細情報アイテムMAP */
    private Map<String, List<OrderHistoryOrderItem>> orderHistoryOrderItemByGoodsCodeMap;
    // 2024-renew No06 to here

    /**
     * 注文履歴の有無
     *
     * @return true..無 false..有
     */
    public boolean isOrderHistoryEmpty() {

        if (orderHistoryOrderItems == null) {
            return true;
        }
        return orderHistoryOrderItems.isEmpty();
    }

}
// PDR Migrate Customization to here
