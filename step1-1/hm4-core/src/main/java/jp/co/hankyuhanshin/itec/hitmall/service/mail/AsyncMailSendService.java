package jp.co.hankyuhanshin.itec.hitmall.service.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;

import java.util.List;

/**
 * 機能概要：新メール送信サービス（非同期送信）
 * 作成日：2021/03/30
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
public interface AsyncMailSendService {

    /**
     * 非同期メール送信：リストで依頼する場合
     *
     * @param mailDtoList mailDtoList
     * @return メール要求コード
     */
    int execute(List<MailDto> mailDtoList) throws JsonProcessingException;

}
