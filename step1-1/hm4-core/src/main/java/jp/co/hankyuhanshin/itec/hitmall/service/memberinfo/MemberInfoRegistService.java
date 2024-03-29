/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

/**
 * 会員登録サービス<br/>
 *
 * @author natume
 * @version $Revision: 1.6 $
 */
public interface MemberInfoRegistService {

    /* メッセージ */

    /**
     * 会員情報登録失敗エラー<br/>
     * <code>MSGCD_MEMBERINFO_REGIST_FAIL</code>
     */
    String MSGCD_MEMBERINFO_REGIST_FAIL = "SMM000101";

    /**
     * ・会員ID重複チェック<br/>
     * ・パスワードの暗号化<br/>
     * ・会員情報の登録を行う <br/>
     * ・メルマガ購読者の登録・更新を行う<br/>
     * ・確認メールの削除を行う<br/>
     *
     * @param memberInfoEntity    会員エンティティ
     * @param confirmMailPassword 確認メールパスワード
     */
    void execute(MemberInfoEntity memberInfoEntity,
                 String confirmMailPassword,
                 Boolean isLogin,
                 String accessUid,
                 Boolean isSiteBack);

    /**
     * ・会員ID重複チェック<br/>
     * ・パスワードの暗号化<br/>
     * ・会員情報の登録を行う <br/>
     * ・メルマガ購読者の登録・更新を行う<br/>
     * ・カート商品のマージを行う（cartMergeがtrueの場合） <br/>
     *
     * @param memberInfoEntity 会員エンティティ
     * @param cartMerge        カートマージをするかどうか。true..する / false..しない
     */
    void executeHm(MemberInfoEntity memberInfoEntity,
                   boolean cartMerge,
                   Boolean isLogin,
                   String accessUid,
                   Boolean isSiteBack);

    /**
     * 会員登録を行います。<br/>
     *
     * <pre>
     * 以下の処理を行います。
     * ・会員ID重複チェック
     * ・パスワードの暗号化
     * ・会員情報の登録を行う
     * </pre>
     * @param memberInfoEntity 会員エンティティ
     * @param onlineFlg true:オンライン　false:バッチ
     */
    void execute(MemberInfoEntity memberInfoEntity, boolean onlineFlg, Boolean isSiteBack, String accessUid);
}
