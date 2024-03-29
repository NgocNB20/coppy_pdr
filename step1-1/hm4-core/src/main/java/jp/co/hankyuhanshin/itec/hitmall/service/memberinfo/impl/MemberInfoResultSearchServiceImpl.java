/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoResultSearchLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoResultSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * バック会員検索サービス実装<br/>
 *
 * @author natume
 * @version $Revision: 1.5 $
 */
@Service
public class MemberInfoResultSearchServiceImpl extends AbstractShopService implements MemberInfoResultSearchService {

    /**
     * 会員検索ロジック<br/>
     */
    private final MemberInfoResultSearchLogic memberInfoResultSearchLogic;

    @Autowired
    public MemberInfoResultSearchServiceImpl(MemberInfoResultSearchLogic memberInfoResultSearchLogic) {
        this.memberInfoResultSearchLogic = memberInfoResultSearchLogic;
    }

    /**
     * 検索処理<br/>
     *
     * @param memberInfoConditionDto 会員検索条件DTO
     * @return 会員情報エンティティリスト
     */
    @Override
    public List<MemberInfoEntity> execute(MemberInfoSearchForDaoConditionDto memberInfoConditionDto) {

        // 検索条件作成
        memberInfoConditionDto.setShopSeq(1001);

        // リスト取得
        List<MemberInfoEntity> resultList = memberInfoResultSearchLogic.execute(memberInfoConditionDto);

        // 結果セット
        return resultList;
    }
}
