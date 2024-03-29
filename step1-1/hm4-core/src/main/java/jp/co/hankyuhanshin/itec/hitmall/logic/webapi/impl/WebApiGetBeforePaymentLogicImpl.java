/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.webapi.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetBeforePaymentResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetBeforePaymentLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.JsonUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * WEB-API連携クラス
 * 前回支払方法取得
 */
@Component
// 2023-renew No14 from here
public class WebApiGetBeforePaymentLogicImpl extends AbstractWebApiLogic implements WebApiGetBeforePaymentLogic {

    /**
     * コンストラクタ
     *
     * @param jsonUtility
     */
    @Autowired
    public WebApiGetBeforePaymentLogicImpl(JsonUtility jsonUtility) {
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
     * @return WEB-API連携取得結果クラス(WebApiGetBeforePaymentResponseDto)
     */
    @Override
    protected AbstractWebApiResponseDto createRes(String res) {

        WebApiGetBeforePaymentResponseDto responseDto =
                        ApplicationContextUtility.getBean(WebApiGetBeforePaymentResponseDto.class);
        responseDto = (WebApiGetBeforePaymentResponseDto) jsonUtility.decode(res, responseDto);

        return responseDto;
    }

    /**
     * ロガークラスを取得する
     *
     * @return ロガークラス
     */
    @Override
    protected Logger getLogger() {

        return LoggerFactory.getLogger(WebApiGetBeforePaymentLogic.class);
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
// 2023-renew No14 to here
