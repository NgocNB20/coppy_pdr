SELECT
    customerNo
FROM
    memberInfo
WHERE
    customerNo is not null
AND customerNo IN /*customerNoList*/(0)
AND sendMailPermitFlag <> '1'
;