/*!
 * BreeDB jQuery JavaScript Library v0.1
 *
 * Copyright 2010, Richard Finkers
 *
 * Date: Wed Jan 20 13:46
 */

// unblock when ajax activity stops
$(document).ajaxStop($.unblockUI);

function test() {
    $.ajax({
        url: 'wait.jsp',
        cache: false
    });
}

// Just a moment message
$(document).ready(function() {
    $('#submitMoment').click(function() {
        $.blockUI({
            message: '<h2><img src="/images/busy.gif" /> Just a moment...</h2>',
            css: {
                border: '2px solid #C71400'
            }
        });
    //test();
    });
});

//
$(document).ready(function() {
    $('#submitProcessing').click(function() {
        $.blockUI({
            message: '<h2><img src="/images/busy.gif" /> The results of your analysis is being computed. Please be patient.</h2>',
            css: {
                border: '2px solid #C71400'
            }
        });
    //test();
    });
});
