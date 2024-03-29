/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvReaderOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentUploadService;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvReaderUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * 出荷CSVファイルアップロードサービス実装クラス
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Service
public class ShipmentUploadServiceImpl extends AbstractShopService implements ShipmentUploadService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentUploadServiceImpl.class);

    /**
     * CSV読み込みのユーティリティ<br/>
     */
    private final CsvReaderUtil csvReaderUtil;

    public ShipmentUploadServiceImpl() {
        this.csvReaderUtil = new CsvReaderUtil();
    }

    /**
     * アップロードされたファイルの検証を行う
     *
     * @param csvFile               ファイル
     * @param counts                返却用レコード件数
     * @param maximumErrorDetection 検出するエラーの最大数
     * @return バリデーション結果
     */
    @Override
    public CsvValidationResult execute(File csvFile, List<Integer> counts, int maximumErrorDetection) {

        // CSV読み込みオプションを設定する
        CsvReaderOptionDto csvReaderOptionDto = new CsvReaderOptionDto();
        csvReaderOptionDto.setValidLimit(maximumErrorDetection);

        // Csvアップロード結果Dto作成
        CsvUploadResult csvUploadResult = new CsvUploadResult();

        /* Csvファイルを読み込み */
        List<ShipmentCsvDto> shipmentCsvDtoList;
        try {
            // CSVの全行分の DTO を作成してみる
            shipmentCsvDtoList =
                            (List<ShipmentCsvDto>) csvReaderUtil.readCsv(csvFile, ShipmentCsvDto.class, csvUploadResult,
                                                                         csvReaderOptionDto
                                                                        );
        } catch (Exception e) {
            // CSV読み込みで有り得ない例外が発生した場合
            LOGGER.error("例外処理が発生しました", e);
            CsvValidationResult csvValidationResult = new CsvValidationResult();
            csvReaderUtil.createUnexpectedExceptionMsg(csvValidationResult);
            return csvValidationResult;
        }

        // 出荷登録データ件数をセット (ヘッダー行分を-1している)
        counts.add(csvUploadResult.getRecordCount() - 1);

        return csvUploadResult.getCsvValidationResult();
    }

}
