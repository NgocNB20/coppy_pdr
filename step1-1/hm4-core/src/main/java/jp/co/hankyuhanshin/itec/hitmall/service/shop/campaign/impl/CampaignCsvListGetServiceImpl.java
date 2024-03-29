/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.access.AccessInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.CampaignCsvEffectDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.handler.csv.CsvDownloadHandler;
import jp.co.hankyuhanshin.itec.hitmall.logic.access.AccessCampaignCsvEffectListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.csv.CsvDownloadBusinessService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignCsvListGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * 広告効果CSV出力<br/>
 *
 * @author kimura
 * @version $Revision: 1.2 $
 *
 */
@Component
public class CampaignCsvListGetServiceImpl extends AbstractShopService implements CampaignCsvListGetService {

    /**
     * CSVファイル名
     */
    private String fileName = FILE_NAME;

    /** 広告効果CSVリスト取得ロジック */
    private final AccessCampaignCsvEffectListGetLogic accessCampaignCsvEffectListGetLogic;

    /**
     * CSVダウンロード機能
     */
    private final CsvDownloadBusinessService csvDownloadBusinessService;

    @Autowired
    public CampaignCsvListGetServiceImpl(AccessCampaignCsvEffectListGetLogic accessCampaignCsvEffectListGetLogic,
                                         CsvDownloadBusinessService csvDownloadBusinessService) {
        this.accessCampaignCsvEffectListGetLogic = accessCampaignCsvEffectListGetLogic;
        this.csvDownloadBusinessService = csvDownloadBusinessService;
    }

    /**
     * 広告効果CSVリスト取得<br/>
     *
     * @param parameters 広告効果検索条件DTO
     * @return int 件数
     */
    @Override
    public void execute(Object... parameters) throws IOException {

        AccessInfoSearchForDaoConditionDto accessInfoSearchForDaoConditionDto =
                        (AccessInfoSearchForDaoConditionDto) parameters[0];
        accessInfoSearchForDaoConditionDto.setShopSeq(1001);

        HttpServletResponse response = (HttpServletResponse) parameters[1];

        Stream<CampaignCsvEffectDto> resultStream =
                        accessCampaignCsvEffectListGetLogic.execute(accessInfoSearchForDaoConditionDto);

        // CSVダウンロードオプションを設定する
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        csvDownloadBusinessService.execute(
                        resultStream, CampaignCsvEffectDto.class, response, csvDownloadOptionDto, getFileName());
    }

    private String getFileName() {
        if (StringUtils.isEmpty(this.fileName)) {
            this.fileName = CsvDownloadHandler.DEFAULT_FILE_NAME;
        }
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);
        return csvUtility.getDownLoadCsvFileName(this.fileName);
    }

}
