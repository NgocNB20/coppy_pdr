package jp.co.hankyuhanshin.itec.cuenoteapi.pdr.response;

public class GetCuenoteApiGetDeliveryResponse {

    /** 配信ID */
    private Integer delivery_id;

    /** アドレス帳 ID */
    private String adbook_id;

    /** アドレス帳名 */
    private String adbook_name;

    /** メール文書セットID */
    private Integer mail_id;

    /** メール文書セット名 */
    private String mail_name;

    /** アドレスの絞り込み条件 (optional) */
    private String refinement;

    /** 過去配信やWebトラッキングの絞り込み条件 (optional) */
    private String conditions;

    /** フリークエンシーで除外する配信回数 (optional) */
    private Integer ratelimit_value;

    /** フリークエンシーで除外する配信回数の集計期間（日数） (optional) */
    private Integer ratelimit_window_day;

    /** 配信準備ステータス [‘wait’, ‘preparing’, ‘end’, ‘failed’] (optional) */
    private String fc_status;

    /** MTAの配信ステータス [‘wait’, ‘prepare’, ‘preparing’, ‘delivering’, ‘done’, ‘suspended’, ‘canceled’] (optional) */
    private String mta_status;

    /** 配信を開始する日時 (W3C DateTime) (optional) */
    private String delivery_time;

    /** MTAに配信を登録した日時 (W3C DateTime) (optional) */
    private String assemble_time;

    /** 初回配信完了日時 (W3C DateTime) (optional) */
    private String first_delivery_end_time;

    /** 配信完了日時 (W3C DateTime) (optional) */
    private String delivery_end_time;

    /** 除外するエラーカウント数 (optional) */
    private Integer error_count_threshold;

    /** 時間帯制御の配信開始時間 (optional) */
    private Integer time_period_starthour;

    /** 時間帯制御の配信終了時間 (optional) */
    private Integer time_period_endhour;

    /** 配信期限 (optional) */
    private Integer delivery_deadline_hour;

    /** 配信速度 (optional) */
    private Integer delivery_velocity;

    /** HTML画像処理 [‘linked’, ‘embedded’] (optional) */
    private String delivery_image_inlines;

    /** 承認機能の利用有無 (optional) */
    private boolean is_approval_use;

    /** 配信除外アドレス帳ID（ただし配信アーカイブ処理後はアドレス帳名） (optional) */
    private String exclusion_adbook_id;

    /** 重複アドレス帳利用時重複除外利用有無 (optional) */
    private boolean is_nonuniq_aggregation_use;

    /** 重複除外機能利用時の判定カラム (optional) */
    private String nonuniq_aggregation_column;

    /** 重複除外機能利用時の条件 [‘max’, ‘min’] (optional) */
    private String nonuniq_aggregation_op;

    /** テスト対象件数。単位は% (optional) */
    private Integer pause_success_percent;

    /** 予約時刻からAB判定までの待ち時間の○時間○分の時間の指定 (optional) */
    private Integer abtesting_measure_time_hour;

    /** 予約時刻からAB判定までの待ち時間の○時間○分の分の指定 (optional) */
    private Integer abtesting_measure_time_min;

    /** ABテストの効果測定方法 [‘open’, ‘click’] (optional) */
    private String abtesting_measure;

    /** 配信予定件数 */
    private Integer stat_count;

    /** 配信成功件数 */
    private Integer stat_success;

    /** 配信失敗件数 */
    private Integer stat_failure;

    /** 配信一時失敗件数 */
    private Integer stat_deferral;

    /**
     * @return the delivery_id
     */
    public Integer getDelivery_id() {
        return delivery_id;
    }

    /**
     * @param delivery_id the delivery_id to set
     */
    public void setDelivery_id(Integer delivery_id) {
        this.delivery_id = delivery_id;
    }

    /**
     * @return the adbook_id
     */
    public String getAdbook_id() {
        return adbook_id;
    }

    /**
     * @param adbook_id the adbook_id to set
     */
    public void setAdbook_id(String adbook_id) {
        this.adbook_id = adbook_id;
    }

    /**
     * @return the adbook_name
     */
    public String getAdbook_name() {
        return adbook_name;
    }

    /**
     * @param adbook_name the adbook_name to set
     */
    public void setAdbook_name(String adbook_name) {
        this.adbook_name = adbook_name;
    }

    /**
     * @return the mail_id
     */
    public Integer getMail_id() {
        return mail_id;
    }

    /**
     * @param mail_id the mail_id to set
     */
    public void setMail_id(Integer mail_id) {
        this.mail_id = mail_id;
    }

    /**
     * @return the mail_name
     */
    public String getMail_name() {
        return mail_name;
    }

