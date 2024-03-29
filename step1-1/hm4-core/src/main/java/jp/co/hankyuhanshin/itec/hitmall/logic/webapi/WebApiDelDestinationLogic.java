// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * PDR#432 【1.7次】基幹リニューアル対応　【要望No.11】　基幹お届け先との同期<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * お届け先削除
 * </pre>
 */
public interface WebApiDelDestinationLogic {

    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "お届け先削除";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.member.deldestination";

    /** お届け先削除失敗エラー */
    public static final String MSGCD_ERR_DEL_DESTINATION = "SMA000301";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
// PDR Migrate Customization to here