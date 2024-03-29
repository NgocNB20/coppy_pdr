/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

import java.util.List;

/**
 * 検索結果一覧取得<br/>
 *
 * @author natume
 * @version $Revision: 1.4 $
 */
public interface MemberInfoResultSearchLogic {

    /**
     * 検索結果一覧取得<br/>
     *
     * @param memberInfoConditionDto 会員検索条件Dto
     * @return 会員エンティティリスト
     */
    List<MemberInfoEntity> execute(MemberInfoSearchForDaoConditionDto memberInfoConditionDto);
}
