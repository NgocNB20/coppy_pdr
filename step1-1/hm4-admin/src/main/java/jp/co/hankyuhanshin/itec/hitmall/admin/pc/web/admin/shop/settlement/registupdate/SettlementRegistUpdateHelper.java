package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.settlement.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEffectiveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodCommissionType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodPriceCommissionFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.MapUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SettlementRegistUpdateHelper {

    /**
     * 詳細内容画面反映
     *
     * @param settlementRegistUpdateModel 決済方法詳細設定画面
     * @param settlementMethodDto         決済方法DTO
     */
    public void toPage(SettlementRegistUpdateModel settlementRegistUpdateModel,
                       SettlementMethodDto settlementMethodDto) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        SettlementMethodEntity settlementMethodEntity = settlementMethodDto.getSettlementMethodEntity();
        // 決済方法情報設定
        HTypeSettlementMethodCommissionType commissionType = settlementMethodEntity.getSettlementMethodCommissionType();
        BigDecimal largeAmountDiscountPrice = settlementMethodEntity.getLargeAmountDiscountPrice();
        settlementRegistUpdateModel.setCommissionType(commissionType);

        // 請求種別
        settlementRegistUpdateModel.setBillType(EnumTypeUtil.getValue(settlementMethodEntity.getBillType()));
        // 請求種別表示用
        settlementRegistUpdateModel.setBillTypeValue(EnumTypeUtil.getValue(settlementMethodEntity.getBillType()));
        // 配送方法SEQ
        settlementRegistUpdateModel.setDeliveryMethodSeq(settlementMethodEntity.getDeliveryMethodSeq());
        // 公開状態携帯
        settlementRegistUpdateModel.setOpenStatusMB(EnumTypeUtil.getValue(settlementMethodEntity.getOpenStatusMB()));
        // 公開状態PC
        settlementRegistUpdateModel.setOpenStatusPC(EnumTypeUtil.getValue(settlementMethodEntity.getOpenStatusPC()));
        // 表示順
        settlementRegistUpdateModel.setOrderDisplay(settlementMethodEntity.getOrderDisplay());
        // 支払期限猶予日数
        settlementRegistUpdateModel.setPaymentTimeLimitDayCount(
                        conversionUtility.toString(settlementMethodEntity.getPaymentTimeLimitDayCount()));

        // 最小購入金額
        settlementRegistUpdateModel.setMinPurchasedPrice(
                        conversionUtility.toString(settlementMethodEntity.getMinPurchasedPrice()));
        // 期限後取消猶予日数
        settlementRegistUpdateModel.setCancelTimeLimitDayCount(
                        conversionUtility.toString(settlementMethodEntity.getCancelTimeLimitDayCount()));
        // 決済関連メール要否フラグ
        settlementRegistUpdateModel.setSettlementMailRequired(
                        EnumTypeUtil.getValue(settlementMethodEntity.getSettlementMailRequired()));
        // ｸﾚｼﾞｯﾄｶｰﾄﾞ登録機能有効化ﾌﾗｸﾞ
        if (HTypeEffectiveFlag.VALID.equals(settlementMethodEntity.getEnableCardNoHolding())) {
            settlementRegistUpdateModel.setEnableCardNoHolding(true);
        }
        // ｸﾚｼﾞｯﾄ3Dｾｷｭｱ有効化ﾌﾗｸﾞ
        if (HTypeEffectiveFlag.VALID.equals(settlementMethodEntity.getEnable3dSecure())) {
            settlementRegistUpdateModel.setEnable3dSecure(true);
        }
        // ｸﾚｼﾞｯﾄ分割支払有効化ﾌﾗｸﾞ
        if (HTypeEffectiveFlag.VALID.equals(settlementMethodEntity.getEnableInstallment())) {
            settlementRegistUpdateModel.setEnableInstallment(true);
        }
        // ｸﾚｼﾞｯﾄﾘﾎﾞ有効化ﾌﾗｸﾞ
        if (HTypeEffectiveFlag.VALID.equals(settlementMethodEntity.getEnableRevolving())) {
            settlementRegistUpdateModel.setEnableRevolving(true);
        }

        // 登録日時
        settlementRegistUpdateModel.setRegistTime(settlementMethodEntity.getRegistTime());
        // 決済方法手数料種別
        settlementRegistUpdateModel.setSettlementMethodCommissionType(
                        EnumTypeUtil.getValue(settlementMethodEntity.getSettlementMethodCommissionType()));
        // 決済方法表示名携帯
        settlementRegistUpdateModel.setSettlementMethodDisplayNameMB(
                        settlementMethodEntity.getSettlementMethodDisplayNameMB());
        // 決済方法表示名PC
        settlementRegistUpdateModel.setSettlementMethodDisplayNamePC(
                        settlementMethodEntity.getSettlementMethodDisplayNamePC());
        // 決済方法名
        settlementRegistUpdateModel.setSettlementMethodName(settlementMethodEntity.getSettlementMethodName());
        // 決済方法SEQ
        settlementRegistUpdateModel.setSettlementMethodSeq(settlementMethodEntity.getSettlementMethodSeq());
        // 決済方法SEQ
        settlementRegistUpdateModel.setSettlementMethodId(settlementMethodEntity.getSettlementMethodSeq());
        // 決済方法種別
        settlementRegistUpdateModel.setSettlementMethodType(
                        EnumTypeUtil.getValue(settlementMethodEntity.getSettlementMethodType()));
        // 決済方法種別表示用
        settlementRegistUpdateModel.setSettlementMethodTypeValue(
                        EnumTypeUtil.getLabelValue(settlementMethodEntity.getSettlementMethodType()));
        // 決済方法説明文携帯
        settlementRegistUpdateModel.setSettlementNoteMB(settlementMethodEntity.getSettlementNoteMB());
        // 決済方法説明文PC
        settlementRegistUpdateModel.setSettlementNotePC(settlementMethodEntity.getSettlementNotePC());
        // ショップSEQ
        settlementRegistUpdateModel.setShopSeq(settlementMethodEntity.getShopSeq());
        // 更新日時
        settlementRegistUpdateModel.setUpdateTime(settlementMethodEntity.getUpdateTime());

        // 配送方法名
        settlementRegistUpdateModel.setDeliveryMethodName(settlementMethodDto.getDeliveryMethodName());

        // 決済方法金額別手数料設定
        HTypeSettlementMethodPriceCommissionFlag settlementMethodPriceCommissionFlag =
                        settlementMethodEntity.getSettlementMethodPriceCommissionFlag();
        settlementRegistUpdateModel.setSettlementMethodPriceCommissionFlag(settlementMethodPriceCommissionFlag);

        if (settlementMethodPriceCommissionFlag != null
            && HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT == settlementMethodPriceCommissionFlag) {

            if (ListUtils.isEmpty(settlementRegistUpdateModel.getPriceCommissionYen())) {
                List<SettlementMethodPriceCommissionEntity> commissonList =
                                settlementMethodDto.getSettlementMethodPriceCommissionEntityList();
                List<SettlementRegistUpdateModelItem> items = new ArrayList<>();

                int count = 0;
                for (SettlementMethodPriceCommissionEntity commisson : commissonList) {
                    SettlementRegistUpdateModelItem item =
                                    ApplicationContextUtility.getBean(SettlementRegistUpdateModelItem.class);
                    item.setCommission(conversionUtility.toString(commisson.getCommission()));
                    item.setMaxPrice((conversionUtility.toString(commisson.getMaxPrice())));
                    item.setRegisttime(commisson.getRegistTime());
                    item.setSettlementmethodseq(commisson.getSettlementMethodSeq());
                    item.setUpdatetime(commisson.getUpdateTime());
                    items.add(item);
                    count++;
                }
                for (; count < 10; count++) {
                    SettlementRegistUpdateModelItem item =
                                    ApplicationContextUtility.getBean(SettlementRegistUpdateModelItem.class);
                    items.add(item);
                }

                // if
                // (HTypeSettlementMethodCommissionType.EACH_AMOUNT_PERCENTAGE
                // ==
                // commissionType) {
                // page.setPriceCommissionPercentage(items);
                // } else {
                settlementRegistUpdateModel.setPriceCommissionYen(items);
                // }
            }
        } else {
            // if (HTypeSettlementMethodCommissionType.FLAT_PERCENTAGE ==
            // commissionType) {
            // // 一律手数料
            // page.setEqualsCommissionPercentage(conversionUtility.toString(settlementMethodEntity.equalsCommission));
            // if (largeAmountDiscountPrice != null &&
            // largeAmountDiscountPrice.compareTo(BigDecimal.ZERO) > 0) {
            // // 高額割引手数料
            // page.setLargeAmountDiscountCommissionPercentage(conversionUtility.toString(settlementMethodEntity.largeAmountDiscountCommission));
            // // 高額割引下限金額
            // page.setLargeAmountDiscountPricePercentage(conversionUtility.toString(largeAmountDiscountPrice));
            // }
            // // 最大購入金額
            // page.setMaxPurchasedPricePercentage(conversionUtility.toString(settlementMethodEntity.maxPurchasedPrice));
            // } else {
            // 一律手数料
            settlementRegistUpdateModel.setEqualsCommissionYen(
                            conversionUtility.toString(settlementMethodEntity.getEqualsCommission()));
            if (largeAmountDiscountPrice != null && largeAmountDiscountPrice.compareTo(BigDecimal.ZERO) > 0) {
                // 高額割引手数料
                settlementRegistUpdateModel.setLargeAmountDiscountCommissionYen(
                                conversionUtility.toString(settlementMethodEntity.getLargeAmountDiscountCommission()));
                // 高額割引下限金額
                settlementRegistUpdateModel.setLargeAmountDiscountPriceYen(
                                conversionUtility.toString(largeAmountDiscountPrice));
            }
            // 最大購入金額
            settlementRegistUpdateModel.setMaxPurchasedPriceYen(
                            conversionUtility.toString(settlementMethodEntity.getMaxPurchasedPrice()));
            // }
        }

        settlementRegistUpdateModel.setSettlementMethodDto(settlementMethodDto);
    }

    /**
     * 決済方法金額別手数料コンポーネント作成<br/>
     *
     * @return 決済方法金額別手数料リスト
     */
    public List<SettlementRegistUpdateModelItem> makePriceCommissionComponent() {

        List<SettlementRegistUpdateModelItem> items = new ArrayList<>();
        for (int count = 0; count < 10; count++) {
            SettlementRegistUpdateModelItem item =
                            ApplicationContextUtility.getBean(SettlementRegistUpdateModelItem.class);
            items.add(item);
        }
        return items;

    }

    /**
     * 決済方法金額別手数料フラグ変更<br/>
     *
     * @param settlementRegistUpdateModel 決済方法詳細設定ページ
     */
    public void changeComissonFlag(SettlementRegistUpdateModel settlementRegistUpdateModel) {
        if (settlementRegistUpdateModel.getSettlementMethodCommissionType() == null) {
            settlementRegistUpdateModel.setSettlementMethodPriceCommissionFlag(null);
            return;
        }
        HTypeSettlementMethodCommissionType commissionType =
                        EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodCommissionType.class,
                                                      settlementRegistUpdateModel.getSettlementMethodCommissionType()
                                                     );
        settlementRegistUpdateModel.setCommissionType(commissionType);

        // 決済方法金額別手数料フラグ設定
        // if (HTypeSettlementMethodCommissionType.EACH_AMOUNT_PERCENTAGE ==
        // commissionType) {
        // page.setSettlementMethodPriceCommissionFlag(HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT);
        // if (page.getPriceCommissionPercentage() != null) {
        // return;
        // }
        // page.setPriceCommissionPercentage(makePriceCommissionComponent());
        // } else
        if (HTypeSettlementMethodCommissionType.EACH_AMOUNT_YEN == commissionType) {
            settlementRegistUpdateModel.setSettlementMethodPriceCommissionFlag(
                            HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT);
            if (settlementRegistUpdateModel.getPriceCommissionYen() != null) {
                return;
            }
            settlementRegistUpdateModel.setPriceCommissionYen(makePriceCommissionComponent());
        } else {
            settlementRegistUpdateModel.setSettlementMethodPriceCommissionFlag(
                            HTypeSettlementMethodPriceCommissionFlag.FLAT);
        }
    }

    /**
     * 選択された配送方法名称を設定
     *
     * @param settlementRegistUpdateModel 決済方法詳細設定ページ
     */
    public void setDeliveryMethodName(SettlementRegistUpdateModel settlementRegistUpdateModel) {
        Integer deliveryMethodSeq = settlementRegistUpdateModel.getDeliveryMethodSeq();
        if (deliveryMethodSeq == null) {
            settlementRegistUpdateModel.setDeliveryMethodName(null);
            return;
        }

        Map<String, String> deliveryMethodSeqItems = settlementRegistUpdateModel.getDeliveryMethodSeqItems();
        if (deliveryMethodSeqItems.containsKey(deliveryMethodSeq.toString())) {
            settlementRegistUpdateModel.setDeliveryMethodName(deliveryMethodSeqItems.get(deliveryMethodSeq.toString()));
        }
    }

    /**
     * コピー処理時の画面反映
     *
     * @param settlementRegistUpdateModel 決済方法詳細設定ページ
     */
    public void toPageForCopyToMobile(SettlementRegistUpdateModel settlementRegistUpdateModel) {
        settlementRegistUpdateModel.setSettlementMethodDisplayNameMB(
                        this.convertToMB(settlementRegistUpdateModel.getSettlementMethodDisplayNamePC()));
        settlementRegistUpdateModel.setSettlementNoteMB(
                        this.convertToMB((settlementRegistUpdateModel.getSettlementNotePC())));
    }

    /**
     * コピー用に半角変換した値を返す
     *
     * @param valuePC PC値
     * @return 携帯値
     */
    protected String convertToMB(String valuePC) {

        String ret = null;
        if (valuePC != null) {
            // 全角、半角の変換Helper取得
            ZenHanConversionUtility zenHanConversionUtility =
                            ApplicationContextUtility.getBean(ZenHanConversionUtility.class);

            ret = zenHanConversionUtility.toHankaku(valuePC, new Character[] {'＆', '＜', '＞', '”', '’', '￥'});
        }

        return ret;
    }

    /**
     * 決済方法DTO作成<br/>
     *
     * @param settlementRegistUpdateModel 決済方法詳細設定：確認ページ
     * @return 決済方法DTO
     */
    public SettlementMethodDto pageToSettlementMethodDto(SettlementRegistUpdateModel settlementRegistUpdateModel) {

        changeComissonFlag(settlementRegistUpdateModel);

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 決済方法金額別手数料フラグ
        HTypeSettlementMethodPriceCommissionFlag priceCommissionFlag =
                        settlementRegistUpdateModel.getSettlementMethodPriceCommissionFlag();

        SettlementMethodDto settlementMethodDto = ApplicationContextUtility.getBean(SettlementMethodDto.class);

        SettlementMethodEntity settlementMethod = ApplicationContextUtility.getBean(SettlementMethodEntity.class);

        // 決済方法SEQ
        settlementMethod.setSettlementMethodSeq(settlementRegistUpdateModel.getSettlementMethodSeq());

        // 決済方法名
        settlementMethod.setSettlementMethodName(settlementRegistUpdateModel.getSettlementMethodName());
        // 決済方法表示名PC
        settlementMethod.setSettlementMethodDisplayNamePC(settlementRegistUpdateModel.getSettlementMethodName());
        // 決済方法表示名携帯
        settlementMethod.setSettlementMethodDisplayNameMB(
                        settlementRegistUpdateModel.getSettlementMethodDisplayNameMB());
        // 公開状態PC
        settlementMethod.setOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                       settlementRegistUpdateModel.getOpenStatusPC()
                                                                      ));
        // 公開状態携帯
        settlementMethod.setOpenStatusMB(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                       settlementRegistUpdateModel.getOpenStatusMB()
                                                                      ));
        // 決済方法説明文PC
        settlementMethod.setSettlementNotePC(settlementRegistUpdateModel.getSettlementNotePC());
        // 決済方法説明文携帯
        settlementMethod.setSettlementNoteMB(settlementRegistUpdateModel.getSettlementNoteMB());
        // 決済方法種別
        settlementMethod.setSettlementMethodType(EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodType.class,
                                                                               settlementRegistUpdateModel.getSettlementMethodType()
                                                                              ));
        // 決済方法手数料種別
        settlementMethod.setSettlementMethodCommissionType(
                        EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodCommissionType.class,
                                                      settlementRegistUpdateModel.getSettlementMethodCommissionType()
                                                     ));
        // 請求種別
        settlementMethod.setBillType(
                        EnumTypeUtil.getEnumFromValue(HTypeBillType.class, settlementRegistUpdateModel.getBillType()));
        // 配送方法SEQ
        settlementMethod.setDeliveryMethodSeq(settlementRegistUpdateModel.getDeliveryMethodSeq());
        // 一律手数料
        settlementMethod.setEqualsCommission(
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getEqualsCommission()));
        // 決済方法金額別手数料フラグ
        settlementMethod.setSettlementMethodPriceCommissionFlag(priceCommissionFlag);
        // 高額割引下限金額
        settlementMethod.setLargeAmountDiscountPrice(
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getLargeAmountDiscountPrice()));
        // 高額割引手数料
        settlementMethod.setLargeAmountDiscountCommission(
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getLargeAmountDiscountCommission()));
        // 最大購入金額
        settlementMethod.setMaxPurchasedPrice(
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getMaxPurchasedPrice()));
        // 最小購入金額
        settlementMethod.setMinPurchasedPrice(
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getMinPurchasedPrice()));
        // クレジット または 前払い(代金引換以外)の決済の場合、入力値をセットする
        if (HTypeSettlementMethodType.CREDIT.getValue().equals(settlementRegistUpdateModel.getSettlementMethodType())
            || (!HTypeSettlementMethodType.RECEIPT_PAYMENT.getValue()
                                                          .equals(settlementRegistUpdateModel.getSettlementMethodType())
                && HTypeBillType.PRE_CLAIM.getValue().equals(settlementRegistUpdateModel.getBillType()))) {
            // 支払期限猶予日数
            settlementMethod.setPaymentTimeLimitDayCount(conversionUtility.toInteger(
                            conversionUtility.toBigDecimal(settlementRegistUpdateModel.getPaymentTimeLimitDayCount())));
            // 期限後取消猶予日数
            settlementMethod.setCancelTimeLimitDayCount(conversionUtility.toInteger(
                            conversionUtility.toBigDecimal(settlementRegistUpdateModel.getCancelTimeLimitDayCount())));
        }
        // 決済関連メール要否フラグ
        settlementMethod.setSettlementMailRequired(EnumTypeUtil.getEnumFromValue(HTypeMailRequired.class,
                                                                                 settlementRegistUpdateModel.getSettlementMailRequired()
                                                                                ));
        // ｸﾚｼﾞｯﾄ3Dｾｷｭｱ有効化ﾌﾗｸﾞ
        settlementMethod.setEnable3dSecure(HTypeEffectiveFlag.INVALID);
        // ｸﾚｼﾞｯﾄｶｰﾄﾞ登録機能有効化ﾌﾗｸﾞ
        settlementMethod.setEnableCardNoHolding(HTypeEffectiveFlag.VALID);
        // ｸﾚｼﾞｯﾄ分割支払有効化ﾌﾗｸﾞ
        settlementMethod.setEnableInstallment(HTypeEffectiveFlag.INVALID);
        // ｸﾚｼﾞｯﾄﾘﾎﾞ有効化ﾌﾗｸﾞ
        settlementMethod.setEnableRevolving(HTypeEffectiveFlag.INVALID);
        // 決済方法種別がクレジットの場合は画面の入力値を設定する
        if (HTypeSettlementMethodType.CREDIT.equals(settlementMethod.getSettlementMethodType())) {
            // ｸﾚｼﾞｯﾄ3Dｾｷｭｱ有効化ﾌﾗｸﾞ
            if (settlementRegistUpdateModel.isEnable3dSecure()) {
                settlementMethod.setEnable3dSecure(HTypeEffectiveFlag.VALID);
            }
            // ｸﾚｼﾞｯﾄｶｰﾄﾞ登録機能有効化ﾌﾗｸﾞ
            if (settlementRegistUpdateModel.isEnableCardNoHolding()) {
                settlementMethod.setEnableCardNoHolding(HTypeEffectiveFlag.VALID);
            }
            // ｸﾚｼﾞｯﾄ分割支払有効化ﾌﾗｸ
            if (settlementRegistUpdateModel.isEnableInstallment()) {
                settlementMethod.setEnableInstallment(HTypeEffectiveFlag.VALID);
            }
            // ｸﾚｼﾞｯﾄﾘﾎﾞ有効化ﾌﾗｸﾞ
            if (settlementRegistUpdateModel.isEnableRevolving()) {
                settlementMethod.setEnableRevolving(HTypeEffectiveFlag.VALID);
            }
        }

        settlementMethodDto.setSettlementMethodEntity(settlementMethod);

        if (HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT != priceCommissionFlag) {
            return settlementMethodDto;
        }

        // 金額別手数料設定
        List<SettlementRegistUpdateModelItem> priceCommissonItemList =
                        settlementRegistUpdateModel.getPriceCommissionItemList();
        List<SettlementMethodPriceCommissionEntity> priceCommissonList = new ArrayList<>();
        BigDecimal maxPurchasedPrice = BigDecimal.ZERO;
        for (SettlementRegistUpdateModelItem item : priceCommissonItemList) {
            SettlementMethodPriceCommissionEntity priceCommission =
                            ApplicationContextUtility.getBean(SettlementMethodPriceCommissionEntity.class);
            BigDecimal maxPrice = conversionUtility.toBigDecimal(item.getMaxPrice());
            BigDecimal commission = conversionUtility.toBigDecimal(item.getCommission());
            if (maxPrice != null && commission != null) {
                // 上限金額
                priceCommission.setMaxPrice(conversionUtility.toBigDecimal(item.getMaxPrice()));
                // 手数料
                priceCommission.setCommission(conversionUtility.toBigDecimal(item.getCommission()));
                priceCommissonList.add(priceCommission);
                if (maxPurchasedPrice.compareTo(priceCommission.getMaxPrice()) < 0) {
                    maxPurchasedPrice = priceCommission.getMaxPrice();
                }
            }
        }
        settlementMethodDto.setSettlementMethodPriceCommissionEntityList(priceCommissonList);
        settlementMethod.setMaxPurchasedPrice(maxPurchasedPrice);

        return settlementMethodDto;
    }

    /**
     * 決済種別リストを再作成する。<br />
     * <pre>
     * 標準PKGの決済種別のリストから、全額割引決済を除去したリストを作成。
     * </pre>
     *
     * @param settlementRegistUpdateModel 決済方法詳細設定画面
     */
    public void toSettlementMethodTypeItems(SettlementRegistUpdateModel settlementRegistUpdateModel) {
        // 決済種別リストを取得
        Map<String, String> settlementMethodTypeList = settlementRegistUpdateModel.getSettlementMethodTypeItems();

        if (!MapUtils.isEmpty(settlementMethodTypeList) && settlementMethodTypeList.containsValue(
                        HTypeSettlementMethodType.DISCOUNT.getValue())) {
            // 決済種別リストから「全額割引」を削除する
            settlementMethodTypeList.remove(HTypeSettlementMethodType.DISCOUNT.getValue());
        }

    }

    /**
     * 決済方法DTO作成<br/>
     *
     * @param settlementRegistUpdateModel 決済方法詳細設定：確認ページ
     * @return 決済方法DTO
     */
    public SettlementMethodDto pageToSettlementMethodDtoConfirm(SettlementRegistUpdateModel settlementRegistUpdateModel) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 決済方法金額別手数料フラグ
        HTypeSettlementMethodPriceCommissionFlag priceCommissionFlag =
                        settlementRegistUpdateModel.getSettlementMethodPriceCommissionFlag();

        SettlementMethodDto settlementMethodDto = settlementRegistUpdateModel.getSettlementMethodDto();

        SettlementMethodEntity settlementMethod = settlementMethodDto.getSettlementMethodEntity();
        if (settlementMethod == null) {
            settlementMethod = ApplicationContextUtility.getBean(SettlementMethodEntity.class);
        }

        // 決済方法SEQ
        settlementMethod.setSettlementMethodSeq(settlementRegistUpdateModel.getSettlementMethodSeq());
        // 表示順
        settlementMethod.setOrderDisplay(settlementRegistUpdateModel.getOrderDisplay());

        // 決済方法名
        settlementMethod.setSettlementMethodName(settlementRegistUpdateModel.getSettlementMethodName());
        // 決済方法表示名PC
        settlementMethod.setSettlementMethodDisplayNamePC(settlementRegistUpdateModel.getSettlementMethodName());
        // 決済方法表示名携帯
        settlementMethod.setSettlementMethodDisplayNameMB(
                        settlementRegistUpdateModel.getSettlementMethodDisplayNameMB());
        // 公開状態PC
        settlementMethod.setOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                       settlementRegistUpdateModel.getOpenStatusPC()
                                                                      ));
        // 公開状態携帯
        settlementMethod.setOpenStatusMB(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                       settlementRegistUpdateModel.getOpenStatusMB()
                                                                      ));
        // 決済方法説明文PC
        settlementMethod.setSettlementNotePC(settlementRegistUpdateModel.getSettlementNotePC());
        // 決済方法説明文携帯
        settlementMethod.setSettlementNoteMB(settlementRegistUpdateModel.getSettlementNoteMB());
        if (settlementRegistUpdateModel.isRegist()) {
            // 決済方法種別
            settlementMethod.setSettlementMethodType(EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodType.class,
                                                                                   settlementRegistUpdateModel.getSettlementMethodType()
                                                                                  ));
            // 請求種別
            settlementMethod.setBillType(EnumTypeUtil.getEnumFromValue(HTypeBillType.class,
                                                                       settlementRegistUpdateModel.getBillType()
                                                                      ));
            // 配送方法SEQ
            settlementMethod.setDeliveryMethodSeq(settlementRegistUpdateModel.getDeliveryMethodSeq());
        }
        // 決済方法手数料種別
        settlementMethod.setSettlementMethodCommissionType(
                        EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodCommissionType.class,
                                                      settlementRegistUpdateModel.getSettlementMethodCommissionType()
                                                     ));
        // 一律手数料
        settlementMethod.setEqualsCommission(
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getEqualsCommission()));
        // 決済方法金額別手数料フラグ
        settlementMethod.setSettlementMethodPriceCommissionFlag(priceCommissionFlag);
        // 高額割引下限金額
        settlementMethod.setLargeAmountDiscountPrice(
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getLargeAmountDiscountPrice()));
        // 高額割引手数料
        settlementMethod.setLargeAmountDiscountCommission(
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getLargeAmountDiscountCommission()));
        // 最大購入金額
        settlementMethod.setMaxPurchasedPrice(
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getMaxPurchasedPrice()));
        // 最小購入金額
        settlementMethod.setMinPurchasedPrice(
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getMinPurchasedPrice()));
        // クレジット または 前払い(代金引換以外)の決済の場合、入力値をセットする
        if (HTypeSettlementMethodType.CREDIT.getValue().equals(settlementRegistUpdateModel.getSettlementMethodType())
            || (!HTypeSettlementMethodType.RECEIPT_PAYMENT.getValue()
                                                          .equals(settlementRegistUpdateModel.getSettlementMethodType())
                && HTypeBillType.PRE_CLAIM.getValue().equals(settlementRegistUpdateModel.getBillType()))) {
            // 支払期限猶予日数
            settlementMethod.setPaymentTimeLimitDayCount(
                            conversionUtility.toInteger(settlementRegistUpdateModel.getPaymentTimeLimitDayCount()));
            // 期限後取消猶予日数
            settlementMethod.setCancelTimeLimitDayCount(
                            conversionUtility.toInteger(settlementRegistUpdateModel.getCancelTimeLimitDayCount()));
        }
        // 決済関連メール要否フラグ
        settlementMethod.setSettlementMailRequired(EnumTypeUtil.getEnumFromValue(HTypeMailRequired.class,
                                                                                 settlementRegistUpdateModel.getSettlementMailRequired()
                                                                                ));
        // ｸﾚｼﾞｯﾄｾｷｭﾘﾃｨｺｰﾄﾞ有効化ﾌﾗｸﾞ
        settlementMethod.setEnableSecurityCode(HTypeEffectiveFlag.INVALID);
        // ｸﾚｼﾞｯﾄｶｰﾄﾞ登録機能有効化ﾌﾗｸﾞ
        settlementMethod.setEnableCardNoHolding(HTypeEffectiveFlag.INVALID);
        // ｸﾚｼﾞｯﾄ3Dｾｷｭｱ有効化ﾌﾗｸﾞ
        settlementMethod.setEnable3dSecure(HTypeEffectiveFlag.INVALID);
        // ｸﾚｼﾞｯﾄ分割支払有効化ﾌﾗｸﾞ
        settlementMethod.setEnableInstallment(HTypeEffectiveFlag.INVALID);
        // ｸﾚｼﾞｯﾄﾘﾎﾞ有効化ﾌﾗｸﾞ
        settlementMethod.setEnableRevolving(HTypeEffectiveFlag.INVALID);
        if (HTypeSettlementMethodType.CREDIT.getValue().equals(settlementRegistUpdateModel.getSettlementMethodType())) {
            // ｸﾚｼﾞｯﾄｶｰﾄﾞ登録機能有効化ﾌﾗｸﾞ
            if (settlementRegistUpdateModel.isEnableCardNoHolding()) {
                settlementMethod.setEnableCardNoHolding(HTypeEffectiveFlag.VALID);
            }
            // ｸﾚｼﾞｯﾄ3Dｾｷｭｱ有効化ﾌﾗｸﾞ
            if (settlementRegistUpdateModel.isEnable3dSecure()) {
                settlementMethod.setEnable3dSecure(HTypeEffectiveFlag.VALID);
            }
            // ｸﾚｼﾞｯﾄ分割支払有効化ﾌﾗｸﾞ
            if (settlementRegistUpdateModel.isEnableInstallment()) {
                settlementMethod.setEnableInstallment(HTypeEffectiveFlag.VALID);
            }
            // ｸﾚｼﾞｯﾄﾘﾎﾞ有効化ﾌﾗｸﾞ
            if (settlementRegistUpdateModel.isEnableRevolving()) {
                settlementMethod.setEnableRevolving(HTypeEffectiveFlag.VALID);
            }
        }

        if (HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT != priceCommissionFlag) {
            return settlementMethodDto;
        }

        // 金額別手数料設定
        List<SettlementRegistUpdateModelItem> priceCommissonItemList =
                        settlementRegistUpdateModel.getPriceCommissionItemList();
        List<SettlementMethodPriceCommissionEntity> priceCommissonList = new ArrayList<>();
        BigDecimal maxPurchasedPrice = BigDecimal.ZERO;
        for (SettlementRegistUpdateModelItem item : priceCommissonItemList) {
            SettlementMethodPriceCommissionEntity priceCommission =
                            ApplicationContextUtility.getBean(SettlementMethodPriceCommissionEntity.class);
            BigDecimal maxPrice = conversionUtility.toBigDecimal(item.getMaxPrice());
            BigDecimal commission = conversionUtility.toBigDecimal(item.getCommission());
            if (maxPrice != null && commission != null) {
                // 上限金額
                priceCommission.setMaxPrice(conversionUtility.toBigDecimal(item.getMaxPrice()));
                // 手数料
                priceCommission.setCommission(conversionUtility.toBigDecimal(item.getCommission()));
                priceCommissonList.add(priceCommission);
                if (maxPurchasedPrice.compareTo(priceCommission.getMaxPrice()) < 0) {
                    maxPurchasedPrice = priceCommission.getMaxPrice();
                }
            }
        }
        settlementMethodDto.setSettlementMethodPriceCommissionEntityList(priceCommissonList);
        settlementMethod.setMaxPurchasedPrice(maxPurchasedPrice);

        return settlementMethodDto;
    }

    /**
     * ページへの変換処理
     *
     * @param settlementRegistUpdateModel 決済方法登録・更新確認画面ページ
     */
    public void toPageForLoad(SettlementRegistUpdateModel settlementRegistUpdateModel) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 表示用にクレジット情報を設定する
        // ｸﾚｼﾞｯﾄｶｰﾄﾞ登録機能有効化ﾌﾗｸﾞ
        if (settlementRegistUpdateModel.isEnableCardNoHolding()) {
            settlementRegistUpdateModel.setEnableCardNoHoldingForDisp(HTypeEffectiveFlag.VALID.getLabel());
        } else {
            settlementRegistUpdateModel.setEnableCardNoHoldingForDisp(HTypeEffectiveFlag.INVALID.getLabel());
        }
        // ｸﾚｼﾞｯﾄ3Dｾｷｭｱ有効化ﾌﾗｸﾞ
        if (settlementRegistUpdateModel.isEnable3dSecure()) {
            settlementRegistUpdateModel.setEnable3dSecureForDisp(HTypeEffectiveFlag.VALID.getLabel());
        } else {
            settlementRegistUpdateModel.setEnable3dSecureForDisp(HTypeEffectiveFlag.INVALID.getLabel());
        }
        // ｸﾚｼﾞｯﾄ分割支払有効化ﾌﾗｸﾞ
        if (settlementRegistUpdateModel.isEnableInstallment()) {
            settlementRegistUpdateModel.setEnableInstallmentForDisp(HTypeEffectiveFlag.VALID.getLabel());
        } else {
            settlementRegistUpdateModel.setEnableInstallmentForDisp(HTypeEffectiveFlag.INVALID.getLabel());
        }
        // ｸﾚｼﾞｯﾄﾘﾎﾞ有効化ﾌﾗｸﾞ
        if (settlementRegistUpdateModel.isEnableRevolving()) {
            settlementRegistUpdateModel.setEnableRevolvingForDisp(HTypeEffectiveFlag.VALID.getLabel());
        } else {
            settlementRegistUpdateModel.setEnableRevolvingForDisp(HTypeEffectiveFlag.INVALID.getLabel());
        }

        // 画面表示用に利用可能最大金額をセットする
        List<SettlementRegistUpdateModelItem> priceCommissonItemList =
                        settlementRegistUpdateModel.getPriceCommissionItemList();
        if (priceCommissonItemList != null) {
            BigDecimal maxPurchasedPrice = BigDecimal.ZERO;
            for (SettlementRegistUpdateModelItem item : priceCommissonItemList) {
                BigDecimal maxPrice = conversionUtility.toBigDecimal(item.getMaxPrice());
                BigDecimal commission = conversionUtility.toBigDecimal(item.getCommission());
                if (maxPrice != null && commission != null) {
                    if (maxPurchasedPrice.compareTo(maxPrice) < 0) {
                        maxPurchasedPrice = maxPrice;
                    }
                }
            }
            settlementRegistUpdateModel.setMaxPurchasedPrice(maxPurchasedPrice.toString());
        }

        if (settlementRegistUpdateModel.getConfigMode() == 2) {
            // 修正箇所の検出
            SettlementMethodEntity original =
                            CopyUtil.deepCopy(settlementRegistUpdateModel.getSettlementMethodEntity());
            SettlementMethodEntity modified = CopyUtil.deepCopy(
                            settlementRegistUpdateModel.getSettlementMethodDto().getSettlementMethodEntity());

            // テキストエリアの項目の改行コードを統一
            if (original.getSettlementNotePC() != null) {
                original.setSettlementNotePC(original.getSettlementNotePC().replaceAll("\r\n", "\n"));
            }

            if (original.getSettlementNoteMB() != null) {
                original.setSettlementNoteMB(original.getSettlementNoteMB().replaceAll("\r\n", "\n"));
            }

            if (modified.getSettlementNotePC() != null) {
                modified.setSettlementNotePC(modified.getSettlementNotePC().replaceAll("\r\n", "\n"));
            }

            if (modified.getSettlementNoteMB() != null) {
                modified.setSettlementNoteMB(modified.getSettlementNoteMB().replaceAll("\r\n", "\n"));
            }

            settlementRegistUpdateModel.setModifiedList(DiffUtil.diff(original, modified));

            // 金額別手数料の差分取得
            if (!settlementRegistUpdateModel.isFlat()) {
                List<List<String>> tmpDiffList = new ArrayList<>();
                int index = 0;

                // 変更後の金額別手数料
                for (SettlementMethodPriceCommissionEntity modifiedPriceCommissionYen : settlementRegistUpdateModel.getSettlementMethodDto()
                                                                                                                   .getSettlementMethodPriceCommissionEntityList()) {

                    if (CollectionUtils.isEmpty(
                                    settlementRegistUpdateModel.getSettlementMethodPriceCommissionEntityList())) {
                        // 変更前が金額別手数料未設定の場合
                        tmpDiffList.add(DiffUtil.diff(
                                        new SettlementMethodPriceCommissionEntity(), modifiedPriceCommissionYen));
                    } else {
                        // 変更前の金額別手数料
                        if (settlementRegistUpdateModel.getSettlementMethodPriceCommissionEntityList().size() > index) {
                            // 金額別手数料を更新した場合
                            SettlementMethodPriceCommissionEntity originalPriceCommissionYen =
                                            settlementRegistUpdateModel.getSettlementMethodPriceCommissionEntityList()
                                                                       .get(index);
                            tmpDiffList.add(DiffUtil.diff(originalPriceCommissionYen, modifiedPriceCommissionYen));
                        } else {
                            // 金額別手数料を新たに追加した場合
                            tmpDiffList.add(DiffUtil.diff(
                                            new SettlementMethodPriceCommissionEntity(), modifiedPriceCommissionYen));
                        }
                    }
                    index++;
                }
                settlementRegistUpdateModel.setModifiedPriceCommissionYenList(tmpDiffList);
            }
        }
    }
}
