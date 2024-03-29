/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;

import java.sql.Timestamp;

/**
 * ニュース詳細 Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
public class NewsModel extends AbstractModel {

    /**
     * ニュースSEQ
     */
    private String nseq;

    /**
     * ニュース本文
     */
    private String newsBodyPC;

    /**
     * ニュース詳細
     */
    private String newsNotePC;

    /**
     * ニュースタイトル
     */
    private String titlePC;

    /**
     * ニュース日時
     */
    private Timestamp newsTime;

    // コンディション

    /**
     * ニュース公開チェック<br/>
     *
     * @return true:公開している
     */
    public boolean isOpenNews() {
        return newsTime != null;
    }

    /**
     * @return titlePC is null?
     */
    public boolean isNullTitlePC() {
        if (titlePC == null || titlePC.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * @return titlePC is not null?
     */
    public boolean isNotNullTitlePC() {
        if (titlePC == null || titlePC.isEmpty()) {
            return false;
        }
        return true;
    }

}
