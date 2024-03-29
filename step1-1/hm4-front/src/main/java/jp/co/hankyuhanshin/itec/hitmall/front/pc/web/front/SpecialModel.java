/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 特集ページ Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
// @GetParameters("fkey, prekey")
public class SpecialModel extends AbstractModel {

    /**
     * 特集ページキー
     */
    private String fkey;

    /**
     * 特集ページタイトル
     */
    private String freeAreaTitle;

    /**
     * 特集ページ本文（PC）
     */
    private String freeAreaBodyPc;

    // コンディション

    /**
     * @return freeAreaTitle is empty?
     */
    public boolean isEmptyFreeAreaTitle() {
        return StringUtils.isEmpty(freeAreaTitle);
    }

}
