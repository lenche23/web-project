$(document).ready(function(){
	loadManagers();
	
	$('#submit').click(function(event){
		if(document.getElementById("manager").length > 1) {
			createRestaurantWithExistingManager();
		}
		else{
			createRestaurantWithNewManager();
		}
	});

	var map = new ol.Map({
		target: 'map',
		layers: [
			new ol.layer.Tile({
			source: new ol.source.OSM()
			})
		],
		view: new ol.View({
			center: ol.proj.fromLonLat([19.825,45.25]),
			zoom: 13.5
		})
	});
});

function createRestaurantWithNewManager() {
	let name = $('#name').val();
	let type = $('#type').val();
	let address = $('#location').val();
	let length = $('#length').val();
	let width = $('#width').val();
	let logo = $('#logo').val().split('\\').pop();
	let location = {"length": length,"width": width,"address": address};
	let username = $('#username').val();
	let email = $('#email').val();
	let password = $('#password').val();
	let firstName = $('#firstName').val();
	let lastName = $('#lastName').val();
	let gender = $('#gender').val();
	let dateOfBirth = $('#dateOfBirth').val();
	let valid = true;
	const nameRegex = new RegExp('^[A-Za-zčšćđžČŠĆĐŽ ]+$');
	const addressRegex = new RegExp('^[A-Za-zčšćđžČŠĆĐŽ ]+ [0-9]+, ?[A-Za-zčšćđžČŠĆĐŽ ]+, ?[0-9]{5}, ?[A-Za-zčšćđžČŠĆĐŽ ]+$');
	const nameAndSurnameRegex = new RegExp('^[A-Za-zčćšđžČŠĆĐŽ]+$');
	const emailRegex = new RegExp('^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$');
	
	$('#name').removeAttr('placeholder');
	$('#name').removeClass('red');
	$('#type').removeClass('red');
	$('#location').removeAttr('placeholder');
	$('#location').removeClass('red');
	$('#length').removeAttr('placeholder');
	$('#length').removeClass('red');
	$('#width').removeAttr('placeholder');
	$('#width').removeClass('red');
	$('#logo').removeClass('red');
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
	
	if(name === "") {
		$('#name').attr('placeholder', 'Naziv restorana ne može biti prazan');
		$('#name').addClass('red');
		valid = false;
	}
	
	if(valid) {
		if(nameRegex.test(name) === false) {
			$('#name').attr('placeholder', 'Naziv restorana može imati samo riječi');
			$('#name').addClass('red');
			valid = false;
		}
	}
	
	if(valid) {
		$.get({
			url: '../rest/restaurants/',
			success: function(restaurants){
				for(let restaurant of restaurants)
					if(restaurant.name === name) {
						$('#name').attr('placeholder', 'Naziv restorana mora biti jedinstven');
						$('#name').addClass('red');
						valid = false;
					}
			}
		})
	}
	
	if(type === "") {
		$('#type').addClass('red');
		valid = false;
	}
	
	if(address === "") {
		$('#location').attr('placeholder', 'Adresa restorana ne može biti prazna');
		$('#location').addClass('red');
		valid = false;
	}
	
	if(address !== "" && addressRegex.test(address) === false) {
		$('#location').attr('placeholder', 'Ulica i broj, grad, poštanski broj, država');
		$('#location').addClass('red');
		valid = false;
	}
	
	if(length === "") {
		$('#length').attr('placeholder', 'Geografska dužina ne može biti prazna');
		$('#length').addClass('red');
		valid = false;
	}
	
	if(length !== "" && isNaN(length)) {
		$('#length').attr('placeholder', 'Geografska dužina mora biti broj');
		$('#length').addClass('red');
		valid = false;
	}
	
	if(length !== "" && !isNaN(length) && (length < -180 || length > 180)) {
		$('#length').attr('placeholder', 'Geografska dužina je broj između -180 i 180');
		$('#length').addClass('red');
		valid = false;
	}
	
	if(width === "") {
		$('#width').attr('placeholder', 'Geografska širina ne može biti prazna');
		$('#width').addClass('red');
		valid = false;
	}
	
	if(width !== "" && isNaN(width)) {
		$('#width').attr('placeholder', 'Geografska širina mora biti broj');
		$('#width').addClass('red');
		valid = false;
	}
	
	if(width !== "" && !isNaN(width) && (width < -90 || width > 90)) {
		$('#width').attr('placeholder', 'Geografska širina je broj između -90 i 90');
		$('#width').addClass('red');
		valid = false;
	}
	
	if($("#logo")[0].files.length === 0) {
		$('#logo').addClass('red');
		valid = false;
	}
	
	if(username === "") {
		$('#username').attr('placeholder', 'Korisničko ime ne može biti prazno');
		$('#username').addClass('red');
		valid = false;
	}
	else {
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
			url: "../rest/restaurants/save",
			data: JSON.stringify({"name": name, "type": type, "location": location, "logo": logo}),
			contentType: 'application/json',
			dataType: 'json',
			success: function(restaurant){
				$.ajax({
					type: 'POST',
					url: "../rest/managers/save",
					data: JSON.stringify({"firstName": firstName, "lastName": lastName, "email": email, "username": username, "password": password, "gender": gender, "dateOfBirth": dateOfBirth, "restaurant": restaurant}),
					contentType: 'application/json',
					dataType: 'json',
					success: function(){
						window.location.href='index.html';
					}
				});
			}
		});
	}
}

