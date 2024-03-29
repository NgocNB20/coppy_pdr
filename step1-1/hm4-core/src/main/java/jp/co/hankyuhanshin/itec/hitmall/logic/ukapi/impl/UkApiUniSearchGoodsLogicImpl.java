/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.ukapi.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.AbstractUkApiResponseHeaderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchGoodsRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchGoodsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.ukapi.AbstractUkApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.ukapi.UkApiUniSearchGoodsLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.JsonUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * UK-API連携 ユニサーチ（商品）Logic実装クラス
 * @author tk32120
 */
@Component
public class UkApiUniSearchGoodsLogicImpl extends AbstractUkApiLogic implements UkApiUniSearchGoodsLogic {
    /**
     * コンストラクタ
     * @param jsonUtility JSONUtility
     */
    @Autowired
    public UkApiUniSearchGoodsLogicImpl(JsonUtility jsonUtility) {
        super(jsonUtility);
    }

    /**
     * UK-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    @Override
    public UkApiUniSearchGoodsResponseDto execute(UkApiUniSearchGoodsRequestDto requestDto) {
        // リクエストデータの作成
        Map<String, Object> valueMap = new HashMap<String, Object>();
        addParam("fq.category", requestDto.getCategory(), valueMap);
        addParam("kw", requestDto.getKw(), valueMap);
        addParam("page", String.valueOf(requestDto.getPage()), valueMap);
        addParam("rows", String.valueOf(requestDto.getRows()), valueMap);
        addParam("sort", requestDto.getSort(), valueMap);
        addParam("user", requestDto.getUser(), valueMap);

        // UK-API呼出し処理
        String res = connect(valueMap);

        // 処理結果が空の場合
        if (StringUtils.isEmpty(res)) {
            log.error(createLogMessage("UK-APIの接続に失敗しました。"));
            // エラーページへ遷移
            throwMessage(MSG_ERR_CODE);
        }

        // レスポンスjsonデータを整形
        UkApiUniSearchGoodsResponseDto dto = createRes(res);
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
    protected UkApiUniSearchGoodsResponseDto createRes(String res) {
        UkApiUniSearchGoodsResponseDto responseDto =
                        ApplicationContextUtility.getBean(UkApiUniSearchGoodsResponseDto.class);
        responseDto = (UkApiUniSearchGoodsResponseDto) jsonUtility.decode(res, responseDto);

        return responseDto;
    }

    /**
     * ロガークラスを取得する
     *
     * @return ロガークラス
     */
    @Override
    protected Logger getLogger() {
        return LoggerFactory.getLogger(UkApiUniSearchGoodsLogic.class);
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
