SELECT categoryid
	FROM category
WHERE
    shopseq = /*shopSeq*/0
AND categoryseqpath LIKE /*categorySeqPath*/0 || '%'
AND categorylevel = /*categoryLevel*/0
AND orderdisplay > /*orderdisplay*/0
ORDER BY orderdisplay
