-- PDR Migrate Customization from here
SELECT
category.categoryseq,
category.shopseq,
category.categoryid,
category.categoryname,
category.categoryopenstatuspc,
category.categoryopenstarttimepc,
category.categoryopenendtimepc,
category.categorytype,
category.categoryseqpath,
category.orderdisplay,
category.rootcategoryseq,
category.categorylevel,
category.categorypath,
category.manualdisplayflag,
category.versionno,
category.registtime,
category.updatetime,
categorydisplay.categorynamepc,
categorydisplay.categorynotepc,
categorydisplay.freetextpc,
categorydisplay.metadescription,
categorydisplay.metakeyword,
categorydisplay.categoryimagepc,
categorydisplay.registtime as displayregisttime,
categorydisplay.updatetime as displayupdatetime
FROM category,categorydisplay
WHERE
category.categoryseq = categorydisplay.categoryseq
AND category.categorylevel >= /*startCategorylevel*/0
AND category.categorylevel <= /*endCategorylevel*/0
AND category.categorytype = /*categoryType.value*/0
/*%if conditionDto.openStatus != null && conditionDto.siteType.value == "0" */
    AND (category.categoryopenstarttimepc <= CURRENT_TIMESTAMP OR category.categoryopenstarttimepc is null)
    AND (category.categoryopenendtimepc >= CURRENT_TIMESTAMP OR category.categoryopenendtimepc is null)
    AND category.categoryopenstatuspc = /*conditionDto.openStatus.value*/0
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
/*%end*/
-- PDR Migrate Customization to here