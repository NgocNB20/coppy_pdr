// 2023-renew No11 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetSaleCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.JsonUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携クラス
 * 販売可否判定
 * </pre>
 */
@Component
public class WebApiGetSaleCheckLogicImpl extends AbstractWebApiLogic implements WebApiGetSaleCheckLogic {

    /**
     * コンストラクタ
     *
     * @param jsonUtility
     */
    @Autowired
    public WebApiGetSaleCheckLogicImpl(JsonUtility jsonUtility) {
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

    @Override
    protected AbstractWebApiResponseDto createRes(String res) {
        WebApiGetSaleCheckResponseDto responseDto =
                        ApplicationContextUtility.getBean(WebApiGetSaleCheckResponseDto.class);
        responseDto = (WebApiGetSaleCheckResponseDto) jsonUtility.decode(res, responseDto);
        return responseDto;
    }

    /**
     * ロガークラスを取得する
     *
     * @return ロガークラス
     */
    @Override
    protected Logger getLogger() {
        return LoggerFactory.getLogger(WebApiGetSaleCheckLogic.class);
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
// 2023-renew No11 to here