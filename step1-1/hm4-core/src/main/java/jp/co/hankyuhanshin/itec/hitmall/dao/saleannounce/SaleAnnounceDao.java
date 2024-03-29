/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.saleannounce;

import jp.co.hankyuhanshin.itec.hitmall.dto.saleannounce.SaleAnnounceAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.saleannounce.SaleAnnounceMailListResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.saleannounce.SaleAnnounceMailEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * セールお知らせDaoクラス
 *
 * @author takasima
 */
@Dao
@ConfigAutowireable
public interface SaleAnnounceDao {

    /**
     * デリート
     *
     * @param  saleAnnounceMailEntity セールお知らせエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(SaleAnnounceMailEntity saleAnnounceMailEntity);

    /**
     * インサート
     *
     * @param saleAnnounceMailEntity セールお知らせエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(SaleAnnounceMailEntity saleAnnounceMailEntity);

    /**
     * アップデート
     *
     * @param  saleAnnounceMailEntity セールお知らせエンティティ
     * @return 処理件数
     */
    @Update
    int update(SaleAnnounceMailEntity saleAnnounceMailEntity);

    /**
     * セールお知らせメールエンティティを取得
     *
     * @return メール配信情報取得
     */
    @Select
    List<SaleAnnounceMailListResultDto> getDeliveryStatus();

    /**
     * 配信中の入荷お知らせメールリストを取得
     *
     * @param keyList 商品SEQと会員SEQをまとめたキー
     * @return セールお知らせメールエンティティ
     */
    @Select
    List<SaleAnnounceAddImportListDto> getSaleAddImportList(List<String> keyList);

}
