$(document).ready(function(){
	$('#submit').click(function(event){
		let username = $('#username').val();
		let email = $('#email').val();
		let password = $('#password').val();
		let firstName = $('#firstName').val();
		let lastName = $('#lastName').val();
		let gender = $('#gender').val();
		let dateOfBirth = $('#dateOfBirth').val();
		let valid = true;
		const nameAndSurnameRegex = new RegExp('^[A-Za-zčćšđžČŠĆĐŽ]+$');
		const emailRegex = new RegExp('^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$');
		
		$('#username').removeAttr('placeholder');
		$('#username').removeClass('red');
		$('#email').removeAttr('placeholder');
		$('#email').removeClass('red');
		$('#password').removeAttr('placeholder');
		$('#password').removeClass('red');
		$('#firstName').removeAttr('placeholder');
		$('#firstName').removeClass('red');
		$('#lastName').removeAttr('placeholder');
		$('#lastName').removeClass('red');
		$('#gender').removeClass('red');
		$('#dateOfBirth').removeClass('red');
		
		if(username === "") {
			$('#username').attr('placeholder', 'Korisničko ime ne može biti prazno');
			$('#username').addClass('red');
			valid = false;
		}
		
		if(valid) { 
			$.get({
					url: '../rest/buyers/',
					success: function(buyers){
						for(let buyer of buyers)
							if(buyer.username === username) {
								$('#username').attr('placeholder', 'Korisničko ime mora biti jedinstveno');
								$('#username').addClass('red');
								valid = false;
							}
					}
			})
			
			$.get({
					url: '../rest/managers/',
					success: function(managers){
						for(let manager of managers)
							if(manager.username === username) {
								$('#username').attr('placeholder', 'Korisničko ime mora biti jedinstveno');
								$('#username').addClass('red');
								valid = false;
							}
					}
			})
			
			$.get({
					url: '../rest/deliverers/',
					success: function(deliverers){
						for(let deliverer of deliverers)
							if(deliverer.username === username) {
								$('#username').attr('placeholder', 'Korisničko ime mora biti jedinstveno');
								$('#username').addClass('red');
								valid = false;
							}
					}
			})
			
			$.get({
					url: '../rest/administrators/',
					success: function(administrators){
						for(let administrator of administrators)
							if(administrator.username === username) {
								$('#username').attr('placeholder', 'Korisničko ime mora biti jedinstveno');
								$('#username').addClass('red');
								valid = false;
							}
					}
			})
		}
		
		if(email === "") {
			$('#email').attr('placeholder', 'Email ne može biti prazan');
			$('#email').addClass('red');
			valid = false;
		}
		else if(emailRegex.test(email) === false) {
			$('#email').attr('placeholder', 'Email nije validan');
			$('#email').addClass('red');
			valid = false;
		}
		
		if(password === "") {
			$('#password').attr('placeholder', 'Šifra ne može biti prazna');
			$('#password').addClass('red');
			valid = false;
		}
		
		if(firstName === "") {
			$('#firstName').attr('placeholder', 'Ime ne može biti prazno');
			$('#firstName').addClass('red');
			valid = false;
		}
		else if(nameAndSurnameRegex.test(firstName) === false) {
			$('#firstName').attr('placeholder', 'Ime mora da sadrži samo slova');
			$('#firstName').addClass('red');
			valid = false;
		}
		
		if(lastName === "") {
			$('#lastName').attr('placeholder', 'Prezime ne može biti prazno');
			$('#lastName').addClass('red');
			valid = false;
		}
		else if(nameAndSurnameRegex.test(lastName) === false) {
			$('#lastName').attr('placeholder', 'Prezime mora da sadrži samo slova');
			$('#lastName').addClass('red');
			valid = false;
		}
		
		if(gender === "") {
			$('#gender').addClass('red');
			valid = false;
		}
		
		let date = new Date($('#dateOfBirth').val());
	    let day = date.getDate();
	    let month = date.getMonth() + 1;
	    let year = date.getFullYear();
	    let now = new Date();
  		now.setHours(0,0,0,0);
		
		if(!day || !month || !year || date > now) {
			$('#dateOfBirth').addClass('red');
			valid = false;
		}
		
		if(valid) {
			$.ajax({
				type: 'POST',
				url: "../rest/managers/save",
				data: JSON.stringify({"firstName": firstName, "lastName": lastName, "email": email, "username": username, "password": password, "gender": gender, "dateOfBirth": dateOfBirth}),
				contentType: 'application/json',
				dataType: 'json',
				success: function(){
					window.location.href='administrator.html';
				}
			});
		}
	});
});