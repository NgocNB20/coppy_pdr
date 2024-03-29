package jp.co.hankyuhanshin.itec.hitmall.logic.mail.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.co.hankyuhanshin.itec.hitmall.dao.batch.BatchJobDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.batch.BatchJobEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.mail.MailBatchInputSelectLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * メール送信入力情報取得<br/>
 *
 * @author Doan Thang
 */
@Component
public class MailBatchInputSelectLogicImpl implements MailBatchInputSelectLogic {

    private final BatchJobDao batchJobDao;

    @Autowired
    public MailBatchInputSelectLogicImpl(BatchJobDao batchJobDao) {
        this.batchJobDao = batchJobDao;
    }

    /**
     * メール送信入力情報取得<br/>
     *
     * @param requestCode 要求SEQ
     * @return メールDTOリスト
     * @throws JsonProcessingException JsonProcessingException
     */
    @Override
    public Stream<BatchJobEntity> execute(Integer requestCode) throws JsonProcessingException {
        AssertionUtil.assertNotNull("requestCode", requestCode);
        return batchJobDao.getListMailDto(requestCode);
    }
}
