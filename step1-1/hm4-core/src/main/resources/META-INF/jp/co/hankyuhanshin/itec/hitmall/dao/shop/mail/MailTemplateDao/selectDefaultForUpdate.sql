SELECT *
FROM
    MailTemplate
WHERE
        shopSeq = /*shopSeq*/0
    and MailTemplateType = /*mailTemplateType.value*/0
    and MailTemplateDefaultFlag = '1'
LIMIT 1
FOR UPDATE NOWAIT
