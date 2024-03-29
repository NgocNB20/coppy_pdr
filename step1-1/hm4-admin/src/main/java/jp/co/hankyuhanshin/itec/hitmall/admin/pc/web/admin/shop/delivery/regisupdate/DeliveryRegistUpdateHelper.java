package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.regisupdate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.EnumType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDispDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShortfallDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.BigDecimalConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 配送方法登録・更新入力画面Helper
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
public class DeliveryRegistUpdateHelper {
    /**
     * 都道府県別送料表示アイテムリスト作成
     *
     * @param deliveryRegistUpdateModel 配送方法登録・更新Model
     * @param deliveryMethodDetailsDto  配送方法詳細DTO
     */
    public void toPageForLoadIndex(DeliveryRegistUpdateModel deliveryRegistUpdateModel,
                                   DeliveryMethodDetailsDto deliveryMethodDetailsDto) {

        boolean registMode = false;
        DeliveryMethodEntity deliveryMethodEntity = null;
        List<DeliveryMethodTypeCarriageEntity> deliveryMethodTypeCarriageEntityList = null;

        // 登録モード
        if (deliveryMethodDetailsDto.getDeliveryMethodEntity() == null) {
            registMode = true;
        } else {
            deliveryMethodEntity = deliveryMethodDetailsDto.getDeliveryMethodEntity();
            deliveryMethodTypeCarriageEntityList = deliveryMethodDetailsDto.getDeliveryMethodTypeCarriageEntityList();
        }

        deliveryRegistUpdateModel.setDeliveryMethodTypeItems(
                        EnumTypeUtil.getEnumMap(HTypeDispDeliveryMethodType.class));
        deliveryRegistUpdateModel.setOpenStatusPCItems(EnumTypeUtil.getEnumMap(HTypeOpenStatus.class));

        // 登録モード
        if (registMode) {
            deliveryRegistUpdateModel.setDeliveryMethodType(HTypeDeliveryMethodType.FLAT.getValue());
            deliveryRegistUpdateModel.setDeliverySpecialChargeAreaCount(0);
            deliveryRegistUpdateModel.setDeliveryImpossibleAreaCount(0);
            deliveryRegistUpdateModel.setEqualsCarriage("");
            deliveryRegistUpdateModel.setLargeAmountDiscountPrice("");
            deliveryRegistUpdateModel.setLargeAmountDiscountCarriage("");

        } else {
            // 変換Helper取得
            ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
            deliveryRegistUpdateModel.setDeliveryMethodId(deliveryMethodEntity.getDeliveryMethodSeq().toString());
            deliveryRegistUpdateModel.setDeliveryMethodName(deliveryMethodEntity.getDeliveryMethodName());
            deliveryRegistUpdateModel.setDeliveryMethodType(deliveryMethodEntity.getDeliveryMethodType().getValue());
            deliveryRegistUpdateModel.setDeliveryMethodTypeName(
                            deliveryMethodEntity.getDeliveryMethodType().getLabel());

            // 配送追跡URL、配送追跡URLの表示期間に値をセット
            deliveryRegistUpdateModel.setDeliveryChaseURL("");
            deliveryRegistUpdateModel.setDeliveryChaseURLDisplayPeriod("");
            if (StringUtil.isNotEmpty(deliveryMethodEntity.getDeliveryChaseURL())) {
                deliveryRegistUpdateModel.setDeliveryChaseURL(deliveryMethodEntity.getDeliveryChaseURL());
            }
            if (deliveryMethodEntity.getDeliveryChaseURLDisplayPeriod() != null) {
                deliveryRegistUpdateModel.setDeliveryChaseURLDisplayPeriod(
                                conversionUtility.toString(deliveryMethodEntity.getDeliveryChaseURLDisplayPeriod()));
            }

            deliveryRegistUpdateModel.setOpenStatusPC(deliveryMethodEntity.getOpenStatusPC().getValue());
            deliveryRegistUpdateModel.setDeliveryMethodDisplayNamePC(deliveryMethodEntity.getDeliveryMethodName());
            deliveryRegistUpdateModel.setDeliveryMethodDisplayNameMB(
                            deliveryMethodEntity.getDeliveryMethodDisplayNameMB());
            deliveryRegistUpdateModel.setDeliveryNotePC(deliveryMethodEntity.getDeliveryNotePC());
            deliveryRegistUpdateModel.setDeliveryNoteMB(deliveryMethodEntity.getDeliveryNoteMB());
            deliveryRegistUpdateModel.setDeliveryLeadTime(
                            conversionUtility.toString(deliveryMethodEntity.getDeliveryLeadTime()));
            deliveryRegistUpdateModel.setPossibleSelectDays(
                            conversionUtility.toString(deliveryMethodEntity.getPossibleSelectDays()));
            deliveryRegistUpdateModel.setReceiverTimeZone1(deliveryMethodEntity.getReceiverTimeZone1());
            deliveryRegistUpdateModel.setReceiverTimeZone2(deliveryMethodEntity.getReceiverTimeZone2());
            deliveryRegistUpdateModel.setReceiverTimeZone3(deliveryMethodEntity.getReceiverTimeZone3());
            deliveryRegistUpdateModel.setReceiverTimeZone4(deliveryMethodEntity.getReceiverTimeZone4());
            deliveryRegistUpdateModel.setReceiverTimeZone5(deliveryMethodEntity.getReceiverTimeZone5());
            deliveryRegistUpdateModel.setReceiverTimeZone6(deliveryMethodEntity.getReceiverTimeZone6());
            deliveryRegistUpdateModel.setReceiverTimeZone7(deliveryMethodEntity.getReceiverTimeZone7());
            deliveryRegistUpdateModel.setReceiverTimeZone8(deliveryMethodEntity.getReceiverTimeZone8());
            deliveryRegistUpdateModel.setReceiverTimeZone9(deliveryMethodEntity.getReceiverTimeZone9());
            deliveryRegistUpdateModel.setReceiverTimeZone10(deliveryMethodEntity.getReceiverTimeZone10());

            if (deliveryMethodEntity.getDeliveryMethodType().equals(HTypeDeliveryMethodType.FLAT)) {
                deliveryRegistUpdateModel.setEqualsCarriage(deliveryMethodEntity.getEqualsCarriage().toString());
            }

            if (!deliveryMethodEntity.getDeliveryMethodType().equals(HTypeDeliveryMethodType.AMOUNT)
                && deliveryMethodEntity.getLargeAmountDiscountPrice() != null) {
                // 高額割引送料,高額割引手数料の両方に「0」が設定されている場合
                // 入力エリアには値を出力しない
                // 【理由】
                // 高額割引設定を未設定とし、配送方法の登録を行った場合、「高額割引下限金額,高額割引手数料」ともに「0」でDBに登録される。
                // そのため、配送方法を更新した際に入力エリアに「0」が出力される。
                // この場合、「[0]円以上購入すると送料[0]円」と画面に出力され
                // 高額割引を設定していないはずが、0円以上だと送料が無料になる設定になると誤解を受ける可能性が高い。
                // また、「決済方法設定」画面では高額割引が未設定の場合は入力エリアが「未入力」となる。
                // 高額割引が設定されていない場合は入力エリアを「未入力」とし、仕様の統一を行う
                if (!deliveryMethodEntity.getLargeAmountDiscountPrice().equals(BigDecimal.ZERO) || !deliveryMethodEntity
                                .getLargeAmountDiscountCarriage()
                                .equals(BigDecimal.ZERO)) {
                    deliveryRegistUpdateModel.setLargeAmountDiscountPrice(
                                    deliveryMethodEntity.getLargeAmountDiscountPrice().toString());
                }
            }

            if (!deliveryMethodEntity.getDeliveryMethodType().equals(HTypeDeliveryMethodType.AMOUNT)
                && deliveryMethodEntity.getLargeAmountDiscountCarriage() != null) {
                // 高額割引送料,高額割引手数料の両方に「0」が設定されている場合
                // 入力エリアには値を出力しない。
                // 【理由】
                // ↑上記に記載
                if (!deliveryMethodEntity.getLargeAmountDiscountPrice().equals(BigDecimal.ZERO) || !deliveryMethodEntity
                                .getLargeAmountDiscountCarriage()
                                .equals(BigDecimal.ZERO)) {
                    deliveryRegistUpdateModel.setLargeAmountDiscountCarriage(
                                    deliveryMethodEntity.getLargeAmountDiscountCarriage().toString());
                }
            }

            if (deliveryMethodEntity.getShortfallDisplayFlag().equals(HTypeShortfallDisplayFlag.ON)) {
                deliveryRegistUpdateModel.setShortfallDisplayFlag(true);
            } else {
                deliveryRegistUpdateModel.setShortfallDisplayFlag(false);
            }

            deliveryRegistUpdateModel.setDeliverySpecialChargeAreaCount(
                            deliveryMethodDetailsDto.getDeliverySpecialChargeAreaCount());
            deliveryRegistUpdateModel.setDeliveryImpossibleAreaCount(
                            deliveryMethodDetailsDto.getDeliveryImpossibleAreaCount());

        }

        /* 都道府県別送料 start */
        DeliveryPrefectureCarriageItem prefectureCarriageItem = null;
        List<DeliveryPrefectureCarriageItem> prefectureCarriageItems = new ArrayList<>();
        List<DeliveryPrefectureCarriageItem> prefectureCarriageList = new ArrayList<>();

        // 都道府県の数分作る
        EnumType[] values = HTypePrefectureType.class.getEnumConstants();
        for (EnumType pref : values) {

            HTypePrefectureType prefectureType = (HTypePrefectureType) pref;

            prefectureCarriageItem = ApplicationContextUtility.getBean(DeliveryPrefectureCarriageItem.class);
            // 登録モード
            if (registMode) {
                // 有効フラグ
                prefectureCarriageItem.setActiveFlag(false);
                // 送料
                prefectureCarriageItem.setPrefectureCarriage("");

                // 更新モード
            } else {
                if (deliveryMethodEntity.getDeliveryMethodType().equals(HTypeDeliveryMethodType.PREFECTURE)) {
                    for (DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity : deliveryMethodTypeCarriageEntityList) {
                        if (deliveryMethodTypeCarriageEntity.getPrefectureType().equals(prefectureType)) {
                            // 有効フラグ
                            prefectureCarriageItem.setActiveFlag(true);
                            // 送料
                            prefectureCarriageItem.setPrefectureCarriage(
                                            deliveryMethodTypeCarriageEntity.getCarriage().toString());
                            break;
                        }
                    }

                } else {
                    // 有効フラグ
                    prefectureCarriageItem.setActiveFlag(false);
                    // 送料
                    prefectureCarriageItem.setPrefectureCarriage("");
                }
            }

            // 都道府県
            prefectureCarriageItem.setPrefectureName(prefectureType.getLabel());
            // 都道府県種別
            prefectureCarriageItem.setPrefectureType(prefectureType);
            // リストに貯める
            prefectureCarriageItems.add(prefectureCarriageItem);
            prefectureCarriageList.add(CopyUtil.deepCopy(prefectureCarriageItem));
        }

        // 作ったリストをページに設定
        deliveryRegistUpdateModel.setDeliveryPrefectureCarriageItems(prefectureCarriageItems);
        deliveryRegistUpdateModel.setDeliveryPrefectureCarriageList(prefectureCarriageList);
        /* 都道府県別送料 end */

        /* 金額別送料 start */
        DeliveryAmountCarriageItem amountCarriageItem = null;
        List<DeliveryAmountCarriageItem> amountCarriageItemList = new ArrayList<>();

        // 10個作る
        for (int i = 0; i < 10; i++) {
            amountCarriageItem = ApplicationContextUtility.getBean(DeliveryAmountCarriageItem.class);
            // 上限金額
            amountCarriageItem.setMaxPrice("");
            // 送料
            amountCarriageItem.setAmountCarriage("");

            // リストに貯める
            amountCarriageItemList.add(amountCarriageItem);
        }

        // 更新モード
        // ※金額別送料は現在未使用
        if (!registMode && deliveryMethodEntity.getDeliveryMethodType().equals(HTypeDeliveryMethodType.AMOUNT)) {
            for (int i = 0; i < deliveryMethodTypeCarriageEntityList.size(); i++) {
                // 上限金額
                amountCarriageItemList.get(i)
                                      .setMaxPrice(deliveryMethodTypeCarriageEntityList.get(i)
                                                                                       .getMaxPrice()
                                                                                       .toString());
                // 送料
                amountCarriageItemList.get(i)
                                      .setAmountCarriage(deliveryMethodTypeCarriageEntityList.get(i)
                                                                                             .getCarriage()
                                                                                             .toString());
            }

        }

        // 作ったリストをページに設定
        deliveryRegistUpdateModel.setDeliveryAmountCarriageItems(amountCarriageItemList);
        /* 金額別送料 end */

        deliveryRegistUpdateModel.setDeliveryMethodDetailsDto(deliveryMethodDetailsDto);
        deliveryRegistUpdateModel.setNormality(true);
    }

