SELECT
    memberInfo.customerNo
FROM
    memberInfo
LEFT JOIN
    loginAdvisability
ON
    loginAdvisability.memberInfoStatus = memberInfo.memberInfoStatus
AND loginAdvisability.approveStatus = memberInfo.approveStatus
AND loginAdvisability.onlineLoginAdvisability = memberInfo.onlineLoginAdvisability
AND loginAdvisability.memberListType = memberInfo.memberListType
AND loginAdvisability.accountingType = memberInfo.accountingType
WHERE
    memberInfo.customerNo IS NOT NULL
AND memberInfo.customerNo IN /*customerNoList*/(0)
AND (loginAdvisability.LoginAdvisabilitytype IS NULL
OR  loginAdvisability.LoginAdvisabilitytype = '0')
;