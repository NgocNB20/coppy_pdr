/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.service;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import org.springframework.beans.factory.support.ScopeNotActiveException;
import org.springframework.stereotype.Service;

/**
 * ショップ用サービス基底クラス
 *
 * @author ueshima
 * @version $Revision: 1.6 $
 */
@Service
public abstract class AbstractShopService {

    /**
     * 共通情報
     */
    private CommonInfo commonInfo;

    /**
     * 共通情報の取得
     *
     * @return 共通情報
     */
    public CommonInfo getCommonInfo() {
        if (commonInfo == null) {
            try {
                commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);
            } catch (ScopeNotActiveException e) {
                // バッチから呼び出された際に管理画面のCommonInfoが参照不可のため、バッチ用のCommonInfoを取得する
                commonInfo = ApplicationContextUtility.getBeanByName("commonInfoBatch", CommonInfo.class);
            }
        }
        return commonInfo;
    }

    /**
     * 共通情報のセット
     * 画面非同期・Filterからの呼び出しの場合は、このメソッドを利用する
     * 画面非同期の場合は、共通情報のコピーをセットする
     *
     * @param commonInfo 共通情報
     */
    public void setCommonInfo(CommonInfo commonInfo) {
        this.commonInfo = commonInfo;
    }
}
