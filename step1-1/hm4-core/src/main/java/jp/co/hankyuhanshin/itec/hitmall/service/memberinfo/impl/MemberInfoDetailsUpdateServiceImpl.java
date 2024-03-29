/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.config.password.AESPasswordEncoder;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.cardinfo.CardInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoDetailsUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * PDR#11 08_データ連携（顧客情報）会員情報の項目追加・変更<br/>
 * <p>
 * 会員詳細情報更新サービス実装クラス<br/>
 * 管理画面で使う事を想定としている。<br/>
 * <pre>
 * ・会員情報
 * ・メルマガ購読者情報
 * </pre>
 *
 * @author negishi
 * @author tomo (itec) 2012/01/23 チケット #2722 対応 入会日・退会日の自動補完処理を削除
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 * @author s.kume 会員状態が「退会」である場合、会員一意制約IDを初期化する処理を削除。
 */
@Service
public class MemberInfoDetailsUpdateServiceImpl extends AbstractShopService implements MemberInfoDetailsUpdateService {

    /**
     * 会員情報取得ロジック
     */
    private final MemberInfoGetLogic memberInfoGetLogic;

    /**
     * 会員情報データチェックロジック
     */
    private final MemberInfoDataCheckLogic memberInfoDataCheckLogic;

    /**
     * 会員情報更新サービス
     */
    private final MemberInfoUpdateService memberInfoUpdateService;

    /**
     * お気に入りリスト削除ロジック
     */
    private final FavoriteListDeleteLogic favoriteListDeleteLogic;

    /**
     * アドレス帳リスト削除ロジック
     */
    private final AddressBookListDeleteLogic addressBookListDeleteLogic;

    // Paygent Customization from here

    /**
     * カード情報操作（取得・登録・削除）ロジック
     */
    private final CardInfoLogic cardInfoLogic;
    // Paygent Customization to here

    /**
     * 会員パスワードの暗号化キーをシステムプロパティから取得するためのキー
     */
    private static final String MEMBER_PASSWORD_ENCRYPT_KEY = "memberPassEncryptKey";

    @Autowired
    public MemberInfoDetailsUpdateServiceImpl(MemberInfoGetLogic memberInfoGetLogic,
                                              MemberInfoDataCheckLogic memberInfoDataCheckLogic,
                                              MemberInfoUpdateService memberInfoUpdateService,
                                              FavoriteListDeleteLogic favoriteListDeleteLogic,
                                              AddressBookListDeleteLogic addressBookListDeleteLogic,
                                              CardInfoLogic cardInfoLogic) {
        this.memberInfoGetLogic = memberInfoGetLogic;
        this.memberInfoDataCheckLogic = memberInfoDataCheckLogic;
        this.memberInfoUpdateService = memberInfoUpdateService;
        this.favoriteListDeleteLogic = favoriteListDeleteLogic;
        this.addressBookListDeleteLogic = addressBookListDeleteLogic;
        // Paygent Customization from here
        this.cardInfoLogic = cardInfoLogic;
        // Paygent Customization to here
    }

