-- 2023-renew categoryCSV from here
SELECT
    a.*,
    categorydisplay.*,
    (select category.categoryid
        from category ,(select categoryseqpath,categorylevel from category where categoryseq = a.categoryseq) childcategory
        where childcategory.categoryseqpath like category.categoryseqpath || '%'
        and childcategory.categorylevel = category.categorylevel + 1
        and a.shopseq = /*conditionDto.shopSeq*/0) as parentCategoryId

FROM category a, categorydisplay
WHERE
a.categoryseq = categorydisplay.categoryseq
AND a.shopseq = /*conditionDto.shopSeq*/0
/*%if conditionDto.notInCategoryIdList != null*/
AND a.categoryid not in /*conditionDto.notInCategoryIdList*/(0)
/*%end*/
order by
    a.categorypath asc
-- 2023-renew categoryCSV to here
