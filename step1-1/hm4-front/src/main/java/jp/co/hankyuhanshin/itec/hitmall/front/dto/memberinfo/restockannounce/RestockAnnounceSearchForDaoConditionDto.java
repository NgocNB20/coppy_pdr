// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.front.base.dto.AbstractConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 入荷お知らせDao用検索条件Dtoクラス
 *
 * @author Thang Doan (VJP)
 */
@Data
@Component
@Scope("prototype")
public class RestockAnnounceSearchForDaoConditionDto extends AbstractConditionDto {

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
     * 入荷状態
     */
    private String restockstatus;
}
// 2023-renew No65 to here
