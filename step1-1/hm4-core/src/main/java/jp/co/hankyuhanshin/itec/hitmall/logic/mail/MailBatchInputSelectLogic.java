package jp.co.hankyuhanshin.itec.hitmall.logic.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.co.hankyuhanshin.itec.hitmall.entity.batch.BatchJobEntity;

import java.util.stream.Stream;

/**
 * メール送信入力情報取得<br/>
 *
 * @author Doan Thang
 */
public interface MailBatchInputSelectLogic {

    /**
     * メール送信入力情報取得<br/>
     *
     * @param requestCode 要求コード
     * @return メールDTOリスト
     * @throws JsonProcessingException JsonProcessingException
     */
    Stream<BatchJobEntity> execute(Integer requestCode) throws JsonProcessingException;
}
