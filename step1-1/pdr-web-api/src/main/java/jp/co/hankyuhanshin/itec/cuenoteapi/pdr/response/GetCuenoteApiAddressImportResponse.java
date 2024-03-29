package jp.co.hankyuhanshin.itec.cuenoteapi.pdr.response;

public class GetCuenoteApiAddressImportResponse {

    /** アドレス帳 ID */
    private String adbook;

    /** 追加されたレコード数（deleteモード時はnull） */
    private Integer add_record;

    /** インポート処理情報の作成時刻 */
    private String createtime;

    /** 削除されたレコード数（insert/replaceモード時はnull） */
    private Integer delete_record;

    /** 形式エラーで追加されなかったレコード数（deleteモード時はnull） */
    private Integer error_record;

    /** インポート反映処理の終了時刻 */
    private String execute_end_time;

    /** インポート反映処理にかかった時間（秒） */
    private Number execute_sec;

    /** インポート反映処理の開始時刻 */
    private String execute_start_time;

    /** インポート処理ID */
    private String import_key;

    /** インポートの方式（X-Modeの値） */
    private String import_type;

    /** 存在せず削除できなかったレコード数（insert/replaceモード時はnull） */
    private Integer notexists_record;

    /** インポート準備処理の終了時刻 */
    private String prepare_end_time;

    /** インポート準備処理にかかった時間（秒） */
    private Number prepare_sec;

    /** インポート準備処理の開始時刻 */
    private String prepare_start_time;

    /** インポートデータの全レコード数 */
    private Integer total_record;

    /** 更新されたレコード数 */
    private Integer update_record;

    /** インポート処理情報の更新時刻 */
    private String updatetime;

    /** ファイル処理にかかった時間（秒） */
    private Number upload_sec;

    /** ファイル処理の開始時刻 */
    private String upload_start_time;

    /**
     * @return the adbook
     */
    public String getAdbook() {
        return adbook;
    }

    /**
     * @param adbook the adbook to set
     */
    public void setAdbook(String adbook) {
        this.adbook = adbook;
    }

    /**
     * @return the add_record
     */
    public Integer getAdd_record() {
        return add_record;
    }

    /**
     * @param add_record the add_record to set
     */
    public void setAdd_record(Integer add_record) {
        this.add_record = add_record;
    }

    /**
     * @return the createtime
     */
    public String getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime the createtime to set
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    /**
     * @return the delete_record
     */
    public Integer getDelete_record() {
        return delete_record;
    }

    /**
     * @param delete_record the delete_record to set
     */
    public void setDelete_record(Integer delete_record) {
        this.delete_record = delete_record;
    }

    /**
     * @return the error_record
     */
    public Integer getError_record() {
        return error_record;
    }

    /**
     * @param error_record the error_record to set
     */
    public void setError_record(Integer error_record) {
        this.error_record = error_record;
    }

    /**
     * @return the execute_end_time
     */
    public String getExecute_end_time() {
        return execute_end_time;
    }

    /**
     * @param execute_end_time the execute_end_time to set
     */
    public void setExecute_end_time(String execute_end_time) {
        this.execute_end_time = execute_end_time;
    }

    /**
     * @return the execute_sec
     */
    public Number getExecute_sec() {
        return execute_sec;
    }

    /**
     * @param execute_sec the execute_sec to set
     */
    public void setExecute_sec(Number execute_sec) {
        this.execute_sec = execute_sec;
    }

    /**
     * @return the execute_start_time
     */
    public String getExecute_start_time() {
        return execute_start_time;
    }

    /**
     * @param execute_start_time the execute_start_time to set
     */
    public void setExecute_start_time(String execute_start_time) {
        this.execute_start_time = execute_start_time;
    }

    /**
     * @return the import_key
     */
    public String getImport_key() {
        return import_key;
    }

    /**
     * @param import_key the import_key to set
     */
    public void setImport_key(String import_key) {
        this.import_key = import_key;
    }

    /**
     * @return the import_type
     */
    public String getImport_type() {
        return import_type;
    }

    /**
     * @param import_type the import_type to set
     */
    public void setImport_type(String import_type) {
        this.import_type = import_type;
    }

    /**
     * @return the notexists_record
     */
    public Integer getNotexists_record() {
        return notexists_record;
    }

    /**
     * @param notexists_record the notexists_record to set
     */
    public void setNotexists_record(Integer notexists_record) {
        this.notexists_record = notexists_record;
    }

    /**
     * @return the prepare_end_time
     */
    public String getPrepare_end_time() {
        return prepare_end_time;
    }

    /**
     * @param prepare_end_time the prepare_end_time to set
     */
    public void setPrepare_end_time(String prepare_end_time) {
        this.prepare_end_time = prepare_end_time;
    }

    /**
     * @return the prepare_sec
     */
    public Number getPrepare_sec() {
        return prepare_sec;
    }

    /**
     * @param prepare_sec the prepare_sec to set
     */
    public void setPrepare_sec(Number prepare_sec) {
        this.prepare_sec = prepare_sec;
    }

    /**
     * @return the prepare_start_time
     */
    public String getPrepare_start_time() {
        return prepare_start_time;
    }

    /**
     * @param prepare_start_time the prepare_start_time to set
     */
    public void setPrepare_start_time(String prepare_start_time) {
        this.prepare_start_time = prepare_start_time;
    }

    /**
     * @return the total_record
     */
    public Integer getTotal_record() {
        return total_record;
    }

    /**
     * @param total_record the total_record to set
     */
    public void setTotal_record(Integer total_record) {
        this.total_record = total_record;
    }

    /**
     * @return the update_record
     */
    public Integer getupdate_record() {
        return update_record;
    }

    /**
     * @param update_record the update_record to set
     */
    public void setUpdate_record(Integer update_record) {
        this.update_record = update_record;
    }

    /**
     * @return the updatetime
     */
    public String getUpdatetime() {
        return updatetime;
    }

    /**
     * @param updatetime the updatetime to set
     */
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * @return the upload_sec
     */
    public Number getUpload_sec() {
        return upload_sec;
    }

    /**
     * @param upload_sec the upload_sec to set
     */
    public void setUpload_sec(Number upload_sec) {
        this.upload_sec = upload_sec;
    }

    /**
     * @return the upload_start_time
     */
    public String getUpload_start_time() {
        return upload_start_time;
    }

    /**
     * @param upload_start_time the upload_start_time to set
     */
    public void setUpload_start_time(String upload_start_time) {
        this.upload_start_time = upload_start_time;
    }

}
