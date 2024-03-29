/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.coupon.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCLower;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateCombo;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRSeparateDateTime;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * クーポン登録更新ページクラス。<br />
 * <pre>
 * クーポン登録更新表示項目。isUpdate
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@HVRSeparateDateTime(targetDate = "couponStartDate", targetTime = "couponStartTime")
@HVRSeparateDateTime(targetDate = "couponEndDate", targetTime = "couponEndTime")
@HVRDateCombo(dateLeftTarget = "couponStartDate", timeLeftTarget = "couponStartTime", dateRightTarget = "couponEndDate",
              timeRightTarget = "couponEndTime")
public class CouponRegistUpdateModel extends AbstractModel {

    /**
     * クーポン名の最大文字列長
     */
    protected static final int LENGTH_COUPON_NAME_MAXIMUM = 80;
    /**
     * 管理メモの最大文字列長
     */
    protected static final int LENGTH_MANAGEMENT_MEMO_MAXIMUM = 2000;

    /**
     * クーポンID
     */
    @NotEmpty
    @Length(min = 1, max = ValidatorConstants.LENGTH_COUPON_ID_MAXIMUM)
    @Pattern(regexp = ValidatorConstants.REGEX_COUPON_ID, message = ValidatorConstants.MSGCD_COUPON_ID_INVALID)
    @HCHankaku
    private String couponId = StringUtils.EMPTY;

    /**
     * クーポン名
     */
    @NotEmpty
    @Length(min = 1, max = LENGTH_COUPON_NAME_MAXIMUM)
    @HVSpecialCharacter(allowPunctuation = true)
    private String couponName = StringUtils.EMPTY;

    /**
     * クーポン表示名PC
     */
    private String couponDisplayNamePc = StringUtils.EMPTY;

    /**
     * クーポン表示名モバイル
     */
    private String couponDisplayNameMb = StringUtils.EMPTY;

    /**
     * クーポン開催開始日
     */
    @NotEmpty
    @HVDate
    @HVSpecialCharacter(allowCharacters = '/')
    @HCDate
    private String couponStartDate;

    /**
     * クーポン開催開始時間
     */
    private String couponStartTime;

    /**
     * クーポン開催終了日
     */
    @NotEmpty
    @HVDate
    @HVSpecialCharacter(allowCharacters = '/')
    @HCDate
    private String couponEndDate;

    /**
     * クーポン開催終了時間
     */
    private String couponEndTime;

    /**
     * クーポンコード
     */
    @NotEmpty
    @Length(min = 1, max = ValidatorConstants.LENGTH_COUPON_CODE_MAXIMUM)
    @HVSpecialCharacter(allowPunctuation = true)
    private String couponCode;

    /**
     * 割引金額
     */
    // マイナスの値が入力されたときのエラーメッセージが必要とするメッセージとそぐわない為、マイナス入力を許可し最小値を設定している
    @NotEmpty
    @HVNumber(minus = true)
    @Digits(integer = 8, fraction = 0)
    @Range(min = 1, max = 99999999)
    @HCHankaku
    private String discountPrice;

    /**
     * 適用金額
     */
    // マイナスの値が入力されたときのエラーメッセージが必要とするメッセージとそぐわない為、マイナス入力を許可し最小値を設定している
    @HVNumber(minus = true)
    @Digits(integer = 8, fraction = 0)
    @Range(min = 1, max = 99999999)
    @HCHankaku
    private String discountLowerOrderPrice;

    /**
     * 利用回数
     */
    @NotEmpty
    @HVNumber(minus = true)
    @Digits(integer = 4, fraction = 0)
    @Range(min = 0, max = 9999)
    @HCHankaku
    @HCNumber
    private String useCountLimit = "0";

    /**
     * 対象商品
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    @HCHankaku
    private String targetGoods;

    /**
     * 対象会員
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    @HCLower
    private String targetMembers;

    /**
     * 管理メモ
     */
    @Length(min = 0, max = LENGTH_MANAGEMENT_MEMO_MAXIMUM)
    @HVSpecialCharacter(allowPunctuation = true)
    private String memo;

    /*
     * 画面項目以外
     */

    /**
     * クーポンSEQ。<br />
     * <pre>
     * 更新対象のクーポンを特定するために使用する。
     * </pre>
     */
    private Integer couponSeq;

    /**
     * クーポンが開催中かを判断するためのフラグ。<br />
     * <pre>
     * 変更確認ダイアログを表示するために使用する。
     * </pre>
     */
    private boolean open;

    /**
     * 変更前のクーポン情報。<br />
     * <pre>
     * 変更前後での差分を確認するために使用。
     * </pre>
     */
    private CouponEntity preUpdateCoupon;

    /**
     * 変更後のクーポン情報。<br />
     * <pre>
     * 変更前後での差分を確認するために使用。
     * </pre>
     */
    private CouponEntity postUpdateCoupon;

    /**
     * 確認画面遷移時に終了時間を変更したかを判断
     */
    private boolean changeEndTime;

    /**
     * 修正されている項目名を保持するリスト。<br />
     * <pre>
     * 変更前後での差異を表示するために使用する。
     * </pre>
     */
    private List<String> modifiedItemNameList;

    /**
     * 更新かを判断。<br />
     * <pre>
     * 更新時に変更可能かどうかを判断する。
     * </pre>
     */
    private boolean updateFlag;

    /*
     * 条件判断
     */

    /**
     * 登録か更新かを判断する。<br />
     * <pre>
     * 新規登録時と更新時でチェックを切り分けるため。
     * </pre>
     *
     * @return 新規登録時にtrueを返す
     */
    public boolean isRegist() {
        return couponSeq == null;
    }

    /**
     * 登録か更新かを判断する。<br />
     * <pre>
     * 新規登録と更新で行うチェックを切り分けるため。
     * </pre>
     *
     * @return 更新時にtrueを返す
     */
    public boolean isUpdate() {
        return couponSeq != null;
    }

    /**
     * 画面描画時にパラメータで渡されたSeq<br />
     * 不正操作チェックするのに利用する
     */
    private Integer scSeq;

    /**
     * 画面描画時にDBから取得したSeq<br />
     * 不正操作チェックするのに利用する
     */
    private Integer dbSeq;
}
