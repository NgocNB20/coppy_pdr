SELECT
	gr.*
FROM
	goodsRelation AS gr
	INNER JOIN goodsGroup AS gg ON gg.goodsGroupSeq = gr.goodsRelationGroupSeq
WHERE
	gr.goodsGroupSeq = /*conditionDto.goodsGroupSeq*/0
	/*%if conditionDto.openStatus != null && conditionDto.siteType != null*/
		/*%if conditionDto.siteType.value != "2"*/
			AND (gg.openStartTimePC <= CURRENT_TIMESTAMP OR gg.openStartTimePC IS NULL)
			AND (gg.openEndTimePC >= CURRENT_TIMESTAMP OR gg.openEndTimePC IS NULL)
			AND gg.goodsOpenStatusPC = /*conditionDto.openStatus.value*/0
		/*%end*/
	/*%end*/
	/*%if conditionDto.goodsGroupSeqList != null*/
		AND gr.goodsRelationGroupSeq NOT IN /*conditionDto.goodsGroupSeqList*/(0)
	/*%end*/
ORDER BY gr.orderDisplay ASC
