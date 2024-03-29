package jp.co.hankyuhanshin.itec.hitmall.batch.core.dao;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.entity.BatchStepExecutionEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface BatchStepExecutionDao {

    /**
     * インサート
     *
     * @param batchStepExecutionEntity batchStepExecutionEntity
     * @return 登録した数
     */
    @Insert(excludeNull = true)
    int insert(BatchStepExecutionEntity batchStepExecutionEntity);

    /**
     * アップデート
     *
     * @param batchStepExecutionEntity batchStepExecutionEntity
     * @return 更新した数
     */
    @Update
    int update(BatchStepExecutionEntity batchStepExecutionEntity);

    /**
     * デリート
     *
     * @param batchStepExecutionEntity batchStepExecutionEntity
     * @return 削除した数
     */
    @Delete
    int delete(BatchStepExecutionEntity batchStepExecutionEntity);

    /**
     * エンティティ取得
     *
     * @param stepExecutionId stepExecutionId
     * @return BatchStepExecutionEntity
     */
    @Select
    BatchStepExecutionEntity getEntity(long stepExecutionId);
}
