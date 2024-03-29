package jp.co.hankyuhanshin.itec.hitmall.dao.batch;

import jp.co.hankyuhanshin.itec.hitmall.entity.batch.BatchJobEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Dao
@ConfigAutowireable
public interface BatchJobDao {

    @Insert(excludeNull = true)
    int insert(BatchJobEntity batchJobEntity);

    @Update
    int update(BatchJobEntity batchJobEntity);

    @Delete
    int delete(BatchJobEntity batchJobEntity);

    @Select
    List<BatchJobEntity> getEntityList(Integer requestCode);

    @Select
    Stream<BatchJobEntity> getListMailDto(Integer requestCode);

    @Select
    int getRequestSeqNextVal();

    @Select
    List<String> getTargetFileListForDelete(Date expiredDateTime);

    @Delete(sqlFile = true)
    int deleteJobExecution(Date expiredDateTime);

    @Delete(sqlFile = true)
    int deleteJobExecutionContext(Date expiredDateTime);

    @Delete(sqlFile = true)
    int deleteJobExecutionParams(Date expiredDateTime);

    @Delete(sqlFile = true)
    int deleteJobExecutionParamsExtension(Date expiredDateTime);

    @Delete(sqlFile = true)
    int deleteJobInstance();

    @Delete(sqlFile = true)
    int deleteStepExecution(Date expiredDateTime);

    @Delete(sqlFile = true)
    int deleteStepExecutionContext(Date expiredDateTime);

}
