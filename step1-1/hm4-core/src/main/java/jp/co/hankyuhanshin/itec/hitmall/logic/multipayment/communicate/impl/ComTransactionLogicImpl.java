/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.ComTransactionLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.ks.merchanttool.connectmodule.exception.PaygentB2BModuleConnectException;
import jp.co.ks.merchanttool.connectmodule.exception.PaygentB2BModuleException;
import jp.co.ks.merchanttool.connectmodule.system.PaygentB2BModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * PDR#022 ユーザー毎の支払方法表示制御<br/>
 *
 * <pre>
 * ペイジェントとの通信APIのラッパー
 *
 * カード情報を複数取得するよう修正
 * </pre>
 *
 * @author satoh
 */
@Component
// Paygent Customization from here
public class ComTransactionLogicImpl extends AbstractShopLogic implements ComTransactionLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ComTransactionLogicImpl.class);

    // PDR Migrate Customization from here
    /**
     * 電文種別キー
     */
    public static final String KEY_KIND = "telegram_kind";
    // PDR Migrate Customization to here

    /**
     * ペイジェントの通信APIを実行
     * <pre>
     * カード照会の場合は複数取得するよう修正
     * </pre>
     *
     * @param request Map key：POSTパラメータ名 value：設定値
     * @return レスポンス情報
     */
    @Override
    public ComResultDto execute(ComRequestDto request) {

        ComResultDto result = ApplicationContextUtility.getBean(ComResultDto.class);

        // 接続モジュールのインスタンスを取得する
        try {

            PaygentB2BModule module = new PaygentB2BModule();

            // ペイジェントへの要求をセットする
            // プロパティファイルから値を設定する
            module.reqPut("merchant_id", PropertiesUtil.getSystemPropertiesValue("paygent.merchant.id")); // マーチャントID
            module.reqPut("connect_id", PropertiesUtil.getSystemPropertiesValue("paygent.connect.id")); // 接続ID
            module.reqPut(
                            "connect_password",
                            PropertiesUtil.getSystemPropertiesValue("paygent.connect.password")
                         ); // 接続パスワード
            module.reqPut(
                            "telegram_version",
                            PropertiesUtil.getSystemPropertiesValue("paygent.telegram.version")
                         ); // 電文バージョン番号

            // Mapから値をセットする
            Map<String, String> requestMap = request.getRequestMap();
            for (String key : requestMap.keySet()) {
                module.reqPut(key, requestMap.get(key));
            }

            // ペイジェントへ要求を送信する。
            module.post();

            // 要求結果を取得する
            // 処理結果は通信モジュールからgetで取得する仕組みとなっているので、executeメソッド内で成型を行なう
            result = ApplicationContextUtility.getBean(ComResultDto.class);

            result.setResultStatus(module.getResultStatus()); // 処理結果
            result.setResponseCode(module.getResponseCode()); // レスポンスコード

            // PDR Migrate Customization from here
            // 返却されるレスポンス詳細に""が付与されているのでここで削除
            result.setResponseDetail(module.getResponseDetail().replace("\"", "")); // レスポンス詳細

            // 電文種別
            String type = request.getRequestMap().get(KEY_KIND);
            // データが存在する場合
            if (ComTransactionUtility.KIND_GET.equals(type)) {
                // 照会(027)の場合
                // 要求結果を複数件取得
                List<Map<String, String>> resultMapList = new ArrayList<>();
                while (module.hasResNext()) {
                    resultMapList.add(module.resNext());
                }
                result.setResultMapList(resultMapList);
            } else {
                if (module.hasResNext()) {
                    result.setResultMap(module.resNext());
                }
            }
            // PDR Migrate Customization to here

            return result;

        } catch (PaygentB2BModuleException | PaygentB2BModuleConnectException e) {
            LOGGER.error("ペイジェントへの接続に失敗しました。 内容: " + e.getMessage());
        }

        // 結果が設定されない場合、ペイジェント通信エラーとする。
        result.setResultStatus(ComTransactionUtility.RESCD_TRAN_ERROR); // 処理結果
        result.setResponseCode(ComTransactionUtility.RESPONSE_CD_RESULT_NULL); // レスポンスコード
        result.setResponseDetail(ComTransactionUtility.RESPONSE_DETAIL_RESULT_NULL); // レスポンス詳細

        return result;
    }
}
// Paygent Customization to here
