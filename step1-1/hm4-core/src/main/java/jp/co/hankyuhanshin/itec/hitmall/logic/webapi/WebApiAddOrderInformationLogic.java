// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * 受注連携
 * </pre>
 */
public interface WebApiAddOrderInformationLogic {

    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "受注連携";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.order.addorderinformation";

    /** お届け先情報取得エラー */
    public static final String GET_DESTINATION_ERROR = "3";

    /** お届け先参照失敗エラー */
    public static final String MSGCD_ERR_GET_DESTINATION = "PDR-0432-004-A-";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
// PDR Migrate Customization to here