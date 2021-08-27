$(document).ready(function(){
	$('#submit').click(function(event){
		addArticle();
	});
});

function addArticle() {
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				let name = $('#name').val();
				let price = $('#price').val();
				let type = $('#type').val();
				let quantity = $('#quantity').val();
				let description = $('#description').val();
				let logo = $('#logo').val().split('\\').pop();
				let valid = true;
				const nameRegex = new RegExp('^[A-Za-zčšćđžČŠĆĐŽ ]+$');
				const quantityRegex =	new RegExp('^[1-9][0-9]*(ml|gr)$');
				
				$('#name').removeAttr('placeholder');
				$('#name').removeClass('red');
				$('#type').removeClass('red');
				$('#price').removeAttr('placeholder');
				$('#price').removeClass('red');
				$('#quantity').removeAttr('placeholder');
				$('#quantity').removeClass('red');
				$('#logo').removeClass('red');
				
				if(name === "") {
					$('#name').attr('placeholder', 'Naziv artikla ne može biti prazan');
					$('#name').addClass('red');
					valid = false;
				}
				
				if(valid) {
					if(nameRegex.test(name) === false) {
						$('#name').attr('placeholder', 'Naziv artikla može imati samo riječi');
						$('#name').addClass('red');
						valid = false;
					}
				}
				
				if(valid) {
					$.get({
						url: '../rest/articles/articlesFromRestaurant?restaurantName=' + manager.restaurant.name,
						success: function(articles){
							for(let article of articles)
								if(article.name === name) {
									$('#name').attr('placeholder', 'Naziv artikla u restoranu mora biti jedinstven');
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
				
				if(price === "") {
					$('#price').attr('placeholder', 'Cena ne može biti prazna');
					$('#price').addClass('red');
					valid = false;
				}
				
				if(price !== "" && isNaN(price)) {
					$('#price').attr('placeholder', 'Cena mora biti broj');
					$('#price').addClass('red');
					valid = false;
				}
				
				if(quantity !== "" && quantityRegex.test(quantity) === false) {
					$('#quantity').attr('placeholder', 'Količina je broj uz ml ili gr');
					$('#quantity').addClass('red');
					valid = false;
				}
				
				if(quantity === "") {
					quantity = "-";
				}
				
				if(description === "") {
					description = "-";
				}
				
				if($("#logo")[0].files.length === 0) {
					$('#logo').addClass('red');
					valid = false;
				}
				
				if(valid) {
					$.ajax({
						type: 'POST',
						url: "../rest/articles/save",
						data: JSON.stringify({"name": name, "type": type, "logo": logo, "description": description, "quantity": quantity, "price": price, "restaurant": manager.restaurant}),
						contentType: 'application/json',
						dataType: 'json',
						success: function(){
							window.location.href='manager.html';
						}
					});
				}
			}
	})
}