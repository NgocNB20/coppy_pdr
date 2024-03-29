-- PDR Migrate Customization from here
SELECT
    memberInfo.*
FROM
    memberInfo
WHERE
	memberInfo.customerNo = /*customerNo*/0
-- PDR Migrate Customization to here