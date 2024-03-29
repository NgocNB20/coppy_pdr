/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * お気に入りDao用検索条件Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class FavoriteSearchForDaoConditionDto extends AbstractConditionDto {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 除去対象商品SEQリスト
     */
    private List<Integer> exclusionGoodsSeqList;

    /**
     * サイト区分
     */
    private HTypeSiteType siteType;

    /**
     * 公開状態
     */
    private HTypeOpenDeleteStatus goodsOpenStatus;

    /**
     * 会員ランク
     */
    private HTypeMemberRank memberRank;
}
