package jp.co.hankyuhanshin.itec.hitmall.batch.core.logic;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementSearchConditionDto;

import java.util.List;

public interface BatchManagementGetListDetailLogic {
    List<BatchManagementDetailDto> execute(BatchManagementSearchConditionDto searchDto);
}
