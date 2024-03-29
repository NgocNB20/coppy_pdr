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
AND category.shopseq = /*shopSeq*/0
/*%if categoryIdList != null*/
AND categoryId in /*categoryIdList*/(1,2,3)
/*%end*/
/*%if openStatus != null && siteType != null */
  /*%if siteType.value == "0" || siteType.value == "1" */
    AND (category.categoryopenstarttimepc <= CURRENT_TIMESTAMP OR category.categoryopenstarttimepc is null)
    AND (category.categoryopenendtimepc >= CURRENT_TIMESTAMP OR category.categoryopenendtimepc is null)
    AND category.categoryopenstatuspc = /*openStatus.value*/0
  /*%end*/
  /*%if siteType.value == "2" */
    AND (category.categoryopenstarttimemb <= CURRENT_TIMESTAMP OR category.categoryopenstarttimemb is null)
    AND (category.categoryopenendtimemb >= CURRENT_TIMESTAMP OR category.categoryopenendtimemb is null)
    AND category.categoryopenstatusmb = /*openStatus.value*/0
    /*%end*/
/*%end*/
 order by
 categoryId
