package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.MessageUtils;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.ValidatorMessage;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.GoodsRegistUpdateModel;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.relation.GoodsRelationListGetForBackService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy.GoodsTogetherBuyGroupListGetForBackService;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理：商品登録更新抽象コントローラー
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public class AbstractGoodsRegistUpdateController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGoodsRegistUpdateController.class);

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "AGG000001";

    /**
     * 商品グループ取得サービス<br/>
     */
    private GoodsGroupGetService goodsGroupGetService;

    /**
     * 関連商品リスト取得サービス（バック用）<br/>
     */
    private GoodsRelationListGetForBackService goodsRelationListGetForBackService;

    // 2023-renew No21 from here
    /**
     * よく一緒に購入されている商品リスト取得サービス（バック用）
     */
    private final GoodsTogetherBuyGroupListGetForBackService goodsTogetherBuyGroupListGetForBackService;
    // 2023-renew No21 to here

    /**
     * コンストラクター
     *
     * @param goodsGroupGetService
     * @param goodsRelationListGetForBackService
     * @param goodsTogetherBuyGroupListGetForBackService
     */
    @Autowired
    public AbstractGoodsRegistUpdateController(GoodsGroupGetService goodsGroupGetService,
                                               GoodsRelationListGetForBackService goodsRelationListGetForBackService,
                                               // 2023-renew No21 from here
                                               GoodsTogetherBuyGroupListGetForBackService goodsTogetherBuyGroupListGetForBackService) {
        // 2023-renew No21 to here
        this.goodsGroupGetService = goodsGroupGetService;
        this.goodsRelationListGetForBackService = goodsRelationListGetForBackService;
        // 2023-renew No21 from here
        this.goodsTogetherBuyGroupListGetForBackService = goodsTogetherBuyGroupListGetForBackService;
        // 2023-renew No21 to here
    }

    /**
     * 商品登録更新画面共通<br/>
     * 初期表示用メソッド<br/>
     *
     * @param abstModel  商品登録更新（商品基本設定）ページ
     * @param abstHelper 商品登録更新（商品基本設定）ページDxo
     * @return エラーの場合のみエラーページを返す
     */
    protected String loadPage(AbstractGoodsRegistUpdateModel abstModel,
                              AbstractGoodsRegistUpdateHelper abstHelper,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // 商品詳細ページからの遷移時、または商品詳細への戻り時
        if (abstModel.getRedirectGoodsGroupCode() != null && !abstModel.isInputingFlg()) {
            abstModel.setGoodsGroupCode(abstModel.getRedirectGoodsGroupCode());
            abstModel.setRecycleFlg(abstModel.getRedirectRecycle());
        }

        // パラメータの商品コードありで登録更新画面に新しく来た場合、あるいはパラメータの商品コードが変更された場合
        if ((abstModel.getGoodsGroupCode() != null && !"".equals(abstModel.getGoodsGroupCode())
             && !abstModel.isInputingFlg()) || (abstModel.getGoodsGroupCode() != null && !"".equals(
                        abstModel.getGoodsGroupCode()) && !abstModel.getGoodsGroupCode()
                                                                    .equals(abstModel.getGoodsGroupDto()
                                                                                     .getGoodsGroupEntity()
                                                                                     .getGoodsGroupCode()))) {
            // 初期処理（更新または再利用登録）
            String selectGoodsGroupCode = abstModel.getGoodsGroupCode();
            abstModel.setGoodsGroupCode(""); // 商品グループが見つからないとき用にクリアしておく
            GoodsGroupDto goodsGroupDto = goodsGroupGetService.execute(selectGoodsGroupCode, HTypeSiteType.BACK);
            if (goodsGroupDto == null) {
                // 商品グループコードなしエラー
                addMessage(MSGCD_ILLEGAL_OPERATION, new Object[] {selectGoodsGroupCode}, redirectAttributes, model);
                return "redirect:/error";
            }

            abstHelper.initGoodsGroupDto(abstModel, goodsGroupDto);
            abstModel.setGoodsRelationEntityList(goodsRelationListGetForBackService.execute(
                            abstModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq()));
            // 2023-renew No21 from here
            abstModel.setGoodsTogetherBuyGroupEntityList(goodsTogetherBuyGroupListGetForBackService.execute(
                            abstModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq()));
            // 2023-renew No21 to here

            if (abstModel.getRecycleFlg() == null) {
                // 通常登録または更新時
                abstModel.setRegistFlg(false);
                abstModel.setGoodsGroupCode(selectGoodsGroupCode);
            } else {
                // 再利用登録時
                abstModel.setRegistFlg(true);
                abstModel.setRecycledGoodsGroupCode(abstModel.getRedirectGoodsGroupCode());
                abstModel.getGoodsGroupDto().getGoodsGroupEntity().setGoodsGroupSeq(null);
                abstModel.getGoodsGroupDto().getGoodsGroupEntity().setRegistTime(null);
                abstModel.getGoodsGroupDto().getGoodsGroupEntity().setUpdateTime(null);
                abstModel.getGoodsGroupDto().getGoodsGroupEntity().setWhatsnewDate(null);

                if (abstModel.getGoodsGroupDto().getGoodsDtoList() != null) {
                    List<GoodsDto> newGoodsDtoList = new ArrayList<>();
                    // 販売状態="削除"の商品はコピーしない
                    for (GoodsDto goodsDto : abstModel.getGoodsGroupDto().getGoodsDtoList()) {
                        if (HTypeGoodsSaleStatus.DELETED != goodsDto.getGoodsEntity().getSaleStatusPC()) {
                            goodsDto.getGoodsEntity().setGoodsGroupSeq(null);
                            goodsDto.getGoodsEntity().setGoodsSeq(null);
                            // 再利用時の実在庫には「0」をセット
                            goodsDto.getStockDto().setRealStock(new BigDecimal(0));
                            newGoodsDtoList.add(goodsDto);
                        }
                    }
                    abstModel.getGoodsGroupDto().setGoodsDtoList(newGoodsDtoList);
                }
                abstModel.getGoodsGroupDto().setGoodsGroupImageEntityList(new ArrayList<>());

            }
            abstModel.setInputingFlg(true);
            // 戻り先ページ設定
            abstModel.setStoredBackPage(abstModel.getBackPage());
        }
        // パラメータの商品コードなしで登録更新画面に新しく来た場合、あるいはパラメータ商品コードありから商品コードなしになった場合、サイドバー、メニューバーの商品新規登録から来た場合
        else if (abstModel.getGoodsGroupCode() == null && !abstModel.isInputingFlg() || (
                        (abstModel.getGoodsGroupCode() == null || "".equals(abstModel.getGoodsGroupCode())) && (
                                        abstModel.getGoodsGroupDto().getGoodsGroupEntity() != null
                                        && abstModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupCode()
                                           != null)) || ("menu".equals(abstModel.getFrom())
                                                         && (abstModel instanceof GoodsRegistUpdateModel))) {
            GoodsGroupDto goodsGroupDto = ApplicationContextUtility.getBean(GoodsGroupDto.class);
            // goodsGroupDto.taxRateItems = taxRateLogic.getEffectTaxRate();
            abstModel.setGoodsGroupDto(goodsGroupDto);
            // 初期処理（新規商品登録）
            abstHelper.initGoodsGroupDto(abstModel);
            // 初期処理（関連商品初期設定）
            abstModel.setGoodsRelationEntityList(new ArrayList<GoodsRelationEntity>());
            // 2023-renew No21 from here
            abstModel.setGoodsTogetherBuyGroupEntityList(new ArrayList<GoodsTogetherBuyGroupEntity>());
            // 2023-renew No21 to here
            // 初期処理（商品グループ画像）
            abstModel.setGoodsGroupImageRegistUpdateDtoList(null);
            // 初期処理（商品規格画像）
            abstModel.setRegistFlg(true);
            abstModel.setInputingFlg(true);
            // 戻り先ページ設定
            abstModel.setStoredBackPage(abstModel.getBackPage());
        }
        // 何らかの理由によりセッション情報がなくなった場合
        else if ((abstModel.getGoodsGroupCode() == null || "".equals(abstModel.getGoodsGroupCode()))
                 && !abstModel.isInputingFlg()) {
            // 新規商品と同じデータを生成
            abstHelper.initGoodsGroupDto(abstModel);
            abstModel.setGoodsRelationEntityList(new ArrayList<GoodsRelationEntity>());
            // 2023-renew No21 from here
            abstModel.setGoodsTogetherBuyGroupEntityList(new ArrayList<GoodsTogetherBuyGroupEntity>());
            // 2023-renew No21 to here
            abstModel.setRegistFlg(true);
            abstModel.setInputingFlg(true);
        }

        // 修正画面の場合、画面用商品グループSEQを設定
        Integer scGoodsGroupSeq = null;
        try {
            scGoodsGroupSeq = abstModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq();
        } catch (Exception e) {
            // NULLチェックを省く目的のtry-catchであるため、例外キャッチ時のログだけ吐いて処理は行わない
            LOGGER.error("例外処理が発生しました", e);
        }
        if (scGoodsGroupSeq != null) {
            abstModel.setScGoodsGroupSeq(scGoodsGroupSeq);
        }

        return null;
    }

    /**
     * 商品登録更新画面共通<br/>
     * 初期表示用メソッド<br/>
     * Ajax呼び出す際<br/>
     *
     * @param abstModel          商品登録更新（商品基本設定）ページ
     * @param abstHelper         商品登録更新（商品基本設定）ページDxo
     * @param redirectAttributes
     * @return エラーの場合のみエラーページを返す
     */
    protected List<ValidatorMessage> loadPageAjax(AbstractGoodsRegistUpdateModel abstModel,
                                                  AbstractGoodsRegistUpdateHelper abstHelper,
                                                  RedirectAttributes redirectAttributes,
                                                  Model model) {
        List<ValidatorMessage> list = new ArrayList<>();
        // 商品詳細ページからの遷移時、または商品詳細への戻り時
        if (abstModel.getRedirectGoodsGroupCode() != null && !abstModel.isInputingFlg()) {
            abstModel.setGoodsGroupCode(abstModel.getRedirectGoodsGroupCode());
            abstModel.setRecycleFlg(abstModel.getRedirectRecycle());
        }

        // パラメータの商品コードありで登録更新画面に新しく来た場合、あるいはパラメータの商品コードが変更された場合
        if ((abstModel.getGoodsGroupCode() != null && !"".equals(abstModel.getGoodsGroupCode())
             && !abstModel.isInputingFlg()) || (abstModel.getGoodsGroupCode() != null && !"".equals(
                        abstModel.getGoodsGroupCode()) && !abstModel.getGoodsGroupCode()
                                                                    .equals(abstModel.getGoodsGroupDto()
                                                                                     .getGoodsGroupEntity()
                                                                                     .getGoodsGroupCode()))) {
            // 初期処理（更新または再利用登録）
            String selectGoodsGroupCode = abstModel.getGoodsGroupCode();
            abstModel.setGoodsGroupCode(""); // 商品グループが見つからないとき用にクリアしておく
            GoodsGroupDto goodsGroupDto = goodsGroupGetService.execute(selectGoodsGroupCode, HTypeSiteType.BACK);
            if (goodsGroupDto == null) {
                // 商品グループコードなしエラー
                MessageUtils.getAllMessage(list, MSGCD_ILLEGAL_OPERATION, new Object[] {selectGoodsGroupCode});
                return list;
            }

            abstHelper.initGoodsGroupDto(abstModel, goodsGroupDto);
            abstModel.setGoodsRelationEntityList(goodsRelationListGetForBackService.execute(
                            abstModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq()));
            // 2023-renew No21 from here
            abstModel.setGoodsTogetherBuyGroupEntityList(goodsTogetherBuyGroupListGetForBackService.execute(
                            abstModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq()));
            // 2023-renew No21 to here
            if (abstModel.getRecycleFlg() == null) {
                // 通常登録または更新時
                abstModel.setRegistFlg(false);
                abstModel.setGoodsGroupCode(selectGoodsGroupCode);
            } else {
                // 再利用登録時
                abstModel.setRegistFlg(true);
                abstModel.setRecycledGoodsGroupCode(abstModel.getRedirectGoodsGroupCode());
                abstModel.getGoodsGroupDto().getGoodsGroupEntity().setGoodsGroupSeq(null);
                abstModel.getGoodsGroupDto().getGoodsGroupEntity().setRegistTime(null);
                abstModel.getGoodsGroupDto().getGoodsGroupEntity().setUpdateTime(null);
                abstModel.getGoodsGroupDto().getGoodsGroupEntity().setWhatsnewDate(null);

                if (abstModel.getGoodsGroupDto().getGoodsDtoList() != null) {
                    List<GoodsDto> newGoodsDtoList = new ArrayList<>();
                    // 販売状態="削除"の商品はコピーしない
                    for (GoodsDto goodsDto : abstModel.getGoodsGroupDto().getGoodsDtoList()) {
                        if (HTypeGoodsSaleStatus.DELETED != goodsDto.getGoodsEntity().getSaleStatusPC()) {
                            goodsDto.getGoodsEntity().setGoodsGroupSeq(null);
                            goodsDto.getGoodsEntity().setGoodsSeq(null);
                            // 再利用時の実在庫には「0」をセット
                            goodsDto.getStockDto().setRealStock(new BigDecimal(0));
                            newGoodsDtoList.add(goodsDto);
                        }
                    }
                    abstModel.getGoodsGroupDto().setGoodsDtoList(newGoodsDtoList);
                }
                abstModel.getGoodsGroupDto().setGoodsGroupImageEntityList(new ArrayList<>());

            }
            abstModel.setInputingFlg(true);
            // 戻り先ページ設定
            abstModel.setStoredBackPage(abstModel.getBackPage());
        }
        // パラメータの商品コードなしで登録更新画面に新しく来た場合、あるいはパラメータ商品コードありから商品コードなしになった場合、サイドバー、メニューバーの商品新規登録から来た場合
        else if (abstModel.getGoodsGroupCode() == null && !abstModel.isInputingFlg() || (
                        (abstModel.getGoodsGroupCode() == null || "".equals(abstModel.getGoodsGroupCode())) && (
                                        abstModel.getGoodsGroupDto().getGoodsGroupEntity() != null
                                        && abstModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupCode()
                                           != null)) || ("menu".equals(abstModel.getFrom())
                                                         && (abstModel instanceof GoodsRegistUpdateModel))) {
            GoodsGroupDto goodsGroupDto = ApplicationContextUtility.getBean(GoodsGroupDto.class);
            // goodsGroupDto.taxRateItems = taxRateLogic.getEffectTaxRate();
            abstModel.setGoodsGroupDto(goodsGroupDto);
            // 初期処理（新規商品登録）
            abstHelper.initGoodsGroupDto(abstModel);
            // 初期処理（関連商品初期設定）
            abstModel.setGoodsRelationEntityList(new ArrayList<GoodsRelationEntity>());
            // 初期処理（よく一緒に購入されている商品初期設定）
            // 2023-renew No21 from here
            abstModel.setGoodsTogetherBuyGroupEntityList(new ArrayList<GoodsTogetherBuyGroupEntity>());
            // 2023-renew No21 to here
            // 初期処理（商品グループ画像）
            abstModel.setGoodsGroupImageRegistUpdateDtoList(null);
            // 初期処理（商品規格画像）
            abstModel.setRegistFlg(true);
            abstModel.setInputingFlg(true);
            // 戻り先ページ設定
            abstModel.setStoredBackPage(abstModel.getBackPage());
        }
        // 何らかの理由によりセッション情報がなくなった場合
        else if ((abstModel.getGoodsGroupCode() == null || "".equals(abstModel.getGoodsGroupCode()))
                 && !abstModel.isInputingFlg()) {
            // 新規商品と同じデータを生成
            abstHelper.initGoodsGroupDto(abstModel);
            abstModel.setGoodsRelationEntityList(new ArrayList<GoodsRelationEntity>());
            // 2023-renew No21 from here
            abstModel.setGoodsTogetherBuyGroupEntityList(new ArrayList<GoodsTogetherBuyGroupEntity>());
            // 2023-renew No21 to here
            abstModel.setRegistFlg(true);
            abstModel.setInputingFlg(true);
        }

        // 修正画面の場合、画面用商品グループSEQを設定
        Integer scGoodsGroupSeq = null;
        try {
            scGoodsGroupSeq = abstModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq();
        } catch (Exception e) {
            // NULLチェックを省く目的のtry-catchであるため、例外キャッチ時のログだけ吐いて処理は行わない
            LOGGER.error("例外処理が発生しました", e);
        }
        if (scGoodsGroupSeq != null) {
            abstModel.setScGoodsGroupSeq(scGoodsGroupSeq);
        }

        return null;
    }

    /**
     * 不正操作チェック
     *
     * @param abstModel 商品管理：商品登録更新抽象ページ
     */
    protected String checkIllegalOperation(AbstractGoodsRegistUpdateModel abstModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {
        Integer scGoodsGroupSeq = abstModel.getScGoodsGroupSeq();
        Integer dbGoodsGroupSeq = null;
        String goodsGroupCode = null;
        if (abstModel.getGoodsGroupDto() != null && abstModel.getGoodsGroupDto().getGoodsGroupEntity() != null) {
            dbGoodsGroupSeq = abstModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq();
            goodsGroupCode = abstModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupCode();
        }
        String md = abstModel.getMd();
        String recycledGoodsGroupCode = abstModel.getRecycledGoodsGroupCode();

        boolean isError = false;

        // 登録画面にも関わらず、商品グループSEQのDB情報を保持している場合エラー
        if (scGoodsGroupSeq == null && dbGoodsGroupSeq != null) {
            isError = true;

            // 修正画面にも関わらず、商品グループSEQのDB情報を保持していない場合エラー
        } else if (scGoodsGroupSeq != null && dbGoodsGroupSeq == null) {
            isError = true;

            // 画面用商品グループSEQとDB用商品グループSEQが異なる場合エラー
        } else if (scGoodsGroupSeq != null && !scGoodsGroupSeq.equals(dbGoodsGroupSeq)) {
            isError = true;

            // 登録画面にも関わらず、再利用元商品グループコードを保持している場合エラー
        } else if (md == null && recycledGoodsGroupCode != null) {
            isError = true;

            // 再利用画面にも関わらず、商品グループコードを保持していない場合エラー
        } else if (recycledGoodsGroupCode != null && goodsGroupCode == null) {
            isError = true;
        }

        if (isError) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/goods/";
        }
        return null;
    }

    /**
     * 商品情報整合性チェック<br/>
     *
     * @param abstModel 商品登録更新ページ
     * @return エラーの場合のみエラーページを返す
     */
    public String checkPageGoodsGroupEntity(AbstractGoodsRegistUpdateModel abstModel,
                                            RedirectAttributes redirectAttributes,
                                            Model model) {
        if (abstModel.getGoodsGroupDto().getGoodsGroupEntity() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/goods/";
        }
        return null;
    }

}
