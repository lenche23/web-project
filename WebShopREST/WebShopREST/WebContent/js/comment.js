$(document).ready(function(){
	$('#submit').click(function(){
		let content = $('#content').val();
		let grade = $('#grade').val();
		let buyer;
		let restaurantName = decodeURI(window.location.href.split("=")[1]);
		let restaurant;
		let id;
		let valid = true;
		$.get({
				url: '../rest/buyers/loggedInBuyer',
				success: function(buyer1){
					buyer = buyer1;
				}
		})
		$.get({
				url: '../rest/restaurants/',
				success: function(restaurants){
					for (let r in restaurants)
						if(r.name === restaurantName)
							restaurant = r;
				}
		})	
		$.get({
				url: '../rest/comments/commentNum',
				success: function(commentNum){
					id = commentNum + 1;
				}
		})	
		
		$('#content').removeAttr('placeholder');
		$('#content').removeClass('red');
		$('#grade').removeClass('red');
		
		if(content === "") {
			$('#content').attr('placeholder', 'Morate uneti tekst komentara');
			$('#content').addClass('red');
			valid = false;
		}
				
		if(grade === "") {
			$('#grade').addClass('red');
			valid = false;
		}
		
		if(valid) {
			$.ajax({
				type: 'POST',
				url: "../rest/comments/save",
				data: JSON.stringify({"id": id, "buyer": buyer, "restaurant": restaurant, "content": content, "grade": grade, "deleted": false}),
				contentType: 'application/json',
				dataType: 'json',
				success: function(){
					window.location.replace("../html/userProfile.html");
				}
			});
		}
	});	
});