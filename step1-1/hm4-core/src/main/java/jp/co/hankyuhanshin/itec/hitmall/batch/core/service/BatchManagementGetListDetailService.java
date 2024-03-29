package jp.co.hankyuhanshin.itec.hitmall.batch.core.service;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementSearchConditionDto;

import java.util.List;

public interface BatchManagementGetListDetailService {
    List<BatchManagementDetailDto> execute(BatchManagementSearchConditionDto conditionDto);
}
