/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.utility;

import org.springframework.stereotype.Component;

/**
 * 画像操作ヘルパークラス
 *
 * @author shibuya
 * @author Iwama (Itec) 2010/08/25 チケット #2202 対応
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class ImageUtility {

    /**
     * 画像拡張子
     */
    public static final String EXTENSION = ".svg";

    /**
     * コンストラクタ<br/>
     */
    public ImageUtility() {
        // nop
    }

}
