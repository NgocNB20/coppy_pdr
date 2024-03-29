<!--2023-renew No16-1 from here-->
function getCookie(name) {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for (const element of ca) {
        let c = element;
        while (c.startsWith(' ')) c = c.substring(1, c.length);
        if (c.startsWith(nameEQ)) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function setCookie(name, value, days) {
    let expires = "";
    if (days) {
        const date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/";
}

$(document).ready(function () {
    let confirm = getCookie('medicalStaffFlag');
    if (confirm !== 'ON') {
        $('.p-top__authentication').show();
        $('.p-top__authentication-button-yes').on('click', function () {
            setCookie('medicalStaffFlag', 'ON', 365);
            $('.p-top__authentication').hide();
        });
    }
});
<!--2023-renew No16-1 to here-->