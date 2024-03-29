/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodCommissionType;
import jp.co.hankyuhanshin.itec.hitmall.dao.conveni.ConvenienceDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodCommissionType.EACH_AMOUNT_YEN;
import static jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodCommissionType.FLAT_YEN;
import static jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType.CONVENIENCE;
import static jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType.CREDIT;

/**
 * 決済方法リスト取得ロジック実装クラス
 *
 * @author negishi
 * @author Kaneko (Itec) チケット#2549対応 2011/12/09
 */
@Component
public class SettlementMethodListGetLogicImpl extends AbstractShopLogic implements SettlementMethodListGetLogic {

    /**
     * カードブランド取得Logic
     */
    private final CardBrandGetLogic cardBrandGetLogic;

    /**
     * 決済方法Dao
     */
    private final SettlementMethodDao settlementMethodDao;

    /**
     * コンビニDao
     */
    private final ConvenienceDao convenienceDao;

    /**
     * CouponUtility
     */
    private final CouponUtility couponUtility;

    /**
     * OrderUtility
     */
    private final OrderUtility orderUtility;

    @Autowired
    public SettlementMethodListGetLogicImpl(CardBrandGetLogic cardBrandGetLogic,
                                            SettlementMethodDao settlementMethodDao,
                                            ConvenienceDao convenienceDao,
                                            CouponUtility couponUtility,
                                            OrderUtility orderUtility) {
        this.cardBrandGetLogic = cardBrandGetLogic;
        this.settlementMethodDao = settlementMethodDao;
        this.convenienceDao = convenienceDao;
        this.couponUtility = couponUtility;
        this.orderUtility = orderUtility;
    }

    /**
     * 実行メソッド
     *
     * @param conditionDto                  決済方法Dao用検索条件DTO
     * @param settlementMethodSeqList       決済方法SEQリスト
     * @param totalGoodsPrice               商品合計金額
     * @param settlementCharge              決済金額
     * @param selectedDeliveryMethodSeqList 配送方法SEQリスト（画面から選択されたもの）
     * @param carriage                      送料（画面から選択された配送方法のやつ）
     * @return 決済Dtoリスト
     */
    @Override
    public List<SettlementDto> execute(SettlementSearchForDaoConditionDto conditionDto,
                                       List<Integer> settlementMethodSeqList,
                                       BigDecimal totalGoodsPrice,
                                       BigDecimal settlementCharge,
                                       List<Integer> selectedDeliveryMethodSeqList,
                                       BigDecimal carriage) {
        return execute(conditionDto, settlementMethodSeqList, totalGoodsPrice, settlementCharge,
                       selectedDeliveryMethodSeqList, carriage, null
                      );
    }

