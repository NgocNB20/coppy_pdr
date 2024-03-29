/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponCodeCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponTimeCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * クーポンチェックLogic実装クラス<br/>
 *
 * @author s_tsuru
 */
@Component
public class CouponCheckLogicImpl extends AbstractShopLogic implements CouponCheckLogic {

    /**
     * 受注サマリDao
     */
    private final OrderSummaryDao orderSummaryDao;

    /**
     * クーポンDAO
     */
    private final CouponDao couponDao;

    /**
     * クーポンコードチェックロジック
     */
    private final CouponCodeCheckLogic couponCodeCheckLogic;

    /**
     * クーポン開催日時チェックロジック
     */
    private final CouponTimeCheckLogic couponTimeCheckLogic;

    /**
     * 商品DAO
     */
    private final GoodsDao goodsDao;

    /**
     * 会員DAO
     */
    private final MemberInfoDao memberInfoDao;

    /**
     * 日付Utility
     */
    private final DateUtility dateUtility;

    /**
     * 注文Utility
     */
    private final OrderUtility orderUtility;

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    @Autowired
    public CouponCheckLogicImpl(OrderSummaryDao orderSummaryDao,
                                CouponDao couponDao,
                                CouponCodeCheckLogic couponCodeCheckLogic,
                                CouponTimeCheckLogic couponTimeCheckLogic,
                                GoodsDao goodsDao,
                                MemberInfoDao memberInfoDao,
                                DateUtility dateUtility,
                                OrderUtility orderUtility,
                                ConversionUtility conversionUtility) {
        this.orderSummaryDao = orderSummaryDao;
        this.couponDao = couponDao;
        this.couponCodeCheckLogic = couponCodeCheckLogic;
        this.couponTimeCheckLogic = couponTimeCheckLogic;
        this.goodsDao = goodsDao;
        this.memberInfoDao = memberInfoDao;
        this.dateUtility = dateUtility;
        this.orderUtility = orderUtility;
        this.conversionUtility = conversionUtility;
    }

    /**
     * 新規登録クーポンチェック処理。
     *
     * @param coupon チェック対象のクーポン
     */
    @Override
    public void checkForRegist(CouponEntity coupon) {

        // 対象商品重複入力チェック
        targetGoodsDuplicationCheck(coupon);
        // 対象会員重複入力チェック
        targetMembersDuplicationCheck(coupon);

        // クーポンIDが既存のクーポンと重複していないことを確認
        // 重複している場合はエラーメッセージをセットする
        String couponId = coupon.getCouponId();
        Integer shopSeq = 1001;
        int idCount = couponDao.checkCouponId(couponId, shopSeq);
        // 登録しようとしたクーポンIDで1件以上のクーポンが取得できた場合はエラーとする
        if (idCount != 0) {
            addErrorMessage(MSGCD_REPETITION_COUPONID);
        }

        // 開始日時が現在より未来であることを確認
        if (couponTimeCheckLogic.execute(coupon.getCouponStartTime()) <= 0) {
            addErrorMessage(MSGCD_CANNOT_SET_STRATTIME);
        }

        // クーポンコードが利用不可であればエラーメッセージをセット
        if (!couponCodeCheckLogic.execute(coupon)) {
            addErrorMessage(MSGCD_REPETITION_COUPONCODE);
        }

        // 対象商品存在チェック
        targetGoodsNotExistCheck(coupon);
        // 対象会員存在チェック
        targetMembersNotExistCheck(coupon);

        // エラーを表示
        if (hasErrorList()) {
            throwMessage();
        }
    }

