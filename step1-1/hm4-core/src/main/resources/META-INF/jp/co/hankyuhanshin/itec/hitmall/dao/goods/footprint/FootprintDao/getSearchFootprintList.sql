SELECT
	fp.*
FROM
	footprint AS fp
	INNER JOIN goodsgroup AS gg
		ON gg.goodsGroupSeq = fp.goodsGroupSeq
WHERE
	fp.shopSeq = /*conditionDto.shopSeq*/0
	AND fp.accessUid = /*conditionDto.accessUid*/0
	AND (gg.openStartTimePc <= CURRENT_TIMESTAMP OR gg.openStartTimePc IS NULL)
	AND (gg.openEndTimePc >= CURRENT_TIMESTAMP OR gg.openEndTimePc IS NULL)
	AND gg.goodsOpenStatusPc = '1'
	/*%if conditionDto.goodsGroupSeqList != null*/
		AND fp.goodsGroupSeq NOT IN /*conditionDto.goodsGroupSeqList*/(1,2,3)
	/*%end*/

ORDER BY

/*%if conditionDto.pageInfo.orderField != null*/
	/*%if conditionDto.pageInfo.orderField == "updateTime"*/
		fp.updateTime DESC
	/*%end*/
/*%else*/
	1 ASC
/*%end*/
