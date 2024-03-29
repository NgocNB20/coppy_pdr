package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderCSVDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.handler.csv.CsvDownloadHandler;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderAllCsvDownloadLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.csv.CsvDownloadBusinessService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderAllCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * 受注CSV出力Service実装クラス
 * <p>
 * Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Service
public class OrderAllCsvDownloadServiceImpl extends AbstractShopService implements OrderAllCsvDownloadService {

    /**
     * CSVファイル名
     */
    private String fileName = FILE_NAME;

    /**
     * 受注CSVダウンロードロジック
     */
    private final OrderAllCsvDownloadLogic orderAllCsvDownloadLogic;

    /**
     * CSVダウンロード機能
     */
    private final CsvDownloadBusinessService csvDownloadBusinessService;

    @Autowired
    public OrderAllCsvDownloadServiceImpl(OrderAllCsvDownloadLogic orderAllCsvDownloadLogic,
                                          CsvDownloadBusinessService csvDownloadBusinessService) {
        this.orderAllCsvDownloadLogic = orderAllCsvDownloadLogic;
        this.csvDownloadBusinessService = csvDownloadBusinessService;
    }

    @Override
    public void execute(Object... parameters) throws IOException {
        // 検索オプションDTOをパラメータから取得する
        OrderSearchConditionDto conditionDto = (OrderSearchConditionDto) parameters[0];
        conditionDto.setShopSeq(1001);

        HttpServletResponse response = (HttpServletResponse) parameters[1];

        // Stream方式で対象データを取得する
        Stream<OrderCSVDto> resultStream = this.orderAllCsvDownloadLogic.execute(conditionDto);

        // CSVダウンロードオプションを設定する
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        csvDownloadBusinessService.execute(
                        resultStream, OrderCSVDto.class, response, csvDownloadOptionDto, getFileName());

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
