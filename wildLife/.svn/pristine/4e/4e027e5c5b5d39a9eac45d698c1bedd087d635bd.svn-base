$(function(){ 
    $.getScript("assets/scripts/pivot/master/highlight.min.js", function(){
        $("head").append(
        $('<link rel="stylesheet" type="text/css" href="assets/css/pivot/master/color-brewer.min.css">'),
        $("<link href='assets/css/pivot/master/css.css' rel='stylesheet' type='text/css'>")
        );
        $("body script").each(function(){
            $("#output").before($("<pre>").append(
                $("<code class='javascript'>").text($(this).text())
                .css({"font-family": "Source Code Pro"})
            ));
            $('pre code').each(function(i, block) {
                hljs.highlightBlock(block);
              });        
        });
    });
});