    /**
     * 実行メソッド
     *
     * @param conditionDto                  配送方法Dao用検索条件DTO
     * @param settlementMethodSeqList       決済方法SEQリスト
     * @param totalGoodsPrice               商品合計金額
     * @param settlementCharge              決済金額
     * @param selectedDeliveryMethodSeqList 配送方法SEQリスト（画面から選択されたもの）
     * @param carriage                      送料（画面から選択された配送方法のやつ）
     * @param available                     利用可能、不可能どちらかを指定。null..全部 / true..利用可能のみ / false..利用不可能のみ
     * @return 決済Dtoリスト
     */
    @Override
    public List<SettlementDto> execute(SettlementSearchForDaoConditionDto conditionDto,
                                       List<Integer> settlementMethodSeqList,
                                       BigDecimal totalGoodsPrice,
                                       BigDecimal settlementCharge,
                                       List<Integer> selectedDeliveryMethodSeqList,
                                       BigDecimal carriage,
                                       Boolean available) {

        // パラメータチェック
        checkParameter(conditionDto, settlementMethodSeqList, totalGoodsPrice, settlementCharge,
                       selectedDeliveryMethodSeqList
                      );

        // 決済詳細Dtoリスト取得
        List<SettlementDetailsDto> settlementDetailsList = getSettlementDetailsList(conditionDto);

        // 決済Dtoリスト作成
        List<SettlementDto> settlementDtoList = new ArrayList<>();

        // 取得した決済詳細Dtoリストの件数分ループ
        for (SettlementDetailsDto settlementDetailsDto : settlementDetailsList) {
            // ① 決済Dto作成
            SettlementDto settlementDto = createSettlementDto(settlementDetailsDto);

            // ② 決済手数料・選択区分の設定
            // 決済手数料・選択区分が設定済みであるかどうかを判断するフラグ
            boolean notSettingFlag = true;
            // ②－１ 選択可能決済方法チェック
            notSettingFlag = checkSettlementMethod(settlementMethodSeqList, settlementDetailsDto, settlementDto,
                                                   notSettingFlag
                                                  );

            if (notSettingFlag) {
                // ②－２ 配送方法指定があるかのチェック
                notSettingFlag = checkDeliveryMethod(selectedDeliveryMethodSeqList, settlementDetailsDto, settlementDto,
                                                     notSettingFlag
                                                    );
            }

            if (notSettingFlag) {
                // ②－３ 金額別手数料の対象チェック
                notSettingFlag = checkSettlementMethodCommission(settlementDetailsDto, settlementDto, notSettingFlag);
            }

            if (notSettingFlag) {
                // ②－４ 高額割引適用チェック
                notSettingFlag = checkLargeAmount(totalGoodsPrice, settlementDetailsDto, settlementDto, notSettingFlag);
            }

            if (notSettingFlag) {
                // ②－５ 決済手数料の計算
                setSettlementCommission(settlementCharge, settlementDetailsDto, settlementDto);
            }

            // 判定フラグ初期化
            notSettingFlag = true;

            // ③ 最大決済金額チェック
            // 選択可能な決済方法の場合
            if (settlementDto.isSelectClass()) {
                notSettingFlag = checkMaxSettlementAmount(settlementCharge, settlementDetailsDto, settlementDto,
                                                          notSettingFlag
                                                         );
            }

            // ④ 利用可能最小金額チェック
            if (settlementDto.isSelectClass()) {
                checkMinSettlementAmount(settlementCharge, settlementDetailsDto, settlementDto);
            }

            // ⑤ コンビニ名称エンティティリストのセット
            if (settlementDetailsDto.getSettlementMethodType().equals(CONVENIENCE)) {
                setConvenienceEntityList(conditionDto.getShopSeq(), settlementDto, conditionDto.isLimitToUseConveni());
            }

            // ⑤－２ カードブランドエンティティリストのセット
            if (settlementDetailsDto.getSettlementMethodType().equals(CREDIT)) {
                setCardBrandEntityList(settlementDto);
            }

            // ⑥ 決済Dtoリストにを設定
            setSettlementDto(available, settlementDtoList, settlementDto);
        }
        return settlementDtoList;
    }

    /**
     * 実行メソッド
     *
     * @param settlementMethodSeqList 決済方法SEQリスト
     * @param deliveryMethodSeqList   配送方法SEQリスト
     * @param allowCreditFlag         クレジット決済許可フラグ
     * @return 決済詳細DTOリスト
     */
    @Override
    public List<SettlementDto> execute(List<Integer> settlementMethodSeqList,
                                       List<Integer> deliveryMethodSeqList,
                                       Boolean allowCreditFlag) {
        Integer shopSeq = 1001;
        // 全額クーポン、全額ポイントの決済方法は除外する
        Integer couponSeq = couponUtility.getCouponSettlementMethodSeq();

        Integer freeSeq = orderUtility.getFreeSettlementMethodSeq();

        List<SettlementDetailsDto> settlementDetailsList =
                        settlementMethodDao.getsettlementDetailsListForLp(settlementMethodSeqList, shopSeq,
                                                                          new Integer[] {couponSeq, freeSeq},
                                                                          deliveryMethodSeqList
                                                                         );

        // 決済Dtoリスト作成
        List<SettlementDto> settlementDtoList = new ArrayList<>();

        for (SettlementDetailsDto settlementDetailsDto : settlementDetailsList) {
            SettlementDto settlementDto = createSettlementDto(settlementDetailsDto);
            // コンビニ名称エンティティリストのセット
            if (CONVENIENCE == settlementDetailsDto.getSettlementMethodType()) {
                setConvenienceEntityList(shopSeq, settlementDto, true);
            }

            // カードブランドエンティティリストのセット
            if (CREDIT == settlementDetailsDto.getSettlementMethodType()) {
                // クレジット決済が許可されていない場合、スキップ
                if (!allowCreditFlag) {
                    continue;
                }
                setCardBrandEntityList(settlementDto);
            }
            settlementDtoList.add(settlementDto);
        }
        return settlementDtoList;
    }

