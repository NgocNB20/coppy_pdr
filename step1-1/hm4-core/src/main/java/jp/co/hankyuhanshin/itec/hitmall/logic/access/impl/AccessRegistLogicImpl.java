/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.access.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.access.AccessInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.access.AccessRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * アクセス情報登録<br/>
 * アクセス情報エンティティをアクセス情報テーブルに登録する。<br/>
 *
 * @author kimura
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class AccessRegistLogicImpl extends AbstractShopLogic implements AccessRegistLogic {

    /** アクセス情報DAO */
    private final AccessInfoDao accessInfoDao;

    /** DateUtility */
    private final DateUtility dateUtility;

    @Autowired
    public AccessRegistLogicImpl(AccessInfoDao accessInfoDao, DateUtility dateUtility) {
        this.accessInfoDao = accessInfoDao;
        this.dateUtility = dateUtility;
    }

    /**
     * アクセス情報登録<br/>
     * アクセス情報エンティティをアクセスログに出力する。
     *
     * @param accessInfoEntity アクセス情報エンティティ
     * @return 登録・更新件数
     */
    @Override
    public int execute(AccessInfoEntity accessInfoEntity) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("accessInfoEntity", accessInfoEntity);

        // リアルタイムでDB登録
        return this.regist(accessInfoEntity);
    }

    /**
     * アクセス情報登録<br/>
     * アクセス情報エンティティをアクセス情報テーブルに登録する。
     *
     * @param accessInfoEntity アクセス情報エンティティ
     * @return 登録件数
     */
    @Override
    public int regist(AccessInfoEntity accessInfoEntity) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("accessInfoEntity", accessInfoEntity);

        Timestamp currentTime = dateUtility.getCurrentTime();

        // アクセス情報更新
        accessInfoEntity.setUpdateTime(currentTime);
        int updateResult = accessInfoDao.updateAccessCount(accessInfoEntity);
        if (updateResult > 0) {
            return updateResult;
        } else {
            // 更新対象が存在しない場合は、アクセス情報登録
            accessInfoEntity.setRegistTime(currentTime);
            accessInfoEntity.setUpdateTime(currentTime);
            return accessInfoDao.insert(accessInfoEntity);
        }
    }

    /**
     * ログ文字列作成<br/>
     *
     * @param accessInfoEntity アクセス情報エンティティ
     * @return ログ文字列
     */
    protected String makeAccessLogString(AccessInfoEntity accessInfoEntity) {
        StringBuilder sb = new StringBuilder();
        // ショップSEQ
        sb.append(accessInfoEntity.getShopSeq()).append(SEPARATOR);
        // アクセス種別
        sb.append(accessInfoEntity.getAccessType().getValue()).append(SEPARATOR);
        // アクセス日時
        sb.append(accessInfoEntity.getAccessDate()).append(SEPARATOR);
        // サイト種別
        sb.append(accessInfoEntity.getSiteType().getValue()).append(SEPARATOR);
        // デバイス種別
        sb.append(accessInfoEntity.getDeviceType().getValue()).append(SEPARATOR);
        // 商品グループSEQ
        sb.append(accessInfoEntity.getGoodsGroupSeq()).append(SEPARATOR);
        // キャンペーンコード
        sb.append(accessInfoEntity.getCampaignCode()).append(SEPARATOR);
        // 端末識別番号
        sb.append(accessInfoEntity.getAccessUid()).append(SEPARATOR);
        // アクセスカウント
        sb.append(accessInfoEntity.getAccessCount());

        return sb.toString();
    }

}
