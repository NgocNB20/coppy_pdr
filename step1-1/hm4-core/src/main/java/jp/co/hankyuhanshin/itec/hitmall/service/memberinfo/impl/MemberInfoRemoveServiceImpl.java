/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsMergeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.cardinfo.CardInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoRemoveService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 会員退会更新サービス実装<br/>
 *
 * @author natume
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class MemberInfoRemoveServiceImpl extends AbstractShopService implements MemberInfoRemoveService {

    /**
     * 会員情報取得
     */
    private final MemberInfoGetLogic memberInfoGetLogic;

    /**
     * 会員情報更新
     */
    private final MemberInfoUpdateLogic memberInfoUpdateLogic;

    /**
     * カートマージロジック<br/>
     */
    private final CartGoodsMergeLogic cartGoodsMergeLogic;

    /**
     * お気に入りリスト削除ロジック<br/>
     */
    private final FavoriteListDeleteLogic favoriteListDeleteLogic;

    /**
     * アドレス帳リスト削除ロジック<br/>
     */
    private final AddressBookListDeleteLogic addressBookListDeleteLogic;

    // Paygent Customization from here
    /**
     * カード情報操作（取得・登録・削除）ロジック
     */
    private final CardInfoLogic cardInfoLogic;
    // Paygent Customization to here

    /**
     * 日付Utility
     */
    private final DateUtility dateUtility;

    /**
     * 会員Utility
     */
    private final MemberInfoUtility memberInfoUtility;

    @Autowired
    public MemberInfoRemoveServiceImpl(MemberInfoGetLogic memberInfoGetLogic,
                                       MemberInfoUpdateLogic memberInfoUpdateLogic,
                                       CartGoodsMergeLogic cartGoodsMergeLogic,
                                       FavoriteListDeleteLogic favoriteListDeleteLogic,
                                       AddressBookListDeleteLogic addressBookListDeleteLogic,
                                       CardInfoLogic cardInfoLogic,
                                       DateUtility dateUtility,
                                       MemberInfoUtility memberInfoUtility) {
        this.memberInfoGetLogic = memberInfoGetLogic;
        this.memberInfoUpdateLogic = memberInfoUpdateLogic;
        this.cartGoodsMergeLogic = cartGoodsMergeLogic;
        this.favoriteListDeleteLogic = favoriteListDeleteLogic;
        this.addressBookListDeleteLogic = addressBookListDeleteLogic;
        // Paygent Customization from here
        this.cardInfoLogic = cardInfoLogic;
        // Paygent Customization to here
        this.dateUtility = dateUtility;
        this.memberInfoUtility = memberInfoUtility;
    }

    /**
     * 会員退会処理<br/>
     *
     * @param memberInfoId       会員ID
     * @param memberInfoPassWord 会員パスワード
     */
    @Override
    public void execute(String memberInfoId, String memberInfoPassWord, Integer memberInfoSeq, String accessUid) {
        ArgumentCheckUtil.assertNotEmpty("memberInfoId", memberInfoId);
        ArgumentCheckUtil.assertNotEmpty("memberInfoPassWord", memberInfoPassWord);

        Integer shopSeq = 1001;

        ArgumentCheckUtil.assertGreaterThanZero("memberInfoSeq", memberInfoSeq);

        // 会員情報取得
        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.execute(memberInfoSeq);
        if (memberInfoEntity == null) {
            throwMessage(MSGCD_NOT_EXSIT);
        }

        // メールアドレスの照合処理
        String shopUniqueId = memberInfoUtility.createShopUniqueId(shopSeq, memberInfoId);
        if (!shopUniqueId.equals(memberInfoEntity.getMemberInfoUniqueId())) {
            throwMessage(MSGCD_ID_FAIL);
        }

        // パスワードの照合処理
        // SpringSecurity準拠の方式で暗号化されたパスワードと入力パスワードをAESPasswordEncoderによる標準コンペアで比較
        PasswordEncoder passwordEncoder =
                        ApplicationContextUtility.getBeanByName("encoderMember", PasswordEncoder.class);
        if (!passwordEncoder.matches(memberInfoPassWord, memberInfoEntity.getMemberInfoPassword())) {
            throwMessage(MSGCD_PASSWORD_FAIL);
        }

        Timestamp currentTime = dateUtility.getCurrentTime();

        // Paygent Customization from here
        // カードお預かり情報削除
        cardInfoLogic.deleteCardInfo(memberInfoSeq, false);
        // Paygent Customization to here

        // 会員退会更新
        int result = removeMemberInfo(memberInfoEntity, currentTime);
        if (result == 0) {
            throwMessage(MSGCD_UPDATE_FAIL);
        }

        // カート商品のゲスト移行
        cartGoodsMergeLogic.execute(shopSeq, memberInfoSeq, accessUid, 0);

        // お気に入り商品の削除
        favoriteListDeleteLogic.execute(memberInfoSeq);

        // アドレス帳の削除
        addressBookListDeleteLogic.execute(memberInfoSeq);
    }

    /**
     * 会員退会処理
     *
     * @param memberInfoEntity 会員エンティティ
     * @param currentTime      現在日時
     * @return 更新件数
     */
    protected int removeMemberInfo(MemberInfoEntity memberInfoEntity, Timestamp currentTime) {
        // 会員状態 = 退会
        memberInfoEntity.setMemberInfoStatus(HTypeMemberInfoStatus.REMOVE);
        // 会員一意制約用ID = null
        memberInfoEntity.setMemberInfoUniqueId(null);
        // 退会日 = 現在日付
        memberInfoEntity.setSecessionYmd(dateUtility.getCurrentYmd());
        // カード登録情報
        memberInfoEntity.setPaymentCardRegistType(HTypeCardRegistType.UNREGISTERED);

        return memberInfoUpdateLogic.execute(memberInfoEntity, currentTime);
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
}
