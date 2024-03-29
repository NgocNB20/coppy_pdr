/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.handler.csv.CsvDownloadHandler;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryCsvDownloadLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.csv.CsvDownloadBusinessService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryCsvDownLoadService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * カテゴリ一括アップロード<br/>
 * カテゴリCsvファイルの一括アップロードを行う。<br/>
 *
 * @author kamei
 */
// 2023-renew categoryCSV from here
@Service
public class CategoryCsvDownLoadServiceImpl extends AbstractShopService implements CategoryCsvDownLoadService {

    /**
     * CSVファイル名
     */
    private String fileName = FILE_NAME;

    /**
     * カテゴリデータCSV出力ロジック
     */
    private final CategoryCsvDownloadLogic categoryCsvDownloadLogic;

    /**
     * CSVダウンロード機能
     */
    private final CsvDownloadBusinessService csvDownloadBusinessService;

    /**
     * コンストラクタ
     *
     * @param categoryCsvDownloadLogic
     * @param csvDownloadBusinessService
     */
    @Autowired
    public CategoryCsvDownLoadServiceImpl(CategoryCsvDownloadLogic categoryCsvDownloadLogic,
                                          CsvDownloadBusinessService csvDownloadBusinessService) {
        this.categoryCsvDownloadLogic = categoryCsvDownloadLogic;
        this.csvDownloadBusinessService = csvDownloadBusinessService;
    }

    /**
     * カテゴリCSVダウンロードサービス実装
     *
     * @param parameters
     * @throws IOException
     */
    @Override
    public void execute(Object... parameters) throws IOException {
        // 検索オプションDTOをパラメータから取得する
        CategorySearchForDaoConditionDto conditionDto = (CategorySearchForDaoConditionDto) parameters[0];

        HttpServletResponse response = (HttpServletResponse) parameters[1];

        // Stream方式で対象データを取得する
        Stream<CategoryCsvDto> resultStream = this.categoryCsvDownloadLogic.execute(conditionDto);

        // CSVダウンロードオプションを設定する
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        csvDownloadBusinessService.execute(
                        resultStream, CategoryCsvDto.class, response, csvDownloadOptionDto, getFileName());

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
// 2023-renew categoryCSV to here
