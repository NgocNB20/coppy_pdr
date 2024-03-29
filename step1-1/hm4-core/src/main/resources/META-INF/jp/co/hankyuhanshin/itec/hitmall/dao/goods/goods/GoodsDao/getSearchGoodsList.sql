SELECT *
FROM
  goods
WHERE
  goods.goodsgroupseq=/*conditionDto.goodsGroupSeq*/0
/*%if conditionDto.shopSeq != null*/
  AND goods.shopSeq = /*conditionDto.shopSeq*/0
/*%end*/
  AND goods.saleStatusPC <> '9'
-- PDR Migrate Customization from here
/*%if conditionDto.csvUploadType == null*/
  AND goods.emotionpricetype <> '2'
/*%end*/
-- PDR Migrate Customization to here
ORDER BY
/*%if conditionDto.orderField != null*/
  /*%if conditionDto.orderField == "orderdisplay"*/
    goods.orderdisplay
  /*%end*/
  /*%if conditionDto.orderAsc */
    ASC
  /*%else*/
    DESC
  /*%end*/
/*%else*/
  1 ASC
/*%end*/
