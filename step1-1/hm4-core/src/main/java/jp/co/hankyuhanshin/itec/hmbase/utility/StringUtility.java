/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hmbase.utility;

import org.springframework.stereotype.Component;

/**
 * 文字列の取扱に関するユーティリティー<br/>
 *
 * @author is40701
 */
@Component
public class StringUtility {
    /**
     * 文字列を切り詰め、省略する<br/>
     * 例)len=3の場合<br/>
     * あああああ => ああ…
     *
     * @param src 対象文字列
     * @param len 三点リーダー全角１文字を含む切り出したい文字数(全角換算)
     * @return 切り出し結果文字列
     */
    public String abbreviate(String src, int len) {
        if (null == src) {
            return null;
        }
        String result = substringByte(src, 2 * len);
        if (src.equals(result)) {
            return src;
        } else {
            // 最後の文字が半角のときは2文字削って「…」を足す
            int endIdxMinus = isHan(result.charAt(result.length() - 1)) ? 2 : 1;
            return result.substring(0, (result.length()) - endIdxMinus) + "…";
        }
    }

    /**
     * 文字数を切り出す<br/>
     *
     * @param src 対象文字数
     * @param len バイト数の長さ(全角１文字は2)
     * @return 切り出し結果文字列
     */
    public String substringByte(String src, int len) {
        int dstLen = 0;
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            boolean isHan = isHan(c);
            if (isHan) {
                dstLen += 1;
            } else {
                dstLen += 2;
            }
            if (dstLen > len) {
                return src.substring(0, i);
            }
        }
        return src;
    }

    /**
     * 引数のCharが半角文字かどうか判定する<br/>
     *
     * @param c 文字
     * @return true:半角文字 / false 全角文字
     */
    public boolean isHan(char c) {
        if ((c <= '\u007e') || // 英数字
            (c == '\u00a5') || // \記号
            (c == '\u203e') || // ~記号
            (c >= '\uff61' && c <= '\uff9f') // 半角カナ
        ) {
            return true;
        }
        return false;
    }

}
