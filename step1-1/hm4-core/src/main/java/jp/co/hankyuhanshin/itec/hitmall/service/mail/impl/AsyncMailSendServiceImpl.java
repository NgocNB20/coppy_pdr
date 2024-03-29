package jp.co.hankyuhanshin.itec.hitmall.service.mail.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.mail.MailBatchInputInsertLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.AsyncMailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 機能概要：新メール送信サービス（非同期送信）
 * 作成日：2021/03/30
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Service
public class AsyncMailSendServiceImpl implements AsyncMailSendService {

    /**
     * メールバッチ入力情報インサートロジック
     */
    private final MailBatchInputInsertLogic mailBatchInputInsertLogic;

    @Autowired
    public AsyncMailSendServiceImpl(MailBatchInputInsertLogic mailBatchInputInsertLogic) {
        this.mailBatchInputInsertLogic = mailBatchInputInsertLogic;
    }

    /**
     * 非同期メール送信：リストで依頼する場合
     *
     * @param mailDtoList mailDtoList
     * @return メール要求コード
     */
    @Override
    public int execute(List<MailDto> mailDtoList) throws JsonProcessingException {
        return mailBatchInputInsertLogic.execute(mailDtoList);
    }

}
