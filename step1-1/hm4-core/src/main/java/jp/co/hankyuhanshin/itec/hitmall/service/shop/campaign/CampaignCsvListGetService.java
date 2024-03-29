/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign;

import java.io.IOException;

/**
 * 広告効果CSV出力<br/>
 *
 * @author kimura
 * @version $Revision: 1.2 $
 *
 */
public interface CampaignCsvListGetService {

    String FILE_NAME = "adEffect";

    void execute(Object... parameters) throws IOException;

}
