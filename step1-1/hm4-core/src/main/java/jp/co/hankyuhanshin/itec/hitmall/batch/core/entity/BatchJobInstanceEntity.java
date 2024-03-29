package jp.co.hankyuhanshin.itec.hitmall.batch.core.entity;

import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Entity
@Table(name = "batch_job_instance")
@Data
@Component
@Scope("prototype")
public class BatchJobInstanceEntity implements Serializable {

    @Column(name = "job_instance_id")
    @Id
    private Integer jobInstanceId;

    @Column(name = "version")
    private Integer version;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_key")
    private String jobKey;
}