    /**
     * サービス実行<br/>
     * <pre>
     * 会員状態が「退会」である場合、会員一意制約IDを初期化する処理を削除
     * </pre>
     *
     * @param memberInfoDetailsDto 会員詳細DTO
     * @return 更新件数
     */
    @Override
    public int execute(MemberInfoDetailsDto memberInfoDetailsDto) {
        // パラメータチェック
        checkParameter(memberInfoDetailsDto);

        // 現在日時取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Timestamp currentTime = dateUtility.getCurrentTime();

        // 更新に使用する会員情報エンティティ
        MemberInfoEntity memberInfoEntity = CopyUtil.deepCopy(memberInfoDetailsDto.getMemberInfoEntity());

        // 修正対象の会員の現在の会員情報を取得
        MemberInfoEntity memberInfoEntityBase = memberInfoGetLogic.execute(memberInfoEntity.getMemberInfoSeq());
        // 修正対象の会員の現在の会員状態を取得
        HTypeMemberInfoStatus memberInfoStatusBase = memberInfoEntityBase.getMemberInfoStatus();

        HTypeMemberInfoStatus memberInfoStatus = memberInfoEntity.getMemberInfoStatus();
        // 入会⇒退会の場合
        // PDR Migrate Customization from here
        if (HTypeMemberInfoStatus.ADMISSION.equals(memberInfoStatusBase) && HTypeMemberInfoStatus.REMOVE.equals(
                        memberInfoStatus)) {
            // PDR Migrate Customization to here
            // お気に入り商品の削除
            favoriteListDeleteLogic.execute(memberInfoEntity.getMemberInfoSeq());
            // 会員一意制約用IDを設定
            memberInfoEntity.setMemberInfoUniqueId(null);

            // Paygent Customization from here
            // カードお預かり情報削除
            cardInfoLogic.deleteCardInfo(memberInfoEntity.getMemberInfoSeq(), false);
            // Paygent Customization to here
            // クレジットカード保持種別を更新する
            memberInfoEntity.setPaymentCardRegistType(HTypeCardRegistType.UNREGISTERED);

            // 退会⇒入会の場合
        } else if (HTypeMemberInfoStatus.REMOVE.equals(memberInfoStatusBase) && HTypeMemberInfoStatus.ADMISSION.equals(
                        memberInfoStatus)) {
            // 会員一意制約用IDを設定
            memberInfoEntity.setMemberInfoUniqueId(createShopUniqueId(memberInfoEntity));
            // 会員情報データチェック
            checkMemberInfo(memberInfoEntity);
        }

        // パスワードの暗号化
        encryptMemberInfoPassword(memberInfoEntity, memberInfoEntityBase);

        // 会員情報更新サービス実行。更新失敗したらここ例外投げられますよ。
        int processeCount = memberInfoUpdateService.execute(memberInfoEntity, currentTime);

        return processeCount;
    }

    /**
     * パラメータチェック
     *
     * @param memberInfoDetailsDto 会員詳細DTO
     */
    protected void checkParameter(MemberInfoDetailsDto memberInfoDetailsDto) {
        ArgumentCheckUtil.assertNotNull("memberInfoDetailsDto", memberInfoDetailsDto);
        ArgumentCheckUtil.assertNotNull("memberInfoEntity", memberInfoDetailsDto.getMemberInfoEntity());
    }

    /**
     * カードDtoを作成する<br/>
     *
     * @param memberInfoEntity 会員情報エンティティ
     * @return CardDto
     */
    protected CardDto createCardDto(MemberInfoEntity memberInfoEntity) {
        CardDto cardDto = ApplicationContextUtility.getBean(CardDto.class);
        Integer memberInfoSeq = memberInfoEntity.getMemberInfoSeq();
        cardDto.setMemberInfoSeq(memberInfoSeq);
        // 決済代行会員ID
        cardDto.setPaymentMemberId(memberInfoEntity.getPaymentMemberId());

        return cardDto;
    }

    /**
     * 会員一意制約用IDを作成
     *
     * @param memberInfoEntity 会員情報エンティティ
     * @return 会員一意制約用ID
     */
    protected String createShopUniqueId(MemberInfoEntity memberInfoEntity) {
        // 会員業務Helper取得
        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);

        return memberInfoUtility.createShopUniqueId(
                        memberInfoEntity.getShopSeq(), memberInfoEntity.getMemberInfoMail());
    }

    /**
     * 会員情報データチェック
     *
     * @param memberInfoEntity 会員エンティティ
     */
    protected void checkMemberInfo(MemberInfoEntity memberInfoEntity) {
        memberInfoDataCheckLogic.execute(memberInfoEntity);
    }

    /**
     * パスワードの暗号化<br/>
     * パスワードが入力されている場合のみ行います。
     *
     * @param memberInfoEntity     会員情報エンティティ
     * @param memberInfoEntityBase 修正対象の会員の現在の会員情報エンティティ
     */
    protected void encryptMemberInfoPassword(MemberInfoEntity memberInfoEntity, MemberInfoEntity memberInfoEntityBase) {
        String password = memberInfoEntity.getMemberInfoPassword();
        // パスワードが入力されていない場合は、元々のパスワードを設定
        if (password == null) {
            memberInfoEntity.setMemberInfoPassword(memberInfoEntityBase.getMemberInfoPassword());
            // パスワードが入力されていれば暗号化
        } else {
            // SpringSecurity準拠の方式で暗号化
            PasswordEncoder encoder = new AESPasswordEncoder(MEMBER_PASSWORD_ENCRYPT_KEY);
            // パスワード暗号化
            String encryptedPassword = encoder.encode(memberInfoEntity.getMemberInfoPassword());

            memberInfoEntity.setMemberInfoPassword(encryptedPassword);
        }
    }
}
