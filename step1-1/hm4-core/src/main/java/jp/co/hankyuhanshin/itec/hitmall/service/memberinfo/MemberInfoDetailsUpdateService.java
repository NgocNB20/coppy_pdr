/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoDetailsDto;

/**
 * 会員詳細情報更新サービス<br/>
 * 管理画面で使う事を想定としている。<br/>
 * <pre>
 * ・会員情報
 * ・メルマガ購読者情報
 * </pre>
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
public interface MemberInfoDetailsUpdateService {

    /**
     * サービス実行
     *
     * @param memberInfoDetailsDto 会員詳細DTO
     * @return 更新件数
     */
    int execute(MemberInfoDetailsDto memberInfoDetailsDto);

}
