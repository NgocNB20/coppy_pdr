/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.dao.administrator.confirmmail;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdminConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.confirmmail.AdminConfirmMailEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * AdminConfirmMail Dao
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface AdminConfirmMailDao {
    /**
     * 削除<br/>
     *
     * @param adminConfirmMailEntity 確認メール情報
     * @return 削除件数
     */
    @Delete
    int delete(AdminConfirmMailEntity adminConfirmMailEntity);

    /**
     * 確認メール情報取得<br/>
     *
     * @param adminConfirmMailSeq 確認メールSEQ
     * @return 確認メールエンティティ
     */
    @Select
    AdminConfirmMailEntity getEntity(Integer adminConfirmMailSeq);

    /**
     * 登録<br/>
     *
     * @param adminConfirmMailEntity 確認メール情報
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(AdminConfirmMailEntity adminConfirmMailEntity);

    /**
     * 更新<br/>
     *
     * @param adminConfirmMailEntity 確認メール情報
     * @return 更新件数
     */
    @Update
    int update(AdminConfirmMailEntity adminConfirmMailEntity);

    /**
     * 確認メールSEQを取得する<br/>
     *
     * @return 確認メールSEQ
     */
    @Select
    Integer getConfirmMailSeqNextVal();

    /**
     * パスワードが一致し、現在日時が有効期限内にある確認メール情報を取得する<br/>
     *
     * @param password パスワード
     * @return 確認メールエンティティ
     */
    @Select
    AdminConfirmMailEntity getEntityByPassword(String password);

    /**
     * パスワードと確認メール種別が一致し、現在日時が有効期限内にある確認メール情報を取得する<br/>
     *
     * @param password             パスワード
     * @param adminConfirmMailType 確認メール種別
     * @return 確認メールエンティティ
     */
    @Select
    AdministratorEntity getEntityByType(String password, HTypeAdminConfirmMailType adminConfirmMailType);

}
