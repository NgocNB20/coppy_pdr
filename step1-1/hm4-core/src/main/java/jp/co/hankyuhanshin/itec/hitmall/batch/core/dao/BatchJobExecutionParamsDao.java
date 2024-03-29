package jp.co.hankyuhanshin.itec.hitmall.batch.core.dao;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.entity.BatchJobExecutionParamsEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface BatchJobExecutionParamsDao {

    /**
     * インサート
     *
     * @param batchJobExecutionParamsEntity batchJobExecutionParamsEntity
     * @return 登録した数
     */
    @Insert(excludeNull = true)
    int insert(BatchJobExecutionParamsEntity batchJobExecutionParamsEntity);

    /**
     * アップデート
     *
     * @param batchJobExecutionParamsEntity batchJobExecutionParamsEntity
     * @return 更新した数
     */
    @Update
    int update(BatchJobExecutionParamsEntity batchJobExecutionParamsEntity);

    /**
     * デリート
     *
     * @param batchJobExecutionParamsEntity batchJobExecutionParamsEntity
     * @return 削除した数
     */
    @Delete
    int delete(BatchJobExecutionParamsEntity batchJobExecutionParamsEntity);

    /**
     * エンティティ取得
     *
     * @param jobExecutionId jobExecutionId
     * @return BatchJobExecutionParamsEntity
     */
    @Select
    BatchJobExecutionParamsEntity getEntity(Long jobExecutionId);
}
