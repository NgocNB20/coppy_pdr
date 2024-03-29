/*
 * Project Name : HIT-MALL4
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.utility.CategoryUtility;
import jp.co.hankyuhanshin.itec.hmbase.seasar.PagerCondition;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * カテゴリ管理
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class CategoryListHelper {

    /**
     * 抽出カテゴリー：プルダウン表示時の分割文字
     */
    protected static final String PULLDOWN_SPLIT = ">";
    /**
     * カテゴリー一覧表示： 表示
     */
    protected static final String STYLE_DISPLAY = "";
    /**
     * カテゴリー一覧表示： 非表示
     */
    protected static final String STYLE_DISPLAY_NONE = "display:none;";

    /**
     * 変換ユーティリティ
     */
    public ConversionUtility conversionUtility;
    /**
     * カテゴリーユーティリティ
     */
    public CategoryUtility categoryUtility;

    /**
     * コンストラクター
     *
     * @param conversionUtility
     * @param categoryUtility
     */
    @Autowired
    public CategoryListHelper(ConversionUtility conversionUtility, CategoryUtility categoryUtility) {
        this.conversionUtility = conversionUtility;
        this.categoryUtility = categoryUtility;
    }

    /**
     * カテゴリー一覧を持っている配列を作成する<br/>
     *
     * @param dtCategory カテゴリリスト
     * @return 表示カテゴリリスト
     */
    public String[] splitCategory(String dtCategory) {
        String[] result = dtCategory.split(ConversionUtility.DIV_CHAR_SLASH);
        return result;
    }

    /**
     * アップデートの為のカテゴリー配列を作成する<br/>
     *
     * @param category カテゴリリスト
     * @return アップデートにカテゴリー配列
     */
    protected String[] splitItemCategoryUpdate(String category) {
        String[] result = category.split(ConversionUtility.DIV_CHAR_COMMA);
        return result;
    }

    /**
     * カテゴリーSEQパスをカテゴリーSEQごとに分割し、setに追加<br/>
     *
     * @param categorySeqPath カテゴリSEQパス
     * @return * カテゴリーSEQパスをカテゴリーSEQごとに分割
     */
    protected Set<Integer> splitCategorySeqPath(String categorySeqPath) {

        Set<Integer> reslut = new HashSet<>();

        // カテゴリーSEQパスが空の場合、nullを返す
        if (StringUtil.isEmpty(categorySeqPath)) {
            return reslut;
        }

        // カテゴリーSEQパスの文字数を取得
        int categoryPathLength = categorySeqPath.length();

        // カテゴリーSEQごとに追加
        for (int i = 0; i < categoryPathLength; i += CategoryUtility.CATEGORY_SEQ_LENGTH) {
            String categoryPath = categorySeqPath.substring(i, i + CategoryUtility.CATEGORY_SEQ_LENGTH);
            reslut.add(conversionUtility.toInteger(categoryPath));
        }

        return reslut;
    }

    /**
     * カテゴリーSEQパスを階層ごとにsetに追加<br/>
     * <p>
     * ex)「categorySeqPath:10000001000000110000002」に対して
     * 「1000000」追加
     * 「100000010000001」追加
     * 「10000001000000110000002」追加
     *
     * @param categorySeqPath カテゴリSEQパス
     * @return カテゴリーSEQパスを階層ごとにset
     */
    protected Set<String> getCategorySeqSetByLevel(String categorySeqPath) {

        Set<String> reslut = new HashSet<>();

        // カテゴリーSEQパスが空の場合、nullを返す
        if (StringUtil.isEmpty(categorySeqPath)) {
            return reslut;
        }

        // カテゴリーSEQパスの文字数を取得
        int categoryPathLength = categorySeqPath.length();

        StringBuilder buff = new StringBuilder();

        // カテゴリーSEQパスを階層ごとに追加
        for (int i = 0; i < categoryPathLength; i += CategoryUtility.CATEGORY_SEQ_LENGTH) {
            String categoryPath = categorySeqPath.substring(i, i + CategoryUtility.CATEGORY_SEQ_LENGTH);
            buff.append(categoryPath);
            reslut.add(buff.toString());
        }

        return reslut;
    }

    /**
     * 親カテゴリーのカテゴリーSEQパスを取得<br/>
     *
     * @param categorySeqPath カテゴリーSEQパス
     * @return 親カテゴリーSEQパス
     */
    public String getParentSeqPath(String categorySeqPath) {

        // 自身を除いたカテゴリーSEQパスを取得
        String parentSeqPath =
                        categorySeqPath.substring(0, categorySeqPath.length() - CategoryUtility.CATEGORY_SEQ_LENGTH);

        return parentSeqPath;
    }

    /**
     * 新規階層を作成する<br/>
     *
     * @param lstCategory カテゴリリスト
     * @param model       カテゴリ一覧
     */
    public void updateListCategory(String lstCategory, CategoryListModel model) {
        String[] category = splitCategory(lstCategory);

        for (String value : category) {
            String[] itemUpdate = splitItemCategoryUpdate(value);
            String categorySeqPathNew = itemUpdate[1].toString();
            int orderDisplay = conversionUtility.toInteger(itemUpdate[2].toString());
            int indexOrigin = conversionUtility.toInteger(itemUpdate[3].toString());
            int level = (categorySeqPathNew.length() / 8) - 1;

            String categoryPath = createCategoryPathNew(itemUpdate[1].toString(), lstCategory);
            CategoryModelItem targetItem = model.getResultItems().get(indexOrigin);

            targetItem.setCategorySeqPath(categorySeqPathNew);
            targetItem.setOrderDisplay(conversionUtility.toInteger(orderDisplay));
            targetItem.setCategoryLevel(level);
            // 生成したカテゴリパスを設定
            targetItem.setCategoryPath(replaceParentPath(categoryPath, model));
        }
    }

    /**
     * 新規階層を作成する<br/>
     *
     * @param category    カテゴリリスト
     * @param lstCategory カテゴリリスト
     * @return 階層
     */
    protected String createCategoryPathNew(String category, String lstCategory) {
        String categorySeqPathNew = category.substring(8);
        String parent = category.substring(0, 8);
        StringBuilder buff = new StringBuilder();

        for (int j = 0; j < categorySeqPathNew.length() / 8; j++) {
            String path = categorySeqPathNew.substring(0, 8 * j + 8);
            String[] arrCategory = splitCategory(lstCategory);
            for (int index = 0; index < arrCategory.length; index++) {
                String[] itemUpdateChild = splitItemCategoryUpdate(arrCategory[index]);
                if (itemUpdateChild[1].toString().equals(parent.concat(path))) {
                    buff.append("/" + String.format(
                                    "%0" + 3 + "d", conversionUtility.toInteger(itemUpdateChild[2].toString())));
                }

            }

        }
        return buff.toString();
    }

    /**
     * 制限したLimit値のカテゴリーレベルまで、カテゴリーパスを置き換える<br/>
     *
     * @param categoryPath カテゴリーパス
     * @param model        カテゴリ一覧
     * @return カテゴリーパス
     */
    protected String replaceParentPath(String categoryPath, CategoryListModel model) {

        // 抽出カテゴリーが表示されていない または プルダウンが「指定なし」の場合、この処理は行わない。
        if (!model.isExtractCategory() || StringUtil.isEmpty(model.getExtractCategoryName())) {
            return categoryPath;
        }

        // 制限された階層のカテゴリーパスの文字数を取得
        int limitPathLength = model.getExtractCategoryLimit() * CategoryUtility.CATEGORY_PATH_LENGTH;

        // 制限された階層よりレベルが低い(親カテゴリー)の場合、この処理は行わない。
        if (categoryPath.length() < limitPathLength) {
            return categoryPath;
        }

        // プルダウン値で指定したカテゴリー取得
        CategoryEntity categoryEntity = model.getCategoryMap().get(model.getExtractCategoryName());

        // カテゴリーパスを置き換えるため、制限された階層まで切り出し
        String categoyPathCut = categoryPath.substring(0, limitPathLength);

        // プルダウン値で指定したカテゴリーパスに置き換え
        String categoyPathNew = categoryPath.replaceFirst(categoyPathCut, categoryEntity.getCategoryPath());

        return categoyPathNew;
    }

    /**
     * 「戻る」ボタン押下時に、カテゴリSEQパス(画面受渡し用)が存在するかチェック
     *
     * @param model カテゴリ管理：カテゴリ一覧ページ
     * @return true:カテゴリSEQパス(画面受渡し用)が存在する
     */
    protected boolean isPreviousCategorySeqPath(CategoryListModel model) {
        return StringUtil.isNotEmpty(model.getCategorySeqPathTarget());
    }

    /**
     * 抽出カテゴリー用の値をページにセット<br/>
     *
     * @param model カテゴリ管理：カテゴリ一覧ページ
     */
    public void setExtractCategory(CategoryListModel model) {

        // html上のextractCategoryNameItemsのlimit値を取得
        int extractCategoryLimit = getLimitForExtractCategoryNameItems(model);

        // limit値が1以上であれば、抽出カテゴリーを表示
        if (1 <= extractCategoryLimit) {
            model.setExtractCategory(true);
        } else {
            model.setExtractCategory(false);
        }

        // limit値をページセット
        model.setExtractCategoryLimit(extractCategoryLimit);
    }

    /**
     * html上のextractCategoryNameItemsのlimit値を取得<br/>
     *
     * @param model カテゴリ管理：カテゴリ一覧ページ
     * @return extractCategoryNameItemsのlimit値
     */
    protected int getLimitForExtractCategoryNameItems(CategoryListModel model) {
        String limit = "0";
        // 空の場合、0を返す
        if (StringUtil.isEmpty(limit)) {
            return 0;
        }

        // limitの値が存在する場合、int型に変換
        return conversionUtility.toInteger(limit);
    }

    /**
     * カテゴリのプルダウン作成<br/>
     *
     * @param categoryDto     カテゴリDTO
     * @param model           カテゴリ管理：カテゴリ一覧ページ
     * @param maxHierarchical 最大表示階層
     */
    public void createPulldown(CategoryDto categoryDto, CategoryListModel model, Integer maxHierarchical) {

        // 子カテゴリ(TOP以降の一階層目の子カテゴリ)が存在しなければ、プルダウンは作成しない。
        if (CollectionUtil.isEmpty(categoryDto.getCategoryDtoList())) {
            return;
        }

        Map<String, String> mapforPulldown = new LinkedHashMap<>();
        Map<String, String> tmpMap = new HashMap<>();

        // 親カテゴリであるTOPを先に、tmpMapにカテゴリー名を追加
        CategoryEntity parentCategoryEntity = categoryDto.getCategoryEntity();
        tmpMap.put(parentCategoryEntity.getCategorySeqPath(), parentCategoryEntity.getCategoryName());

        // 子カテゴリ(TOP以降の一階層目の子カテゴリ)が存在すれば、プルダウン用mapを作成
        if (CollectionUtil.isNotEmpty(categoryDto.getCategoryDtoList())) {
            mapforPulldown = createMapForPulldown(categoryDto, mapforPulldown, tmpMap, maxHierarchical);
        }

        // ページにセット
        model.setExtractCategoryNameItems(mapforPulldown);

    }

    /**
     * 再帰的に呼び出し、プルダウン用mapを作成<br/>
     *
     * @param categoryDto     カテゴリDTO
     * @param mapforPulldown  プルダウン用作成Map
     * @param tmpMap          カテゴリー名格納用
     * @param maxHierarchical 最大表示階層
     * @return mapforPulldown プルダウン用作成Map
     */
    protected Map<String, String> createMapForPulldown(CategoryDto categoryDto,
                                                       Map<String, String> mapforPulldown,
                                                       Map<String, String> tmpMap,
                                                       int maxHierarchical) {

        for (CategoryDto childCategoryDto : categoryDto.getCategoryDtoList()) {

            // 子カテゴリDtoから子カテゴリEntityを取得
            CategoryEntity childCategoryEntity = childCategoryDto.getCategoryEntity();

            // tmpMapのKey用として、自身のcategoryseqを含めないカテゴリパスを取得
            String keyMap = getParentSeqPath(childCategoryEntity.getCategorySeqPath());

            // 「tmpMapに保存したカテゴリー名」と「子カテゴリのカテゴリー名」を結合
            String combinedCategoryName = tmpMap.get(keyMap) + PULLDOWN_SPLIT + childCategoryEntity.getCategoryName();

            // tmpMapに「結合させたカテゴリー名」を追加
            tmpMap.put(childCategoryEntity.getCategorySeqPath(), combinedCategoryName);

            // 指定された最大表示階層のみ、プルダウン用のマップに「結合させたカテゴリー名」を追加
            // // Map<categoryId,結合させたカテゴリー名>
            if (childCategoryEntity.getCategoryLevel() == maxHierarchical) {
                mapforPulldown.put(childCategoryEntity.getCategoryId(), combinedCategoryName);
            }

            // 子カテゴリDtoに、もう一階層の子カテゴリが存在すれば、再帰的に呼び出す
            if (CollectionUtil.isNotEmpty(childCategoryDto.getCategoryDtoList())) {
                createMapForPulldown(childCategoryDto, mapforPulldown, tmpMap, maxHierarchical);
            }
        }

        return mapforPulldown;
    }

    /**
     * カテゴリツリーの生成
     *
     * @param categoryDto       カテゴリDTO
     * @param model             カテゴリ管理：カテゴリ一覧ページ
     * @param categoryEntityMap カテゴリ商品数DTOのMAP
     */
    public void createNode(CategoryDto categoryDto,
                           CategoryListModel model,
                           Map<Integer, CategoryEntity> categoryEntityMap) {

        List<CategoryModelItem> modelItem = new ArrayList<>();
        String categorySeqPath = null;
        Set<Integer> parentSeqSet = null;
        Set<String> parentLevelSet = null;

        // カテゴリSEQパス(画面受渡し用)が存在する場合
        if (isPreviousCategorySeqPath(model)) {

            if (model.isDeleteCategory()) {
                // 「削除」ボタン押下時、カテゴリSEQパス(画面受渡し用)そのまま格納
                categorySeqPath = model.getCategorySeqPathTarget();
            } else {
                // 「戻る」ボタン押下時、親カテゴリーのカテゴリーSEQパスを取得
                categorySeqPath = getParentSeqPath(model.getCategorySeqPathTarget());
            }

            // カテゴリーSEQパスをカテゴリーSEQごとに分割しsetに追加(画面受渡し用)
            parentSeqSet = splitCategorySeqPath(categorySeqPath);
            // カテゴリーSEQパスを階層ごとにsetに追加(画面受渡し用)
            parentLevelSet = getCategorySeqSetByLevel(categorySeqPath);
        }

        // 再帰処理にてカテゴリツリーを生成
        allNode(categoryDto, model, categoryEntityMap, modelItem, parentSeqSet, parentLevelSet, categorySeqPath);

        // PageInfoHelper取得
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        // ページングセットアップ
        categoryDto = pageInfoHelper.setupPageInfoForSkipCount(categoryDto, PagerCondition.NONE_LIMIT);

        model.setResultItems(modelItem);
    }

    /**
     * 再帰処理にてカテゴリツリーを生成
     *
     * @param categoryDto       カテゴリDTO
     * @param model             カテゴリ管理：カテゴリ一覧ページ
     * @param categoryEntityMap カテゴリごとに紐づく商品数DTOマップ
     * @param list              カテゴリツリー
     * @param parentSeqSet      親カテゴリーSEQパスSet(画面受渡し用)
     * @param parentLevelSet    親カテゴリーSEQ階層パスSet(画面受渡し用)
     * @param categorySeqPath   カテゴリーSEQパス
     */
    protected void allNode(CategoryDto categoryDto,
                           CategoryListModel model,
                           Map<Integer, CategoryEntity> categoryEntityMap,
                           List<CategoryModelItem> list,
                           Set<Integer> parentSeqSet,
                           Set<String> parentLevelSet,
                           String categorySeqPath) {

        // 戻り値の作成
        CategoryEntity categoryEntity = ApplicationContextUtility.getBean(CategoryEntity.class);

        categoryEntity.setOpenGoodsCountPC(
                        categoryEntityMap.get(categoryDto.getCategoryEntity().getCategorySeq()).getOpenGoodsCountPC());
        categoryEntity.setOpenGoodsCountMB(
                        categoryEntityMap.get(categoryDto.getCategoryEntity().getCategorySeq()).getOpenGoodsCountMB());

        model.getCategoryMap().put(categoryDto.getCategoryEntity().getCategoryId(), categoryDto.getCategoryEntity());
        CategoryModelItem modelItem = ApplicationContextUtility.getBean(CategoryModelItem.class);
        // トップはラジオボタンを出力しない
        if (!categoryUtility.getCategoryIdTop().equals(categoryDto.getCategoryEntity().getCategoryId())) {
            modelItem.setModify(true);
        }
        modelItem.setCategoryId(categoryDto.getCategoryEntity().getCategoryId());
        modelItem.setCategoryName(categoryDto.getCategoryEntity().getCategoryName());
        modelItem.setCategoryLevel(categoryDto.getCategoryEntity().getCategoryLevel());
        modelItem.setCategoryPath(categoryDto.getCategoryEntity().getCategoryPath());
        modelItem.setCategorySeqPath(categoryDto.getCategoryEntity().getCategorySeqPath());
        modelItem.setOrderDisplay(categoryDto.getCategoryEntity().getOrderDisplay());
        modelItem.setCategoryOpenStatusPC(categoryDto.getCategoryEntity().getCategoryOpenStatusPC());
        modelItem.setCategoryOpenStatusMB(categoryDto.getCategoryEntity().getCategoryOpenStatusMB());
        modelItem.setSiteMapFlag(categoryDto.getCategoryEntity().getSiteMapFlag());
        modelItem.setVersionNo(categoryDto.getCategoryEntity().getVersionNo());

        // カテゴリー一覧表示の制御
        setCategoryView(model, modelItem);

        // 抽出カテゴリー用カテゴリー一覧表示の制御
        setCategoryViewForExtractCategory(model, modelItem);

        // 「戻る」,「削除」ボタン押下用ボタン押下用カテゴリー一覧表示の制御
        setCategoryViewForPrevious(categoryDto, model, modelItem, parentSeqSet, parentLevelSet);

        // 「削除」ボタン押下用カテゴリー一覧表示の制御
        setCategoryViewForDelete(categoryDto, model, modelItem, categorySeqPath);

        modelItem.setLevelView(makeNodeLayer(categoryDto.getCategoryEntity().getCategoryLevel()));

        // 各カテゴリーごとの開閉ボタン表示・非表示
        setSquareOpenClose(categoryDto, model, modelItem);

        // 全てのカテゴリーの開閉ボタン表示・非表示
        setAllpenClose(categoryDto, model, modelItem);

        list.add(modelItem);

        for (CategoryDto subNode : categoryDto.getCategoryDtoList()) {
            allNode(subNode, model, categoryEntityMap, list, parentSeqSet, parentLevelSet, categorySeqPath);
        }

        modelItem.setCategoryOpenGoodsCountPC(categoryEntity.getOpenGoodsCountPC());
        modelItem.setCategoryOpenGoodsCountMB(categoryEntity.getOpenGoodsCountMB());
    }

    /**
     * カテゴリー一覧表示の制御<br/>
     * <p>
     * setCurrentCategoryView ⇒
     * STYLE_DISPLAY：カテゴリーの表示,STYLE_DISPLAY_NONE:カテゴリーの非表示<br/>
     * setOpenFlg ⇒ true：「-」,false：「+」開閉画像の表示制御<br/>
     *
     * @param model     カテゴリ管理：カテゴリ一覧
     * @param modelItem カテゴリ管理：カテゴリ一覧アイテム
     */
    protected void setCategoryView(CategoryListModel model, CategoryModelItem modelItem) {

        // 抽出カテゴリーを表示する場合、この制御は行わない。
        if (model.isExtractCategory()) {
            return;
        }

        if (modelItem.getCategoryLevel() == 0) {
            // TOP階層
            modelItem.setCategoryViewStyle(STYLE_DISPLAY);
            modelItem.setOpenFlg(true);
        } else if (modelItem.getCategoryLevel() == 1) {
            // 1階層
            modelItem.setCategoryViewStyle(STYLE_DISPLAY);
            modelItem.setOpenFlg(false);
        } else {
            // 2階層以上
            modelItem.setCategoryViewStyle(STYLE_DISPLAY_NONE);
            modelItem.setOpenFlg(false);
        }
    }

    /**
     * 抽出カテゴリー用カテゴリー一覧表示の制御<br/>
     * <p>
     * setCurrentCategoryView ⇒
     * STYLE_DISPLAY：カテゴリーの表示,STYLE_DISPLAY_NONE:カテゴリーの非表示<br/>
     * setOpenFlg ⇒ true：「-」,false：「+」開閉画像の表示制御<br/>
     *
     * @param model     カテゴリ管理：カテゴリ一覧
     * @param modelItem カテゴリ管理：カテゴリ一覧アイテム
     */
    protected void setCategoryViewForExtractCategory(CategoryListModel model, CategoryModelItem modelItem) {

        // 抽出カテゴリーを表示しない場合、この制御は行わない。
        if (!model.isExtractCategory()) {
            return;
        }

        if (modelItem.getCategoryLevel() <= model.getExtractCategoryLimit()) {
            // TOP～「最大表示階層」
            modelItem.setCategoryViewStyle(STYLE_DISPLAY);
            modelItem.setOpenFlg(true);
        } else if (modelItem.getCategoryLevel() == model.getExtractCategoryLimit() + 1) {
            // 「最大表示階層」+1階層
            modelItem.setCategoryViewStyle(STYLE_DISPLAY);
            modelItem.setOpenFlg(false);
        } else {
            // 「最大表示階層」+1階層以降
            modelItem.setCategoryViewStyle(STYLE_DISPLAY_NONE);
            modelItem.setOpenFlg(false);
        }

    }

    /**
     * 「戻る」,「削除」ボタン押下用カテゴリー一覧表示の制御<br/>
     * <p>
     * setCurrentCategoryView ⇒
     * STYLE_DISPLAY：カテゴリーの表示,STYLE_DISPLAY_NONE:カテゴリーの非表示<br/>
     * setOpenFlg ⇒ true：「-」,false：「+」開閉画像の表示制御<br/>
     *
     * @param categoryDto    カテゴリー情報
     * @param model          カテゴリ管理：カテゴリ一覧
     * @param modelItem      カテゴリ管理：カテゴリ一覧アイテム
     * @param parentSeqSet   親カテゴリーSEQパスSet
     * @param parentLevelSet 親カテゴリーSEQ階層パスSet
     */
    protected void setCategoryViewForPrevious(CategoryDto categoryDto,
                                              CategoryListModel model,
                                              CategoryModelItem modelItem,
                                              Set<Integer> parentSeqSet,
                                              Set<String> parentLevelSet) {

        // 「戻る」,「削除」ボタン押下時のカテゴリSEQパス(画面受渡し用)が存在しない場合、この制御は行わない。
        if (!isPreviousCategorySeqPath(model)) {
            return;
        }

        // カテゴリーEntity
        CategoryEntity categoryEntity = categoryDto.getCategoryEntity();

        // 「抽象カテゴリー」 かつ 「limit値」以下のカテゴリーレベルは、この制御は行わない。
        if (model.isExtractCategory() && categoryEntity.getCategoryLevel() <= model.getExtractCategoryLimit()) {
            return;
        }

        // 「戻る」ボタン押下時、選択した「親カテゴリー」が存在した場合、カテゴリーを表示するように制御
        if (parentSeqSet.contains(categoryEntity.getCategorySeq())) {
            modelItem.setCategoryViewStyle(STYLE_DISPLAY);
            modelItem.setOpenFlg(true);
            return;
        }

        // 「戻る」ボタン押下時、選択した「親カテゴリー」に紐づく「子カテゴリー」が存在した場合、カテゴリーを表示するように制御
        if (parentLevelSet.contains(getParentSeqPath(categoryEntity.getCategorySeqPath()))) {
            modelItem.setCategoryViewStyle(STYLE_DISPLAY);
            modelItem.setOpenFlg(false);
            return;
        }
    }

    /**
     * 「削除」ボタン押下用カテゴリー一覧表示の制御<br/>
     * <p>
     * setCurrentCategoryView ⇒
     * STYLE_DISPLAY：カテゴリーの表示,STYLE_DISPLAY_NONE:カテゴリーの非表示<br/>
     * setOpenFlg ⇒ true：「-」,false：「+」開閉画像の表示制御<br/>
     *
     * @param categoryDto     カテゴリー情報
     * @param model           カテゴリ管理：カテゴリ一覧
     * @param modelItem       カテゴリ管理：カテゴリ一覧アイテム
     * @param categorySeqPath カテゴリーSEQパス
     */
    protected void setCategoryViewForDelete(CategoryDto categoryDto,
                                            CategoryListModel model,
                                            CategoryModelItem modelItem,
                                            String categorySeqPath) {

        // 「削除」ボタン押下されていない場合、この制御は行わない。
        if (!model.isDeleteCategory()) {
            return;
        }

        // カテゴリーEntity
        CategoryEntity categoryEntity = categoryDto.getCategoryEntity();

        // 「削除」ボタン押下時、削除したカテゴリーの1階層上の親カテゴリーの場合、カテゴリーを表示するように制御
        if (categorySeqPath.equals(categoryEntity.getCategorySeqPath())) {
            modelItem.setCategoryViewStyle(STYLE_DISPLAY);
            modelItem.setOpenFlg(true);
            return;
        }

        // 「削除」ボタン押下時、削除したカテゴリーと同じ親カテゴリーの場合、カテゴリーを表示するように制御
        if (categorySeqPath.equals(getParentSeqPath(categoryEntity.getCategorySeqPath()))) {
            modelItem.setCategoryViewStyle(STYLE_DISPLAY);
            modelItem.setOpenFlg(false);
            return;
        }

    }

    /**
     * 各カテゴリーごとを開閉する「＋」「－」ボタンの表示・非表示<br/>
     * setLowest ⇒ true：「＋」「－」開閉ボタンを表示させない<br/>
     *
     * @param categoryDto カテゴリー情報
     * @param model       カテゴリ管理：カテゴリ一覧
     * @param modelItem   カテゴリ管理：カテゴリ一覧アイテム
     */
    protected void setSquareOpenClose(CategoryDto categoryDto, CategoryListModel model, CategoryModelItem modelItem) {

        // 抽出カテゴリー表示の場合、TOP階層から最大表示階層未満の「＋」・「－」開閉ボタンは表示させない。
        if (model.isExtractCategory()) {
            if (modelItem.getCategoryLevel() < model.getExtractCategoryLimit()) {
                modelItem.setLowest(true);
                return;
            }
        }

        // 最下階層の子カテゴリである場合、「＋」・「－」開閉ボタンは表示させない。
        if (CollectionUtil.isEmpty(categoryDto.getCategoryDtoList())) {
            modelItem.setLowest(true);
        } else {
            modelItem.setLowest(false);
        }
    }

    /**
     * 全てカテゴリーを開閉する「＋」「－」ボタンの表示・非表示<br/>
     * setLowest ⇒ true：「＋」「－」開閉ボタンを表示させない<br/>
     *
     * @param categoryDto カテゴリー情報
     * @param model       カテゴリ管理：カテゴリ一覧
     * @param modelItem   カテゴリ管理：カテゴリ一覧アイテム
     */
    protected void setAllpenClose(CategoryDto categoryDto, CategoryListModel model, CategoryModelItem modelItem) {

        CategoryEntity categoryEntity = categoryDto.getCategoryEntity();

        // 抽出カテゴリーが表示 かつ limit値と同じ階層の場合、全てカテゴリーを開閉する「＋」「－」ボタンの表示
        if (model.isExtractCategory() && categoryEntity.getCategoryLevel() == model.getExtractCategoryLimit()) {
            modelItem.setAllOpenClose(true);
            return;
        }

        // 抽出カテゴリーが非表示 かつ 階層レベルが「1」の場合、全てカテゴリーを開閉する「＋」「－」ボタンの表示
        if (!model.isExtractCategory() && categoryEntity.getCategoryLevel() == 1) {
            modelItem.setAllOpenClose(true);
            return;
        }

        // それ以外は、非表示
        modelItem.setAllOpenClose(false);
    }

    /**
     * カテゴリツリーの階層文字列を作成
     *
     * @param level レベル
     * @return 階層文字列
     */
    protected String makeNodeLayer(Integer level) {
        StringBuilder nodeLayer = new StringBuilder();
        for (int i = 0; i < level; i++) {
            nodeLayer.append("　　　");
        }
        return nodeLayer.toString();
    }

}
