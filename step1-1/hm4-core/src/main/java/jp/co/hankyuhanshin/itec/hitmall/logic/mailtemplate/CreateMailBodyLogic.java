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
 * マップの値を指定されたテンプレートタイプの
 * メールテンプレートに代入して返すロジック
 *
 * @author kamei
 */
// 2023-renew general-mail from here
public interface CreateMailBodyLogic {

    /**
     * 実行
     *
     * @param mailContentsMap
     * @param hTypeMailTemplateType
     * @return 結果
     */
    String execute(Map<String, Object> mailContentsMap, HTypeMailTemplateType hTypeMailTemplateType);

}
// 2023-renew general-mail to here
