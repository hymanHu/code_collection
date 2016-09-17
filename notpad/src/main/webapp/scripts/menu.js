$(document).ready(
		function() {
			//$('#msg').css('color', 'red');
			$('#username').focus();
//			$('#login_button').click(login);
			
			document.onkeydown = function(event) {
				event = event ? event : (window.event ? window.event : null);
				if (event.keyCode == 13) {
					login();
				}
			};
		}
);
function login() {
//	if ($('#username').val().length <= 0) {
//		$('#username').focus();
//		$('#msg').html('User name is required!');
//		return;
//	}
//	if ($('#password').val().length <= 0) {
//		$('#password').focus();
//		$('#msg').html('Password is required!');
//		return;
//	}
	$('#msg').html('');
	$('#loginForm').submit();
}


