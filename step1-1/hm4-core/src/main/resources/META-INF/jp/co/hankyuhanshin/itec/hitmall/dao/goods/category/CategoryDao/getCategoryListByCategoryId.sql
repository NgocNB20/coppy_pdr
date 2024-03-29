SELECT
category.*,
categorydisplay.categorynamepc,
categorydisplay.categorynamemb,
categorydisplay.categorynotepc,
categorydisplay.categorynotemb,
categorydisplay.freetextpc,
categorydisplay.freetextsp,
categorydisplay.freetextmb,
categorydisplay.metadescription,
categorydisplay.metakeyword,
categorydisplay.categoryimagepc,
categorydisplay.categoryimagesp,
categorydisplay.categoryimagemb,
categorydisplay.registtime as displayregisttime,
categorydisplay.updatetime as displayupdatetime
FROM
  category
INNER JOIN categoryDisplay ON category.categoryseq = categorydisplay.categoryseq
WHERE
  -- 指定のカテゴリIDに紐づく親カテゴリを取得
  ((SELECT categoryseqpath FROM category WHERE categoryid = /*categoryId*/0 ) LIKE '%' || category.categoryseq  || '%')
 OR
  -- 指定のカテゴリIDに紐づく子カテゴリを取得
  category.categoryseqpath LIKE (SELECT categoryseqpath FROM category WHERE categoryid =  /*categoryId*/0 ) || '%'
ORDER BY
 categorypath ASC
