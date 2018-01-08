function resize_footer(screen_width) {
    $("main").height(window.screen.availHeight);
    $('.page-footer').css({
        "width": screen_width + "px",
        "padding": 0
    });
}

function checkNumber() {
    var value = $("#time").val();
    if (isNaN(value)) {
        Materialize.toast("播放时间必须是整数", 5000);
        $("#time").val('10');
        return false;
    } else {
        return true;
    }
}

function toast(msg) {
    if (msg !== '') {
        Materialize.toast(msg, 5000);
    }
}
