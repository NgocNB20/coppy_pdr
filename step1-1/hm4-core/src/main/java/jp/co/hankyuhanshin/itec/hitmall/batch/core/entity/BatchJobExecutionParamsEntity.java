package jp.co.hankyuhanshin.itec.hitmall.batch.core.entity;

import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "batch_job_execution_params")
@Data
@Component
@Scope("prototype")
public class BatchJobExecutionParamsEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "job_execution_id")
    private Long jobExecutionId;

    @Column(name = "type_cd")
    private String typeCd;

    @Column(name = "key_name")
    private String keyName;

    @Column(name = "string_val")
    private String stringVal;

    @Column(name = "date_val")
    private Date dateVal;

    @Column(name = "long_val")
    private Long longVal;

    @Column(name = "double_val")
    private Double doubleVal;

    @Column(name = "identifying")
    private String identifying;

}
