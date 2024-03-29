/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoAllCsvDownloadLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * 会員情報
 * CSVダウンロードロジックの実装クラス：全件出力
 * 作成日：2021/04/13
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Component
public class MemberInfoAllCsvDownloadLogicImpl extends AbstractShopLogic implements MemberInfoAllCsvDownloadLogic {

    /**
     * 会員情報DAO
     */
    private final MemberInfoDao memberInfoDao;

    @Autowired
    public MemberInfoAllCsvDownloadLogicImpl(MemberInfoDao memberInfoDao) {
        this.memberInfoDao = memberInfoDao;
    }

    @Override
    public Stream<MemberCsvDto> execute(MemberInfoSearchForDaoConditionDto conditionDto) {
        return this.memberInfoDao.exportCsvByMemberInfoSearchForDaoConditionDtoStream(conditionDto);
    }

}
