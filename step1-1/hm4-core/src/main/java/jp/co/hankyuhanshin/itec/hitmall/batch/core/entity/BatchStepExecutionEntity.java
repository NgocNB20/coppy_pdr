package jp.co.hankyuhanshin.itec.hitmall.batch.core.entity;

import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "batch_step_execution")
@Data
@Component
@Scope("prototype")
public class BatchStepExecutionEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "step_execution_id")
    @Id
    private long stepExecutionId;

    @Column(name = "version")
    private long version;

    @Column(name = "step_name")
    private String stepName;

    @Column(name = "job_execution_id")
    private long jobExecutionId;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "status")
    private String status;

    @Column(name = "commit_count")
    private long commitCount;

    @Column(name = "read_count")
    private long read_count;

    @Column(name = "filter_count")
    private long filterCount;

    @Column(name = "write_count")
    private long writeCount;

    @Column(name = "read_skip_count")
    private long readSkipCount;

    @Column(name = "write_skip_count")
    private long writeSkipCount;

    @Column(name = "process_skip_count")
    private long processSkipCount;

    @Column(name = "rollback_count")
    private long rollbackCount;

    @Column(name = "exit_code")
    private String exitCode;

    @Column(name = "exit_message")
    private String exitMessage;

    @Column(name = "last_updated")
    private Timestamp lastUpdated;
}
