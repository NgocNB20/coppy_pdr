package jp.co.hankyuhanshin.itec.hitmall.batch.core.logic.impl;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.dao.BatchJobExecutionDao;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.logic.BatchManagementGetListDetailLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchManagementGetListDetailLogicImpl implements BatchManagementGetListDetailLogic {

    private final BatchJobExecutionDao batchJobExecutionDao;

    @Autowired
    public BatchManagementGetListDetailLogicImpl(BatchJobExecutionDao batchJobExecutionDao) {
        this.batchJobExecutionDao = batchJobExecutionDao;
    }

    @Override
    public List<BatchManagementDetailDto> execute(BatchManagementSearchConditionDto searchDto) {
        return batchJobExecutionDao.getListDetail(searchDto, searchDto.getPageInfo().getSelectOptions());
    }
}
