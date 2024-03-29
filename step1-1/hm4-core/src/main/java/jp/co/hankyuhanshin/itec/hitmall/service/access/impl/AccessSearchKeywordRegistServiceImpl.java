/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.access.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessSearchKeywordEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.access.AccessSearchKeywordRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.access.AccessSearchKeywordRegistService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * アクセス検索キーワード情報登録<br/>
 * 検索キーワード情報を登録する<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
@Service
public class AccessSearchKeywordRegistServiceImpl extends AbstractShopService
                implements AccessSearchKeywordRegistService {

    /**
     * アクセス検索キーワードロジッククラス
     */
    private final AccessSearchKeywordRegistLogic accessSearchKeywordRegistLogic;

    @Autowired
    public AccessSearchKeywordRegistServiceImpl(AccessSearchKeywordRegistLogic accessSearchKeywordRegistLogic) {
        this.accessSearchKeywordRegistLogic = accessSearchKeywordRegistLogic;
    }

    /**
     * アクセス検索キーワード情報登録<br/>
     * アクセス検索キーワード情報を登録する<br/>
     *
     * @param accessSearchKeywordEntity アクセス検索キーワード情報エンティティ
     * @return 更新・登録件数
     */
    @Override
    public int execute(AccessSearchKeywordEntity accessSearchKeywordEntity, String accessUid) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("accessSearchKeywordEntity", accessSearchKeywordEntity);

        // 共通情報チェック
        Integer shopSeq = 1001;
        HTypeSiteType siteType = HTypeSiteType.FRONT_PC;

        ArgumentCheckUtil.assertNotNull("accessUid", accessUid);

        // アクセス情報登録処理実行
        accessSearchKeywordEntity.setAccessUid(accessUid);
        accessSearchKeywordEntity.setShopSeq(shopSeq);
        accessSearchKeywordEntity.setSiteType(siteType);
        return accessSearchKeywordRegistLogic.execute(accessSearchKeywordEntity);
    }

}
