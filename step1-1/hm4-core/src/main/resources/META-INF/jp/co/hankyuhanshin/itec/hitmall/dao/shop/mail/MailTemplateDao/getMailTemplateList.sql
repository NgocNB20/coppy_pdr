SELECT *
FROM
    mailtemplate
WHERE
    shopseq = /*shopSeq*/0
AND
    mailtemplatetype = /*mailTemplateType.value*/0
    /*%if mailTemplateDefaultFlag != null*/
        AND mailTemplateDefaultFlag = /*mailTemplateDefaultFlag.value*/0
    /*%end*/
ORDER BY
    mailtemplateseq
