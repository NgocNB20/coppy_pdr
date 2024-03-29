/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.utility;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * WEB-APIのUtilityクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class WebApiUtility {

    /**
     * List<String>からパイプ区切りの文字列を作成します。
     *
     * @param list リスト
     * @return パイプ区切りの文字列
     */
    public static String createStrPipeByList(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (String str : list) {
            if (result.length() > 0) {
                result.append("|");
            }
            result.append(str);
        }

        return result.toString();
    }

    /**
     * パイプ区切りの文字列からList<String>を作成します。
     *
     * @param strPipe パイプ区切りの文字列
     * @return リスト
     */
    public static List<String> creatListByStrPipe(String strPipe) {

        if (StringUtils.isEmpty(strPipe)) {
            return null;
        }

        String[] array = strPipe.split("\\|");

        if (array == null || array.length == 0) {
            return null;
        }

        return Arrays.asList(array);
    }
}
