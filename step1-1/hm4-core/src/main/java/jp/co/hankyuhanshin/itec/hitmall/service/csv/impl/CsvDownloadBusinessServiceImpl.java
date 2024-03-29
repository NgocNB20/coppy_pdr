package jp.co.hankyuhanshin.itec.hitmall.service.csv.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.handler.csv.CsvDownloadHandler;
import jp.co.hankyuhanshin.itec.hitmall.service.csv.CsvDownloadBusinessService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Stream;

@Service
public class CsvDownloadBusinessServiceImpl implements CsvDownloadBusinessService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvDownloadBusinessServiceImpl.class);

    @Override
    public void execute(Stream<?> csvDtoStream,
                        Class<?> csvClass,
                        HttpServletResponse response,
                        CsvDownloadOptionDto csvDownloadOptionDto,
                        String fileName) throws IOException {

        response.setCharacterEncoding("MS932");

        // HTTPレスポンスのヘッダー情報を生成する
        response.setContentType(MediaType.TEXT_PLAIN.toString());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        // CSVダウンロード時にLogFilterを通さないようにするため、responseTypeをレスポンスヘッダーに設定
        response.setHeader("responseType", "Async");

        CSVFormat outputCsvFormat = CsvDownloadHandler.csvFormatBuilder(csvDownloadOptionDto);

        // Apache Common CSV を特化したCSVプリンター（設定したCSVフォーマットに沿ってデータを出力）を初期化する
        StringWriter stringWriter = new StringWriter();

        CSVPrinter printer = new CSVPrinter(stringWriter, outputCsvFormat);

        PrintWriter writer = response.getWriter();

        // CSVヘッダーを出力する
        if (csvDownloadOptionDto.isOutputHeader()) {
            printer.printRecord(CsvDownloadHandler.outHeader(csvClass));
            writer.write(stringWriter.toString());
            stringWriter.getBuffer().setLength(0);
            writer.flush();
        }

        csvDtoStream.forEach(csvDto -> {
            try {
                printer.printRecord(CsvDownloadHandler.outCsvRecord(csvDto));
                writer.write(stringWriter.toString());
                stringWriter.getBuffer().setLength(0);
            } catch (IOException e) {
                LOGGER.error("例外処理が発生しました", e);
            }
        });
        writer.flush();
    }
}
