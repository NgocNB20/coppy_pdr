// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNonConsultationDay;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNonConsultationType;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * PDR#11 08_データ連携（顧客情報）休診曜日判定用Utilityクラス
 *
 * @author s.kume
 * @version $Revision:$
 */
@Component
public class NonConsultationDayUtility {

    /**
     * 休診曜日文字列長
     */
    private static final int NON_CONSULTATION_LENGTH = 8;

    /**
     * 休診曜日null時の文字列
     */
    public static final String NON_CONSULTATION_NULL_STRING = "000000000";

    /**
     * 休診曜日を判定して文字列を生成<br/>
     * 休診曜日文字列から画面表示用文字列(XX曜XX休診)を生成する<br/>
     *
     * @param nonConsultation 休診曜日
     * @return 画面表示用休診曜日文字列
     */
    public String getNonConsultationDay(String nonConsultation) {

        // nullチェック
        if (StringUtil.isEmpty(nonConsultation)) {
            return StringUtils.EMPTY;
        }
        // 表示用休診曜日文字列
        StringBuilder dispNonConsultation = new StringBuilder();

        // 休診曜日ラベルマップ
        Map<String, String> nonConsultationDay = EnumTypeUtil.getEnumMap(HTypeNonConsultationDay.class);

        // 文字列の末尾を見て値が「無休」であれば無休を表示
        if (nonConsultation.endsWith(HTypeNonConsultationType.NOHOLIDAY.getValue())) {
            dispNonConsultation.append(HTypeNonConsultationType.NOHOLIDAY.getLabel());
            return dispNonConsultation.toString();
        }
        // 文字列の末尾以外を順番にチェック。終日営業以外を表示用文字列に格納
        for (Integer digit = 0; digit < NON_CONSULTATION_LENGTH; digit++) {

            // チェックする値
            String targetNonConsultation = String.valueOf(nonConsultation.charAt(digit));

            // 格納用文字列が空以外、かつチェックする値が終日営業以外ならば読点付与
            if (!"0".equals(targetNonConsultation) && dispNonConsultation.length() != 0) {
                dispNonConsultation.append("、");
            }
            if (HTypeNonConsultationType.AM.getValue().equals(targetNonConsultation)) {
                dispNonConsultation.append(nonConsultationDay.get(String.valueOf(digit)));
                dispNonConsultation.append(HTypeNonConsultationType.AM.getLabel());
            }
            if (HTypeNonConsultationType.PM.getValue().equals(targetNonConsultation)) {
                dispNonConsultation.append(nonConsultationDay.get(String.valueOf(digit)));
                dispNonConsultation.append(HTypeNonConsultationType.PM.getLabel());
            }
            if (HTypeNonConsultationType.ALLDAY.getValue().equals(targetNonConsultation)) {
                dispNonConsultation.append(nonConsultationDay.get(String.valueOf(digit)));
                dispNonConsultation.append(HTypeNonConsultationType.ALLDAY.getLabel());
            }
        }
        return dispNonConsultation.toString();
    }

    /**
     * 休診曜日文字列から休診表のAM欄用のチェック配列を返す<br/>
     * 文字列内が1または3である場合にtrueとする<br/>
     *
     * @param nonConsultationDay 休診曜日文字列
     * @return AM欄チェック配列
     */
    public boolean[] setAmNonConsultationCheckList(String nonConsultationDay) {

        // nullチェック
        if (StringUtil.isEmpty(nonConsultationDay)) {
            return new boolean[NON_CONSULTATION_LENGTH];
        }

        // 返却用配列
        boolean[] amCheckArray = new boolean[NON_CONSULTATION_LENGTH];

        // 文字列を左から1文字ずつチェックする
        for (Integer checkNum = 0; checkNum < NON_CONSULTATION_LENGTH; checkNum++) {

            // チェックする文字
            String targetNonConsultation = String.valueOf(nonConsultationDay.charAt(checkNum));

            // チェックした文字列が「AM休診」、または「終日休診」の場合チェック
            if (HTypeNonConsultationType.AM.getValue().equals(targetNonConsultation)
                || HTypeNonConsultationType.ALLDAY.getValue().equals(targetNonConsultation)) {
                amCheckArray[checkNum] = true;
            } else {
                amCheckArray[checkNum] = false;
            }
        }
        return amCheckArray;
    }

