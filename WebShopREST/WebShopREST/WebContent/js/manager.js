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
	
	$("#approveComment").click(function() {
		approveComment();
	});
	
	$("#declineComment").click(function() {
		declineComment();
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
				$.get({
					url: '../rest/comments/',
					success: function(comments){
						for(let c of comments) {
							if(!c.deleted && c.restaurantName === manager.restaurant.name)
								addCommentToTable(c);
						}
					}
				})
			}
	})
}

function declineComment() {
	let id = $('tr.selected').find('td:eq(0)').text();
	let accepted = $('tr.selected').find('td:eq(4)').text();
	$('tr.selected').remove();
	
	if (accepted === "Ceka na odobravanje"){
		$.ajax({
			type: 'PUT',
			url: '../rest/comments/delete/' + id,
			contentType: 'application/json',
			dataType: 'json'
		});	
	}	
}

function approveComment() {
	let id = $('tr.selected').find('td:eq(0)').text();
	let accepted = $('tr.selected').find('td:eq(4)').text();
	$('tr.selected').find('td:eq(4)').text("Odobren");
	
	if (accepted === "Ceka na odobravanje"){
		$.ajax({
			type: 'PUT',
			url: '../rest/comments/approve/' + id,
			contentType: 'application/json',
			dataType: 'json'
		});	
	}	
}

function addCommentToTable(comment){
	let tableBody = $('#tableBody2');
	let newRow = $('<tr>');
	
	let id = $('<td>').text(comment.id);
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
	let accepted = "";
	if(comment.accepted) {
		accepted = $('<td>').text("Odobren");
	}  else {
		accepted = $('<td>').text("Ceka na odobravanje");
	}
	
	newRow.append(id).append(buyer).append(content).append(grade).append(accepted);
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