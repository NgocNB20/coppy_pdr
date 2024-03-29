SELECT
    *
FROM
	administrator admin
LEFT JOIN
	adminConfirmMail
ON
	admin.administratorseq = adminConfirmMail.administratorseq
WHERE
    adminConfirmMail.adminConfirmMailPassword = /*password*/0
/*%if(adminConfirmMailType != null)*/
AND
    adminConfirmMail.adminConfirmMailType = /*adminConfirmMailType.value*/0
/*%end*/
AND
    adminConfirmMail.effectiveTime >= current_timestamp
