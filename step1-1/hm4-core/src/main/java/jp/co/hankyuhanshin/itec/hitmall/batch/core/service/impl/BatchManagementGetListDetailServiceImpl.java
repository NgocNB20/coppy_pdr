package jp.co.hankyuhanshin.itec.hitmall.batch.core.service.impl;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.logic.BatchManagementGetListDetailLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.service.BatchManagementGetListDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchManagementGetListDetailServiceImpl implements BatchManagementGetListDetailService {
    private final BatchManagementGetListDetailLogic batchManagementGetListDetailLogic;

    @Autowired
    public BatchManagementGetListDetailServiceImpl(BatchManagementGetListDetailLogic batchManagementGetListDetailLogic) {
        this.batchManagementGetListDetailLogic = batchManagementGetListDetailLogic;
    }

    @Override
    public List<BatchManagementDetailDto> execute(BatchManagementSearchConditionDto conditionDto) {
        return batchManagementGetListDetailLogic.execute(conditionDto);
    }
}
