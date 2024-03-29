/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.top;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.top.TopInitialaizeInfoDto;

/**
 * トップ画面初期表示用サービスインターフェース
 *
 * @author $author$
 * @version $Revision: 1.3 $
 */
public interface TopInitialaizeInfoGetService {

    /**
     * マップキー：TABLE
     */
    String TABLE = "table";

    /**
     * マップキー：TABLE
     */
    String CHART = "chart";

    /**
     * トップ画面初期表示情報を取得します<br >
     *
     * @return TopInitialaizeInfoDto
     */
    TopInitialaizeInfoDto execute();
}
