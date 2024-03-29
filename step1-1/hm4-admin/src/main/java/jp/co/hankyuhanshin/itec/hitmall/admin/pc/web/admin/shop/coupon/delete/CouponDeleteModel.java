package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.coupon.delete;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * クーポン登録削除確認ページクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
public class CouponDeleteModel extends AbstractModel {

    /**
     * クーポンID
     */
    private String couponId;

    /**
     * クーポン名
     */
    private String couponName;

    /**
     * クーポン表示名PC
     */
    private String couponDisplayNamePc;

    /**
     * クーポン表示名モバイル
     */
    private String couponDisplayNameMb;

    /**
     * 開催開始日
     */
    private Timestamp couponStartDate;

    /**
     * 開催開始時間
     */
    private Timestamp couponStartTime;

    /**
     * 開催終了日
     */
    private Timestamp couponEndDate;

    /**
     * 開催終了時間
     */
    private Timestamp couponEndTime;

    /**
     * クーポンコード
     */
    private String couponCode;

    /**
     * 割引金額
     */
    private BigDecimal discountPrice;

    /**
     * 適用金額
     */
    private BigDecimal discountLowerOrderPrice;

    /**
     * 利用回数
     */
    private String useCountLimit;

    /**
     * 対象商品
     */
    private String targetGoods;

    /**
     * 対象会員
     */
    private String targetMembers;

    /**
     * 管理用メモ
     */
    private String memo;

    /*
     * 表示項目以外
     */

    /**
     * クーポンSEQ
     */
    private Integer couponSeq;

    /**
     * 削除クーポン情報
     */
    private CouponEntity deleteCoupon;

    /**
     * 削除可否フラグ。<br />
     *
     * <pre>
     * 削除対象のクーポンが削除可能かを判断する。
     * </pre>
     */
    private boolean deleteFlag;

    /**
     * 削除可否チェック。<br />
     *
     * <pre>
     * 削除可能のときは「削除」ボタンを表示。
     * 削除不可のときは「削除不可」イメージを表示。
     * </pre>
     *
     * @return 削除可能な場合はtrueを返す
     */
    public boolean isDelete() {
        return deleteFlag;
    }
}
