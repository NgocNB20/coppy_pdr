/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalTreatmentFlag;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 会員業務ヘルパークラス
 *
 * @author natume
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更
 */
@Component
public class MemberInfoUtility {

    /**
     * コンストラクタ
     */
    public MemberInfoUtility() {
        // nop
    }

    /**
     * ショップユニークIDの作成
     * ※4桁0埋めのショップSEQ + メールアドレスの小文字
     *
     * @param shopSeq ショップSEQ
     * @param mail    メールアドレス
     * @return ショップユニークID
     */
    public String createShopUniqueId(Integer shopSeq, String mail) {
        if (shopSeq == null || mail == null) {
            return null;
        }

        // 4桁0埋めのショップSEQ + メールアドレスの小文字
        return new DecimalFormat("0000").format(shopSeq) + mail.toLowerCase();
    }

    /**
     * 表示金額への変換
     *
     * @param price 金額
     * @return 表示金額への変換
     */
    public String toString(BigDecimal price) {
        NumberFormat f = NumberFormat.getNumberInstance();
        return f.format(price);
    }

    /**
     * 会員SEQが会員を表しているかを判定
     *
     * @param memberInfoSeq 会員SEQ
     * @return true：会員の場合
     */
    public boolean isMember(Integer memberInfoSeq) {
        return !isGuest(memberInfoSeq);
    }

    /**
     * 会員SEQがゲストを表しているかを判定
     *
     * @param memberInfoSeq 会員SEQ
     * @return true：ゲストの場合
     */
    public boolean isGuest(Integer memberInfoSeq) {
        return memberInfoSeq == null || memberInfoSeq == 0;
    }

    /**
     * SHA-256ハッシュ値計算用文字列生成
     * ショップSEQ、会員SEQ、パスワードを連結し、SHA-256ハッシュ化用文字列を生成する
     *
     * @param shopSeq       ショップSEQ
     * @param memberInfoSeq 会員SEQ
     * @param password      パスワード
     * @return SHA-256ハッシュ値計算用文字列
     */
    public String createSHA256HashValue(Integer shopSeq, Integer memberInfoSeq, String password) {
        return shopSeq.toString() + memberInfoSeq.toString() + password;
    }

    /**
     * 設定ファイルにあるアカウントロックログイン回数を取得
     *
     * @return アカウントロックログイン回数
     */
    public int getAccountLockCount() {
        String str = PropertiesUtil.getSystemPropertiesValue("account.lock.count");
        /*
         * 設定値は99以下の数字であること 会員テーブルのログイン失敗回数の桁数が2桁のため
         */
        if (str == null || !str.matches("^0$|^[1-9][0-9]?$")) {
            throw new IllegalArgumentException("設定ファイルの、アカウントロックログイン回数(account.lock.count)の設定が不正です。99以下の数字を設定してください。[ "
                                               + str + " ]");
        }

        return Integer.parseInt(str);

    }

    /**
     * アカウントロック状態を取得する<br/>
     *
     * @param lockTime アカウントロック日時
     * @return true..ロックされている / false..ロックされていない
     */
    public boolean isAccountStatusLock(Timestamp lockTime) {
        return lockTime == null ? false : true;
    }

    /**
     * アカウントロック機能が利用可能かどうか
     * 設定ファイルの account.lock.count の値が0の時は、機能を無効化する。
     *
     * @return true..利用可能
     */
    public boolean isAvailableAccountLock() {
        return getAccountLockCount() > 0;
    }

    // 2023-renew No25 from here
    /**
     * 仮パスワードを発行します。<br/>
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
        // 処理実施日時
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        StringBuilder strBuff = new StringBuilder();

        // 現在時刻でジェネレータ作成
        Random rnd = new Random(dateUtility.getCurrentTime().getTime());

        // 9桁の仮パスワードを生成
        while (strBuff.length() < 9) {
            // 0~25の乱数に97(aのchar)を足してa-zを取得
            strBuff.append(rnd.nextInt(26) + 97);

            // 0~25の乱数に97(aのchar)を足してA-Zを取得
            strBuff.append(rnd.nextInt(26) + 65);

            // 0~9の乱数を取得
            strBuff.append(rnd.nextInt(9));
        }

        return strBuff.toString();
    }
    // 2023-renew No25 to here

    // 2023-renew AddNo2 from here
    /**
     * ...
     *
     * @param medicalTreatmentFlag
     * @param medicalTreatmentTitleList
     * @return
     */
    public String getMedicalTreatment(String medicalTreatmentFlag, List<String> medicalTreatmentTitleList) {
        if (StringUtil.isBlank(medicalTreatmentFlag)) {
            return StringUtils.EMPTY;
        }
        List<String> medicalTreatments = new ArrayList<>();
        String[] medicalTreatment =
                        String.format("%-10s", medicalTreatmentFlag).replace(" ", "0").split("");
        for (int i=0; i<medicalTreatment.length; i++) {
            if (HTypeMedicalTreatmentFlag.ON.getValue().equals(medicalTreatment[i])) {
                medicalTreatments.add(medicalTreatmentTitleList.get(i));
            }
        }
        return String.join("、", medicalTreatments);
    }
    // 2023-renew AddNo2 to here
}
