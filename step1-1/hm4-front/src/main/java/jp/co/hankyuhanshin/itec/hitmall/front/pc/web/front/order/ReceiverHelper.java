/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFrontBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeRequisitionType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member.WebApiGetDestinationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * PDR#037 住所情報の取り込み<br/>
 * お届け先入力画面 Helper
 *
 * @author kimura
 * @author satoh
 */
@Component
public class ReceiverHelper {

    /**
     * 変換Helper
     */
    private final ConversionUtility conversionUtility;

    /**
     * 共通情報Helper取得
     */
    private final CommonInfoUtility commonInfoUtility;

    /**
     * コンストラクタ
     *
     * @param conversionUtility 変換Helper
     * @param commonInfoUtility 共通情報Helper取得
     */
    @Autowired
    public ReceiverHelper(ConversionUtility conversionUtility, CommonInfoUtility commonInfoUtility) {
        this.conversionUtility = conversionUtility;
        this.commonInfoUtility = commonInfoUtility;
    }

    /**
     * Modelへの変換処理。<br />
     * 受注配送エンティティリスト ⇒ Model
     *
     * <pre>
     * Itemに設定する処理を削除し
     * Modelクラスに直接値を設定します。
     * </pre>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param receiverModel    お届け先入力画面Model
     * @param orderDeliveryDto 受注配送Dtoクラス
     */
    public void toPageForLoad(OrderCommonModel orderCommonModel,
                              ReceiverModel receiverModel,
                              OrderDeliveryDto orderDeliveryDto) {
        // PDR Migrate Customization from here
        if (ObjectUtils.isEmpty(orderDeliveryDto)) {
            return;
        }

        String addType = orderDeliveryDto.getAddType();

        // ラジオボタン初期値を設定
        receiverModel.setReceiverSelectTypeRadio(addType);

        if (orderCommonModel.getReceiveOrderDto() != null
            && orderCommonModel.getReceiveOrderDto().getOrderPersonEntity() != null) {
            // 受注ご注文主エンティティからお届け先リストへの変換処理実行
            createOrderMember(orderCommonModel, receiverModel);
            if (ReceiverModel.ADD_TYPE_SENDER.equals(addType)) {
                return;
            }
        }

        // 住所録が選択されている場合
        if (ReceiverModel.ADD_TYPE_ADDRESS_BOOK.equals(addType) && orderDeliveryDto.getCustomerNo() != null) {
            // 住所録情報エンティティからお届け先リストへの変換処理実行
            if (orderDeliveryDto.getOrderDeliveryEntity() == null) {
                return;
            }
            receiverModel.setReceiverAddressBook(String.valueOf(orderDeliveryDto.getCustomerNo()));
            receiverModel.setAddressBookCustomNo(orderDeliveryDto.getCustomerNo());

            OrderDeliveryEntity orderDeliveryEntity = orderDeliveryDto.getOrderDeliveryEntity();

            receiverModel.setAddressBookLastName(orderDeliveryEntity.getReceiverLastName());
            receiverModel.setAddressBookLastKana(orderDeliveryEntity.getReceiverLastKana());
            receiverModel.setAddressBookFirstName(
                            StringUtils.defaultString(orderDeliveryEntity.getReceiverFirstName()));
            receiverModel.setAddressBookTel(orderDeliveryEntity.getReceiverTel());
            String[] zipCodes = conversionUtility.toZipCodeArray(orderDeliveryEntity.getReceiverZipCode());
            receiverModel.setAddressBookZipCode1(zipCodes[0]);
            receiverModel.setAddressBookZipCode2(zipCodes[1]);
            receiverModel.setAddressBookAddress1(orderDeliveryEntity.getReceiverAddress1()
                                                                    .replace(orderDeliveryEntity.getReceiverPrefecture(),
                                                                             StringUtils.EMPTY
                                                                            ));
            receiverModel.setAddressBookAddress2(orderDeliveryEntity.getReceiverAddress2());
            receiverModel.setAddressBookAddress3(
                            conversionUtility.toSpaceConnect(orderDeliveryEntity.getReceiverAddress3(),
                                                             orderDeliveryEntity.getReceiverAddress4()
                                                            ));

            return;
        }

        if (orderDeliveryDto.getOrderDeliveryEntity() == null) {
            return;
        }
        OrderDeliveryEntity orderDeliveryEntity = orderDeliveryDto.getOrderDeliveryEntity();

        receiverModel.setAddType(orderDeliveryDto.getAddType());
        receiverModel.setReceiverLastName(orderDeliveryEntity.getReceiverLastName());
        receiverModel.setReceiverFirstName(StringUtils.defaultString(orderDeliveryEntity.getReceiverFirstName()));
        receiverModel.setReceiverLastKana(orderDeliveryEntity.getReceiverLastKana());
        receiverModel.setReceiverTel(orderDeliveryEntity.getReceiverTel());
        String[] zipCodes = conversionUtility.toZipCodeArray(orderDeliveryEntity.getReceiverZipCode());
        receiverModel.setReceiverZipCode1(zipCodes[0]);
        receiverModel.setReceiverZipCode2(zipCodes[1]);
        receiverModel.setReceiverPrefecture(orderDeliveryEntity.getReceiverPrefecture());
        // 住所 都道府県が含まれていた場合は空文字に置換
        receiverModel.setReceiverAddress1(orderDeliveryEntity.getReceiverAddress1()
                                                             .replace(orderDeliveryEntity.getReceiverPrefecture(),
                                                                      StringUtils.EMPTY
                                                                     ));
        receiverModel.setReceiverAddress2(orderDeliveryEntity.getReceiverAddress2());
        receiverModel.setReceiverAddress3(orderDeliveryEntity.getReceiverAddress3());
        receiverModel.setReceiverAddress4(orderDeliveryEntity.getReceiverAddress4());

        // 業種
        if (orderDeliveryDto.getBusinessType() != null) {
            receiverModel.setReceiverBusinessType(orderDeliveryDto.getBusinessType().getValue());
        } else {
            receiverModel.setReceiverBusinessType("");
        }
        // PDR Migrate Customization to here
    }

