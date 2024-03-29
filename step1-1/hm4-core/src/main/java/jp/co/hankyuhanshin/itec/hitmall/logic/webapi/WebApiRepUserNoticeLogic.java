// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * PDR#429 1.7次　基幹リニューアル対応　【要望No.6,7,8】　フロント会員更新追加<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * 会員お知らせ情報更新
 * </pre>
 */
public interface WebApiRepUserNoticeLogic {

    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "会員お知らせ情報更新";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.member.repusernotice";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
// PDR Migrate Customization to here