    /**
     * ページへの変換処理
     *
     * @param deliveryRegistUpdateModel 配送方法登録・更新画面ページ
     */
    public void toPageForConfirmIndex(DeliveryRegistUpdateModel deliveryRegistUpdateModel) {
        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // ***** 配送方法エンティティの設定。*****
        DeliveryMethodEntity deliveryMethodEntity = CopyUtil.deepCopy(
                        deliveryRegistUpdateModel.getDeliveryMethodDetailsDto().getDeliveryMethodEntity());
        if (deliveryMethodEntity == null) {
            deliveryMethodEntity = deliveryRegistUpdateModel.getDeliveryMethodEntity();
            if (deliveryMethodEntity == null) {
                deliveryMethodEntity = new DeliveryMethodEntity();
            }
        }

        deliveryMethodEntity.setDeliveryMethodName(deliveryRegistUpdateModel.getDeliveryMethodName());
        deliveryMethodEntity.setDeliveryChaseURL(deliveryRegistUpdateModel.getDeliveryChaseURL());
        deliveryMethodEntity.setDeliveryChaseURLDisplayPeriod(
                        conversionUtility.toBigDecimal(deliveryRegistUpdateModel.getDeliveryChaseURLDisplayPeriod()));
        deliveryMethodEntity.setDeliveryMethodDisplayNamePC(deliveryRegistUpdateModel.getDeliveryMethodName());
        deliveryMethodEntity.setDeliveryMethodDisplayNameMB(deliveryRegistUpdateModel.getDeliveryMethodDisplayNameMB());
        // 公開状態PCをEnumに変換
        HTypeOpenDeleteStatus openStatus = EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                         deliveryRegistUpdateModel.getOpenStatusPC()
                                                                        );
        deliveryMethodEntity.setOpenStatusPC(openStatus);
        // 公開状態携帯をEnumに変換
        deliveryMethodEntity.setOpenStatusMB(HTypeOpenDeleteStatus.NO_OPEN);
        deliveryMethodEntity.setDeliveryNotePC(deliveryRegistUpdateModel.getDeliveryNotePC());
        deliveryMethodEntity.setDeliveryNoteMB(deliveryRegistUpdateModel.getDeliveryNoteMB());

