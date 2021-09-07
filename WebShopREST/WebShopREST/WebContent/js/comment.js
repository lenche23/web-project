$(document).ready(function(){
	$('#submit').click(function(){
		let content = $('#content').val();
		let grade = $('#grade').val();
		let buyer = "";
		let restaurant = decodeURI(window.location.href.split("=")[1]);
		let id = "";
		let valid = true;
		
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
			$.get({
				url: '../rest/buyers/loggedInBuyer',
				success: function(buyer1){
					buyer = buyer1.username;
					
							
					$.ajax({
						type: 'POST',
						url: "../rest/comments/save",
						data: JSON.stringify({"buyerUsername": buyer, "restaurantName": restaurant, "content": content, "grade": grade}),
						contentType: 'application/json',
						dataType: 'json',
						success: function(){
							window.location.replace("../html/userProfile.html");
						}
					});
						
					
				}
			})
			
		}
	});	
});