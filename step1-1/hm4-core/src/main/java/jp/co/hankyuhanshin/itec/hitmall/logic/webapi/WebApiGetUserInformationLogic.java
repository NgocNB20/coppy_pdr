// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * 会員変更情報取得
 * </pre>
 */
public interface WebApiGetUserInformationLogic {
    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "会員変更情報取得";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.member.getuserinformation";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
// PDR Migrate Customization to here