    /**
     * 更新クーポンチェック処理。
     *
     * @param preUpdateCoupon  更新前のクーポン
     * @param postUpdateCoupon 更新後のクーポン
     */
    @Override
    public void checkForUpdate(CouponEntity preUpdateCoupon, CouponEntity postUpdateCoupon) {
        // 対象商品重複入力チェック
        targetGoodsDuplicationCheck(postUpdateCoupon);
        // 対象会員重複入力チェック
        targetMembersDuplicationCheck(postUpdateCoupon);

        // 利用期間が終了しているものはエラーメッセージをセット
        if (!dateUtility.isOpen(null, preUpdateCoupon.getCouponEndTime())) {
            this.throwMessage(MSGCD_CANNOT_CAHNGE_COUPONDATA);
        }

        // クーポンの開催が終了していないものにチェックを行う
        // 開催中の場合は以下のチェックを行う
        if (couponTimeCheckLogic.execute(preUpdateCoupon.getCouponStartTime()) <= 0) {

            // 開始日時が変更されていないこと
            if (preUpdateCoupon.getCouponStartTime().compareTo(postUpdateCoupon.getCouponStartTime()) != 0) {
                this.addErrorMessage(MSGCD_CANNOT_CHANGE_STARTTIME);
            }

            // クーポンコードが変更されていないこと
            if (!preUpdateCoupon.getCouponCode().equals(postUpdateCoupon.getCouponCode())) {
                this.addErrorMessage(MSGCD_CANNOT_CHANGE_COUPONCODE);
            }

            // 開催前の場合は以下のチェックを行う
        } else {

            // 開始日時が現在より未来であることを確認
            if (couponTimeCheckLogic.execute(postUpdateCoupon.getCouponStartTime()) <= 0) {
                this.addErrorMessage(MSGCD_CANNOT_SET_STRATTIME);
            }

            // クーポンコードが利用不可であればエラーメッセージをセット
            if (!preUpdateCoupon.getCouponCode().equals(postUpdateCoupon.getCouponCode())) {
                if (!couponCodeCheckLogic.execute(postUpdateCoupon)) {
                    this.addErrorMessage(MSGCD_REPETITION_COUPONCODE);
                }
            }
        }
        // クーポンコードが利用不可であればエラーメッセージをセット
        if (!couponCodeCheckLogic.execute(postUpdateCoupon)) {
            addErrorMessage(MSGCD_REPETITION_COUPONCODE);
        }

        // 対象商品存在チェック
        targetGoodsNotExistCheck(postUpdateCoupon);
        // 対象会員存在チェック
        targetMembersNotExistCheck(postUpdateCoupon);

        // エラーを表示
        if (hasErrorList()) {
            throwMessage();
        }
    }

    /**
     * 対象商品重複入力チェック
     *
     * @param coupon チェック対象のクーポン
     */
    protected void targetGoodsDuplicationCheck(CouponEntity coupon) {
        if (coupon.getTargetGoods() == null) {
            return;
        }
        // 対象商品リスト作成
        List<String> targetGoodsList = Arrays.asList(conversionUtility.toDivArray(coupon.getTargetGoods()));
        dataDuplicationCheck(targetGoodsList, MSGCD_DUPLICATION_TARGET_GOODS);
    }

    /**
     * 対象会員重複入力チェック
     *
     * @param coupon チェック対象のクーポン
     */
    protected void targetMembersDuplicationCheck(CouponEntity coupon) {
        if (coupon.getTargetMembers() == null) {
            return;
        }
        // 対象会員リスト作成
        List<String> targetMembersList = Arrays.asList(conversionUtility.toDivArray(coupon.getTargetMembers()));
        dataDuplicationCheck(targetMembersList, MSGCD_DUPLICATION_TARGET_MEMBERS);
    }

    /**
     * 入力データ重複チェック処理。
     *
     * @param checkList   重複チェックリスト
     * @param messageCode メッセージコード
     */
    protected void dataDuplicationCheck(List<String> checkList, String messageCode) {
        Set<String> checkSet = new HashSet<>();
        Set<String> messageSet = new HashSet<>();
        for (String data : checkList) {
            if (checkSet.contains(data)) {
                if (!messageSet.contains(data)) {
                    messageSet.add(data);
                    addErrorMessage(messageCode, new Object[] {data});
                }
            } else {
                checkSet.add(data);
            }
        }
    }

