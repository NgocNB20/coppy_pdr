-- PDR Migrate Customization from here
SELECT
  memberinfo.customerNo 
FROM
  newsviewablemember
INNER JOIN 
  memberinfo
ON
  newsviewablemember.memberinfoseq = memberinfo.memberinfoseq
WHERE
  newsviewablemember.newsseq = /*newsSeq*/0

/* ************** sort ************** */
ORDER BY
  newsviewablemember.memberinfoseq ASC
-- PDR Migrate Customization to here
