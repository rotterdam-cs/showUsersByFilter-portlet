//@author Prj.M@x <pablo.rendon@rotterdam-cs.com>

//General Response Handler
function getResponse(response, postdata){
    return getResponseTextInfo(response.responseText);      
}

function getResponseTextInfo(responseText){    
    var success = false;  
    var msg = "";
    if(responseText != "") {  
        var obj = jQuery_1_7_1.parseJSON(responseText); 
        success = (obj.success == "true" || obj.success === true)?true:false;
        msg = obj.message;
        body = obj.body;
    }
    return [success, msg, body];
}