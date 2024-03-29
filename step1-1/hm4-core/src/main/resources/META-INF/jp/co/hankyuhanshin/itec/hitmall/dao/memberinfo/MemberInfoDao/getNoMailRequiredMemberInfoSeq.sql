SELECT
    memberInfo.memberInfoSeq
FROM
    memberInfo
WHERE
    customerNo is not null
AND memberInfoSeq IN /*memberInfoSeqList*/(0)
AND sendMailPermitFlag <> '1'
;