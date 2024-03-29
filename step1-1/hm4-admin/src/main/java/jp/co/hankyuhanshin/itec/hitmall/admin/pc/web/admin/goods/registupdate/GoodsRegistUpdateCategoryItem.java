/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 商品管理：商品登録更新（カテゴリ設定）ページ情報<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class GoodsRegistUpdateCategoryItem implements Serializable {

    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /************************************
     ** カテゴリ項目
     ************************************/
    /**
     * カテゴリ登録チェック<br/>
     */
    private boolean registCategoryCheck;

    /**
     * カテゴリSEQ<br/>
     */
    private Integer categorySeq;

    /**
     * カテゴリID<br/>
     */
    private String categoryId;

    /**
     * カテゴリ名<br/>
     */
    private String categoryName;

    /**
     * カテゴリ階層<br/>
     */
    private Integer categoryLevel;

    /**
     * カテゴリSEQパス<br/>
     */
    private String categorySeqPath;

    /************************************
     **  カテゴリ字下げ用
     ************************************/
    /**
     * カテゴリ字下げ文字列取得
     *
     * @return カテゴリ字下げ文字列
     */
    public String getCategoryIndent() {
        String ret = "";
        for (int i = 1; categoryLevel != null && i < categoryLevel; i++) {
            ret += "　　";
        }
        return ret;
    }

    /************************************
     **  判定
     ************************************/
    /**
     * TOPカテゴリ判定<br/>
     *
     * @return true=TOPカテゴリ
     */
    public boolean isTopCategory() {
        return (categoryLevel == 0);
    }
}