    /**
     * 対象商品存在チェック
     *
     * @param coupon チェック対象のクーポン
     */
    protected void targetGoodsNotExistCheck(CouponEntity coupon) {
        if (coupon.getTargetGoods() == null) {
            return;
        }
        // 対象商品リスト作成
        List<String> targetGoodsList = Arrays.asList(conversionUtility.toDivArray(coupon.getTargetGoods()));
        // DB存在チェック用リスト取得
        List<String> checkGoodsList = goodsDao.getEntityListByGoodsCodeList(targetGoodsList);
        for (String goodsCode : targetGoodsList) {
            if (!checkGoodsList.contains(goodsCode)) {
                addErrorMessage(MSGCD_NOT_EXIST_TARGET_GOODS, new Object[] {goodsCode});
            }
        }
    }

    /**
     * 対象会員存在チェック
     *
     * @param coupon チェック対象のクーポン
     */
    protected void targetMembersNotExistCheck(CouponEntity coupon) {
        if (coupon.getTargetMembers() == null) {
            return;
        }
        // 対象会員リスト作成
        List<String> targetMembersList = Arrays.asList(conversionUtility.toDivArray(coupon.getTargetMembers()));
        // DB存在チェック用リスト取得
        List<String> checkMembersList = memberInfoDao.getEntityListByMemberInfoIdList(targetMembersList);
        for (String memberId : targetMembersList) {
            if (!checkMembersList.contains(memberId)) {
                addErrorMessage(MSGCD_NOT_EXIST_TARGET_MEMBERS, new Object[] {memberId});
            }
        }
    }

    /**
     * 適用可能なクーポンかを判定する<br/>
     *
     * @param entity          クーポンEntity
     * @param receiveOrderDto 受注Dto
     */
    @Override
    public void checkUsableCoupon(CouponEntity entity,
                                  ReceiveOrderDto receiveOrderDto,
                                  HTypeSiteType siteType,
                                  Integer memberInfoSeq) {
        if (entity == null) {
            // EntityがNULLの時、適用されているクーポンが
            // 存在しないということなので処理終了
            return;
        }
        // クーポンの開催日時と現在日付より利用可能かをチェックする
        checkExpiryDate(entity, receiveOrderDto, siteType);
        // クーポン利用回数チェック
        checkUsableLimitOver(entity, receiveOrderDto, siteType.isFront(), memberInfoSeq);
        // クーポン適用商品チェック
        checkTargetGoods(entity, receiveOrderDto);
        // クーポン適用会員チェック
        checkTargetMembers(entity, receiveOrderDto, siteType.isFront(), memberInfoSeq);
        // クーポン情報が取得できたら適用金額チェックを行う
        checkDiscountLowerOrderPrice(entity, receiveOrderDto);
        // check coupon rule for amazon pay
        checkForAmazonPay(entity, receiveOrderDto);

    }

    /**
     * クーポンの有効期限のチェックを行う。
     *
     * @param coupon          クーポンエンティティ
     * @param receiveOrderDto 受注DTO
     */
    protected void checkExpiryDate(CouponEntity coupon, ReceiveOrderDto receiveOrderDto, HTypeSiteType siteType) {

        // 現在日時取得
        Timestamp currentTime = dateUtility.getCurrentTime();

        // クーポンが開催されていなければエラーとする
        Timestamp startTime = coupon.getCouponStartTime();
        if (startTime.after(currentTime)) {
            throwMessage(MSGCD_BEFORE_COUPONSTARTTIME);
        }

        // フロントサイトの場合はクーポンが終了していたら適用対象外
        // バックサイト以外の注文の場合
        if (siteType.isFront()) {
            Timestamp endTime = coupon.getCouponEndTime();
            // クーポンが終了していた場合エラーとする
            if (endTime.before(currentTime)) {
                throwMessage(MSGCD_AFTER_COUPONENDTIME);
            }
        }
    }

