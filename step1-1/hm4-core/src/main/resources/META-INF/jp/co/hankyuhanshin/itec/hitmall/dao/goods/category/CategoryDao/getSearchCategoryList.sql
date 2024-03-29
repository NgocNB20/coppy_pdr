select
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
from
  category,categoryDisplay
where
  category.categoryseq = categorydisplay.categoryseq and
/*%if conditionDto.categoryId != null*/
  category.categoryseqpath LIKE (select categoryseqpath from category where categoryID = /*conditionDto.categoryId*/0) || '%' and
/*%end*/
/*%if conditionDto.openStatus != null && conditionDto.siteType != null */
  /*%if conditionDto.siteType.value == "0" || conditionDto.siteType.value == "1" */
    (category.categoryopenstarttimepc <= CURRENT_TIMESTAMP OR category.categoryopenstarttimepc is null) and
    (category.categoryopenendtimepc >= CURRENT_TIMESTAMP OR category.categoryopenendtimepc is null) and
    category.categoryopenstatuspc = /*conditionDto.openStatus.value*/0 and
  /*%end*/
  /*%if conditionDto.siteType.value == "2"*/
    (category.categoryopenstarttimemb <= CURRENT_TIMESTAMP OR category.categoryopenstarttimemb is null) and
    (category.categoryopenendtimemb >= CURRENT_TIMESTAMP OR category.categoryopenendtimemb is null) and
    category.categoryopenstatusmb = /*conditionDto.openStatus.value*/0 and
  /*%end*/
/*%end*/
  (
/*%if conditionDto.maxHierarchical == null && conditionDto.categorySeqList == null && currentCategoryId == null*/
 1=1
/*%end*/
/*%if conditionDto.maxHierarchical != null*/
    (category.categorylevel <= /*conditionDto.maxHierarchical*/0 )
  /*%if conditionDto.categorySeqList != null || currentCategoryId != null*/
    or
  /*%end*/
/*%end*/
/*%if conditionDto.categorySeqList != null*/
    (category.categoryseq IN /*conditionDto.categorySeqList*/(1,2,3))
    /*%if currentCategoryId != null*/
        or
    /*%end*/
/*%end*/
/*%if currentCategoryId != null*/
    (category.categoryseqpath LIKE  (select categoryseqpath from category where categoryID = /*currentCategoryId*/0) || '%' and
     category.categorylevel <= (select categorylevel from category where categoryID = /*currentCategoryId*/0) + 1)
/*%end*/
  )

  UNION
  select
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
  from
    category,
    categoryDisplay
/*%if conditionDto.categorySeqList != null*/
    ,(select category.categoryseqpath ,category.categorylevel from category where category.categoryseq IN /*conditionDto.categorySeqList*/(1,2,3)) as newcategory
/*%end*/
  where
/*%if conditionDto.categorySeqList != null*/
    category.categoryseq = categorydisplay.categoryseq and
    category.categoryseqpath like newcategory.categoryseqpath || '%' and category.categorylevel <= newcategory.categorylevel + 1
  /*%if conditionDto.openStatus != null && conditionDto.siteType != null */
    /*%if conditionDto.siteType.value == "0" || conditionDto.siteType.value == "1" */
      and (category.categoryopenstarttimepc <= CURRENT_TIMESTAMP OR category.categoryopenstarttimepc is null) and
      (category.categoryopenendtimepc >= CURRENT_TIMESTAMP OR category.categoryopenendtimepc is null) and
      category.categoryopenstatuspc = /*conditionDto.openStatus.value*/0
    /*%end*/
    /*%if conditionDto.siteType.value == "2"*/
      and (category.categoryopenstarttimemb <= CURRENT_TIMESTAMP OR category.categoryopenstarttimemb is null) and
      (category.categoryopenendtimemb >= CURRENT_TIMESTAMP OR category.categoryopenendtimemb is null) and
      category.categoryopenstatusmb = /*conditionDto.openStatus.value*/0
    /*%end*/
  /*%end*/
/*%else*/
    false
/*%end*/

  order by
  /*%if conditionDto.orderField == "categorypath"*/
    categorypath
    /*%if conditionDto.orderAsc*/
      asc
    /*%else*/
      desc
    /*%end*/
  /*%else*/
    1 asc
  /*%end*/
