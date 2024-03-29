/*PDR Migrate Customization from here*/
/*
 * 会員検索：会員詳細修正画面で使用する。
 * 承認状態に応じて画面の活性・非活性を操作する。
 *
 */
function selectDisabled(){
	if (document.getElementById("approveStatusFlag").value == "true") {
		var readOnlyElements = document.getElementsByClassName( "readOnlyField" );
		var cnt = readOnlyElements.length;
		for(var index=0; index < cnt; index++) {
			readOnlyElements[index].readOnly = true;
		}
		document.getElementById("approveStatusSubstitute").value=document.getElementById("approveStatus").value;
		document.getElementById("approveStatus").disabled = true;
		document.getElementById("sendNoticeMailFlagSubstitute").value=document.getElementById("sendNoticeMailFlag").value;
		document.getElementById("sendNoticeMailFlag").disabled = true;
		document.getElementById("businessTypeSubstitute").value=document.getElementById("businessType").value;
		document.getElementById("businessType").disabled = true;
		document.getElementById("confDocumentTypeSubstitute").value=document.getElementById("confDocumentType").value;
		document.getElementById("confDocumentType").disabled = true;
		document.getElementById("amSunNonConsultationSubstitute").value=document.getElementById("amSunNonConsultation").value;
		document.getElementById("amSunNonConsultation").disabled = true;
		document.getElementById("amMonNonConsultationSubstitute").value=document.getElementById("amMonNonConsultation").value;
		document.getElementById("amMonNonConsultation").disabled = true;
		document.getElementById("amTueNonConsultationSubstitute").value=document.getElementById("amTueNonConsultation").value;
		document.getElementById("amTueNonConsultation").disabled = true;
		document.getElementById("amWedNonConsultationSubstitute").value=document.getElementById("amWedNonConsultation").value;
		document.getElementById("amWedNonConsultation").disabled = true;
		document.getElementById("amThuNonConsultationSubstitute").value=document.getElementById("amThuNonConsultation").value;
		document.getElementById("amThuNonConsultation").disabled = true;
		document.getElementById("amFriNonConsultationSubstitute").value=document.getElementById("amFriNonConsultation").value;
		document.getElementById("amFriNonConsultation").disabled = true;
		document.getElementById("amSatNonConsultationSubstitute").value=document.getElementById("amSatNonConsultation").value;
		document.getElementById("amSatNonConsultation").disabled = true;
		document.getElementById("amHolidayNonConsultationSubstitute").value=document.getElementById("amHolidayNonConsultation").value;
		document.getElementById("amHolidayNonConsultation").disabled = true;
		document.getElementById("allConsultationSubstitute").value=document.getElementById("allConsultation").value;
		document.getElementById("allConsultation").disabled = true;
		document.getElementById("pmSunNonConsultationSubstitute").value=document.getElementById("pmSunNonConsultation").value;
		document.getElementById("pmSunNonConsultation").disabled = true;
		document.getElementById("pmMonNonConsultationSubstitute").value=document.getElementById("pmMonNonConsultation").value;
		document.getElementById("pmMonNonConsultation").disabled = true;
		document.getElementById("pmTueNonConsultationSubstitute").value=document.getElementById("pmTueNonConsultation").value;
		document.getElementById("pmTueNonConsultation").disabled = true;
		document.getElementById("pmWedNonConsultationSubstitute").value=document.getElementById("pmWedNonConsultation").value;
		document.getElementById("pmWedNonConsultation").disabled = true;
		document.getElementById("pmThuNonConsultationSubstitute").value=document.getElementById("pmThuNonConsultation").value;
		document.getElementById("pmThuNonConsultation").disabled = true;
		document.getElementById("pmFriNonConsultationSubstitute").value=document.getElementById("pmFriNonConsultation").value;
		document.getElementById("pmFriNonConsultation").disabled = true;
		document.getElementById("pmSatNonConsultationSubstitute").value=document.getElementById("pmSatNonConsultation").value;
		document.getElementById("pmSatNonConsultation").disabled = true;
		document.getElementById("pmHolidayNonConsultationSubstitute").value=document.getElementById("pmHolidayNonConsultation").value;
		document.getElementById("pmHolidayNonConsultation").disabled = true;
		document.getElementById("drugSalesTypeSubstitute").value=document.getElementById("drugSalesType").value;
		document.getElementById("drugSalesType").disabled = true;
		document.getElementById("medicalEquipmentSalesTypeSubstitute").value=document.getElementById("medicalEquipmentSalesType").value;
		document.getElementById("medicalEquipmentSalesType").disabled = true;
		document.getElementById("dentalMonopolySalesTypeSubstitute").value=document.getElementById("dentalMonopolySalesType").value;
		document.getElementById("dentalMonopolySalesType").disabled = true;
		document.getElementById("creditPaymentUseFlagSubstitute").value=document.getElementById("creditPaymentUseFlag").value;
		document.getElementById("creditPaymentUseFlag").disabled = true;
		document.getElementById("transferPaymentUseFlagSubstitute").value=document.getElementById("transferPaymentUseFlag").value;
		document.getElementById("transferPaymentUseFlag").disabled = true;
		document.getElementById("cashDeliveryUseFlagSubstitute").value=document.getElementById("cashDeliveryUseFlag").value;
		document.getElementById("cashDeliveryUseFlag").disabled = true;
		document.getElementById("directDebitUseFlagSubstitute").value=document.getElementById("directDebitUseFlag").value;
		document.getElementById("directDebitUseFlag").disabled = true;
		document.getElementById("monthlyPayUseFlagSubstitute").value=document.getElementById("monthlyPayUseFlag").value;
		document.getElementById("monthlyPayUseFlag").disabled = true;
		document.getElementById("memberListTypeSubstitute").value=document.getElementById("memberListType").value;
		document.getElementById("memberListType").disabled = true;
		document.getElementById("accountingTypeSubstitute").value=document.getElementById("accountingType").value;
		document.getElementById("accountingType").disabled = true;
		document.getElementById("onlineLoginAdvisabilitySubstitute").value=document.getElementById("onlineLoginAdvisability").value;
		document.getElementById("onlineLoginAdvisability").disabled = true;
		document.getElementById("sendMailSubstitute").value=document.getElementById("sendMail").value;
		document.getElementById("sendMail").disabled = true;
		document.getElementById("sendFaxSubstitute").value=document.getElementById("sendFax").value;
		document.getElementById("sendFax").disabled = true;
		document.getElementById("sendDirectMailSubstitute").value=document.getElementById("sendDirectMail").value;
		document.getElementById("sendDirectMail").disabled = true;
		document.getElementById("metalPermitFlagSubstitute").value=document.getElementById("metalPermit").value;
		document.getElementById("metalPermit").disabled = true;
         // 2023-renew No79 from here
		document.getElementById("orderCompletePermitFlagSubstitute").value=document.getElementById("orderCompletePermit").value;
		document.getElementById("orderCompletePermit").disabled = true;
		document.getElementById("deliveryCompletePermitFlagSubstitute").value=document.getElementById("deliveryCompletePermit").value;
		document.getElementById("deliveryCompletePermit").disabled = true;
        // 2023-renew No79 to here
		document.getElementById("medicalTreatment1Substitute").value=document.getElementById("medicalTreatment1").value;
		document.getElementById("medicalTreatment1").disabled = true;
		document.getElementById("medicalTreatment2Substitute").value=document.getElementById("medicalTreatment2").value;
		document.getElementById("medicalTreatment2").disabled = true;
		document.getElementById("medicalTreatment3Substitute").value=document.getElementById("medicalTreatment3").value;
		document.getElementById("medicalTreatment3").disabled = true;
		document.getElementById("medicalTreatment4Substitute").value=document.getElementById("medicalTreatment4").value;
		document.getElementById("medicalTreatment4").disabled = true;
		document.getElementById("medicalTreatment5Substitute").value=document.getElementById("medicalTreatment5").value;
		document.getElementById("medicalTreatment5").disabled = true;
		document.getElementById("medicalTreatment6Substitute").value=document.getElementById("medicalTreatment6").value;
		document.getElementById("medicalTreatment6").disabled = true;
		document.getElementById("medicalTreatment7Substitute").value=document.getElementById("medicalTreatment7").value;
		document.getElementById("medicalTreatment7").disabled = true;
		document.getElementById("medicalTreatment8Substitute").value=document.getElementById("medicalTreatment8").value;
		document.getElementById("medicalTreatment8").disabled = true;
		document.getElementById("medicalTreatment9Substitute").value=document.getElementById("medicalTreatment9").value;
		document.getElementById("medicalTreatment9").disabled = true;
		document.getElementById("medicalTreatment10Substitute").value=document.getElementById("medicalTreatment10").value;
		document.getElementById("medicalTreatment10").disabled = true;
	}
}

