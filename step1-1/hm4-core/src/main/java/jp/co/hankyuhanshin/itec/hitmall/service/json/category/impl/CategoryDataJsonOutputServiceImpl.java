// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.service.json.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.json.category.CategoryDataGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.json.category.CategoryDataJsonOutputService;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfo;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import lombok.Data;
import net.arnx.jsonic.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * カテゴリーデータJSON出力サービス<br/>
 *
 * @author kato
 */
@Service
public class CategoryDataJsonOutputServiceImpl extends AbstractShopService implements CategoryDataJsonOutputService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDataJsonOutputServiceImpl.class);

    /**
     * エラー監視用ログ
     */
    private static final Log LOG = LogFactory.getLog("category");

    /**
     * 文字コード
     */
    private static final String CHAR_SET = "UTF-8";

    /**
     * コンテンツタイプ
     */
    private static final String CONTENT_TYPE = "application/json";

    /**
     * カテゴリーデータ取得ロジック
     */
    private final CategoryDataGetLogic categoryDataGetLogic;

    /**
     * 親カテゴリーパス取得用オフセット
     */
    private static final Integer PARENT_CATEGORY_PATH_OFFSET = 4;

    @Autowired
    public CategoryDataJsonOutputServiceImpl(CategoryDataGetLogic categoryDataGetLogic) {
        this.categoryDataGetLogic = categoryDataGetLogic;
    }

    /**
     * カテゴリー情報を取得し、JSON形式で返却する。
     *
     * @param sl       開始カテゴリー階層（検索パラメータ）
     * @param el       終了カテゴリー階層（検索パラメータ）
     * @param response レスポンス
     */
    @Override
    public void execute(String sl, String el, HttpServletResponse response) {

        // ログ出力
        LOGGER.debug("カテゴリーデータJSON出力を開始します。");

        // レスポンスにバイナリデータを出力するための出力ストリームを取得
        OutputStream outStream = getOutputStream(response);

        try {

            // ヘッダー出力
            response.reset();
            response.setBufferSize(8 * 1024);
            response.setContentType(CONTENT_TYPE + "; charset=" + CHAR_SET);
            response.setCharacterEncoding(CHAR_SET);

            // 引数チェック
            if (StringUtils.isEmpty(sl)) {
                // 未設定の場合は異常終了
                throw new RuntimeException("開始カテゴリー階層が未設定です。");
            } else if (!sl.matches("^[0-9]+$")) {
                // 整数でない場合は異常終了
                throw new RuntimeException("開始カテゴリー階層が不正です。　[開始カテゴリー階層：" + sl + "]");
            }
            if (StringUtils.isEmpty(el)) {
                // 未設定の場合は異常終了
                throw new RuntimeException("終了カテゴリー階層が未設定です。");
            } else if (!el.matches("^[0-9]+$")) {
                // 整数でない場合は異常終了
                throw new RuntimeException("終了カテゴリー階層が不正です。　[終了カテゴリー階層：" + el + "]");
            }

            // 検索条件の設定（標準検索条件DTO）
            CategorySearchForDaoConditionDto conditionDto =
                            ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
            conditionDto.setSiteType(HTypeSiteType.FRONT_PC);
            conditionDto.setOpenStatus(HTypeOpenStatus.OPEN);

            // ページングセットアップ
            PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);
            pageInfo.setPage(1);
            pageInfo.setLimit(Integer.MAX_VALUE);
            pageInfo.setOrder(CategorySearchForDaoConditionDto.CATEGORY_FIELD_CATEGORY_PATH, true);
            pageInfo.setupSelectOptions();
            conditionDto.setPageInfo(pageInfo);

            // 検索条件の設定（カテゴリー階層）
            Integer startCategorylevel = new Integer(sl);
            Integer endCategorylevel = new Integer(el);
            // 検索条件の設定（カテゴリー種類）
            HTypeCategoryType categoryType = HTypeCategoryType.NORMAL;

            // カテゴリーデータの取得
            List<CategoryDetailsDto> categoryDetailsDtoList =
                            categoryDataGetLogic.execute(conditionDto, startCategorylevel, endCategorylevel,
                                                         categoryType
                                                        );
            if ((categoryDetailsDtoList == null) || categoryDetailsDtoList.isEmpty()) {
                // カテゴリーデータが取得できなかった場合は異常終了
                throw new RuntimeException("カテゴリーデータが存在しません。");
            }

            // JSON用カテゴリーデータリストを生成
            ArrayList<InnerCategoryJson> innerCategoryJsonList = new ArrayList<InnerCategoryJson>();
            // 木構造検証用カテゴリーパスリストを生成
            ArrayList<String> parentCategoryPathList = new ArrayList<String>();
            // 木構造検証理由：Daoで取得した値をそのままデータリストに登録してしまうと
            // カテゴリータイプが"特殊"、または公開状態が"非公開"のために取得されない階層レベル1のカテゴリーが存在する場合に
            // そのカテゴリーの木構造に紐付く階層レベル2のカテゴリーが取得されているとそのまま木構造の違う別の階層レベル1の
            // カテゴリーに紐付くように表示されてしまう。
            // そのため、階層レベル2以上のカテゴリーは木構造の親カテゴリーパスを確認し親が登録されている場合のみ登録する。

            for (CategoryDetailsDto categoryDetailsDto : categoryDetailsDtoList) {
                InnerCategoryJson innerCategoryJson = new InnerCategoryJson();

                // カテゴリーレベルをチェック
                // 階層レベル1ならばそのままデータリストに登録
                if (categoryDetailsDto.getCategoryLevel() != 1) {
                    // 木構造検証用カテゴリーパスリストが空ならばデータリストに登録しない
                    if (parentCategoryPathList.isEmpty()) {
                        continue;
                    }
                    // 自身のカテゴリパスの親パスが存在するかをチェック(木構造が正確であるか検証)
                    // 自身のパスの親パス取得
                    String parentCategoryPath = categoryDetailsDto.getCategoryPath()
                                                                  .substring(0, categoryDetailsDto.getCategoryPath()
                                                                                                  .length()
                                                                                - PARENT_CATEGORY_PATH_OFFSET);
                    // 木構造検証用カテゴリーパスリスト内に自身の親パスがなければデータリストに登録しない
                    if (!parentCategoryPathList.contains(parentCategoryPath)) {
                        continue;
                    }
                }
                // カテゴリー詳細DTOから値を設定
                // カテゴリID
                innerCategoryJson.setCategoryid(categoryDetailsDto.getCategoryId());
                // カテゴリ階層
                innerCategoryJson.setCategorylevel(categoryDetailsDto.getCategoryLevel());
                // カテゴリパス
                innerCategoryJson.setCategorypath(categoryDetailsDto.getCategoryPath());
                // カテゴリ表示名
                innerCategoryJson.setCategoryname(categoryDetailsDto.getCategoryNamePC());
                // フリーテキスト
                innerCategoryJson.setFreetext(categoryDetailsDto.getFreeTextPC());
                // meta-description
                innerCategoryJson.setMetadescription(categoryDetailsDto.getMetaDescription());
                // meta-keyword
                innerCategoryJson.setMetakeyword(categoryDetailsDto.getMetaKeyword());
                // カテゴリ画像
                innerCategoryJson.setCategoryimage(categoryDetailsDto.getCategoryImagePC());

                // Null値を「""」に置換
                innerCategoryJson.setFreetext(StringUtils.defaultIfEmpty(innerCategoryJson.getFreetext(), ""));
                innerCategoryJson.setMetadescription(
                                StringUtils.defaultIfEmpty(innerCategoryJson.getMetadescription(), ""));
                innerCategoryJson.setMetakeyword(StringUtils.defaultIfEmpty(innerCategoryJson.getMetakeyword(), ""));
                innerCategoryJson.setCategoryimage(
                                StringUtils.defaultIfEmpty(innerCategoryJson.getCategoryimage(), ""));

                // 自身のカテゴリーパスを木構造検証用カテゴリーパスリストに追加
                parentCategoryPathList.add(categoryDetailsDto.getCategoryPath());
                // JSON用カテゴリーデータリストに追加
                innerCategoryJsonList.add(innerCategoryJson);

            }

            // JSON用カテゴリーデータを生成
            CategoryJson categoryJson = new CategoryJson();
            categoryJson.setArray(innerCategoryJsonList);

            // JSONのエンコード処理
            String jsonStr = JSON.encode(categoryJson);

            // 出力ストリームへ書き出す
            outStream.write(jsonStr.getBytes(Charset.forName(CHAR_SET)));

            // ログ出力
            LOGGER.debug("カテゴリーデータJSON出力を終了します。");

        } catch (Exception e) {
            // HTTPステータス・コード「404 Not Found」を返却
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            // ログ出力
            LOGGER.error("カテゴリーデータJSON出力に失敗しました。", e);
            // エラー監視用ログ出力
            // 「frontPcCategoryDataJson.log」はエラー時のみ出力
            LOG.error("カテゴリーデータJSON出力に失敗しました。", e);

        } finally {
            try {
                // 出力ストリームの終了
                outStream.flush();
                outStream.close();
            } catch (Exception e) {
                // ログ出力
                LOGGER.error("カテゴリーデータJSON出力に失敗しました。", e);
                // エラー監視用ログ出力
                // 「frontPcCategoryDataJson.log」はエラー時のみ出力
                LOG.error("カテゴリーデータJSON出力に失敗しました。", e);
            }
        }
    }

    /**
     * HttpServletResponse から出力ストリームを取得する。
     *
     * @param response HttpServletResponse オブジェクト
     * @return 出力ストリーム
     */
    protected OutputStream getOutputStream(HttpServletResponse response) {
        try {
            return response.getOutputStream();
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON用カテゴリーデータリストBean
     */
    @Data
    class InnerCategoryJson implements Serializable {

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 1L;

        /**
         * カテゴリID
         */
        public String categoryid;

        /**
         * カテゴリ階層
         */
        public Integer categorylevel;

        /**
         * カテゴリ表示名
         */
        public String categoryname;

        /**
         * カテゴリパス
         */
        public String categorypath;

        /**
         * フリーテキスト
         */
        public String freetext;

        /**
         * meta-description
         */
        public String metadescription;

        /**
         * meta-keyword
         */
        public String metakeyword;

        /**
         * カテゴリ画像
         */
        public String categoryimage;

    }

    /**
     * JSON用カテゴリーデータBean
     */
    @Data
    class CategoryJson implements Serializable {

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 1L;

        /**
         * 情報の説明 情報の種別
         */
        public final String type = "category";

        /**
         * データ
         */
        public ArrayList<InnerCategoryJson> array;

    }

}

// PDR Migrate Customization to here
