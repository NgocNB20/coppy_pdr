package jp.co.hankyuhanshin.itec.hitmall.batch.core.dto;

import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Component
@Scope("prototype")
public class BatchManagementSearchConditionDto extends AbstractConditionDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * バッチジョッブId
     */
    private String batchName;

    /**
     * バッチ開始時刻
     */
    private Timestamp createTime;

    /**
     * バッチ終了時刻
     */
    private Timestamp endTime;

    /**
     * バッチステータス
     */
    private String status;
}
