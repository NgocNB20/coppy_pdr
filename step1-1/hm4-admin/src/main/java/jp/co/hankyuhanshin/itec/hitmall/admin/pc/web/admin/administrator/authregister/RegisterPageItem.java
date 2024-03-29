/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.authregister;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 権限グループ登録画面 PageItem クラス
 *
 * @author tomo (itec) HM3.2 管理者権限対応（サービス＆ロジック統合及び DTO 改修含む)
 */
@Data
@Component
@Scope("prototype")
public class RegisterPageItem implements Serializable {

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

}
