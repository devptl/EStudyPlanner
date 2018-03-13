
$(document).ready( function ()
{
    

    $("#addstudymaterial").click(function ()
    {
     var v=$("#inputstudymaterial").val();
        
        
             $("#studyMaterialsList").append("<option value=\""+v+"\" selected=\"selected\" >"+v+"</option>");
        
    
    });
    
    $("#removestudymaterial").click(function ()
    {
     var v=$("#inputstudymaterial").val();
        
        
             $("#studyMaterialsList option:contains("+v+")").remove();
        
    
    });
    
    
    
});