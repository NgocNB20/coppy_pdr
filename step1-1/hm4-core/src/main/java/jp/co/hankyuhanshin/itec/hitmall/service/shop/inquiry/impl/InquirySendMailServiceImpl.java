/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.InquiryTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquirySendMailService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 問い合わせメール送信サービス<br/>
 *
 * @author Doan Thang (VTI Japan Co. Ltd)
 */
@Service
public class InquirySendMailServiceImpl extends AbstractShopService implements InquirySendMailService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InquirySendMailServiceImpl.class);

    /**
     * 問い合わせ情報取得サービス<br/>
     */
    private final InquiryGetService inquiryGetService;

    /**
     * 問い合わせ取得ロジック
     */
    private final InquiryGetLogic inquiryGetLogic;

    /**
     * メールテンプレート取得ロジック<br/>
     */
    private final MailTemplateGetLogic mailTemplateGetLogic;

    /**
     * メール送信サービス（同期送信）
     */
    private final MailSendService mailSendService;

    /**
     * メールUtility取得
     */
    private final MailUtility mailUtility;

    @Autowired
    public InquirySendMailServiceImpl(InquiryGetService inquiryGetService,
                                      InquiryGetLogic inquiryGetLogic,
                                      MailTemplateGetLogic mailTemplateGetLogic,
                                      MailSendService mailSendService,
                                      MailUtility mailUtility) {

        this.inquiryGetService = inquiryGetService;
        this.inquiryGetLogic = inquiryGetLogic;
        this.mailTemplateGetLogic = mailTemplateGetLogic;
        this.mailSendService = mailSendService;
        this.mailUtility = mailUtility;
    }

    /**
     * 問い合わせメール送信処理<br/>
     *
     * @param inquirySeq       問い合わせSEQ
     * @param mailTemplateType メールテンプレート(問い合わせ受付/問い合わせ回答)
     * @return メール送信結果
     */
    @Override
    public boolean execute(Integer inquirySeq, HTypeMailTemplateType mailTemplateType) {

        // ショップSEQ
        Integer shopSeq = 1001;

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("inquirySeq", inquirySeq);

        // 問い合わせ情報取得
        InquiryDetailsDto inquiryDetailsDto = inquiryGetService.execute(inquirySeq);

        // メール送信
        return sendMail(shopSeq, mailTemplateType, inquiryDetailsDto);
    }

    /**
     * 問い合わせメール送信処理<br/>
     *
     * @param inquiryCode      問い合わせSEQ
     * @param mailTemplateType メールテンプレート(問い合わせ受付/問い合わせ回答)
     * @return メール送信結果
     */
    @Override
    public boolean execute(String inquiryCode, HTypeMailTemplateType mailTemplateType) {

        // ショップSEQ
        Integer shopSeq = 1001;

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("inquiryCode", inquiryCode);

        // 問い合わせ情報取得
        InquiryDetailsDto inquiryDetailsDto = inquiryGetLogic.execute(inquiryCode);

        if (inquiryDetailsDto == null || inquiryDetailsDto.getInquiryEntity() == null) {
            throwMessage(MSGCD_INQUIRY_NULL);
        }

        return sendMail(shopSeq, mailTemplateType, inquiryDetailsDto);
    }

    /**
     * @param shopSeq           ショップSeq
     * @param mailTemplateType  メールテンプレートタイプ
     * @param inquiryDetailsDto 問い合わせ詳細Dto
     * @return true: 成功/ false: 失敗
     */
    private boolean sendMail(int shopSeq, HTypeMailTemplateType mailTemplateType, InquiryDetailsDto inquiryDetailsDto) {
        // 送信に使用するメールテンプレートを取得する
        MailTemplateEntity entity = this.mailTemplateGetLogic.execute(shopSeq, mailTemplateType);

        // テンプレートがない場合
        if (entity == null) {
            return false;
        }

        // 送信先取得
        List<String> toList = Collections.singletonList(inquiryDetailsDto.getInquiryEntity().getInquiryMail());

        // メール用値マップの作成
        Transformer transformer = ApplicationContextUtility.getBean(InquiryTransformHelper.class);
        Map<String, String> mailContentsMap = transformer.toValueMap(inquiryDetailsDto);

        // メールDto作成
        MailDto mailDto = mailUtility.createMailDto(mailTemplateType, entity, toList, mailContentsMap);

        // メール送信
        try {
            mailSendService.execute(mailDto);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            return false;
        }

        return true;
    }
}
