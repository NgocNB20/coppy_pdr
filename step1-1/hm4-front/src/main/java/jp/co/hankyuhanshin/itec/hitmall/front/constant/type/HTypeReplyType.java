/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * アンケート回答形式種別：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeReplyType implements EnumType {

    /**
     * テキストボックス
     */
    TEXTBOX("テキストボックス", "0"),

    /**
     * テキストエリア
     */
    TEXTAREA("テキストエリア", "1"),

    /**
     * ラジオボタン
     */
    RADIOBUTTON("ラジオボタン", "2"),

    /**
     * プルダウン
     */
    PULLDOWN("プルダウン", "3"),

    /**
     * チェックボックス
     */
    CHECKBOX("チェックボックス", "4");

    /**
     * ラベル<br/>
     */
    private String label;
    /**
     * 区分値<br/>
     */
    private String value;

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeReplyType of(String value) {

        HTypeReplyType hType = EnumTypeUtil.getEnumFromValue(HTypeReplyType.class, value);
        return hType;
    }

    /**
     * 形式（replyType）が「テキストボックス/テキストエリア」であるかチェック<br />
     *
     * @return 「テキストボックス/テキストエリア」の場合にtrueを返す
     */
    public boolean isTextType() {
        return HTypeReplyType.TEXTBOX.equals(this) || HTypeReplyType.TEXTAREA.equals(this);
    }

    /**
     * 形式（replyType）が「ラジオボタン/プルダウン/チェックボックス」であるかチェック<br />
     *
     * @return 「ラジオボタン/プルダウン/チェックボックス」の場合にtrueを返す
     */
    public boolean isSelectType() {
        return HTypeReplyType.RADIOBUTTON.equals(this) || HTypeReplyType.PULLDOWN.equals(this)
               || HTypeReplyType.CHECKBOX.equals(this);
    }
}
