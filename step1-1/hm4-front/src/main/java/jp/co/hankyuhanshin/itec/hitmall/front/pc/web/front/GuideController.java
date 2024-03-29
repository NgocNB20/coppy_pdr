package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ご利用ガイド Controller
 *
 * @author
 * @version $Revision: 1.0 $
 */
@RequestMapping("/guide")
@Controller
public class GuideController extends AbstractController {

    /**
     * ご利用ガイド 画面 : 初期処理
     *
     * @param model
     * @return ご利用ガイド 画面
     */
    @GetMapping(value = {"/", "/index.html"})
    protected String doLoadIndex(Model model) {

        return "guide/index";
    }

    /**
     * ご利用ガイド 画面
     *
     * @param model
     * @return ご利用規約 画面
     */
    @GetMapping(value = {"/agreement", "/agreement.html"})
    protected String doLoadTermOfUse(Model model) {

        return "guide/agreement";
    }

    /**
     * 商品について 画面
     *
     * @param model
     * @return 商品について 画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/commodity", "/commodity.html"})
    // PDR Migrate Customization to here
    protected String doLoadProduct(Model model) {

        return "guide/commodity";
    }

    /**
     * 特定商取引法に基づく表記 画面
     *
     * @param model
     * @return 特定商取引法に基づく表記 画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/law", "/law.html"})
    // PDR Migrate Customization to here
    protected String doLoadLaw(Model model) {

        return "guide/law";
    }

    /**
     * ご注文について 画面
     *
     * @param model
     * @return
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/order", "/order.html"})
    // PDR Migrate Customization to here
    protected String doLoadOrder(Model model) {

        return "guide/order";
    }

    /**
     * 会員について 画面
     *
     * @param model
     * @return 会員について 画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/member", "/member.html"})
    // PDR Migrate Customization to here
    protected String doLoadMembers(Model model) {

        return "guide/member";
    }

    /**
     * お支払い・配送について 画面
     *
     * @param model
     * @return お支払い・配送について 画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/payment", "/payment.html"})
    // PDR Migrate Customization to here
    protected String doLoadPaymentDelivery(Model model) {

        return "guide/payment";
    }

    /**
     * プライバシーポリシー 画面
     *
     * @param model
     * @return プライバシーポリシー 画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/privacy", "/privacy.html"})
    // PDR Migrate Customization to here
    protected String doLoadPrivacyPolicy(Model model) {

        return "guide/privacy";
    }

    /**
     * HIT-MALL Ver3 DEMO SHOPについて 画面
     *
     * @param model
     * @return HIT-MALL Ver3 DEMO SHOPについて 画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/shop", "/shop.html"})
    // PDR Migrate Customization to here
    protected String doLoadAboutShop(Model model) {

        return "guide/shop";
    }

    // PDR Migrate Customization from here

    /**
     * 予約の注文方法画面
     *
     * @param model
     * @return 予約の注文方法画面
     */
    @GetMapping(value = {"/reserve", "/reserve.html"})
    protected String doLoadReserve(Model model) {
        return "guide/reserve";
    }

    /**
     * よくある質問Q&A画面
     *
     * @param model
     * @return よくある質問Q&A画面
     */
    @GetMapping(value = {"/faq", "/faq.html"})
    protected String doLoadFaq(Model model) {

        return "guide/faq";
    }
    // PDR Migrate Customization to here
}
