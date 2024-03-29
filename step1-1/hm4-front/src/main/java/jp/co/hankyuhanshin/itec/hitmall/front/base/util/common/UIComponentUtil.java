/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.base.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UIComponent関連のユーティリティクラス
 *
 * @author ueshima
 */
public final class UIComponentUtil {

    /**
     * コンストラクタ<br />
     */
    private UIComponentUtil() {
    }

    /**
     * アイテムリストの取得<br />
     *
     * @param optionMap オプションMap
     * @return アイテムリスト
     */
    public static List<Map<String, String>> getItemList(Map<String, String> optionMap) {

        List<Map<String, String>> resultList = new ArrayList<>();
        if (optionMap == null) {
            return resultList;
        }

        for (Map.Entry<String, String> entry : optionMap.entrySet()) {
            Map<String, String> selectMap = new HashMap<>();
            selectMap.put("value", entry.getKey());
            selectMap.put("label", entry.getValue());
            resultList.add(selectMap);
        }
        return resultList;
    }

    /**
     * items に存在するvalue かを検証
     *
     * @param items アイテムリスト
     * @param value 値
     * @return true..items に存在するvalueだった
     */
    public static boolean exists(List<Map<String, String>> items, String value) {
        if (getSelectLabel(items, value) == null) {
            // Itemsに存在しないvalue
            return false;
        }

        return true;
    }

    /**
     * Itemsのvalueからlabelを取得する
     *
     * @param items アイテムリスト
     * @param value 値
     * @return valueにマッチするlabel
     */
    public static String getSelectLabel(List<Map<String, String>> items, String value) {
        for (Map<String, String> map : items) {
            if (map.get("value").equals(value)) {
                return map.get("label");
            }
        }

        // Itemsに存在しないvalueを渡されたので、nullを返却
        return null;
    }

    /**
     * Itemsのvalueからlabelを取得する
     *
     * @param items アイテムリスト
     * @param value 値
     * @return valueにマッチするlabel
     */
    public static String getSelectLabel(Map<String, String> items, String value) {
        for (Map.Entry<String, String> entry : items.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }

        // Itemsに存在しないvalueを渡されたので、nullを返却
        return null;
    }

    /**
     * Itemsのlabelからvalueを取得する
     *
     * @param items 処理対象のアイテム
     * @param label 取得するlabel値
     * @return labelにマッチするvalue
     */
    public static String getSelectValue(Map<String, String> items, String label) {
        for (Map.Entry<String, String> entry : items.entrySet()) {
            if (entry.getKey().equals(label)) {
                return entry.getValue();
            }
        }

        // Itemsに存在しないkeyを渡されたので、nullを返却
        return null;
    }

}
