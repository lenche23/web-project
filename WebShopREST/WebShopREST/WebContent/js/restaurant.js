$(document).ready(function(){
	loadRestaurantPage();
	
	$('#checkCartBtn').click(function(){
		window.location.href='basket.html';
	});
	
	$('#addToCartBtn').click(function(){
		addToCart();
	});
});

function addToCart() {
	let quantity = $('tr.selected').find("td:eq(6) input[type='text']").val();
	let validQuantity = true;
	
	$('tr.allTr').find("td:eq(6) input[type='text']").removeAttr('placeholder');
	$('tr.allTr').find("td:eq(6) input[type='text']").removeClass('red');
	
	if(!quantity || quantity < 1 || !Number.isInteger(parseInt(quantity))){
		validQuantity = false;
		$('tr.selected').find("td:eq(6) input[type='text']").attr('placeholder', 'Količina je ceo broj veći od 0');
		$('tr.selected').find("td:eq(6) input[type='text']").addClass('red');
	}
	
	if(validQuantity){
		let fullLogoPath = $('tr.selected').find("td:eq(0) img").attr('src').split('/');
		let logo = fullLogoPath[2];
		let name = $('tr.selected').find("td:eq(1)").text();
		let fullPrice = $('tr.selected').find("td:eq(2)").text().split(' ');
		let price = fullPrice[0];
		let article = {"name": name,"logo": logo,"price": price};
		
		$.ajax({
					type: 'POST',
					url: "../rest/basket/addArticleWithQuantity",
					data: JSON.stringify({"article": article, "quantity": quantity}),
					contentType: 'application/json',
					dataType: 'json',
					success: function(){
						alert("Artikal uspešno dodat u korpu!");
					}
		});
	}
	
	$('tr.selected').find("td:eq(6) input[type='text']").val("");
}

function loadRestaurantPage() {
	$.get({
			url: '../rest/restaurants/getViewedRestaurant',
			success: function(restaurant){
				$('#promoText').html("&nbsp" + restaurant.name);
				$("#logo").attr('src', "../images/" + restaurant.logo );
				$('#address').val(restaurant.location.address);
				$('#type').val(restaurant.type);
				$('#grade').val(restaurant.grade);
				if(restaurant.status === "OPEN")
					$('#status').val("Otvoren");
				else if(restaurant.status === "CLOSED")
					$('#status').val("Zatvoren");
					
				$.get({
						url: '../rest/buyers/loggedInBuyer',
						success: function(buyer){
							if(buyer.username !== "") {
								$.get({
									url: '../rest/articles/articlesFromRestaurant?restaurantName=' + restaurant.name,
									success: function(articles){
										if(restaurant.status === "OPEN"){
											for(let article of articles)
												if(!article.deleted){
													addArticleToTableBuyer(article);
												}
										}
										else {
											$('#actionBtnsDiv').hide();
											$('#divBetweenButtonAndMyOrders').hide();
											$("#articlesTable th:last-child").remove();
											for(let article of articles)
												if(!article.deleted){
													addArticleToTable(article);
												}
										}					
									}
								})
							}
							else{
								$('#actionBtnsDiv').hide();
								$('#divBetweenButtonAndMyOrders').hide();
								$("#articlesTable th:last-child").remove();
								$.get({
									url: '../rest/articles/articlesFromRestaurant?restaurantName=' + restaurant.name,
									success: function(articles){
										for(let article of articles)
											if(!article.deleted)
												addArticleToTable(article);
									}
								})
							}
						
						}
			})	
			$.get({
				url: '../rest/comments/',
				success: function(comments){
					for(let c of comments) {
						if(!c.deleted && c.restaurantName === restaurant.name && c.accepted)
							addCommentToTable(c);
					}
				}
			})
			}
	})
}

function addCommentToTable(comment){
	let tableBody = $('#tableBody2');
	let newRow = $('<tr>');
	
	let buyer = $('<td>').text(comment.buyerUsername);
	let content = $('<td>').text(comment.content);
	let grade = "";
	if(comment.grade === "ONE") {
		grade = $('<td>').text("1");
	} else if (comment.grade === "TWO"){
		grade = $('<td>').text("2");
	} else if (comment.grade === "THREE"){
		grade = $('<td>').text("3");
	} else if (comment.grade === "FOUR"){
		grade = $('<td>').text("4");
	} else {
		grade = $('<td>').text("5");
	}
	
	newRow.append(buyer).append(content).append(grade);
	tableBody.append(newRow);
}

function addArticleToTableBuyer(article){
	let tableBody = $('#tableBody');
	let newRow = $('<tr>');
	
	let logoTd = $('<td>');
	let logo = $('<img style="width: 100px;" src="../images/' + article.logo + '" alt="Slika">');
	logoTd.append(logo);
	let name = $('<td>').text(article.name);
	let price = $('<td>').text(article.price + ' RSD');
	let type = "";
	if(article.type === "FOOD")
		type = $('<td>').text("Jelo");
	else
		type = $('<td>').text("Piće");
	let size = $('<td>').text(article.quantity);
	let description = $('<td>').text(article.description);
	let quantityTd = $('<td>');
	let quantity = $('<input type="text" />');
	quantityTd.append(quantity);
	
	newRow.append(logoTd).append(name).append(price).append(type).append(size).append(description).append(quantityTd);
	newRow.click(selectedRow());
	tableBody.append(newRow);
}

function addArticleToTable(article){
	let tableBody = $('#tableBody');
	let newRow = $('<tr>');
	
	let logoTd = $('<td>');
	let logo = $('<img style="width: 100px;" src="../images/' + article.logo + '" alt="Slika">');
	logoTd.append(logo);
	let name = $('<td>').text(article.name);
	let price = $('<td>').text(article.price + ' RSD');
	let type = "";
	if(article.type === "FOOD")
		type = $('<td>').text("Jelo");
	else
		type = $('<td>').text("Piće");
	let size = $('<td>').text(article.quantity);
	let description = $('<td>').text(article.description);
	
	newRow.append(logoTd).append(name).append(price).append(type).append(size).append(description);
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