        HTypeDeliveryMethodType deliveryMethodType = EnumTypeUtil.getEnumFromValue(HTypeDeliveryMethodType.class,
                                                                                   deliveryRegistUpdateModel.getDeliveryMethodType()
                                                                                  );
        deliveryMethodEntity.setDeliveryMethodType(deliveryMethodType);
        // 全国一律なら一律送料を設定
        if (deliveryMethodType.equals(HTypeDeliveryMethodType.FLAT)) {
            deliveryMethodEntity.setEqualsCarriage(
                            BigDecimalConversionUtil.toBigDecimal(deliveryRegistUpdateModel.getEqualsCarriage()));
        }
        // 金額別以外なら高額割引送料を設定
        if (!deliveryMethodType.equals(HTypeDeliveryMethodType.AMOUNT)) {
            if (StringUtil.isEmpty(deliveryRegistUpdateModel.getLargeAmountDiscountPrice())) {
                deliveryMethodEntity.setLargeAmountDiscountPrice(BigDecimal.ZERO);
            } else {
                deliveryMethodEntity.setLargeAmountDiscountPrice(BigDecimalConversionUtil.toBigDecimal(
                                deliveryRegistUpdateModel.getLargeAmountDiscountPrice()));
            }

            if (StringUtil.isEmpty(deliveryRegistUpdateModel.getLargeAmountDiscountCarriage())) {
                deliveryMethodEntity.setLargeAmountDiscountCarriage(BigDecimal.ZERO);
            } else {
                deliveryMethodEntity.setLargeAmountDiscountCarriage(BigDecimalConversionUtil.toBigDecimal(
                                deliveryRegistUpdateModel.getLargeAmountDiscountCarriage()));
            }
        }
        if (StringUtil.isEmpty(deliveryRegistUpdateModel.getDeliveryLeadTime())) {
            // 入力がない場合は、0をセット
            deliveryRegistUpdateModel.setDeliveryLeadTime("0");
        }
        deliveryMethodEntity.setDeliveryLeadTime(
                        conversionUtility.toInteger(deliveryRegistUpdateModel.getDeliveryLeadTime()));
        if (StringUtil.isEmpty(deliveryRegistUpdateModel.getPossibleSelectDays())) {
            // 入力がない場合は、0をセット
            deliveryRegistUpdateModel.setPossibleSelectDays("0");
        }
        deliveryMethodEntity.setPossibleSelectDays(
                        conversionUtility.toInteger(deliveryRegistUpdateModel.getPossibleSelectDays()));
        deliveryMethodEntity.setReceiverTimeZone1(deliveryRegistUpdateModel.getReceiverTimeZone1());
        deliveryMethodEntity.setReceiverTimeZone2(deliveryRegistUpdateModel.getReceiverTimeZone2());
        deliveryMethodEntity.setReceiverTimeZone3(deliveryRegistUpdateModel.getReceiverTimeZone3());
        deliveryMethodEntity.setReceiverTimeZone4(deliveryRegistUpdateModel.getReceiverTimeZone4());
        deliveryMethodEntity.setReceiverTimeZone5(deliveryRegistUpdateModel.getReceiverTimeZone5());
        deliveryMethodEntity.setReceiverTimeZone6(deliveryRegistUpdateModel.getReceiverTimeZone6());
        deliveryMethodEntity.setReceiverTimeZone7(deliveryRegistUpdateModel.getReceiverTimeZone7());
        deliveryMethodEntity.setReceiverTimeZone8(deliveryRegistUpdateModel.getReceiverTimeZone8());
        deliveryMethodEntity.setReceiverTimeZone9(deliveryRegistUpdateModel.getReceiverTimeZone9());
        deliveryMethodEntity.setReceiverTimeZone10(deliveryRegistUpdateModel.getReceiverTimeZone10());

