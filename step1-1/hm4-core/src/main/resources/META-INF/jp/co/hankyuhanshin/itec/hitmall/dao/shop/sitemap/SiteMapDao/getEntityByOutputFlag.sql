SELECT
  *
FROM
  sitemap
WHERE
  outputflag = '1'
ORDER BY
  outputfilename ASC,
  urltype        ASC,
  priority       DESC
