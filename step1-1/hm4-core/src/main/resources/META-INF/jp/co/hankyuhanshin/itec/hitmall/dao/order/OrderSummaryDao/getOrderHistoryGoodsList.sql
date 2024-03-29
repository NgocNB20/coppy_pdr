SELECT
	ordergoods.orderseq
	,ordergoods.goodsgroupseq
	,ordergoods.goodsGroupCode
	,ordergoods.goodsseq
	,ordergoods.goodscode
	,ordergoods.goodsgroupname
	,ordergoods.unitvalue1
	,ordergoods.unitvalue2
	,ordergoods.goodscount
	,goodsgroup.goodsopenstatuspc
	,goodsgroup.openstarttimepc
	,goodsgroup.openendtimepc
FROM
	ordersummary
JOIN
	orderindex
ON
	ordersummary.orderseq = orderindex.orderseq
	AND ordersummary.orderversionno = orderindex.orderversionno
JOIN
	ordergoods
ON
	orderindex.orderseq = ordergoods.orderseq
	AND orderindex.ordergoodsversionno = ordergoods.ordergoodsversionno
LEFT JOIN
	goods
ON
	ordergoods.goodsseq = goods.goodsseq
LEFT JOIN
	goodsgroup
ON
	goods.goodsgroupseq = goodsgroup.goodsgroupseq
WHERE
	ordersummary.memberInfoSeq = /*memberInfoSeq*/0
	AND (/*startTime*/0 <= ordersummary.ordertime AND ordersummary.ordertime <= /*endTime*/0)
ORDER BY
	ordersummary.ordertime DESC
	,ordergoods.orderdisplay
