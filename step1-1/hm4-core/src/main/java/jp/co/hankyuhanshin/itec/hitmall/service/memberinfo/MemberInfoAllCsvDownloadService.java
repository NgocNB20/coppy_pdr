/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

import java.io.IOException;

/**
 * 会員情報
 * CSVダウンロードサービス：全件出力
 * 作成日：2021/04/13
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
public interface MemberInfoAllCsvDownloadService {

    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "member";

    void execute(Object... parameters) throws IOException;

}
