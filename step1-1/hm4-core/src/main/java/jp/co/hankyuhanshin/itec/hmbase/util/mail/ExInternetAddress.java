/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hmbase.util.mail;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 標準の InternetAddress クラスを継承したクラス。<br />
 *
 * @author Doan Thang (VTI Japan Co. Ltd)
 */
public class ExInternetAddress extends InternetAddress {

    /**
     * シリアル
     */
    private static final long serialVersionUID = -3841722236108666069L;

    /**
     * 個人名を含まないメールアドレスのコンストラクタ。
     *
     * @param baredAddress メールアドレス
     */
    public ExInternetAddress(final String baredAddress) {
        super();
        this.setAddress(baredAddress);
    }

    /**
     * コンストラクタ。
     *
     * @param address 人名とメールアドレスの両方またはメールアドレスのみが含まれる情報
     * @param charset 人名のエンコード方法
     * @throws AddressException メールアドレスが不正な場合にスローされる
     */
    public ExInternetAddress(final String address, final String charset) throws AddressException {
        super(extractEmalAddress(address));

        try {
            final String personal = extractPersonal(address);
            if (personal != null) {
                this.setPersonal(personal, charset);
            }
        } catch (final UnsupportedEncodingException uee) {
            // 文字セットくらいちゃんとしたものが渡されてくると信用して欲しい。> java
            throw new RuntimeException(uee.getMessage(), uee);
        }
    }

    /**
     * "MAIL OWNER NAME&lt;mail@address.xxx&gt;" の "mail@address.xxx" 部分を取得する処理。
     *
     * @param address アドレス文字列
     * @return メールアドレス部分
     */
    private static String extractEmalAddress(final String address) {

        final Pattern pat = Pattern.compile("\\<[^\\<]*\\>"); // 最小マッチの書き方がわからん。
        final Matcher matcher = pat.matcher(address);

        // 名前<メールアドレス>形式でない場合
        if (matcher == null || !matcher.find()) {
            return address;
        }

        String email = null;

        do {
            email = matcher.group().replaceAll("(\\<|\\>)", "");
        } while (matcher.find());

        return email;
    }

    /**
     * "MAIL OWNER NAME&lt;mail@address.xxx&gt;" の "MAIL OWNER NAME" 部分を取得する処理。
     *
     * @param address アドレス文字列
     * @return パーソナル部分
     */
    private static String extractPersonal(final String address) {
        final Pattern pat = Pattern.compile("\\<.+\\>");
        final Matcher matcher = pat.matcher(address);

        // mail と personal が区切られていいない様であれば personal は null とする。
        if (!matcher.find()) {
            return null;
        }

        final String personal = address.substring(0, address.lastIndexOf('<'));

        return personal;
    }
}
