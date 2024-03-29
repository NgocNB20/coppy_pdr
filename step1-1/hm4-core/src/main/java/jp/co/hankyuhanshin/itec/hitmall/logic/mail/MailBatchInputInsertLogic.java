package jp.co.hankyuhanshin.itec.hitmall.logic.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;

import java.util.List;

/**
 * メール送信入力情報登録<br/>
 *
 * @author Doan Thang
 */
public interface MailBatchInputInsertLogic {

    /**
     * メール送信入力情報登録<br/>
     *
     * @param mailDtoList メールDtoリスト
     * @return メール要求コード
     * @throws JsonProcessingException JsonProcessingException
     */
    Integer execute(List<MailDto> mailDtoList) throws JsonProcessingException;
}
