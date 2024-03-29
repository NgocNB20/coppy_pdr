/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.authdelete;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 権限グループ削除確認画面 PageItem クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
public class AdministratorAuthDeletePageItem implements Serializable {

    /**
     * シリアル
     */
    private static final long serialVersionUID = 1L;

    /**
     * 権限種別名
     */
    private String authTypeName;

    /**
     * 権限レベル名
     */
    private String authLevelName;
}
