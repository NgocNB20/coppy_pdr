// dialog additionalcharge
$(function () {
    $("#additionalcharge_dialog_btn").click(function () {
        doAdditionalCharge()
    });
})

function doAdditionalCharge() {
    var form = $('form')[0]
    var doAdditionalCharge = new FormData(form)
    $.ajax({
        type: "POST",
        url: pkg_common.getAppComplementUrl() + "/order/detailsupdate/doAdditionalChargeAjax",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        processData: false,
        contentType: false,
        dataType: "json",
        data: doAdditionalCharge,
        timeout: 30000
    })
        .done(function (data) {
            $("#additionalcharge_dialog").dialog({
                modal: true,
                width: 820,
                title: "その他料金の追加",
                buttons: [{
                    text: "追加料金を入力する",
                    click: function() {
                        doUpdateAjax()
                    }
                }
                ],
                close: function (event, ui) {
                    clearPopupMessage()
                }
            });
            additionalChargeIndexWithDetailsUpdateModel(data)
        })
        .fail(function (data) {
            if (data && data.status && data.status === 403) {
                    location.href = '/admin/login/'
            }
            bindingGlobalMessageDetail(data.responseJSON)
            document.getElementById("global-mesage").scrollIntoView();
        })
}

function additionalChargeIndexWithDetailsUpdateModel(detailsUpdateModel) {
    $.ajax({
        type: "POST",
        url: pkg_common.getAppComplementUrl() + "/order/details/additionalcharge",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(detailsUpdateModel),
        timeout: 30000

    }).done(function () {
    })
        .fail(function (data) {
            if (data && data.status && data.status === 403) {
                    location.href = '/admin/login/'
            }
            bindingPopupMessage(data.responseJSON)
        })
}

function doUpdateAjax() {
    let formUpdate = new FormData()
    formUpdate.append("inputAdditionalDetailsName", $("#inputAdditionalDetailsNameInput").val())
    formUpdate.append("inputAdditionalDetailsPrice", $("#inputAdditionalDetailsPriceInput").val())
    $.ajax({
        type: "POST",
        url: pkg_common.getAppComplementUrl() + "/order/details/additionalcharge/doUpdate",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        processData: false,
        contentType: false,
        dataType: "json",
        data: formUpdate,
        timeout: 30000
    }).done(function (data) {
        redirectToDetailsUpdate(data)
    })
        .fail(function (data) {
            if (data && data.status && data.status === 403) {
                location.href = '/admin/login/'
            }
            bindingPopupMessage(data.responseJSON)
        })
}

function redirectToDetailsUpdate(additionalCharge) {
    $.ajax({
        type: "POST",
        url: pkg_common.getAppComplementUrl() + "/order/detailsupdate",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        contentType: "application/json",
        dataType: "text",
        data: JSON.stringify(additionalCharge),
        timeout: 30000
    }).done(function () {
        location.href = "/admin/order/detailsupdate?md=confirm"
    })
        .fail(function (data) {
            if (data && data.status && data.status === 403) {
                location.href = '/admin/login/'
            }
        })
}

function bindingGlobalMessageDetail(error) {
    $("#global-mesage").html('')
    if (error && error.length > 0) {
        error.forEach(e => {
            if (e.field == 'globalMessage') {
                bindingMessage("#global-mesage", null, e.message)
            }
        })
    }
}

function bindingPopupMessage(error) {
    $("#globalMessage").html('')
    $("#inputAdditionalDetailsName").html('')
    $("#inputAdditionalDetailsPrice").html('')
    if (error && error.length > 0) {
        error.forEach(e => {
            if (e.field == 'globalMessage') {
                bindingMessage("#globalMessage",null, e.message)
            }
            if (e.field == 'inputAdditionalDetailsName') {
                bindingMessage("#inputAdditionalDetailsName","inputAdditionalDetailsNameInputError", e.message)
                $('#inputAdditionalDetailsNameInput').addClass("error")
            }
            if (e.field == 'inputAdditionalDetailsPrice') {
                bindingMessage("#inputAdditionalDetailsPrice","inputAdditionalDetailsPriceInputError", e.message)
                $('#inputAdditionalDetailsPriceInput').addClass("error")
            }
        })
    }
}

function bindingMessage(fieldId,divError, message) {
    if(divError){
        let divE = "<div id='" + divError + "'></div>"
        $(fieldId).append(divE)
        $('#' + divError).append("<ul></ul>")
    }else{
        $(fieldId).append("<ul></ul>")
    }
    let itemErr = '<li>' + message + '</li>'
    $(fieldId + " ul").append(itemErr);
    if ($(fieldId + " ul li").length) {
        $(fieldId).attr("style", "display:block")

    }
}

function clearPopupMessage() {
    $("#globalMessage").html('')
    $("#inputAdditionalDetailsName").html('')
    $("#inputAdditionalDetailsPrice").html('')
    $("#globalMessage").attr("style", "display:none")
    $("#inputAdditionalDetailsName").attr("style", "display:none")
    $("#inputAdditionalDetailsPrice").attr("style", "display:none")
    $('#inputAdditionalDetailsNameInput').removeClass("error")
    $('#inputAdditionalDetailsPriceInput').removeClass("error")
}

function clearDetailMessage() {
    $("#global-mesage").attr("style", "display:none")
}