function createRestaurantWithExistingManager() {
	let name = $('#name').val();
	let type = $('#type').val();
	let address = $('#location').val();
	let length = $('#length').val();
	let width = $('#width').val();
	let logo = $('#logo').val().split('\\').pop();
	let location = {"length": length,"width": width,"address": address};
	let manager = $('#manager').val();
	let valid = true;
	const nameRegex = new RegExp('^[A-Za-zčšćđžČŠĆĐŽ ]+$');
	const addressRegex = new RegExp('^[A-Za-zčšćđžČŠĆĐŽ ]+ [0-9]+, ?[A-Za-zčšćđžČŠĆĐŽ ]+, ?[0-9]{5}, ?[A-Za-zčšćđžČŠĆĐŽ ]+$');
	
	$('#name').removeAttr('placeholder');
	$('#name').removeClass('red');
	$('#type').removeClass('red');
	$('#location').removeAttr('placeholder');
	$('#location').removeClass('red');
	$('#length').removeAttr('placeholder');
	$('#length').removeClass('red');
	$('#width').removeAttr('placeholder');
	$('#width').removeClass('red');
	$('#logo').removeClass('red');
	$('#manager').removeClass('red');
	
	if(name === "") {
		$('#name').attr('placeholder', 'Naziv restorana ne može biti prazan');
		$('#name').addClass('red');
		valid = false;
	}
	
	if(valid) {
		if(nameRegex.test(name) === false) {
			$('#name').attr('placeholder', 'Naziv restorana može imati samo riječi');
			$('#name').addClass('red');
			valid = false;
		}
	}
	
	if(valid) {
		$.get({
			url: '../rest/restaurants/',
			success: function(restaurants){
				for(let restaurant of restaurants)
					if(restaurant.name === name) {
						$('#name').attr('placeholder', 'Naziv restorana mora biti jedinstven');
						$('#name').addClass('red');
						valid = false;
					}
			}
		})
	}
	
	if(type === "") {
		$('#type').addClass('red');
		valid = false;
	}
	
	if(address === "") {
		$('#location').attr('placeholder', 'Adresa restorana ne može biti prazna');
		$('#location').addClass('red');
		valid = false;
	}
	
	if(address !== "" && addressRegex.test(address) === false) {
		$('#location').attr('placeholder', 'Ulica i broj, grad, poštanski broj, država');
		$('#location').addClass('red');
		valid = false;
	}
	
	if(length === "") {
		$('#length').attr('placeholder', 'Geografska dužina ne može biti prazna');
		$('#length').addClass('red');
		valid = false;
	}
	
	if(length !== "" && isNaN(length)) {
		$('#length').attr('placeholder', 'Geografska dužina mora biti broj');
		$('#length').addClass('red');
		valid = false;
	}
	
	if(length !== "" && !isNaN(length) && (length < -180 || length > 180)) {
		$('#length').attr('placeholder', 'Geografska dužina je broj između -180 i 180');
		$('#length').addClass('red');
		valid = false;
	}
	
	if(width === "") {
		$('#width').attr('placeholder', 'Geografska širina ne može biti prazna');
		$('#width').addClass('red');
		valid = false;
	}
	
	if(width !== "" && isNaN(width)) {
		$('#width').attr('placeholder', 'Geografska širina mora biti broj');
		$('#width').addClass('red');
		valid = false;
	}
	
	if(width !== "" && !isNaN(width) && (width < -90 || width > 90)) {
		$('#width').attr('placeholder', 'Geografska širina je broj između -90 i 90');
		$('#width').addClass('red');
		valid = false;
	}
	
	if($("#logo")[0].files.length === 0) {
		$('#logo').addClass('red');
		valid = false;
	}
	
	if(manager === "") {
		$('#manager').addClass('red');
		valid = false;
	}
	
	if(valid) {
		$.ajax({
			type: 'POST',
			url: "../rest/restaurants/save",
			data: JSON.stringify({"name": name, "type": type, "location": location, "logo": logo}),
			contentType: 'application/json',
			dataType: 'json',
			success: function(){
				$.ajax({
					type: 'PUT',
					url: "../rest/managers/addManager/" + manager + "/toRestaurant/" + name,
					contentType: 'application/json',
					dataType: 'json',
					success: function(){
						window.location.href='index.html';
					}
				});
			}
		});
	}
}

function loadManagers() {
	$.get({
		url: '../rest/managers/',
		success: function(managers){
			for(let manager of managers){
				if(manager.restaurant.name == "")
					$("#manager").append('<option value=' + manager.username + '>' + manager.username + '</option>');
			}
			
			if(document.getElementById("manager").length > 1) {
				$('.dataManager').hide();
				$('.textBottom').hide();
				$('#spaceDiv').hide();
			}
			else {
				$('#managerDiv').hide();
			}
		}
	});
}