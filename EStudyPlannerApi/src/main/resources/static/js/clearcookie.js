/**
 * 
 */

$(document).ready(function() {

	function deleteAllCookies() {
		var cookies = document.cookie.split(";");

		for (var i = 0; i < cookies.length; i++) {
			var cookie = cookies[i];
			var eqPos = cookie.indexOf("=");
			var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
			document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
		}
	}

	$("#loginButton").on("click", function() {
		deleteAllCookies();
		console.log("cleared cookies");

	});

	console.log("ready!");
});
