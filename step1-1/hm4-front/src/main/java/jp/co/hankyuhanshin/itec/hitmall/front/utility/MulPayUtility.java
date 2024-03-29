/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.utility;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import org.springframework.stereotype.Component;

/**
 * マルチペイメントヘルパークラス<br/>
 *
 * @author hirata
 * @author Kaneko (itec) 2012/08/08 UtilからHelperへ変更
 */
@Component
public class MulPayUtility {

    /**
     * 決済代行会員IDを作成する<br/>
     *
     * @param memberSeq 会員SEQ
     * @return 決済代行会員ID
     */
    public String createPaymentMemberId(Integer memberSeq) {
        if (memberSeq == null) {
            return null;
        }

        // Paygent Customization from here
        // システムプロパティから環境値取得
        String gmoPrefix = PropertiesUtil.getSystemPropertiesValue("paygent.member.prefix");
        // Paygent Customization to here

        // プロパティ設定をしていない場合、
        // 決済代行会員IDが"null会員SEQ"となるのを回避する
        if (gmoPrefix == null) {
            gmoPrefix = "";
        }

        return gmoPrefix + memberSeq.toString();
    }
}
