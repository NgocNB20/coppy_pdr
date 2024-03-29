/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoForMemberMemnuDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

import java.sql.Timestamp;

/**
 * 会員情報取得ロジック<br/>
 *
 * @author negishi
 * @version $Revision: 1.5 $
 */
public interface MemberInfoGetLogic {

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoSeq 会員情報SEQ
     * @return 会員情報エンティティ
     */
    MemberInfoEntity execute(Integer memberInfoSeq);

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoSeq    会員情報SEQ
     * @param memberInfoStatus 会員状態
     * @return 会員情報エンティティ
     */
    MemberInfoEntity execute(Integer memberInfoSeq, HTypeMemberInfoStatus memberInfoStatus);

    /**
     * 会員情報を取得する<br />
     *
     * @param shopSeq            ショップSEQ
     * @param memberInfoId       会員ID
     * @param memberInfoBirthDay 生年月日
     * @return 会員情報エンティティ
     */
    MemberInfoEntity execute(Integer shopSeq, String memberInfoId, Timestamp memberInfoBirthDay);

    /**
     * 会員情報を取得する<br />
     *
     * @param shopSeq          ショップSEQ
     * @param memberInfoId     会員ID
     * @param memberInfoStatus 会員状態
     * @return 会員情報エンティティ
     */
    MemberInfoEntity execute(Integer shopSeq, String memberInfoId, HTypeMemberInfoStatus memberInfoStatus);

    /**
     * 会員情報を取得する<br />
     *
     * @param shopSeq   ショップSEQ
     * @param accessUid 端末識別番号
     * @return 会員情報エンティティ
     */
    MemberInfoEntity execute(Integer shopSeq, String accessUid);

    /**
     * 会員情報を取得する<br />
     *
     * @param shopUniqueId ショップユニークId
     * @return 会員情報エンティティ
     */
    MemberInfoEntity execute(String shopUniqueId);

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoSeq 会員情報SEQ
     * @return MemberInfoForMemberTopDto フロント表示用会員情報Dto
     */
    MemberInfoForMemberMemnuDto getMemberMenuDto(Integer memberInfoSeq);

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoMail   メールアドレス
     * @param memberInfoStatus 会員ステータス
     * @return 会員情報エンティティ
     */
    MemberInfoEntity executeByMailStatus(String memberInfoMail, HTypeMemberInfoStatus memberInfoStatus);

    /**
     * 暫定会員情報を取得する<br />
     *
     * @param memberInfoUniqueId 会員ユニークID
     * @param memberInfoMail     メールアドレス
     * @return 暫定会員情報エンティティ
     */
    MemberInfoEntity executeForProvisional(String memberInfoUniqueId, String memberInfoMail);

    // PDR Migrate Customization from here

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoIdOrCustomerNo 会員ID 又は 顧客番号
     * @return 会員情報エンティティ
     */
    MemberInfoEntity getMemberInfoEntityByMemberInfoIdOrCustomerNo(String memberInfoIdOrCustomerNo);

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoTel 電話番号
     * @return 会員情報エンティティ
     */
    MemberInfoEntity getMemberInfoEntityByMemberInfoTel(String memberInfoTel);

    /**
     * メールを取得する
     * @param memberInfoMail メール
     * @return メールエンティティ
     */
    // 2023-renew No79 from here
    MemberInfoEntity getMemberInfoEntityByMemberInfoMail(String memberInfoMail);
    // 2023-renew No79 to here

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoTel 電話番号
     * @param customerNo    顧客番号
     * @return 会員情報エンティティ
     */
    MemberInfoEntity getMemberInfoEntityByMemberInfoTelAndCustomerNo(String memberInfoTel, String customerNo);

    /**
     * 会員情報を取得する<br />
     *
     * @param customerNo 顧客番号
     * @return 会員情報エンティティ
     */
    MemberInfoEntity getMemberInfoEntityByCustomerNo(Integer customerNo);
    // PDR Migrate Customization to here

    /**
     * 会員情報を取得する（ロック）<br />
     *
     * @param memberInfoSeq 会員情報SEQ
     * @return 会員情報エンティティ
     */
    MemberInfoEntity getEntityForUpdate(Integer memberInfoSeq);
}
