// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * PDR#385 21_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * 注文履歴（過去1年）取得
 * </pre>
 */
public interface WebApiGetPreShipmentOrderHistoryAggregateLogic {

    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "注文履歴（過去1年）取得";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.order.getpreshipmentorderhistoryaggregate";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
// PDR Migrate Customization to here
