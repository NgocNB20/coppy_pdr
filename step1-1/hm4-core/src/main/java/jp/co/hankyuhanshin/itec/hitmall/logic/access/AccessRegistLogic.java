/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.access;

import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessInfoEntity;

/**
 * アクセス情報登録ロジック<br/>
 * <p>
 * 以下の情報を記録する。<br/>
 * ・商品アクセス数記録機能<br/>
 * ・商品カート投入回数記録機能<br/>
 * ・受注回数記録機能<br/>
 * ・受注個数記録機能<br/>
 * ・受注金額記録機能<br/>
 * ・キャンペーンアクセス数記録機能<br/>
 * ・会員入会数<br/>
 * ・会員退会数<br/>
 * ・メルマガ入会数<br/>
 * ・メルマガ退会数<br/>
 * ・セッション<br/>
 * ・トップPV<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface AccessRegistLogic {

    /** 区切り文字 */
    public static final String SEPARATOR = ",";

    /**
     * アクセス情報登録<br/>
     * 以下の情報を記録する。<br/>
     * ・商品アクセス数記録機能<br/>
     * ・商品カート投入回数記録機能<br/>
     * ・受注回数記録機能<br/>
     * ・受注個数記録機能<br/>
     * ・受注金額記録機能<br/>
     * ・キャンペーンアクセス数記録機能<br/>
     * ・会員入会数<br/>
     * ・会員退会数<br/>
     * ・メルマガ入会数<br/>
     * ・メルマガ退会数<br/>
     * ・セッション<br/>
     * ・トップPV<br/>
     *
     * @param accessInfoEntity アクセス情報エンティティ
     * @return 登録件数
     */
    int execute(AccessInfoEntity accessInfoEntity);

    /**
     * アクセス情報登録<br/>
     * 以下の情報を記録する。<br/>
     * ・商品アクセス数記録機能<br/>
     * ・商品カート投入回数記録機能<br/>
     * ・受注回数記録機能<br/>
     * ・受注個数記録機能<br/>
     * ・受注金額記録機能<br/>
     * ・キャンペーンアクセス数記録機能<br/>
     * ・会員入会数<br/>
     * ・会員退会数<br/>
     * ・メルマガ入会数<br/>
     * ・メルマガ退会数<br/>
     * ・セッション<br/>
     * ・トップPV<br/>
     *
     * @param accessInfoEntity アクセス情報エンティティ
     * @return 登録件数
     */
    int regist(AccessInfoEntity accessInfoEntity);
}
