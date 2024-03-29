-- PDR Migrate Customization from here
SELECT * FROM
    cartgoods 
WHERE
/*%if memberInfoSeq != null*/ 
    memberInfoSeq = /*memberInfoSeq*/0
/*%end*/
/*%if shopSeq != null*/ 
    AND shopSeq = /*shopSeq*/0
/*%end*/
FOR UPDATE NOWAIT
-- PDR Migrate Customization to here