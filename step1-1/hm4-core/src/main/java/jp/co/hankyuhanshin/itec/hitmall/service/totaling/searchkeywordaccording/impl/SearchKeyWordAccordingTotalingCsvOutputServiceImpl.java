package jp.co.hankyuhanshin.itec.hitmall.service.totaling.searchkeywordaccording.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.handler.csv.CsvDownloadHandler;
import jp.co.hankyuhanshin.itec.hitmall.logic.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingListCsvOutputLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.csv.CsvDownloadBusinessService;
import jp.co.hankyuhanshin.itec.hitmall.service.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingCsvOutputService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * 検索キーワード集計CSV出力サービス実装クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Service
public class SearchKeyWordAccordingTotalingCsvOutputServiceImpl extends AbstractShopService
                implements SearchKeyWordAccordingTotalingCsvOutputService {

    /**
     * CSVファイル名
     */
    private String fileName = FILE_NAME;

    /**
     * CSVダウンロード機能
     */
    private final CsvDownloadBusinessService csvDownloadBusinessService;

    /**
     * 検索キーワード集計CSV出力Logic
     */
    private final SearchKeyWordAccordingTotalingListCsvOutputLogic searchKeyWordAccordingTotalingListCsvOutputLogic;

    @Autowired
    public SearchKeyWordAccordingTotalingCsvOutputServiceImpl(CsvDownloadBusinessService csvDownloadBusinessService,
                                                              SearchKeyWordAccordingTotalingListCsvOutputLogic searchKeyWordAccordingTotalingListCsvOutputLogic) {
        this.csvDownloadBusinessService = csvDownloadBusinessService;
        this.searchKeyWordAccordingTotalingListCsvOutputLogic = searchKeyWordAccordingTotalingListCsvOutputLogic;
    }

    /**
     * 検索キーワード集計CSV出力処理を実行します
     */
    @Override
    public void execute(Object... parameters) throws IOException {

        AccessSearchKeywordTotalSearchForConditionDto accessSearchKeywordTotalSearchForConditionDto =
                        (AccessSearchKeywordTotalSearchForConditionDto) parameters[0];

        HttpServletResponse response = (HttpServletResponse) parameters[1];

        Stream<AccessSearchKeywordTotalDto> resultStream =
                        this.searchKeyWordAccordingTotalingListCsvOutputLogic.execute(
                                        accessSearchKeywordTotalSearchForConditionDto);

        // CSVダウンロードオプションを設定する
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        csvDownloadBusinessService.execute(
                        resultStream, AccessSearchKeywordTotalDto.class, response, csvDownloadOptionDto, getFileName());
    }

    private String getFileName() {
        if (StringUtils.isEmpty(this.fileName)) {
            this.fileName = CsvDownloadHandler.DEFAULT_FILE_NAME;
        }
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);
        return csvUtility.getDownLoadCsvFileName(this.fileName);
    }
}
