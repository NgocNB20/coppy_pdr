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
 * 配信情報取得APIレスポンスDTO
 *
 * @author st75001
 */
@Data
@Component
@Scope("prototype")
public class CuenoteApiGetDeliveryResponseDto implements Serializable {

    /** serialVersionUID */
    public static final long serialVersionUID = 1L;

    /** 配信ID */
    @JSONHint(name = "delivery_id")
    public Integer deliveryId;

    /** アドレス帳ID */
    @JSONHint(name = "adbook_id")
    public String adBookId;

    /** アドレス帳名 */
    @JSONHint(name = "adbook_name")
    public String adBookName;

    /** メール文書セットID */
    @JSONHint(name = "mail_id")
    public Integer mailId;

    /** メール文書セット名 */
    @JSONHint(name = "mail_name")
    public String mailName;

    /** アドレスの絞り込み条件 (optional) */
    @JSONHint(name = "refinement")
    public String refinement;

    /** 過去配信やWebトラッキングの絞り込み条件 (optional) */
    @JSONHint(name = "conditions")
    public String conditions;

    /** フリークエンシーで除外する配信回数 (optional) */
    @JSONHint(name = "ratelimit_value")
    public Integer rateLimitValue;

    /** フリークエンシーで除外する配信回数の集計期間（日数） (optional) */
    @JSONHint(name = "ratelimit_window_day")
    public Integer rateLimitWindowDay;

    /** 配信準備ステータス [‘wait’, ‘preparing’, ‘end’, ‘failed’] (optional) */
    @JSONHint(name = "fc_status")
    public String fcStatus;

    /** MTAの配信ステータス [‘wait’, ‘prepare’, ‘preparing’, ‘delivering’, ‘done’, ‘suspended’, ‘canceled’] (optional) */
    @JSONHint(name = "mta_status")
    public String mtaStatus;

    /** 配信を開始する日時 (W3C DateTime) (optional) */
    @JSONHint(name = "delivery_time")
    public String deliveryTime;

    /** MTAに配信を登録した日時 (W3C DateTime) (optional) */
    @JSONHint(name = "assemble_time")
    public String assembleTime;

    /** 初回配信完了日時 (W3C DateTime) (optional) */
    @JSONHint(name = "first_delivery_end_time")
    public String firstDeliveryEndTime;

    /** 配信完了日時 (W3C DateTime) (optional) */
    @JSONHint(name = "delivery_end_time")
    public String deliveryEndTime;

    /** 除外するエラーカウント数 (optional) */
    @JSONHint(name = "error_count_threshold")
    public Integer errorCountThreshold;

    /** 時間帯制御の配信開始時間 (optional) */
    @JSONHint(name = "time_period_starthour")
    public Integer timePeriodStartHour;

    /** 時間帯制御の配信終了時間 (optional) */
    @JSONHint(name = "time_period_endhour")
    public Integer timePeriodEndHour;

    /** 配信期限 (optional) */
    @JSONHint(name = "delivery_deadline_hour")
    public Integer deliveryDeadlineHour;

    /** 配信速度 (optional) */
    @JSONHint(name = "delivery_velocity")
    public Integer deliveryVelocity;

    /** HTML画像処理 [‘linked’, ‘embedded’] (optional) */
    @JSONHint(name = "delivery_image_inlines")
    public String deliveryImageInlines;

    /** 承認機能の利用有無 (optional) */
    @JSONHint(name = "is_approval_use")
    public boolean isApprovalUse;

    /** 配信除外アドレス帳ID（ただし配信アーカイブ処理後はアドレス帳名） (optional) */
    @JSONHint(name = "exclusion_adbook_id")
    public String exclusionAdBookId;

    /** 重複アドレス帳利用時重複除外利用有無 (optional) */
    @JSONHint(name = "is_nonuniq_aggregation_use")
    public boolean isNonuniqAggregationUse;

    /** 重複除外機能利用時の判定カラム (optional) */
    @JSONHint(name = "nonuniq_aggregation_column")
    public String nonuniqAggregationColumn;

    /** 重複除外機能利用時の条件 [‘max’, ‘min’] (optional) */
    @JSONHint(name = "nonuniq_aggregation_op")
    public String nonuniqAggregationOp;

    /** テスト対象件数。単位は% (optional) */
    @JSONHint(name = "pause_success_percent")
    public Integer pauseSuccessPercent;

    /** 予約時刻からAB判定までの待ち時間の○時間○分の時間の指定 (optional) */
    @JSONHint(name = "abtesting_measure_time_hour")
    public Integer abTestingMeasureTimeHour;

    /** 予約時刻からAB判定までの待ち時間の○時間○分の分の指定 (optional) */
    @JSONHint(name = "abtesting_measure_time_min")
    public Integer abTestingMeasureTimeMin;

    /** ABテストの効果測定方法 [‘open’, ‘click’] (optional) */
    @JSONHint(name = "abtesting_measure")
    public String abTestingMeasure;

    /** 配信予定件数 */
    @JSONHint(name = "stat_count")
    public Integer statCount;

    /** 配信成功件数 */
    @JSONHint(name = "stat_success")
    public Integer statSuccess;

    /** 配信失敗件数 */
    @JSONHint(name = "stat_failure")
    public Integer statFailure;

    /** 配信一時失敗件数 */
    @JSONHint(name = "stat_deferral")
    public Integer statDeferral;
}