    /**
     * @param mail_name the mail_name to set
     */
    public void setMail_name(String mail_name) {
        this.mail_name = mail_name;
    }

    /**
     * @return the refinement
     */
    public String getRefinement() {
        return refinement;
    }

    /**
     * @param refinement the refinement to set
     */
    public void setRefinement(String refinement) {
        this.refinement = refinement;
    }

    /**
     * @return the conditions
     */
    public String getConditions() {
        return conditions;
    }

    /**
     * @param conditions the conditions to set
     */
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    /**
     * @return the ratelimit_value
     */
    public Integer getRatelimit_value() {
        return ratelimit_value;
    }

    /**
     * @param ratelimit_value the ratelimit_value to set
     */
    public void setRatelimit_value(Integer ratelimit_value) {
        this.ratelimit_value = ratelimit_value;
    }

    /**
     * @return the ratelimit_window_day
     */
    public Integer getRatelimit_window_day() {
        return ratelimit_window_day;
    }

    /**
     * @param ratelimit_window_day the ratelimit_window_day to set
     */
    public void setRatelimit_window_day(Integer ratelimit_window_day) { this.ratelimit_window_day = ratelimit_window_day; }

    /**
     * @return the fc_status
     */
    public String getFc_status() {
        return fc_status;
    }

    /**
     * @param fc_status the fc_status to set
     */
    public void setFc_status(String fc_status) {
        this.fc_status = fc_status;
    }

    /**
     * @return the mta_status
     */
    public String getMta_status() {
        return mta_status;
    }

    /**
     * @param mta_status the mta_status to set
     */
    public void setMta_status(String mta_status) {
        this.mta_status = mta_status;
    }

    /**
     * @return the delivery_time
     */
    public String getDelivery_time() {
        return delivery_time;
    }

    /**
     * @param delivery_time the delivery_time to set
     */
    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    /**
     * @return the assemble_time
     */
    public String getAssemble_time() {
        return assemble_time;
    }

    /**
     * @param assemble_time the assemble_time to set
     */
    public void setAssemble_time(String assemble_time) {
        this.assemble_time = assemble_time;
    }

    /**
     * @return the first_delivery_end_time
     */
    public String getFirst_delivery_end_time() {
        return first_delivery_end_time;
    }

    /**
     * @param first_delivery_end_time the first_delivery_end_time to set
     */
    public void setFirst_delivery_end_time(String first_delivery_end_time) {
        this.first_delivery_end_time = first_delivery_end_time;
    }

    /**
     * @return the delivery_end_time
     */
    public String getDelivery_end_time() {
        return delivery_end_time;
    }

    /**
     * @param delivery_end_time the delivery_end_time to set
     */
    public void setDelivery_end_time(String delivery_end_time) {
        this.delivery_end_time = delivery_end_time;
    }

    /**
     * @return the error_count_threshold
     */
    public Integer getError_count_threshold() {
        return error_count_threshold;
    }

    /**
     * @param error_count_threshold the error_count_threshold to set
     */
    public void setError_count_threshold(Integer error_count_threshold) {
        this.error_count_threshold = error_count_threshold;
    }

    /**
     * @return the time_period_starthour
     */
    public Integer getTime_period_starthour() {
        return time_period_starthour;
    }

    /**
     * @param time_period_starthour the time_period_starthour to set
     */
    public void setTime_period_starthour(Integer time_period_starthour) {
        this.time_period_starthour = time_period_starthour;
    }

    /**
     * @return the time_period_endhour
     */
    public Integer getTime_period_endhour() {
        return time_period_endhour;
    }

    /**
     * @param time_period_endhour the time_period_endhour to set
     */
    public void setTime_period_endhour(Integer time_period_endhour) {
        this.time_period_endhour = time_period_endhour;
    }

    /**
     * @return the delivery_deadline_hour
     */
    public Integer getDelivery_deadline_hour() {
        return delivery_deadline_hour;
    }

    /**
     * @param delivery_deadline_hour the delivery_deadline_hour to set
     */
    public void setDelivery_deadline_hour(Integer delivery_deadline_hour) {
        this.delivery_deadline_hour = delivery_deadline_hour;
    }

    /**
     * @return the delivery_velocity
     */
    public Integer getDelivery_velocity() {
        return delivery_velocity;
    }

    /**
     * @param delivery_velocity the delivery_velocity to set
     */
    public void setDelivery_velocity(Integer delivery_velocity) {
        this.delivery_velocity = delivery_velocity;
    }

    /**
     * @return the delivery_image_inlines
     */
    public String getDelivery_image_inlines() {
        return delivery_image_inlines;
    }

