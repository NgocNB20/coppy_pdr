/*
 * Project Name : HIT-MALL4
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CategoryUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.ImageUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * カテゴリ管理：カテゴリ入力
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class CategoryInputHelper {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryInputHelper.class);

    /**
     * ConversionUtility
     */
    public ConversionUtility conversionUtility;

    /**
     * DateUtility
     */
    public DateUtility dateUtility;

    /**
     * ZenHanConversionUtility
     */
    public ZenHanConversionUtility zenHanConversionUtility;

    /**
     * CategoryUtility
     */
    public CategoryUtility categoryUtility;

    /**
     * FileOperationUtility
     */
    public FileOperationUtility fileOperationUtility;

    /**
     * ImageUtility
     */
    public ImageUtility imageUtility;

    /**
     * コンストラクター
     *
     * @param conversionUtility
     * @param dateUtility
     * @param zenHanConversionUtility
     * @param categoryUtility
     * @param fileOperationUtility
     * @param imageUtility
     */
    @Autowired
    public CategoryInputHelper(ConversionUtility conversionUtility,
                               DateUtility dateUtility,
                               ZenHanConversionUtility zenHanConversionUtility,
                               CategoryUtility categoryUtility,
                               FileOperationUtility fileOperationUtility,
                               ImageUtility imageUtility) {
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
        this.zenHanConversionUtility = zenHanConversionUtility;
        this.categoryUtility = categoryUtility;
        this.fileOperationUtility = fileOperationUtility;
        this.imageUtility = imageUtility;
    }

    /**
     * セッションがある場合の初期値設定<br/>
     *
     * @param model ページ
     */
    public void sessionInit(CategoryInputModel model) {
        CategoryDto dto = model.getCategoryDto();

        model.setCategoryId(dto.getCategoryEntity().getCategoryId());
        model.setCategoryName(dto.getCategoryEntity().getCategoryName());
        model.setCategoryType(dto.getCategoryEntity().getCategoryType().getValue());
        // 2023-renew categoryCSV from here
        model.setCategoryNamePC(dto.getCategoryDisplayEntity().getCategoryNamePC());
        // 2023-renew categoryCSV to here
        model.setMetaDescription(dto.getCategoryDisplayEntity().getMetaDescription());
        model.setMetaKeyword(dto.getCategoryDisplayEntity().getMetaKeyword());
        model.setManualDisplayFlag(dto.getCategoryEntity().getManualDisplayFlag().getValue());
        model.setSiteMapFlag(dto.getCategoryEntity().getSiteMapFlag().getValue());

        model.setCategoryOpenFromDatePC(
                        dateUtility.formatYmdWithSlash(dto.getCategoryEntity().getCategoryOpenStartTimePC()));
        model.setCategoryOpenFromTimePC(dateUtility.formatHMS(dto.getCategoryEntity().getCategoryOpenStartTimePC()));
        model.setCategoryOpenToDatePC(
                        dateUtility.formatYmdWithSlash(dto.getCategoryEntity().getCategoryOpenEndTimePC()));
        model.setCategoryOpenToTimePC(dateUtility.formatHMS(dto.getCategoryEntity().getCategoryOpenEndTimePC()));

        model.setCategoryOpenStatusPC(dto.getCategoryEntity().getCategoryOpenStatusPC().getValue());
        model.setCategoryOpenStatusMB(dto.getCategoryEntity().getCategoryOpenStatusMB().getValue());

        model.setFreeTextPC(dto.getCategoryDisplayEntity().getFreeTextPC());

        model.setCategoryImagePC(dto.getCategoryDisplayEntity().getCategoryImagePC());
        model.setFileNamePC(dto.getCategoryDisplayEntity().getCategoryImagePC());

        if (model.getTargetParentCategory() != null) {
            model.setTmpImagePC(model.getTargetParentCategory().isTmpImagePC());
        }

        // PCテンプ画像がある場合は、テンプ画像を表示
        if (model.isTmpImagePC()) {
            // PC画像が指定されていれば設定
            if (StringUtils.isNotEmpty(model.getCategoryImagePC())) {
                model.setCategoryImagePathPC(categoryUtility.getCategoryImageTempPath(model.getCategoryImagePC()));
            }
        } else {
            // PC画像が指定されていれば設定
            if (StringUtils.isNotEmpty(model.getCategoryImagePC())) {
                model.setCategoryImagePathPC(categoryUtility.getCategoryImagePath(model.getCategoryImagePC()));
            }
        }
    }

    /**
     * 次画面へ遷移のためDTOへ設定<br/>
     *
     * @param model ページ
     */
    public void toDto(CategoryInputModel model) {
        CategoryDto dto = model.getCategoryDto();

        dto.getCategoryEntity().setCategoryId(model.getCategoryId());
        dto.getCategoryEntity().setCategoryName(model.getCategoryName());

        dto.getCategoryEntity()
           .setCategoryOpenStatusPC(
                           EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, model.getCategoryOpenStatusPC()));
        dto.getCategoryEntity()
           .setCategoryOpenStartTimePC(conversionUtility.toTimeStampWithDefaultHms(model.getCategoryOpenFromDatePC(),
                                                                                   model.getCategoryOpenFromTimePC(),
                                                                                   ConversionUtility.DEFAULT_START_TIME
                                                                                  ));

        dto.getCategoryEntity()
           .setCategoryOpenEndTimePC(conversionUtility.toTimeStampWithDefaultHms(model.getCategoryOpenToDatePC(),
                                                                                 model.getCategoryOpenToTimePC(),
                                                                                 ConversionUtility.DEFAULT_END_TIME
                                                                                ));

        dto.getCategoryEntity()
           .setCategoryOpenStatusMB(
                           EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, model.getCategoryOpenStatusMB()));

        dto.getCategoryEntity()
           .setCategoryType(EnumTypeUtil.getEnumFromValue(HTypeCategoryType.class, model.getCategoryType()));
        dto.getCategoryEntity()
           .setManualDisplayFlag(
                           EnumTypeUtil.getEnumFromValue(HTypeManualDisplayFlag.class, model.getManualDisplayFlag()));
        dto.getCategoryEntity()
           .setSiteMapFlag(EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class, model.getSiteMapFlag()));
        // 2023-renew categoryCSV from here
        dto.getCategoryDisplayEntity().setCategoryNamePC(model.getCategoryNamePC());
        // 2023-renew categoryCSV to here
        dto.getCategoryDisplayEntity().setFreeTextPC(model.getFreeTextPC());
        dto.getCategoryDisplayEntity().setMetaDescription(model.getMetaDescription());
        dto.getCategoryDisplayEntity().setMetaKeyword(model.getMetaKeyword());
    }

    /**
     * 初期値設定(新規登録用)<br/>
     *
     * @param inputmodel ページ
     */
    public void registInit(CategoryInputModel inputmodel) {
        CategoryDto categoryDto = ApplicationContextUtility.getBean(CategoryDto.class);
        CategoryEntity categoryEntity = ApplicationContextUtility.getBean(CategoryEntity.class);
        CategoryDisplayEntity categoryDisplayEntity = ApplicationContextUtility.getBean(CategoryDisplayEntity.class);
        categoryDto.setCategoryEntity(categoryEntity);
        categoryDto.setCategoryDisplayEntity(categoryDisplayEntity);
        inputmodel.setCategoryDto(categoryDto);
    }

    /**
     * 修正で一覧からきた場合の設定(修正用)<br/>
     *
     * @param inputmodel ページ
     */
    public void toModigyFromList(CategoryInputModel inputmodel) {
        CategoryTreeItem categoryTreeItems = ApplicationContextUtility.getBean(CategoryTreeItem.class);
        String categoryPathName =
                        makeNodeName(inputmodel, inputmodel.getCategoryDto().getCategoryEntity().getCategorySeqPath());
        categoryTreeItems.setListScreen(inputmodel.isListScreen());
        categoryTreeItems.setCategoryId(inputmodel.getCategoryDto().getCategoryEntity().getCategoryId());
        categoryTreeItems.setCategoryPathName(categoryPathName);
        inputmodel.setCategoryPathName(categoryPathName);
        inputmodel.setTargetParentCategory(categoryTreeItems);
    }

    /**
     * 新規登録で確認からきた場合の設定(新規登録用)<br/>
     *
     * @param inputmodel ページ
     */
    public void toRegistFromConfirm(CategoryInputModel inputmodel) {
        inputmodel.setTarget(inputmodel.getTargetParentCategory().getValue());
    }

    /**
     * 修正で確認からきた場合の設定(修正用)<br/>
     *
     * @param inputmodel ページ
     */
    public void toModifyFromConfirm(CategoryInputModel inputmodel) {
        inputmodel.setCategoryTree(inputmodel.getTargetParentCategory().getValue());
        inputmodel.setCategoryPathName(inputmodel.getTargetParentCategory().getCategoryPathName());
        inputmodel.setFileNamePC(inputmodel.getTargetParentCategory().getFileNamePC());
    }

    /**
     * 次画面へ遷移のための設定(新規登録用)<br/>
     *
     * @param inputmodel ページ
     */
    public void toRegistNextModel(CategoryInputModel inputmodel) {
        inputmodel.setTargetParentCategory(inputmodel.getCategoryTreeItems().get(inputmodel.getTarget()));
        inputmodel.getTargetParentCategory().setValue(inputmodel.getTarget());
        inputmodel.getTargetParentCategory()
                  .setCategoryPathName(
                                  makeNodeName(inputmodel, inputmodel.getTargetParentCategory().getCategorySeqPath()));
        inputmodel.getTargetParentCategory().setTmpImagePC(inputmodel.isTmpImagePC());
        inputmodel.getTargetParentCategory().setFileNamePC(inputmodel.getFileNamePC());
    }

    /**
     * 次画面へ遷移のための設定(修正用)<br/>
     *
     * @param inputmodel ページ
     */
    public void toModifyNextModel(CategoryInputModel inputmodel) {
        inputmodel.getTargetParentCategory().setTmpImagePC(inputmodel.isTmpImagePC());
        inputmodel.getTargetParentCategory().setFileNamePC(inputmodel.getFileNamePC());
    }

    /**
     * カテゴリツリーの生成
     *
     * @param inputmodel ページ
     */
    public void prerender(CategoryInputModel inputmodel) {
        List<CategoryTreeItem> categoryTreeItems = new ArrayList<>();
        int[] currentIndex = {0};
        /** テンプ用 */
        allNode(inputmodel.getCategoryDtoDB(), categoryTreeItems, currentIndex);
        inputmodel.setCategoryTreeItems(categoryTreeItems);
    }

    /**
     * 連結文字を生成し設定<br/>
     *
     * @param inputmodel      ページ
     * @param categorySeqPath カテゴリSEQパス
     * @return カテゴリ連結文字
     */
    protected String makeNodeName(CategoryInputModel inputmodel, String categorySeqPath) {
        Iterator<String> ite = divide(categorySeqPath).iterator();
        StringBuilder buff = new StringBuilder();
        if (inputmodel.isListScreen()) {
            // 更新画面用
            while (ite.hasNext()) {
                String categorySeq = ite.next();
                if (!categorySeq.equals(
                                categorySeqPath.substring(categorySeqPath.length() - 8, categorySeqPath.length()))) {
                    allNodeName(inputmodel.getCategoryDtoDB(), categorySeq, buff);
                }
            }
            return buff.toString();
        }
        // 登録画面用
        while (ite.hasNext()) {
            allNodeName(inputmodel.getCategoryDtoDB(), ite.next(), buff);
        }
        return buff.toString();
    }

    /**
     * 再帰処理にてカテゴリツリーを生成
     *
     * @param categoryDto  カテゴリDTO
     * @param list         ツリーリスト
     * @param currentIndex カレントインデックス
     */
    protected void allNode(CategoryDto categoryDto, List<CategoryTreeItem> list, int[] currentIndex) {
        CategoryTreeItem dto = ApplicationContextUtility.getBean(CategoryTreeItem.class);
        String categorySeqPath = categoryDto.getCategoryEntity().getCategorySeqPath();
        dto.setValue(list.size());
        dto.setCategorySeqPath(categorySeqPath);
        dto.setCategoryId(categoryDto.getCategoryEntity().getCategoryId());
        dto.setTargetValue(currentIndex[0]);
        dto.setCategorySeq(categoryDto.getCategoryEntity().getCategorySeq());
        dto.setTCategoryName(categoryDto.getCategoryEntity().getCategoryName());
        dto.setCategoryLevel(categoryDto.getCategoryEntity().getCategoryLevel());
        list.add(dto);
        currentIndex[0]++;
        for (CategoryDto subNode : categoryDto.getCategoryDtoList()) {
            allNode(subNode, list, currentIndex);
        }
    }

    /**
     * 再帰処理にて連続名称を生成
     *
     * @param categoryDto カテゴリDTO
     * @param categorySeq カテゴリSeqPath
     * @param buff        カテゴリ連結文字列
     */
    protected void allNodeName(CategoryDto categoryDto, String categorySeq, StringBuilder buff) {
        if (categorySeq.equals(categoryDto.getCategoryEntity().getCategorySeq().toString())) {
            if (!categoryUtility.getCategoryIdTop().equals(categoryDto.getCategoryEntity().getCategoryId())) {
                buff.append(" > ");
            }
            buff.append(categoryDto.getCategoryEntity().getCategoryName());
        }
        for (CategoryDto subNode : categoryDto.getCategoryDtoList()) {
            allNodeName(subNode, categorySeq, buff);
        }
    }

    /**
     * カテゴリSEQを分割
     *
     * @param categorySeqPath カテゴリSeqPath
     * @return list カテゴリSEQリスト
     */
    protected List<String> divide(String categorySeqPath) {
        int length = 8;
        int i = 0;
        int j = 8;
        List<String> list = new ArrayList<>();
        if (categorySeqPath != null && categorySeqPath.length() >= length) {
            while (categorySeqPath.length() >= j) {
                list.add(categorySeqPath.substring(i, j));
                i += length;
                j += length;
            }
        }
        return list;
    }

    /**
     * コピー用に半角変換した値を返す
     *
     * @param str 変換対象文字列
     * @return 変換後文字列
     */
    protected String convert(String str) {
        return zenHanConversionUtility.toHankaku(str, new Character[] {'＆', '＜', '＞', '”', '’', '￥'});
    }

    /***********************************************************
     ** カテゴリ確認
     ***********************************************************/
    /**
     * DTOの初期化<br/>
     *
     * @param model ページ
     */
    public void init(CategoryInputModel model) {
        CategoryDto dto = model.getCategoryDto();

        model.setCategoryId(dto.getCategoryEntity().getCategoryId());
        model.setCategoryName(dto.getCategoryEntity().getCategoryName());
        model.setCategoryType(dto.getCategoryEntity().getCategoryType().getValue());
        // 2023-renew categoryCSV from here
        model.setCategoryNamePC(dto.getCategoryDisplayEntity().getCategoryNamePC());
        // 2023-renew categoryCSV to here
        model.setMetaDescription(dto.getCategoryDisplayEntity().getMetaDescription());
        model.setMetaKeyword(dto.getCategoryDisplayEntity().getMetaKeyword());
        model.setManualDisplayFlag(dto.getCategoryEntity().getManualDisplayFlag().getValue());
        model.setSiteMapFlag(dto.getCategoryEntity().getSiteMapFlag().getValue());

        model.setCategoryOpenFromDatePC(
                        dateUtility.formatYmdWithSlash(dto.getCategoryEntity().getCategoryOpenStartTimePC()));
        model.setCategoryOpenFromTimePC(dateUtility.formatHMS(dto.getCategoryEntity().getCategoryOpenStartTimePC()));
        model.setCategoryOpenToDatePC(
                        dateUtility.formatYmdWithSlash(dto.getCategoryEntity().getCategoryOpenEndTimePC()));
        model.setCategoryOpenToTimePC(dateUtility.formatHMS(dto.getCategoryEntity().getCategoryOpenEndTimePC()));

        model.setCategoryOpenStatusPC(dto.getCategoryEntity().getCategoryOpenStatusPC().getValue());
        model.setCategoryOpenStatusMB(dto.getCategoryEntity().getCategoryOpenStatusMB().getValue());

        model.setFreeTextPC(dto.getCategoryDisplayEntity().getFreeTextPC());

        model.setCategoryPathName(model.getTargetParentCategory().getCategoryPathName());

        if (model.getTargetParentCategory() != null) {
            model.setTmpImagePC(model.getTargetParentCategory().isTmpImagePC());
            model.setFileNamePC(model.getTargetParentCategory().getFileNamePC());
            model.setListScreen(model.getTargetParentCategory().isListScreen());
        }

        model.setCategoryImagePC(dto.getCategoryDisplayEntity().getCategoryImagePC());

        // PCテンプ画像がある場合は、テンプ画像を表示
        if (model.isTmpImagePC()) {
            // PC画像が指定されていれば設定
            if (StringUtils.isNotEmpty(model.getCategoryImagePC())) {
                model.setCategoryImagePathPC(categoryUtility.getCategoryImageTempPath(model.getCategoryImagePC()));
            }
        } else {
            // PC画像が指定されていれば設定
            if (StringUtils.isNotEmpty(model.getCategoryImagePC())) {
                model.setCategoryImagePathPC(categoryUtility.getCategoryImagePath(model.getCategoryImagePC()));
            }
        }
    }

    /**
     * 画像ファイルの処理
     *
     * @param model ページ
     */
    public void fileMovement(CategoryInputModel model) {
        String realTmpPath = imageUtility.getRealTempPath();
        String realPath = categoryUtility.getCategoryImageRealPath();

        // PCテンプ画像がある場合は、テンプから画像を移動
        if (model.getTargetParentCategory().isTmpImagePC()) {
            String pcFileName = "p_" + model.getCategoryDto().getCategoryEntity().getCategoryId()
                                + model.getTargetParentCategory()
                                       .getFileNamePC()
                                       .substring(model.getTargetParentCategory().getFileNamePC().lastIndexOf("."));
            putFile(realTmpPath + "/" + model.getCategoryImagePC(), realPath + "/" + pcFileName, true);
            model.getCategoryDto().getCategoryDisplayEntity().setCategoryImagePC(pcFileName);
        }
    }

    /**
     * ファイル移動処理<br/>
     *
     * @param src       移動元ファイル名
     * @param dest      移動先ファイル名
     * @param removeSrc 元ファイル削除有無
     */
    protected void putFile(String src, String dest, boolean removeSrc) {
        try {
            fileOperationUtility.put(src, dest, removeSrc);
        } catch (IOException e) {
            LOGGER.warn("カテゴリー画像のアップロードに失敗しました。(" + src + ")", e);
        }
    }

}
