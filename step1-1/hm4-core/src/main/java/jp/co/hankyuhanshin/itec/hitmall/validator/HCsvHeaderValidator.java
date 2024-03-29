/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.validator;

import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVCsvHeader;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvReaderUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.QuoteMode;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * <span class="logicName">【CSVヘッダ】</span>CSVヘッダ行の確認を行うバリデータ。<br />
 * <br />
 * CSVファイルのヘッダが指定のヘッダ情報と異なる場合エラー<br />
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Data
public class HCsvHeaderValidator implements ConstraintValidator<HVCsvHeader, MultipartFile> {

    /**
     * CSVヘッダバリデーションNG時メッセージ
     */
    public static final String CSV_HEADER_VALIDATOR_MESSAGE_ID = "{HCsvHeaderValidator.INVALID_detail}";

    /**
     * ファイルアップロード失敗メッセージ
     */
    public static final String UPLOAD_FAILED_MESSAGE_ID = "{AYH000107E}";

    /**
     * ヘッダ行情報を収集する対象のクラス
     */
    private Class<?> csvDtoClass;

    /**
     * 部分アップロードを禁止する:true 許可する:false
     */
    private boolean restrict;

    /**
     * CSVファイルの文字セット
     */
    private String csvCharset;

    @Override
    public void initialize(HVCsvHeader annotation) {
        this.csvDtoClass = annotation.csvDtoClass();
        this.restrict = annotation.restrict();
        this.csvCharset = annotation.csvCharset();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        // null or 空の場合は未実施
        if (value == null || !StringUtils.hasLength(value.getOriginalFilename())) {
            return true;
        }

        // CSVではないファイルの場合は未実施
        if (!checkFileExtension(value.getOriginalFilename())) {
            return true;
        }

        // Object class は無効とする
        if (csvDtoClass == Object.class) {
            return true;
        }

        // ----------------------------------------------
        // CSVヘッダーの検証を行う
        // ----------------------------------------------
        CsvReaderUtil csvReaderUtil = new CsvReaderUtil();
        List<String> expectedCsvHeader = csvReaderUtil.getCsvHeader(csvDtoClass);

        // CSVHelper取得
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);
        // アップロードファイルをテンプファイルとして保存
        String tmpFileName = csvUtility.getUploadCsvTmpFileName("temp");

        try {
            Files.write(Paths.get(tmpFileName), value.getBytes());

            // Apache Common CSV のリーダーの宣言
            try (Reader reader = Files.newBufferedReader(Paths.get(tmpFileName), Charset.forName(csvCharset))) {

                // Apache Common CSV のCSVフォーマッターの宣言
                CSVFormat readerCsvFormat = CSVFormat.DEFAULT.withIgnoreHeaderCase()
                                                             .withTrim()
                                                             .withQuote('"')
                                                             .withRecordSeparator("\r\n")
                                                             .withQuoteMode(QuoteMode.ALL)
                                                             .withFirstRecordAsHeader();

                // Apache Common CSV のパーサーの宣言
                CSVParser csvParser = new CSVParser(reader, readerCsvFormat);

                // アップロードされたCSVのヘッダー行の取得
                List<String> uploadedCsvHeader = csvParser.getHeaderNames();

                if (CollectionUtils.isEmpty(uploadedCsvHeader)) {
                    return false;
                }

                if (restrict) {
                    return validateRestricted(expectedCsvHeader, uploadedCsvHeader);
                } else {
                    return validateUnrestricted(expectedCsvHeader, uploadedCsvHeader);
                }
            } catch (IllegalArgumentException e) {
                return false;
            }
        } catch (IOException e) {
            // アップロード時に予想外のエラーが発生した場合
            makeContext(context, UPLOAD_FAILED_MESSAGE_ID);
            return false;
        }
    }

    /**
     * エラーメッセージを構成<br/>
     * メッセージセット＋エラーメッセージを表示する項目を指定
     */
    private void makeContext(ConstraintValidatorContext context, String messageId) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageId).addConstraintViolation();
    }

    /**
     * 部分アップロードを禁止する場合のヘッダー検証
     *
     * @param expectedCsvHeader
     * @param uploadedCsvHeader
     * @return
     */
    private boolean validateRestricted(List<String> expectedCsvHeader, List<String> uploadedCsvHeader) {
        if (expectedCsvHeader.size() != uploadedCsvHeader.size()) {
            return false;
        }
        for (String columnName : expectedCsvHeader) {
            if (!uploadedCsvHeader.contains(columnName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 部分アップロードを禁止しない場合のヘッダー検証
     *
     * @param expectedCsvHeader
     * @param uploadedCsvHeader
     * @return
     */
    private boolean validateUnrestricted(List<String> expectedCsvHeader, List<String> uploadedCsvHeader) {
        if (uploadedCsvHeader.size() > expectedCsvHeader.size()) {
            return false;
        }
        for (String columnName : uploadedCsvHeader) {
            if (!expectedCsvHeader.contains(columnName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * ファイル拡張子のバリデーション<br/>
     */
    private boolean checkFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");

        // 拡張子なしの場合
        if (lastIndexOf == -1) {
            return false;
        }

        // 不当な拡張子の場合
        String fileExtension = fileName.substring(lastIndexOf + 1);
        if (!"csv".equalsIgnoreCase(fileExtension)) {
            return false;
        }

        return true;
    }

}
