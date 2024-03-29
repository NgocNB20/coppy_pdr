// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * プロモーション
 * </pre>
 */
public interface WebApiGetPromotionInformationLogic {
    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "プロモーション";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.order.getpromotioninformation";

    /** WEB-API ステータス：1(正常（（プロモーション対象外））) */
    public static final String WEB_API_STATUS_SUCCESS_EXCLUDED = "1";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
// PDR Migrate Customization to here