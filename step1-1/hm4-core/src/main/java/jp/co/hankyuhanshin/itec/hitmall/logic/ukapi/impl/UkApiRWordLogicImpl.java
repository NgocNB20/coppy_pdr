package jp.co.hankyuhanshin.itec.hitmall.logic.ukapi.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.AbstractUkApiResponseHeaderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiRWordRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiRWordResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiAddOrderInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.ukapi.AbstractUkApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.ukapi.UkApiRWordLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.JsonUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UK-API連携 関連ワードLogic実装クラス
 * @author tt32117
 */
@Component
public class UkApiRWordLogicImpl extends AbstractUkApiLogic implements UkApiRWordLogic {
    /**
     * コンストラクタ
     * @param jsonUtility
     */
    @Autowired
    public UkApiRWordLogicImpl(JsonUtility jsonUtility) {
        super(jsonUtility);
    }

    /**
     * UK-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    @Override
    public UkApiRWordResponseDto execute(UkApiRWordRequestDto requestDto) {
        // リクエストデータの作成
        Map<String, Object> valueMap = new HashMap<String, Object>();
        addParam("kw", requestDto.getKw(), valueMap);
        addParam("rows", String.valueOf(requestDto.getRows()), valueMap);

        // UK-API呼出し処理
        String res = connect(valueMap);

        // 処理結果が空の場合
        if (StringUtils.isEmpty(res)) {
            log.error(createLogMessage("UK-APIの接続に失敗しました。"));
            // エラーページへ遷移
            throwMessage(MSG_ERR_CODE);
        }

        // レスポンスjsonデータを整形
        UkApiRWordResponseDto dto = createRes(res);
        AbstractUkApiResponseHeaderDto header = dto.getResponseHeader();

        // JSON 変換エラー
        if (dto == null) {
            log.error(createLogMessage("JSONへの変換に失敗しました。"));
            // エラーページへ遷移
            throwMessage(MSG_ERR_CODE);
        }

        // UK-API 処理結果 エラー
        if (!WEB_API_STATUS_SUCCESS.equals(header.getStatus())) {
            log.error(createLogMessage("処理結果ステータス：" + header.getStatus() + " エラーメッセージ:" + header.getErrorMessage()));
            // ヘッダーのレスポンス値（status、reqID）を利用するため、正常（200:OK）でfrontへ渡す。
        }

        // Dtoを返却
        return dto;
    }

    /**
     * レスポンスデータを整形します。
     *
     * @param res レスポンスjsonデータ
     * @return UK-API連携取得結果クラス
     */
    @Override
    protected UkApiRWordResponseDto createRes(String res) {
        UkApiRWordResponseDto responseDto = ApplicationContextUtility.getBean(UkApiRWordResponseDto.class);
        responseDto = (UkApiRWordResponseDto) jsonUtility.decode(res, responseDto);

        return responseDto;
    }

    /**
     * ロガークラスを取得する
     *
     * @return ロガークラス
     */
    @Override
    protected Logger getLogger() {
        return LoggerFactory.getLogger(UkApiRWordLogic.class);
    }

    /**
     * UK-API連携名称を取得する
     *
     * @return UK-API連携名称
     */
    @Override
    protected String getUkApiName() {
        return UK_API_NAME;
    }

    /**
     * 接続先URLを取得する
     *
     * @return 接続先URL
     */
    @Override
    protected String getUrl() {
        return PropertiesUtil.getSystemPropertiesValue(UK_API_URL_KEY);
    }
}
