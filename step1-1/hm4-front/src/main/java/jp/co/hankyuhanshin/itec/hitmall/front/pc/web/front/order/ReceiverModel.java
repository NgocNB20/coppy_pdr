/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCZenkaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCZenkakuKana;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVRItems;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVRZipCode;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.RegularExpressionsConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFrontBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member.WebApiGetDestinationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.group.ReceiverDoAddAddressBookGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.group.ReceiverDoAddReceiverGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.group.ReceiverGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Map;

/**
 * お届け先入力画面Model
 *
 * @author ota-s5
 */
@Data
@HVRItems(target = "receiverAddressBook", comparison = "receiverAddressBookItems",
          groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
@HVRZipCode(targetLeft = "receiverZipCode1", targetRight = "receiverZipCode2",
            groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
@HVRItems(target = "receiverPrefecture", comparison = "receiverPrefectureItems",
          groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
public class ReceiverModel extends AbstractModel {

    // PDR Migrate Customization from here
    /**
     * 追加方法：ご注文主から追加（PDR表記：「請求先（ご登録住所）に送る」）
     */
    public static final String ADD_TYPE_SENDER = "0";

    /**
     * 追加方法：お届け先情報入力から追加（PDR表記：「新しいお届け先に送る／住所変更」）
     */
    public static final String ADD_TYPE_RECEIVER = "1";

    /**
     * 追加方法：アドレス帳から追加（PDR表記：「下記のお届け先リストから選ぶ」）
     */
    public static final String ADD_TYPE_ADDRESS_BOOK = "2";

    /**
     * コンストラクタ
     **/
    public ReceiverModel() {
        // モデルクリア処理のためprivate変数に直アクセスしない

        // 初期選択 登録済みのお届け先リストから選ぶ
        setReceiverSelectTypeRadio(ADD_TYPE_ADDRESS_BOOK);
    }

    /**
     * 「ご入力方法選択」ラジオボタン
     */
    @NotEmpty
    public String receiverSelectTypeRadio;

    /**
     * 追加種別 お届け先入力画面にて使用
     */
    public String addType;
    // PDR Migrate Customization to here

    // << お届け先リスト
    /**
     * アドレス帳プルダウン
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = {ReceiverDoAddAddressBookGroup.class})
    private String receiverAddressBook;

    /**
     * アドレス帳プルダウン用リスト
     */
    private Map<String, String> receiverAddressBookItems;

    /**
     * 住所録 事業所名
     */
    private String addressBookLastName;

    /**
     * 住所録 事業所名(カナ)
     */
    private String addressBookLastKana;

    /**
     * 住所録 代表者名
     */
    private String addressBookFirstName;

    /**
     * 住所録 TEL
     */
    private String addressBookTel;

    /**
     * 住所録 郵便番号1
     */
    private String addressBookZipCode1;

    /**
     * 住所録 郵便番号2
     */
    private String addressBookZipCode2;

    /**
     * 住所録 市区群
     */
    private String addressBookAddress1;

    /**
     * 住所録 町村・番地
     */
    private String addressBookAddress2;

    /**
     * 住所録 それ以降の住所
     */
    private String addressBookAddress3;

    /**
     * 住所録 顧客番号
     */
    private Integer addressBookCustomNo;

    /**
     * 承認フラグ
     */
    private String approvalFlag;

    // PDR Migrate Customization from here
    /**
     * お届け先DTO
     */
    private WebApiGetDestinationResponseDto destinationDto;
    // PDR Migrate Customization to here
    // >>

    // << 新しいお届け先
    /**
     * 事業所名
     */
    @NotEmpty(groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class}) @Pattern(
                    regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
                    groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // PDR Migrate Customization from here
    @Length(max = 40, groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // PDR Migrate Customization to here
    @HCZenkaku
    private String receiverLastName;

    /**
     * 事業所名フリガナ
     */
    // 2023-renew No85-1 from here
    @NotEmpty(groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class}) @Pattern(
                    regexp = RegularExpressionsConstants.HANKAKU_KANA_REGEX,
                    message = "{HHankakuKanaValidator.INVALID_detail}",
                    groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class}) @Length(max = 30,
                                                                                              groups = {ReceiverGroup.class,
                                                                                                              ReceiverDoAddReceiverGroup.class})
    @HCHankaku
    // 2023-renew No85-1 to here
    private String receiverLastKana;

    /**
     * 代表者名
     */
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
             groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // PDR Migrate Customization from here
    @Length(max = 15, groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // PDR Migrate Customization to here
    @HCZenkaku
    private String receiverFirstName;

    /**
     * フリガナ（メイ）
     * ※PDR不使用
     */
    @NotEmpty(groups = {ReceiverGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_KANA_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
             groups = {ReceiverGroup.class})
    @Length(min = 1, max = 40, groups = {ReceiverGroup.class})
    @HCZenkakuKana
    private String receiverFirstKana;

    // PDR Migrate Customization from here
    /**
     * 業種
     */
    @HVItems(target = HTypeFrontBusinessType.class, groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    @NotEmpty(groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    @Length(max = 2, groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    private String receiverBusinessType;

    /**
     * 業種選択肢
     */
    public Map<String, String> receiverBusinessTypeItems;
    // PDR Migrate Customization to here

    /**
     * 電話番号
     */
    @NotEmpty(groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class}) @Pattern(
                    regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
                    message = "{HTelephoneNumberValidator.INVALID_detail}",
                    groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // 2023-renew No85-1 from here
    @Length(max = 14, groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // 2023-renew No85-1 to here
    @HCHankaku
    private String receiverTel;

    /**
     * 郵便番号（左側）
     */
    @Length(min = 0, max = 4, groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    @HCHankaku
    private String receiverZipCode1;

    /**
     * 郵便番号（右側）
     */
    @NotEmpty(groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    @Length(min = 1, max = 4, groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    @HCHankaku
    private String receiverZipCode2;

    /**
     * 都道府県
     */
    @NotEmpty(groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    private String receiverPrefecture;

    /**
     * 都道府県プルダウン用リスト
     */
    private Map<String, String> receiverPrefectureItems;

    /**
     * 市区郡
     */
    @NotEmpty(groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class}) @Pattern(
                    regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
                    groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // 2023-renew No85-1 from here
    @Length(max = 36, groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // 2023-renew No85-1 to here
    private String receiverAddress1;

    /**
     * 町村・番地
     */
    @NotEmpty(groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class}) @Pattern(
                    regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
                    groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // 2023-renew No85-1 from here
    @Length(max = 40, groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // 2023-renew No85-1 to here
    @HCZenkaku
    private String receiverAddress2;

    /**
     * マンション・建物名
     */
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
             groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // 2023-renew No85-1 from here
    @Length(min = 0, max = 40, groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    // 2023-renew No85-1 to here
    @HCZenkaku
    private String receiverAddress3;

    // PDR Migrate Customization from here
    /**
     * 部屋番号
     */
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
             groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    @Length(max = 15, groups = {ReceiverGroup.class, ReceiverDoAddReceiverGroup.class})
    @HCZenkaku
    public String receiverAddress4;
    // PDR Migrate Customization to here
    // >>

    // PDR Migrate Customization from here
    // << 請求先(ご登録住所)
    /**
     * 受注ご注文主 事業所名
     */
    private String orderLastName;

    /**
     * 受注ご注文主 TEL
     */
    private String orderTel;

    /**
     * 受注ご注文主 郵便番号1
     */
    private String orderZipCode1;

    /**
     * 受注ご注文主 郵便番号2
     */
    private String orderZipCode2;

    /**
     * 受注ご注文主 市区郡
     */
    private String orderAddress1;

    /**
     * 受注ご注文主 町村・番地
     */
    private String orderAddress2;

    /**
     * 受注ご注文主 それ以降の住所
     */
    private String orderAddress3;
    // >>

    /**
     * 住所録から取得した情報を表示するか否か<br/>
     */
    public boolean isCheckAddressBookCustomNo() {
        return this.addressBookCustomNo != null;
    }
    // PDR Migrate Customization to here
}
