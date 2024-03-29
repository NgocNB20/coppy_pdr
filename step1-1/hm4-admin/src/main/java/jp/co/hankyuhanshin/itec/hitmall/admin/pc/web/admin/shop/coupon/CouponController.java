/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.coupon.CouponSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponSearchResultListgetLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * クーポン検索アクションクラス。<br />
 *
 * <pre>
 * 検索条件を元に検索結果を一覧表示する。
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/coupon")
@Controller
@SessionAttributes(value = "couponModel")
@PreAuthorize("hasAnyAuthority('SHOP:4')")
public class CouponController extends AbstractController {

    /**
     * デフォルトページ番号
     */
    private static final String DEFAULT_PNUM = "1";

    /**
     * デフォルト：ソート項目
     */
    private static final String DEFAULT_ORDER_FIELD = "couponStartTime";

    /**
     * デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_ORDER_ASC = false;

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * クーポン検索用DXO
     */
    private final CouponHelper couponHelper;

    /**
     * クーポン情報取得ロジック
     */
    private final CouponSearchResultListgetLogic couponSearchResultListgetLogic;

    @Autowired
    public CouponController(CouponHelper couponHelper, CouponSearchResultListgetLogic couponSearchResultListgetLogic) {

        this.couponHelper = couponHelper;
        this.couponSearchResultListgetLogic = couponSearchResultListgetLogic;
    }

    /**
     * 初期表示
     *
     * <pre>
     * 登録更新画面からの再遷移時に検索条件を保持する。
     * </pre>
     *
     * @param couponModel
     * @param model
     * @return 初期画面
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "coupon/index")
    protected String doLoadIndex(CouponModel couponModel, Model model) {

        // 再検索の場合
        if (model.containsAttribute(FLASH_MD)) {
            // 再検索を行う
            search(couponModel, model);
        } else {
            clearModel(CouponModel.class, couponModel, model);
        }
        return "coupon/index";

    }

    /**
     * クーポンを検索する。<br />
     *
     * <pre>
     * 画面に入力された検索条件を元に検索結果を表示する。
     * </pre>
     *
     * @param couponModel
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "coupon/index")
    protected String doSearch(@Validated CouponModel couponModel,
                              BindingResult error,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (error.hasErrors()) {
            return "coupon/index";
        }

        // ページング関連項目初期化（limitは画面プルダウンで指定されてくる）
        couponModel.setPageNumber(DEFAULT_PNUM);
        couponModel.setOrderField(DEFAULT_ORDER_FIELD);
        couponModel.setOrderAsc(DEFAULT_ORDER_ASC);

        // 検索処理を行う
        search(couponModel, model);

        return "coupon/index";

    }

    /**
     * 表示切替時の再検索。<br />
     *
     * <pre>
     * 表示順・ページャなどで表示切替を行ったタイミングで再検索を行う。
     * </pre>
     *
     * @param couponModel
     * @param error
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "coupon/index")
    protected String doDisplayChange(@Validated CouponModel couponModel, BindingResult error, Model model) {

        if (error.hasErrors()) {
            return "coupon/index";
        }

        // 検索処理を行う
        search(couponModel, model);

        return "coupon/index";
    }

    /**
     * クーポン検索処理。<br />
     *
     * <pre>
     * 入力された検索条件を元に検索結果を返す。
     * </pre>
     *
     * @param couponModel
     * @param model
     */
    private void search(CouponModel couponModel, Model model) {

        // 検索条件を画面から取得
        CouponSearchForDaoConditionDto conditionDto = couponHelper.toCouponConditionDtoForSearch(couponModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, couponModel.getPageNumber(), couponModel.getLimit(),
                                     couponModel.getOrderField(), couponModel.isOrderAsc()
                                    );

        // 検索条件を元にクーポン情報Listを取得する
        List<CouponEntity> couponList = couponSearchResultListgetLogic.execute(conditionDto);

        // 検索結果をpageにセットする
        couponHelper.toPageForSearch(couponList, couponModel, conditionDto);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(conditionDto, couponModel);

        // 検索件数をセットする
        couponModel.setTotalCount(conditionDto.getTotalCount());
    }

}
