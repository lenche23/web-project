$(document).ready(function(){
	loadPage();

	$('#submit').click(function(){
		let name = $('#name').val();
		let price = $('#price').val();
		let type = $('#type').val();
		let quantity = $('#quantity').val();
		let description = $('#description').val();
		let logo = $('#logo').val().split('\\').pop();
		let valid = true;
		const quantityRegex =	new RegExp('^[1-9][0-9]*(ml|gr)$');
		
		let typeForPut;
		if(type === "Jelo")
			typeForPut = "FOOD";
		else if(type === "Piće")
			typeForPut = "DRINK";
		
		$('#type').removeClass('red');
		$('#price').removeAttr('placeholder');
		$('#price').removeClass('red');
		$('#quantity').removeAttr('placeholder');
		$('#quantity').removeClass('red');
		$('#logo').removeClass('red');
		
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
			$.get({
				url: '../rest/managers/loggedInManager',
				success: function(manager){
					$.ajax({
						type: 'PUT',
						url: "../rest/articles/saveArticleChanges",
						data: JSON.stringify({"name": name, "type": typeForPut, "logo": logo, "description": description, "quantity": quantity, "price": price, "restaurant": manager.restaurant}),
						contentType: 'application/json',
						dataType: 'json',
						success: function() {
							window.location.href='manager.html';
						}
					});
				}
			})
			
		}
	});
});

function loadPage() {
	$.get({
			url: '../rest/articles/getUpdatedArticle',
			success: function(article){
				$('#name').val(article.name);
				$('#price').val(article.price);
				if(article.quantity !== "-")
					$('#quantity').val(article.quantity);
				if(article.description !== "-")
					$('#description').val(article.description);
				
				if(article.type === "FOOD")
					$('#type').val("Jelo");
				else
					$('#type').val("Piće");
			}
	})
}