    /**
     * クーポンの利用可能回数を超過していないかチェックする<br/>
     *
     * @param entity          クーポンEntity
     * @param receiveOrderDto 受注Dto
     */
    protected void checkUsableLimitOver(CouponEntity entity,
                                        ReceiveOrderDto receiveOrderDto,
                                        Boolean isFrontSite,
                                        Integer memberInfoSeq) {
        if (entity.getUseCountLimit() == 0) {
            return;
        }

        // 会員SEQ取得
        memberInfoSeq = this.getMemberInfoSeq(receiveOrderDto, isFrontSite, memberInfoSeq);

        // ゲストが"利用回数の設定されているクーポン"を利用しようとしているのでエラー
        if (memberInfoSeq == null) {
            throwMessage(MSGCD_GUEST_CANT_USE_COUPON_ERROR);
        }

        // 受注サマリからクーポン利用回数を算出
        Integer couponUseCount =
                        getCouponUseCountForOrder(memberInfoSeq, entity.getCouponSeq(), entity.getCouponStartTime());

        // 利用回数+1(今回利用分)が利用上限数を超えているか判定
        if (entity.getUseCountLimit() < couponUseCount + 1) {
            throwMessage(MSGCD_OVER_COUPON_USE_CNT);
        }
    }

    /**
     * クーポン利用回数チェック<br/>
     * 受注サマリの会員SEQとクーポンSEQ、クーポン連番から使用回数を判定<br/>
     *
     * @param memberInfoSeq   会員SEQ
     * @param couponSeq       クーポンSEQ
     * @param couponStartTime クーポン開始日時
     * @return クーポン利用回数
     */
    protected Integer getCouponUseCountForOrder(Integer memberInfoSeq, Integer couponSeq, Timestamp couponStartTime) {
        return orderSummaryDao.getCouponCountByMemberInfoSeq(memberInfoSeq, couponSeq, couponStartTime);
    }

