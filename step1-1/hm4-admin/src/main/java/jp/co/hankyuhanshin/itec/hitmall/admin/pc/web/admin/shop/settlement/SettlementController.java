package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayBottomGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayDownGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayUpGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.SettlementMethodEntityListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.SettlementMethodOrderDisplayUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * SettlementController Class
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/settlement")
@Controller
@SessionAttributes(value = "settlementModel")
@PreAuthorize("hasAnyAuthority('SETTING:4')")
public class SettlementController extends AbstractController {

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "AYD000101";

    /**
     * メッセージコード：表示順の保存に成功
     */
    protected static final String MSGCD_SAVE_SUCCESS = "AYC000102";

    /**
     * リスト画面から
     */
    public static final String FLASH_FROM_LIST = "fromList";

    /**
     * 決済方法設定Dxo
     */
    private final SettlementHelper settlementHelper;

    /**
     * 決済方法取得サービス
     */
    private final SettlementMethodEntityListGetService settlementMethodEntityListGetService;

    /**
     * 決済方法表示順更新サービス
     */
    private final SettlementMethodOrderDisplayUpdateService settlementMethodOrderDisplayUpdateService;

    @Autowired
    public SettlementController(SettlementHelper settlementHelper,
                                SettlementMethodEntityListGetService settlementMethodEntityListGetService,
                                SettlementMethodOrderDisplayUpdateService settlementMethodOrderDisplayUpdateService) {
        this.settlementHelper = settlementHelper;
        this.settlementMethodEntityListGetService = settlementMethodEntityListGetService;
        this.settlementMethodOrderDisplayUpdateService = settlementMethodOrderDisplayUpdateService;
    }

    /**
     * 画面ロード処理<br/>
     *
     * @return 自画面
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/index")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> md,
                                 SettlementModel settlementModel,
                                 Model model) {
        if (!model.containsAttribute(FLASH_FROM_LIST)) {
            clearModel(SettlementModel.class, settlementModel, model);
        }
        List<SettlementMethodEntity> resultList = settlementMethodEntityListGetService.execute();
        settlementHelper.toPage(settlementModel, resultList);
        settlementModel.setOrderDisplay(null);
        return "settlement/index";
    }

    /**
     * 1つ上に移動<br />
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doOrderDisplayUp")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/index")
    public String doOrderDisplayUp(@Validated(DisplayUpGroup.class) SettlementModel settlementModel,
                                   BindingResult error) {
        if (error.hasErrors()) {
            return "settlement/index";
        }

        settlementHelper.changeDisplay(settlementModel, 0);
        return "settlement/index";
    }

    /**
     * 1つ下に移動<br />
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doOrderDisplayDown")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/index")
    public String doOrderDisplayDown(@Validated(DisplayDownGroup.class) SettlementModel settlementModel,
                                     BindingResult error) {
        if (error.hasErrors()) {
            return "settlement/index";
        }

        settlementHelper.changeDisplay(settlementModel, 1);
        return "settlement/index";
    }

    /**
     * 先頭に移動<br />
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doOrderDisplayTop")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/index")
    public String doOrderDisplayTop(@Validated(DisplayTopGroup.class) SettlementModel settlementModel,
                                    BindingResult error) {
        if (error.hasErrors()) {
            return "settlement/index";
        }

        settlementHelper.changeDisplay(settlementModel, 2);
        return "settlement/index";
    }

    /**
     * 末尾に移動<br />
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doOrderDisplayBottom")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/index")
    public String doOrderDisplayBottom(@Validated(DisplayBottomGroup.class) SettlementModel settlementModel,
                                       BindingResult error) {
        if (error.hasErrors()) {
            return "settlement/index";
        }

        settlementHelper.changeDisplay(settlementModel, 3);
        return "settlement/index";
    }

    /**
     * 表示順を保存<br />
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doOnceOrderDisplayUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/index")
    public String doOnceOrderDisplayUpdate(SettlementModel settlementModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {
        // 決済方法リスト取得
        List<SettlementMethodEntity> settlementMethodList =
                        settlementHelper.getSettlementMethodEntityList(settlementModel);

        // 更新処理実行
        settlementMethodOrderDisplayUpdateService.execute(settlementMethodList);
        addInfoMessage(MSGCD_SAVE_SUCCESS, null, redirectAttributes, model);
        redirectAttributes.addFlashAttribute(FLASH_FROM_LIST, true);
        return "redirect:/settlement/";
    }

    /**
     * 新規登録画面遷移<br/>
     *
     * @return 決済方法詳細設定画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doRegist")
    public String doRegist() {
        return "redirect:/settlement/registupdate?mode=new";
    }

}
