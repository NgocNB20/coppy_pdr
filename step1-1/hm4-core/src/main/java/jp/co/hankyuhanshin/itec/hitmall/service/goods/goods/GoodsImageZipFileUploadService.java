/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.goods;

import org.springframework.web.multipart.MultipartFile;

/**
 * 商品画像zip一括アップロードServiceインターフェース
 *
 * @author $author$
 * @version $Revision: 1.2 $
 */
public interface GoodsImageZipFileUploadService {
    /**
     * 商品画像zip一括アップロード処理を実行します。
     *
     * @param uploadedFile UploadedFile
     * @return int
     */
    int execute(MultipartFile uploadedFile);

    /**
     * 商品画像zip一括アップロード処理を実行します。
     *
     * @param uploadedFile   UploadedFile
     * @param zipImageTarget アップロード先判定
     * @return int
     */
    int execute(MultipartFile uploadedFile, String zipImageTarget);
}
