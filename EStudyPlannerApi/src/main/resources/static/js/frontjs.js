/**
 * 
 */

function frontDisplay() {

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