UPDATE
  category
SET
  categorypath = REGEXP_REPLACE(categorypath,(SELECT cg.categorypath FROM category cg WHERE cg.categoryseq = /*categoryEntity.categorySeq*/0),/*categoryEntity.categoryPath*/0),
  updatetime = /*categoryEntity.updateTime*/0,
  versionno = versionno + 1
WHERE
  -- 自身のカテゴリーは含めない
  categoryseq <> /*categoryEntity.categorySeq*/0
 AND
  -- 指定したカテゴリーから子階層のカテゴリーのみ抽出
  categoryseqpath LIKE /*categoryEntity.categorySeqPath*/0 || '%'
