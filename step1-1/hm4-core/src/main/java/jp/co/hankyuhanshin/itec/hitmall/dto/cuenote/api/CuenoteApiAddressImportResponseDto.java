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
 * アドレス帳インポートAPIレスポンスDTO
 *
 * @author st75001
 */
@Data
@Component
@Scope("prototype")
public class CuenoteApiAddressImportResponseDto implements Serializable {

    /** serialVersionUID */
    public static final long serialVersionUID = 1L;

    /** アドレス帳 ID */
    @JSONHint(name = "adbook")
    public String adBook;

    /** 追加されたレコード数（deleteモード時はnull） */
    @JSONHint(name = "add_record")
    public Integer addRecord;

    /** インポート処理情報の作成時刻 */
    @JSONHint(name = "createtime")
    public String createTime;

    /** 削除されたレコード数（insert/replaceモード時はnull） */
    @JSONHint(name = "delete_record")
    public Integer deleteRecord;

    /** 形式エラーで追加されなかったレコード数（deleteモード時はnull） */
    @JSONHint(name = "error_record")
    public Integer errorRecord;

    /** インポート反映処理の終了時刻 */
    @JSONHint(name = "execute_end_time")
    public String executeEndTime;

    /** インポート反映処理にかかった時間（秒） */
    @JSONHint(name = "execute_sec")
    public Number executeSec;

    /** インポート反映処理の開始時刻 */
    @JSONHint(name = "execute_start_time")
    public String executeStartTime;

    /** インポート処理ID */
    @JSONHint(name = "import_key")
    public String importKey;

    /** インポートの方式（X-Modeの値） */
    @JSONHint(name = "import_type")
    public String importType;

    /** 存在せず削除できなかったレコード数（insert/replaceモード時はnull） */
    @JSONHint(name = "notexists_record")
    public Integer notexistsRecord;

    /** インポート準備処理の終了時刻 */
    @JSONHint(name = "prepare_end_time")
    public String prepareEndTime;

    /** インポート準備処理にかかった時間（秒） */
    @JSONHint(name = "prepare_sec")
    public Number prepareSec;

    /** インポート準備処理の開始時刻 */
    @JSONHint(name = "prepare_start_time")
    public String prepareStartTime;

    /** インポートデータの全レコード数 */
    @JSONHint(name = "total_record")
    public Integer totalRecord;

    /** 更新されたレコード数 */
    @JSONHint(name = "update_record")
    public Integer updateRecord;

    /** インポート処理情報の更新時刻 */
    @JSONHint(name = "updatetime")
    public String updateTime;

    /** ファイル処理にかかった時間（秒） */
    @JSONHint(name = "upload_sec")
    public Number uploadSec;

    /** ファイル処理の開始時刻 */
    @JSONHint(name = "upload_start_time")
    public String uploadStartTime;
}