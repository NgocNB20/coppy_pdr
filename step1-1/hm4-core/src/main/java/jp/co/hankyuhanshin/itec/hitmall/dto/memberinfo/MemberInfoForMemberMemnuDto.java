/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 会員TOP表示用会員情報Dtoクラス
 *
 * @author s_tsuru
 */
@Data
@Component
@Scope("prototype")
public class MemberInfoForMemberMemnuDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
}
