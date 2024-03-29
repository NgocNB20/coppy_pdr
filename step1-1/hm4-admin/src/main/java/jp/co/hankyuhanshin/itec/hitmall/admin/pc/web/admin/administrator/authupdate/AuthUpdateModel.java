/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.authupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 権限グループ登録画面用 Page クラス
 *
 * @author tomo (itec) HM3.2 管理者権限対応（サービス＆ロジック統合及び DTO 改修含む)
 */
@Data
public class AuthUpdateModel extends AbstractModel {

    /**
     * 変種画面を開いた際に取得したエンティティ
     */
    private AdminAuthGroupEntity originalEntity;

    /**
     * 修正後差分エンティティ
     */
    private AdminAuthGroupEntity modifiedEntity;

    /**
     * 差分リスト
     */
    private List<String> modifiedList;

    /**
     * 入力項目：権限グループ名称
     */
    @NotEmpty
    @Length(min = 1, max = 40)
    private String authGroupDisplayName;

    /**
     * 権限種別アイテム
     */

    private List<AuthUpdatePageItem> authItems;
    /**
     * 変更前権限種別アイテム
     */
    private List<AuthUpdatePageItem> originalAuthItems;

    /**
     * 表示情報：権限種別表示名称(メタデータ)
     */
    private String typeDisplayName;

    /**
     * 表示情報：権限レベル表示名称(メタデータ)
     */
    private String level;

    /**
     * 権限レベル情報
     */
    private Map<String, String> levelItems;

    /**
     * 表示情報
     */
    private AuthUpdatePageItem auth;

    private Integer seq;

    private Integer scSeq;

    private Integer dbSeq;

    // /**
    // * 選択されている権限レベルに対応する名称を取得する
    // * @return 権限レベル名称
    // */
    // public String getLevelName() {
    //
    // for (Map<String, ?> items : auth.getLevelItems()) {
    //
    // if (level.equals(items.get("value").toString())) {
    // return (String) items.get("label");
    // }
    //
    // }
    //
    // // 通常ではここに来ることはない
    // return "";
    // }

    /**
     * 処理中の権限種別配列番号
     */
    private int authIndex;
}
