/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsListGetBySeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.SettlementMethodSelectListGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 決済方法選択可能リスト取得サービス実装クラス
 *
 * @author negishi
 * @author matsumoto(itec) 2012/02/07 #2761 対応
 */
@Service
public class SettlementMethodSelectListGetServiceImpl extends AbstractShopService
                implements SettlementMethodSelectListGetService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SettlementMethodSelectListGetServiceImpl.class);

    /**
     * クーポン関連Utility
     */
    private final CouponUtility couponUtility;

    /**
     * 受注業務ユーティリティ
     */
    private final OrderUtility orderUtility;

    /**
     * 金額計算ユーティリティ
     */
    private final CalculatePriceUtility calculatePriceUtility;

    /**
     * 決済方法DAO
     */
    private final SettlementMethodDao settlementMethodDao;

    /**
     * 決済方法リスト取得ロジック
     */
    private final SettlementMethodListGetLogic settlementMethodListGetLogic;

    /**
     * 商品詳細情報リスト取得
     */
    private final GoodsDetailsListGetBySeqLogic goodsDetailsListGetBySeqLogic;

    /**
     * 決済金額にポイント割引を含めるか否か
     * <pre>
     * 新規受注時の決済方法リスト作成時にはポイント割引は含めない
     * （理由）
     *  ・決済方法選択画面と同じ画面で、ポイントを使うか否かや、ポイント数が変更可能な為、
     *   ポイント数は除外した決済金額で利用可能な決済方法を取得する
     * </pre>
     */
    protected boolean isChargeIncludPointDiscount;

    @Autowired
    public SettlementMethodSelectListGetServiceImpl(CouponUtility couponUtility,
                                                    OrderUtility orderUtility,
                                                    CalculatePriceUtility calculatePriceUtility,
                                                    SettlementMethodDao settlementMethodDao,
                                                    SettlementMethodListGetLogic settlementMethodListGetLogic,
                                                    GoodsDetailsListGetBySeqLogic goodsDetailsListGetBySeqLogic) {

        this.couponUtility = couponUtility;
        this.orderUtility = orderUtility;
        this.calculatePriceUtility = calculatePriceUtility;
        this.settlementMethodDao = settlementMethodDao;
        this.settlementMethodListGetLogic = settlementMethodListGetLogic;
        this.goodsDetailsListGetBySeqLogic = goodsDetailsListGetBySeqLogic;
    }

    /**
     * 実行メソッド<br/>
     * フロントで使用
     *
     * @param receiveOrderDto 受注DTO
     * @param available       利用可能、不可能どちらかを指定。null..全部 / true..利用可能のみ / false..利用不可能のみ
     * @param allowCreditFlag クレジット決済許可フラグ　true:クレジット決済許可 false:クレジット決済禁止
     * @return 決済Dtoリスト
     */
    @Override
    public List<SettlementDto> execute(ReceiveOrderDto receiveOrderDto,
                                       Boolean available,
                                       Boolean allowCreditFlag,
                                       String memberInfoId,
                                       HTypeSiteType siteType) {
        // パラメータチェック
        checkParameter(receiveOrderDto);

        // 決済金額を算出
        BigDecimal settlementCharge = getSettlementCharge(receiveOrderDto);
        // 決済方法Dao用検索条件DTOを作成
        SettlementSearchForDaoConditionDto conditionDto =
                        calculatePriceUtility.getSettlementSearchForDaoConditionDto(receiveOrderDto, siteType,
                                                                                    receiveOrderDto.getOrderSettlementEntity()
                                                                                   );
        // フロントなので使うコンビニに絞込して取得したい
        conditionDto.setLimitToUseConveni(true);
        BigDecimal totalGoodsPrice = getTotalGoodsPrice(receiveOrderDto);
        // 選択された配送方法SEQリストを取得
        List<Integer> deliveryMethodSeqList = orderUtility.createSelectDeliveryMethodSeqList(receiveOrderDto);
        // 選択された配送方法の送料を取得
        BigDecimal carriage = receiveOrderDto.getOrderSettlementEntity().getCarriage();
        // 決済方法リスト取得ロジック実行
        List<SettlementDto> settlementList =
                        settlementMethodListGetLogic.execute(conditionDto, null, totalGoodsPrice, settlementCharge,
                                                             deliveryMethodSeqList, carriage, available
                                                            );
        // 全額割引決済の編集
        editSettlementList(receiveOrderDto, settlementList, allowCreditFlag, memberInfoId, siteType);

        return settlementList;
    }

    /**
     * パラメータのチェックを行います。
     *
     * @param receiveOrderDto 受注Dto
     */
    protected void checkParameter(ReceiveOrderDto receiveOrderDto) {
        ArgumentCheckUtil.assertNotNull("receiveOrderDto", receiveOrderDto);
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        ArgumentCheckUtil.assertNotNull("orderSettlementEntity", orderSettlementEntity);
        ArgumentCheckUtil.assertNotNull("goodsPriceTotal", orderSettlementEntity.getGoodsPriceTotal());
        ArgumentCheckUtil.assertNotNull("carriage", orderSettlementEntity.getCarriage());
        ArgumentCheckUtil.assertNotNull("couponDiscountPrice", orderSettlementEntity.getCouponDiscountPrice());
        ArgumentCheckUtil.assertNotNull("othersPriceTotal", orderSettlementEntity.getOthersPriceTotal());
    }

    /**
     * 決済金額を算出します<br />
     *
     * @param receiveOrderDto 受注Dto
     * @return 決済金額
     */
    protected BigDecimal getSettlementCharge(ReceiveOrderDto receiveOrderDto) {

        // 定期次回決済方法作成の場合は、次回の決済金額（税込）を返す
        if (receiveOrderDto.isPeriodicOrderFlag()) {
            return calculatePriceUtility.getCommissionLessTaxIncludedOrderPrice(
                            receiveOrderDto.getOrderNextSettlementEntity());
        }
        return calculatePriceUtility.getCommissionLessTaxIncludedOrderPrice(receiveOrderDto.getOrderSettlementEntity());

    }

    /**
     * 商品合計金額を算出します<br />
     *
     * @param receiveOrderDto 受注Dto
     * @return 商品合計金額
     */
    protected BigDecimal getTotalGoodsPrice(ReceiveOrderDto receiveOrderDto) {
        BigDecimal totalGoodsPrice;
        // 定期次回決済方法作成の場合は、次回商品金額合計を利用する
        if (receiveOrderDto.isPeriodicOrderFlag()) {
            totalGoodsPrice = receiveOrderDto.getOrderNextSettlementEntity().getGoodsPriceTotal();
        } else {
            totalGoodsPrice = receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal();
        }
        return totalGoodsPrice;
    }

    /**
     * 実行メソッド
     *
     * @param receiveOrderDto 受注Dto
     * @param allowCreditFlag クレジット決済許可フラグ　true:クレジット決済許可 false:クレジット決済禁止
     * @return 決済Dtoリスト
     */
    @Override
    public List<SettlementDto> execute(ReceiveOrderDto receiveOrderDto,
                                       Boolean allowCreditFlag,
                                       String memberInfoId,
                                       HTypeSiteType siteType) {
        // パラメータチェック
        checkParameter(receiveOrderDto);
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        if (orderSettlementEntity.getOrderSeq() != null) {
            // 受注修正の場合は決済金額にポイント割引を含めるようにする
            // 無理やりだが、IF拡張するのが手間なので 受注SEQ の有無で判断している
            isChargeIncludPointDiscount = true;
        }

        // 決済金額を算出
        BigDecimal goodsPriceTotal = getTotalGoodsPrice(receiveOrderDto);
        BigDecimal carriage = orderSettlementEntity.getCarriage();
        BigDecimal commissionLessOrderPrice = getSettlementCharge(receiveOrderDto);

        // 決済方法Dao用検索条件DTOを作成
        SettlementSearchForDaoConditionDto conditionDto =
                        calculatePriceUtility.getSettlementSearchForDaoConditionDto(receiveOrderDto, siteType,
                                                                                    orderSettlementEntity
                                                                                   );
        // 絞り込んで取得したい
        conditionDto.setLimitToUseConveni(true);
        // 選択された配送方法SEQリストを取得
        List<Integer> deliveryMethodSeqList = orderUtility.createSelectDeliveryMethodSeqList(receiveOrderDto);

        // 決済方法リスト取得ロジック実行
        List<SettlementDto> settlementList = settlementMethodListGetLogic.execute(conditionDto, null, goodsPriceTotal,
                                                                                  commissionLessOrderPrice,
                                                                                  deliveryMethodSeqList, carriage
                                                                                 );

        editSettlementList(receiveOrderDto, settlementList, allowCreditFlag, memberInfoId, siteType);

        return settlementList;
    }

    /**
     * 商品詳細情報取得
     * <pre>
     * 数量0は除外
     * セット商品の子商品は除外
     * </pre>
     *
     * @param receiveOrderDto 受注Dto
     * @return 商品詳細情報
     */
    protected List<GoodsDetailsDto> getGoodsDetailsList(ReceiveOrderDto receiveOrderDto) {
        // 商品SEQのセットを初期化
        Set<Integer> goodsSeqSet = new LinkedHashSet<>();

        for (OrderGoodsEntity orderGoodsEntity : receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()) {

            // 数量 > 0 が対象
            if (BigDecimal.ZERO.compareTo(orderGoodsEntity.getGoodsCount()) < 0) {
                goodsSeqSet.add(orderGoodsEntity.getGoodsSeq());
            }
        }

        // 商品がない場合
        if (goodsSeqSet.isEmpty()) {
            return null;
        }
        // 商品詳細情報取得
        return goodsDetailsListGetBySeqLogic.execute(new ArrayList<>(goodsSeqSet));
    }

    /**** 選択可能絞り込み配送決済共通処理 ****/

    /**
     * list1のリストデータからカンマ区切りのString型リストデータにないものを削除する。<br />
     * list1がnullの場合はカンマ区切りのString型リストデータの内容をセットする。<br />
     *
     * @param strSeqList 絞り込み元になるカンマ区切りの文字列型リスト
     * @param list1      絞り込み対象のリスト
     * @return 絞りこんだリスト
     */
    protected List<Integer> stringToList(String strSeqList, List<Integer> list1) {
        List<Integer> list2 = stringToIntegerList(strSeqList);
        return narrowingList(list1, list2);
    }

    /**
     * リストデータの絞り込み<br/>
     * <p>
     * list1のデータがlist2にない場合、list1から削除します。
     * list1がnullの場合list2のデータを全て移行します。
     *
     * @param list1 データリスト
     * @param list2 データリスト
     * @return 絞り込んだリスト
     */
    protected List<Integer> narrowingList(List<Integer> list1, List<Integer> list2) {
        if (list1 == null) {
            list1 = list2;
        } else {
            for (int index = 0; index < list1.size() && list2 != null; index++) {
                if (!list2.contains(list1.get(index))) {
                    list1.remove(index);
                    index--;
                }
            }
        }
        return list1;
    }

    /**
     * カンマ区切りの文字列をInteger型のリストにして返す。<br/>
     *
     * @param str カンマ区切り文字列
     * @return Integer型リスト
     */
    protected List<Integer> stringToIntegerList(String str) {
        if (str == null) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        String[] strs = str.split(SEPARATOR);
        for (String s : strs) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    /**
     * 決済方法リストの編集を行う。<br />
     * <pre>
     * 全額ポイント決済、全額クーポン決済は利用できない場合、削除する。
     * 全額クーポン決済が利用可能な場合には他の決済方法はすべて削除する。
     * </pre>
     *
     * @param receiveOrderDto 受注DTO
     * @param settlementList  選択可能決済方法リスト
     * @param allowCreditFlag クレジット決済許可フラグ　true:クレジット決済許可 false:クレジット決済禁止
     */
    protected void editSettlementList(ReceiveOrderDto receiveOrderDto,
                                      List<SettlementDto> settlementList,
                                      Boolean allowCreditFlag,
                                      String memberInfoId,
                                      HTypeSiteType siteType) {
        OrderSettlementEntity settlementEntity = receiveOrderDto.getOrderSettlementEntity();
        // 受注修正時には orderTempDto は作られない
        if (receiveOrderDto.getOrderTempDto() != null) {
            // 初期化
            receiveOrderDto.getOrderTempDto().setCanUseCouponSettlementFlg(false);
            receiveOrderDto.getOrderTempDto().setCanUseAllPointSettlementFlg(false);
        }
        // 受注修正時の処理か
        boolean isOrderUpdate = receiveOrderDto.getOrderIndexEntity() != null;

        // ポイント割引額
        BigDecimal pointDiscountPrice = settlementEntity.getPointDiscountPrice();

        // クーポン割引により注文金額が0円になっているか
        boolean canUseCouponSettlement = false;

        // クーポン割引額が0円か
        boolean isCouponDiscountPriceEqualsZero =
                        settlementEntity.getCouponDiscountPrice().compareTo(BigDecimal.ZERO) == 0;
        // ポイント利用前受注金額が0円以下
        boolean isPrePointDiscountOrderPriceZeroOrLess =
                        couponUtility.getSettlementCharge(receiveOrderDto).compareTo(BigDecimal.ZERO) <= 0;

        // ・受注修正時の処理でない場合は、受注金額を 受注金額 + ポイント割引額 - 決済手数料 として判定
        // クーポン優位で決済方法リストを生成するため、全額クーポン決済を表示する場合は、
        // ポイント割引額を含めないで評価したいため。
        // ・受注修正時の処理の場合は、追加料金などの影響があるため、受注金額はそのまま利用する。
        // 受注金額
        BigDecimal orderPriceForCoupon = receiveOrderDto.getOrderSummaryEntity().getOrderPrice();
        // 決済手数料
        BigDecimal settlementCommission = settlementEntity.getSettlementCommission();
        if (!isOrderUpdate) {
            // 受注修正でない場合は決済手数料を減算
            orderPriceForCoupon = orderPriceForCoupon.add(pointDiscountPrice).subtract(settlementCommission);
        }

        // 受注金額が0円か
        boolean isOrderPriceEqualsZero = orderPriceForCoupon.compareTo(BigDecimal.ZERO) == 0;

        // ・クーポン割引により注文金額が0円
        // クーポン割引額が0円でない場合
        // かつ、ポイント利用前受注金額が0円以下
        // かつ、受注金額(※受注修正時でない場合は受注金額 + ポイント割引額 - 決済手数料)が0円
        if (!isCouponDiscountPriceEqualsZero && isPrePointDiscountOrderPriceZeroOrLess && isOrderPriceEqualsZero) {
            // 受注修正時には orderTempDto は作成されない
            if (receiveOrderDto.getOrderTempDto() != null) {
                receiveOrderDto.getOrderTempDto().setCanUseCouponSettlementFlg(true);
            }
            // クーポン割引により注文金額が0円になっている
            canUseCouponSettlement = true;
        }

        // 注文金額を全額ポイントで支払いできるか
        boolean canUseAllPointSettlement = false;
        // 全額クーポン決済が利用可能の場合、全額ポイント決済は利用不可
        if (!canUseCouponSettlement) {
            // 受注修正時の処理で、かつ、会員の場合、常に全額ポイント決済を表示
            if (isOrderUpdate && orderUtility.isMember(memberInfoId, siteType, receiveOrderDto)) {
                canUseAllPointSettlement = true;
                // 受注修正時の処理でない、または、会員でない場合、全額ポイントで支払い可能か判定
            } else {
                // 受注修正時には orderTempDto は作成されない
                if (receiveOrderDto.getOrderTempDto() != null) {
                    receiveOrderDto.getOrderTempDto().setCanUseAllPointSettlementFlg(canUseAllPointSettlement);
                }
            }
        }

        // 全額ポイント決済、全額クーポン決済がリストに含まれている場合 trueに更新する
        boolean hasPointSettlement = false;
        boolean hasCouponSettlement = false;

        Integer couponSettlementSeq = couponUtility.getCouponSettlementMethodSeq();
        for (Iterator<SettlementDto> it = settlementList.iterator(); it.hasNext(); ) {
            SettlementDto dto = it.next();

            // 全額ポイント決済、全額クーポン決済は利用できない場合、削除する
            // 全額クーポン決済が利用可能な場合には他の決済方法はすべて削除する
            if (dto.getSettlementDetailsDto().getSettlementMethodType() == HTypeSettlementMethodType.DISCOUNT) {
                Integer seq = dto.getSettlementDetailsDto().getSettlementMethodSeq();

                if (couponSettlementSeq.compareTo(seq) == 0) {
                    hasCouponSettlement = true;

                    // 全額クーポン決済が利用不可能であれば、利用可能リストから削除する
                    if (!canUseCouponSettlement) {
                        it.remove();
                    } else {
                        // 決済方法が利用可能であれば選択可能とする
                        // バックからの決済方法取得時には明示的に選択可能とする必要がある
                        dto.setSelectClass(true);
                    }
                }
            } else if (dto.getSettlementDetailsDto().getSettlementMethodType() == HTypeSettlementMethodType.CREDIT) {
                // クレジット決済を利用可能リストから削除
                if (!allowCreditFlag) {
                    it.remove();
                }

            } else {
                // 他の決済方法
                // 全額クーポン決済が利用可能な場合、利用可能リストから削除する
                if (canUseCouponSettlement) {
                    it.remove();
                }
            }
        }

        // 商品に決済方法が紐付けられている場合、ポイント決済、クーポン決済がリストに存在しないので、追加する
        if (!hasPointSettlement && canUseAllPointSettlement) {

            // 表示順によるソートを実施
            Collections.sort(settlementList, new Comparator<SettlementDto>() {
                /**
                 * 表示順の比較を行う
                 * @param o1 決済方法１
                 * @param o2 決済方法２
                 * @return 決済方法１の表示順 < 決済方法１の表示順の場合、 -1, 決済方法１の表示順 == 決済方法１の表示順の場合、 0, 決済方法１の表示順 > 決済方法１の表示順の場合、1
                 */
                @Override
                public int compare(SettlementDto o1, SettlementDto o2) {
                    return o1.getSettlementDetailsDto()
                             .getOrderDisplay()
                             .compareTo(o2.getSettlementDetailsDto().getOrderDisplay());
                }
            });
        }

        if (!hasCouponSettlement && canUseCouponSettlement) {
            // クーポン決済は単独で表示されるのでソート不要
            settlementList.add(createSettlementDto(couponUtility.getCouponSettlementMethodSeq()));
        }
        return;
    }

    /**
     * 決済方法SEQを元に決済DTO を作成する。<br />
     * <pre>
     * 編集方法はSettlementMethodListGetLogicImplと同様。
     * </pre>
     *
     * @param seq 全額決済方法のSEQ
     * @return 決済DTO
     */
    protected SettlementDto createSettlementDto(Integer seq) {
        SettlementMethodEntity entity = settlementMethodDao.getEntity(seq);
        SettlementDto dto = getComponent(SettlementDto.class);
        SettlementDetailsDto details = getComponent(SettlementDetailsDto.class);

        // エンティティと詳細DTOの共通項目をコピーしてセット
        try {
            BeanUtils.copyProperties(details, entity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // 開発時以外発生しない
            LOGGER.error("例外処理が発生しました", e);
            throw new RuntimeException(e);
        }
        dto.setSettlementDetailsDto(details);

        // 選択可能にする
        dto.setSelectClass(true);

        // 高額割引手数料は0円
        dto.setCharge(BigDecimal.ZERO);
        return dto;
    }

}
