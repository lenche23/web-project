$(document).ready(function(){
	$('#submit').click(function(event){
		let name = $('#name').val();
		let type = $('#type').val();
		let address = $('#location').val();
		let length = $('#length').val();
		let width = $('#width').val();
		let logo = $('#logo').val().split('\\').pop();
		let location = {"length": length,"width": width,"address": address};
		
		$.ajax({
			type: 'POST',
			url: "../rest/restaurants/save",
			data: JSON.stringify({"name": name, "type": type, "location": location, "logo": logo}),
			contentType: 'application/json',
			dataType: 'json'
		});
	});
});