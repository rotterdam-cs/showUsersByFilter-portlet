//@author Prj.M@x <pablo.rendon@rotterdam-cs.com>

function setSelectOptions(selectId, options) {
	jQuery_1_7_1.each(options, function(val, text) {
		setSelectOption(selectId, val, text);
	});
}

function clearSelectOption(selectId) {
	jQuery_1_7_1("#" + selectId + " .added").remove();
}

function setSelectOption(selectId, val, text) {
	var mySelect = jQuery_1_7_1('#' + selectId);
	mySelect.append(
        jQuery_1_7_1('<option class="added"></option>').val(val).html(text)
    );
}

function showError(message){	
	if (jQuery_1_7_1("#" + namespace + "alert-content").is(":visible")) {
		jQuery_1_7_1("#" + namespace + "alert-content").append("<br /><br />" + message);
	} else {
		jQuery_1_7_1("#" + namespace + "alert-content").html(message);
	}
    jQuery_1_7_1(".alert-success").hide();
    jQuery_1_7_1(".alert-error").fadeIn();
}

function showInfo(message){
	jQuery_1_7_1(".alert-error").hide();
	if (jQuery_1_7_1("#" + namespace + "info-content").is(":visible")) {
		jQuery_1_7_1("#" + namespace + "info-content").append("<br /><br />" + message);
	} else {
		jQuery_1_7_1("#" + namespace + "info-content").html(message);
	}
    jQuery_1_7_1(".alert-success").fadeIn();
}

function getLocallizedKey(fkey) {
	var message=defaultErrorMessage;
	try {
		jQuery_1_7_1.each(messages.messages, function(key, value) {
			if (value.key == fkey) {
				message = value.value;
			}			
		});
	}catch(e){
		console.log("global variable 'messages' is not defined");
	}
	return message;	       
}

function inArray(needle, haystack) {
    var length = haystack.length;
    for(var i = 0; i < length; i++) {
        if(haystack[i] == needle) return true;
    }
    return false;
}



/*
 * Obscuscate Email Functions
 */

function addEmail(address, containerId){
	address = address.replace("[at]", "@");
	jQuery_1_7_1('#' + containerId).html(munge(address,containerId));
}

rnd.today=new Date();
rnd.seed=rnd.today.getTime();

function rnd() {
	rnd.seed = (rnd.seed*9301+49297) % 233280;
	return rnd.seed/(233280.0);
};

function rand(number) {
	return Math.ceil(rnd()*number);
};

function munge(address,containerId) {	
	address = address.toLowerCase();
	coded = "";

	linktext="\"+link+\"";

	unmixedkey = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	inprogresskey = unmixedkey;
	mixedkey="";
	unshuffled = 62;
	for (var i = 0; i <= 62; i++) {
		ranpos = rand(unshuffled) - 1;
		nextchar = inprogresskey.charAt(ranpos);
		mixedkey += nextchar;
		before = inprogresskey.substring(0,ranpos);
		after = inprogresskey.substring(ranpos+1,unshuffled);
		inprogresskey = before + after;
		unshuffled -= 1;
	}
	cipher = mixedkey;

	shift = address.length;

    txt =	'<script type=\"text/javascript\" language=\"javascript\">\n' +
        	'<!-'+'-\n';

    for (var j=0; j<address.length; j++) {
			if (cipher.indexOf(address.charAt(j)) == -1 ) {
				chr = address.charAt(j);
				coded += address.charAt(j);
			}
			else {
				chr = (cipher.indexOf(address.charAt(j)) + shift) % cipher.length;
				coded += cipher.charAt(chr);
			}
    }

	txt +=	'{ coded = \"' + coded + '\"\n' +
		'  key = "'+cipher+'"\n'+
		'  shift=coded.length\n'+
		'  link=""\n'+
		'  for (i=0; i<coded.length; i++) {\n' +
		'    if (key.indexOf(coded.charAt(i))==-1) {\n' +
		'      ltr = coded.charAt(i)\n' +
		'      link += (ltr)\n' +
		'    }\n' +
		'    else {     \n'+
		'      ltr = (key.indexOf(coded.charAt(i))-shift+key.length) % key.length\n'+
		'      link += (key.charAt(ltr))\n'+
		'    }\n'+
		'  }\n'+
		'jQuery_1_7_1("#' + containerId +'").html("<a href=\'mailto:"+link+"\'>'+linktext+'</a>")\n' +
		'}\n'+
       	'//-'+'->\n' +
       	'<' + '/script><noscript>Sorry, you need Javascript on to email us.' +
		'<'+'/noscript>\n'
        return txt;
    }

