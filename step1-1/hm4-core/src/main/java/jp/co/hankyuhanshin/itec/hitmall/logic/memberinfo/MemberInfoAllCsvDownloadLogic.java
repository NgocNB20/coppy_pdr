/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoSearchForDaoConditionDto;

import java.util.stream.Stream;

/**
 * 会員情報
 * CSVダウンロードロジック：全件出力
 * 作成日：2021/04/13
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
public interface MemberInfoAllCsvDownloadLogic {

    Stream<MemberCsvDto> execute(MemberInfoSearchForDaoConditionDto conditionDto);

}
