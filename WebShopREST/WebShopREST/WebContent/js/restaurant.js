$(document).ready(function(){
	loadRestaurantPage();
});

function loadRestaurantPage() {
	$.get({
			url: '../rest/restaurants/getViewedRestaurant',
			success: function(restaurant){
				$('#promoText').html("&nbsp" + restaurant.name);
				$("#logo").attr('src', "../images/" + restaurant.logo );
				$('#address').val(restaurant.location.address);
				$('#type').val(restaurant.type);
				$('#grade').val('-');
				if(restaurant.status === "OPEN")
					$('#status').val("Otvoren");
				else if(restaurant.status === "CLOSED")
					$('#status').val("Zatvoren");
					
				$.get({
					url: '../rest/articles/articlesFromRestaurant?restaurantName=' + restaurant.name,
					success: function(articles){
						for(let article of articles)
							if(!article.deleted)
								addArticleToTable(article);
					}
				})
					
				$('#commentsTable').hide();
			}
	})
}

function addArticleToTable(article){
	let tableBody = $('#tableBody');
	let newRow = $('<tr>');
	
	let logoTd = $('<td>');
	let logo = $('<img style="width: 100px;" src="../images/' + article.logo + '" alt="Slika">');
	logoTd.append(logo);
	let name = $('<td>').text(article.name);
	let price = $('<td>').text(article.price);
	let type = "";
	if(article.type === "FOOD")
		type = $('<td>').text("Jelo");
	else
		type = $('<td>').text("Piće");
	let quantity = $('<td>').text(article.quantity);
	let description = $('<td>').text(article.description);
	
	newRow.append(logoTd).append(name).append(price).append(type).append(quantity).append(description);
	newRow.click(selectedRow());
	tableBody.append(newRow);
}

function selectedRow() {
	return function() {
		$('tr.selected').removeClass('selected');
		$(this).addClass('selected');
	};
}