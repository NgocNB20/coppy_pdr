/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.mail.MailTemplateIndexDto;

import java.util.List;

/**
 * テンプレート見出し一覧を取得するロジック
 *
 * @author tm27400
 * @version $Revision: 1.2 $
 */
public interface MailTemplateGetIndexListLogic {

    /**
     * 実行
     *
     * @param shopSeq shopSeq
     * @return 結果
     */
    List<MailTemplateIndexDto> execute(Integer shopSeq);

}