/*
 * 会員検索：会員詳細修正画面で使用する。
 * 画面の非活性の値をセッションに保存する。
 *
 */
function reverseDisabledValue(){
	if (document.getElementById("approveStatusFlag").value == "true") {
		document.getElementById("approveStatus").disabled = false;
		document.getElementById("approveStatus").value=document.getElementById("approveStatusSubstitute").value;
		document.getElementById("sendNoticeMailFlag").disabled = false;
		document.getElementById("sendNoticeMailFlag").value=document.getElementById("sendNoticeMailFlagSubstitute").value;
		document.getElementById("businessType").disabled = false;
		document.getElementById("businessType").value=document.getElementById("businessTypeSubstitute").value;
		document.getElementById("confDocumentType").disabled = false;
		document.getElementById("confDocumentType").value=document.getElementById("confDocumentTypeSubstitute").value;
		document.getElementById("amSunNonConsultation").disabled = false;
		document.getElementById("amSunNonConsultation").value=document.getElementById("amSunNonConsultationSubstitute").value;
		document.getElementById("amMonNonConsultation").disabled = false;
		document.getElementById("amMonNonConsultation").value=document.getElementById("amMonNonConsultationSubstitute").value;
		document.getElementById("amTueNonConsultation").disabled = false;
		document.getElementById("amTueNonConsultation").value=document.getElementById("amTueNonConsultationSubstitute").value;
		document.getElementById("amWedNonConsultation").disabled = false;
		document.getElementById("amWedNonConsultation").value=document.getElementById("amWedNonConsultationSubstitute").value;
		document.getElementById("amThuNonConsultation").disabled = false;
		document.getElementById("amThuNonConsultation").value=document.getElementById("amThuNonConsultationSubstitute").value;
		document.getElementById("amFriNonConsultation").disabled = false;
		document.getElementById("amFriNonConsultation").value=document.getElementById("amFriNonConsultationSubstitute").value;
		document.getElementById("amSatNonConsultation").disabled = false;
		document.getElementById("amSatNonConsultation").value=document.getElementById("amSatNonConsultationSubstitute").value;
		document.getElementById("amHolidayNonConsultation").disabled = false;
		document.getElementById("amHolidayNonConsultation").value=document.getElementById("amHolidayNonConsultationSubstitute").value;
		document.getElementById("allConsultation").disabled = false;
		document.getElementById("allConsultation").value=document.getElementById("allConsultationSubstitute").value;
		document.getElementById("pmSunNonConsultation").disabled = false;
		document.getElementById("pmSunNonConsultation").value=document.getElementById("pmSunNonConsultationSubstitute").value;
		document.getElementById("pmMonNonConsultation").disabled = false;
		document.getElementById("pmMonNonConsultation").value=document.getElementById("pmMonNonConsultationSubstitute").value;
		document.getElementById("pmTueNonConsultation").disabled = false;
		document.getElementById("pmTueNonConsultation").value=document.getElementById("pmTueNonConsultationSubstitute").value;
		document.getElementById("pmWedNonConsultation").disabled = false;
		document.getElementById("pmWedNonConsultation").value=document.getElementById("pmWedNonConsultationSubstitute").value;
		document.getElementById("pmThuNonConsultation").disabled = false;
		document.getElementById("pmThuNonConsultation").value=document.getElementById("pmThuNonConsultationSubstitute").value;
		document.getElementById("pmFriNonConsultation").disabled = false;
		document.getElementById("pmFriNonConsultation").value=document.getElementById("pmFriNonConsultationSubstitute").value;
		document.getElementById("pmSatNonConsultation").disabled = false;
		document.getElementById("pmSatNonConsultation").value=document.getElementById("pmSatNonConsultationSubstitute").value;
		document.getElementById("pmHolidayNonConsultation").disabled = false;
		document.getElementById("pmHolidayNonConsultation").value=document.getElementById("pmHolidayNonConsultationSubstitute").value;
		document.getElementById("drugSalesType").disabled = false;
		document.getElementById("drugSalesType").value=document.getElementById("drugSalesTypeSubstitute").value;
		document.getElementById("medicalEquipmentSalesType").disabled = false;
		document.getElementById("medicalEquipmentSalesType").value=document.getElementById("medicalEquipmentSalesTypeSubstitute").value;
		document.getElementById("dentalMonopolySalesType").disabled = false;
		document.getElementById("dentalMonopolySalesType").value=document.getElementById("dentalMonopolySalesTypeSubstitute").value;
		document.getElementById("creditPaymentUseFlag").disabled = false;
		document.getElementById("creditPaymentUseFlag").value=document.getElementById("creditPaymentUseFlagSubstitute").value;
		document.getElementById("transferPaymentUseFlag").disabled = false;
		document.getElementById("transferPaymentUseFlag").value=document.getElementById("transferPaymentUseFlagSubstitute").value;
		document.getElementById("cashDeliveryUseFlag").disabled = false;
		document.getElementById("cashDeliveryUseFlag").value=document.getElementById("cashDeliveryUseFlagSubstitute").value;
		document.getElementById("directDebitUseFlag").disabled = false;
		document.getElementById("directDebitUseFlag").value=document.getElementById("directDebitUseFlagSubstitute").value;
		document.getElementById("monthlyPayUseFlag").disabled = false;
		document.getElementById("monthlyPayUseFlag").value=document.getElementById("monthlyPayUseFlagSubstitute").value;
		document.getElementById("memberListType").disabled = false;
		document.getElementById("memberListType").value=document.getElementById("memberListTypeSubstitute").value;
		document.getElementById("accountingType").disabled = false;
		document.getElementById("accountingType").value=document.getElementById("accountingTypeSubstitute").value;
		document.getElementById("onlineLoginAdvisability").disabled = false;
		document.getElementById("onlineLoginAdvisability").value=document.getElementById("onlineLoginAdvisabilitySubstitute").value;
		document.getElementById("sendMail").disabled = false;
		document.getElementById("sendMail").value=document.getElementById("sendMailSubstitute").value;
		document.getElementById("sendFax").disabled = false;
		document.getElementById("sendFax").value=document.getElementById("sendFaxSubstitute").value;
		document.getElementById("sendDirectMail").disabled = false;
		document.getElementById("sendDirectMail").value=document.getElementById("sendDirectMailSubstitute").value;
		document.getElementById("metalPermit").disabled = false;
		document.getElementById("metalPermit").value=document.getElementById("metalPermitFlagSubstitute").value;
        // 2023-renew No79 from here
		document.getElementById("orderCompletePermit").disabled = false;
		document.getElementById("orderCompletePermit").value=document.getElementById("orderCompletePermitFlagSubstitute").value;
		document.getElementById("deliveryCompletePermit").disabled = false;
		document.getElementById("deliveryCompletePermit").value=document.getElementById("deliveryCompletePermitFlagSubstitute").value;
        // 2023-renew No79 to here
		document.getElementById("medicalTreatment1").disabled = false;
		document.getElementById("medicalTreatment1").value=document.getElementById("medicalTreatment1Substitute").value;
		document.getElementById("medicalTreatment2").disabled = false;
		document.getElementById("medicalTreatment2").value=document.getElementById("medicalTreatment2Substitute").value;
		document.getElementById("medicalTreatment3").disabled = false;
		document.getElementById("medicalTreatment3").value=document.getElementById("medicalTreatment3Substitute").value;
		document.getElementById("medicalTreatment4").disabled = false;
		document.getElementById("medicalTreatment4").value=document.getElementById("medicalTreatment4Substitute").value;
		document.getElementById("medicalTreatment5").disabled = false;
		document.getElementById("medicalTreatment5").value=document.getElementById("medicalTreatment5Substitute").value;
		document.getElementById("medicalTreatment6").disabled = false;
		document.getElementById("medicalTreatment6").value=document.getElementById("medicalTreatment6Substitute").value;
		document.getElementById("medicalTreatment7").disabled = false;
		document.getElementById("medicalTreatment7").value=document.getElementById("medicalTreatment7Substitute").value;
		document.getElementById("medicalTreatment8").disabled = false;
		document.getElementById("medicalTreatment8").value=document.getElementById("medicalTreatment8Substitute").value;
		document.getElementById("medicalTreatment9").disabled = false;
		document.getElementById("medicalTreatment9").value=document.getElementById("medicalTreatment9Substitute").value;
		document.getElementById("medicalTreatment10").disabled = false;
		document.getElementById("medicalTreatment10").value=document.getElementById("medicalTreatment10Substitute").value;
	}
}
/*PDR Migrate Customization to here*/