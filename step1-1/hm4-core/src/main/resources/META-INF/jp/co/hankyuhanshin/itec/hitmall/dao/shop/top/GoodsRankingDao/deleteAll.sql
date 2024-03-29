DELETE FROM
    goodsranking
WHERE
    /*%if shopSeq != null*/
        shopSeq = /*shopSeq*/0
    /*%else*/
        true
    /*%end*/
