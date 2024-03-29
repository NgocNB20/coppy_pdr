/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;

/**
 * メールテンプレート種別で使用できるプレースホルダ情報を取得するサービス
 *
 * @author tm27400
 * @version $Revision: 1.2 $
 */
public interface MailTemplateGetPlaceholderGuideService {

    /**
     * 実行
     *
     * @param tempType tempType
     * @return 結果
     */
    String execute(HTypeMailTemplateType tempType);
}
