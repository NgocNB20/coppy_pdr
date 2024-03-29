/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.handler.csv.CsvDownloadHandler;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoCsvDownloadLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.csv.CsvDownloadBusinessService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * 会員情報
 * CSVダウンロードサービスの実装クラス：選択した会員のみ出力
 * 作成日：2021/04/13
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Service
public class MemberInfoCsvDownloadServiceImpl extends AbstractShopService implements MemberInfoCsvDownloadService {

    /**
     * CSVファイル名
     */
    private String fileName = FILE_NAME;

    /**
     * 会員データCSV出力ロジック
     */
    private final MemberInfoCsvDownloadLogic memberInfoCsvDownloadLogic;

    /**
     * CSVダウンロード機能
     */
    private final CsvDownloadBusinessService csvDownloadBusinessService;

    @Autowired
    public MemberInfoCsvDownloadServiceImpl(MemberInfoCsvDownloadLogic memberInfoCsvDownloadLogic,
                                            CsvDownloadBusinessService csvDownloadBusinessService) {
        this.memberInfoCsvDownloadLogic = memberInfoCsvDownloadLogic;
        this.csvDownloadBusinessService = csvDownloadBusinessService;
    }

    @Override
    public void execute(Object... parameters) throws IOException {
        // 出力対象会員SEQ一覧をパラメータから取得する
        List<Integer> memberInfoSeqList = (List<Integer>) parameters[0];

        HttpServletResponse response = (HttpServletResponse) parameters[1];

        // Stream方式で対象データを取得する
        Stream<MemberCsvDto> resultStream = this.memberInfoCsvDownloadLogic.execute(memberInfoSeqList);

        // CSVダウンロードオプションを設定する
        // 特に設定しない場合は、NULLを引数として渡す ⇒ デフォルトのオプションが適用される
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        csvDownloadBusinessService.execute(
                        resultStream, MemberCsvDto.class, response, csvDownloadOptionDto, getFileName());

    }

    /**
     * 出力CSVファイル名を設定する
     *
     * @return
     */
    private String getFileName() {
        if (StringUtils.isEmpty(this.fileName)) {
            this.fileName = CsvDownloadHandler.DEFAULT_FILE_NAME;
        }
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);
        return csvUtility.getDownLoadCsvFileName(this.fileName);
    }

}
