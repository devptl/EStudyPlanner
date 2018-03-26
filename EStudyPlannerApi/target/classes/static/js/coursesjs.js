/**
 * 
 */

function courseSetting(focusId)
{
	
	document.getElementById(focusId).focus();
 

    $("#addstudymaterial").click(function ()
    {
     var v=$("#inputstudymaterial").val();
     var x=$("#inputstudymaterial option[value='"+v+"']").text();
        
        
             $("#studyMaterialsList").append("<option value=\""+v+"\" selected=\"selected\" >"+x+"</option>");
        
    
    });
    
    $("#removestudymaterial").click(function ()
    {
     var v=$("#inputstudymaterial").val();
     var x=$("#inputstudymaterial option[value='"+v+"']").text()
        
        
             $("#studyMaterialsList option:contains("+x+")").remove();
        
    
    });
    
    
    
}