/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.web;

import jp.co.hankyuhanshin.itec.hitmall.front.base.dto.AbstractConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.favorite.MemberFavoriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

/**
 * PageInfoヘルパー
 * PageInfoの設定を行うの便利メソッドを提供する
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class PageInfoHelper {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PageInfoHelper.class);

    /**
     * ページングセットアップ【共通】
     *
     * @param model      検索画面のModel
     * @param page       ページ番号
     * @param limit      最大表示件数
     * @param nextPage   次のページ番号
     * @param prevPage   前のページ番号
     * @param total      全件数
     * @param totalPages ページ数
     * @param stypeMap   ソート項目マップ（stype⇔orderFieldの対応を持つマップ）
     * @param stype      ソート項目（省略文字）
     * @param orderField ソート項目
     * @param orderAsc   昇順/降順フラグ
     * @param vtype      画像表示条件
     * @return ページングセットアップ後の検索画面のModel
     */
    public <T extends AbstractModel> T setupPageInfo(T model,
                                                     Integer page,
                                                     Integer limit,
                                                     Integer nextPage,
                                                     Integer prevPage,
                                                     Integer total,
                                                     Integer totalPages,
                                                     Map<String, String> stypeMap,
                                                     String stype,
                                                     String orderField,
                                                     boolean orderAsc,
                                                     String vtype) {
        return setupPageInfo(model, page, limit, nextPage, prevPage, total, totalPages, stypeMap, stype, orderField,
                             orderAsc, vtype, null
                            );
    }

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * ページングセットアップ（ユニサーチ用）
     *
     * @param model      検索画面のModel
     * @param page       ページ番号
     * @param limit      最大表示件数
     * @param nextPage   次のページ番号
     * @param prevPage   前のページ番号
     * @param total      全件数
     * @param totalPages ページ数
     * @param stypeMap   ソート項目マップ（stype⇔orderFieldの対応を持つマップ）
     * @param stype      ソート項目（省略文字）
     * @param orderField ソート項目
     * @param orderAsc   昇順/降順フラグ
     * @param vtype      画像表示条件
     * @param sort       UKソート順
     * @return ページングセットアップ後の検索画面のModel
     */
    public <T extends AbstractModel> T setupPageInfo(T model,
                                                     Integer page,
                                                     Integer limit,
                                                     Integer nextPage,
                                                     Integer prevPage,
                                                     Integer total,
                                                     Integer totalPages,
                                                     Map<String, String> stypeMap,
                                                     String stype,
                                                     String orderField,
                                                     boolean orderAsc,
                                                     String vtype,
                                                     String sort) {

        // ----------------------------
        // PageInfo生成
        // ----------------------------
        PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);

        // ----------------------------
        // 各種値をセット
        // -----------------------------
        // 現在のページ番号
        if (page != null) {
            pageInfo.setPnum(page);
        }
        // 最大表示件数
        if (limit != null) {
            pageInfo.setLimit(limit);
        }
        // 次のページ番号
        if (nextPage != null) {
            pageInfo.setNextPage(nextPage);
        }
        // 前のページ番号
        if (prevPage != null) {
            pageInfo.setPrevPage(limit);
        }
        // 全件数
        if (total != null) {
            pageInfo.setTotal(total);
        }
        // ページ数
        if (totalPages != null) {
            pageInfo.setTotalPages(totalPages);
        }

        // stype⇒orderFieldに変換
        // ※注意※stypeはこれ以降利用しない
        if (stype != null) {
            orderField = stype;
            pageInfo.setOrderField(orderField);
        }

        // ソート条件Mapがある場合
        if (stypeMap != null) {

            // stypeMapはPageInfoに保持させる
            // ※ページャリンクで使うため（orderField⇒stypeへの変換用に）
            pageInfo.setStypeMap(stypeMap);
        }
        pageInfo.setOrderAsc(orderAsc);
        // ソート条件（ソート項目が渡されていれば、セット)
        if (orderField != null) {
            pageInfo.setOrder(orderField, orderAsc);
        }
        // 画像表示区分
        if (vtype != null) {
            pageInfo.setVtype(vtype);
        }
        // UK並び順
        if (sort != null) {
            pageInfo.setSort(sort);
        }

        // ----------------------------
        // Modelにセット
        // ----------------------------
        model.setPageInfo(pageInfo);

        // ----------------------------
        // Modelを返却
        // ----------------------------
        return model;
    }
    // 2023-renew No36-1, No61,67,95 to here

    public <T extends AbstractConditionDto> T setupPageInfoForSkipCount(T conditionDto,
                                                                        int limit,
                                                                        String orderField,
                                                                        boolean orderAsc) {
        // ----------------------------
        // PageInfo生成
        // ----------------------------
        PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);

        if (limit > 0) {
            pageInfo.setLimit(limit);
        }

        // ソート条件（ソート項目が渡されていれば、セット)
        if (orderField != null) {
            pageInfo.setOrder(orderField, orderAsc);
        }

        // ----------------------------
        // ConditionDtoにセット
        // ----------------------------
        conditionDto.setPageInfo(pageInfo);

        // ----------------------------
        // ConditionDtoを返却
        // ----------------------------
        return conditionDto;
    }

    /**
     * ページャーセットアップ
     * - PageInfo内部をページャーHTMLで利用できるようセットアップする
     * - ModelにPageInfoをセットする
     *
     * @param pageInfo Page情報
     * @param model    Model
     */
    public void setupViewPager(PageInfo pageInfo, Model model) {
        // Pagerセットアップ
        pageInfo.setupViewPager();
        // ModelにpageInfoをセット
        model.addAttribute(PageInfo.ATTRIBUTE_NAME_KEY, pageInfo);
    }

    /**
     * ページャーセットアップ（AbstractModelに保持する場合）
     * - PageInfo内部をページャーHTMLで利用できるようセットアップする
     * - AbstractModelにPageInfoをセットする
     *
     * @param pageInfo Page情報
     * @param model    AbstractModel
     */
    public void setupViewPager(PageInfo pageInfo, AbstractModel model) {
        // Pagerセットアップ
        pageInfo.setupViewPager();
        // AbstractModelにpageInfoをセット
        model.setPageInfo(pageInfo);
    }

    /**
     * ページャーセットアップ
     * - PageInfo内部をページャーHTMLで利用できるようセットアップする
     * - ModelにPageInfoをセットする
     *
     * @param pageInfo      Page情報
     * @param model         Model
     * @param pageLinkCount ページ番号表示数
     */
    public void setupViewPager(PageInfo pageInfo, Model model, int pageLinkCount) {
        // Pagerセットアップ
        pageInfo.setupViewPager(pageLinkCount);
        // ModelにpageInfoをセット
        model.addAttribute(PageInfo.ATTRIBUTE_NAME_KEY, pageInfo);
    }

    /**
     * ページリクエストを設定
     *
     * @param pageRequest ページ情報リクエスト
     * @param page        ページリクエストを設定
     * @param limit       ページサイズ
     * @param orderBy     ソート項目
     * @param sort        ソート条件（true：昇順、false：降順
     */
    public void setupPageRequest(Object pageRequest, Integer page, Integer limit, String orderBy, Boolean sort) {
        try {
            Field[] fields = pageRequest.getClass().getDeclaredFields();
            for (Field field : fields) {
                switch (field.getName()) {
                    case "page":
                        field.setAccessible(true);
                        field.set(pageRequest, page);
                        break;
                    case "limit":
                        field.setAccessible(true);
                        field.set(pageRequest, limit);
                        break;
                    case "orderBy":
                        field.setAccessible(true);
                        field.set(pageRequest, orderBy);
                        break;
                    case "sort":
                        field.setAccessible(true);
                        field.set(pageRequest, sort);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
