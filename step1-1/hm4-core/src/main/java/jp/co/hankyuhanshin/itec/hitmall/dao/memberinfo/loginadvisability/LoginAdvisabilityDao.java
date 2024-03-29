// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.loginadvisability;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * PDR#023 顧客番号でのログイン<br/>
 *
 * <pre>
 * 会員情報取得ロジック
 *
 * 会員ID 又は 顧客番号で会員情報を取得するメソッドを追加
 * 顧客番号採番のために必要な顧客番号Seq取得するメソッドを追加
 * 電話番号から会員情報を取得するソッドを追加
 * 会員TELと顧客番号から、新規会員登録可能な会員情報を取得するメソッドを追加
 * </pre>
 *
 * @author satoh
 * @version $Revision:$
 */

@Dao
@ConfigAutowireable
public interface LoginAdvisabilityDao {

    /**
     * 取得された会員情報の<br/>
     * ログイン可否判定を取得する。
     *
     * @param memberInfoStatus        memberInfoStatus
     * @param approveStatus           approveStatus
     * @param onlineLoginAdvisability onlineLoginAdvisability
     * @param memberListType          memberListType
     * @param accountingType          accountingType
     * @return 会員エンティティ
     */
    @Select
    LoginAdvisabilityEntity getEntityByLoginAdvisability(String memberInfoStatus,
                                                         String approveStatus,
                                                         String onlineLoginAdvisability,
                                                         String memberListType,
                                                         String accountingType);

    /**
     * ログイン不可の会員情報を取得する<br />
     *
     * @param customerNoList 顧客番号リスト
     * @return 顧客番号リスト
     */
    @Select
    List<Integer> getCanNotLoginMember(List<Integer> customerNoList);

    /**
     * ログイン不可の会員情報を取得する<br />
     *
     * @param memberInfoSeqList 会員SEQリスト
     * @return 会員SEQリスト
     */
    @Select
    List<Integer> getCanNotLoginMemberInfoSeq(List<Integer> memberInfoSeqList);
}
// PDR Migrate Customization to here
