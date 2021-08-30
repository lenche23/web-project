$(document).ready(function(){
	loadPage();
	
	$('#removeBtn').click(function(){
		removeFromCart();
	});
	
	$('#changeQuantityBtn').click(function(){
		changeQuantity();
	});
	
	$('#orderBtn').click(function(){
		order();
	});
});

function order(){
	$.get({
			url: '../rest/basket/',
			success: function(basket){
				let articles = [];
				let i = 0;
				for(let articleWithQuantity of basket.articlesWithQuantity) {
					articles[i] = articleWithQuantity.article;
					i++;
				}
				let price = basket.price;
				$.get({
						url: '../rest/buyers/loggedInBuyer',
						success: function(buyer){
							$.get({
									url: '../rest/restaurants/getViewedRestaurant',
									success: function(restaurant){
										$.ajax({
											type: 'POST',
											url: "../rest/orders/save",
											data: JSON.stringify({"articles": articles, "price": price, "restaurant": restaurant, "buyer": buyer}),
											contentType: 'application/json',
											dataType: 'json'
										});
									}
							})
						}
				})
			}
	})
}

function changeQuantity() {
	let quantity = $('tr.selected').find("td:eq(3) input[type='text']").val();
	let validQuantity = true;
	
	$('tr.allTr').find("td:eq(3) input[type='text']").removeAttr('placeholder');
	$('tr.allTr').find("td:eq(3) input[type='text']").removeClass('red');
	
	if(!quantity || quantity < 1 || !Number.isInteger(parseInt(quantity))){
		validQuantity = false;
		$('tr.selected').find("td:eq(3) input[type='text']").attr('placeholder', 'Količina je ceo broj veći od 0');
		$('tr.selected').find("td:eq(3) input[type='text']").addClass('red');
	}
	
	if(validQuantity){
		let name = $('tr.selected').find("td:eq(1)").text();
		let price = $('tr.selected').find("td:eq(2)").text();
		let article = {"name": name, "price": price};
		
		$.ajax({
					type: 'PUT',
					url: "../rest/basket/changeQuantity/" + name,
					data: JSON.stringify({"article": article, "quantity": quantity}),
					contentType: 'application/json',
					dataType: 'json',
					success: function(){
						$.get({
								url: '../rest/basket/',
								success: function(basket){
									$('#priceSentence').html('&nbsp&nbsp&nbspUkupna cena porudžbine: ' + parseFloat(basket.price).toFixed(2));
								}
						})
					}
		});
	}
}

function removeFromCart(){
		let name = $('tr.selected').find("td:eq(1)").text();
		let price = $('tr.selected').find("td:eq(2)").text();
		let quantity = $('tr.selected').find("td:eq(3) input[type='text']").val();
		let article = {"name": name, "price": price};
		
		$.ajax({
			type: 'POST',
			url: "../rest/basket/removeArticleWithQuantity",
			data: JSON.stringify({"article": article, "quantity": quantity}),
			contentType: 'application/json',
			dataType: 'json',
			success: function(){
				$('tr.selected').remove();
				$.get({
						url: '../rest/basket/',
						success: function(basket){
							$('#priceSentence').html('&nbsp&nbsp&nbspUkupna cena porudžbine: ' + parseFloat(basket.price).toFixed(2));
						}
				})
			}
		});
}

function loadPage(){
	$.get({
			url: '../rest/basket/',
			success: function(basket){
				for(let articleWithQuantity of basket.articlesWithQuantity)
					addArticleWithQuantityToTable(articleWithQuantity);
				
				$('#priceSentence').html('&nbsp&nbsp&nbspUkupna cena porudžbine: ' + parseFloat(basket.price).toFixed(2));
			}
	})
}

function addArticleWithQuantityToTable(articleWithQuantity) {
	let tableBody = $('#tableBody');
	let newRow = $('<tr>');
	
	let logoTd = $('<td>');
	let logo = $('<img class="photo" src="../images/' + articleWithQuantity.article.logo + '" alt="Slika">');
	logoTd.append(logo);
	let name = $('<td>').text(articleWithQuantity.article.name);
	let price = $('<td>').text(articleWithQuantity.article.price);
	let quantityTd = $('<td>');
	let quantity = $('<input type="text" />');
	quantity.val(articleWithQuantity.quantity);
	quantityTd.append(quantity);
	
	newRow.append(logoTd).append(name).append(price).append(quantityTd);
	newRow.click(selectedRow());
	tableBody.append(newRow);
}

function selectedRow() {
	return function() {
		$('tr.selected').removeClass('selected');
		$(this).addClass('selected');
		$(this).addClass('allTr');
	};
}