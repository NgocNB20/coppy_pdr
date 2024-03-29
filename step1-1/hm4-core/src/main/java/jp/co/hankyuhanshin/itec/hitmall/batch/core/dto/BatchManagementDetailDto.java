package jp.co.hankyuhanshin.itec.hitmall.batch.core.dto;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.entity.BatchJobExecutionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Component
@Scope("prototype")
public class BatchManagementDetailDto extends BatchJobExecutionEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * バッチジョッブId
     */
    private String batchName;
}
