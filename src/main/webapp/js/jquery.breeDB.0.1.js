/*!
 * BreeDB jQuery JavaScript Library v0.1
 *
 * Copyright 2010, Richard Finkers
 *
 * Date: Wed Jan 20 13:46
 */

// unblock when ajax activity stops
$(document).ajaxStop($.unblockUI);

$(document).ready(function() {
    $('#validateSheet').click(function() {
        $.blockUI({
            message: '<h2><img src="../images/busy.gif" /> Your datasheets are being validated. Please wait a moment.</h2>',
            css: {
                border: '2px solid #C71400'
            }
        });
    });
});


$('.testSubject').button({});
$('.buttonsetSubject').buttonset()





