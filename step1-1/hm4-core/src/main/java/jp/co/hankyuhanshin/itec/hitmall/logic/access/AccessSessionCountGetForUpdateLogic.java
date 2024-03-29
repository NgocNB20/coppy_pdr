/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.access;

import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessInfoEntity;

/**
 * AccessInfo のエンティティを更新用に取得する
 *
 * @author tm27400
 * @version $Revision: 1.2 $
 *
 */
public interface AccessSessionCountGetForUpdateLogic {

    /**
     * 更新用にエンティティを取得する
     *
     * @param condition アクセス情報エンティティ
     * @return condition アクセス情報エンティティ
     */
    AccessInfoEntity getEntity(AccessInfoEntity condition);
}
