jQuery_1_7_1(document).ready(function (jQuery_1_7_1) {
    /*
     * Team Portlet javascript actions
    **/
   console.log('start');
    if (jQuery_1_7_1('div#team-portlet').length > 0) {
        
        // Tags Meta Filter
        var memberLIs = jQuery_1_7_1(".team > li"),
        tagFilters  = jQuery_1_7_1("#tag-filter a");
        
        tagFilters.click(function() {
            if ( jQuery_1_7_1(this).attr("id") == "all-business-units") {
            	tagFilters.removeClass("active");
                memberLIs.addClass("active");
                memberLIs.show();
            } else {
	        	// If already active, remove the filter
	            if (jQuery_1_7_1(this).hasClass("active")) {
	                tagFilters.removeClass("active");
	                memberLIs.addClass("active");
	                memberLIs.show();
	            }
	            // Otherwise, only show matching tags
	            else {
	                var tagName = "." + jQuery_1_7_1(this).attr("id");
	                memberLIs.hide();
	                tagFilters.removeClass("active");
	                memberLIs.removeClass("active");
	                jQuery_1_7_1(this).addClass("active");
	                memberLIs.find(tagName).parents(".member").addClass("active").show();
	            }
            }
        });

        // Picture mouseovers
        memberLIs.find(".picture").mouseenter(
            function () {
                var thisEmployee = jQuery_1_7_1(this).parent();
                memberLIs.find('.info').hide();
                thisEmployee.find('.info').fadeIn("fast");
            }
        ).mouseleave(
            function () {
                var thisEmployee = jQuery_1_7_1(this).parent();
                memberLIs.find(".info").hide();
                jQuery_1_7_1(".team > li, #tag-filter").animate({
                    opacity: 1
                }, 400);
            }
        );
    }
}); // End Document.ready