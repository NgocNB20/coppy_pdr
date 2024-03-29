package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea;

import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;

import java.io.File;
import java.util.List;

/**
 * フリーエリア表示対象会員情報アップロードロジック<br/>
 */
public interface FreeAreaViewableMemberCsvUploadLogic {

    /** 会員が存在しない*/
    public static final String MSGCD_MEMBERNONE_FAILED = "PKG-3642-001-L-";
    /** データ件数超過*/
    public static final String MSGCD_DATALIMIT_FAILED = "PKG-3642-002-L-";

    /**
     * フリーエリアCSVアップロード<br/>
     *
     * @param uploadedFile アップロードファイル
     * @param validLimit バリデータチェック限度値
     * @param recordLimit レコード数限度値
     * @param resultList アップロードデータ
     * @return CsvUploadResult CSVアップロード結果
     */
    CsvUploadResult execute(File uploadedFile, int validLimit, int recordLimit, List<Integer> resultList);

}
