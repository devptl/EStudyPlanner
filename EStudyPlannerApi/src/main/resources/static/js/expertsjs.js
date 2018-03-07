
$(document).ready( function ()
{
    
    $("#addminorcourse").click(function ()
    {
     var v=$("#inputminorcourse").val();
        
        
             $("#minorlist").append(" <li class=\"list-group-item\">"+v+"</li>");
        
    
    });
    
    $("#removeminorcourse").click(function ()
    {
     var v=$("#inputminorcourse").val();
        
        
             $("#minorlist li:contains("+v+")").remove();
        
    
    });
    
    $("#addstudymaterial").click(function ()
    {
     var v=$("#inputstudymaterial").val();
        
        
             $("#studymaterial").append(" <li class=\"list-group-item\">"+v+"</li>");
        
    
    });
    
    $("#removestudymaterial").click(function ()
    {
     var v=$("#inputstudymaterial").val();
        
        
             $("#studymaterial li:contains("+v+")").remove();
        
    
    });
    
    
    
});