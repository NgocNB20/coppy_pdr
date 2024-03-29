/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.mail;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateDefaultFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.mail.MailTemplateIndexDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * メールテンプレートDaoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface MailTemplateDao {

    /**
     * インサート<br/>
     *
     * @param mailTemplateEntity メールテンプレートエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(MailTemplateEntity mailTemplateEntity);

    /**
     * アップデート<br/>
     *
     * @param mailTemplateEntity メールテンプレートエンティティ
     * @return 処理件数
     */
    @Update
    int update(MailTemplateEntity mailTemplateEntity);

    /**
     * デリート<br/>
     *
     * @param mailTemplateEntity メールテンプレートエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(MailTemplateEntity mailTemplateEntity);

    /**
     * 指定されたシーケンスのエンティティを削除する
     *
     * @param shopSeq         ショップSEQ
     * @param mailTemplateSeq メールテンプレートSEQ
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteBySeq(Integer shopSeq, Integer mailTemplateSeq);

    /**
     * エンティティ取得<br/>
     *
     * @param mailTemplateSeq メールテンプレートSEQ
     * @return メールテンプレートエンティティ
     */
    @Select
    MailTemplateEntity getEntity(Integer mailTemplateSeq);

    /**
     * シーケンスでエンティティを取得
     *
     * @param shopSeq         間違ってとらないように保険
     * @param mailTemplateSeq シーケンス
     * @return エンティティ
     */
    @Select
    MailTemplateEntity getEntityBySeq(Integer shopSeq, Integer mailTemplateSeq);

    /**
     * タイプでエンティティを取得
     * 標準を１件だけほしい。
     * 標準がないのであれば一般でもよい。
     *
     * @param shopSeq          間違ってとらないように保険
     * @param mailTemplateType タイプ
     * @return エンティティ
     */
    @Select
    MailTemplateEntity getEntityByType(Integer shopSeq, HTypeMailTemplateType mailTemplateType);

    /**
     * 指定されたメールテンプレートタイプの標準レコードをロック取得する
     *
     * @param shopSeq          ショップSEQ
     * @param mailTemplateType メールテンプレートタイプ
     * @return エンティティ
     */
    @Select
    MailTemplateEntity selectDefaultForUpdate(Integer shopSeq, HTypeMailTemplateType mailTemplateType);

    /**
     * メールテンプレート見出しを取得する
     *
     * @param shopSeq ショップSEQ
     * @return 見出し一覧
     */
    @Select
    List<MailTemplateIndexDto> selectIndexList(Integer shopSeq);

    /**
     * 指定されたテンプレート種別の指定されたシーケンス以外のメールテンプレートを標準にする。
     *
     * @param shopSeq          ショップSEQ
     * @param mailTemplateType テンプレート種別
     * @param mailTemplateSeq  テンプレートSEQ
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateOthersAsGeneral(Integer shopSeq, HTypeMailTemplateType mailTemplateType, Integer mailTemplateSeq);

    /**
     * タイプ別メールテンプレートリスト取得
     *
     * @param shopSeq                 ショップSEQ
     * @param mailTemplateType        メールテンプレートタイプ
     * @param mailTemplateDefaultFlag メールテンプレート標準フラグ
     * @return テンプレートリスト
     */
    @Select
    List<MailTemplateEntity> getMailTemplateList(Integer shopSeq,
                                                 HTypeMailTemplateType mailTemplateType,
                                                 HTypeMailTemplateDefaultFlag mailTemplateDefaultFlag);

}
