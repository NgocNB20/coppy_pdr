-- PDR Migrate Customization from here
SELECT
    memberInfo.*
FROM
    memberInfo
WHERE
    (memberInfo.memberInfoId = /*memberInfoId*/0
/*%if customerNo != null*/
OR
	memberInfo.customerNo = /*customerNo*/0
/*%end*/
)
-- PDR Migrate Customization to here