/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import net.arnx.jsonic.JSON;
import org.springframework.stereotype.Component;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * ・JSONとオブジェクトの変換処理<br/>
 * ・エスケープ処理<br/>
 * ・null → 空文字処理<br/>
 * </pre>
 */
@Component
public class JsonUtility {

    /**
     * 引数に渡されたDTOをJSONにエンコードします<br/>
     *
     * @param args DTO
     * @return JSON文字列
     */
    public String encode(Object... args) {
        return JSON.encode(args[0]);
    }

    /**
     * JSON文字列をDTOにデコードします<br/>
     *
     * @param jsonStr JSON文字列
     * @param args    DTO
     * @return DTO
     */
    public Object decode(String jsonStr, Object... args) {
        return JSON.decode(jsonStr, args[0].getClass());
    }

    /**
     * 引数の文字列がnull なら空文字を返却する<br/>
     *
     * @param value 文字列
     * @return null:空文字 / null:そのままの文字列
     */
    public String returnBlankStr(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}
