// 2023-renew No85-1 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * 【要望No85-1】 新規登録時の情報入力フロー検討<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * 会員FAX情報更新
 * </pre>
 */
public interface WebApiRepUserFaxLogic {

    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "会員FAX情報更新";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.member.repuserfax";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
// 2023-renew No85-1 to here
