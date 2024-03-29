/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hmbase.application.AppLevelFacesMessage;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

/**
 * CheckMessageDto生成Utility
 *
 * @author Faizan Momin
 */
@Component
public class CheckMessageUtility {

    /**
     * メッセージ取得
     *
     * @param messageId メッセージID
     * @return チェックメッセージDto
     */
    public CheckMessageDto createCheckMessageDto(String messageId) {
        return createCheckMessageDto(messageId, true, (Object[]) null);
    }

    /**
     * メッセージ取得
     *
     * @param messageId メッセージID
     * @param args      メッセージ引数
     * @return チェックメッセージDto
     */
    public CheckMessageDto createCheckMessageDto(String messageId, Object... args) {
        return createCheckMessageDto(messageId, true, args);
    }

    /**
     * メッセージ取得
     *
     * @param messageId メッセージID
     * @param error     エラーフラグ
     * @return チェックメッセージDto
     */
    public CheckMessageDto createCheckMessageDto(String messageId, boolean error) {
        return createCheckMessageDto(messageId, error, (Object[]) null);
    }

    /**
     * メッセージ取得
     *
     * @param messageId メッセージID
     * @param error     エラーフラグ
     * @param args      メッセージ引数
     * @return チェックメッセージDto
     */
    public CheckMessageDto createCheckMessageDto(String messageId, boolean error, Object... args) {
        CheckMessageDto checkMessageDto = ApplicationContextUtility.getBean(CheckMessageDto.class);
        checkMessageDto.setMessageId(messageId);
        String message = getMessage(messageId, args);
        checkMessageDto.setMessage(message);
        checkMessageDto.setArgs(args);
        checkMessageDto.setError(error);
        return checkMessageDto;
    }

    /**
     * メッセージ取得
     *
     * @param messageId メッセージID
     * @param args      メッセージ引数
     * @return メッセージ
     */
    public String getMessage(String messageId, Object... args) {
        AppLevelFacesMessage facesMessage = AppLevelFacesMessageUtil.getAllMessage(messageId, args);
        return facesMessage.getMessage();
    }

}
