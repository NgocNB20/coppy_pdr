-- PDR Migrate Customization from here
SELECT
  *
FROM
  loginadvisability
WHERE
  loginadvisability.memberinfostatus = /*memberInfoStatus*/0
  AND loginadvisability.approvestatus = /*approveStatus*/0
  AND loginadvisability.onlineloginadvisability = /*onlineLoginAdvisability*/0
  AND loginadvisability.memberlisttype = /*memberListType*/0
  AND loginadvisability.accountingtype = /*accountingType*/0
-- PDR Migrate Customization to here
