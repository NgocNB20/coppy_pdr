/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.FreeAreaEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.FreeAreaGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 特集ページ Controller
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@RequestMapping("/")
@Controller
public class SpecialController extends AbstractController {

    /**
     * ログ<br/>
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialController.class);

    /**
     * 特集ページ画面のHelperクラス
     */
    public SpecialHelper specialHelper;

    /**
     * ショップAPI
     */
    private final ShopApi shopApi;

    /**
     * アプリケーションログ出力Utility
     */
    public ApplicationLogUtility applicationLogUtility;

    /**
     * コンストラクタ
     *
     * @param specialHelper
     * @param shopApi
     * @param applicationLogUtility
     */
    @Autowired
    public SpecialController(SpecialHelper specialHelper,
                             ShopApi shopApi,
                             ApplicationLogUtility applicationLogUtility) {
        this.specialHelper = specialHelper;
        this.shopApi = shopApi;
        this.applicationLogUtility = applicationLogUtility;
    }

    /**
     * 特集ページ画面：初期処理
     *
     * @param specialModel
     * @param model
     * @return 特集ページ画面
     */
    @GetMapping(value = {"/special", "/special.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "special")
    protected String doLoadIndex(SpecialModel specialModel, Model model) {

        // 特集ページ用フリーエリア取得
        FreeAreaEntity freeAreaEntity = null;

        // 通常モード
        String fKey = specialModel.getFkey();

        if (StringUtils.isNotEmpty(fKey)) {
            Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();

            FreeAreaGetRequest freeAreaGetRequest = new FreeAreaGetRequest();
            freeAreaGetRequest.setFreeAreaKey(fKey);
            freeAreaGetRequest.setMemberInfoSeq(memberInfoSeq);

            FreeAreaEntityResponse freeAreaEntityResponse = null;
            try {
                freeAreaEntityResponse = shopApi.getNewsFreearea(freeAreaGetRequest);

            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            freeAreaEntity = specialHelper.toFreeAreaEntity(freeAreaEntityResponse);
        }

        if (freeAreaEntity == null) {
            // 特集ページ用フリーエリアが非公開または存在しない
            LOGGER.debug("存在しない、もしくは公開されていない特集ページが選択されました");
            return "redirect:/error.html";
        }

        specialHelper.toPageForLoad(freeAreaEntity, specialModel);

        return "special";

    }
}
