/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.authregister;

import jp.co.hankyuhanshin.itec.hitmall.dto.administrator.MetaAuthLevel;
import jp.co.hankyuhanshin.itec.hitmall.dto.administrator.MetaAuthType;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupDetailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 権限グループ登録画面用 Dxo クラス
 *
 * @author tomo (itec) HM3.2 管理者権限対応（サービス＆ロジック統合及び DTO 改修含む)
 */
@Component
public class AuthRegisterHelper {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRegisterHelper.class);

    /**
     * doLoad 用ページ作成処理
     *
     * @param metaList メタ権限情報
     * @param model    ページ
     */
    public void toPageForLoad(List<MetaAuthType> metaList, AuthRegisterModel model) {

        model.setAuthGroupDisplayName("");
        model.setAuthItems(new ArrayList<>());

        // 権限種別毎のアイテムを作成する
        for (MetaAuthType dto : metaList) {

            // 権限種別アイテム
            RegisterPageItem authItem = ApplicationContextUtility.getBean(RegisterPageItem.class);

            // 権限種別名称
            authItem.setTypeDisplayName(dto.getTypeDisplayName());
            authItem.setAuthTypeCode(dto.getAuthTypeCode());
            authItem.setLevel("0");

            // 選択可能な権限レベル一覧を設定する
            authItem.setLevelItems(new ArrayList<Map<String, ?>>());

            for (MetaAuthLevel level : dto.getMetaAuthLevelList()) {
                Map<String, ? super Object> map = new HashMap<>();
                map.put("label", level.getLevelDisplayName() + " ");
                map.put("value", level.getMetaLevel());
                authItem.getLevelItems().add(map);
            }

            // 権限レベル強度順に権限レベルをソートする
            // ※DICON に設定されてる選択可能権限レベル一覧はレベル順にソートされてない可能性がある
            authItem.getLevelItems().sort(new Comparator<Map<String, ?>>() {

                /**
                 * オブジェクトを比較する
                 * @param o1 比較オブジェクト1
                 * @param o2 比較オブジェクト2
                 * @return 比較結果
                 */
                @Override
                public int compare(Map<String, ?> o1, Map<String, ?> o2) {
                    Integer value1 = (Integer) o1.get("value");
                    Integer value2 = (Integer) o2.get("value");
                    return value1.compareTo(value2);
                }
            });

            //            sortByValue(authItem.getLevelItems());

            model.getAuthItems().add(authItem);
        }

    }

    /**
     * 登録に使用する AdminAuthGroupEntity を作成する
     *
     * @param authRegisterModel AuthRegisterModel
     * @return 登録用 AdminAuthGroupEntity
     */
    public AdminAuthGroupEntity toAdminAuthGroupEntityForRegister(AuthRegisterModel authRegisterModel) {

        AdminAuthGroupEntity group = ApplicationContextUtility.getBean(AdminAuthGroupEntity.class);

        group.setAuthGroupDisplayName(authRegisterModel.getAuthGroupDisplayName());
        group.setAdminAuthGroupDetailList(new ArrayList<AdminAuthGroupDetailEntity>());

        for (RegisterPageItem item : authRegisterModel.getAuthItems()) {

            AdminAuthGroupDetailEntity detail = ApplicationContextUtility.getBean(AdminAuthGroupDetailEntity.class);

            try {
                detail.setAuthTypeCode(item.getAuthTypeCode());
                detail.setAuthLevel(Integer.parseInt(item.getLevel()));
            } catch (NumberFormatException nfe) {
                LOGGER.error("例外処理が発生しました", nfe);
                // 数値に変換出来ない場合は権限レベル 0 が適用される
                detail.setAuthLevel(0);
                detail.setAuthTypeCode((String) (item.getLevelItems().get(0).get("label")));
            }

            group.getAdminAuthGroupDetailList().add(detail);

        }

        return group;
    }

    public void setLevelName(AuthRegisterModel authRegisterModel) {
        for (int i = 0; i < authRegisterModel.getAuthItems().size(); i++) {
            for (Map<String, ?> levelItem : authRegisterModel.getAuthItems().get(i).getLevelItems()) {
                String level = authRegisterModel.getAuthItems().get(i).getLevel();
                String itemLevel = levelItem.get("value").toString();
                String label = levelItem.get("label").toString();

                if (level.equals(itemLevel)) {
                    authRegisterModel.getAuthItems().get(i).setLevelName(label);
                }
            }
        }
    }
}
