package jp.co.hankyuhanshin.itec.hitmall.batch.core.dao;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.entity.BatchJobInstanceEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface BatchJobInstanceDao {

    /**
     * インサート
     *
     * @param batchJobInstanceEntity batchJobInstanceEntity
     * @return 登録した数
     */
    @Insert(excludeNull = true)
    int insert(BatchJobInstanceEntity batchJobInstanceEntity);

    /**
     * アップデート
     *
     * @param batchJobInstanceEntity batchJobInstanceEntity
     * @return 更新した数
     */
    @Update
    int update(BatchJobInstanceEntity batchJobInstanceEntity);

    /**
     * デリート
     *
     * @param batchJobInstanceEntity batchJobInstanceEntity
     * @return 削除した数
     */
    @Delete
    int delete(BatchJobInstanceEntity batchJobInstanceEntity);

    /**
     * エンティティ取得
     *
     * @param jobInstanceId jobInstanceId
     * @return BatchJobInstanceEntity
     */
    @Select
    BatchJobInstanceEntity getEntity(Integer jobInstanceId);

}
