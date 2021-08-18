$(document).ready(function(){
	$('#submit').click(function(event){
		let username = $('#username').val();
		let email = $('#email').val();
		let password = $('#password').val();
		let firstName = $('#firstName').val();
		let lastName = $('#lastName').val();
		let gender = $('#gender').val();
		let dateOfBirth = $('#dateOfBirth').val();
		
		$.ajax({
			type: 'POST',
			url: "../rest/buyers/save",
			data: JSON.stringify({"firstName": firstName, "lastName": lastName, "email": email, "username": username, "password": password, "gender": gender, "dateOfBirth": dateOfBirth}),
			contentType: 'application/json',
			dataType: 'json'
		});
	});
});