/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.core.dao;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.entity.BatchJobExecutionEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.sql.Timestamp;
import java.util.List;

@Dao
@ConfigAutowireable
public interface BatchJobExecutionDao {

    /**
     * インサート
     *
     * @param batchJobExecutionEntity batchJobExecutionEntity
     * @return 登録した数
     */
    @Insert(excludeNull = true)
    int insert(BatchJobExecutionEntity batchJobExecutionEntity);

    /**
     * アップデート
     *
     * @param batchJobExecutionEntity batchJobExecutionEntity
     * @return 更新した数
     */
    @Update
    int update(BatchJobExecutionEntity batchJobExecutionEntity);

    /**
     * デリート
     *
     * @param batchJobExecutionEntity batchJobExecutionEntity
     * @return 削除した数
     */
    @Delete
    int delete(BatchJobExecutionEntity batchJobExecutionEntity);

    /**
     * エンティティ取得
     *
     * @param jobExecutionId jobExecutionId
     * @return BatchJobExecutionEntity
     */
    @Select
    BatchJobExecutionEntity getEntity(Long jobExecutionId);

    /**
     * エンティティ取得
     *
     * @param searchDto     BatchJobExecutionSearchDto
     * @param selectOptions 検索オプション
     * @return BatchManagementDetailDtoList
     */
    @Select
    List<BatchManagementDetailDto> getListDetail(BatchManagementSearchConditionDto searchDto,
                                                 SelectOptions selectOptions);

    // PDR Migrate Customization from here

    /**
     * アラート対象のバッチタスク一覧を返す。
     *
     * @param progressTime アラート対象経過時間（分）
     * @param currentTime  処理実施日時
     * @return 該当するタスク一覧
     */
    @Select
    List<BatchManagementDetailDto> getAlertBatchTaskList(final Integer progressTime, final Timestamp currentTime);
    // PDR Migrate Customization to here
}
