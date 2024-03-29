/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.ReceiverDateDto;

import java.sql.Timestamp;
import java.util.List;

/**
 * お届け希望日取得ロジック<br/>
 *
 * @author hs32101
 */
public interface ReceiverDateGetLogic {

    /**
     * お届け希望日DTO作成処理<br/>
     * お届け希望日DTOを作成し、配送DTOにセットする<br/>
     *
     * @param deliveryDtoList 配送DTOリスト
     * @param nonSelectFlag   指定なし選択肢有無(true..あり、false..なし)
     */
    void createReceiverDateList(List<DeliveryDto> deliveryDtoList, boolean nonSelectFlag);

    /**
     * お届け希望日DTO作成判定<br/>
     * 配送リードタイム、選択可能日数を元に、お届け希望日DTOを作成するか判定<br/>
     *
     * @param leadTime          配送リードタイム
     * @param selectDays        選択可能日数
     * @param nonSelectFlag     指定なし選択肢有無(true..あり、false..なし)
     * @param deliveryMethodSeq 配送方法SEQ
     * @return お届け希望日DTO
     */
    ReceiverDateDto checkCreateReceiverDateList(int leadTime,
                                                int selectDays,
                                                boolean nonSelectFlag,
                                                Integer deliveryMethodSeq);

    /**
     * お届け希望日DTO作成処理<br/>
     * 配送リードタイム、選択可能日数を元に、お届け希望日DTOを作成する<br/>
     *
     * @param receiverDateDto   お届け希望日DTO
     * @param leadTime          配送リードタイム
     * @param selectDays        選択可能日数
     * @param deliveryMethodSeq 配送方法SEQ
     * @return お届け希望日プルダウン開始日
     */
    Timestamp createReceiverDateList(ReceiverDateDto receiverDateDto,
                                     int leadTime,
                                     int selectDays,
                                     Integer deliveryMethodSeq);

    /**
     * お届け不可日判定<br/>
     * お届け不可日であった場合はエラーとし、次画面に遷移させない<br/>
     *
     * @param deliveryList 受注配送リスト
     * @return エラー:true エラーではない:false
     */
    boolean checkDeliveryImpossibleDay(OrderDeliveryDto deliveryList);

}
