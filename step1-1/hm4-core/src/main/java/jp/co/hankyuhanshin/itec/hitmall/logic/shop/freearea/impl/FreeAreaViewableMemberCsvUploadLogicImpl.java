package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvReaderOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.FreeAreaViewableMemberCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaViewableMemberCsvUploadLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvReaderUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadError;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * フリーエリアCSVアップロードロジック<br/>
 */
@Component
public class FreeAreaViewableMemberCsvUploadLogicImpl extends AbstractShopLogic
                implements FreeAreaViewableMemberCsvUploadLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FreeAreaViewableMemberCsvUploadLogicImpl.class);

    /**
     * 会員情報取得ロジック<br/>
     */
    public final MemberInfoGetLogic memberInfoGetLogic;

    /**
     * CSV読み込みのユーティリティ<br/>
     */
    private final CsvReaderUtil csvReaderUtil;

    public FreeAreaViewableMemberCsvUploadLogicImpl(MemberInfoGetLogic memberInfoGetLogic) {
        this.memberInfoGetLogic = memberInfoGetLogic;
        this.csvReaderUtil = new CsvReaderUtil();
    }

    public CsvUploadResult execute(File uploadedFile, int validLimit, int recordLimit, List<Integer> dataList) {

        // CSV読み込みオプションを設定する
        CsvReaderOptionDto csvReaderOptionDto = new CsvReaderOptionDto();
        csvReaderOptionDto.setValidLimit(validLimit);

        // Csvアップロード結果Dto作成
        CsvUploadResult csvUploadResult = new CsvUploadResult();

        // CSVファイルを読み込み
        List<FreeAreaViewableMemberCsvDto> MemberCsvDtoList;
        try {
            MemberCsvDtoList = (List<FreeAreaViewableMemberCsvDto>) csvReaderUtil.readCsv(uploadedFile,
                                                                                          FreeAreaViewableMemberCsvDto.class,
                                                                                          csvUploadResult,
                                                                                          csvReaderOptionDto
                                                                                         );
        } catch (Exception e) {
            // CSV読み込みで有り得ない例外が発生した場合
            LOGGER.error("例外処理が発生しました", e);
            csvReaderUtil.createUnexpectedExceptionMsg(csvUploadResult);
            return csvUploadResult;
        }

        // CSV読み込みチェック
        if (csvUploadResult.getErrorCount() > 0) {
            return csvUploadResult;
        }

        // 入力チェック
        if (validate(MemberCsvDtoList, validLimit, recordLimit, csvUploadResult)) {
            return csvUploadResult;
        }

        // 登録処理チェック
        if (readProcess(MemberCsvDtoList, validLimit, dataList, csvUploadResult)) {
            return csvUploadResult;
        }

        // 正常終了時は、処理件数を返す
        return csvUploadResult;
    }

    /**
     * Csvバリデータチェック<br/>
     *
     * @param memberCsvDtoList Csvアップロードヘルパー
     * @param validLimit       バリデータ限界値
     * @param recordLimit      レコード数限度値
     * @param csvUploadResult  Csvアップロード結果
     * @return true:エラー有り、false:エラー無し
     */
    protected boolean validate(List<FreeAreaViewableMemberCsvDto> memberCsvDtoList,
                               int validLimit,
                               int recordLimit,
                               CsvUploadResult csvUploadResult) {

        // データ件数チェック
        // csvレコード件数（ヘッダー行を除いた）が上限値を超えた場合エラー
        if (memberCsvDtoList.size() - 1 > recordLimit) {
            throwMessage(MSGCD_DATALIMIT_FAILED, new Object[] {recordLimit}, null);
        }

        // バリデータエラーカウンタ
        int errorCounter = 0;

        // CSVバリデータ結果
        CsvValidationResult csvValidationResult = new CsvValidationResult();

        // CSV のレコードを DTO に変換する
        for (FreeAreaViewableMemberCsvDto memberCsvDto : memberCsvDtoList) {

            // バリデータエラー行数限界値の場合 終了
            if (errorCounter >= validLimit) {
                csvUploadResult.setValidLimitFlg(true); // more表示フラグON
                break;
            }

        }

        // エラーあり
        if (errorCounter != 0) {
            csvUploadResult.setCsvValidationResult(csvValidationResult);
            csvUploadResult.setRecordCount(1); // ヘッダー行の1をセット
            csvUploadResult.setErrorCount(errorCounter);
            return true;
        }

        // エラーなし
        return false;
    }

    /**
     * フリーエリア表示対象会員CSV読込処理<br/>
     *
     * @param memberCsvDtoList CSVアップロードヘルパー
     * @param validLimit       バリデータ限界値
     * @param dataList         アップロードデータ
     * @param csvUploadResult  CSVアップロード結果
     * @return true:処理失敗 false:処理成功
     */
    protected boolean readProcess(List<FreeAreaViewableMemberCsvDto> memberCsvDtoList,
                                  int validLimit,
                                  List<Integer> dataList,
                                  CsvUploadResult csvUploadResult) {

        List<CsvUploadError> csvUploadErrorList = new ArrayList<CsvUploadError>();

        List<String> errCustomerNos = new ArrayList<String>();

        for (FreeAreaViewableMemberCsvDto memberCsvDto : memberCsvDtoList) {

            // データ部0件の対応
            if (memberCsvDto == null) {
                continue;
            }
            // バリデータエラー行数限界値の場合 終了
            if (errCustomerNos.size() >= validLimit) {
                break;
            }
            // 会員の存在有無を確認
            // 会員が存在しない場合「,」区切りで顧客番号を保持
            Integer memberSeq = checkCustomerNoStatus(memberCsvDto.customerNo);
            if (memberSeq == null) {
                errCustomerNos.add(Integer.toString(memberCsvDto.customerNo));
                continue;
            }

            // 表示対象会員追加
            dataList.add(memberSeq);
        }

        boolean hasError = false;
        // エラーがあった場合
        if (CollectionUtil.isNotEmpty(errCustomerNos)) {
            for (String errNo : errCustomerNos) {
                csvUploadErrorList.add(createCsvUploadError(MSGCD_MEMBERNONE_FAILED, new String[] {errNo}));
            }
            // エラーリストを返す
            csvUploadResult.setCsvUploadErrorlList(csvUploadErrorList);
            hasError = true;
        }

        return hasError;
    }

    /**
     * 会員存在チェック<br/>
     *
     * @param customerNo 顧客番号
     * @return 会員SEQ
     */
    protected Integer checkCustomerNoStatus(Integer customerNo) {

        // 会員情報取得
        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.getMemberInfoEntityByCustomerNo(customerNo);
        if (memberInfoEntity == null) {
            return null;
        }
        // エラーなし
        return memberInfoEntity.getMemberInfoSeq();
    }

    /**
     * CSVアップロードエラーDto<br/>
     *
     * @param messageCode メッセージコード
     * @param args        引数
     * @return CSVアップロードエラー
     */
    protected CsvUploadError createCsvUploadError(String messageCode, Object[] args) {
        CsvUploadError csvUploadError = new CsvUploadError();
        csvUploadError.setMessageCode(messageCode);
        csvUploadError.setArgs(args);
        csvUploadError.setMessage(AppLevelFacesMessageUtil.getAllMessage(messageCode, args).getMessage());
        return csvUploadError;
    }
}
