SELECT campaigncode
FROM accessinfo
WHERE 
shopSeq = /*shopSeq*/0 AND
accessUid = /*accessUid*/0 AND
accesstype = 'C001' AND
accessdate >= /*startDate*/0 AND
accessdate <= /*endDate*/0
ORDER BY updatetime DESC
OFFSET 0 LIMIT 1
