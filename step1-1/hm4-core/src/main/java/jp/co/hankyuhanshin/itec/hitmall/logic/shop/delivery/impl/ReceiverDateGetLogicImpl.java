/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverDateDesignationFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleDayDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.HolidayDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.ReceiverDateDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.ReceiverDateGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * お届け希望日取得ロジック<br/>
 *
 * @author hs32101
 */
@Component
public class ReceiverDateGetLogicImpl extends AbstractShopLogic implements ReceiverDateGetLogic {

    /**
     * 休日Dao
     */
    private final HolidayDao holidayDao;

    /**
     * お届け不可日Dao
     */
    private final DeliveryImpossibleDayDao deliveryImpossibleDayDao;

    @Autowired
    public ReceiverDateGetLogicImpl(HolidayDao holidayDao, DeliveryImpossibleDayDao deliveryImpossibleDayDao) {
        this.holidayDao = holidayDao;
        this.deliveryImpossibleDayDao = deliveryImpossibleDayDao;
    }

    /**
     * お届け希望日DTO作成処理<br/>
     * お届け希望日DTOを作成し、配送DTOにセットする<br/>
     *
     * @param deliveryDtoList 配送DTOリスト
     * @param nonSelectFlag   指定なし選択肢有無(true..あり、false..なし)
     */
    @Override
    public void createReceiverDateList(List<DeliveryDto> deliveryDtoList, boolean nonSelectFlag) {

        // 配送方法ごとに処理
        for (DeliveryDto dto : deliveryDtoList) {
            // リードタイム、選択可能日数を取得
            int leadTime = dto.getDeliveryDetailsDto().getDeliveryLeadTime();
            int selectDays = dto.getDeliveryDetailsDto().getPossibleSelectDays();

            // お届け日リスト作成
            ReceiverDateDto receiverDateDto = this.checkCreateReceiverDateList(leadTime, selectDays, nonSelectFlag,
                                                                               dto.getDeliveryDetailsDto()
                                                                                  .getDeliveryMethodSeq()
                                                                              );
            // 配送方法DTOにお届け日リストをセット
            dto.setReceiverDateDto(receiverDateDto);
        }

    }

    /**
     * お届け希望日DTO作成判定<br/>
     * 配送リードタイム、選択可能日数を元に、お届け希望日DTOを作成判定<br/>
     *
     * @param leadTime      配送リードタイム
     * @param selectDays    選択可能日数
     * @param nonSelectFlag 指定なし選択肢有無(true..あり、false..なし)
     * @return お届け希望日DTO
     */
    @Override
    public ReceiverDateDto checkCreateReceiverDateList(int leadTime,
                                                       int selectDays,
                                                       boolean nonSelectFlag,
                                                       Integer deliveryMethodSeq) {

        ReceiverDateDto receiverDateDto = getComponent(ReceiverDateDto.class);

        // 最短お届け日の算出
        // 選択日数 1日、指定なし選択肢 なし で作成
        receiverDateDto.setShortestDeliveryDateToRegist(
                        createReceiverDateList(receiverDateDto, leadTime, 1, deliveryMethodSeq));

        // 選択可能日数が0か判定
        if (selectDays == 0) {
            receiverDateDto.setDateMap(null);
            receiverDateDto.setReceiverDateDesignationFlag(HTypeReceiverDateDesignationFlag.OFF);
            return receiverDateDto;
        } else {
            receiverDateDto.setReceiverDateDesignationFlag(HTypeReceiverDateDesignationFlag.ON);
        }

        Timestamp insertTime = createReceiverDateList(receiverDateDto, leadTime, selectDays, deliveryMethodSeq);

        // 日付Mapを作成し、DTOにセット
        receiverDateDto.setDateMap(this.createDateMap(insertTime, selectDays, nonSelectFlag, deliveryMethodSeq));

        return receiverDateDto;
    }

