SELECT
    goodsgroup.goodsgroupcode
    ,goods.goodscode
    ,goodsgroup.goodsgroupnameadmin
    ,memberinfo.customerno
    ,salegoods.salestatus
    ,salegoods.salecd
    ,salegoods.saleto
FROM
    (SELECT (CAST(goodsseq AS VARCHAR) || CAST(memberinfoseq AS VARCHAR)) AS key,memberinfoseq ,goodsseq FROM favorite) AS favorite
    INNER JOIN memberinfo
        ON favorite.memberinfoseq = memberinfo.memberinfoseq
    INNER JOIN goods
        ON favorite.goodsseq = goods.goodsseq
    INNER JOIN goodsgroup
        ON goods.goodsgroupseq = goodsgroup.goodsgroupseq
    LEFT JOIN salegoods
        ON favorite.goodsseq = salegoods.goodsseq
WHERE
    favorite.key IN /*favoriteSeqList*/(0)
/*************** sort ***************/
order by goodsgroup.goodsgroupcode ASC
