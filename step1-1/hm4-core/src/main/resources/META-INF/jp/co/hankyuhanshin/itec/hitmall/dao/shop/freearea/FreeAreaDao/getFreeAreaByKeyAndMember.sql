SELECT
  freearea.* 
FROM
  freearea
INNER JOIN
(
    SELECT
      freearea.freeareaseq 
    FROM
      freearea  
    WHERE
      freearea.shopseq = /*shopSeq*/0
    AND
      freearea.freeareakey = /*freeAreaKey*/0
    AND
      freearea.openstarttime <= CURRENT_TIMESTAMP
    ORDER BY
      freearea.openstarttime DESC 
    LIMIT 1
) AS lastfreearea
ON freearea.freeareaseq = lastfreearea.freeareaseq 
WHERE
/*%if memberInfoSeq != null && memberInfoSeq != 0*/
    EXISTS
      ( 
      SELECT
        1 
      FROM
        freeareaviewablemember 
      WHERE
        freeareaviewablemember.freeareaseq = freearea.freeareaseq 
      AND
        freeareaviewablemember.memberinfoseq = /*memberInfoSeq*/0
      ) 
  OR
    NOT EXISTS
      ( 
      SELECT
        1 
      FROM
        freeareaviewablemember 
      WHERE
        freeareaviewablemember.freeareaseq = freearea.freeareaseq
      )
  /*%else*/
  NOT EXISTS
    ( 
    SELECT
      1 
    FROM
      freeareaviewablemember 
    WHERE
      freeareaviewablemember.freeareaseq = freearea.freeareaseq
    )
/*%end*/
