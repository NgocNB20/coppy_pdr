/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.include;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 共通サイドメニューAjax Modelアイテム
 *
 * @author kaneda
 */
@Data
@Component
@Scope("prototype")
public class SidemenuModelItem implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // カテゴリ
    /**
     * カテゴリID
     */
    private String cid;
    /**
     * カテゴリ表示名PC
     */
    private String categoryName;
    /**
     * カテゴリSEQパス
     */
    private String categorySeqPath;
    /**
     * カテゴリレベル
     */
    private Integer categoryLevel;
    /**
     * カテゴリファイル名
     */
    private String categoryFileName;
    /**
     * 選択ルートカテゴリ
     */
    private boolean selectedRootCategory;
}
