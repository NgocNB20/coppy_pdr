/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.web;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;
import jp.co.hankyuhanshin.itec.hitmall.service.zipcode.ZipCodeAddressGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 郵便番号検索コントローラー<br/>
 *
 * @author yt23807
 */
@RestController

@RequestMapping("/")
public class ZipCodeSearchController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipCodeSearchController.class);

    /**
     * マップに格納する都道府県のkey名
     */
    private static final String KEY_OF_PRIFECTURE = "prefecture";

    /**
     * マップに格納する市区郡のkey名
     */
    private static final String KEY_OF_ADDRESS1 = "address1";

    /**
     * マップに格納する町村名のkey名
     */
    private static final String KEY_OF_ADDRESS2 = "address2";

    /**
     * マップに格納する小字名、丁目、番地等のkey名
     */
    private static final String KEY_OF_ADDRESS3 = "address3";

    /**
     * マップに格納するエラメッセージのkey名
     */
    private static final String KEY_OF_ERROR_MSG = "zipErrorMsg";

    /**
     * 郵便番号検索処理<br/>
     *
     * @param zipCode       郵便番号
     * @param prefectureFlg 都道府県情報フラグ
     * @return 郵便番号検索結果、または、エラーメッセージを保持する文字列
     */
    @GetMapping("/zipcodeSearch")
    @ResponseBody
    public Map<String, String> execute(@RequestParam(name = "zipCode", required = false) Optional<String> zipCode,
                                       @RequestParam(name = "prefectureFlg", defaultValue = "false")
                                                       boolean prefectureFlg) {

        // htmlに返す値を格納するマップを生成
        Map<String, String> map = new HashMap<>();

        // zipCodeが空orNullの場合
        if (zipCode.isEmpty()) {
            // 未入力エラー
            map.put(KEY_OF_ERROR_MSG, "郵便番号を入力してください。<br />");

        } else {
            // 郵便番号検索
            this.search(zipCode.get(), map, prefectureFlg);
        }

        // Mapを返却（呼び出し元はJSON形式で受領する）
        return map;
    }

    /**
     * 郵便番号検索<br/>
     *
     * @param zipCode       郵便番号
     * @param map           値を格納するマップ
     * @param prefectureFlg 都道府県情報フラグ
     */
    protected void search(String zipCode, Map<String, String> map, boolean prefectureFlg) {
        try {

            // 郵便番号検索サービスのコンポーネントを生成
            ZipCodeAddressGetService zipCodeAddressGetService =
                            ApplicationContextUtility.getBean(ZipCodeAddressGetService.class);

            // 郵便番号検索サービスを実行し、郵便番号住所情報Dtoを取得
            ZipCodeAddressDto zipCodeAddressDto = zipCodeAddressGetService.execute(zipCode);

            // サービスでエラーがない場合は各住所をセット
            if (prefectureFlg) {
                HTypePrefectureType type = EnumTypeUtil.getEnumFromLabel(HTypePrefectureType.class,
                                                                         zipCodeAddressDto.getPrefectureName()
                                                                        );
                map.put(KEY_OF_PRIFECTURE, type.getValue());
            } else {
                map.put(KEY_OF_PRIFECTURE, zipCodeAddressDto.getPrefectureName());
            }
            map.put(KEY_OF_ADDRESS1, zipCodeAddressDto.getCityName());
            map.put(KEY_OF_ADDRESS2, zipCodeAddressDto.getTownName());
            map.put(KEY_OF_ADDRESS3, zipCodeAddressDto.getNumbers());

            // サービスでエラーがある場合はエラーメッセージをセット
        } catch (AppLevelListException e) {
            LOGGER.error("例外処理が発生しました", e);
            // エラーリスト取得
            List<AppLevelException> errorList = e.getErrorList();
            // 文字列を格納する枠を生成
            StringBuilder sb = new StringBuilder();
            // エラーリストがある場合
            if ((errorList != null) && !errorList.isEmpty()) {
                // エラーリスト分、ループ
                for (AppLevelException ae : errorList) {
                    String errorMessage = ae.getAppLevelFacesMessage().getMessage();
                    if (!StringUtils.isEmpty(errorMessage)) {
                        // エラーメッセージを格納
                        sb.append(errorMessage);
                        sb.append("<br />");
                    }
                }
            }
            // エラーメッセージをセット
            map.put(KEY_OF_ERROR_MSG, sb.toString());

        }
    }

}
