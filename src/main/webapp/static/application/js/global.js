function resize_footer(screen_width) {
    $('.page-footer').css({
        "width": screen_width + "px",
        "height": 40 + 'px',
        "padding": 0
    });
}

function checkNumber() {
    var value = $("#time").val();
    if (isNaN(value)) {
        Materialize.toast("播放时间必须是整数", 5000);
        $("#time").val(null);
        return false;
    } else {
        return true;
    }
}
