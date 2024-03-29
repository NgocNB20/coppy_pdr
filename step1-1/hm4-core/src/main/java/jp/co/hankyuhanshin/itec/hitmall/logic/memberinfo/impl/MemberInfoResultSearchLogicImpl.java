/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoResultSearchLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 会員検索結果一覧取得ロジック<br/>
 *
 * @author natume
 * @version $Revision: 1.5 $
 */
@Component
public class MemberInfoResultSearchLogicImpl extends AbstractShopLogic implements MemberInfoResultSearchLogic {

    /**
     * MemberInfoDao<br/>
     */
    private final MemberInfoDao memberInfoDao;

    @Autowired
    public MemberInfoResultSearchLogicImpl(MemberInfoDao memberInfoDao) {
        this.memberInfoDao = memberInfoDao;
    }

    /**
     * 会員情報一覧取得処理<br/>
     *
     * @param memberInfoConditionDto 会員検索条件Dto
     * @return 会員エンティティリスト
     */
    @Override
    public List<MemberInfoEntity> execute(MemberInfoSearchForDaoConditionDto memberInfoConditionDto) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoConditionDto", memberInfoConditionDto);

        // 一覧取得
        return memberInfoDao.getMemberInfoConditionDtoList(
                        memberInfoConditionDto, memberInfoConditionDto.getPageInfo().getSelectOptions());
    }
}