        // ***** 配送区分別送料エンティティの設定 *****
        List<DeliveryMethodTypeCarriageEntity> deliveryMethodTypeCarriageEntityList = new ArrayList<>();
        DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity = null;
        if (deliveryMethodType.equals(HTypeDeliveryMethodType.PREFECTURE)) {
            for (DeliveryPrefectureCarriageItem prefectureCarriageItem : deliveryRegistUpdateModel.getDeliveryPrefectureCarriageItems()) {
                if (prefectureCarriageItem.isActiveFlag()) {
                    // 配送区分別送料エンティティの作成
                    deliveryMethodTypeCarriageEntity =
                                    ApplicationContextUtility.getBean(DeliveryMethodTypeCarriageEntity.class);

                    deliveryMethodTypeCarriageEntity.setPrefectureType(prefectureCarriageItem.getPrefectureType());
                    deliveryMethodTypeCarriageEntity.setMaxPrice(BigDecimal.ZERO);
                    deliveryMethodTypeCarriageEntity.setCarriage(BigDecimalConversionUtil.toBigDecimal(
                                    prefectureCarriageItem.getPrefectureCarriage()));

                    // リストに貯める
                    deliveryMethodTypeCarriageEntityList.add(deliveryMethodTypeCarriageEntity);
                }
            }

        } else if (deliveryMethodType.equals(HTypeDeliveryMethodType.AMOUNT)) {
            // ※金額別送料は現在未使用
            for (DeliveryAmountCarriageItem amountCarriageItem : deliveryRegistUpdateModel.getDeliveryAmountCarriageItems()) {
                if (!StringUtil.isEmpty(amountCarriageItem.getMaxPrice())) {
                    // 配送区分別送料エンティティの作成
                    deliveryMethodTypeCarriageEntity =
                                    ApplicationContextUtility.getBean(DeliveryMethodTypeCarriageEntity.class);

                    deliveryMethodTypeCarriageEntity.setPrefectureType(HTypePrefectureType.HOKKAIDO);
                    deliveryMethodTypeCarriageEntity.setMaxPrice(
                                    BigDecimalConversionUtil.toBigDecimal(amountCarriageItem.getMaxPrice()));
                    deliveryMethodTypeCarriageEntity.setCarriage(
                                    BigDecimalConversionUtil.toBigDecimal(amountCarriageItem.getAmountCarriage()));

                    deliveryMethodTypeCarriageEntityList.add(deliveryMethodTypeCarriageEntity);
                }
            }
        }
        if (deliveryRegistUpdateModel.isShortfallDisplayFlag()) {
            deliveryMethodEntity.setShortfallDisplayFlag(HTypeShortfallDisplayFlag.ON);
        } else {
            deliveryMethodEntity.setShortfallDisplayFlag(HTypeShortfallDisplayFlag.OFF);
        }

