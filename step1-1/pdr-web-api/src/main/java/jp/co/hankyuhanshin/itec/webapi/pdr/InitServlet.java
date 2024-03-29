/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

// PDR#15 20161115 k-katoh 新規作成

/**
 * クラス概要（例：値の変換クラス）<br/>
 * クラス説明・備考を記述<br/>
 *
 * @author k-katoh
 */
@WebListener
public class InitServlet implements ServletContextListener {

    /**
     * Tomcat起動時処理<br/>
     *
     * @param event ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {

        ServletContext context = event.getServletContext();

        // messages.propertiesファイルをcontextに保存する
        Properties msgProp = getProperties("/messages.properties");
        context.setAttribute("msg", msgProp); // アプリケーションスコープに保存

        // conf.propertiesファイルをcontextに保存する
        Properties confProp = getProperties("/conf.properties");
        context.setAttribute("conf", confProp); // アプリケーションスコープに保存
    }

    /**
     * Tomcatシャットダウン時処理<br/>
     *
     * @param event ServletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    /**
     * 指定されたプロパティファイルを読み込む（UTF-8）
     *
     * @param path プロパティファイルのパス
     * @return プロパティファイルを格納したPropertiesオブジェクト
     */
    private Properties getProperties(String path) {

        Properties prop = null;

        try (InputStreamReader in = new InputStreamReader(this.getClass().getResourceAsStream(path), "UTF-8")) {
            prop = new Properties();
            prop.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }
}
