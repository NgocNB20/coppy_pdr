UPDATE
    MailTemplate
SET
    mailTemplateDefaultFlag = '0'
WHERE
        shopSeq = /*shopSeq*/0
    and mailTemplateType = /*mailTemplateType.value*/0
    and mailTemplateSeq != /*mailTemplateSeq*/0
