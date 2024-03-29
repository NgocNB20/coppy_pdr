/*

 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.application.filter;

import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * アクセスログフィルタ<br />
 * EncodingFilter の次に（２番目に）適応すること。
 *
 * @author tm27400
 * @author Kaneko (itec) 2012/08/10 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
public class LogFilter implements Filter {

    /**
     * オブジェクト初期化処理
     *
     * @param arg0 引数
     * @throws ServletException 発生したログ
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // do nothing
    }

    /**
     * オブジェクト破棄処理
     */
    @Override
    public void destroy() {
        // do nothing
    }

    /**
     * フィルタリング処理
     *
     * @param rq         リクエスト
     * @param rs         レスポンス
     * @param nextFilter 次のフィルタを呼ぶためのオブジェクト
     * @throws IOException      発生した I/O 例外
     * @throws ServletException 発生したサーブレット例外
     */
    @Override
    public void doFilter(final ServletRequest rq, final ServletResponse rs, final FilterChain nextFilter)
                    throws IOException, ServletException {

        //
        // オブジェクト変換
        //

        final HttpServletRequest request = (HttpServletRequest) rq;

        // -------------------------------------------------------------------------------------------------
        // 管理画面でCSVダウンロードを行った際、LogFilter処理の奥でExceptionが発生し、CSVダウンロードに失敗してしまうため、
        // CSVダウンロード時にLogFilterを通さないようにする
        // -------------------------------------------------------------------------------------------------
        String responseType = ((HttpServletResponse) rs).getHeader("responseType");
        if (!StringUtils.isEmpty(responseType) && "Async".equals(responseType)) {
            nextFilter.doFilter(rq, rs);
            return;
        }

        final LoggingServletResponse response = new LoggingServletResponse(rs);

        // アプリケーションログ出力Helper取得
        ApplicationLogUtility applicationLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);

        // スレッドにリクエストを登録
        applicationLogUtility.setHttpServletRequest(request);

        try {

            // 処理開始時刻
            final long accepted = System.currentTimeMillis();

            // 業務処理
            nextFilter.doFilter(rq, response);

            // 処理にかかった時間
            final long elapsed = System.currentTimeMillis() - accepted;

            // ログ出力
            applicationLogUtility.writeAccessLog(response, elapsed);

        } finally {

            // スレッドからリクエストを除去
            applicationLogUtility.remveHttpServletRequest();

        }

    }

}
