package jp.co.hankyuhanshin.itec.hitmall.entity.batch;

import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Entity
@Table(name = "batch_job_execution_params_extension")
@Data
@Component
@Scope("prototype")
public class BatchJobEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * batchSeq（必須）
     */
    @Column(name = "batchSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "batchSeq")
    private Integer batchSeq;

    /**
     * requestCode（必須）
     */
    @Column(name = "requestCode")
    private Integer requestCode;

    /**
     * requestNo（必須）
     */
    @Column(name = "requestNo")
    private Integer requestNo;

    /**
     * requestData（必須）
     */
    @Column(name = "requestData")
    private String requestData;

    /**
     * 引数無しコンストラクタが無いと、Domaのコンパイルエラー発生
     */
    public BatchJobEntity() {
    }

    /**
     * コンストラクタ
     */
    public BatchJobEntity(Integer requestCode, Integer requestNo) {
        this.requestCode = requestCode;
        this.requestNo = requestNo;
    }
}
