SELECT
    memberInfo.*
FROM
    memberInfo
WHERE
    memberInfo.memberinfoseq in /*memberInfoSeqList*/(1,2,3)