    /**
     * 休診曜日文字列から休診表のPM欄用のチェック配列を返す<br/>
     * 文字列内が2または3である場合にtrueとする<br/>
     *
     * @param nonConsultationDay 休診曜日文字列
     * @return PM欄用チェック配列
     */
    public boolean[] setPmNonConsultationCheckList(String nonConsultationDay) {

        // nullチェック
        if (StringUtil.isEmpty(nonConsultationDay)) {
            return new boolean[NON_CONSULTATION_LENGTH];
        }
        // 返却用配列
        boolean[] pmCheckArray = new boolean[NON_CONSULTATION_LENGTH];

        // 文字列を左から1文字ずつチェックする
        for (Integer checkNum = 0; checkNum < NON_CONSULTATION_LENGTH; checkNum++) {

            // チェックする文字
            String targetNonConsultation = String.valueOf(nonConsultationDay.charAt(checkNum));

            // チェックした文字列が「PM休診」、または「終日休診」の場合チェック
            if (HTypeNonConsultationType.PM.getValue().equals(targetNonConsultation)
                || HTypeNonConsultationType.ALLDAY.getValue().equals(targetNonConsultation)) {
                pmCheckArray[checkNum] = true;
            } else {
                pmCheckArray[checkNum] = false;
            }
        }
        return pmCheckArray;
    }

    /**
     * 休診区分が無休であるかの判定メソッド<br/>
     * 引数の文字列の最後をチェックし、4である場合のみtrue<br/>
     *
     * @param nonConsultationDay 休診曜日
     * @return true or false
     */
    public boolean setNoHolidayNonConsultation(String nonConsultationDay) {

        // nullチェック
        if (StringUtil.isEmpty(nonConsultationDay)) {
            return false;
        }

        // 無休の場合(最後の文字チェック)
        if (nonConsultationDay.endsWith(HTypeNonConsultationType.NOHOLIDAY.getValue())) {
            return true;
        }

        return false;
    }

    /**
     * 引数のAM、PM用配列から休診曜日文字列を生成<br/>
     * 配列を順にチェックしていく。AMのみtrueであれば1(AM休診)を格納。PMのみtrueであれば2(PM休診)を格納。<br/>
     * AM、PM両方がtrueならば3(終日休診)を格納。それ以外は0(終日営業)を格納<br/>
     * 配列の最後のみtrueであるならば無休を表す休診曜日文字列(000000004)を返却<br/>
     *
     * @param amCheck AM用チェックリスト
     * @param pmCheck PM用チェックリスト
     * @return 休診曜日文字列
     */
    public String getNonConsultationDayString(boolean[] amCheck, boolean[] pmCheck) {

        // 登録用休診曜日文字列
        StringBuilder nonConsultationDay = new StringBuilder();
        // 配列の最後をチェック
        if (amCheck[amCheck.length - 1] && pmCheck[pmCheck.length - 1]) {
            // 無休を表す文字列を格納
            nonConsultationDay.append("000000004");
            return nonConsultationDay.toString();
        }
        // 配列を順番にチェック
        for (int digitNum = 0; digitNum < amCheck.length; digitNum++) {

            // 休診区分を表す文字
            int nonConsultationType = 0;

            // AMチェック
            if (amCheck[digitNum]) {
                nonConsultationType += 1;
            }
            // PMチェック
            if (pmCheck[digitNum]) {
                nonConsultationType += 2;
            }
            // 休診区分を表す文字を格納
            nonConsultationDay.append(nonConsultationType);
        }

        return nonConsultationDay.toString();
    }
}
// PDR Migrate Customization to here
