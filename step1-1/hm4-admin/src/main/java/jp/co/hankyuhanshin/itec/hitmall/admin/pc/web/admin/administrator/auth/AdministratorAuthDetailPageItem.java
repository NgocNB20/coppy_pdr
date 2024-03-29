/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.auth;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 管理者権限詳細ページアイテム クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
public class AdministratorAuthDetailPageItem implements Serializable {

    /**
     * シリアル
     */
    private static final long serialVersionUID = 1L;

    /**
     * 権限種別表示名
     */
    private String authTypeName;

    /**
     * 権限レベル名
     */
    private String authLevelName;

}
