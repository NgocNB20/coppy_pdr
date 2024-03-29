-- PDR Migrate Customization from here
SELECT
    memberInfo.*
FROM
    memberInfo
WHERE
    memberInfo.memberInfoTel = /*memberInfoTel*/0
AND
    memberInfo.customerNo = /*customerNo*/0
-- PDR Migrate Customization to here