    /**
     * クーポン対象商品チェック<br/>
     *
     * @param coupon          クーポンエンティティ
     * @param receiveOrderDto 受注DTO
     */
    protected void checkTargetGoods(CouponEntity coupon, ReceiveOrderDto receiveOrderDto) {
        String targetGoods = coupon.getTargetGoods();

        // 対象商品がnullの場合は、全商品対象
        if (targetGoods == null) {
            return;
        }

        List<String> targetGoodsList = Arrays.asList(conversionUtility.toDivArray(targetGoods));

        List<OrderGoodsEntity> orderGoodsEntityList = orderUtility.getALLGoodsEntityList(receiveOrderDto);
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            if (targetGoodsList.contains(orderGoodsEntity.getGoodsCode())) {
                return;
            }
        }
        throwMessage(MSGCD_NO_TARGETGOODS);
    }

    /**
     * クーポン対象会員チェック<br/>
     *
     * @param coupon          クーポンエンティティ
     * @param receiveOrderDto 受注DTO
     */
    protected void checkTargetMembers(CouponEntity coupon,
                                      ReceiveOrderDto receiveOrderDto,
                                      Boolean isFrontSite,
                                      Integer memberInfoSeq) {
        String targetMembers = coupon.getTargetMembers();

        // 対象会員がnullの場合は、全会員対象
        if (targetMembers == null) {
            return;
        }

        // 会員登録済のメールアドレスを取得
        String memberInfoId = this.getMemberMail(receiveOrderDto, isFrontSite, memberInfoSeq);
        // 「会員登録済のメールアドレス」かつ「クーポン対象のメールアドレス」であるかチェック
        List<String> targetMembersList = Arrays.asList(conversionUtility.toDivArray(targetMembers));
        if (Objects.nonNull(memberInfoId) && targetMembersList.contains(memberInfoId)) {
            return;
        }

        throwMessage(MSGCD_NO_TARGETMEMBERS);
    }

    /**
     * 会員登録済みのメールアドレスを取得
     * <pre>
     * ゲスト注文の場合、メールアドレスは「null」で返却する。
     * </pre>
     *
     * @param receiveOrderDto 受注Dtoクラス
     * @return メールアドレス
     */
    protected String getMemberMail(ReceiveOrderDto receiveOrderDto, Boolean isFrontSite, Integer memberInfoSeq) {
        // 会員SEQを取得
        memberInfoSeq = this.getMemberInfoSeq(receiveOrderDto, isFrontSite, memberInfoSeq);
        // ゲスト注文の場合、メールアドレスは「null」で返却
        if (Objects.isNull(memberInfoSeq)) {
            return null;
        }

        // 会員登録済みの場合、会員Entityからメールアドレスを取得
        MemberInfoEntity memberInfoEntity = memberInfoDao.getEntity(memberInfoSeq);
        return memberInfoEntity.getMemberInfoId();
    }

    /**
     * 会員登録済みの会員SEQを取得
     * <pre>
     *  ゲスト注文の場合、会員SEQは「null」で返却する。
     * </pre>
     *
     * @param receiveOrderDto 受注Dtoクラス
     * @return MemberInfoSeq
     */
    protected Integer getMemberInfoSeq(ReceiveOrderDto receiveOrderDto, Boolean isSiteFront, Integer memberInfoSeq) {

        if (isSiteFront) {
            // フロント
            // 【会員登録済み】新規注文
            return memberInfoSeq;
        } else {
            // バック
            // 【会員登録済み】新規注文
            memberInfoSeq = receiveOrderDto.getOrderSummaryEntity().getMemberInfoSeq();
        }

        // 【会員未登録】ゲスト注文の場合、会員SEQは「null」で返却
        return memberInfoSeq;
    }

    /**
     * 割引対象金額がクーポンの適用金額を満たしているかをチェックする。<br />
     * <pre>
     * クーポン適用金額に割引対象金額が満たなかった場合エラー。
     * </pre>
     *
     * @param coupon          クーポンエンティティ
     * @param receiveOrderDto 受注DTO
     */
    protected void checkDiscountLowerOrderPrice(CouponEntity coupon, ReceiveOrderDto receiveOrderDto) {

        // 割引対象金額を取得するを取得する
        BigDecimal targetPrice = receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal();

        // クーポン適用金額を取得する
        BigDecimal discountLowerOrderPrice = coupon.getDiscountLowerOrderPrice();

        // クーポン適用金額に割引対象金額が満たなかった場合エラーとする
        if (targetPrice.compareTo(discountLowerOrderPrice) < 0) {
            throwMessage(MSGCD_NOTFULL_COUPONDISCOUNTCONDITION);
        }
    }

    /**
     * Checks whether entire amazon payment amount is done with coupon.Throws error message for same
     *
     * @param coupon          クーポンエンティティ
     * @param receiveOrderDto 受注DTO
     */
    protected void checkForAmazonPay(CouponEntity coupon, ReceiveOrderDto receiveOrderDto) {

        if (!(HTypeSettlementMethodType.AMAZON_PAYMENT == receiveOrderDto.getOrderSettlementEntity()
                                                                         .getSettlementMethodType())) {
            return;
        }

        // coupon discount price
        BigDecimal couponDiscountPrice = coupon.getDiscountPrice();

        // クーポン適用金額を取得する
        BigDecimal preCouponDiscountOrderPrice =
                        receiveOrderDto.getOrderSettlementEntity().getBeforeDiscountOrderPrice();

        // Check if all the order price is paid using coupon.
        // If yes then throw error message.
        if (couponDiscountPrice != null && preCouponDiscountOrderPrice != null) {
            if (couponDiscountPrice.compareTo(preCouponDiscountOrderPrice) >= 0) {
                throwMessage(MSGCD_AMAZON_PAY_ALL_AMOUNT_COUPON_USE);
                return;
            }
        }
    }
}
