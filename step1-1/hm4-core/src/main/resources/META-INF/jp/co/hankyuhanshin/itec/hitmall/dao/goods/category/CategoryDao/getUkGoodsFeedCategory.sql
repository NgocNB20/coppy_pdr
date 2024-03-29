WITH categoryQuery AS (
    SELECT DISTINCT
        c2.categorySeq
        , c2.categoryPath
        , c1.categoryPath
        , CASE
            WHEN c2.categorySeq = c1.categorySeq THEN c1.categoryId || ''
            ELSE c1.categoryId || /*delimiter*/''
            END AS categoryId
        , CASE
            WHEN c2.categorySeq = c1.categorySeq THEN c1.categoryName || ''
            ELSE c1.categoryName || /*delimiter*/''
            END AS categoryName
        , CASE
            WHEN c2.categorySeq = c1.categorySeq THEN TO_CHAR(c1.orderDisplay, 'FM000') || ''
            ELSE TO_CHAR(c1.orderDisplay, 'FM000') || /*delimiter*/''
            END AS orderDisplay
    FROM
        category c1
        INNER JOIN (SELECT * FROM category WHERE category.categoryLevel > 0) AS c2
            ON c2.categorySeqPath LIKE c1.categorySeqPath || '%'
    WHERE
        c1.categoryLevel > 0
    ORDER BY
        c2.categorySeq
        , c2.categoryPath
        , c1.categoryPath
)
SELECT
    c.categorySeq
    , array_to_string(
        ARRAY (
            SELECT
                categoryQuery.categoryId
            FROM
                categoryQuery
            WHERE
                c.categorySeq = categoryQuery.categorySeq
        )
        , ''
    ) AS categoryIdList
    , array_to_string(
        ARRAY (
            SELECT
                categoryQuery.categoryName
            FROM
                categoryQuery
            WHERE
                c.categorySeq = categoryQuery.categorySeq
        )
        , ''
    ) AS categoryNameList
    , array_to_string(
        ARRAY (
            SELECT
                categoryQuery.orderDisplay
            FROM
                categoryQuery
            WHERE
                c.categorySeq = categoryQuery.categorySeq
        )
        , ''
    ) AS categoryOrderList
FROM
    category AS c
WHERE
    c.categoryLevel > 0
ORDER BY
    c.categoryPath
;