    /**
     * お届け希望日DTO作成処理<br/>
     * 配送リードタイム、選択可能日数を元に、お届け希望日DTOを作成する<br/>
     * <p>
     * <br/>【以下説明】 2015/07/10<br/>
     * 出荷予定日 : 現在日 + 配送リードタイム<br/>
     * （現在日から出荷予定日内に休業日の場合は、日付をずらす）<br/>
     * お届け希望日開始日 : 出荷予定日 + 配送準備日数 + 1<br/>
     * お届け希望日終了日 : 出荷予定日 + 配送準備日数 + 選択可能日数<br/>
     * （配送準備日数はあくまでも準備のため、希望時間帯に配送するには +1日必要）<br/>
     *
     * @param receiverDateDto お届け希望日DTO
     * @param leadTime        配送リードタイム
     * @param selectDays      選択可能日数
     * @return お届け希望日プルダウン開始日
     */
    @Override
    public Timestamp createReceiverDateList(ReceiverDateDto receiverDateDto,
                                            int leadTime,
                                            int selectDays,
                                            Integer deliveryMethodSeq) {

        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // システム日付を取得
        Timestamp now = dateUtility.getCurrentDate();
        Timestamp leadTimeDate;
        // 即日出荷フラグ
        boolean sameDayShippingFlag;

        if (leadTime == 0) {
            // リードタイム 0 の場合は注文日が、休業日でないか判定する必要がある
            leadTimeDate = now;
            sameDayShippingFlag = true;
        } else {
            // 注文日はリードタイムに含めないため、翌日からの判定となる
            leadTimeDate = dateUtility.getAmountDayTimestamp(1, true, now);
            sameDayShippingFlag = false;
        }
        // 休業日を考慮した加算日数
        int leadTimeCnt = leadTime;

        while (true) {
            int holiday = holidayDao.getCountByDate(leadTimeDate, deliveryMethodSeq);
            if (holiday != 0) {
                // 注文日はリードタイムに含めない
                if (!leadTimeDate.equals(now)) {
                    leadTimeCnt++;
                }
            } else {
                leadTime--;

                if (leadTimeCalculationEnd(sameDayShippingFlag, leadTime)) {
                    break;
                }
            }
            leadTimeDate = dateUtility.getAmountDayTimestamp(1, true, leadTimeDate);
        }

        // システムプロパティ値から配送準備日数を取得
        String deliveryNumberOfDays = PropertiesUtil.getSystemPropertiesValue("delivery.number.days");
        // リードタイムに加算
        leadTimeCnt += Integer.parseInt(deliveryNumberOfDays);

        // 開始日(システム日付+リードタイム)を取得
        // お届け希望時間帯に配送するために 1日加算
        Timestamp insertTime = dateUtility.getAmountDayTimestamp(leadTimeCnt + 1, true, now);

        return insertTime;
    }

    /**
     * お届け不可日判定<br/>
     * お届け不可日であった場合はエラーとし、次画面に遷移させない<br/>
     *
     * @param deliveryDto 受注配送
     * @return true:選択エラー
     */
    @Override
    public boolean checkDeliveryImpossibleDay(OrderDeliveryDto deliveryDto) {

        // 配送不可日判定(お届け希望日がnullの場合はスキップ
        if (deliveryDto.getOrderDeliveryEntity().getReceiverDate() != null) {
            int deliveryImpossibleDay = deliveryImpossibleDayDao.getCountByDate(
                            deliveryDto.getOrderDeliveryEntity().getReceiverDate(),
                            deliveryDto.getOrderDeliveryEntity().getDeliveryMethodSeq()
                                                                               );
            if (deliveryImpossibleDay != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * リードタイム計算終了判定メソッド<br/>
     * 即日配送 : リードタイム < 0 <br/>
     * その他 : リードタイム <= 0 <br/>
     * 即日配送の場合は、リードタイム 0のため考慮に入れる必要がある。<br/>
     *
     * @param sameDayShippingFlag 即日配送フラグ
     * @param leadTime            リードタイム
     * @return true:終了 / false:未終了
     */
    private boolean leadTimeCalculationEnd(boolean sameDayShippingFlag, int leadTime) {
        boolean calculationEndFlag = false;

        if (sameDayShippingFlag) {
            if (leadTime < 0) {
                calculationEndFlag = true;
            }
        } else {
            if (leadTime <= 0) {
                calculationEndFlag = true;
            }
        }
        return calculationEndFlag;
    }

    /**
     * 日付Mapを作成する<br/>
     * パラメータの指定日付~選択可能日数の日付分でMapを作成する<br/>
     * 選択可能日に配送不可日が存在する場合は配送不可であることを明示する<br/>
     *
     * @param insertTime        指定日付
     * @param selectDays        選択可能日数
     * @param nonSelectFlag     指定なし選択肢有無(true..あり、false..なし)
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 日時Map(key = YYYYMMDD, value = YYYY / MM / DD)
     */
    private Map<String, String> createDateMap(Timestamp insertTime,
                                              int selectDays,
                                              boolean nonSelectFlag,
                                              Integer deliveryMethodSeq) {

        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Map<String, String> map = new LinkedHashMap<>();

        // フラグがONの場合、「指定なし」をMapの先頭にセット
        if (nonSelectFlag) {
            map.put(ReceiverDateDto.NON_SELECT_KEY, ReceiverDateDto.NON_SELECT_VALUE);
        }

        // 選択可能日数分ループ
        for (int i = 0; i < selectDays; i++) {
            // 配送不可日判定
            int deliveryImpossibleDay = deliveryImpossibleDayDao.getCountByDate(insertTime, deliveryMethodSeq);
            if (deliveryImpossibleDay != 0) {
                // Map(YYYY/MM/DD,YYYY/MM/DD ×配送不可)を作成
                map.put(dateUtility.formatYmd(insertTime), ReceiverDateDto.getFormatMdWithWeek(insertTime) + " ×配送不可");
            } else {
                // Map(YYYY/MM/DD,YYYY/MM/DD)を作成
                map.put(dateUtility.formatYmd(insertTime), ReceiverDateDto.getFormatMdWithWeek(insertTime));
            }
            // 日付を1日進める
            insertTime = dateUtility.getAmountDayTimestamp(1, true, insertTime);
        }
        return map;
    }

}
