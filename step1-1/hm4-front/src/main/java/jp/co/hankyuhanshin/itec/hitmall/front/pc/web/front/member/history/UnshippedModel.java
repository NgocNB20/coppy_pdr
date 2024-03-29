// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import lombok.Data;

/**
 * PDR#032 配送状況・注文履歴の基幹連携
 * 注文履歴（未出荷）画面ページModel
 * 注文連携Web-APIより取得した情報を画面に表示
 *
 * @author s.kume
 */
@Data
public class UnshippedModel extends BaseShipModel {

    // 2024-renew No06 from here
    /**
     * 表示フラグ: 商品コード
     */
    private boolean displayByGoodsCode;
    // 2024-renew No06 to here

    // 2023-renew No68 from here
    /**
     * キャンセル受注番号（ダイアログ用）
     */
    private String cancelledOrderNo;

    /**
     * お届け予定日（ダイアログ用）
     */
    private String cancelReceiveDate;
    // 2023-renew No68 to here

}
// PDR Migrate Customization to here
