/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.authregister;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 権限グループ登録画面用 Page クラス
 *
 * @author tomo (itec) HM3.2 管理者権限対応（サービス＆ロジック統合及び DTO 改修含む)
 */
@Data
public class AuthRegisterModel extends AbstractModel {

    /**
     * 入力項目：権限グループ名称
     */
    @NotEmpty
    @Length(min = 1, max = 40)
    private String authGroupDisplayName;

    /**
     * 権限種別アイテム(確認画面でも使い回しするためサブアプリスコープ)
     */
    private List<RegisterPageItem> authItems;

    //    /** 表示情報：権限種別表示名称(メタデータ) */
    //    private String typeDisplayName;
    //
    //    /** 表示情報：権限レベル表示名称(メタデータ) */
    //    private String level;
    //
    //    /** 権限レベル情報 */
    //    private List<Map<String, String>> levelItems;
    //
    //    /** 表示情報 */
    //    private RegisterPageItem auth;
    //
    //    /**
    //     * 選択されている権限レベルに対応する名称を取得する
    //     * @return 権限レベル名称
    //     */
    //    public String getLevelName() {
    //        for (Map<String, ?> items : auth.getLevelItems()) {
    //            if (level.equals(items.get("value").toString())) {
    //                return (String) items.get("label");
    //            }
    //
    //        }
    //
    //        // AdminAuthGroupLogic#adjustAuthLevel() で不正な設定が入り込まないように制御されているため、
    //        // 通常ではここに来ることはない
    //        return "";
    //    }
}
