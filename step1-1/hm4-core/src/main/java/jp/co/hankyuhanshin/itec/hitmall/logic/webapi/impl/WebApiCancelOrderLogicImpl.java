/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.webapi.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiCancelOrderResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiCancelOrderResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiCancelOrderLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.JsonUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * WEB-API連携クラス
 * 注文キャンセル
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
// 2023-renew No68 from here
public class WebApiCancelOrderLogicImpl extends AbstractWebApiLogic implements WebApiCancelOrderLogic {

    /**
     * コンストラクタ
     *
     * @param jsonUtility
     */
    @Autowired
    public WebApiCancelOrderLogicImpl(JsonUtility jsonUtility) {
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

        // WEB-API呼出し処理
        String res = connect(requestDto);

        // 処理結果が空の場合
        if (StringUtils.isEmpty(res)) {
            log.error(createLogMessage("WEB-APIの接続に失敗しました。"));
            // エラーページへ遷移
            throwMessage(MSG_ERR_CODE);
        }

        // レスポンスjsonデータを整形
        AbstractWebApiResponseDto dto = createRes(res);

        // JSON 変換エラー
        if (dto == null) {
            log.error(createLogMessage("JSONへの変換に失敗しました。"));
            // エラーページへ遷移
            throwMessage(MSG_ERR_CODE);
        }

        // WEB-API 処理結果 エラー
        if (!WEB_API_STATUS_SUCCESS.equals(dto.getResult().getStatus())) {
            log.error(createLogMessage(
                            "処理結果ステータス：" + dto.getResult().getStatus() + " メッセージ:" + dto.getResult().getMessage()));
            if (!WEB_API_STATUS_ERROR.equals(dto.getResult().getStatus())) {
                // ECサイト側ではステータス値が"0"、"99"以外はメッセージをそのまま画面に表示
                throwMessage(MSG_ERR_CODE_RETURN_MESSAGE, new Object[] {dto.getResult().getMessage()});
            } else {
                throwMessage(MSG_ERR_CODE);
            }
        }

        // レスポンスDTO取得エラー
        List<WebApiCancelOrderResponseDetailDto> info = ((WebApiCancelOrderResponseDto) dto).getInfo();
        if (CollectionUtil.isEmpty(info)) {
            throwMessage(MSG_ERR_CODE);
        }

        // 注文キャンセル失敗 エラー
        for (WebApiCancelOrderResponseDetailDto detailDto : info) {
            if (detailDto.getCancelResult() != WebApiCancelOrderLogic.CANCEL_SUCCESS) {
                throwMessage(MSGCD_CANCEL_ORDER_FAIL);
            }
        }

        // Dtoを返却
        return dto;
    }

    /**
     * レスポンスデータを整形します。
     *
     * @param res レスポンスjsonデータ
     * @return WEB-API連携取得結果クラス(WebApiCancelOrderResponseDto)
     */
    public AbstractWebApiResponseDto createRes(String res) {

        WebApiCancelOrderResponseDto responseDto =
                        ApplicationContextUtility.getBean(WebApiCancelOrderResponseDto.class);
        responseDto = (WebApiCancelOrderResponseDto) jsonUtility.decode(res, responseDto);

        return responseDto;

    }

    /**
     * ロガークラスを取得する
     *
     * @return ロガークラス
     */
    @Override
    protected Logger getLogger() {

        return LoggerFactory.getLogger(WebApiCancelOrderLogic.class);
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
// 2023-renew No68 to here
