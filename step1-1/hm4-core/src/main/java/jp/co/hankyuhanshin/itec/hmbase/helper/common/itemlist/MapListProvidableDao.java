/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hmbase.helper.common.itemlist;

import java.util.List;
import java.util.Map;

/**
 * HUItemListアノテーションのProviderに指定するDaoが実装するインターフェース<br/>
 *
 * @author matsumoto
 * @version $Revision: 1.2 $
 */
public interface MapListProvidableDao {
    /**
     * 選択項目リストの作成に利用するデータを返却する<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 問い合わせ結果を格納したMap
     */
    List<Map<String, ?>> getItemMapList(Integer shopSeq);
}
