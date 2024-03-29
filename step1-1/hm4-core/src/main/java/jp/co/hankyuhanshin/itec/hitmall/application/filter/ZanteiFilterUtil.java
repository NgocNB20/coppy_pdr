/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.application.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * フィルター暫定処理のクラス
 *
 * @author yt23807
 */
public class ZanteiFilterUtil {

    /**
     * ロガー
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(ZanteiFilterUtil.class);

    /**
     * フィルタースキップ対象のURL文字列パターン（css、js、画像、アイコン）
     */
    private static Pattern excludeUrls =
                    Pattern.compile("^.*(/css/|/js/|images|/sttc/|.ico|mainte.html).*$", Pattern.CASE_INSENSITIVE);

    /**
     * HIT-MALL Ver4 フェーズ１アプリケーションの場合、
     * 画像やjsなどへのアクセスも含め、
     * すべてのアクセスをFilterが捕捉してしまうため
     * フィルター処理をスキップするための仕組みを入れ込んでおく
     *
     * @param servletRequest HTTPリクエスト
     * @return true スキップする
     */
    public static boolean isSkipFilterProcess(ServletRequest servletRequest) {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // フィルタースキップ対象のURL文字列パターンにマッチする場合はskip
        String url = request.getRequestURI().toString();
        Matcher m = excludeUrls.matcher(url);

        // アクセスのあったURLとマッチ結果を出力（デバッグ用）
        //LOGGER.debug("URL：" + url + "/match=" + m.matches());
        return m.matches();
    }
}
