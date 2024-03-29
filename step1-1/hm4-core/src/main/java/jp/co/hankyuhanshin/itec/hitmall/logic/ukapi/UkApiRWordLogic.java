package jp.co.hankyuhanshin.itec.hitmall.logic.ukapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiRWordRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiRWordResponseDto;

/**
 * UK-API連携 関連ワードLogic
 * @author tt32117
 */
public interface UkApiRWordLogic {
    /** UK-API連携名称 */
    public static final String UK_API_NAME = "関連ワード";

    /** UK-API 接続URL 取得キー  */
    public static final String UK_API_URL_KEY = "ukapi.url.rword";

    /**
     * UK-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    UkApiRWordResponseDto execute(UkApiRWordRequestDto requestDto);
}