        // ページに設定
        deliveryRegistUpdateModel.setDeliveryMethodEntity(deliveryMethodEntity);
        deliveryRegistUpdateModel.setDeliveryMethodTypeCarriageEntityList(deliveryMethodTypeCarriageEntityList);

    }

    /**
     * ページへの変換処理
     *
     * @param deliveryRegistUpdateModel 配送方法登録・更新確認画面ページ
     */
    public void toPageForLoadConfirm(DeliveryRegistUpdateModel deliveryRegistUpdateModel) {
        deliveryRegistUpdateModel.setShortfallDisplayFlag(deliveryRegistUpdateModel.getDeliveryMethodEntity()
                                                                                   .getShortfallDisplayFlag()
                                                                                   .equals(HTypeShortfallDisplayFlag.ON));

        if (!deliveryRegistUpdateModel.isRegistMode()) {
            // 修正箇所の検出
            DeliveryMethodEntity original = CopyUtil.deepCopy(
                            deliveryRegistUpdateModel.getDeliveryMethodDetailsDto().getDeliveryMethodEntity());
            DeliveryMethodEntity modified = CopyUtil.deepCopy(deliveryRegistUpdateModel.getDeliveryMethodEntity());

            // テキストエリアの項目の改行コードを統一
            if (original.getDeliveryNotePC() != null) {
                original.setDeliveryNotePC(original.getDeliveryNotePC().replaceAll("\r\n", "\n"));
            }

            if (original.getDeliveryNoteMB() != null) {
                original.setDeliveryNoteMB(original.getDeliveryNoteMB().replaceAll("\r\n", "\n"));
            }

            if (modified.getDeliveryNotePC() != null) {
                modified.setDeliveryNotePC(modified.getDeliveryNotePC().replaceAll("\r\n", "\n"));
            }

            if (modified.getDeliveryNoteMB() != null) {
                modified.setDeliveryNoteMB(modified.getDeliveryNoteMB().replaceAll("\r\n", "\n"));
            }

            deliveryRegistUpdateModel.setModifiedList(DiffUtil.diff(original, modified));

            List<List<String>> tmpDiffList = new ArrayList<>();
            // 変更前の都道府県別
            for (int i = 0; i < deliveryRegistUpdateModel.getDeliveryPrefectureCarriageList().size(); i++) {
                // 変更後の都道府県別
                tmpDiffList.add(DiffUtil.diff(
                                deliveryRegistUpdateModel.getDeliveryPrefectureCarriageList().get(i),
                                deliveryRegistUpdateModel.getDeliveryPrefectureCarriageItems().get(i)
                                             ));
            }
            deliveryRegistUpdateModel.setModifiedPrefectureList(tmpDiffList);
        }
    }

    /**
     * ページへの変換処理
     *
     * @param deliveryRegistUpdateModel 配送方法登録・更新確認画面ページ
     * @return 配送方法DTO
     */
    public DeliveryMethodDetailsDto toDeliveryMethodDtoForConfirm(DeliveryRegistUpdateModel deliveryRegistUpdateModel) {
        DeliveryMethodDetailsDto deliveryMethodDto = ApplicationContextUtility.getBean(DeliveryMethodDetailsDto.class);

        deliveryRegistUpdateModel.getDeliveryMethodEntity().setOpenStatusMB(HTypeOpenDeleteStatus.NO_OPEN);

        deliveryMethodDto.setDeliveryMethodEntity(deliveryRegistUpdateModel.getDeliveryMethodEntity());
        deliveryMethodDto.setDeliveryMethodTypeCarriageEntityList(
                        deliveryRegistUpdateModel.getDeliveryMethodTypeCarriageEntityList());

        return deliveryMethodDto;
    }
}
