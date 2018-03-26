/**
 * 
 */

function frontDisplay() {
	
	$("#divrole").css("display", "none");
	$("#divfield").css("display", "none");
	$("#divqualification").css("display", "none");
	$("#divyearofexperience").css("display", "none");

	$("#expertbutton").click(function() {
		$("#role").val("Expert");
		$("#divfield").css("display", "none");
		$("#divqualification").css("display", "");
		$("#divyearofexperience").css("display", "");

	});

	$("#studentbutton").click(function() {
		$("#role").val("Student");
		$("#divfield").css("display", "");
		$("#divqualification").css("display", "none");
		$("#divyearofexperience").css("display", "none");

	});

	$("#alreadHaveAccountButton").click(function() {
		$("#modalcontent4").css("display", "none");
	});
	
	$("#loginCloseButton").click(function() {
		$("#modalcontent4").css("display", "");
	});
	
	$("#RegisterButton").click(function() {
		$("#modalcontent4").css("display", "");
	});

	$("#RegisterMainButton").click(function() {
		$("#modalcontent4").css("display", "");
	});
	
	$("#forgotAccountButton").click(function() {
		$("#modalcontent1").css("display", "none");
	});
	
	$("#changeAccountButton").click(function() {
		$("#modalcontent1").css("display", "none");
	});
	
	$("#loginButton").click(function() {
		$("#modalcontent1").css("display", "");
	});
	
	$("#forgotCloseButton").click(function() {
		$("#modalcontent1").css("display", "");
	});
	
	$("#changeCloseButton").click(function() {
		$("#modalcontent1").css("display", "");
	});

}


function autotab(current, to) {
	if (current.getAttribute
			&& current.value.length == current.getAttribute("maxlength")) {
		    var f = document.getElementById(to);
	        f.focus();
	}
}