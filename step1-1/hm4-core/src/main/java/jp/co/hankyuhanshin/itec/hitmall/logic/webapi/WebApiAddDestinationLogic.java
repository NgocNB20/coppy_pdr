// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * PDR#432 【1.7次】基幹リニューアル対応　【要望No.11】　基幹お届け先との同期<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * お届け先登録
 * </pre>
 */
public interface WebApiAddDestinationLogic {

    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "お届け先登録";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.member.adddestination";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
// PDR Migrate Customization to here