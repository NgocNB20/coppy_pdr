/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.auth;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.administrator.MetaAuthType;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminAuthGroupLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 権限グループ一覧画面 Action クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/administrator/auth")
@Controller
@SessionAttributes(value = "administratorAuthModel")
@PreAuthorize("hasAnyAuthority('ADMIN:4')")
public class AdministratorAuthController extends AbstractController {

    /**
     * 権限グループ操作ロジック
     */
    public AdminAuthGroupLogic authLogic;

    /**
     * Helper
     */
    public AdministratorAuthHelper administratorAuthHelper;

    /**
     * メタ権限情報。DICON で設定された値が自動バインドされる。必ず設定されている必要あり
     */
    private final List<MetaAuthType> metaAuthList;

    /**
     * コンストラクタ
     *
     * @param authLogic
     * @param administratorAuthHelper
     */
    @Autowired
    public AdministratorAuthController(AdminAuthGroupLogic authLogic, AdministratorAuthHelper administratorAuthHelper) {
        this.authLogic = authLogic;
        this.administratorAuthHelper = administratorAuthHelper;
        this.metaAuthList = (List<MetaAuthType>) ApplicationContextUtility.getApplicationContext()
                                                                          .getBean("metaAuthTypeList");
    }

    /**
     * 初期表示
     *
     * @return 遷移先ページ
     */
    @GetMapping(value = "")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/auth/index")
    protected String doLoadIndex(AdministratorAuthModel administratorAuthModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttrs,
                                 Model model) {

        //        super.doLoad();

        clearModel(AdministratorAuthModel.class, administratorAuthModel, model);

        // 権限グループ情報とメタ権限情報をページへ設定する
        Integer shopSeq = getCommonInfo().getCommonInfoBase().getShopSeq();
        List<AdminAuthGroupEntity> authList = authLogic.getAdminAuthGroupList(shopSeq);
        administratorAuthHelper.toPageForLoad(authList, metaAuthList, administratorAuthModel);

        return "administrator/auth/index";
    }
}
