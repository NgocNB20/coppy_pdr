/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hmbase.utility;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.handler.csv.CsvDownloadHandler;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 新HIT-MALLのCSVファイル出力
 * 作成日：2021/07/26
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
public class CsvExtractUtility {

    private final Class<?> csvDtoClass;
    private final List<?> csvDtoList;
    private final CsvDownloadOptionDto optionDto;
    private final Stream<Object> resultStream;
    private final String filePath;

    /**
     * デフォルトコンストラクタ
     *
     * @param csvDtoClass
     * @param parameters
     */
    public CsvExtractUtility(Class<?> csvDtoClass, Object... parameters) {
        // 処理対象のCSVクラス
        this.csvDtoClass = csvDtoClass;
        // CSVダウンロードオプション
        CsvDownloadOptionDto optionDtoParam;
        optionDtoParam = (CsvDownloadOptionDto) parameters[0];
        if (optionDtoParam == null) {
            optionDtoParam = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        }
        this.optionDto = optionDtoParam;
        // CSVダウンロードデータ（Stream形式）
        if (parameters[1] != null) {
            this.resultStream = (Stream<Object>) parameters[1];
        } else {
            this.resultStream = null;
        }
        // 出力ファイルのパス
        this.filePath = (String) parameters[2];
        // CsvDtoListありの場合
        if (parameters.length > 3 && parameters[3] != null) {
            this.csvDtoList = (List<?>) parameters[3];
        } else {
            this.csvDtoList = null;
        }
    }

    /**
     * CSVファイル出力
     *
     * @return 件数
     * @throws IOException
     */
    public int outCsv() throws IOException {
        // CSVを出力するWRITER
        try (FileWriter csvWriter = new FileWriter(this.filePath, this.optionDto.getCharset())) {

            // Apache Common CSV を特化したCSVフォーマットを準備する
            // 主にHIT-MALL独自のCsvDownloadOptionDtoからCSVFormatに変換する
            CSVFormat outputCsvFormat = CsvDownloadHandler.csvFormatBuilder(this.optionDto);

            // Apache Common CSV を特化したCSVプリンター（設定したCSVフォーマットに沿ってデータを出力）を初期化する
            CSVPrinter printer = new CSVPrinter(csvWriter, outputCsvFormat);

            // CSVヘッダーを出力する
            if (this.optionDto.isOutputHeader()) {
                printer.printRecord(CsvDownloadHandler.outHeader(this.csvDtoClass));
                csvWriter.flush();
            }

            // 出力件数のカウンタ
            int cnt = 0;

            // CSVデータを1件ずつ出力する
            // Stream形式＆List形式の双方を対応する
            if (this.csvDtoList == null) {
                Iterator<Object> resultIterator = this.resultStream.iterator();
                while (resultIterator.hasNext()) {
                    printer.printRecord(CsvDownloadHandler.outCsvRecord(resultIterator.next()));
                    csvWriter.flush();
                    cnt++;
                }
                // 処理終了後に、Streamをクローズする
                this.resultStream.close();
            } else {
                for (Object csvDto : this.csvDtoList) {
                    printer.printRecord(CsvDownloadHandler.outCsvRecord(csvDto));
                    csvWriter.flush();
                    cnt++;
                }
            }
            printer.flush();
            return cnt;
        }
    }

    /**
     * CSVファイル出力（byte型）
     *
     * @return CSVコンテンツ
     * @throws IOException
     */
    public byte[] outCsvToByte() throws IOException {
        // CSVを出力するWRITER
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter csvWriter = new PrintWriter(outputStream, true, this.optionDto.getCharset());

        // Apache Common CSV を特化したCSVフォーマットを準備する
        // 主にHIT-MALL独自のCsvDownloadOptionDtoからCSVFormatに変換する
        CSVFormat outputCsvFormat = CsvDownloadHandler.csvFormatBuilder(this.optionDto);

        // Apache Common CSV を特化したCSVプリンター（設定したCSVフォーマットに沿ってデータを出力）を初期化する
        CSVPrinter printer = new CSVPrinter(csvWriter, outputCsvFormat);

        // CSVヘッダーを出力する
        if (this.optionDto.isOutputHeader()) {
            printer.printRecord(CsvDownloadHandler.outHeader(this.csvDtoClass));
            csvWriter.flush();
        }

        // CSVデータを1件ずつ出力する
        Iterator<Object> resultIterator = this.resultStream.iterator();
        while (resultIterator.hasNext()) {
            printer.printRecord(CsvDownloadHandler.outCsvRecord(resultIterator.next()));
            csvWriter.flush();
        }

        // 処理終了後に、Streamをクローズする
        this.resultStream.close();
        printer.flush();

        return outputStream.toByteArray();
    }

    /**
     * TSVファイル出力
     *
     * @return 件数
     * @throws IOException IOException
     */
    public int outTsv() throws IOException {
        // CSVを出力するWRITER
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.filePath)) {

            OutputStreamWriter outputStreamWriter =
                            new OutputStreamWriter(fileOutputStream, this.optionDto.getCharset());
            CSVFormat outputCsvFormat = CsvDownloadHandler.tsvFormatBuilder(this.optionDto);
            CSVPrinter csvPrinter = new CSVPrinter(outputStreamWriter, outputCsvFormat);

            // CSVヘッダーを出力する
            if (this.optionDto.isOutputHeader()) {
                csvPrinter.printRecord(CsvDownloadHandler.outHeader(this.csvDtoClass));
            }

            // 出力件数のカウンタ
            int cnt = 0;

            // CSVデータを1件ずつ出力する
            // Stream形式＆List形式の双方を対応する
            if (this.csvDtoList == null) {
                Iterator<Object> resultIterator = this.resultStream.iterator();
                while (resultIterator.hasNext()) {
                    csvPrinter.printRecord(CsvDownloadHandler.outCsvRecord(resultIterator.next()));
                    cnt++;
                }
                // 処理終了後に、Streamをクローズする
                this.resultStream.close();
            } else {
                for (Object csvDto : this.csvDtoList) {
                    csvPrinter.printRecord(CsvDownloadHandler.outCsvRecord(csvDto));
                    cnt++;
                }
            }
            csvPrinter.flush();
            return cnt;
        }
    }

}
