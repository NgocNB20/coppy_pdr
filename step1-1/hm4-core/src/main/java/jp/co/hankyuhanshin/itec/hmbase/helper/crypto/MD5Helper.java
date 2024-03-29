/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hmbase.helper.crypto;

import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5計算クラス<br/>
 * MD5関連の処理を行う<br/>
 *
 * @author matsumoto
 * @version $Revision: 1.2 $
 */
@Component
public class MD5Helper {
    /**
     * 指定された文字列とハッシュ値の照合を行う<br/>
     * パラメータに null が指定された場合、　false を返却します。
     *
     * @param input 比較する文字列
     * @param hash  比較するハッシュ値
     * @return 文字列とハッシュ値が一致した場合 true
     */
    public boolean verify(String input, String hash) {
        if (hash == null | input == null) {
            return false;
        }

        return hash.equals(createHash(input));
    }

    /**
     * MD5ハッシュ値を作成する<br/>
     * パラメータに null が指定された場合、 nullを返却します。
     *
     * @param plaintext ハッシュ計算対象の文字列
     * @return MD5で求めたハッシュ値
     */
    public String createHash(String plaintext) {
        if (plaintext == null) {
            return null;
        }

        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(plaintext.getBytes());
            //            return org.seasar.framework.util.StringUtil.toHex(digest.digest());
            return StringUtil.toHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            // エンコードはMD5固定なので基本的に発生しない。
            throw new RuntimeException(e);
        }
    }
}
