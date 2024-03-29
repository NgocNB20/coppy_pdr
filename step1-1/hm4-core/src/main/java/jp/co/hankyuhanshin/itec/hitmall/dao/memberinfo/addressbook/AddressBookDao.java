/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.addressbook.AddressBookSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;

/**
 * アドレス帳DAO<br/>
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface AddressBookDao {

    /**
     * 追加<br/>
     *
     * @param addressBookEntity アドレス帳情報
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(AddressBookEntity addressBookEntity);

    /**
     * 更新<br/>
     *
     * @param addressBookEntity アドレス帳情報
     * @return 更新件数
     */
    @Update
    int update(AddressBookEntity addressBookEntity);

    /**
     * 削除<br/>
     *
     * @param addressBookEntity アドレス帳情報
     * @return 削除件数
     */
    @Delete
    int delete(AddressBookEntity addressBookEntity);

    /**
     * エンティティ取得<br/>
     *
     * @param addressBookSeq アドレス帳SEQ
     * @param memberInfoSeq  会員SEQ
     * @return アドレス帳エンティティ
     */
    @Select
    AddressBookEntity getEntity(Integer addressBookSeq, Integer memberInfoSeq);

    /**
     * 名称指定のエンティティ取得<br/>
     *
     * @param memberInfoSeq   会員SEQ
     * @param addressBookName アドレス帳名称
     * @return アドレス帳エンティティ
     */
    @Select
    AddressBookEntity getAddressBookByName(Integer memberInfoSeq, String addressBookName);

    /**
     * エンティティリスト取得<br/>
     *
     * @param conditionDto アドレス帳検索条件DTO
     * @return アドレス帳エンティティリスト
     */
    @Select
    List<AddressBookEntity> getSearchAddressBookList(AddressBookSearchForDaoConditionDto conditionDto,
                                                     SelectOptions selectOptions);

    /**
     * 会員のアドレス帳登録件数を取得<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return アドレス帳登録件数
     */
    @Select
    int getAddressBookCount(Integer memberInfoSeq);

    /**
     * アドレス帳リスト削除<br/>
     *
     * @param addressBookSeqList アドレス帳SEQリスト
     * @param memberInfoSeq      会員SEQ
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteList(Integer memberInfoSeq, List<Integer> addressBookSeqList);

    /**
     * アドレス帳リスト削除<br/>
     * 会員に紐づくアドレス帳を全て削除
     *
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteListByMemberInfoSeq(Integer memberInfoSeq);

    // PDR Migrate Customization from here
    //     /**
    //      * PDR#011 #037 住所情報の取り込み<br/>
    //      *
    //      * <pre>
    //      * 顧客番号で住所録情報を取得するメソッドを追加
    //      * </pre>
    //      *
    //      */

    /**
     * 顧客番号から住所録情報を取得<br/>
     *
     * @param customerNo 顧客番号
     * @return 住所録エンティティ
     */
    @Select
    AddressBookEntity getEntityByCustomerNo(Integer customerNo);

    /**
     * 顧客番号リスト取得<br/>
     *
     * @param conditionDto 住所録検索条件DTO
     * @return 顧客番号リスト
     */
    @Select
    List<Integer> getCustomerNoList(AddressBookSearchForDaoConditionDto conditionDto);

    // PDR Migrate Customization to here
}
