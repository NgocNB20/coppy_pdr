package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaViewableMemberDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.FreeAreaViewableMemberCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaViewableMemberCsvDownloadLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.csv.CsvDownloadBusinessService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * フリーエリア表示対象会員CSVダウンロードロジック<br/>
 */
@Component
public class FreeAreaViewableMemberCsvDownloadLogicImpl extends AbstractShopLogic
                implements FreeAreaViewableMemberCsvDownloadLogic {

    /** フリーエリア表示対象会員Dao */
    public FreeAreaViewableMemberDao freeAreaViewableMemberDao;

    /**
     * CSVダウンロード機能
     */
    private final CsvDownloadBusinessService csvDownloadBusinessService;

    @Autowired
    public FreeAreaViewableMemberCsvDownloadLogicImpl(FreeAreaViewableMemberDao freeAreaViewableMemberDao,
                                                      CsvDownloadBusinessService csvDownloadBusinessService) {
        this.freeAreaViewableMemberDao = freeAreaViewableMemberDao;
        this.csvDownloadBusinessService = csvDownloadBusinessService;
    }

    /**
     * CSVダウンロード実行<br/>
     *
     * @param parameters
     * @throws IOException
     */
    @Override
    public void execute(Object... parameters) throws IOException {

        HttpServletResponse response = (HttpServletResponse) parameters[1];
        // Stream方式で対象データを取得する
        Stream<FreeAreaViewableMemberCsvDto> resultStream = writeData(parameters[0]);

        // CSVダウンロードオプションを設定する
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        csvDownloadBusinessService.execute(resultStream, FreeAreaViewableMemberCsvDto.class, response,
                                           csvDownloadOptionDto, getFileName()
                                          );
    }

    /**
     * CSV出力用DTO取得メソッド<br/>
     *
     * @return CSV出力用DTOクラス
     */
    protected Class<?> getCsvDtoClass() {
        return FreeAreaViewableMemberCsvDto.class;
    }

    /**
     * CSV出力ファイル名称取得メソッド<br/>
     *
     * @return CSVファイル名
     */
    protected String getFileName() {

        // CSVHelper取得
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);

        return csvUtility.getDownLoadCsvFileName("freeAreaViewableMember");

    }

    /**
     * フリーエリア表示対象会員出力<br/>
     *
     * @param args フリーエリアSEQ
     * @return 表示対象会員CsvDto
     */
    protected Stream<FreeAreaViewableMemberCsvDto> writeData(Object... args) {
        Integer freeAreaSeq = (Integer) args[0];
        return freeAreaViewableMemberDao.exportCsvByFreeAreaSeq(freeAreaSeq);
    }
}
