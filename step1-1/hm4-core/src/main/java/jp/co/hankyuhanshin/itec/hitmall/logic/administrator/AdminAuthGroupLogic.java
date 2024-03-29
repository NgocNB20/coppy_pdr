/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.administrator;

import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupDetailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 権限グループ操作ロジック
 *
 * @author tomo (itec) HM3.2 管理者権限対応（サービス＆ロジック統合及び DTO 改修含む)
 */
public interface AdminAuthGroupLogic {

    /**
     * 権限グループ詳細を含む権限グループを登録する。
     *
     * @param group 権限グループ
     */
    void register(AdminAuthGroupEntity group);

    /**
     * 権限グループ詳細を含む権限グループを更新する。
     *
     * @param group 権限グループ
     */
    void update(AdminAuthGroupEntity group);

    /**
     * 権限グループを削除する。
     *
     * @param group 権限グループ
     */
    void delete(AdminAuthGroupEntity group);

    /**
     * 権限グループ詳細を含む権限グループを取得する。
     *
     * @param adminAuthGroupSeq 管理者権限グループSEQ
     * @return 権限グループ
     */
    AdminAuthGroupEntity getAdminAuthGroup(Integer adminAuthGroupSeq);

    /**
     * 権限グループ詳細を含む権限グループを取得する。
     *
     * @param shopSeq              ショップSEQ
     * @param authGroupDisplayName 権限グループ名称
     * @return 権限グループ
     */
    AdminAuthGroupEntity getAdminAuthGroup(Integer shopSeq, String authGroupDisplayName);

    /**
     * 指定されたショップの権限グループ一覧を取得する。<br />
     * 各権限グループには権限詳細が含まれる。
     *
     * @param shopSeq ショップSEQ
     * @return 権限グループ一覧
     */
    List<AdminAuthGroupEntity> getAdminAuthGroupList(Integer shopSeq);

    /**
     * 権限レベルを適切な内容に補正する。
     *
     * @param detailList        補正対象権限設定
     * @param adminAuthGroupSeq 即席で権限詳細を作成しなければならないときに使用する権限グループSEQ
     * @return 補正された権限設定
     */
    List<AdminAuthGroupDetailEntity> adjustAuthLevel(List<AdminAuthGroupDetailEntity> detailList,
                                                     Integer adminAuthGroupSeq);

    /**
     * 選択項目リストの作成に利用するデータを返却する<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 権限グループ結果を格納したMap
     */
    Map<String, String> getItemMapList(Integer shopSeq);
}
