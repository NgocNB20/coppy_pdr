-- PDR Migrate Customization from here
SELECT
    memberInfo.*
FROM
    memberInfo
WHERE
    memberInfo.memberInfoTel = /*memberInfoTel*/0
LIMIT 1
-- PDR Migrate Customization to here