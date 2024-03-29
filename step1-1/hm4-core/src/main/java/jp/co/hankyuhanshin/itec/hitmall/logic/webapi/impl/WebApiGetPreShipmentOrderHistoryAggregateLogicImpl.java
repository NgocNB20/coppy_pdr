// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetPreShipmentOrderHistoryAggregateLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.JsonUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PDR#385 21_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * 注文履歴（過去1年）取得
 * </pre>
 */
@Component
public class WebApiGetPreShipmentOrderHistoryAggregateLogicImpl extends AbstractWebApiLogic
                implements WebApiGetPreShipmentOrderHistoryAggregateLogic {

    /**
     * コンストラクタ
     *
     * @param jsonUtility
     */
    @Autowired
    public WebApiGetPreShipmentOrderHistoryAggregateLogicImpl(JsonUtility jsonUtility) {
        super(jsonUtility);
    }

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    @Override
    public AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto) {

        // 継承元のメソッド呼出
        return super.execute(requestDto);
    }

    /**
     * レスポンスデータを整形します。
     *
     * @param res レスポンスjsonデータ
     * @return WEB-API連携取得結果クラス(WebApiGetPreShipmentOrderHistoryResponseDto)
     */
    public AbstractWebApiResponseDto createRes(String res) {

        WebApiGetPreShipmentOrderHistoryResponseDto responseDto =
                        ApplicationContextUtility.getBean(WebApiGetPreShipmentOrderHistoryResponseDto.class);
        responseDto = (WebApiGetPreShipmentOrderHistoryResponseDto) jsonUtility.decode(res, responseDto);

        return responseDto;

    }

    /**
     * ロガークラスを取得する
     *
     * @return ロガークラス
     */
    @Override
    protected Logger getLogger() {

        return LoggerFactory.getLogger(WebApiGetPreShipmentOrderHistoryAggregateLogic.class);
    }

    /**
     * WEB-API連携名称を取得する
     *
     * @return WEB-API連携名称
     */

    @Override
    protected String getWebApiName() {
        return WEB_API_NAME;
    }

    /**
     * 接続先URLを取得する
     *
     * @return 接続先URL
     */
    @Override
    protected String getUrl() {
        return PropertiesUtil.getSystemPropertiesValue(WEB_API_URL_KEY);
    }
}
// PDR Migrate Customization to here
