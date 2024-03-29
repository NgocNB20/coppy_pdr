package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * エラー画面 Controller
 *
 * @author Thang Doan (VJP)
 */
@Controller
public class ErrorController {

    /**
     * コンストラクタ
     *
     */
    public ErrorController() {
    }

    /**
     * エラー画面
     *
     * @return エラー画面 string
     */
    @GetMapping(value = {"/error", "/error.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "error")
    public String doLoadError() {
        return "error";
    }
}
