package jp.co.hankyuhanshin.itec.hitmall.batch.common;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * バッチ終了メッセージ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class BatchExitMessageDto {
    // 処理済件数
    private String processedRecord;

    // レポート内容
    private String report;

    // コンストラクタ（no param）
    public BatchExitMessageDto() {
    }

    // コンストラクタ（all params）
    public BatchExitMessageDto(String processedRecord, String report) {
        this.processedRecord = processedRecord;
        this.report = report;
    }
}
