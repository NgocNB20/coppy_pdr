package jp.co.hankyuhanshin.itec.hitmall.web;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * セッションパラメーターユーティル
 *
 * @author Doan Thang (VJP)
 */
public class SessionParamsUtil {

    /**
     * 会員Seqの取得
     *
     * @return 会員Seq
     */
    public String getMemberInfoSeq() {
        HttpServletRequest request =
                        ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                                        .getRequest();
        return request.getSession().getAttribute("memberInfoSeq").toString();
    }

    /**
     * セッションIDの取得
     *
     * @return セッションID
     */
    public String getSessionId() {
        HttpServletRequest request =
                        ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                                        .getRequest();
        return request.getSession().getAttribute("sessionId").toString();
    }

    /**
     * 端末識別番号の取得
     *
     * @return 端末識別番号
     */
    public String getAccessUid() {
        HttpServletRequest request =
                        ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                                        .getRequest();
        return request.getSession().getAttribute("accessUid").toString();
    }

    /**
     * セッションに情報を設定（ ペイジェント通信時のインタセプタ用）
     *
     * @param memberInfoSeq 会員Seq
     * @param sessionId     セッションID
     * @param accessUid     端末識別番号
     */
    public void setToSessionParams(Integer memberInfoSeq, String sessionId, String accessUid) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpSession session = ((ServletRequestAttributes) requestAttributes).getRequest().getSession();
            session.setAttribute("memberInfoSeq", memberInfoSeq);
            session.setAttribute("sessionId", sessionId);
            session.setAttribute("accessUid", accessUid);
        }
    }

    /**
     * セッションに情報を削除
     */
    public void removeSessionParams() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpSession session = ((ServletRequestAttributes) requestAttributes).getRequest().getSession();
            session.removeAttribute("memberInfoSeq");
            session.removeAttribute("sessionId");
            session.removeAttribute("accessUid");
        }
    }
}

