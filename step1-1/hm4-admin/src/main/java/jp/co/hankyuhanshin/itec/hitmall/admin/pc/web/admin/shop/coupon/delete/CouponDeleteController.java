package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.coupon.delete;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponTimeCheckLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * クーポン削除コントロールクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@SessionAttributes(value = "couponDeleteModel")
@RequestMapping("/coupon")
@Controller
@PreAuthorize("hasAnyAuthority('SHOP:8')")
public class CouponDeleteController extends AbstractController {

    // ACP0004xx

    /**
     * 検索画面からの遷移時に情報が取得できなかった場合エラー
     */
    public static final String MSGCD_NOT_GET_COUPONDATA = "ACP000401";

    /**
     * 開催中・終了のクーポンを削除した場合エラー
     */
    public static final String MSGCD_DONOT_DELETE = "ACP000402";

    /**
     * 既に削除されているクーポンを削除した場合エラー
     */
    public static final String MSGCD_PREVIOUSLY_DELETE = "ACP000403";

    /**
     * 削除完了メッセージ
     */
    public static final String MSGCD_DELETE_COMPLETE = "ACP000404";

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * クーポン削除Helper
     */
    private CouponDeleteHelper couponDeleteHelper;

    /**
     * クーポン情報取得ロジック
     */
    private CouponGetLogic couponGetLogic;

    /**
     * クーポン削除ロジック
     */
    private CouponDeleteLogic couponDeleteLogic;

    /**
     * クーポン開催日時チェックロジック
     */
    private CouponTimeCheckLogic couponTimeCheckLogic;

    /**
     * コンストラクタ
     *
     * @param couponDeleteHelper
     * @param couponGetLogic
     * @param couponDeleteLogic
     * @param couponTimeCheckLogic
     */
    @Autowired
    public CouponDeleteController(CouponDeleteHelper couponDeleteHelper,
                                  CouponGetLogic couponGetLogic,
                                  CouponDeleteLogic couponDeleteLogic,
                                  CouponTimeCheckLogic couponTimeCheckLogic) {
        this.couponDeleteHelper = couponDeleteHelper;
        this.couponGetLogic = couponGetLogic;
        this.couponDeleteLogic = couponDeleteLogic;
        this.couponTimeCheckLogic = couponTimeCheckLogic;
    }

    /**
     * 画面初期表示処理
     *
     * @param couponDeleteModel
     * @param model
     * @return 削除画面
     */
    @GetMapping(value = "/delete/delete")
    @HEHandler(exception = AppLevelListException.class, returnView = "coupon/delete/delete")
    public String doLoadIndex(@RequestParam(required = false) Optional<Integer> seq,
                              CouponDeleteModel couponDeleteModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        couponDeleteModel.setCouponSeq(seq.get());
        CouponEntity coupon = couponGetLogic.execute(couponDeleteModel.getCouponSeq());

        // 他のユーザによる削除対応
        // 指定されたクーポンSEQに対応するクーポンが存在しない場合、検索画面に遷移しエラーメッセージを表示する
        if (coupon == null) {
            addMessage(MSGCD_NOT_GET_COUPONDATA, redirectAttributes, model);
            return "redirect:/coupon/";
        }

        // クーポン情報をページにセットする
        couponDeleteHelper.toPageForLoad(coupon, couponDeleteModel);

        // 削除可能のとき「削除」ボタンを表示
        if (canDelete(couponDeleteModel)) {
            couponDeleteModel.setDeleteFlag(true);
        } else {
            couponDeleteModel.setDeleteFlag(false);
        }

        return "coupon/delete/delete";

    }

    /**
     * クーポン削除処理を行う。<br />
     *
     * <pre>
     * クーポン削除チェック後、クーポンを物理削除する。
     * </pre>
     *
     * @return 検索画面
     */
    @PostMapping(value = "/delete", params = "doOnceDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "coupon/delete/delete")
    public String doOnceRegist(CouponDeleteModel couponDeleteModel,
                               RedirectAttributes redirectAttrs,
                               SessionStatus sessionStatus,
                               Model model) {

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        // クーポン削除可能チェック
        // クーポンが未開催でないものはエラーメッセージをセットする
        if (!canDelete(couponDeleteModel)) {
            throwMessage(MSGCD_DONOT_DELETE);
        }

        // クーポン削除
        if (couponDeleteLogic.execute(couponDeleteModel.getDeleteCoupon()) == 0) {
            // クーポンの削除件数が0件の場合は既に削除済みとする
            addMessage(
                            MSGCD_PREVIOUSLY_DELETE,
                            new Object[] {couponDeleteModel.getDeleteCoupon().getCouponName().trim()}, redirectAttrs,
                            model
                      );
            return "redirect:/coupon/";
        }

        // 登録完了後メッセージを表示する
        addInfoMessage(MSGCD_DELETE_COMPLETE, new String[] {"クーポン"}, redirectAttrs, model);

        // 検索画面に遷移する
        redirectAttrs.addFlashAttribute(FLASH_MD, MODE_LIST);
        return "redirect:/coupon/";

    }

    /**
     * キャンセルボタン押下時に検索画面へ遷移する。<br />
     *
     * <pre>
     * 検索条件を元に再検索を行う。
     * </pre>
     *
     * @return 検索画面
     */
    @PostMapping(value = "/delete", params = "doCancel")
    public String doCancel(CouponDeleteModel couponDeleteModel, RedirectAttributes redirectAttributes, Model model) {
        redirectAttributes.addFlashAttribute(FLASH_MD, MODE_LIST);
        return "redirect:/coupon/";
    }

    /**
     * クーポン削除可否チェックを行う。<br />
     *
     * <pre>
     * クーポン開始日時が現在＋リードタイムより未来であるかをチェックする。
     * </pre>
     *
     * @param couponDeleteModel クーポン登録削除確認モデル
     * @return クーポンが削除可能の場合trueを返す
     */
    private boolean canDelete(CouponDeleteModel couponDeleteModel) {

        Timestamp couponStartTime = couponDeleteModel.getDeleteCoupon().getCouponStartTime();
        return couponTimeCheckLogic.execute(couponStartTime) > 0;

    }

}
