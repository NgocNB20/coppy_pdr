/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

import java.util.List;

/**
 * 会員情報リスト取得ロジック<br/>
 *
 * @author natume
 * @version $Revision: 1.4 $
 */
public interface MemberInfoListGetLogic {

    /**
     * 会員情報リストを取得する<br />
     *
     * @param memberInfoSeqList 会員SEQリスト
     * @return 会員情報エンティティリスト
     */
    List<MemberInfoEntity> execute(List<Integer> memberInfoSeqList);

}
