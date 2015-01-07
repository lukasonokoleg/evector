$(document).ready(function () {
    $(".tab_content").hide();
//    $(".tab_content:first").show();

    $(".gwt-TabBarItem").click(function () {
        $(".gwt-TabBarItem").removeClass("active");
        $(this).addClass("active");
        $(".tab_content").hide();
        $("#" + $(this).attr("rel")).fadeIn();
    });

    $(window.location.hash).click();
});