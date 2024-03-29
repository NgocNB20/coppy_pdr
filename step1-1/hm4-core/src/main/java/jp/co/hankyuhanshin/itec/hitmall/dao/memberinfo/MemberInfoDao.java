/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Stream;

/**
 * 会員情報DAOクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface MemberInfoDao {

    /**
     * インサート
     *
     * @param memberInfoEntity 会員情報
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(MemberInfoEntity memberInfoEntity);

    /**
     * アップデート
     *
     * @param memberInfoEntity 会員情報
     * @return 更新件数
     */
    @Update
    int update(MemberInfoEntity memberInfoEntity);

    /**
     * デリート
     *
     * @param memberInfoEntity 会員情報
     * @return 削除件数
     */
    @Delete
    int delete(MemberInfoEntity memberInfoEntity);

    /**
     * エンティティ取得
     *
     * @param memberInfoSeq 会員SEQ
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntity(Integer memberInfoSeq);

    /**
     * 会員情報リスト取得（会員ID）
     *
     * @param memberInfoIdList 会員IDリスト
     * @return 会員情報エンティティリスト
     */
    @Select
    List<String> getEntityListByMemberInfoIdList(List<String> memberInfoIdList);

    /**
     * ショップ会員ユニークIDで会員情報を取得する
     *
     * @param memberInfoUniqueId 会員ユニークID
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntityByMemberInfoUniqueId(String memberInfoUniqueId);

    /**
     * 指定されたショップの端末識別番号から
     * 会員状態「入会」の会員情報を取得する
     * オートログイン時に仕様予定
     *
     * @param shopSeq   ショップSEQ
     * @param accessUid 端末識別番号
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntityByAccessUid(Integer shopSeq, String accessUid);

    /**
     * ショップSEQ・会員ID・会員状態から会員情報の取得
     *
     * @param shopSeq          ショップSEQ
     * @param memberInfoId     会員ID
     * @param memberInfoStatus 会員状態
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntityByIdStatus(Integer shopSeq, String memberInfoId, HTypeMemberInfoStatus memberInfoStatus);

    /**
     * ショップSEQ・会員ID・会員状態・会員電話番号から会員情報の取得
     *
     * @param shopSeq            ショップSEQ
     * @param memberInfoId       会員ID
     * @param memberInfoBirthDay 生年月日
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntityByIdBirthDay(Integer shopSeq, String memberInfoId, Timestamp memberInfoBirthDay);

    /**
     * 会員SEQと会員状態から会員情報を取得
     *
     * @param memberInfoSeq    会員SEQ
     * @param memberInfoStatus 会員状態
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntityByStatus(Integer memberInfoSeq, HTypeMemberInfoStatus memberInfoStatus);

    /**
     * ログイン日時の更新を行う
     *
     * @param memberInfoSeq 会員SEQ
     * @param userAgent     最終ログインユーザーエージェント
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateLogin(Integer memberInfoSeq, String userAgent, HTypeDeviceType deviceType);

    /**
     * 携帯のログイン・ログアウトで利用する
     *
     * @param memberInfoSeq 会員SEQ
     * @param accessUid     端末識別番号
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateAccessUid(Integer memberInfoSeq, String accessUid);

    /**
     * 端末識別番号をクリアする
     * 携帯のログイン時に利用
     *
     * @param accessUid 端末識別番号
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateAccessUidClear(String accessUid);

    /**
     * ログイン失敗回数を行う
     *
     * @param memberInfoSeq   会員SEQ
     * @param accountLockTime アカウントロック状態
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateLoginFailureCount(Integer memberInfoSeq, Timestamp accountLockTime);

    /**
     * ログイン失敗回数を行う
     *
     * @param memberInfoSeq 会員SEQ
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateLoginFailureCountReset(Integer memberInfoSeq);

    /**
     * 会員検索一覧取得
     *
     * @param conditionDto  会員検索条件DTO
     * @param selectOptions 検索オプション
     * @return 会員情報エンティティリスト
     */
    @Select
    List<MemberInfoEntity> getMemberInfoConditionDtoList(MemberInfoSearchForDaoConditionDto conditionDto,
                                                         SelectOptions selectOptions);

    /**
     * CSV出力する。
     *
     * @param memberInfoSeqList 会員SEQリスト
     * @return 出力件数
     */
    @Select
    Stream<MemberCsvDto> exportCsvByMemberInfoSeqList(List<Integer> memberInfoSeqList);

    /**
     * 会員検索全件CSV出力
     *
     * @param conditionDto 会員検索条件DTO
     * @return 出力件数
     */
    @Select
    Stream<MemberCsvDto> exportCsvByMemberInfoSearchForDaoConditionDtoStream(MemberInfoSearchForDaoConditionDto conditionDto);

    /**
     * 選択会員情報リスト取得
     *
     * @param memberInfoSeqList 会員SEQリスト
     * @return 会員情報エンティティリスト
     */
    @Select
    List<MemberInfoEntity> getEntityListByMemberInfoSeqList(List<Integer> memberInfoSeqList);

    /**
     * 以下の条件を満たすか会員SEQリストを取得する。
     * <pre>
     * 《期限切れポイント告知用》
     *   期限切れポイント告知メールの対象者を取得する
     *
     * 《期限切れポイント無効化用》
     *   期限切れポイント無効化対象を取得する
     * </pre>
     *
     * @param checkeDate     チェックの基準日
     * @param isNotification true=期限切れポイント告知用, false=期限切れポイント無効化用
     * @return 条件を満たす会員SEQリスト
     */
    @Select
    List<Integer> getSeqListForPointInvalidation(Timestamp checkeDate, boolean isNotification);

    /**
     * メールアドレスと会員ステータスで会員情報を取得する
     *
     * @param memberInfoMail   メールアドレス
     * @param memberInfoStatus 会員ステータス
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntityByMailStatus(String memberInfoMail, HTypeMemberInfoStatus memberInfoStatus);

    /**
     * 新規会員SEQ取得
     * 新たに登録する会員情報の会員SEQを取得する。
     *
     * @return 新規会員SEQ
     */
    @Select
    int getMemberInfoSeqNextVal();

    // PDR Migrate Customization from here
    //     /**
    //      * PDR#023 顧客番号でのログイン<br/>
    //      *
    //      * <pre>
    //      * 会員情報取得ロジック
    //      *
    //      * 会員ID 又は 顧客番号で会員情報を取得するメソッドを追加
    //      * 顧客番号採番のために必要な顧客番号Seq取得するメソッドを追加
    //      * 電話番号から会員情報を取得するソッドを追加
    //      * 会員TELと顧客番号から、新規会員登録可能な会員情報を取得するメソッドを追加
    //      * </pre>
    //      *
    //      */

    /**
     * 会員ID 又は 顧客番号から<br/>
     * 会員情報を取得する。
     *
     * @param memberInfoId 会員ID
     * @param customerNo   顧客番号
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntityByMemberInfoMailOrCustomerNo(String memberInfoId, Integer customerNo);

    // PDR Migrate Customization from here

    /**
     * 顧客番号SEQを取得する<br/>
     * 顧客番号採番のために必要な顧客番号Seq取得<br/>
     *
     * @return 顧客番号SEQ
     */
    @Select
    Integer getCustomerSeqNextVal();
    // PDR Migrate Customization to here

    /**
     * 会員TELから会員情報を取得する。<br/>
     *
     * @param memberInfoTel 会員TEL
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntityByMemberInfoTel(String memberInfoTel);

    /**
     * 会員メールから会員情報を取得する。<br/>
     *
     * @param memberInfoMail 会員メール
     * @return 会員エンティティ
     */
    // 2023-renew No79 from here
    @Select
    MemberInfoEntity getEntityByMemberInfoMail(String memberInfoMail);
    // 2023-renew No79 to here

    /**
     * 会員TELと顧客番号から、新規会員登録可能な<br/>
     * 会員情報を取得する。<br/>
     * <pre>
     * 以下条件に一致する会員は新規登録可能
     * 名簿区分 = 1:顧客
     * </pre>
     *
     * @param memberInfoTel 会員TEL
     * @param customerNo    顧客番号
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntityByMemberInfoTelAndCustomerNo(String memberInfoTel, Integer customerNo);

    /**
     * 顧客番号から会員情報を取得する。<br/>
     *
     * @param customerNo 顧客番号
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntityByCustomerNo(Integer customerNo);
    // PDR Migrate Customization to here

    /**
     * メール不要会員の顧客番号一覧を取得する<br/>
     *
     * @param customerNoList 顧客番号リスト
     * @return 顧客番号リスト
     */
    @Select
    List<Integer> getNoMailRequiredMemberInfo(List<Integer> customerNoList);

    /**
     * メール不要会員の顧客番号一覧を取得する(会員SEQ)<br/>
     *
     * @param memberInfoSeqList 会員SEQリスト
     * @return 会員SEQリスト
     */
    @Select
    List<Integer> getNoMailRequiredMemberInfoSeq(List<Integer> memberInfoSeqList);

    /**
     * エンティティ取得(ロック)
     *
     * @param memberInfoSeq 会員SEQ
     * @return 会員エンティティ
     */
    @Select
    MemberInfoEntity getEntityForUpdate(Integer memberInfoSeq);

}
