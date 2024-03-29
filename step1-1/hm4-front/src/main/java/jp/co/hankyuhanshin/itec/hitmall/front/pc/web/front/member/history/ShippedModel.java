// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * PDR#032 配送状況・注文履歴の基幹連携
 * 注文履歴（出荷済）画面ページModel
 *
 * @author s.kume
 */
@Data
public class ShippedModel extends BaseShipModel {

    // 2023-renew No52 from here
    /**
     * 検索開始日年Items
     */
    private Map<String, String> searchStartDayYearItems;

    /**
     * 検索開始日月Items
     */
    private Map<String, String> searchStartDayMonthItems;

    /**
     * 検索終了日年Items
     */
    private Map<String, String> searchEndDayYearItems;

    /**
     * 検索終了日月Items
     */
    private Map<String, String> searchEndDayMonthItems;

    /**
     * 検索開始日年
     */
    @NotEmpty(message = "{xB000112}")
    private String searchStartDayYear;

    /**
     * 検索開始日月
     */
    @NotEmpty(message = "{xB000112}")
    private String searchStartDayMonth;

    /**
     * 検索終了日年
     */
    private String searchEndDayYear;

    /**
     * 検索終了日月
     */
    private String searchEndDayMonth;
    // 2023-renew No52 to here
}
// PDR Migrate Customization to here