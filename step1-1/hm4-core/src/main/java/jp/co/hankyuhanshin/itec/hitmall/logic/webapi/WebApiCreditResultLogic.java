// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * オーソリ結果連携
 * </pre>
 */
public interface WebApiCreditResultLogic {

    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "オーソリ結果連携";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.credit.creditresult";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