    // PDR Migrate Customization from here

    /**
     * 受注ご注文主を画面に設定します。
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param receiverModel お届け先入力画面Model
     */
    public void createOrderMember(OrderCommonModel orderCommonModel, ReceiverModel receiverModel) {
        OrderPersonEntity orderPersonEntity = orderCommonModel.getReceiveOrderDto().getOrderPersonEntity();
        // 事業所名
        receiverModel.setOrderLastName(orderPersonEntity.getOrderLastName());
        // 電話番号
        receiverModel.setOrderTel(orderPersonEntity.getOrderTel());
        // 郵便番号
        String[] zipCode = conversionUtility.toZipCodeArray(orderPersonEntity.getOrderZipCode());
        receiverModel.setOrderZipCode1(zipCode[0]);
        receiverModel.setOrderZipCode2(zipCode[1]);
        // 住所
        receiverModel.setOrderAddress1(orderPersonEntity.getOrderAddress1());
        receiverModel.setOrderAddress2(orderPersonEntity.getOrderAddress2());
        receiverModel.setOrderAddress3(orderPersonEntity.getOrderAddress3());
    }

    /**
     * お届け先への設定処理
     * WEB-APIで取得したお届け先 ⇒ Model
     *
     * <pre>
     * Itemに設定する処理を削除し
     * Modelクラスに直接値を設定します。
     * </pre>
     *
     * @param orderCommonModel  注文フロー共通Model
     * @param receiverModel     お届け先入力画面Model
     * @param receiveCustomerNo お届け先顧客番号
     */
    public void toReceiverListAddAddressBookSelect(OrderCommonModel orderCommonModel,
                                                   ReceiverModel receiverModel,
                                                   Integer receiveCustomerNo) {

        // ラジオボタンの値を設定
        receiverModel.setReceiverSelectTypeRadio(ReceiverModel.ADD_TYPE_ADDRESS_BOOK);
        for (WebApiGetDestinationResponseDetailDto res : receiverModel.getDestinationDto().getInfo()) {
            if (!receiveCustomerNo.equals(res.getReceiveCustomerNo())) {
                continue;
            }
            receiverModel.setAddressBookLastName(res.getOfficeName());
            receiverModel.setAddressBookLastKana(res.getOfficeKana());
            receiverModel.setAddressBookFirstName(res.getRepresentative());
            receiverModel.setAddressBookTel(res.getTel());
            String[] zipCodes = conversionUtility.toZipCodeArray(res.getZipCode());
            receiverModel.setAddressBookZipCode1(zipCodes[0]);
            receiverModel.setAddressBookZipCode2(zipCodes[1]);
            receiverModel.setAddressBookAddress1(res.getCity());
            receiverModel.setAddressBookAddress2(res.getAddress());
            receiverModel.setAddressBookAddress3(conversionUtility.toSpaceConnect(res.getBuilding(), res.getOther()));

            // 顧客番号
            receiverModel.setAddressBookCustomNo(res.getReceiveCustomerNo());
            // 承認フラグ
            receiverModel.setApprovalFlag(res.getApprovalFlag());
        }
    }

