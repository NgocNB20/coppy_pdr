package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.json.category;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDataGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategorySearchForDaoConditionDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfo;
import lombok.Data;
import net.arnx.jsonic.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * カテゴリー情報を取得用サーブレット
 *
 * @author kato
 */
public class CategoryDataListServlet extends HttpServlet {
    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDataListServlet.class);

    /**
     * エラー監視用ログ
     */
    private static final Log LOG = LogFactory.getLog("category");

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * カテゴリー情報を取得用サーブレット: NONE_LIMIT
     */
    private static final int NONE_LIMIT = -1;

    /**
     * 文字コード
     */
    private static final String CHAR_SET = "UTF-8";

    /**
     * コンテンツタイプ
     */
    private static final String CONTENT_TYPE = "application/json";

    /**
     * 親カテゴリーパス取得用オフセット
     */
    private static final Integer PARENT_CATEGORY_PATH_OFFSET = 4;

    /**
     * DEFAULT_CATEGORY_MIN_LEVEL
     */
    private static final String DEFAULT_CATEGORY_MIN_LEVEL = "top";

    /**
     * ALL_CATEGORY_ID_LEVEL_2_DEFAULT
     */
    private static final String ALL_CATEGORY_ID_LEVEL_2_DEFAULT = "-all";

    /**
     * ALL_CATEGORY_NAME_LEVEL_2_DEFAULT
     */
    private static final String ALL_CATEGORY_NAME_LEVEL_2_DEFAULT = "すべて見る";

    /**
     * リクエストパラメータをチェックしてエラー内容をログに出力します<br/>
     * エラーでない場合は処理を続行します
     *
     * @param req  リクエスト
     * @param resp レスポンス
     * @throws ServletException 処理の失敗
     * @throws IOException      入出力処理の失敗
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if ("GET".equals(req.getMethod())) {
                // リクエストパラメータを取得
                String sl = req.getParameter("sl");
                String el = req.getParameter("el");

                // パラメータチェック
                if (StringUtil.isEmpty(sl)) {
                    // 未設定の場合は異常終了
                    throw new RuntimeException("開始カテゴリー階層[sl]が未設定です。");
                } else if (!sl.matches("^[0-9]+$")) {
                    // 整数でない場合は異常終了
                    throw new RuntimeException("開始カテゴリー階層[sl]が不正です。　[指定開始カテゴリー階層：" + sl.toString() + "]");
                } else if ((sl.length() > 2)) {
                    // 2桁以内でなければ異常終了
                    throw new RuntimeException("開始カテゴリー階層[sl]は2桁以内で指定してください。　[指定開始カテゴリー階層：" + sl.toString() + "]");
                }

                if (StringUtil.isEmpty(el)) {
                    // 未設定の場合は異常終了
                    throw new RuntimeException("終了カテゴリー階層[el]が未設定です。");
                } else if (!el.matches("^[0-9]+$")) {
                    // 整数でない場合は異常終了
                    throw new RuntimeException("終了カテゴリー階層[el]が不正です。　[指定終了カテゴリー階層：" + el.toString() + "]");
                } else if ((el.length() > 2)) {
                    // 2桁以内でなければ異常終了
                    throw new RuntimeException("開始カテゴリー階層[el]は2桁以内で指定してください。　[指定終了カテゴリー階層：" + el.toString() + "]");
                }
            }
        } catch (Exception e) {
            // HTTPステータス・コード「404 Not Found」を返却
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            // ログ出力
            LOGGER.error("カテゴリーデータJSON出力に失敗しました。", e);
            // エラー監視用ログ出力
            // 「frontPcCategoryDataJson.log」はエラー時のみ出力
            LOG.error("カテゴリーデータJSON出力に失敗しました。", e);

            return;
        }

        super.service(req, resp);
    }

    /**
     * カテゴリー情報を取得します
     *
     * @param req  リクエスト
     * @param resp レスポンス
     * @throws ServletException 処理の失敗
     * @throws IOException      入出力処理の失敗
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // リクエストパラメータを取得
        String sl = req.getParameter("sl");
        String el = req.getParameter("el");

        // ログ出力
        LOGGER.debug("カテゴリーデータJSON出力を開始します。");

        // レスポンスにバイナリデータを出力するための出力ストリームを取得
        OutputStream outStream = getOutputStream(resp);

        try {
            // ヘッダー出力
            resp.reset();
            resp.setBufferSize(8 * 1024);
            resp.setContentType(CONTENT_TYPE + "; charset=" + CHAR_SET);
            resp.setCharacterEncoding(CHAR_SET);

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
            conditionDto.setOrderField(CategorySearchForDaoConditionDto.CATEGORY_FIELD_CATEGORY_PATH);
            conditionDto.setOrderAsc(true);

            // 検索前ページャーセットアップ
            PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);
            pageInfo.setPnum(1);
            pageInfo.setLimit(NONE_LIMIT);
            pageInfo.setOrderField(CategorySearchForDaoConditionDto.CATEGORY_FIELD_CATEGORY_PATH);
            pageInfo.setOrderAsc(true);

            conditionDto.setPageInfo(pageInfo);

            // 検索条件の設定（カテゴリー階層）
            Integer startCategorylevel = new Integer(sl);
            Integer endCategorylevel = new Integer(el);
            // 検索条件の設定（カテゴリー種類）
            HTypeCategoryType categoryType = HTypeCategoryType.NORMAL;

            CategoryDataGetRequest categoryDataGetRequest =
                            toCategoryDataGetRequest(conditionDto, startCategorylevel, endCategorylevel, categoryType);

            // カテゴリーデータの取得
            GoodsApi goodsApi = ApplicationContextUtility.getBean(GoodsApi.class);
            List<CategoryDetailsDtoResponse> categoryDetailsDtoResponseList =
                            goodsApi.getCategoryData(categoryDataGetRequest);
            List<CategoryDetailsDto> categoryDetailsDtoList = toCategoryDetailsDtoList(categoryDetailsDtoResponseList);

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

            Map<String, String> cidParentMap = new HashMap<>();
            for (CategoryDetailsDto categoryDetailsDto : categoryDetailsDtoList) {
                cidParentMap.put(categoryDetailsDto.getCategoryPath(), categoryDetailsDto.getCategoryId());
            }

            for (CategoryDetailsDto categoryDetailsDto : categoryDetailsDtoList) {
                String cidParent = DEFAULT_CATEGORY_MIN_LEVEL;
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
                    // 自身のカテゴリIDの親パス取得
                    if (cidParentMap.containsKey(parentCategoryPath)) {
                        cidParent = cidParentMap.get(parentCategoryPath);
                    }

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
                // 自身のカテゴリIDの親パス
                innerCategoryJson.setCidParent(cidParent);

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

                if (categoryDetailsDto.getCategoryLevel() == 1) {
                    InnerCategoryJson allCategoryInfoLevel2 = new InnerCategoryJson();

                    //カテゴリID
                    allCategoryInfoLevel2.setCategoryid(categoryDetailsDto.getCategoryId() + ALL_CATEGORY_ID_LEVEL_2_DEFAULT);
                    //カテゴリ階層
                    allCategoryInfoLevel2.setCategorylevel(categoryDetailsDto.getCategoryLevel() + 1);
                    // カテゴリパス
                    allCategoryInfoLevel2.setCategorypath(categoryDetailsDto.getCategoryPath());
                    // カテゴリ表示名
                    allCategoryInfoLevel2.setCategoryname(ALL_CATEGORY_NAME_LEVEL_2_DEFAULT);
                    // 自身のカテゴリIDの親パス
                    allCategoryInfoLevel2.setCidParent(categoryDetailsDto.getCategoryId());

                    // JSON用カテゴリーデータリストに追加
                    innerCategoryJsonList.add(allCategoryInfoLevel2);
                }

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
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
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

        /**
         * 自身のカテゴリIDの親パス
         */
        public String cidParent;

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
        public ArrayList<CategoryDataListServlet.InnerCategoryJson> array;

    }

    /**
     * カテゴリーデータ取得リクエストに変換
     *
     * @param conditionDto       カテゴリ情報Dao用検索条件Dto
     * @param startCategorylevel カテゴリー階層
     * @param endCategorylevel   カテゴリー階層
     * @param categoryType       カテゴリー種類
     * @return カテゴリーデータ取得リクエスト
     */
    private CategoryDataGetRequest toCategoryDataGetRequest(CategorySearchForDaoConditionDto conditionDto,
                                                            Integer startCategorylevel,
                                                            Integer endCategorylevel,
                                                            HTypeCategoryType categoryType) {
        CategoryDataGetRequest categoryDataGetRequest = new CategoryDataGetRequest();
        categoryDataGetRequest.setConditionDto(toCategorySearchForDaoConditionDtoRequest(conditionDto));
        categoryDataGetRequest.setStartCategorylevel(startCategorylevel);
        categoryDataGetRequest.setEndCategorylevel(endCategorylevel);
        if (categoryType != null) {
            categoryDataGetRequest.setCategoryType(categoryType.getValue());
        }

        return categoryDataGetRequest;
    }

    /**
     * カテゴリ検索条件Dtoリクエストに変換
     *
     * @param conditionDto カテゴリ情報Dao用検索条件Dto
     * @return カテゴリ検索条件Dtoリクエスト
     */
    private CategorySearchForDaoConditionDtoRequest toCategorySearchForDaoConditionDtoRequest(
                    CategorySearchForDaoConditionDto conditionDto) {
        if (conditionDto == null) {
            return null;
        }

        CategorySearchForDaoConditionDtoRequest conditionDtoRequest = new CategorySearchForDaoConditionDtoRequest();
        conditionDtoRequest.setCategoryId(conditionDto.getCategoryId());
        conditionDtoRequest.setCategorySeqList(conditionDto.getCategorySeqList());
        conditionDtoRequest.setMaxHierarchical(conditionDto.getMaxHierarchical());
        if (conditionDto.getOpenStatus() != null) {
            conditionDtoRequest.setOpenStatus(conditionDto.getOpenStatus().getValue());
        }
        conditionDtoRequest.setNotInCategoryIdList(conditionDto.getNotInCategoryIdList());
        conditionDtoRequest.setOrderField(conditionDto.getOrderField());
        conditionDtoRequest.setOrderAsc(conditionDto.isOrderAsc());

        return conditionDtoRequest;
    }

    /**
     * カテゴリ詳細Dtoリストに変換
     *
     * @param categoryDetailsDtoResponseList カテゴリ詳細Dtoレスポンスリスト
     * @return カテゴリ詳細Dtoリスト
     */
    private List<CategoryDetailsDto> toCategoryDetailsDtoList(List<CategoryDetailsDtoResponse> categoryDetailsDtoResponseList) {
        if (CollectionUtil.isEmpty(categoryDetailsDtoResponseList)) {
            return new ArrayList<>();
        }
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        List<CategoryDetailsDto> categoryDetailsDtoList = new ArrayList<>();
        categoryDetailsDtoResponseList.forEach(response -> {
            CategoryDetailsDto categoryDetailsDto = new CategoryDetailsDto();

            categoryDetailsDto.setCategoryId(response.getCategoryId());
            categoryDetailsDto.setCategoryNamePC(response.getCategoryName());
            categoryDetailsDto.setCategoryNotePC(response.getCategoryNote());
            categoryDetailsDto.setFreeTextPC(response.getFreeText());
            categoryDetailsDto.setMetaDescription(response.getMetaDescription());
            categoryDetailsDto.setMetaKeyword(response.getMetaKeyword());
            categoryDetailsDto.setCategoryImagePC(response.getCategoryImage());
            categoryDetailsDto.setCategorySeq(response.getCategorySeq());
            categoryDetailsDto.setShopSeq(1001);
            categoryDetailsDto.setCategoryName(response.getCategoryName());
            categoryDetailsDto.setCategoryOpenStatusPC(HTypeOpenStatus.of(response.getCategoryOpenStatus()));
            categoryDetailsDto.setCategoryOpenStartTimePC(
                            conversionUtility.toTimeStamp(response.getCategoryOpenStartTime()));
            categoryDetailsDto.setCategoryOpenEndTimePC(
                            conversionUtility.toTimeStamp(response.getCategoryOpenEndTime()));
            categoryDetailsDto.setCategoryType(HTypeCategoryType.of(response.getCategoryType()));
            categoryDetailsDto.setCategorySeqPath(response.getCategorySeqPath());
            categoryDetailsDto.setOrderDisplay(response.getOrderDisplay());
            categoryDetailsDto.setRootCategorySeq(response.getRootCategorySeq());
            categoryDetailsDto.setCategoryLevel(response.getCategoryLevel());
            categoryDetailsDto.setCategoryPath(response.getCategoryPath());
            categoryDetailsDto.setManualDisplayFlag(HTypeManualDisplayFlag.of(response.getManualDisplayFlag()));
            categoryDetailsDto.setVersionNo(response.getVersionNo());
            categoryDetailsDto.setRegistTime(conversionUtility.toTimeStamp(response.getRegistTime()));
            categoryDetailsDto.setUpdateTime(conversionUtility.toTimeStamp(response.getUpdateTime()));
            categoryDetailsDto.setDisplayRegistTime(conversionUtility.toTimeStamp(response.getDisplayRegistTime()));
            categoryDetailsDto.setDisplayUpdateTime(conversionUtility.toTimeStamp(response.getDisplayUpdateTime()));
            categoryDetailsDto.setSiteMapFlag(HTypeSiteMapFlag.of(response.getSiteMapFlag()));

            categoryDetailsDtoList.add(categoryDetailsDto);
        });

        return categoryDetailsDtoList;
    }
}
