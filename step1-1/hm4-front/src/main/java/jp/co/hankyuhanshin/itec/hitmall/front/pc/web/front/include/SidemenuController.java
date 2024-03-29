/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.include;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryTreeNodeGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryTreeNodeResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

/**
 * 共通サイドメニューAjax コントローラー
 *
 * @author kaneda
 */
@RestController
@RequestMapping("/")
public class SidemenuController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SidemenuController.class);

    /**
     * 商品Api
     */
    private final GoodsApi goodsApi;

    /**
     * 共通サイドメニューAjax Helper
     */
    private final SidemenuHelper sidemenuHelper;

    /**
     * 共通サイドメニューAjax コントローラー: NONE_LIMIT
     */
    private static final int NONE_LIMIT = -1;

    @Autowired
    public SidemenuController(GoodsApi goodsApi, SidemenuHelper sidemenuHelper) {
        this.goodsApi = goodsApi;
        this.sidemenuHelper = sidemenuHelper;
    }

    /**
     * 全カテゴリ情報の取得(Ajax)
     *
     * @param viewLevel
     * @param cid       現在の表示カテゴリID
     * @return List<SidemenuModelItem>
     */
    @GetMapping("getCategoryJsonData")
    @ResponseBody
    public List<SidemenuModelItem> getMultipleCategoryData(@RequestParam(required = false) String viewLevel,
                                                           @RequestParam(required = false) String cid) {

        List<SidemenuModelItem> sidemenuModelItems = new ArrayList<SidemenuModelItem>();

        if (StringUtils.isEmpty(viewLevel)) {
            viewLevel = PropertiesUtil.getSystemPropertiesValue("sidemenu.category.view.level");
        }

        // 全カテゴリ情報の取得
        CategoryDto categoryDto = getCategoryDto(NONE_LIMIT, viewLevel);
        if (categoryDto != null) {
            sidemenuHelper.toDataForLoad(categoryDto, sidemenuModelItems, cid);
        }

        return sidemenuModelItems;
    }

    /**
     * ルートカテゴリ一覧情報の取得
     *
     * @param limit     最大表示件数
     * @param viewLevel カテゴリー階層数
     * @return カテゴリDTO
     */
    protected CategoryDto getCategoryDto(Integer limit, String viewLevel) {
        CategoryTreeNodeGetRequest request = sidemenuHelper.toCategoryTreeNodeGetRequest(viewLevel);
        CategoryTreeNodeResponse response = null;
        try {
            response = goodsApi.getCategoryTreeNode(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryEntity(sidemenuHelper.toCategoryEntity(response.getCategoryEntity()));
        categoryDto.setCategoryDisplayEntity(
                        sidemenuHelper.toCategoryDisplayEntity(response.getCategoryDisplayEntity()));
        categoryDto.setCategoryDtoList(sidemenuHelper.toCategoryDtoList(response.getCategoryDtoList()));

        return categoryDto;
    }
}
