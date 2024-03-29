/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;

import java.util.List;

/**
 * コンビニ名称取得ロジック<br/>
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
public interface ConvenienceLogic {

    /**
     * コンビニ名称取得処理<br/>
     *
     * @param shopSeq     ショップSEQ
     * @param conveniCode 選択コンビニコード
     * @return コンビニ名称エンティティ
     */
    ConvenienceEntity execute(Integer shopSeq, String conveniCode);

    /**
     * コンビニ名称リスト取得処理<br/>
     *
     * @return コンビニ名称エンティティリスト
     */
    List<ConvenienceEntity> getConvenienceList();
}
