/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;

import java.util.Map;

/**
 * valueObjects から mailTemplateType に対応した valueMap を作成する。
 *
 * @author tm27400
 * @version $Revision: 1.2 $
 */
public interface MailTemplateGetValueMapLogic {

    /**
     * LIM001001E={0} は登録されていないメールテンプレート種別です。
     */
    String INVALID_TEMPLATE_TYPE = "LIM001001";

    /**
     * mailTemplate に対応した valueMap を作成する。
     *
     * @param mailTemplateType メールテンプレート種別
     * @param valueObjects     valueMap へ変換する元のオブジェクト。Transformer クラスを実装したクラスが valuMap を生成する。
     * @return 結果
     */
    Map<String, ?> execute(HTypeMailTemplateType mailTemplateType, Object... valueObjects);
}
