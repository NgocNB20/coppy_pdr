SELECT *
FROM categorygoods
WHERE
    categorygoods.categoryseq = /*categorySeq*/0
/*%if goodsGroupSeq != null */
    AND categorygoods.goodsgroupseq = /*goodsGroupSeq*/0
/*%end*/
/*%if fromOrderDisplay != null */
    AND categorygoods.orderDisplay >= /*fromOrderDisplay*/0
/*%end*/
/*%if toOrderDisplay != null */
    AND categorygoods.orderDisplay <= /*toOrderDisplay*/0
/*%end*/

ORDER BY orderDisplay

/*%if orderBy*/
ASC
/*%end*/

/*%if !orderBy*/
DESC
/*%end*/
