$(document).ready(function(){
	loadPage();
	
	$("#deleteArticle").click(function() {
		deleteArticle();
	});
	
	$("#searchIcon").click(function() {
		search();
	});
	
	$('#logoutBtn').click(function(){
		logout();
	});
	
	$("#updateArticle").click(function() {
		updateArticle();
	});
});

function logout() {
	$.get({
			url: '../rest/administrators/logout',
			success: function(){
				window.location.href='index.html';
			}
	})
}

function updateArticle() {
	if($('tr.selected').length === 1){
		let articleName = $('tr.selected').find('td:eq(1)').text();
	
		$.get({
				url: '../rest/managers/loggedInManager',
				success: function(manager){
					$.get({
							url: '../rest/articles/setUpdatedArticle/' + articleName + '/' + manager.restaurant.name,
							contentType: 'application/json',
							dataType: 'json',
							success: function() {
								window.location.href='articleUpdate.html';
							}
					});			
				}
		})
	}
}

function deleteArticle() {
	let articleName = $('tr.selected').find('td:eq(1)').text();
	$('tr.selected').remove();
	
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				$.ajax({
					type: 'PUT',
					url: '../rest/articles/delete/' + manager.restaurant.name + '/' + articleName,
					contentType: 'application/json',
					dataType: 'json'
				});	
			}
	})
}

function loadPage() {
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				$('#buyersText').html("&nbsp&nbsp" + manager.firstName + " " + manager.lastName);
				$("#logo").attr('src', "../images/" + manager.restaurant.logo );
				$('#name').val(manager.restaurant.name);
				$('#address').val(manager.restaurant.location.address);
				$('#type').val(manager.restaurant.type);
				$('#grade').val('-');
				if(manager.restaurant.status === "OPEN")
					$('#status').val("Otvoren");
				else if(manager.restaurant.status === "CLOSED")
					$('#status').val("Zatvoren");
					
				$.get({
					url: '../rest/articles/articlesFromRestaurant?restaurantName=' + manager.restaurant.name,
					success: function(articles){
						for(let article of articles)
							if(!article.deleted)
								addArticleToTable(article);
					}
				})
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
	let price = $('<td>').text(article.price + ' RSD');
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

function search() {
	let name = $('#inputBox').val();
	
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				$.get({
					url: '../rest/articles/search?searchArticle=' + name + '&searchRestaurant=' + manager.restaurant.name,
					success: function(articles){
						$('#tableBody').empty();
						for(let article of articles)
							if(!article.deleted)
								addArticleToTable(article);
						$('#inputBox').val("");
					}
				})
			}
	})
}