// 2023-renew No11 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * 販売可否判定
 * </pre>
 */
public interface WebApiGetSaleCheckLogic {

    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "販売可否判定";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.goods.getsalecheck";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
// 2023-renew No11 to here
