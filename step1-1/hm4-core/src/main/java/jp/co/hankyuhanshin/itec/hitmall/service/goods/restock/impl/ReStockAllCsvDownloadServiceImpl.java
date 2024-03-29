/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDownloadCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.handler.csv.CsvDownloadHandler;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.ReStockAllCsvDownloadLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.csv.CsvDownloadBusinessService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.ReStockAllCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * 入荷お知らせ商品CSV一括ダウンロードサービスクラス<br/>
 *
 * @author st75001
 */
@Service
public class ReStockAllCsvDownloadServiceImpl extends AbstractShopService implements ReStockAllCsvDownloadService {

    /**
     * CSVファイル名
     */
    private String fileName = FILE_NAME;

    /**
     * 商品検索CSV出力ロジッククラス<br/>
     */
    private final ReStockAllCsvDownloadLogic reStockAllCsvDownloadLogic;

    /**
     * CSVダウンロード機能
     */
    private final CsvDownloadBusinessService csvDownloadBusinessService;

    @Autowired
    public ReStockAllCsvDownloadServiceImpl(ReStockAllCsvDownloadLogic reStockAllCsvDownloadLogic,
                                            CsvDownloadBusinessService csvDownloadBusinessService) {
        this.reStockAllCsvDownloadLogic = reStockAllCsvDownloadLogic;
        this.csvDownloadBusinessService = csvDownloadBusinessService;
    }

    @Override
    public void execute(Object... parameters) throws IOException {
        // 検索オプションDTOをパラメータから取得する
        ReStockSearchForBackDaoConditionDto conditionDto = (ReStockSearchForBackDaoConditionDto) parameters[0];

        HttpServletResponse response = (HttpServletResponse) parameters[1];
        // Stream方式で対象データを取得する
        Stream<ReStockDownloadCsvDto> resultStream = this.reStockAllCsvDownloadLogic.execute(conditionDto);

        // CSVダウンロードオプションを設定する
        // 特に設定しない場合は、NULLを引数として渡す ⇒ デフォルトのオプションが適用される
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        csvDownloadBusinessService.execute(
                        resultStream, ReStockDownloadCsvDto.class, response, csvDownloadOptionDto, getFileName());
    }

    /**
     * 出力CSVファイル名を設定する
     *
     * @return Csvダウンロードファイル名の取得
     */
    private String getFileName() {
        if (StringUtils.isEmpty(this.fileName)) {
            this.fileName = CsvDownloadHandler.DEFAULT_FILE_NAME;
        }
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);
        return csvUtility.getDownLoadCsvFileName(this.fileName);
    }
}
