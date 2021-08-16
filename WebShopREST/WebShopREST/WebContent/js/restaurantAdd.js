$(document).ready(function(){
	$('#submit').click(function(event){
		let name = $('#name').val();
		let type = $('#type').val();
		let address = $('#location').val();
		let length = $('#length').val();
		let width = $('#width').val();
		let logo = $('#logo').val().split('\\').pop();
		let location = {"length": length,"width": width,"address": address};
		let valid = true;
		const nameRegex = new RegExp('^[A-Za-z ]+$');
		const addressRegex = new RegExp('^[A-Za-z ]+ [0-9]+, ?[A-Za-z ]+, ?[0-9]{5}, ?[A-Za-z ]+$');
		
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
		
		if(valid) {
			$.ajax({
				type: 'POST',
				url: "../rest/restaurants/save",
				data: JSON.stringify({"name": name, "type": type, "location": location, "logo": logo}),
				contentType: 'application/json',
				dataType: 'json'
			});
		}
	});
});