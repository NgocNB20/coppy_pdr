package jp.co.hankyuhanshin.itec.hitmall.service.shop.questionnaire.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyCsvSearchDto;
import jp.co.hankyuhanshin.itec.hitmall.handler.csv.CsvDownloadHandler;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireAllCsvDownloadLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.csv.CsvDownloadBusinessService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.questionnaire.QuestionnaireAllCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * アンケート回答取得ロジックの実装クラス。<br />
 *
 * @author matsuda
 */
@Component
public class QuestionnaireAllCsvDownloadServiceImpl extends AbstractShopService
                implements QuestionnaireAllCsvDownloadService {

    /**
     * アンケート回答情報取得ロジックのインタフェースクラス
     */
    private final QuestionnaireAllCsvDownloadLogic questionnaireAllCsvDownloadLogic;

    /**
     * CSVダウンロード機能
     */
    private final CsvDownloadBusinessService csvDownloadBusinessService;

    /**
     * 出力CSVファイル名
     */
    private String fileName = FILE_NAME;

    /**
     * Instantiates
     *
     * @param questionnaireAllCsvDownloadLogic アンケート回答情報取得ロジックのインタフェースクラス
     * @param csvDownloadBusinessService
     */
    public QuestionnaireAllCsvDownloadServiceImpl(QuestionnaireAllCsvDownloadLogic questionnaireAllCsvDownloadLogic,
                                                  CsvDownloadBusinessService csvDownloadBusinessService) {
        this.questionnaireAllCsvDownloadLogic = questionnaireAllCsvDownloadLogic;
        this.csvDownloadBusinessService = csvDownloadBusinessService;
    }

    @Override
    public void execute(Object... parameters) throws IOException {
        QuestionnaireReplyCsvSearchDto questionnaireReplyCsvSearchDto = (QuestionnaireReplyCsvSearchDto) parameters[0];

        HttpServletResponse response = (HttpServletResponse) parameters[1];

        Stream<QuestionnaireReplyCsvDto> questionnaireReplyCsvDtoStream =
                        this.questionnaireAllCsvDownloadLogic.execute(questionnaireReplyCsvSearchDto);

        // CSVダウンロードオプションを設定する
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        csvDownloadBusinessService.execute(questionnaireReplyCsvDtoStream, QuestionnaireReplyCsvDto.class, response,
                                           csvDownloadOptionDto, getFileName()
                                          );

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
