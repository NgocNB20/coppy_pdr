/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.NewsDetailsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.NewsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

/**
 * ニュース詳細 Controller
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@RequestMapping("/")
@Controller
public class NewsController extends AbstractController {

    /**
     * ニュース画面のHelperクラス
     */
    protected NewsHelper newsHelper;

    /**
     * ショップAPI
     */
    private final ShopApi shopApi;

    /**
     * アプリケーションログ出力Utility
     */
    public ApplicationLogUtility applicationLogUtility;

    /**
     * ログ<br/>
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);

    /**
     * コンストラクタ
     *
     * @param newsHelper
     * @param shopApi
     * @param applicationLogUtility
     */
    @Autowired
    public NewsController(NewsHelper newsHelper, ShopApi shopApi, ApplicationLogUtility applicationLogUtility) {
        this.newsHelper = newsHelper;
        this.shopApi = shopApi;
        this.applicationLogUtility = applicationLogUtility;
    }

    /**
     * ニュース詳細画面：初期処理
     *
     * @param newsModel
     * @param model
     * @return ニュース詳細画面
     */
    @GetMapping(value = {"/news", "/news.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "news")
    protected String doLoadIndex(NewsModel newsModel, Model model) {

        NewsEntity newsEntity = null;
        try {
            Integer nSeq = Integer.parseInt(newsModel.getNseq());
            if (nSeq.intValue() > 0) {
                NewsDetailsGetRequest newsDetailsGetRequest = new NewsDetailsGetRequest();
                newsDetailsGetRequest.setNewsSeq(nSeq);

                NewsEntityResponse newsEntityResponse = null;
                try {
                    newsEntityResponse = shopApi.getNewsDetails(newsDetailsGetRequest);

                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    // AppLevelListExceptionへ変換
                    addAppLevelListException(e);
                    throwMessage();
                }
                newsEntity = newsHelper.toNewsEntity(newsEntityResponse);
            }
        } catch (NumberFormatException ne) {
            // 不正なパラメータの場合、ここに入る。
            // 画面にエラーメッセージが表示されるためここでは例外処理は行わない。
            LOGGER.error("例外処理が発生しました", ne);
        } catch (AppLevelListException e) {
            // 存在しないニュースの場合、ここに入る。
            // エンティティがnullのため、後続の処理でエラー画面に遷移する
            LOGGER.error("例外処理が発生しました", e);
        }

        newsHelper.toPageForLoad(newsEntity, newsModel);

        if (newsModel.getNewsTime() == null) {
            // ニュースが非公開または存在しない
            LOGGER.debug("存在しない、もしくは公開されていないニュースが選択されました");
            return "redirect:/error.html";
        }

        return "news";
    }
}
