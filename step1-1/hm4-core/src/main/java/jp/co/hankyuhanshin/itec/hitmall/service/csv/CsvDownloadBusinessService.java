package jp.co.hankyuhanshin.itec.hitmall.service.csv;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * 新HIT-MALLのCSVダウンロード機能
 * 各種業務機能の最上位インターフェース
 * 作成日：2021/04/13
 * 更新日：2023/09/22
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 * @author Thang Doan (VTI Japan Co., Ltd.)
 */
public interface CsvDownloadBusinessService {

    void execute(Stream<?> csvDtoStream,
                 Class<?> csvClass,
                 HttpServletResponse response,
                 CsvDownloadOptionDto csvDownloadOptionDto,
                 String fileName) throws IOException;
}
