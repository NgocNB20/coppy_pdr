/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import com.gmo_pg.g_pay.client.output.BaseOutput;
import com.gmo_pg.g_pay.client.output.ErrHolder;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CommunicateResult;
import jp.co.hankyuhanshin.itec.hmbase.application.AppLevelFacesMessage;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 通信ヘルパークラス
 *
 * @author yt13605
 * @author tomo (itec) 2011/08/29 #2717 GMO側に取引データが存在しない場合の対応
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更
 * @author Kaneko(itec) 2012/09/26 UTF-8のプロパティファイルを読み込めるように対応
 */
@Component
public class CommunicateUtility {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunicateUtility.class);

    /**
     * 標準メッセージID
     */
    public static final String MULTIPAY_DEFAULT_MSG_ID = "LMC000001";

    /**
     * 未エラーメッセージID
     */
    protected static final String MULTIPAY_NOTERROR_MSG_ID = "LMC01";

    /**
     * 180日以上経過しているか、GMOに取引情報が残っていない（直接メッセージ変換を行うので末尾の'W'は必要)
     */
    protected static final String MSGCD_GMO_TRAN_OUTDATED_WARN = "LOX000312W";

    /**
     * クレジット決済の取消時にエラーが返却された場合（直接メッセージ変換を行うので末尾の'W'は必要)
     */
    protected static final String MSGCD_GMO_CREDIT_CANCEL_ERROR = "LOX000318W";

    /**
     * 定数：エラーコード、エラー詳細コード区切り文字
     **/
    protected static final String MULTIPAY_ERR_SEPARETOR = "\\|";

    /**
     * 定数：重複防止用Prefix(messageIdMapからメッセージID取得用)
     */
    protected static final String MSG_ID_MAP_PREFIX = "mmap.";

    /**
     * 通信処理エラーチェック
     *
     * @param output 出力パラメータ
     * @return エラーメッセージリスト (null時はエラーなし)
     */
    public List<CheckMessageDto> checkOutput(BaseOutput output) {

        if (!output.isErrorOccurred()) {
            return null;
        }

        List<CheckMessageDto> res = new ArrayList<>();
        List<?> list = output.getErrList();
        for (Object obj : list) {
            if (obj instanceof ErrHolder) {
                CheckMessageDto e = ApplicationContextUtility.getBean(CheckMessageDto.class);
                String messageId = (String) PropertiesUtil.getSystemPropertiesValue(
                                MSG_ID_MAP_PREFIX + ((ErrHolder) obj).getErrInfo());
                if (messageId == null) {
                    messageId = MULTIPAY_DEFAULT_MSG_ID;
                    e.setArgs(new Object[] {((ErrHolder) obj).getErrInfo()});
                }

                e.setMessageId(messageId);
                e.setMessage(getMessage(messageId, null));
                if (messageId.startsWith(MULTIPAY_NOTERROR_MSG_ID)) {
                    e.setError(false);
                } else {
                    e.setError(true);
                }
                res.add(e);
            }
        }

        return res;
    }

    /**
     * メッセージ取得
     *
     * @param messageId コード
     * @param args      引数
     * @return メッセージ
     */
    protected String getMessage(String messageId, Object[] args) {
        return AppLevelFacesMessageUtil.getAllMessage(messageId, args).getMessage();
    }

    /**
     * 該当する取引情報がGMOサーバ上に存在しない GMOエラーコードセット
     */
    protected static final Set<String> GMO_ERROR_CODE_TRAN_NOT_FOUND;

    /**
     * 取引情報は期限の180日を経過している GMOエラーコードセット
     */
    protected static final Set<String> GMO_ERROR_CODE_TRAN_EXPIRED;

    /**
     * 仮売上後90日（AmazonPayは30日)を経過している　GMOエラーコードセット
     */
    protected static final Set<String> GMO_ERROR_CODE_AUTH_EXPIRED;

    static {

        // 「指定されたIDとパスワードの取引が存在しません。」のエラーコードセット
        Set<String> gmoErrorCodeTranNotFound = new HashSet<>();
        gmoErrorCodeTranNotFound.add("E01110002");
        GMO_ERROR_CODE_TRAN_NOT_FOUND = Collections.unmodifiableSet(gmoErrorCodeTranNotFound);

        // 「180日超えの取引のため、処理を行う事が出来ません。」のエラーコードセット
        Set<String> gmoErrorCodeTranExpired = new HashSet<>();
        gmoErrorCodeTranExpired.add("E11010010");
        gmoErrorCodeTranExpired.add("E11010011");
        gmoErrorCodeTranExpired.add("E11010012");
        gmoErrorCodeTranExpired.add("E11010013");
        gmoErrorCodeTranExpired.add("E11010014");
        GMO_ERROR_CODE_TRAN_EXPIRED = Collections.unmodifiableSet(gmoErrorCodeTranExpired);

        // 「仮売上有効期間を超えています。」のエラーコードセット
        Set<String> gmoErrorCodeAuthExpired = new HashSet<>();
        gmoErrorCodeAuthExpired.add("E01420010");
        gmoErrorCodeAuthExpired.add("M01060010");
        GMO_ERROR_CODE_AUTH_EXPIRED = Collections.unmodifiableSet(gmoErrorCodeAuthExpired);
    }

    /**
     * 取引情報が存在しない場合のエラーかを判定
     *
     * @param output GMOとの通信アウトプット
     * @return true 取引情報が存在しない
     */
    public boolean isNotFound(BaseOutput output) {
        List<?> list = output.getErrList();
        // エラー情報がない場合
        if (CollectionUtil.isEmpty(list)) {
            return false;
        }

        for (Object errObject : list) {
            ErrHolder holder = (ErrHolder) errObject;
            String errInfo = holder.getErrInfo();
            // 取引情報がGMOに存在しない場合
            if (GMO_ERROR_CODE_TRAN_NOT_FOUND.contains(errInfo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取引情報が時間経過によって無効になっているかどうかを返す
     *
     * @param output GMOとの通信アウトプット
     * @return true 無効, false 時間経過によって無効になっていない
     */
    public boolean isOutdatedTran(BaseOutput output) {

        List<?> list = output.getErrList();
        // エラー情報がない場合
        if (list == null || list.isEmpty()) {
            return false;
        }

        for (Object errObject : list) {
            ErrHolder holder = (ErrHolder) errObject;
            String errInfo = holder.getErrInfo();
            // 取引情報がGMOに存在しない場合
            if (GMO_ERROR_CODE_TRAN_NOT_FOUND.contains(errInfo)) {
                return true;
            }
            // 取引情報が180日を経過していた場合
            if (GMO_ERROR_CODE_TRAN_EXPIRED.contains(errInfo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 仮売上の有効期限が切れているかどうかを返す
     *
     * @param output GMOとの通信アウトプット
     * @return true 無効, false 有効期限切れでない
     */
    public boolean isAuthExpired(BaseOutput output) {
        List<?> list = output.getErrList();
        // エラー情報がない場合
        if (list == null || list.isEmpty()) {
            return false;
        }

        for (Object errObject : list) {
            ErrHolder holder = (ErrHolder) errObject;
            String errInfo = holder.getErrInfo();
            // 仮売上後、90日を経過していた場合(AmazonPayの場合は30日)
            if (GMO_ERROR_CODE_AUTH_EXPIRED.contains(errInfo)) {
                return true;
            }
        }

        return false;
    }

    /**
     * GMOの取引取り消しに失敗している場合、画面表示用のメッセージを FacesContext へ設定する。
     * <pre>
     * GMO取引情報が180日経過している/存在していない場合、とその他のエラーでメッセージを切り分ける
     * </pre>
     *
     * @param output GMOとの通信アウトプット
     */
    public void setGmoCancelFailureMessage(BaseOutput output) {
        if (output == null || !output.isErrorOccurred()) {
            return;
        }

        AppLevelFacesMessage message;
        if (isOutdatedTran(output)) {
            message = AppLevelFacesMessageUtil.getAllMessage(MSGCD_GMO_TRAN_OUTDATED_WARN, null);
        } else {
            // 複数のエラーが返ってくることはないかもしれないが、コードを連結して出力するようにする
            StringBuilder code = new StringBuilder();
            for (Object errObject : output.getErrList()) {
                ErrHolder holder = (ErrHolder) errObject;
                if (code.length() > 0) {
                    code.append(",");
                }
                code.append(holder.getErrCode() + ":" + holder.getErrInfo());
            }
            message = AppLevelFacesMessageUtil.getAllMessage(MSGCD_GMO_CREDIT_CANCEL_ERROR, new Object[] {code});
        }
        message.setSeverity(AppLevelFacesMessage.SEVERITY_WARN);

        // 請求情報の不具合報告処理で使用する為格納しておく
        CommunicateResult communicateResult = ApplicationContextUtility.getBean(CommunicateResult.class);
        communicateResult.setCancelError(message);
    }

    /**
     * メッセージコード(HIT-MALLのメッセージコード)内に部分文字列が含まれるかを取得<br/>
     * メッセージコード(HIT-MALLのメッセージコード)の中に部分文字列が含まれるかを返却<br/>
     *
     * @param messageCode メッセージコード
     * @param needleList  部分文字列リスト
     * @return true: 含まれる、false: 含まれない
     */
    public boolean containsCode(String messageCode, List<String> needleList) {
        if (messageCode == null || needleList == null || needleList.isEmpty()) {
            return false;
        }
        if (needleList.contains(messageCode)) {// 含まれる
            return true;
        }
        return false;// 含まれない
    }

    /**
     * エラーホルダからメッセージを作成する
     *
     * @param errHolderList エラーホルダ
     * @return メッセージ
     */
    public String holder2Message(List<ErrHolder> errHolderList) {

        String errorText = "";

        // エラーメッセージを取得する
        if (errHolderList != null && !errHolderList.isEmpty()) {
            List<String> errorMessages = getMessages(errHolderList);

            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < errHolderList.size(); i++) {
                String errCode = errHolderList.get(i).getErrCode();
                String errInfo = errHolderList.get(i).getErrInfo();
                String errMsg = errorMessages.get(i);

                builder.append(errCode + ":" + errInfo + " - " + errMsg + "\n");
            }

            errorText = builder.toString();

        }

        return errorText;
    }

    /**
     * 例外からメッセージを作成する
     *
     * @param th 例外
     * @return メッセージ
     */
    public String exception2Message(Throwable th) {

        String errorText = "";

        // 例外をメッセージにする
        if (th != null) {
            String exception = th.getClass().getName();
            String mes = th.getMessage();
            errorText += "GMO マルチペイメントと通信中に " + exception + " が発生しました。\n" + mes + "\n";
        }

        return errorText;
    }

    /**
     * GMOマルチペイメントエラーコードリスト変換処理
     * <pre>
     * GMOマルチペイメント通信で返されたパイプ区切りの
     * エラーコードとエラー詳細コードをListに変換する
     * </pre>
     *
     * @param errCodes パイプ区切りのエラーコード
     * @param errInfos パイプ区切りのエラー詳細コード
     * @return errHolderList エラーリスト
     */
    public List<ErrHolder> getErrHolderList(String errCodes, String errInfos) {

        String[] errCodeList = errCodes.split(MULTIPAY_ERR_SEPARETOR);
        String[] errInfoList = errInfos.split(MULTIPAY_ERR_SEPARETOR);

        List<ErrHolder> errHolderList = new ArrayList<>();

        for (int i = 0; i < errCodeList.length; i++) {
            ErrHolder errHolder = new ErrHolder();
            errHolder.setErrCode(errCodeList[i]);
            errHolder.setErrInfo(errInfoList[i]);
            errHolderList.add(errHolder);
        }

        return errHolderList;
    }

    /**
     * GMOマルチペイメントエラーメッセージ取得
     * <pre>
     * エラー詳細コードからメッセージを取得する
     * </pre>
     *
     * @param errInfo エラー詳細コード
     * @return エラーメッセージ
     */
    public String getMessagesSingle(String errInfo) {

        if (errInfo != null && !errInfo.isEmpty()) {
            return StringUtils.trimToEmpty(PropertiesUtil.getSystemPropertiesValue(errInfo));
        }

        return null;
    }

    /**
     * エラーメッセージに対応するメッセージリソースを取得する
     *
     * @param errHolders GMO xxxTranOutput の ErrHolder
     * @return ErrHolder に対応したメッセージリスト
     */
    public List<String> getMessages(List<ErrHolder> errHolders) {

        List<String> returnList = new ArrayList<>();
        if (errHolders == null) {
            return returnList;
        }

        for (ErrHolder holder : errHolders) {

            String errInfo = holder.getErrInfo();
            String message = "";

            if (errInfo != null && !errInfo.isEmpty()) {
                message = StringUtils.trimToEmpty(PropertiesUtil.getSystemPropertiesValue(errInfo));
            }

            returnList.add(message);
        }

        return returnList;
    }

    /**
     * エラー情報作成
     * <pre>
     * GMOレスポンスのエラーリストからエラー情報を取得して文字列化する。
     * エラーが複数ある場合は「｜」区切りで連結する。
     * // 要素0：エラーコード
     * // 要素1：エラー情報
     * </pre>
     *
     * @param output GMOレスポンス
     * @return エラー情報文字列
     */
    public String[] getError(BaseOutput output) {
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        List<String> strErrCodeList = new ArrayList<>();
        List<String> strErrInfoList = new ArrayList<>();

        @SuppressWarnings("unchecked")
        List<ErrHolder> errList = output.getErrList();
        for (ErrHolder holder : errList) {
            strErrCodeList.add(holder.getErrCode());
            strErrInfoList.add(holder.getErrInfo());
        }

        String[] retValue = {conversionUtility.toUnitStr(strErrCodeList, "|"),
                        conversionUtility.toUnitStr(strErrInfoList, "|")};
        return retValue;
    }
}
