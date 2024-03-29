/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.mailtemplate.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.mail.MailTemplateIndexDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetIndexListLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.mailtemplate.MailTemplateGetIndexListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * MailTemplateGetIndexListServiceの実装
 *
 * @author tm27400
 * @version $Revision: 1.2 $
 */
@Service
public class MailTemplateGetIndexListServiceImpl extends AbstractShopService
                implements MailTemplateGetIndexListService {

    /**
     * ロジック
     */
    private final MailTemplateGetIndexListLogic mailTemplateGetIndexListLogic;

    @Autowired
    public MailTemplateGetIndexListServiceImpl(MailTemplateGetIndexListLogic mailTemplateGetIndexListLogic) {
        this.mailTemplateGetIndexListLogic = mailTemplateGetIndexListLogic;
    }

    /**
     * 主処理
     *
     * @param types types
     * @return 結果
     */
    @Override
    public List<MailTemplateIndexDto> execute(HTypeMailTemplateType... types) {
        Integer shopSeq = 1001;

        List<MailTemplateIndexDto> indexList = this.mailTemplateGetIndexListLogic.execute(shopSeq);

        if (types.length == 0) {
            return indexList;
        }

        List<HTypeMailTemplateType> typeList = Arrays.asList(types);

        //
        // 引数の HTypeMailTemplateType 一覧にない種別種別はリストから除去する
        //

        for (Iterator<MailTemplateIndexDto> iter = indexList.iterator(); iter.hasNext(); ) {
            MailTemplateIndexDto dto = iter.next();
            if (!typeList.contains(dto.getMailTemplateType())) {
                iter.remove();
            }
        }

        return indexList;
    }
}
