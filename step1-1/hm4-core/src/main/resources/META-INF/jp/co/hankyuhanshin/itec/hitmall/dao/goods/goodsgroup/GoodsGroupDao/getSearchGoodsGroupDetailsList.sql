SELECT * FROM (
	SELECT DISTINCT
		  goodsgroup.goodsgroupseq
	    -- PDR Migrate Customization from here
		, catalogdisplayorder
        , goodsclasstype
        , groupprice
        , groupsaleprice
        , grouppricemarkdispflag
        , groupsalepricemarkdispflag
        , groupsalepriceintegrityflag
        , saleiconflag
        , reserveiconflag
        , newiconflag
        --   2023-renew No92 from here
        , outleticonflag
        --   2023-renew No92 to here
        -- PDR Migrate Customization to here
		, goodsgroup.goodsgroupcode
        , goodsgroup.goodsgroupmaxpricepc
        , goodsgroup.goodsgroupminpricepc
--   2023-renew AddNo5 from here
        , goodsgroup.goodsgroupmaxpricemb
        , goodsgroup.goodsgroupminpricemb
--   2023-renew AddNo5 to here
        /*%if conditionDto.getTaxRoundingModeStr() == "UP"*/
        , (CEIL(goodsgroup.goodsgroupminpricepc * goodsgroup.taxRate / 100) + goodsgroup.goodsgroupminpricepc) AS goodspriceincludedtax
        /*%else*/
        , (FLOOR(goodsgroup.goodsgroupminpricepc * goodsgroup.taxRate / 100) + goodsgroup.goodsgroupminpricepc) AS goodspriceincludedtax
        /*%end*/
		, goodsgroup.whatsnewdate
		, goodsgroup.goodsopenstatuspc
		, goodsgroup.shopseq
		, goodsgroup.goodsgroupname
		, goodsgroup.taxrate
		, goodsgroup.goodstaxtype
		, goodsgroup.goodsprediscountprice
		, goodsgroupdisplay.goodsnote1
		, goodsgroupdisplay.goodsnote2
		, goodsgroupdisplay.goodsnote3
		, goodsgroupdisplay.goodsnote4
		, goodsgroupdisplay.goodsnote5
		, goodsgroupdisplay.goodsnote6
		, goodsgroupdisplay.goodsnote7
		, goodsgroupdisplay.goodsnote8
		, goodsgroupdisplay.goodsnote9
		, goodsgroupdisplay.goodsnote10
		, goodsgroupdisplay.orderSetting1
		, goodsgroupdisplay.orderSetting2
		, goodsgroupdisplay.orderSetting3
		, goodsgroupdisplay.orderSetting4
		, goodsgroupdisplay.orderSetting5
		, goodsgroupdisplay.orderSetting6
		, goodsgroupdisplay.orderSetting7
		, goodsgroupdisplay.orderSetting8
		, goodsgroupdisplay.orderSetting9
		, goodsgroupdisplay.orderSetting10
		, goodsgroupdisplay.informationiconpc
		, goodsgrouppopularity.popularitycount
		, stockStatusDisplay.stockstatuspc
        , preDiscountMaxMin.preDiscountMaxPrice
        , preDiscountMaxMin.preDiscountMinPrice
        -- PDR Migrate Customization from here
        , stockStatusDisplay.stockstatusmb
        , openstarttimepc
        , dentalMonopolySalesFlg
        -- PDR Migrate Customization to here
		/*%if conditionDto.pageInfo.orderField == "normal"*/
			, categorygoods.orderdisplay
		/*%end*/
	FROM
		goodsgroup
            LEFT OUTER JOIN (SELECT goodsgroupseq
                                  , MAX(prediscountprice) AS preDiscountMaxPrice
                                  , MIN(prediscountprice) AS preDiscountMinPrice
                             FROM goods
                             where goods.salestatuspc = '1'
                             GROUP BY goodsgroupseq)
            AS preDiscountMaxMin
                            ON (preDiscountMaxMin.goodsgroupseq = goodsgroup.goodsgroupseq)
		INNER JOIN goodsgroupdisplay
			ON goodsgroupdisplay.goodsgroupseq = goodsgroup.goodsgroupseq
		INNER JOIN goodsgrouppopularity
			ON goodsgrouppopularity.goodsgroupseq = goodsgroup.goodsgroupseq
		LEFT OUTER JOIN stockStatusDisplay
			ON stockStatusDisplay.goodsgroupseq = goodsgroup.goodsgroupseq

		/*%if conditionDto.pageInfo.orderField == "normal"*/
			INNER JOIN categorygoods
				ON categorygoods.goodsgroupseq = goodsgroup.goodsgroupseq
			INNER JOIN category
				ON category.categoryseq = categorygoods.categoryseq
				AND category.categoryid = /*conditionDto.categoryId*/0
		/*%end*/
	WHERE 1 = 1
		/*%if conditionDto.categoryId != null*/
			AND goodsgroup.goodsgroupseq IN (
				SELECT
					categorygoods.goodsgroupseq
				FROM
					category
					INNER JOIN categorygoods
						ON categorygoods.categoryseq = category.categoryseq
				WHERE
					(category.categoryopenstarttimepc <= CURRENT_TIMESTAMP OR category.categoryopenstarttimepc IS NULL)
					AND (category.categoryopenendtimepc >= CURRENT_TIMESTAMP OR category.categoryopenendtimepc IS NULL)
					AND category.categoryopenstatuspc = '1'
					/*%if conditionDto.pageInfo.orderField == "normal"*/
						AND category.categoryid = /*conditionDto.categoryId*/0
					/*%else*/
						AND category.categoryseqpath LIKE (
							SELECT categoryseqpath FROM category WHERE categoryid = /*conditionDto.categoryId*/0
						) || '%'
					/*%end*/
			)
		/*%end*/
		/*%if conditionDto.goodsGroupSeqList != null*/
			AND goodsgroup.goodsgroupseq IN /*conditionDto.goodsGroupSeqList*/(0)
		/*%end*/
		/*%if conditionDto.shopSeq != null*/
			AND goodsgroup.shopSeq = /*conditionDto.shopSeq*/0
		/*%end*/
		AND (openstarttimepc <= CURRENT_TIMESTAMP OR openstarttimepc IS NULL)
		AND (openendtimepc >= CURRENT_TIMESTAMP OR openendtimepc IS NULL)
		/*%if conditionDto.openStatus != null*/
		  AND goodsopenstatuspc = /*conditionDto.openStatus.value*/0
		/*%end*/
		/*%if conditionDto.keywordLikeCondition1 != null*/AND goodsgroupdisplay.searchkeywordem LIKE '%' || /*conditionDto.keywordLikeCondition1*/0 || '%'/*%end*/
		/*%if conditionDto.keywordLikeCondition2 != null*/AND goodsgroupdisplay.searchkeywordem LIKE '%' || /*conditionDto.keywordLikeCondition2*/0 || '%'/*%end*/
		/*%if conditionDto.keywordLikeCondition3 != null*/AND goodsgroupdisplay.searchkeywordem LIKE '%' || /*conditionDto.keywordLikeCondition3*/0 || '%'/*%end*/
		/*%if conditionDto.keywordLikeCondition4 != null*/AND goodsgroupdisplay.searchkeywordem LIKE '%' || /*conditionDto.keywordLikeCondition4*/0 || '%'/*%end*/
		/*%if conditionDto.keywordLikeCondition5 != null*/AND goodsgroupdisplay.searchkeywordem LIKE '%' || /*conditionDto.keywordLikeCondition5*/0 || '%'/*%end*/
		/*%if conditionDto.keywordLikeCondition6 != null*/AND goodsgroupdisplay.searchkeywordem LIKE '%' || /*conditionDto.keywordLikeCondition6*/0 || '%'/*%end*/
		/*%if conditionDto.keywordLikeCondition7 != null*/AND goodsgroupdisplay.searchkeywordem LIKE '%' || /*conditionDto.keywordLikeCondition7*/0 || '%'/*%end*/
		/*%if conditionDto.keywordLikeCondition8 != null*/AND goodsgroupdisplay.searchkeywordem LIKE '%' || /*conditionDto.keywordLikeCondition8*/0 || '%'/*%end*/
		/*%if conditionDto.keywordLikeCondition9 != null*/AND goodsgroupdisplay.searchkeywordem LIKE '%' || /*conditionDto.keywordLikeCondition9*/0 || '%'/*%end*/
		/*%if conditionDto.keywordLikeCondition10 != null*/AND goodsgroupdisplay.searchkeywordem LIKE '%' || /*conditionDto.keywordLikeCondition10*/0 || '%'/*%end*/
		/*%if conditionDto.stcockExistStatus != null*/
			AND stockStatusDisplay.stockstatuspc IN /*conditionDto.stcockExistStatus*/(0)
		/*%end*/
) goodsgroupdetail
WHERE 1 = 1
	/*%if conditionDto.minPrice != null*/
		AND goodsgroupdetail.goodsgroupminpricepc >= /*conditionDto.minPrice*/0
	/*%end*/
	/*%if conditionDto.maxPrice != null*/
		AND goodsgroupdetail.goodsgroupmaxpricepc <= /*conditionDto.maxPrice*/0
	/*%end*/
