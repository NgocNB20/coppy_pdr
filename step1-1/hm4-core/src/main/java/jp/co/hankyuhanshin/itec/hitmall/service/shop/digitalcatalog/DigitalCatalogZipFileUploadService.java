// 2023-renew No42 from here
package jp.co.hankyuhanshin.itec.hitmall.service.shop.digitalcatalog;

import org.springframework.web.multipart.MultipartFile;

/**
 * デジタルカタロzip一括アップロードServiceインターフェース
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public interface DigitalCatalogZipFileUploadService {

    /**
     * zipファイルをディレクトリに保存します
     *
     * @param uploadedFile           UploadedFile
     * @param tmpCatalogZipFilePath  アップロード先判定
     * @return
     */
    int execute(MultipartFile uploadedFile, String tmpCatalogZipFilePath);

    /**
     * デジタルカタロzip一括アップロード処理を実行します
     *
     * @param tmpCatalogZipFilePath  アップロード先判定
     * @param digitalCatalogDir      デジタルカタログアップロードディレクトリ
     * @return
     */
    int execute(String tmpCatalogZipFilePath, String digitalCatalogDir);
}
// 2023-renew No42 to here