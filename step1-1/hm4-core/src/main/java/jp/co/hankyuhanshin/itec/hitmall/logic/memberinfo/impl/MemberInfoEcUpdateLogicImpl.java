// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.crypto.CryptoHelper;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoEcUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoDataCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * PDR#011 08_データ連携（顧客情報）<br/>
 *
 * <pre>
 * EC会員情報更新ロジック
 * </pre>
 *
 * @author satoh
 * @version $Revision:$
 */
@Component
public class MemberInfoEcUpdateLogicImpl extends AbstractShopLogic implements MemberInfoEcUpdateLogic {

    /**
     * 日付関連Utilityクラス
     */
    public final DateUtility dateUtility;

    /**
     * 会員情報データチェックサービス
     */
    public final MemberInfoDataCheckService memberInfoDataCheckService;

    /**
     * 会員情報更新サービス
     */
    public final MemberInfoUpdateService memberInfoUpdateService;

    @Autowired
    public MemberInfoEcUpdateLogicImpl(DateUtility dateUtility,
                                       MemberInfoDataCheckService memberInfoDataCheckService,
                                       MemberInfoUpdateService memberInfoUpdateService) {
        this.dateUtility = dateUtility;
        this.memberInfoDataCheckService = memberInfoDataCheckService;
        this.memberInfoUpdateService = memberInfoUpdateService;
    }

    /**
     * EC会員情報の更新を行います。
     *
     * <pre>
     * 以下の処理を行う。
     * ・メールアドレス重複チェック
     * ・会員情報更新に必要な情報をEntityに設定
     * ・メールアドレス行進用WEB-APIを実行
     * </pre>
     *
     * @param memberInfoEntity 会員情報Entity
     */
    @Override
    public void execute(MemberInfoEntity memberInfoEntity) {

        // 会員情報更新に必要な情報をEntityに設定
        createEntity(memberInfoEntity);

        // 会員情報データチェックサービス実行
        // メールアドレス重複チェック
        memberInfoDataCheckService.execute(memberInfoEntity);

        // 会員情報更新処理
        memberInfoUpdateService.execute(memberInfoEntity);

    }

    /**
     * 会員情報更新に必要な情報をEntityに設定
     *
     * <pre>
     * 以下情報を設定
     * ・承認状態:1(承認)
     * ・会員状態:0(入会)
     * ・オンライン登録フラグ:1(EC会員)
     * ・パスワード変更要求フラグ:1(要求する)
     * ・仮パスワードを設定
     * </pre>
     *
     * @param memberInfoEntity 会員情報Entity
     */
    public void createEntity(MemberInfoEntity memberInfoEntity) {

        // 承認状態:1(承認)
        memberInfoEntity.setApproveStatus(HTypeApproveStatus.ON);

        // 会員状態:0(入会)
        memberInfoEntity.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);

        // オンライン登録フラグ:1(EC会員)
        memberInfoEntity.setOnlineRegistFlag(HTypeOnlineRegistFlag.ON);

        // パスワード変更要求フラグ:1(要求する)
        memberInfoEntity.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.ON);

        // 暗号化/複合関連Helper取得
        CryptoHelper cryptoHelper = ApplicationContextUtility.getBean(CryptoHelper.class);
        // パスワード暗号化
        // 仮パスワードを設定
        memberInfoEntity.setMemberInfoPassword(cryptoHelper.encryptMemberPassword(createPassword()));

        /*---------- #216 start----------*/
        // 入会日Ymd　新規お届け先会員・TEL/FAX会員の場合、入会日を設定する
        memberInfoEntity.setAdmissionYmd(dateUtility.getCurrentYmd());

        // 退会日Ymd　新規お届け先会員・TEL/FAX会員の場合、退会日をクリアする
        memberInfoEntity.setSecessionYmd(null);
        /*---------- #216 end----------*/

    }

    /**
     * 仮パスワードを発行します。<br/>
     *
     * <pre>
     * 以下の処理を行い仮パスワードを生成
     * ①現在時刻でジェネレータ生成
     * ②0～25の乱数に97(aのchar)を足して、a～zを取得
     * ③0～25の乱数に65(Aのchar)を足して、A～Zを取得
     * ④0～9の乱数を取得
     * ⑤上記の②～④を合計3回実施して結合したものを仮パスワードとする。
     * </pre>
     *
     * @return 仮パスワード
     */
    public String createPassword() {
        StringBuilder strBuff = new StringBuilder();

        // 現在時刻でジェネレータ作成
        Random rnd = new Random(dateUtility.getCurrentTime().getTime());

        // 9桁の仮パスワードを生成
        while (strBuff.length() < 9) {
            // 0~25の乱数に97(aのchar)を足してa-zを取得

            strBuff.append((char) (rnd.nextInt(26) + 97));

            // 0~25の乱数に97(aのchar)を足してA-Zを取得
            strBuff.append((char) (rnd.nextInt(26) + 65));

            // 0~9の乱数を取得
            strBuff.append(rnd.nextInt(9));
        }

        return strBuff.toString();
    }
}
// PDR Migrate Customization to here
