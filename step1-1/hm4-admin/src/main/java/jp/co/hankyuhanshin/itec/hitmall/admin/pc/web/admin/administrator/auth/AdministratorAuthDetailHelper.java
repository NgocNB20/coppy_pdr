/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.auth;

import jp.co.hankyuhanshin.itec.hitmall.dto.administrator.MetaAuthLevel;
import jp.co.hankyuhanshin.itec.hitmall.dto.administrator.MetaAuthType;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupDetailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 権限グループ詳細画面 Helper クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class AdministratorAuthDetailHelper {

    /**
     * 初期表示用変換
     *
     * @param entity                       権限グループ情報
     * @param metaList                     メタ権限データ
     * @param administratorAuthDetailModel 変換後データ格納先ページ
     */
    public void toPageForLoad(AdminAuthGroupEntity entity,
                              List<MetaAuthType> metaList,
                              AdministratorAuthDetailModel administratorAuthDetailModel) {

        administratorAuthDetailModel.setAdminAuthGroupSeq(entity.getAdminAuthGroupSeq().toString());
        administratorAuthDetailModel.setAuthGroupDisplayName(entity.getAuthGroupDisplayName());
        administratorAuthDetailModel.setUnmodifiableGroup(entity.getUnmodifiableGroup());

        //
        // 権限グループに設定されている権限種別毎の表示情報を作成する
        //

        List<AdministratorAuthDetailPageItem> items = new ArrayList<>();

        for (AdminAuthGroupDetailEntity detail : entity.getAdminAuthGroupDetailList()) {

            String authTypeCode = detail.getAuthTypeCode();
            Integer authLevel = detail.getAuthLevel();

            //
            // authTypeCode に該当するメタ権限情報を探す
            //

            for (MetaAuthType meta : metaList) {

                if (!meta.getAuthTypeCode().equals(authTypeCode)) {
                    continue;
                }

                //
                // メタ権限情報を見つけた場合
                //

                AdministratorAuthDetailPageItem item = new AdministratorAuthDetailPageItem();

                // 権限種別名称を取得
                item.setAuthTypeName(meta.getTypeDisplayName());

                // 権限レベル名称を取得
                item.setAuthLevelName(getAuthLevelName(authLevel, meta));

                items.add(item);

                break;
            }

        }

        administratorAuthDetailModel.setDetailPageItems(items);

        // 不正操作対策の情報をセットする
        administratorAuthDetailModel.setScSeq(entity.getAdminAuthGroupSeq());
        administratorAuthDetailModel.setDbSeq(entity.getAdminAuthGroupSeq());
    }

    /**
     * 権限レベルの名称を取得する
     *
     * @param authLevel 権限レベル
     * @param meta      メタ権限設定
     * @return 権限レベル名称
     */
    protected String getAuthLevelName(Integer authLevel, MetaAuthType meta) {

        //
        // 対応する権限レベル設定名称を取得する
        //

        for (MetaAuthLevel dto : meta.getMetaAuthLevelList()) {

            if (!authLevel.equals(dto.getMetaLevel())) {
                continue;
            }

            return dto.getLevelDisplayName();
        }

        // AdminAuthGroupLogic#adjustAuthLevel() で不正な設定が入り込まないように制御されているため、
        // 通常ではここに来ることはない
        return "Level" + authLevel;
    }
}
