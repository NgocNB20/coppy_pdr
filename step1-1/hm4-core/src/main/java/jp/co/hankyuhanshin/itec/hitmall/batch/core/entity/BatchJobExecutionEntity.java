/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

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
@Table(name = "batch_job_execution")
@Data
@Component
@Scope("prototype")
public class BatchJobExecutionEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "job_execution_id")
    @Id
    private Long jobExecutionId;

    @Column(name = "version")
    private Long version;

    @Column(name = "job_instance_id")
    private Long jobInstanceId;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "status")
    private String status;

    @Column(name = "exit_code")
    private String exitCode;

    @Column(name = "exit_message")
    private String exitMessage;

    @Column(name = "last_updated")
    private Timestamp lastUpdated;

    @Column(name = "job_configuration_location")
    private String jobConfigurationLocation;

}
