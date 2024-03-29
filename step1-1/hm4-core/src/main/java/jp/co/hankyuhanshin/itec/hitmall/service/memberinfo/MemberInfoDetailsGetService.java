/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoDetailsDto;

/**
 * 会員詳細取得サービス<br/>
 * 管理画面専用
 * <pre>
 * ・会員情報
 * ・メルマガ情報
 * </pre>
 *
 * @author negishi
 * @version $Revision: 1.2 $
 */
public interface MemberInfoDetailsGetService {

    /**
     * エラーコード<br/>
     * 会員情報の取得に失敗した。
     */
    String MSGCD_MEMBERINFO_GET_NULL = "SMM000701";

    /**
     * サービス実行
     *
     * @param memberInfoSeq 会員SEQ
     * @return 会員詳細DTO
     */
    MemberInfoDetailsDto execute(Integer memberInfoSeq);

}
