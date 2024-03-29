/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.auth;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import lombok.Data;

import java.util.List;

/**
 * 権限グループ詳細画面 Page クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
public class AdministratorAuthDetailModel extends AbstractModel {
    /**
     * 権限グループSEQ (修正or削除画面から戻ってきて F5 を押しても大丈夫にするためサブアプリスコープ。PageScope だと doLoad 時に削除されるため。)
     */
    private String adminAuthGroupSeq;

    /**
     * 権限グループ名称
     */
    private String authGroupDisplayName;

    /**
     * 権限内容繰り返しアイテム
     */
    private List<AdministratorAuthDetailPageItem> detailPageItems;

    /**
     * 権限種別表示名
     */
    private String authTypeName;

    /**
     * 権限レベル名
     */
    private String authLevelName;

    /**
     * 変更不可権限グループかどうか
     */
    private boolean unmodifiableGroup;

    /**
     * 画面描画時にパラメータで渡されたSeq<br />
     * 　画面描画時の画面情報と登録情報に差異がないかをチェックするのに利用する
     */
    private Integer scSeq;

    /**
     * 画面描画時にDBから取得したSeq<br />
     * 画面描画時の画面情報と登録情報に差異がないかをチェックするのに利用する
     */
    private Integer dbSeq;
}