-- PDR Migrate Customization from here
    /*%if conditionDto.salesGoodsTypeList != null*/
        AND goodsgroupdetail.goodsclasstype IN /*conditionDto.salesGoodsTypeList*/()
    /*%end*/
    /*%if conditionDto.dentalMonopolySalesFlgList != null*/
        AND goodsgroupdetail.dentalMonopolySalesFlg IN /*conditionDto.dentalMonopolySalesFlgList*/()
    /*%end*/
    /*%if conditionDto.salesAdvisabilitySeqList != null*/
        AND exists(select
                 1
             from
                 goodsgroup gb
                     inner join salesAdvisability sa
                                on sa.salesadvisabilityseq IN /*conditionDto.salesAdvisabilitySeqList*/()
                                    and sa.goodsclasstype = gb.goodsclasstype
                                    and sa.dentalmonopolysalesflg = gb.dentalmonopolysalesflg
             where goodsgroupdetail.goodsgroupseq = gb.goodsgroupseq
)
    /*%end*/
/*************** sort ***************/
ORDER BY
/*%if conditionDto.pageInfo.orderField == "newlyGoodsItems"*/
    /*%if conditionDto.pageInfo.orderAsc*/
    goodsgroupdetail.stockstatusmb ASC, goodsgroupdetail.openstarttimepc ASC, goodsgroupdetail.goodsgroupseq ASC
    /*%else*/
    goodsgroupdetail.stockstatusmb ASC, goodsgroupdetail.openstarttimepc DESC NULLS LAST, goodsgroupdetail.goodsgroupseq DESC
    /*%end*/
