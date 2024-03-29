$(document).ready(function(){
	loadPageForAdministrator();
	loadPageForManager();
	loadPageForDeliverer();
	loadPageForBuyer();

	$('#submit').click(function(){
		let username = $('#username').val();
		let email = $('#email').val();
		let password = $('#password').val();
		let firstName = $('#firstName').val();
		let lastName = $('#lastName').val();
		let gender = $('#gender').val();
		let genderForPut;
		
		if(gender === "Muško")
			genderForPut = "MALE";
		else if(gender === "Žensko")
			genderForPut = "FEMALE";
		
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
			$.get({
				url: '../rest/administrators/loggedInAdministrator',
				success: function(administrator){
					if(administrator.username !== "") {
						$.ajax({
							type: 'PUT',
							url: "../rest/administrators/saveProfileChanges/" + username,
							data: JSON.stringify({"username": username, "firstName": firstName, "lastName": lastName, "email": email, "password": password, "gender": genderForPut, "dateOfBirth": dateOfBirth}),
							contentType: 'application/json',
							dataType: 'json'
						});
						window.location.href='userProfile.html';
					}
				}
			})
			
			$.get({
				url: '../rest/managers/loggedInManager',
				success: function(manager){
					if(manager.username !== "") {
						$.ajax({
							type: 'PUT',
							url: "../rest/managers/saveProfileChanges/" + username,
							data: JSON.stringify({"username": username, "firstName": firstName, "lastName": lastName, "email": email, "password": password, "gender": genderForPut, "dateOfBirth": dateOfBirth}),
							contentType: 'application/json',
							dataType: 'json',
							success: function(){
								window.location.href='userProfile.html';
							}
						});
					}
				}
			})
			
			$.get({
				url: '../rest/deliverers/loggedInDeliverer',
				success: function(deliverer){
					if(deliverer.username !== "") {
						$.ajax({
							type: 'PUT',
							url: "../rest/deliverers/saveProfileChanges/" + username,
							data: JSON.stringify({"username": username, "firstName": firstName, "lastName": lastName, "email": email, "password": password, "gender": genderForPut, "dateOfBirth": dateOfBirth}),
							contentType: 'application/json',
							dataType: 'json',
							success: function(){
								window.location.href='userProfile.html';
							}
						});
					}
				}
			})
			
			$.get({
				url: '../rest/buyers/loggedInBuyer',
				success: function(buyer){
					if(buyer.username !== "") {
						$.ajax({
							type: 'PUT',
							url: "../rest/buyers/saveProfileChanges/" + username,
							data: JSON.stringify({"username": username, "firstName": firstName, "lastName": lastName, "email": email, "password": password, "gender": genderForPut, "dateOfBirth": dateOfBirth}),
							contentType: 'application/json',
							dataType: 'json',
							success: function(){
								window.location.href='userProfile.html';
							}
						});
					}
				}
			})
		}
	});
});

function loadPageForAdministrator() {
	$.get({
			url: '../rest/administrators/loggedInAdministrator',
			success: function(administrator){
				if(administrator.username !== "") {
					$('#firstName').val(administrator.firstName);
					$('#lastName').val(administrator.lastName);
					$('#username').val(administrator.username);
					$('#email').val(administrator.email);
					
					let date = new Date(administrator.dateOfBirth);
					var day = ("0" + date.getDate()).slice(-2);
					var month = ("0" + (date.getMonth() + 1)).slice(-2);
					var dateFormat = date.getFullYear()+"-"+(month)+"-"+(day) ;
					$('#dateOfBirth').val(dateFormat);
					
					$('#password').val(administrator.password);
					if(administrator.gender === "MALE")
						$('#gender').val("Muško");
					else
						$('#gender').val("Žensko");
				}
			}
	})
}

function loadPageForManager() {
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				if(manager.username !== "") {
					$('#firstName').val(manager.firstName);
					$('#lastName').val(manager.lastName);
					$('#username').val(manager.username);
					$('#email').val(manager.email);
					
					let date = new Date(manager.dateOfBirth);
					var day = ("0" + date.getDate()).slice(-2);
					var month = ("0" + (date.getMonth() + 1)).slice(-2);
					var dateFormat = date.getFullYear()+"-"+(month)+"-"+(day) ;
					$('#dateOfBirth').val(dateFormat);
					
					$('#password').val(manager.password);
					if(manager.gender === "MALE")
						$('#gender').val("Muško");
					else
						$('#gender').val("Žensko");
				}
			}
	})
}

function loadPageForDeliverer() {
	$.get({
			url: '../rest/deliverers/loggedInDeliverer',
			success: function(deliverer){
				if(deliverer.username !== "") {
					$('#firstName').val(deliverer.firstName);
					$('#lastName').val(deliverer.lastName);
					$('#username').val(deliverer.username);
					$('#email').val(deliverer.email);
					
					let date = new Date(deliverer.dateOfBirth);
					var day = ("0" + date.getDate()).slice(-2);
					var month = ("0" + (date.getMonth() + 1)).slice(-2);
					var dateFormat = date.getFullYear()+"-"+(month)+"-"+(day) ;
					$('#dateOfBirth').val(dateFormat);
					
					$('#password').val(deliverer.password);
					if(deliverer.gender === "MALE")
						$('#gender').val("Muško");
					else
						$('#gender').val("Žensko");
				}
			}
	})
}

function loadPageForBuyer() {
	$.get({
			url: '../rest/buyers/loggedInBuyer',
			success: function(buyer){
				if(buyer.username !== "") {
					$('#firstName').val(buyer.firstName);
					$('#lastName').val(buyer.lastName);
					$('#username').val(buyer.username);
					$('#email').val(buyer.email);
					
					let date = new Date(buyer.dateOfBirth);
					var day = ("0" + date.getDate()).slice(-2);
					var month = ("0" + (date.getMonth() + 1)).slice(-2);
					var dateFormat = date.getFullYear()+"-"+(month)+"-"+(day) ;
					$('#dateOfBirth').val(dateFormat);
					
					$('#password').val(buyer.password);
					if(buyer.gender === "MALE")
						$('#gender').val("Muško");
					else
						$('#gender').val("Žensko");
				}
			}
	})
}