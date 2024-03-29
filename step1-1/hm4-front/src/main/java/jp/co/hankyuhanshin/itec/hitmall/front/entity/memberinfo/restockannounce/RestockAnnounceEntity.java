// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeRestockAnnounceWatchFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeRestockStatus;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 入荷お知らせクラス
 *
 * @author Thang Doan (VJP)
 */

@Data
@Component
@Scope("prototype")
public class RestockAnnounceEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ (FK)（必須）
     */
    private Integer memberInfoSeq;

    /**
     * 商品SEQ (FK)（必須）
     */
    private Integer goodsSeq;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;
}
// 2023-renew No65 to here
