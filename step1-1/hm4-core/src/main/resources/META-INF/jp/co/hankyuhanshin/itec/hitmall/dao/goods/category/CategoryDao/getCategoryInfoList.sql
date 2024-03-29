SELECT
category.categoryId,
category.categoryLevel,
category.categorypath,
categorydisplay.categorynamepc,
categorydisplay.categorynamemb
FROM category,categorydisplay

WHERE
category.categoryseq = categorydisplay.categoryseq
/*%if conditionDto.categorySeqList != null*/
AND category.categoryseq in /*conditionDto.categorySeqList*/(1,2,3)
/*%end*/
/*%if conditionDto.shopSeq != null*/
AND category.shopseq = /*conditionDto.shopSeq*/0
/*%end*/
/*%if conditionDto.openStatus != null && conditionDto.siteType != null */
  /*%if conditionDto.siteType.value == "0" || conditionDto.siteType.value == "1" */
    AND (category.categoryopenstarttimepc <= CURRENT_TIMESTAMP OR category.categoryopenstarttimepc is null)
    AND (category.categoryopenendtimepc >= CURRENT_TIMESTAMP OR category.categoryopenendtimepc is null)
    AND category.categoryopenstatuspc = /*conditionDto.openStatus.value*/0
  /*%end*/
  /*%if conditionDto.siteType.value == "2" */
    AND (category.categoryopenstarttimemb <= CURRENT_TIMESTAMP OR category.categoryopenstarttimemb is null)
    AND (category.categoryopenendtimemb >= CURRENT_TIMESTAMP OR category.categoryopenendtimemb is null)
    AND category.categoryopenstatusmb = /*conditionDto.openStatus.value*/0
  /*%end*/
/*%end*/

ORDER BY
/*%if conditionDto.orderField != null*/
  /*%if conditionDto.orderField == "categorypath"*/
  category.categorypath
  /*%end*/
  /*%if conditionDto.orderAsc */
  asc
  /*%else*/
  desc
  /*%end*/
/*%else*/
  1 ASC
/*%end*/

