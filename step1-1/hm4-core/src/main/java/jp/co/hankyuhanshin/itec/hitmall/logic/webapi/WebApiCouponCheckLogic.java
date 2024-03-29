// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * PDR#436 【1.7次】基幹リニューアル対応　【要望No.22】　値引きクーポンの対応<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * クーポン有効性チェック
 * </pre>
 */
public interface WebApiCouponCheckLogic {

    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "クーポン有効性チェック";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.order.couponcheck";

    /** クーポン取得エラー */
    public static final String GET_COUPON_ERROR = "1";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
// PDR Migrate Customization to here
