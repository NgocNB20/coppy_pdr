/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.helper.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * JSON関連のHelper クラス<br/>
 *
 * @author s_tsuru
 */
@Component
public class JsonHelper {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHelper.class);

    /**
     * JSON形式の文字列にエンコードする<br/>
     * シリアライズインタフェースを実装しているオブジェクトからJSON形式の文字列を生成
     *
     * @param object シリアライズを実装しているオブジェクト
     * @return JSON形式の文字列
     */
    public String encode(Serializable object) {
        String enCodeJson = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            enCodeJson = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.warn("クラスからJSONのデコードに失敗しました。", e);
        }
        return enCodeJson;
    }

    /**
     * JSON形式の文字列からデコードする<br/>
     * JSON形式の文字列からオブジェクトに値をセットする
     *
     * @param <T>            検索条件Dto抽象クラス
     * @param jsonStr        JSON形式の文字列
     * @param conditionClass 実装クラス
     * @return JSON形式の文字列
     */
    public <T> T decode(Class<T> conditionClass, String jsonStr) {
        T deCodeClass = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            deCodeClass = objectMapper.readValue(jsonStr, conditionClass);
        } catch (JsonProcessingException je) {
            LOGGER.warn("JSONからクラスのデコードに失敗しました。", je);
        }

        return deCodeClass;
    }
}
