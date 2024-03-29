/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 仮会員登録Dto
 *
 * @author s_nose
 */
@Data
@Component
@Scope("prototype")
public class TempMemberInfoDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注SEQ
     */
    private Integer orderSeq;

    /**
     * 会員エンティティ
     */
    private MemberInfoEntity memberInfoEntity;

}
