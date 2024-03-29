/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.thymeleaf;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 差分スタイルクラス用ユーティリティ
 *
 * @author kimura
 */
public class DiffStyleClassViewUtil {

    /**
     * 　区切り文字
     */
    private static final String SEPARATOR = ".";

    /**
     * 差分スタイルクラス
     */
    private String DIFF_STYLE_CLASS = "diff";

    /**
     * 差分スタイルクラス上書用setter<br/>
     *
     * @param style
     */
    public void setDiffStyleClass(String style) {
        if (!StringUtils.isEmpty(style)) {
            DIFF_STYLE_CLASS = style;
        }
    }

    // 2023-renew AddNo2 from here

    /**
     * 差分リストに指定フィールドが含まれる場合スタイルを変更<br/>
     *
     * @param diffList 差分リスト
     * @param names    フィールド名（複数指定可）
     * @return style
     */
    public String diff(final List<String> diffList, final String[] names) {
        return this.diff(diffList, names, null);
    }

    /**
     * 差分リストに指定フィールドが含まれる場合スタイルを変更<br/>
     *
     * @param diffList 差分リスト
     * @param names    フィールド名（複数指定可）
     * @return style
     */
    public String diff(final List<String> diffList, final String[] names, final String style) {
        String defaultStyle = DIFF_STYLE_CLASS;
        if (StringUtil.isNotBlank(style))
            defaultStyle = style;

        if (!CollectionUtils.isEmpty(diffList)) {

            // 差分リストから 「オブジェクト名.フィールド名」を取得
            String objectName = diffList.get(0);

            // 「.」が含まれているかチェック ※含まれていない場合、差分リストではないため、デフォルトスタイルを返す
            if (objectName.contains(SEPARATOR)) {
                // 「.」より前を切り出す
                objectName = objectName.substring(0, objectName.indexOf(SEPARATOR));

                for (String name : names) {

                    // フィールド名にオブジェクト名を結合
                    StringBuilder target = new StringBuilder();
                    target.append(objectName);
                    target.append(SEPARATOR);
                    target.append(name);

                    if (diffList.contains(target.toString())) {
                        return defaultStyle;
                    }
                }
            }
        }
        return "";
    }
    // 2023-renew AddNo2 to here
}
