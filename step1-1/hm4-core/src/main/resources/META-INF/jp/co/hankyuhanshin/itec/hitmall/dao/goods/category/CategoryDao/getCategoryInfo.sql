SELECT
category.*,
categorydisplay.categorynamepc,
categorydisplay.categorynamemb,
categorydisplay.categorynotepc,
categorydisplay.categorynotemb,
categorydisplay.freetextpc,
categorydisplay.freetextsp,
categorydisplay.freetextmb,
categorydisplay.metadescription,
categorydisplay.metakeyword,
categorydisplay.categoryimagepc,
categorydisplay.categoryimagesp,
categorydisplay.categoryimagemb,
categorydisplay.registtime as displayregisttime,
categorydisplay.updatetime as displayupdatetime
FROM category,categorydisplay
WHERE
    category.categoryseq = categorydisplay.categoryseq
    AND category.shopseq = /*conditionDto.shopSeq*/0
    AND category.categoryid = /*conditionDto.categoryId*/0
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