    /**
     * エンティティへの変換処理
     * Model ⇒ 受注配送Dto
     *
     * <pre>
     * Itemから設定する処理を削除し
     * Modelクラスから直接値を設定します。
     * </pre>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param receiverModel お届け先入力画面Model
     * @param commonInfo    共通情報
     * @return 受注配送Dto
     */
    public OrderDeliveryDto toOrderDeliveryDtoForDeliveryMethod(OrderCommonModel orderCommonModel,
                                                                ReceiverModel receiverModel,
                                                                CommonInfo commonInfo) {

        OrderDeliveryDto originalOrderDeliveryDto = orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto();

        // 受注配送Dtoがない為、作成する
        OrderDeliveryDto orderDeliveryDto;
        if (ObjectUtils.isEmpty(originalOrderDeliveryDto)) {
            orderDeliveryDto = ApplicationContextUtility.getBean(OrderDeliveryDto.class);
        } else {
            orderDeliveryDto = originalOrderDeliveryDto;
        }

        // 2023-renew No14 from here
        // 必要項目を退避
        Timestamp receiverDate = null;
        String receiverTimeZone = null;
        if (ObjectUtils.isNotEmpty(orderDeliveryDto.getOrderDeliveryEntity())) {
            receiverDate = orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate();
            receiverTimeZone = orderDeliveryDto.getOrderDeliveryEntity().getReceiverTimeZone();
        }

        // 不要項目を削除するために毎回新規作成を行う。
        orderDeliveryDto.setOrderDeliveryEntity(ApplicationContextUtility.getBean(OrderDeliveryEntity.class));

        // 必要項目を復元
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverDate(receiverDate);
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverTimeZone(receiverTimeZone);
        // 配送方法SEQ ダミーデータ設定
        orderDeliveryDto.getOrderDeliveryEntity()
                        .setDeliveryMethodSeq(Integer.parseInt(OrderUtility.DUMMY_DELIVERY_METHOD_SEQ));
        // 2023-renew No14 to here

        // 選択されているお届け先から受注配送DTOを作成
        if (ReceiverModel.ADD_TYPE_ADDRESS_BOOK.equals(receiverModel.getReceiverSelectTypeRadio())) {
            // 登録しているお届け先から選ぶ
            createOrderDeliveryEntityByAddAddressBook(orderCommonModel, receiverModel, orderDeliveryDto);
        } else if (ReceiverModel.ADD_TYPE_RECEIVER.equals(receiverModel.getReceiverSelectTypeRadio())) {
            // 新しいお届け先に送る
            createOrderDeliveryEntityByReceiver(orderCommonModel, receiverModel, orderDeliveryDto);
        } else {
            // 会員登録の住所に送る
            createOrderDeliveryEntityBySender(orderCommonModel, receiverModel, orderDeliveryDto, commonInfo);
        }

        return orderDeliveryDto;
    }

    /**
     * 住所録から受注配送DTOを作成します。
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param receiverModel    お届け先入力画面Model
     * @param orderDeliveryDto 受注配送Dto
     */
    public void createOrderDeliveryEntityByAddAddressBook(OrderCommonModel orderCommonModel,
                                                          ReceiverModel receiverModel,
                                                          OrderDeliveryDto orderDeliveryDto) {

        orderDeliveryDto.setAddType(ReceiverModel.ADD_TYPE_ADDRESS_BOOK);
        // 事業所名
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverLastName(receiverModel.getAddressBookLastName());
        // 事業所名(フリガナ)
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverLastKana(receiverModel.getAddressBookLastKana());
        // 代表者名
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverFirstName(receiverModel.getAddressBookFirstName());
        // 氏名(フリガナ)
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverFirstKana(StringUtils.EMPTY);
        // TEL
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverTel(receiverModel.getAddressBookTel());
        // 郵便番号
        String zipCode = receiverModel.getAddressBookZipCode1() + receiverModel.getAddressBookZipCode2();
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverZipCode(zipCode);
        // 都道府県 ダミーデータとして東京を固定で設定
        // 配送方法選択可能リスト取得を行う際に 都道府県が設定されていないと
        // 上記リストが取得できずエラーとなってしまうため。
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverPrefecture(HTypePrefectureType.TOKYO.getLabel());
        // 住所
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverAddress1(receiverModel.getAddressBookAddress1());
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverAddress2(receiverModel.getAddressBookAddress2());
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverAddress3(receiverModel.getAddressBookAddress3());
        // 顧客番号
        orderDeliveryDto.setCustomerNo(receiverModel.getAddressBookCustomNo());

        // 請求書種別:別送
        orderDeliveryDto.setRequisitionType(HTypeRequisitionType.SEPARATE);
    }

