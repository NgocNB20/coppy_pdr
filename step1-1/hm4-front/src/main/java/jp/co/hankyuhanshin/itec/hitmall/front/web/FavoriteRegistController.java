/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.web;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.ClientErrorResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.ErrorContent;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ResultCountResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * お気に入り商品登録コントローラ
 *
 * @author kimura
 */
@RestController
@RequestMapping("/")
public class FavoriteRegistController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteRegistController.class);

    /**
     * 郵便番号API
     */
    private final MemberInfoApi memberInfoApi;

    @Autowired
    public FavoriteRegistController(MemberInfoApi memberInfoApi) {
        this.memberInfoApi = memberInfoApi;
    }

    /**
     * お気に入り商品登録
     *
     * @param gcd
     * @return favoriteMapList
     */
    @GetMapping("/getFavoriteRegisterResponse")
    @ResponseBody
    public List<Map<String, String>> getFavoriteRegisterResponse(
                    @RequestParam(name = "gcd", required = false) String gcd) {
        int regist = 0;
        List<Map<String, String>> favoriteMapList = new ArrayList<>();
        Map<String, String> favoriteMap = new HashMap<>();
        String result = "false";
        if (gcd != null) {
            try {

                FavoriteRegistRequest favoriteRegistRequest = new FavoriteRegistRequest();
                favoriteRegistRequest.setGcd(gcd);
                favoriteRegistRequest.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());

                ResultCountResponse resultCountResponse = null;
                try {
                    resultCountResponse = memberInfoApi.registFavorite(favoriteRegistRequest);
                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    String errorMessage = AbstractController.buildMessageFromHttpClientErrorException(e);
                    // エラーメッセージをセット
                    favoriteMap.put("errorFavoriteMsg", errorMessage);
                }

                if (resultCountResponse != null && resultCountResponse.getResultCount() != null) {
                    regist = resultCountResponse.getResultCount();
                }

                if (regist != 0) {
                    result = "true";
                }
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);
                result = "false";
            }
        }
        favoriteMap.put("favoriteRegistSuccess", result);
        favoriteMap.put("gcd", gcd);

        favoriteMapList.add(favoriteMap);
        return favoriteMapList;
    }

    // 2023-renew No11 from here
    /**
     * お気に入り商品登録を解除する
     *
     * @param gcd
     * @return  favoriteMapResult
     */
    @GetMapping("/getFavoriteUnregisterResponse")
    @ResponseBody
    public Map<String, Object> getFavoriteUnregisterResponse(
            @RequestParam(name = "gcd", required = false) String gcd) {
        Map<String, Object> favoriteMapResult = new HashMap<>();
        String result = Boolean.FALSE.toString();
        if (StringUtils.isNotBlank(gcd)) {
            try {
                FavoriteListDeleteRequest request = new FavoriteListDeleteRequest();
                request.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
                request.setGcd(gcd.trim());

                ResultCountResponse resultCountResponse = null;
                try {
                    resultCountResponse = memberInfoApi.deleteFavoriteList(request);
                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    String errorMessage = AbstractController.buildMessageFromHttpClientErrorException(e);
                    // エラーメッセージをセット
                    favoriteMapResult.put("errorFavoriteMsg", errorMessage);
                }

                if (Objects.nonNull(resultCountResponse)
                        && Objects.nonNull(resultCountResponse.getResultCount())
                        && resultCountResponse.getResultCount() != 0) {
                    result = Boolean.TRUE.toString();
                }
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);
                result = Boolean.FALSE.toString();
            }
        }
        favoriteMapResult.put("favoriteUnregistStatus", result);
        favoriteMapResult.put("gcd", gcd);
        return favoriteMapResult;
    }
    // 2023-renew No11 to here
}