    /**
     * パラメータのチェックを行います。
     *
     * @param conditionDto                  決済方法Dao用検索条件DTO
     * @param settlementMethodSeqList       決済方法SEQリスト
     * @param totalGoodsPrice               商品合計金額
     * @param settlementCharge              決済金額
     * @param selectedDeliveryMethodSeqList 配送方法SEQリスト（画面から選択されたもの）
     */
    protected void checkParameter(SettlementSearchForDaoConditionDto conditionDto,
                                  List<Integer> settlementMethodSeqList,
                                  BigDecimal totalGoodsPrice,
                                  BigDecimal settlementCharge,
                                  List<Integer> selectedDeliveryMethodSeqList) {

        // 配送方法Dao用検索条件DTO
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);
        // 決済金額
        ArgumentCheckUtil.assertNotNull("conditionDto.settlementCharge", conditionDto.getSettlementCharge());
        // 決済方法SEQリスト
        if (settlementMethodSeqList != null && settlementMethodSeqList.isEmpty()) {
            throwMessage();
        }
        // 商品合計金額
        ArgumentCheckUtil.assertNotNull("totalGoodsPrice", totalGoodsPrice);
        // 決済金額
        ArgumentCheckUtil.assertNotNull("settlementCharge", settlementCharge);
        // 配送方法リストSEQ
        ArgumentCheckUtil.assertNotNull("selectedDeliveryMethodSeqList", selectedDeliveryMethodSeqList);
    }

    /**
     * 決済詳細Dtoリスト取得
     *
     * @param conditionDto 検索条件
     * @param customParams 案件用引数
     * @return 決済方法詳細リスト
     */
    protected List<SettlementDetailsDto> getSettlementDetailsList(SettlementSearchForDaoConditionDto conditionDto,
                                                                  Object... customParams) {
        return settlementMethodDao.getSearchSettlementDetailsList(conditionDto);
    }

    /**
     * 決済Dto作成<br/>
     *
     * @param settlementDetailsDto 決済詳細Dto
     * @param customParams         案件用引数
     * @return 決済Dto
     */
    protected SettlementDto createSettlementDto(SettlementDetailsDto settlementDetailsDto, Object... customParams) {
        SettlementDto settlementDto = getComponent(SettlementDto.class);
        // 決済Dtoに決済詳細Dtoを設定
        settlementDto.setSettlementDetailsDto(settlementDetailsDto);
        // 選択区分を「可」に設定
        settlementDto.setSelectClass(true);
        return settlementDto;
    }

    /**
     * 選択可能決済方法チェック<br/>
     *
     * @param settlementMethodSeqList 決済方法SEQリスト
     * @param settlementDetailsDto    決済詳細DTO
     * @param settlementDto           決済DTO
     * @param notSettingFlag          設定済みフラグ
     * @param customParams            案件用引数
     * @return 設定済みフラグ
     */
    protected boolean checkSettlementMethod(List<Integer> settlementMethodSeqList,
                                            SettlementDetailsDto settlementDetailsDto,
                                            SettlementDto settlementDto,
                                            boolean notSettingFlag,
                                            Object... customParams) {
        if (settlementMethodSeqList != null && !settlementMethodSeqList.contains(
                        settlementDetailsDto.getSettlementMethodSeq())) {
            // 決済手数料を０に設定
            settlementDto.setCharge(BigDecimal.ZERO);
            // 選択区分を「不可」に設定
            settlementDto.setSelectClass(false);
            notSettingFlag = false;
        }
        return notSettingFlag;
    }

    /**
     * 配送方法指定チェック<br/>
     *
     * @param selectedDeliveryMethodSeqList 配送方法SEQリスト
     * @param settlementDetailsDto          決済詳細DTO
     * @param settlementDto                 決済DTO
     * @param notSettingFlag                設定済みフラグ
     * @param customParams                  案件用引数
     * @return 設定済みフラグ
     */
    protected boolean checkDeliveryMethod(List<Integer> selectedDeliveryMethodSeqList,
                                          SettlementDetailsDto settlementDetailsDto,
                                          SettlementDto settlementDto,
                                          boolean notSettingFlag,
                                          Object... customParams) {
        Integer deliveryMethodSeqRelatedToSettlment = settlementDetailsDto.getDeliveryMethodSeq();// .deliveryMethodSeq;

        if (deliveryMethodSeqRelatedToSettlment == null) {
            return notSettingFlag;
        }
        for (Integer selectedDeliveryMethodSeq : selectedDeliveryMethodSeqList) {
            if (deliveryMethodSeqRelatedToSettlment.compareTo(selectedDeliveryMethodSeq) != 0) {
                // 決済手数料を０に設定
                settlementDto.setCharge(BigDecimal.ZERO);
                // 選択区分を「不可」に設定
                settlementDto.setSelectClass(false);
                notSettingFlag = false;
                break;
            }
        }
        return notSettingFlag;
    }

    /**
     * 決済手数料チェック<br/>
     *
     * @param settlementDetailsDto 決済詳細DTO
     * @param settlementDto        決済DTO
     * @param notSettingFlag       設定済みフラグ
     * @param customParams         案件用引数
     * @return 設定済みフラグ
     */
    protected boolean checkSettlementMethodCommission(SettlementDetailsDto settlementDetailsDto,
                                                      SettlementDto settlementDto,
                                                      boolean notSettingFlag,
                                                      Object... customParams) {
        HTypeSettlementMethodCommissionType settlementMethodClass =
                        settlementDetailsDto.getSettlementMethodCommissionType();
        if (settlementDetailsDto.getMaxPrice() == null && (settlementMethodClass.equals(EACH_AMOUNT_YEN))) {
            // || settlementMethodClass.equals(EACH_AMOUNT_PERCENTAGE))) {
            // 決済手数料を０に設定
            settlementDto.setCharge(BigDecimal.ZERO);
            // 選択区分を「不可」に設定
            settlementDto.setSelectClass(false);
            notSettingFlag = false;
        }
        return notSettingFlag;
    }

    /**
     * 高額割引適用チェック<br/>
     *
     * @param totalGoodsPrice      商品合計金額
     * @param settlementDetailsDto 決済詳細DTO
     * @param settlementDto        決済DTO
     * @param notSettingFlag       設定済みフラグ
     * @param customParams         案件用引数
     * @return 設定済みフラグ
     */
    protected boolean checkLargeAmount(BigDecimal totalGoodsPrice,
                                       SettlementDetailsDto settlementDetailsDto,
                                       SettlementDto settlementDto,
                                       boolean notSettingFlag,
                                       Object... customParams) {
        BigDecimal largeAmountDiscountPrice =
                        settlementDetailsDto.getLargeAmountDiscountPrice();// .largeAmountDiscountPrice;
        if (largeAmountDiscountPrice != null && largeAmountDiscountPrice.compareTo(new BigDecimal(0)) > 0
            && totalGoodsPrice.compareTo(largeAmountDiscountPrice) >= 0) {
            // 決済手数料を設定
            settlementDto.setCharge(settlementDetailsDto.getLargeAmountDiscountCommission());
            // 選択区分を「可」に設定
            settlementDto.setSelectClass(true);
            notSettingFlag = false;
        }
        return notSettingFlag;
    }

    /**
     * 決済手数料の計算<br/>
     *
     * @param settlementCharge     決済金額
     * @param settlementDetailsDto 決済詳細DTO
     * @param settlementDto        決済DTO
     * @param customParams         案件用引数
     */
    protected void setSettlementCommission(BigDecimal settlementCharge,
                                           SettlementDetailsDto settlementDetailsDto,
                                           SettlementDto settlementDto,
                                           Object... customParams) {
        // 金額別（円）の場合
        if (settlementDetailsDto.getSettlementMethodCommissionType().equals(EACH_AMOUNT_YEN)) {
            // 決済手数料に手数料を設定
            settlementDto.setCharge(settlementDetailsDto.getCommission());
            // 選択区分を「可」に設定
            settlementDto.setSelectClass(true);

            // 金額別（％）の場合
            // } else if
            // (settlementDetailsDto.settlementMethodCommissionType.equals(EACH_AMOUNT_PERCENTAGE))
            // {
            // // 決済手数料 = （商品金額合計 ＋ 送料 + その他金額合計） × 手数料（率） ÷ 100
            // BigDecimal settlementCommision =
            // settlementCharge.multiply(settlementDetailsDto.commission).divide(new
            // BigDecimal(100));
            // // 小数点切捨て
            // settlementCommision = settlementCommision.setScale(0,
            // RoundingMode.DOWN);
            // // 決済手数料を設定
            // settlementDto.charge = settlementCommision;
            // // 選択区分を「可」に設定
            // settlementDto.selectClass = true;

            // 一律（円）の場合
        } else if (settlementDetailsDto.getSettlementMethodCommissionType().equals(FLAT_YEN)) {
            // 決済手数料に一律手数料を設定
            settlementDto.setCharge(settlementDetailsDto.getEqualsCommission());
            // 選択区分を「可」に設定
            settlementDto.setSelectClass(true);

            // 一律（％）の場合
            // } else if
            // (settlementDetailsDto.settlementMethodCommissionType.equals(FLAT_PERCENTAGE))
            // {
            // // 決済手数料 = (商品金額合計 + 送料 + その他金額合計) × 一律手数料（率） ÷ 100
            // BigDecimal settlementCommission =
            // settlementCharge.multiply(settlementDetailsDto.equalsCommission).divide(new
            // BigDecimal(100));
            // // 小数点切捨て
            // settlementCommission = settlementCommission.setScale(0,
            // RoundingMode.DOWN);
            // // 決済手数料を設定
            // settlementDto.charge = settlementCommission;
            // // 選択区分を「可」に設定
            // settlementDto.selectClass = true;
        }
    }

    /**
     * 最大決済金額チェック<br/>
     *
     * @param settlementCharge     決済金額
     * @param settlementDetailsDto 決済詳細DTO
     * @param settlementDto        決済DTO
     * @param notSettingFlag       設定済みフラグ
     * @param customParams         案件用引数
     * @return 設定済みフラグ
     */
    protected boolean checkMaxSettlementAmount(BigDecimal settlementCharge,
                                               SettlementDetailsDto settlementDetailsDto,
                                               SettlementDto settlementDto,
                                               boolean notSettingFlag,
                                               Object... customParams) {
        // ④－１ ショップ別最大決済金額チェック
        // 最大決済金額をシステムプロパティから取得
        BigDecimal maxSettlementAmount = new BigDecimal(PropertiesUtil.getSystemPropertiesValue(ORDER_MAX_AMOUNT_KEY));
        BigDecimal settlementAmount = settlementCharge.add(settlementDto.getCharge());

        // 決済金額 > システムプロパティ.最大決済金額の場合
        if (BigDecimal.ZERO.compareTo(maxSettlementAmount) < 0 && settlementAmount.compareTo(maxSettlementAmount) > 0) {
            // 決済手数料を０に設定
            settlementDto.setCharge(BigDecimal.ZERO);
            // 選択区分を「不可」に設定
            settlementDto.setSelectClass(false);
            notSettingFlag = false;
        }

        if (notSettingFlag) {
            // ④－２ 決済方法別最大決済金額チェック
            // 決済金額 > 決済詳細.最大決済金額
            maxSettlementAmount = settlementDetailsDto.getMaxPurchasedPrice();
            if (maxSettlementAmount != null && settlementAmount.compareTo(maxSettlementAmount) > 0) {
                // 決済手数料を０に設定
                settlementDto.setCharge(BigDecimal.ZERO);
                // 選択区分を「不可」に設定
                settlementDto.setSelectClass(false);
            }
        }
        return notSettingFlag;
    }

    /**
     * 利用可能最小金額チェック<br/>
     *
     * @param settlementCharge     決済金額
     * @param settlementDetailsDto 決済詳細DTO
     * @param settlementDto        決済DTO
     * @param customParams         案件用引数
     */
    protected void checkMinSettlementAmount(BigDecimal settlementCharge,
                                            SettlementDetailsDto settlementDetailsDto,
                                            SettlementDto settlementDto,
                                            Object... customParams) {
        // 決済金額(商品金額+配送料+決済手数料)の取得
        BigDecimal settlementAmount = settlementCharge.add(settlementDto.getCharge());
        // 利用可能最小金額の取得
        BigDecimal minSettlementAmount = settlementDetailsDto.getMinPurchasedPrice();
        // 決済金額 < 利用可能最小金額 の場合 選択不可
        // 決済金額 = 利用可能最小金額 の場合 選択可能
        // 決済金額 > 利用可能最小金額 の場合 選択可能
        if (minSettlementAmount.compareTo(settlementAmount) > 0) {
            // 選択区分を「不可」に設定
            settlementDto.setSelectClass(false);
        }
    }

    /**
     * コンビニ名称エンティティリストの設定<br/>
     *
     * @param shopSeq           ショップSEQ
     * @param settlementDto     決済DTO
     * @param limitToUseConveni 使うコンビニに制限するか true:制限する
     * @param customParams      案件用引数
     */
    protected void setConvenienceEntityList(Integer shopSeq,
                                            SettlementDto settlementDto,
                                            boolean limitToUseConveni,
                                            Object... customParams) {
        // コンビニリスト取得
        List<ConvenienceEntity> convenienceEntityList = convenienceDao.getConvenienceList(shopSeq, limitToUseConveni);
        // 決済Dtoにコンビニリストを設定
        settlementDto.setConvenienceEntityList(convenienceEntityList);
    }

    /**
     * カードブランドエンティティリストの設定<br/>
     *
     * @param settlementDto 決済DTO
     * @param customParams  案件用引数
     */
    protected void setCardBrandEntityList(SettlementDto settlementDto, Object... customParams) {
        // カードブランドリスト取得
        List<CardBrandEntity> cardBrandEntityList = cardBrandGetLogic.execute(true);
        // 決済Dtoにカードブランドリストを設定
        settlementDto.setCardBrandEntityList(cardBrandEntityList);
    }

    /**
     * 決済Dtoリストへ設定<br/>
     *
     * @param available         利用可能、不可能どちらかを指定。null..全部 / true..利用可能のみ / false..利用不可能のみ
     * @param settlementDtoList 決済DTOリスト
     * @param settlementDto     決済DTO
     * @param customParams      案件用引数
     */
    protected void setSettlementDto(Boolean available,
                                    List<SettlementDto> settlementDtoList,
                                    SettlementDto settlementDto,
                                    Object... customParams) {
        if (available == null) {
            settlementDtoList.add(settlementDto);
        } else if (available && settlementDto.isSelectClass()) {
            settlementDtoList.add(settlementDto);
        } else if (!available && !settlementDto.isSelectClass()) {
            settlementDtoList.add(settlementDto);
        }
    }

}
