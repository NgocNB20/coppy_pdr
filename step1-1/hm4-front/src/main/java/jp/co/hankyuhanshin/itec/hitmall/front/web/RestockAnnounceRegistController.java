// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.web;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ResultCountResponse;
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

/**
 * 入荷お知ら商品登録コントローラ
 *
 * @author Thang Doan (VJP)
 */
@RestController
@RequestMapping("/")
public class RestockAnnounceRegistController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestockAnnounceRegistController.class);

    /**
     * 郵便番号API
     */
    private final MemberInfoApi memberInfoApi;

    @Autowired
    public RestockAnnounceRegistController(MemberInfoApi memberInfoApi) {
        this.memberInfoApi = memberInfoApi;
    }

    /**
     * 入荷お知らせ商品登録
     *
     * @param gcd
     * @return restockAnnounceMapList
     */
    @GetMapping("/getRestockAnnounceRegisterResponse")
    @ResponseBody
    public List<Map<String, String>> getRestockAnnounceRegisterResponse(
                    @RequestParam(name = "gcd", required = false) String gcd) {
        int regist = 0;
        List<Map<String, String>> restockAnnounceMapList = new ArrayList<>();
        Map<String, String> restockAnnounceMap = new HashMap<>();
        String result = "false";
        if (gcd != null) {
            try {

                RestockAnnounceRegistRequest restockAnnounceRegistRequest = new RestockAnnounceRegistRequest();
                restockAnnounceRegistRequest.setGcd(gcd);
                restockAnnounceRegistRequest.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());

                ResultCountResponse resultCountResponse = null;
                try {
                    resultCountResponse = memberInfoApi.registRestockAnnounce(restockAnnounceRegistRequest);
                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    String errorMessage = AbstractController.buildMessageFromHttpClientErrorException(e);
                    // エラーメッセージをセット
                    restockAnnounceMap.put("errorRestockAnnounceMsg", errorMessage);
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
        restockAnnounceMap.put("restockAnnounceRegistSuccess", result);
        restockAnnounceMap.put("gcd", gcd);

        restockAnnounceMapList.add(restockAnnounceMap);
        return restockAnnounceMapList;
    }
}
// 2023-renew No65 to here
