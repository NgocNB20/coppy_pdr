SELECT memberInfo.memberinfoseq
FROM
    -- 入会中の会員データを抽出
    (SELECT memberinfoseq FROM memberInfo
     WHERE memberinfostatus = '0') AS memberInfo
    INNER JOIN
    (
        -- 会員ごとの最大の有効期限を取得
        SELECT memberinfoseq,date_trunc('day', MAX(limitDate)) AS limitDate
        FROM
        (
            -- 会員、ポイントSEQごとのポイント連番の最大値のデータを抽出
            SELECT *
            FROM point
            WHERE (memberinfoseq,pointseq,pointversionno)
            IN (
                    SELECT memberinfoseq,pointseq,MAX(pointversionno)
                    FROM point wk_point
                    GROUP BY memberinfoseq,pointseq
                )
        )point
        GROUP BY memberinfoseq
    ) point
    ON memberInfo.memberinfoseq = point.memberinfoseq
    INNER JOIN
    (
        SELECT
            memberinfoseq
        FROM
            point AS mainPoint
        WHERE
            mainPoint.pointversionNo = (
                SELECT
                    MAX(pointversionNo)
                FROM
                    point As subPoint
                WHERE
                    mainPoint.pointseq = subPoint.pointseq
                GROUP BY
                    subPoint.pointseq
            )
        group by memberinfoseq
        having COALESCE(SUM(totalpoint), 0) > 0
    ) kakutei
    ON memberInfo.memberinfoseq = kakutei.memberinfoseq
WHERE
    /*%if isNotification*/
    -- 最大の有効期限=処理日
    point.limitDate = TO_TIMESTAMP(/*checkeDate*/'2010/1/1', 'yyyy/MM/dd')
    /*%else*/
    -- 最大の有効期限＜処理日
    point.limitDate < /*checkeDate*/'2010/1/1'
    /*%end*/
ORDER BY
    memberInfo.memberinfoseq
