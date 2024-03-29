// PDR Migrate Customization from here

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import org.springframework.stereotype.Component;

/**
 * PDR#032 配送状況・注文履歴の基幹連携
 * 注文履歴（出荷済）画面Dxoクラス
 * 注文連携Web-APIより取得した情報を画面に表示
 *
 * @author s.kume
 */
@Component
public class ShippedHelper extends BaseShipHelper {

    public ShippedHelper(ConversionUtility conversionUtility) {
        super(conversionUtility);
    }

}
// PDR Migrate Customization to here
