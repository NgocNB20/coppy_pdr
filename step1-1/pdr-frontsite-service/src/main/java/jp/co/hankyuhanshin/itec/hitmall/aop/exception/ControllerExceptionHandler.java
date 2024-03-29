package jp.co.hankyuhanshin.itec.hitmall.aop.exception;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.ErrorContent;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.ErrorResponse;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationLogUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * コントローラーの例外ハンドラー
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * ボトムアップ実装のAPI例外ハンドリング
     * 例外タイプ：AppLevelListException
     *
     * @param e エラー
     * @return レスポンスコード：400 / エラーリスト（マップ形式）
     */
    @ExceptionHandler(AppLevelListException.class)
    public ResponseEntity<ErrorResponse> handleAppLevelListException(AppLevelListException e) {

        // ログを出力
        for (AppLevelException appLevelException : e.getErrorList()) {
            if (appLevelException.getAppLevelFacesMessage() != null) {
                LOGGER.error(appLevelException.getAppLevelFacesMessage().getMessage());
            } else if (appLevelException.getCause() != null) {
                LOGGER.error(appLevelException.getCause().getMessage());
            }
        }

        // -------------------
        // アプリケーションログを出力
        // -------------------
        ApplicationLogUtility applicationLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
        applicationLogUtility.writeLogicErrorLog(e);

        // AppLevelException内のerrorListからList<ErrorContent>に変換
        List<AppLevelException> errorList = e.getErrorList();
        List<ErrorContent> errorResponseList = new ArrayList<>();
        for (AppLevelException exception : errorList) {
            ErrorContent errorContent = new ErrorContent();
            errorContent.setMessage(exception.getMessage());
            errorContent.setCode(exception.getMessageCode());
            errorResponseList.add(errorContent);
        }

        // エラーリストをクリアする
        if (e.getErrorList() != null) {
            e.getErrorList().clear();
        }

        // エラーマップを生成
        Map<String, List<ErrorContent>> errorMap = new HashMap<>();
        errorMap.put("common", errorResponseList);

        // エラーレスポンスを設定
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessages(errorMap);

        // エラーレスポンスを返却
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 予期せぬエラー
     *
     * @param e Throwable
     * @return レスポンスコード：500
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleThrowable(Throwable e) {

        // ログを出力
        LOGGER.error(e.getMessage());

        // -------------------
        // アプリケーションログを出力
        // -------------------
        ApplicationLogUtility applicationLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
        applicationLogUtility.writeExceptionLog(e);

        // エラーレスポンスを返却
        return new ResponseEntity<>("UNEXPECTED_EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

