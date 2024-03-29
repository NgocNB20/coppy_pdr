SELECT
/*%if urlType == "11"*/
    DISTINCT ON (latestUpdateTime ,goodsgroup.goodsgroupseq)
    goodsgroupimage.imagetypeversionno,
/*%end*/
    goodsgroup.goodsgroupseq,
    goodsgroup.goodsgroupcode,
    goodsgroup.goodsopenstatuspc,
    CASE WHEN stockstatusdisplay.updatetime IS NULL THEN goodsgroup.updatetime
    	 WHEN goodsgroup.updatetime > stockstatusdisplay.updatetime THEN  goodsgroup.updatetime
         WHEN goodsgroup.updatetime < stockstatusdisplay.updatetime THEN  stockstatusdisplay.updatetime
         ELSE goodsgroup.updatetime
    END AS latestUpdateTime
FROM
    goodsgroup
LEFT JOIN
    stockstatusdisplay
ON
    goodsgroup.goodsgroupseq = stockstatusdisplay.goodsgroupseq
/*%if urlType == "11"*/
INNER JOIN
    goodsgroupimage
ON
    goodsgroup.goodsgroupseq = goodsgroupimage.goodsgroupseq
/*%end*/
WHERE
/*%if siteType == "0"*/
(
    goodsgroup.goodsopenstatuspc = '1'
AND
    (goodsgroup.openstarttimepc <= /*currentTime*/0 OR goodsgroup.openstarttimepc IS NULL)
AND
    (goodsgroup.openendtimepc   >= /*currentTime*/0 OR goodsgroup.openendtimepc   IS NULL)
)
/*%end*/
ORDER BY
    latestUpdateTime DESC,
    goodsgroup.goodsgroupseq DESC
/*%if urlType == "11"*/
    ,goodsgroupimage.imagetypeversionno
/*%end*/
;
