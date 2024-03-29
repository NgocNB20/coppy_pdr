/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import lombok.Data;

/**
 * PDR#035 代理注文時のフロントサイト利用<br/>
 * リダイレクト用Pageクラス<br/>
 */
@Data
public class ProxyModel extends AbstractModel {

    /**
     * 会員SEQ<br/>
     */
    public Integer memberInfoSeq;

    /**
     * 検索条件保持判定用<br/>
     * 遷移元パッケージを格納<br/>
     */
    public String from;
}
