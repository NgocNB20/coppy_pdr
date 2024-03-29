/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.handler.csv.CsvDownloadHandler;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.favorite.FavoriteCsvDownloadLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.csv.CsvDownloadBusinessService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.favorite.FavoriteCsvDownloadService;
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
 * お気に入り商品検索CSVダウンロードサービスクラス<br/>
 *
 * @author takashima
 */
@Service
public class FavoriteCsvDownloadServiceImpl extends AbstractShopService implements FavoriteCsvDownloadService {

    /**
     * CSVファイル名
     */
    private String fileName = FILE_NAME;

    /**
     * 商品検索CSV出力ロジッククラス<br/>
     */
    private final FavoriteCsvDownloadLogic favoriteCsvDownloadLogic;

    /**
     * CSVダウンロード機能
     */
    private final CsvDownloadBusinessService csvDownloadBusinessService;

    @Autowired
    public FavoriteCsvDownloadServiceImpl(FavoriteCsvDownloadLogic favoriteCsvDownloadLogic,
                                          CsvDownloadBusinessService csvDownloadBusinessService) {
        this.favoriteCsvDownloadLogic = favoriteCsvDownloadLogic;
        this.csvDownloadBusinessService = csvDownloadBusinessService;
    }

    public void execute(Object... parameters) throws IOException {
        // 出力対象会員SEQ一覧をパラメータから取得する
        List<Integer> favoriteSeqList = (List<Integer>) parameters[0];

        HttpServletResponse response = (HttpServletResponse) parameters[1];

        // Stream方式で対象データを取得する
        Stream<FavoriteCsvDto> resultStream = this.favoriteCsvDownloadLogic.execute(favoriteSeqList);

        // CSVダウンロードオプションを設定する
        // 特に設定しない場合は、NULLを引数として渡す ⇒ デフォルトのオプションが適用される
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        csvDownloadBusinessService.execute(
                        resultStream, FavoriteCsvDto.class, response, csvDownloadOptionDto, getFileName());

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
