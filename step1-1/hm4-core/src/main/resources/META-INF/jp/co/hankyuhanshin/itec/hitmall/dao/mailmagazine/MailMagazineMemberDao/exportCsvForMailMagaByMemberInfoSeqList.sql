SELECT
    memberInfo.memberInfoMail AS mail
FROM
    (SELECT * FROM memberInfo WHERE memberInfoSeq IN /*memberInfoSeqList*/(1,2,3)) AS memberInfo

ORDER BY
    memberInfo.memberInfoMail
