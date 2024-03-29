/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.authupdate;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 権限グループ登録画面/登録確認画面 PageItem クラス
 *
 * @author tomo (itec) HM3.2 管理者権限対応（サービス＆ロジック統合及び DTO 改修含む)
 */
@Data
@Component
@Scope("prototype")
public class AuthUpdatePageItem implements Serializable {

    /**
     * シリアル
     */
    private static final long serialVersionUID = 1L;

    /**
     * 権限種別表示名称
     */
    private String typeDisplayName;

    /**
     * 権限種別コード
     */
    private String authTypeCode;

    /**
     * 権限レベル値
     */
    private String level;

    /**
     * 権限レベル名称
     */
    private String levelName;

    /**
     * 権限種別の権限レベル一覧
     */
    private List<Map<String, ?>> levelItems;

    //    /**
    //     * @return the typeDisplayName
    //     */
    //    public String getTypeDisplayName() {
    //        return typeDisplayName;
    //    }
    //
    //    /**
    //     * @param typeDisplayName the typeDisplayName to set
    //     */
    //    public void setTypeDisplayName(String typeDisplayName) {
    //        this.typeDisplayName = typeDisplayName;
    //    }
    //
    //    /**
    //     * @return the authTypeCode
    //     */
    //    public String getAuthTypeCode() {
    //        return authTypeCode;
    //    }
    //
    //    /**
    //     * @param authTypeCode the authTypeCode to set
    //     */
    //    public void setAuthTypeCode(String authTypeCode) {
    //        this.authTypeCode = authTypeCode;
    //    }
    //
    //    /**
    //     * @return the level
    //     */
    //    public String getLevel() {
    //        return level;
    //    }
    //
    //    /**
    //     * @param level the level to set
    //     */
    //    public void setLevel(String level) {
    //        this.level = level;
    //    }
    //
    //    /**
    //     * @return the levelName
    //     */
    //    public String getLevelName() {
    //        return levelName;
    //    }
    //
    //    /**
    //     * @param levelName the levelName to set
    //     */
    //    public void setLevelName(String levelName) {
    //        this.levelName = levelName;
    //    }
    //
    //    /**
    //     * @return the levelItems
    //     */
    //    public List<Map<String, ?>> getLevelItems() {
    //        return levelItems;
    //    }
    //
    //    /**
    //     * @param levelItems the levelItems to set
    //     */
    //    public void setLevelItems(List<Map<String, ?>> levelItems) {
    //        this.levelItems = levelItems;
    //    }
}
