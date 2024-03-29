SELECT
  COUNT(*)
FROM
  goodsgroup gg,goods g
WHERE gg.goodsgroupseq = g.goodsgroupseq
  AND goodsgroupcode = /*goodsGroupCode*/0
  AND goodscode != /*goodsCode*/0
  AND salestatuspc != '9' -- 削除はPC/MBで同じ値が保証されるので、PCのみ確認でOK
  -- PDR Migrate Customization from here
  AND emotionPriceType != '2'
  -- PDR Migrate Customization to here
  AND unitvalue1 = /*unitvalue1*/0
/*%if unitvalue2 != null*/
  AND unitvalue2 = /*unitvalue2*/0
/*%end*/