    /**
     * @param delivery_image_inlines the delivery_image_inlines to set
     */
    public void setDelivery_image_inlines(String delivery_image_inlines) {
        this.delivery_image_inlines = delivery_image_inlines;
    }

    /**
     * @return the is_approval_use
     */
    public boolean getIs_approval_use() {
        return is_approval_use;
    }

    /**
     * @param is_approval_use the is_approval_use to set
     */
    public void setIs_approval_use(boolean is_approval_use) {
        this.is_approval_use = is_approval_use;
    }

    /**
     * @return the exclusion_adbook_id
     */
    public String getExclusion_adbook_id() {
        return exclusion_adbook_id;
    }

    /**
     * @param exclusion_adbook_id the exclusion_adbook_id to set
     */
    public void setExclusion_adbook_id(String exclusion_adbook_id) {
        this.exclusion_adbook_id = exclusion_adbook_id;
    }

    /**
     * @return the is_nonuniq_aggregation_use
     */
    public boolean getIs_nonuniq_aggregation_use() {
        return is_nonuniq_aggregation_use;
    }

    /**
     * @param is_nonuniq_aggregation_use the is_nonuniq_aggregation_use to set
     */
    public void setIs_nonuniq_aggregation_use(boolean is_nonuniq_aggregation_use) {
        this.is_nonuniq_aggregation_use = is_nonuniq_aggregation_use;
    }

    /**
     * @return the nonuniq_aggregation_column
     */
    public String getNonuniq_aggregation_column() {
        return nonuniq_aggregation_column;
    }

    /**
     * @param nonuniq_aggregation_column the nonuniq_aggregation_column to set
     */
    public void setNonuniq_aggregation_column(String nonuniq_aggregation_column) {
        this.nonuniq_aggregation_column = nonuniq_aggregation_column;
    }

    /**
     * @return the nonuniq_aggregation_op
     */
    public String getNonuniq_aggregation_op() {
        return nonuniq_aggregation_op;
    }

    /**
     * @param nonuniq_aggregation_op the nonuniq_aggregation_op to set
     */
    public void setNonuniq_aggregation_op(String nonuniq_aggregation_op) {
        this.nonuniq_aggregation_op = nonuniq_aggregation_op;
    }

    /**
     * @return the pause_success_percent
     */
    public Integer getPause_success_percent() {
        return pause_success_percent;
    }

    /**
     * @param pause_success_percent the pause_success_percent to set
     */
    public void setPause_success_percent(Integer pause_success_percent) {
        this.pause_success_percent = pause_success_percent;
    }

    /**
     * @return the abtesting_measure_time_hour
     */
    public Integer getAbtesting_measure_time_hour() {
        return abtesting_measure_time_hour;
    }

    /**
     * @param abtesting_measure_time_hour the abtesting_measure_time_hour to set
     */
    public void setAbtesting_measure_time_hour(Integer abtesting_measure_time_hour) {
        this.abtesting_measure_time_hour = abtesting_measure_time_hour;
    }

    /**
     * @return the abtesting_measure_time_min
     */
    public Integer getAbtesting_measure_time_min() {
        return abtesting_measure_time_min;
    }

    /**
     * @param abtesting_measure_time_min the abtesting_measure_time_min to set
     */
    public void setAbtesting_measure_time_min(Integer abtesting_measure_time_min) {
        this.abtesting_measure_time_min = abtesting_measure_time_min;
    }

    /**
     * @return the abtesting_measure
     */
    public String getAbtesting_measure() {
        return abtesting_measure;
    }

    /**
     * @param abtesting_measure the abtesting_measure to set
     */
    public void setAbtesting_measure(String abtesting_measure) {
        this.abtesting_measure = abtesting_measure;
    }

    /**
     * @return the stat_count
     */
    public Integer getStat_count() {
        return stat_count;
    }

    /**
     * @param stat_count the stat_count to set
     */
    public void setStat_count(Integer stat_count) {
        this.stat_count = stat_count;
    }

    /**
     * @return the stat_success
     */
    public Integer getStat_success() {
        return stat_success;
    }

    /**
     * @param stat_success the stat_success to set
     */
    public void setStat_success(Integer stat_success) {
        this.stat_success = stat_success;
    }

    /**
     * @return the stat_failure
     */
    public Integer getStat_failure() {
        return stat_failure;
    }

    /**
     * @param stat_failure the stat_failure to set
     */
    public void setStat_failure(Integer stat_failure) {
        this.stat_failure = stat_failure;
    }

    /**
     * @return the stat_deferral
     */
    public Integer getStat_deferral() {
        return stat_deferral;
    }

    /**
     * @param stat_deferral the stat_deferral to set
     */
    public void setStat_deferral(Integer stat_deferral) {
        this.stat_deferral = stat_deferral;
    }

}
