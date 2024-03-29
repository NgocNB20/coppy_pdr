SELECT
        COALESCE(COUNT(accesssearch.searchkeyword), 0) AS searchCount
        ,COALESCE(AVG(accesssearch.searchresultcount), 0) AS searchResultCount
        ,accesssearch.searchkeyword AS searchkeyword
    FROM
        (
            SELECT
                    subAccesssearch.searchpricefrom
                    ,subAccesssearch.searchpriceto
                    ,subAccesssearch.search_key AS searchkeyword
                    ,subAccesssearch.searchresultcount
                FROM
                    (
                        SELECT
                                searchpricefrom
                                ,searchpriceto
                                --全角英小文字を全角英大文字に置換する
                                ,translate(
                                    searchkeyword
                                    ,'ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ'
                                    ,'ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ'
                                ) AS search_key
                                ,searchresultcount
                            FROM
                                accesssearchkeyword
                            WHERE
                                /*%if conditionDto.shopSeq != null*/
                                shopseq = /*conditionDto.shopSeq*/0
                                AND
                                /*%end*/
                                accesstime >= /*conditionDto.processDateFrom*/0
                                AND accesstime <= /*conditionDto.processDateTo*/0
                                /*%if conditionDto.orderSiteTypeList.size() > 0*/
                                AND sitetype IN /*conditionDto.orderSiteTypeList*/(0)
                                /*%end*/
                    ) AS subAccesssearch
                WHERE
                    /*%if conditionDto.searchKeyword != null && !conditionDto.searchKeyword.equals("")*/
                        subAccesssearch.search_key LIKE '%' || /*conditionDto.searchKeyword*/0 || '%'
                    /*%else*/
                        true
                    /*%end*/
        ) AS accesssearch
    GROUP BY
        accesssearch.searchkeyword
    HAVING
        1 = 1
        /*%if conditionDto.searchResultCountFrom != null*/
        AND COUNT(accesssearch.searchkeyword) >= /*conditionDto.searchResultCountFrom*/0
        /*%end*/
        /*%if conditionDto.searchResultCountTo != null*/
        AND COUNT(accesssearch.searchkeyword) <= /*conditionDto.searchResultCountTo*/0
        /*%end*/
    ORDER BY
        searchCount DESC
        ,searchkeyword ASC
