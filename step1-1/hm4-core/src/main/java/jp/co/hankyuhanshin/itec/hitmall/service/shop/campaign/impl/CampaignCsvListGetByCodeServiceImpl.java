package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.handler.csv.CsvDownloadHandler;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignCsvListGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.csv.CsvDownloadBusinessService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignCsvListGetByCodeService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * 広告CSV出力<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Service
public class CampaignCsvListGetByCodeServiceImpl extends AbstractShopService
                implements CampaignCsvListGetByCodeService {

    /**
     * CSVファイル名
     */
    private String fileName = FILE_NAME;

    /** キャンペーンCSV用リスト取得ロジック */
    private final CampaignCsvListGetByCodeLogic campaignCsvListGetByCodeLogic;

    /**
     * CSVダウンロード機能
     */
    private final CsvDownloadBusinessService csvDownloadBusinessService;

    @Autowired
    public CampaignCsvListGetByCodeServiceImpl(CampaignCsvListGetByCodeLogic campaignCsvListGetByCodeLogic,
                                               CsvDownloadBusinessService csvDownloadBusinessService) {
        this.campaignCsvListGetByCodeLogic = campaignCsvListGetByCodeLogic;
        this.csvDownloadBusinessService = csvDownloadBusinessService;
    }

    @Override
    public void execute(Object... parameters) throws IOException {
        // 検索オプションDTOをパラメータから取得する
        CampaignSearchForDaoConditionDto conditionDto = (CampaignSearchForDaoConditionDto) parameters[0];
        conditionDto.setShopSeq(1001);

        HttpServletResponse response = (HttpServletResponse) parameters[1];

        // Stream方式で対象データを取得する
        Stream<CampaignCsvDto> resultStream = this.campaignCsvListGetByCodeLogic.execute(conditionDto);

        // CSVダウンロードオプションを設定する
        // 特に設定しない場合は、NULLを引数として渡す ⇒ デフォルトのオプションが適用される
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        csvDownloadBusinessService.execute(
                        resultStream, CampaignCsvDto.class, response, csvDownloadOptionDto, getFileName());
    }

    private String getFileName() {
        if (StringUtils.isEmpty(this.fileName)) {
            this.fileName = CsvDownloadHandler.DEFAULT_FILE_NAME;
        }
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);
        return csvUtility.getDownLoadCsvFileName(this.fileName);
    }

}