/*%elseif conditionDto.pageInfo.orderField == "whatsnewdate"*/
    /*%if conditionDto.pageInfo.orderAsc*/
        goodsgroupdetail.stockstatusmb ASC, goodsgroupdetail.catalogdisplayorder ASC, goodsgroupdetail.goodsgroupcode ASC
    /*%else*/
        goodsgroupdetail.stockstatusmb ASC, goodsgroupdetail.catalogdisplayorder DESC, goodsgroupdetail.goodsgroupcode DESC
    /*%end*/
/*%elseif conditionDto.pageInfo.orderField == "goodsGroupMinPrice"*/
    /*%if conditionDto.pageInfo.orderAsc*/
        goodsgroupdetail.goodspriceincludedtax  ASC, goodsgroupdetail.catalogdisplayorder ASC, goodsgroupdetail.goodsgroupcode ASC
    /*%else*/
        goodsgroupdetail.stockstatusmb ASC, goodsgroupdetail.goodspriceincludedtax DESC, goodsgroupdetail.catalogdisplayorder DESC, goodsgroupdetail.goodsgroupcode DESC
    /*%end*/
/*%elseif conditionDto.pageInfo.orderField == "normal"*/
    /*%if conditionDto.pageInfo.orderAsc*/
        goodsgroupdetail.stockstatusmb ASC, goodsgroupdetail.orderdisplay ASC, goodsgroupdetail.goodsgroupcode ASC
    /*%else*/
        goodsgroupdetail.stockstatusmb ASC, goodsgroupdetail.orderdisplay DESC, goodsgroupdetail.goodsgroupcode DESC
    /*%end*/
/*%elseif conditionDto.pageInfo.orderField == "popularityCount"*/
    /*%if conditionDto.pageInfo.orderAsc*/
        goodsgroupdetail.stockstatusmb ASC, goodsgroupdetail.popularitycount ASC, goodsgroupdetail.catalogdisplayorder ASC, goodsgroupdetail.goodsgroupcode ASC
    /*%else*/
        goodsgroupdetail.stockstatusmb ASC, goodsgroupdetail.popularitycount DESC, goodsgroupdetail.catalogdisplayorder DESC, goodsgroupdetail.goodsgroupcode DESC
    /*%end*/
/*%else*/
    1 ASC
/*%end*/
-- PDR Migrate Customization to here
