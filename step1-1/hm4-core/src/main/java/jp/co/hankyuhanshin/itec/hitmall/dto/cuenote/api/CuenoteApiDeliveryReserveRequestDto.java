/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api;

import lombok.Data;
import net.arnx.jsonic.JSONHint;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Cuenote API
 * 配信情報予約APIリクエストDTO
 *
 * @author st75001
 */
@Data
@Component
@Scope("prototype")
public class CuenoteApiDeliveryReserveRequestDto implements Serializable {

    /** serialVersionUID */
    public static final long serialVersionUID = 1L;

    /** アドレス帳IDを指定。（必須） */
    @JSONHint(name = "adbook_id")
    public String adBookId;

    /** メール文書セットIDを指定。（必須） */
    @JSONHint(name = "mail_id")
    public Integer mailId;

    /** アドレスの絞り込みを指定。 */
    @JSONHint(name = "refinement")
    public String refinement;

    /** 過去配信やWebトラッキングの絞り込みを指定。 */
    @JSONHint(name = "conditions")
    public String conditions;

    /** フリークエンシーで除外する配信回数を指定。 */
    @JSONHint(name = "ratelimit_value")
    public Integer rateLimitValue;

    /** フリークエンシーで除外する配信回数の集計期間を日数で指定。 */
    @JSONHint(name = "ratelimit_window_day")
    public Integer rateLimitWindowDay;

    /** 配信を開始する日時を指定。 時刻はW3C-DTFで指定。（必須） */
    @JSONHint(name = "delivery_time")
    public String deliveryTime;

    /** 除外するエラーカウント数を指定。 */
    @JSONHint(name = "error_count_threshold")
    public Integer errorCountThreshold;

    /** 時間帯制御の配信開始時間を指定。 */
    @JSONHint(name = "time_period_starthour")
    public Integer timePeriodStartHour;

    /** 時間帯制御の配信終了時間を指定。 */
    @JSONHint(name = "time_period_endhour")
    public Integer timePeriodEndHour;

    /** 配信期限を時間単位で指定。 */
    @JSONHint(name = "delivery_deadline_hour")
    public Integer deliveryDeadlineHour;

    /** 配信速度を秒間辺りの通数で指定。 */
    @JSONHint(name = "delivery_velocity")
    public Integer deliveryVelocity;

    /** HTML画像の処理方法を指定。 */
    @JSONHint(name = "delivery_image_inlines")
    public String deliveryImageInlines;

    /** 承認機能の利用有無を指定。 */
    @JSONHint(name = "is_approval_use")
    public boolean isApprovalUse;

    /** 配信除外アドレス帳IDを指定。 */
    @JSONHint(name = "exclusion_adbook_id")
    public String exclusionAdBookId;

    /** 重複アドレス帳利用時重複除外利用有無を指定。 */
    @JSONHint(name = "is_nonuniq_aggregation_use")
    public boolean isNonuniqAggregationUse;

    /** 重複除外機能利用時の判定カラムを指定。 */
    @JSONHint(name = "nonuniq_aggregation_column")
    public String nonuniqAggregationColumn;

    /** 重複除外機能利用時の条件を指定。 */
    @JSONHint(name = "nonuniq_aggregation_op")
    public String nonuniqAggregationOp;

    /** テスト対象件数を%で指定。1～100までの値が指定出来ます。 */
    @JSONHint(name = "pause_success_percent")
    public Integer pauseSuccessPercent;

    /** 予約時刻からAB判定までの待ち時間を指定。 */
    @JSONHint(name = "abtesting_measure_time_hour")
    public Integer abTestingMeasureTimeHour;

    /** 予約時刻からAB判定までの待ち時間を指定。 */
    @JSONHint(name = "abtesting_measure_time_min")
    public Integer abTestingMeasureTimeMin;

    /** ABテストの効果測定方法を開封率またはクリック率から選びます。 */
    @JSONHint(name = "abtesting_measure")
    public String abTestingMeasure;

}