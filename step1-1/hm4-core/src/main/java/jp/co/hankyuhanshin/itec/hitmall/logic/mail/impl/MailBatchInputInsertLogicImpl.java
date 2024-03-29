package jp.co.hankyuhanshin.itec.hitmall.logic.mail.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jp.co.hankyuhanshin.itec.hitmall.dao.batch.BatchJobDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.batch.BatchJobEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.mail.MailBatchInputInsertLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * メール送信入力情報登録<br/>
 *
 * @author Doan Thang
 */
@Component
public class MailBatchInputInsertLogicImpl implements MailBatchInputInsertLogic {

    private final BatchJobDao batchJobDao;

    @Autowired
    public MailBatchInputInsertLogicImpl(BatchJobDao batchJobDao) {
        this.batchJobDao = batchJobDao;
    }

    /**
     * メール送信入力情報登録<br/>
     *
     * @param mailDtoList メールDtoリスト
     * @return メール要求コード
     * @throws JsonProcessingException JsonProcessingException
     */
    @Override
    public Integer execute(List<MailDto> mailDtoList) throws JsonProcessingException {
        int requestCode = batchJobDao.getRequestSeqNextVal();
        int requestNo = 0;
        int count = 0;

        for (MailDto mailDto : mailDtoList) {
            BatchJobEntity batchJobEntity = new BatchJobEntity(requestCode, requestNo);
            batchJobEntity.setRequestData(convertMailDtoToJson(mailDto));
            count += batchJobDao.insert(batchJobEntity);
            requestNo++;
        }

        if (count > 0) {
            return requestCode;
        } else {
            return null;
        }
    }

    /**
     * MailDtoからJsonに変換<br/>
     *
     * @param mailDto mailDto
     * @return String
     * @throws JsonProcessingException JsonProcessingException
     */
    private String convertMailDtoToJson(MailDto mailDto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(mailDto);
    }
}
