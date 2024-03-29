/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.mail.MailTemplateIndexDto;

import java.util.List;

/**
 * メールテンプレート見出し一覧を取得するサービス
 *
 * @author tm27400
 * @version $Revision: 1.2 $
 */
public interface MailTemplateGetIndexListService {

    /**
     * メールテンプレート見出し一覧を取得する
     *
     * @param types テンプレート種別一覧
     * @return メールテンプレート見出し一覧
     */
    List<MailTemplateIndexDto> execute(HTypeMailTemplateType... types);
}
