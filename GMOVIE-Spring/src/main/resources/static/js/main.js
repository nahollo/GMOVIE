/*
	Verti by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
*/

var isLoggedIn = sessionStorage.getItem('userNo');
var loginLink = document.getElementById('loginLink');
var logoutLink = document.getElementById('logoutLink');

// Update the display based on the login status
if (isLoggedIn !== null) {
	loginLink.style.display = 'none';
	logoutLink.style.display = 'block';
} else {
	loginLink.style.display = 'block';
	logoutLink.style.display = 'none';
}


function logout() {
	window.location.href = 'logout';
}

function joinMeeting() {

	var isLoggedIn = sessionStorage.getItem('userNo');

	if (isLoggedIn !== null) {
		window.location.href = 'http://localhost:3000';
	} else {
		alert('로그인 먼저 해주세요');
		window.location.href = 'login';
	}
}

function getSummary() {
	var isLoggedIn = sessionStorage.getItem('userNo');

	if (isLoggedIn !== null) {
		window.location.href = 'summary';
	} else {
		alert('로그인 먼저 해주세요');
		window.location.href = 'login';
	}
}

(function ($) {

	var $window = $(window),
		$body = $('body');

	// Breakpoints.
	breakpoints({
		xlarge: ['1281px', '1680px'],
		large: ['981px', '1280px'],
		medium: ['737px', '980px'],
		small: [null, '736px']
	});

	// Play initial animations on page load.
	$window.on('load', function () {
		window.setTimeout(function () {
			$body.removeClass('is-preload');
		}, 100);
	});

	// Dropdowns.
	$('#nav > ul').dropotron({
		mode: 'fade',
		noOpenerFade: true,
		speed: 300
	});

	// Nav.

	// Toggle.
	$(
		'<div id="navToggle">' +
		'<a href="#navPanel" class="toggle"></a>' +
		'</div>'
	)
		.appendTo($body);

	// Panel.
	$(
		'<div id="navPanel">' +
		'<nav>' +
		$('#nav').navList() +
		'</nav>' +
		'</div>'
	)
		.appendTo($body)
		.panel({
			delay: 500,
			hideOnClick: true,
			hideOnSwipe: true,
			resetScroll: true,
			resetForms: true,
			side: 'left',
			target: $body,
			visibleClass: 'navPanel-visible'
		});

})(jQuery);