    /**
     * 新しいお届け先から受注配送DTOを作成します。
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param receiverModel    お届け先入力画面Model
     * @param orderDeliveryDto 受注配送Dto
     */
    public void createOrderDeliveryEntityByReceiver(OrderCommonModel orderCommonModel,
                                                    ReceiverModel receiverModel,
                                                    OrderDeliveryDto orderDeliveryDto) {

        orderDeliveryDto.setAddType(ReceiverModel.ADD_TYPE_RECEIVER);
        // 事業所名
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverLastName(receiverModel.getReceiverLastName());
        // 事業所名フリガナ
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverLastKana(receiverModel.getReceiverLastKana());
        // 代表者名
        orderDeliveryDto.getOrderDeliveryEntity()
                        .setReceiverFirstName(StringUtils.defaultString(receiverModel.getReceiverFirstName()));
        // TEL
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverTel(receiverModel.getReceiverTel());
        // 郵便番号
        String zipCode = receiverModel.getReceiverZipCode1() + receiverModel.getReceiverZipCode2();
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverZipCode(zipCode);

        // 都道府県は使用しないが、
        // ページ遷移し戻った際に都道府県プルダウンを設定された値で表示するために設定
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverPrefecture(receiverModel.getReceiverPrefecture());
        // 都道府県コード
        if (EnumTypeUtil.getEnumFromLabel(HTypePrefectureType.class, receiverModel.getReceiverPrefecture()) != null) {
            orderDeliveryDto.setCityCd(EnumTypeUtil.getEnumFromLabel(HTypePrefectureType.class,
                                                                     receiverModel.getReceiverPrefecture()
                                                                    ).getValue());
        }

        // 住所
        orderDeliveryDto.getOrderDeliveryEntity()
                        .setReceiverAddress1(StringUtils.defaultString(receiverModel.getReceiverPrefecture())
                                             + receiverModel.getReceiverAddress1());
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverAddress2(receiverModel.getReceiverAddress2());
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverAddress3(receiverModel.getReceiverAddress3());
        orderDeliveryDto.getOrderDeliveryEntity()
                        .setReceiverAddress4(StringUtils.defaultString(receiverModel.getReceiverAddress4()));

        orderDeliveryDto.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeFrontBusinessType.class,
                                                                       receiverModel.getReceiverBusinessType()
                                                                      ));
        // 確認書類
        if (HTypeFrontBusinessType.OTHER.getValue().equals(receiverModel.getReceiverBusinessType())) {
            // 3:その他が選択されていた場合は７:― を設定
            orderDeliveryDto.setConfDocumentType(HTypeConfDocumentType.NOT_SET);
        } else {
            // それ以外は3:未確認 を設定
            orderDeliveryDto.setConfDocumentType(HTypeConfDocumentType.UNCONF);
        }
        // 請求書種別:別送
        orderDeliveryDto.setRequisitionType(HTypeRequisitionType.SEPARATE);
    }

    /**
     * 会員登録の住所から受注配送DTOを作成します。
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param receiverModel    お届け先入力画面Model
     * @param orderDeliveryDto 受注配送Dto
     * @param commonInfo       共通情報
     */
    public void createOrderDeliveryEntityBySender(OrderCommonModel orderCommonModel,
                                                  ReceiverModel receiverModel,
                                                  OrderDeliveryDto orderDeliveryDto,
                                                  CommonInfo commonInfo) {

        orderDeliveryDto.setAddType(ReceiverModel.ADD_TYPE_SENDER);
        // 事業所名
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverLastName(receiverModel.getOrderLastName());
        // 事業所名(フリガナ)
        orderDeliveryDto.getOrderDeliveryEntity()
                        .setReceiverLastKana(orderCommonModel.getMemberInfoEntity().getMemberInfoLastKana());
        // 代表者名
        orderDeliveryDto.getOrderDeliveryEntity()
                        .setReceiverFirstName(orderCommonModel.getMemberInfoEntity().getRepresentativeName());
        // 氏名(フリガナ) 未使用
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverFirstKana(StringUtils.EMPTY);
        // TEL
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverTel(receiverModel.getOrderTel());
        // 郵便番号
        String zipCode = receiverModel.getOrderZipCode1() + receiverModel.getOrderZipCode2();
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverZipCode(zipCode);

        // 都道府県 ダミーデータとして東京を固定で設定
        // 配送方法選択可能リスト取得を行う際に 都道府県が設定されていないと
        // 上記リストが取得できずエラーとなってしまうため。
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverPrefecture(HTypePrefectureType.TOKYO.getLabel());
        // 住所
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverAddress1(receiverModel.getOrderAddress1());
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverAddress2(receiverModel.getOrderAddress2());
        orderDeliveryDto.getOrderDeliveryEntity().setReceiverAddress3(receiverModel.getOrderAddress3());
        // 顧客番号
        orderDeliveryDto.setCustomerNo(commonInfoUtility.getCustomerNo(commonInfo));

        // 請求書種別:同梱
        orderDeliveryDto.setRequisitionType(HTypeRequisitionType.INCLUDE);
    }

    // PDR Migrate Customization to here
}
