/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.access;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccessType;
import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * アクセス情報Dao用検索条件Dtoクラス
 *
 * @author DtoGenerator
 */
@Data
@Component
@Scope("prototype")
public class AccessInfoSearchForDaoConditionDto extends AbstractConditionDto {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** ショップSEQ */
    public Integer shopSeq;

    /** アクセス種別 */
    public HTypeAccessType accessType;

    /** 端末識別番号 */
    public String accessUid;

    /** 期間(From) */
    public Timestamp campaignFromDate;

    /** 期間(To) */
    public Timestamp campaignToDate;

    /** 広告コード */
    public String campaignCode;

    /** 広告名 */
    public String campaignName;

    /** メモ */
    public String note;

    /** キャンペーンコードリスト */
    public List<String> campaignCodeList;
}
