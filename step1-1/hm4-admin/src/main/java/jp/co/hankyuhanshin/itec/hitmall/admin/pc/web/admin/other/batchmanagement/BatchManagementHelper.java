package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.other.batchmanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchDerive;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchStatus;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BatchManagementHelper {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchManagementHelper.class);

    public BatchManagementSearchConditionDto convertToBatchManagementSearchConditionDto(BatchManagementModel model) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        BatchManagementSearchConditionDto searchConditionDto =
                        ApplicationContextUtility.getBean(BatchManagementSearchConditionDto.class);

        if (!StringUtils.isEmpty(model.getBatchtypes())) {
            searchConditionDto.setBatchName(model.getBatchtypes());
        }

        if (!StringUtils.isEmpty(model.getTaskstatuses())) {
            searchConditionDto.setStatus(model.getTaskstatuses());
        }

        if (!StringUtils.isEmpty(model.getAccepttimeFrom())) {
            searchConditionDto.setCreateTime(conversionUtility.toTimeStamp(model.getAccepttimeFrom()));
        }

        if (!StringUtils.isEmpty(model.getAccepttimeTo())) {
            searchConditionDto.setEndTime(conversionUtility.toTimeStamp(model.getAccepttimeTo()));
        }

        return searchConditionDto;
    }

    public BatchManagementReportItem convertToBatchManagementReportItem(BatchManagementDetailDto detailDto) {

        BatchManagementReportItem reportModel = new BatchManagementReportItem();
        reportModel.setTaskid(Math.toIntExact(detailDto.getJobExecutionId()));

        if (detailDto.getBatchName().equals(HTypeBatchName.BATCH_GOODS_ASYNCHRONOUS.getValue())
            || detailDto.getBatchName().equals(HTypeBatchName.BATCH_ORDER_CSV_ASYNCHRONOUS.getValue())
            || detailDto.getBatchName().equals(HTypeBatchName.BATCH_MAIL.getValue()) || detailDto.getBatchName()
                                                                                                 .equals(HTypeBatchName.BATCH_GOODSIMAGE_UPDATE
                                                                                                                         .getValue())
            // 2023-renew No42 from here
            || detailDto.getBatchName().equals(HTypeBatchName.BATCH_DIGITALCATALOG_IMPORT.getValue())) {
            // 2023-renew No42 to here
            reportModel.setBatchderive(HTypeBatchDerive.ONLINE);
        } else {
            reportModel.setBatchderive(HTypeBatchDerive.OFFLINE);
        }

        if (detailDto.getBatchName() != null
            && EnumTypeUtil.getEnumFromValue(HTypeBatchName.class, detailDto.getBatchName()) != null) {
            reportModel.setBatchname(
                            EnumTypeUtil.getEnumFromValue(HTypeBatchName.class, detailDto.getBatchName()).getLabel());
        }

        reportModel.setAccepttime(detailDto.getCreateTime());

        reportModel.setTaskstatus(EnumTypeUtil.getEnumFromValue(HTypeBatchStatus.class, detailDto.getStatus()));

        reportModel.setTerminatetime(detailDto.getEndTime());

        BatchExitMessageUtil batchExitMessageUtil = new BatchExitMessageUtil();

        try {
            BatchExitMessageDto exitMessageDto = batchExitMessageUtil.convertJsonToObject(detailDto.getExitMessage());
            reportModel.setProcessedrecord(exitMessageDto.getProcessedRecord());
            reportModel.setReportstring(exitMessageDto.getReport());
        } catch (JsonProcessingException e) {
            reportModel.setProcessedrecord(null);
            reportModel.setReportstring(null);
        }

        if (detailDto.getStatus().equals(HTypeBatchStatus.COMPLETED.getValue())) {
            reportModel.setEndMarkDisplay(false);
        } else {
            reportModel.setEndMarkDisplay(true);
        }

        return reportModel;
    }

    public BatchManagementReportItem getBatchManagementReportItem(BatchManagementModel batchManagementModel) {
        for (BatchManagementReportItem model : batchManagementModel.getResultItems()) {
            if (model.getTaskid().equals(batchManagementModel.getReportTaskId())) {
                return model;
            }
        }
        return new BatchManagementReportItem();
    }
}
