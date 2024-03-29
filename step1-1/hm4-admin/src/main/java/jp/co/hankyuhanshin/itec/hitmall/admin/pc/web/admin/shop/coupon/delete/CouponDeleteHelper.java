package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.coupon.delete;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * クーポン削除HELPERクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class CouponDeleteHelper {

    /**
     * 変換Utilityクラス
     */
    private ConversionUtility conversionUtility;

    /**
     * コンストラクタ
     *
     * @param conversionUtility
     */
    @Autowired
    public CouponDeleteHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 画面初期表示。<br />
     *
     * <pre>
     * クーポン情報を画面に反映する。
     * </pre>
     *
     * @param coupon     削除対象のクーポン
     * @param deletePage 削除画面ページ
     */
    public void toPageForLoad(CouponEntity coupon, CouponDeleteModel couponModel) {

        // クーポンID
        couponModel.setCouponId(coupon.getCouponId());
        // クーポン名
        couponModel.setCouponName(coupon.getCouponName());
        // クーポン表示名PC
        couponModel.setCouponDisplayNamePc(coupon.getCouponDisplayNamePC());
        // クーポン表示名モバイル
        couponModel.setCouponDisplayNameMb(coupon.getCouponDisplayNameMB());
        // 開催開始日
        couponModel.setCouponStartDate(coupon.getCouponStartTime());
        // 開催開始時間
        couponModel.setCouponStartTime(coupon.getCouponStartTime());
        // 開催終了日
        couponModel.setCouponEndDate(coupon.getCouponEndTime());
        // 開催終了時間
        couponModel.setCouponEndTime(coupon.getCouponEndTime());
        // クーポンコード
        couponModel.setCouponCode(coupon.getCouponCode());
        // 割引金額
        couponModel.setDiscountPrice(coupon.getDiscountPrice());
        // 適用金額
        couponModel.setDiscountLowerOrderPrice(coupon.getDiscountLowerOrderPrice());
        // 利用回数
        couponModel.setUseCountLimit(conversionUtility.toString(coupon.getUseCountLimit()));
        // 対象商品
        couponModel.setTargetGoods(coupon.getTargetGoods());
        // 対象会員
        couponModel.setTargetMembers(coupon.getTargetMembers());
        // 管理用メモ
        couponModel.setMemo(coupon.getMemo());

        /*
         * 画面項目以外
         */
        // 削除対象クーポン情報
        couponModel.setDeleteCoupon(coupon);
    }
}
