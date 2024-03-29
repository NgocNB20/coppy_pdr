/*
 * Project Name : HIT-MALL4
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ImageUploadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category.validation.group.NextGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryModifyService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryTableLockService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryTreeNodeGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CategoryUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.ImageUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * カテゴリ管理 : カテゴリ入力
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */

@RequestMapping("/goods/category")
@Controller
@SessionAttributes(value = "categoryInputModel")
@PreAuthorize("hasAnyAuthority('GOODS:8')")
public class CategoryInputController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryInputController.class);

    /**
     * CategoryUtility
     */
    private final CategoryUtility categoryUtility;

    /**
     * カテゴリ登録Helper
     */
    private final CategoryInputHelper inputHelper;

    /**
     * カテゴリ木構造取得サービス
     */
    private final CategoryTreeNodeGetService categoryTreeNodeGetService;

    /**
     * カテゴリ取得サービス
     */
    private final CategoryGetService categoryGetService;

    /**
     * カテゴリ登録サービス
     */
    private final CategoryRegistService categoryRegistService;

    /**
     * カテゴリ修正サービス
     */
    private final CategoryModifyService categoryModifyService;

    /**
     * カテゴリロックサービス
     */
    private final CategoryTableLockService categoryTableLockService;

    /**
     * コンストラクター
     *
     * @param categoryUtility
     * @param inputHelper
     * @param categoryTreeNodeGetService
     * @param categoryGetService
     * @param categoryRegistService
     * @param categoryModifyService
     * @param categoryTableLockService
     */
    @Autowired
    public CategoryInputController(CategoryUtility categoryUtility,
                                   CategoryInputHelper inputHelper,
                                   CategoryTreeNodeGetService categoryTreeNodeGetService,
                                   CategoryGetService categoryGetService,
                                   CategoryRegistService categoryRegistService,
                                   CategoryModifyService categoryModifyService,
                                   CategoryTableLockService categoryTableLockService) {
        this.categoryUtility = categoryUtility;
        this.inputHelper = inputHelper;
        this.categoryTreeNodeGetService = categoryTreeNodeGetService;
        this.categoryGetService = categoryGetService;
        this.categoryRegistService = categoryRegistService;
        this.categoryModifyService = categoryModifyService;
        this.categoryTableLockService = categoryTableLockService;
    }

    /**
     * 初期表示<br/>
     *
     * @param listScreen
     * @param categoryId
     * @param categoryInputModel
     * @param model
     * @return
     */
    @GetMapping("/input/")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/category/input")
    protected String doLoad(@RequestParam(required = false) Optional<String> listScreen,
                            @RequestParam(required = false) Optional<String> from,
                            @RequestParam(required = false) Optional<String> categoryId,
                            CategoryInputModel categoryInputModel,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        if (from.isPresent() && from.get().equals("confirm")) {
            return "goods/category/input";
        }

        // 自動スクロールのターゲットポジションをリセット
        categoryInputModel.setTargetAutoScrollTagId("");

        // コンポーネント値初期化
        initComponentValue(categoryInputModel);

        // 確認画面からの遷移でなければ
        if (!categoryInputModel.isConfirmScreen()) {
            // モデルのクリア処理
            clearModel(CategoryInputModel.class, categoryInputModel, model);
            initComponentValue(categoryInputModel);
        }

        if (listScreen.isPresent() && categoryId.isPresent()) {
            categoryInputModel.setListScreen(Boolean.parseBoolean(listScreen.get()));
            categoryInputModel.setCategoryId(categoryId.get());
        }

        setTargetCurrentCategory(categoryInputModel);

        // 新規登録の場合
        if (!categoryInputModel.isListScreen() && !categoryInputModel.isConfirmScreen()) {
            inputHelper.registInit(categoryInputModel);
            inputHelper.prerender(categoryInputModel);
            categoryInputModel.setCategorySeqPathTarget(null);
            // 実行前処理
            String check = preDoAction(categoryInputModel, redirectAttributes, model);
            if (StringUtils.isNotEmpty(check)) {
                return check;
            }
            return "goods/category/input";
        }

        // 新規登録で確認画面からの遷移の場合
        if (!categoryInputModel.isListScreen() && categoryInputModel.isConfirmScreen()) {
            inputHelper.sessionInit(categoryInputModel);
            inputHelper.toRegistFromConfirm(categoryInputModel);
            inputHelper.prerender(categoryInputModel);

            // フラグリセット
            categoryInputModel.setConfirmScreen(false);

            // 実行前処理
            String check = preDoAction(categoryInputModel, redirectAttributes, model);
            if (StringUtils.isNotEmpty(check)) {
                return check;
            }
            return "goods/category/input";
        }

        // 修正で一覧画面からの場合
        if (categoryInputModel.isListScreen() && !categoryInputModel.isConfirmScreen()) {

            // TOPカテゴリの場合エラーとする（URL直リンクの場合に発生）
            if (categoryUtility.getCategoryIdTop().equals(categoryInputModel.getCategoryId())) {
                addMessage("AGC000027", redirectAttributes, model);
                return "redirect:/goods/category/";
            }

            CategorySearchForDaoConditionDto conditionDto1 =
                            ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
            conditionDto1.setOrderField("categorypath");
            conditionDto1.setOrderAsc(true);

            conditionDto1.setCategoryId(categoryInputModel.getCategoryId());
            CategoryDto dto1 = categoryTreeNodeGetService.execute(conditionDto1, categoryInputModel.getCategoryId(),
                                                                  HTypeSiteType.BACK
                                                                 );
            if (dto1 == null) {
                addMessage("AGC000017", redirectAttributes, model);
                return "redirect:/goods/category/";
            }
            // 変更前情報
            categoryInputModel.setOriginalCategoryEntity(CopyUtil.deepCopy(dto1.getCategoryEntity()));
            categoryInputModel.setOriginalCategoryDisplayEntity(CopyUtil.deepCopy(dto1.getCategoryDisplayEntity()));

            categoryInputModel.setCategoryDto(dto1);
            categoryInputModel.setScCategorySeq(dto1.getCategoryEntity().getCategorySeq());
            inputHelper.sessionInit(categoryInputModel);
            inputHelper.toModigyFromList(categoryInputModel);

            // 画面受渡し用のカテゴリーSEQパスに追加
            categoryInputModel.setCategorySeqPathTarget(dto1.getCategoryEntity().getCategorySeqPath());
            // 初期表示(カテゴリー一覧ページ)
            categoryInputModel.setInitialDisplayList(true);

            // 実行前処理
            String check = preDoAction(categoryInputModel, redirectAttributes, model);
            if (StringUtils.isNotEmpty(check)) {
                return check;
            }
            return "goods/category/input";
        }

        // 修正で確認画面から遷移の場合
        if (categoryInputModel.isListScreen() && categoryInputModel.isConfirmScreen()) {
            inputHelper.sessionInit(categoryInputModel);
            inputHelper.toModifyFromConfirm(categoryInputModel);
            categoryInputModel.setScCategorySeq(
                            categoryInputModel.getCategoryDto().getCategoryEntity().getCategorySeq());

            // フラグリセット
            categoryInputModel.setConfirmScreen(false);

            // 実行前処理
            String check = preDoAction(categoryInputModel, redirectAttributes, model);
            if (StringUtils.isNotEmpty(check)) {
                return check;
            }
            return "goods/category/input";
        }

        // 実行前処理
        String check = preDoAction(categoryInputModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        return "goods/category/input";
    }

    /**
     * PC画像アップロード<br/>
     *
     * @return class
     */
    @PostMapping(value = "/input/", params = "doUploadImagePC")
    public String doUploadImagePC(@Validated(ImageUploadGroup.class) CategoryInputModel categoryInputModel,
                                  BindingResult error,
                                  RedirectAttributes attributes,
                                  Model model) {

        if (error.hasErrors()) {
            return "goods/category/input";
        }

        // アップロードされている場合のみ
        if (categoryInputModel.getUploadCategoryImagePC() != null) {
            // 画像操作Utility取得
            ImageUtility imageUtility = ApplicationContextUtility.getBean(ImageUtility.class);
            FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);

            // 仮のファイル名
            String tempFileName = imageUtility.createTempImageFileNameExtension(
                            categoryInputModel.getUploadCategoryImagePC().getOriginalFilename(), "");

            // ファイルコピー
            String realTmpFilePath = imageUtility.getRealTempPath() + "/" + tempFileName;

            try {
                if (!fileOperationUtility.put(categoryInputModel.getUploadCategoryImagePC(), realTmpFilePath)) {
                    addMessage("AGC000003", attributes, model);
                    return "goods/category/input";
                }
            } catch (IOException e) {
                LOGGER.error("例外処理が発生しました", e);
                addMessage("AGC000504", attributes, model);
                return "goods/category/input";
            }

            // 一時アップロードされたファイルへのパスへ変更
            String tmpPath = imageUtility.getTempPath() + "/" + tempFileName;
            categoryInputModel.setCategoryImagePathPC(tmpPath);
            categoryInputModel.getCategoryDto().getCategoryDisplayEntity().setCategoryImagePC(tempFileName);
            categoryInputModel.setFileNamePC(categoryInputModel.getUploadCategoryImagePC().getOriginalFilename());
            categoryInputModel.setTmpImagePC(true);
        }

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        categoryInputModel.setTargetAutoScrollTagId("autoScrollCategoryImages");

        return "goods/category/input";
    }

    /**
     * PC画像削除<br/>
     *
     * @return class
     */
    @PostMapping(value = "/input/", params = "doDeleteImagePC")
    public String doDeleteImagePC(CategoryInputModel categoryInputModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        // 実行前処理
        String check = preDoAction(categoryInputModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        categoryInputModel.setCategoryImagePathPC(null);
        categoryInputModel.getCategoryDto().getCategoryDisplayEntity().setCategoryImagePC(null);
        categoryInputModel.setFileNamePC(null);
        categoryInputModel.setTmpImagePC(false);

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        categoryInputModel.setTargetAutoScrollTagId("autoScrollCategoryImages");

        return "goods/category/input";
    }

    /**
     * 次のページ<br/>
     *
     * @param categoryInputModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/input/", params = "doNext")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/category/input")
    public String doNext(@Validated(NextGroup.class) CategoryInputModel categoryInputModel,
                         BindingResult error,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        // 自動スクロールのターゲットポジションをリセット
        categoryInputModel.setTargetAutoScrollTagId("");

        // 実行前処理
        String check = preDoAction(categoryInputModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "goods/category/input";
        }

        // チェックボックスにデフォルト値を設定
        initValueFlag(categoryInputModel);

        // セッション値チェック
        if (categoryInputModel.getCategoryDto().getCategoryEntity() == null) {
            addMessage(CategoryInputModel.MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/goods/category/";
        }

        inputHelper.toDto(categoryInputModel);

        // カテゴリID値チェック
        if (categoryInputModel.getCategoryId() == null) {
            addMessage(CategoryInputModel.MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/goods/category/";
        }

        CategoryEntity categoryEntity = categoryGetService.execute(categoryInputModel.getCategoryId());

        redirectAttributes.addFlashAttribute("isListScreen", categoryInputModel.isListScreen());

        // 新規登録の場合
        if (!categoryInputModel.isListScreen()) {

            // 親カテゴリの選択必須チェック
            if (categoryInputModel.getTarget() == null) {
                throwMessage("AGC000005");
            }

            inputHelper.toRegistNextModel(categoryInputModel);

            CategorySearchForDaoConditionDto conditionDto =
                            ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
            conditionDto.setCategoryId(categoryInputModel.getCategoryDto().getCategoryEntity().getCategoryId());

            // 階層上限チェック(11階層が上限とする:DBの長さよりSeqPathが長ければNG)
            if (categoryInputModel.getTargetParentCategory().getCategorySeqPath().length() + 8 > 100) {
                throwMessage("AGC000025");
            }

            // 重複チェック
            if (categoryEntity != null) {
                throwMessage("AGC000004");
            }
            return "redirect:/goods/category/confirm/";
        }

        inputHelper.toModifyNextModel(categoryInputModel);

        // 修正の場合
        if (categoryEntity != null && !categoryEntity.getCategoryId()
                                                     .equals(categoryInputModel.getTargetParentCategory()
                                                                               .getCategoryId())) {
            throwMessage("AGC000004");
        }

        return "redirect:/goods/category/confirm/";
    }

    /**
     * キャンセル<br/>
     *
     * @param categoryInputModel
     * @param sessionStatus
     * @return
     */
    @PostMapping(value = "/input/", params = "doCancel")
    public String doCancel(CategoryInputModel categoryInputModel,
                           SessionStatus sessionStatus,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        // 実行前処理
        String check = preDoAction(categoryInputModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        categoryInputModel.setTmpImagePC(false);
        categoryInputModel.setFileNamePC(null);
        if (categoryInputModel.getTargetParentCategory() != null) {
            categoryInputModel.getTargetParentCategory().setTmpImagePC(false);
            categoryInputModel.getTargetParentCategory().setFileNamePC(null);
        }
        // カテゴリ一覧画面に戻る
        sessionStatus.setComplete();
        return "redirect:/goods/category/";
    }

    /**
     * アクション実行前処理
     *
     * @param categoryInputModel
     */
    public String preDoAction(CategoryInputModel categoryInputModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        // 不正操作チェック
        return checkIllegalOperation(categoryInputModel, redirectAttributes, model);
    }

    /**
     * カテゴリー一覧移行
     *
     * @param categoryInputModel
     * @return
     */
    @PostMapping(value = "/input/", params = "jumpList")
    public String jumpList(CategoryInputModel categoryInputModel) {
        return "redirect:/goods/category/";
    }

    /**
     * 不正操作チェック
     *
     * @param categoryInputModel
     * @return
     */
    protected String checkIllegalOperation(CategoryInputModel categoryInputModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {
        Integer scCategorySeq = categoryInputModel.getScCategorySeq();
        Integer dbCategorySeq = null;
        if (categoryInputModel.getCategoryDto() != null) {
            dbCategorySeq = categoryInputModel.getCategoryDto().getCategoryEntity().getCategorySeq();
        }

        boolean isError = false;

        if (scCategorySeq == null && dbCategorySeq != null) {
            // 登録画面にも関わらず、カテゴリSEQのDB情報を保持している場合エラー
            isError = true;

        } else if (scCategorySeq != null && dbCategorySeq == null) {
            // 修正画面にも関わらず、カテゴリSEQのDB情報を保持していない場合エラー
            isError = true;

        } else if (scCategorySeq != null && !scCategorySeq.equals(dbCategorySeq)) {
            // 画面用カテゴリSEQとDB用カテゴリSEQが異なる場合エラー
            isError = true;
        }

        if (isError) {
            addMessage(CategoryInputModel.MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/goods/category/";
        }
        return "";
    }

    /**
     * カレントカテゴリを設定<br/>
     *
     * @param categoryInputModel
     */
    protected void setTargetCurrentCategory(CategoryInputModel categoryInputModel) {
        CategorySearchForDaoConditionDto conditionDto2 =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
        conditionDto2.setCategoryId(categoryUtility.getCategoryIdTop());

        conditionDto2.setOrderField("categorypath");
        conditionDto2.setOrderAsc(true);

        CategoryDto dto2 = categoryTreeNodeGetService.execute(conditionDto2, null, HTypeSiteType.BACK);
        categoryInputModel.setCategoryDtoDB(dto2);
    }

    /***********************************************************
     ** カテゴリ確認
     ***********************************************************/
    /**
     * 初期表示<br/>
     *
     * @param categoryInputModel
     * @param model
     * @return
     */
    @GetMapping("/confirm/")
    protected String doLoadConfirm(CategoryInputModel categoryInputModel,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        // セッション値チェック（更新の場合）
        if (categoryInputModel.getCategoryDto() == null) {
            return "redirect:/goods/category/";
        }

        // セッション値チェック（登録の場合）
        if (categoryInputModel.getCategoryDto().getCategoryEntity().getCategoryId() == null) {
            return "redirect:/goods/category/input/?from=confirm";
        }

        inputHelper.init(categoryInputModel);

        // 修正の場合、画面用カテゴリSEQを設定、変更箇所を検出し設定
        if (categoryInputModel.getTargetParentCategory().isListScreen()) {
            categoryInputModel.setScCategorySeq(
                            categoryInputModel.getCategoryDto().getCategoryEntity().getCategorySeq());

            // 入力値からエンティティを作成（変更後データ）
            CategoryEntity modifiedCategoryEntity = categoryInputModel.getCategoryDto().getCategoryEntity();
            CategoryDisplayEntity modifiedCategoryDisplayEntity =
                            categoryInputModel.getCategoryDto().getCategoryDisplayEntity();

            // 変更前データと変更後データの差異リスト作成
            List<String> modifiedCategoryList =
                            DiffUtil.diff(categoryInputModel.getOriginalCategoryEntity(), modifiedCategoryEntity);
            List<String> modifiedCategoryDisplayList =
                            DiffUtil.diff(categoryInputModel.getOriginalCategoryDisplayEntity(),
                                          modifiedCategoryDisplayEntity
                                         );
            categoryInputModel.setModifiedCategoryList(modifiedCategoryList);
            categoryInputModel.setModifiedCategoryDisplayList(modifiedCategoryDisplayList);
        }

        // 実行前処理
        String check = preDoAction(categoryInputModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        return "goods/category/confirm";
    }

    /**
     * 登録処理<br/>
     *
     * @param categoryInputModel
     * @param redirectAttributes
     * @param sessionStatus
     * @param model
     * @return
     */
    @PostMapping(value = "/confirm/", params = "doOnceRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/goods/category/input/")
    public String doOnceRegist(CategoryInputModel categoryInputModel,
                               RedirectAttributes redirectAttributes,
                               SessionStatus sessionStatus,
                               Model model) {

        // 実行前処理
        String check = preDoAction(categoryInputModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        categoryInputModel.setTargetParentCategoryId(null);

        // セッション値チェック
        if (categoryInputModel.getCategoryDto().getCategoryEntity() == null
            || categoryInputModel.getCategoryDto().getCategoryEntity().getCategoryId() == null) {
            return "redirect:/goods/category/input/";
        }

        CategoryEntity categoryEntity = categoryGetService.execute(
                        categoryInputModel.getCategoryDto().getCategoryEntity().getCategoryId());

        // カテゴリテーブルロック
        categoryTableLockService.execute();

        // 新規登録の場合
        if (!categoryInputModel.getTargetParentCategory().isListScreen()) {
            if (categoryEntity != null) {
                addMessage("AGC000004", redirectAttributes, model);
                return "redirect:/goods/category/input/";
            }

            // 階層上限チェック(11階層が上限とする:DBの長さよりSeqPathが長ければNG)
            if (categoryInputModel.getTargetParentCategory().getCategorySeqPath().length() + 8 > 100) {
                throwMessage("AGC000025");
            }

            // 画像ファイルの処理
            inputHelper.fileMovement(categoryInputModel);
            if (categoryRegistService.execute(categoryInputModel.getCategoryDto(),
                                              categoryInputModel.getTargetParentCategory().getCategoryId()
                                             ) != 1) {
                addMessage("AGC000001", redirectAttributes, model);
                categoryInputModel.getTargetParentCategory().setTmpImagePC(false);
                categoryInputModel.getTargetParentCategory().setFileNamePC(null);
                return "goods/category/confirm";
            }
            addInfoMessage("AGC000008", null, redirectAttributes, model);
            categoryInputModel.getTargetParentCategory().setTmpImagePC(false);
            categoryInputModel.getTargetParentCategory().setFileNamePC(null);
            sessionStatus.setComplete();
            return "redirect:/goods/category/input/";
        }

        // 修正の場合
        if (categoryInputModel.getTargetParentCategory().isListScreen()) {
            // 画像ファイルの処理
            inputHelper.fileMovement(categoryInputModel);
            if (categoryModifyService.execute(categoryInputModel.getCategoryDto()) != 1) {
                addMessage("AGC000002", redirectAttributes, model);
                categoryInputModel.getTargetParentCategory().setTmpImagePC(false);
                categoryInputModel.getTargetParentCategory().setFileNamePC(null);
                return "redirect:/goods/category/";
            }

            addInfoMessage("AGC000009", null, redirectAttributes, model);
            categoryInputModel.getTargetParentCategory().setTmpImagePC(false);
            categoryInputModel.getTargetParentCategory().setFileNamePC(null);
            categoryInputModel.setTargetParentCategoryId(categoryInputModel.getTargetParentCategory().getCategoryId());
        }

        sessionStatus.setComplete();
        return "redirect:/goods/category/";
    }

    /**
     * 戻る
     *
     * @param categoryInputModel
     * @param redirectAttributes
     * @return
     */
    @PostMapping(value = "/confirm/", params = "doReturn")
    public String doReturn(CategoryInputModel categoryInputModel, RedirectAttributes redirectAttributes, Model model) {

        // 実行前処理
        String check = preDoAction(categoryInputModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // セッション値チェック
        if (categoryInputModel.getCategoryDto().getCategoryEntity() == null) {
            return "redirect:/goods/category/input/";
        }

        categoryInputModel.setConfirmScreen(true);
        categoryInputModel.setListScreen(categoryInputModel.getTargetParentCategory().isListScreen());

        return "redirect:/goods/category/input/";
    }

    /**
     * コンポーネント値初期化
     *
     * @param categoryInputModel
     */
    private void initComponentValue(CategoryInputModel categoryInputModel) {

        // プルダウンアイテム情報を取得
        categoryInputModel.setCategoryOpenStatusPCItems(EnumTypeUtil.getEnumMap(HTypeOpenStatus.class));
    }

    /**
     * チェックボックスにデフォルト値を設定
     *
     * @param categoryInputModel
     */
    private void initValueFlag(CategoryInputModel categoryInputModel) {
        if (StringUtils.isEmpty(categoryInputModel.getCategoryType())) {
            categoryInputModel.setCategoryType(HTypeCategoryType.RECOMMEND.getValue());
        }
        if (StringUtils.isEmpty(categoryInputModel.getManualDisplayFlag())) {
            categoryInputModel.setManualDisplayFlag(HTypeManualDisplayFlag.OFF.getValue());
        }
        if (StringUtils.isEmpty(categoryInputModel.getSiteMapFlag())) {
            categoryInputModel.setSiteMapFlag(HTypeSiteMapFlag.OFF.getValue());
        }
    }
}
