/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.BatchName;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentCompleteMailBatchRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentListRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentRegistService;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 出荷リスト登録サービス<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.5 $
 */
@Service
public class ShipmentListRegistServiceImpl extends AbstractShipmentRegistService implements ShipmentListRegistService {

    @Autowired
    public ShipmentListRegistServiceImpl(ShipmentRegistService shipmentRegistService,
                                         ShipmentCompleteMailBatchRegistService mailBatchRegistService) {

        super(shipmentRegistService, mailBatchRegistService);
    }

    /**
     * 実行メソッド<br/>
     *
     * @param shipmentRegistDtoList 出荷登録DTOリスト
     * @return 処理結果メッセージリスト
     */
    @Override
    public List<CheckMessageDto> execute(List<ShipmentRegistDto> shipmentRegistDtoList, String administratorName) {
        List<CheckMessageDto> result = new ArrayList<>();
        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();

        if (shipmentRegistDtoList == null) {
            throwMessage(MSGCD_ARGS_NULL);
        }

        // 出荷完了保持リスト（出荷件数表示用）
        List<ShipmentRegistDto> successList = new ArrayList<>();
        // 出荷完了保持リスト（出荷完了メール送信用）
        List<ShipmentRegistDto> mailSendList = new ArrayList<>();

        for (ShipmentRegistDto shipmentRegistDto : shipmentRegistDtoList) {

            // 出荷登録処理実行
            int i = registor(shipmentRegistDto, checkMessageDtoList, MSGCD_SHIPMENTREGIST_FATAL, administratorName);

            if (i > 0) {
                successList.add(shipmentRegistDto);

                // 出荷対象の商品数量が0の場合は出荷完了メールを送信しない
                if (shipmentRegistDto.isGoodsCountNotZeroFlag()) {
                    mailSendList.add(shipmentRegistDto);
                }
            }
        }
        if (!successList.isEmpty()) {
            CheckMessageDto checkMessageDto =
                            toCheckMessageDto(MSGCD_SHIPMENT_SUCCESS_COUNT_INFO, new Object[] {successList.size()},
                                              false
                                             );
            result.add(checkMessageDto);
        }

        try {
            mailBatchRegist(mailSendList);
        } catch (Exception e) {
            LOGGER.info("出荷完了メール送信バッチ起動失敗", e);
        }

        result.addAll(checkMessageDtoList);

        return result;
    }

    /**
     * 出荷完了メール送信バッチタスク登録サービス実行<br/>
     */
    protected void mailBatchRegist(List<ShipmentRegistDto> mailSendList)
                    throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException,
                    JobInstanceAlreadyCompleteException {
        /** 出荷完了メール送信バッチタスク登録サービス */
        ShipmentCompleteMailBatchRegistService mailBatchRegistService =
                        ApplicationContextUtility.getBean(ShipmentCompleteMailBatchRegistService.class);
        int mailRequestCode = mailBatchRegistService.execute(mailSendList);

        // 一括メール送信バッチ起動
        JobLauncher jobLauncher = ApplicationContextUtility.getApplicationContext()
                                                           .getBean("jobLauncherAsync", JobLauncher.class);
        Job job = ApplicationContextUtility.getApplicationContext().getBean(BatchName.BATCH_MAIL, Job.class);
        JobExecution jobExecution = jobLauncher.run(
                        job, new JobParametersBuilder().addString("requestCode", String.valueOf(mailRequestCode))
                                                       .addString("totalRecord", String.valueOf(mailSendList.size()))
                                                       .toJobParameters());
        if (jobExecution.getExitStatus().equals(ExitStatus.STOPPED)) {
            LOGGER.error("一括メール送信バッチ起動失敗");
        } else {
            LOGGER.info("一括メール送信バッチ起動成功");
        }
    }
}
