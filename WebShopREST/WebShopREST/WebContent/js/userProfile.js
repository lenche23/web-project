$(document).ready(function(){
	loadPageForAdministrator();
	loadPageForManager();
	loadPageForDeliverer();
	loadPageForBuyer();
	
	$('#logoutBtn').click(function(){
		logout();
	});
	
	$('#users').click(function(){
		window.location.href='administrator.html';
	});
	
	$('#restaurantsBtn').click(function(){
		window.location.href='index.html';
	});
	
	$('#managerBtn').click(function(){
		window.location.href='manager.html';
	});
	
	$('#profile').click(function(){
		window.location.href='userProfile.html';
	});
	
	$('#cancelBtn').click(function(){
		cancelOrder();
	});
	
	$('#requestBtn').click(function(){
		requestToDeliver();
	});
	
	$('#deliveredBtn').click(function(){
		changeToDelivered();
	});
});

function changeToDelivered() {
	let status = $('tr.selected').find("td:eq(6)").text();
	let id = $('tr.selected').find("td:eq(0)").text();
	
	if(status === "U transportu") {
		$.ajax({
			type: 'PUT',
			url: "../rest/orders/changeToDelivered/" + id,
			contentType: 'application/json',
			dataType: 'json',
			success: function(){
				$('tr.selected').find("td:eq(6)").text('Dostavljena');
			}
		});
	}
}

function requestToDeliver() {
	let id = $('tr.selected').find("td:eq(0)").text();
	if(id){
		$.ajax({
			type: 'PUT',
			url: "../rest/orders/addDeliverer/" + id,
			contentType: 'application/json',
			dataType: 'json',
			success: function(){
				$('tr.selected').remove();
			}
		});
	}
}

function cancelOrder() {
	let status = $('tr.selected').find("td:eq(5)").text();
	let price = $('tr.selected').find("td:eq(4)").text().split(' ')[0];
	let id = $('tr.selected').find("td:eq(0)").text();
	
	if(status === "Obrada"){
		$.ajax({
			type: 'PUT',
			url: "../rest/orders/changeToCanceled/" + id,
			contentType: 'application/json',
			dataType: 'json',
			success: function(){
				$('tr.selected').find("td:eq(5)").text('Otkazana');
				
				$.ajax({
					type: 'PUT',
					url: "../rest/buyers/removePoints",
					data: JSON.stringify({"price": parseFloat(price)}),
					contentType: 'application/json',
					dataType: 'json',
					success: function(){
						$.get({
								url: '../rest/buyers/loggedInBuyer',
								success: function(buyer){
									if(buyer.type.name === "GOLDEN")
										$('#buyerTypeInput').val("Zlatni");
									else if(buyer.type.name === "SILVER")
										$('#buyerTypeInput').val("Srebrni");
									else if(buyer.type.name === "BRONZE")
										$('#buyerTypeInput').val("Bronzani");	
								}
						})
					}
				});
			}
		});
	}
}

function addOrderToTableBuyer(order) {
	let tableBody = $('#tableBodyMyOrders');
	let newRow = $('<tr>');
	
	let id = $('<td>').text(order.id);
	let price = $('<td>').text(order.price + ' RSD');
	let status = '';
	if(order.status == "PROCESSING")
		status = $('<td>').text('Obrada');
	else if(order.status == "PREPARATING")
		status = $('<td>').text('U pripremi');
	else if(order.status == "WAITING_FOR_DELIVERER")
		status = $('<td>').text('Čeka dostavljača');
	else if(order.status == "TRANSPORTING")
		status = $('<td>').text('U transportu');
	else if(order.status == "DELIVERED")
		status = $('<td>').text('Dostavljena');
	else if(order.status == "CANCELED")
		status = $('<td>').text('Otkazana');
	let restaurant = $('<td>').text(order.restaurant.name);
	
	articles = "";
	for(let article of order.articles)
		articles = articles.concat(article.name + ', ');
	articles = articles.substring(0, articles.length-2);
	let articlesTd = $('<td>').text(articles);
	
	let date = new Date(order.dateAndTime);
	let dateAndTime = $('<td>').text(date.getHours() + ':' + date.getMinutes() + " " + date.getDate() + "." + (date.getMonth()+1) + "." + date.getFullYear());
		
	newRow.append(id).append(articlesTd).append(restaurant).append(dateAndTime).append(price).append(status);
	newRow.click(selectedRow());
	tableBody.append(newRow);
}

function addOrderToTableDelivererMine(order) {
	let tableBody = $('#tableBodyMyOrders');
	let newRow = $('<tr>');
	
	let id = $('<td>').text(order.id);
	let price = $('<td>').text(order.price + ' RSD');
	let status = '';
	if(order.status == "PROCESSING")
		status = $('<td>').text('Obrada');
	else if(order.status == "PREPARATING")
		status = $('<td>').text('U pripremi');
	else if(order.status == "WAITING_FOR_DELIVERER")
		status = $('<td>').text('Čeka dostavljača');
	else if(order.status == "TRANSPORTING")
		status = $('<td>').text('U transportu');
	else if(order.status == "DELIVERED")
		status = $('<td>').text('Dostavljena');
	else if(order.status == "CANCELED")
		status = $('<td>').text('Otkazana');
	let restaurant = $('<td>').text(order.restaurant.name);
	let buyer = $('<td>').text(order.buyer.username);
	
	articles = "";
	for(let article of order.articles)
		articles = articles.concat(article.name + ', ');
	articles = articles.substring(0, articles.length-2);
	let articlesTd = $('<td>').text(articles);
	
	let date = new Date(order.dateAndTime);
	let dateAndTime = $('<td>').text(date.getHours() + ':' + date.getMinutes() + " " + date.getDate() + "." + (date.getMonth()+1) + "." + date.getFullYear());
		
	newRow.append(id).append(articlesTd).append(restaurant).append(buyer).append(dateAndTime).append(price).append(status);
	newRow.click(selectedRow());
	tableBody.append(newRow);
}

function addOrderToTableDelivererWaiting(order) {
	let tableBody = $('#tableBodyOrdersOnWait');
	let newRow = $('<tr>');
	
	let id = $('<td>').text(order.id);
	let price = $('<td>').text(order.price + ' RSD');
	let status = '';
	if(order.status == "PROCESSING")
		status = $('<td>').text('Obrada');
	else if(order.status == "PREPARATING")
		status = $('<td>').text('U pripremi');
	else if(order.status == "WAITING_FOR_DELIVERER")
		status = $('<td>').text('Čeka dostavljača');
	else if(order.status == "TRANSPORTING")
		status = $('<td>').text('U transportu');
	else if(order.status == "DELIVERED")
		status = $('<td>').text('Dostavljena');
	else if(order.status == "CANCELED")
		status = $('<td>').text('Otkazana');
	let restaurant = $('<td>').text(order.restaurant.name);
	let buyer = $('<td>').text(order.buyer.username);
	
	articles = "";
	for(let article of order.articles)
		articles = articles.concat(article.name + ', ');
	articles = articles.substring(0, articles.length-2);
	let articlesTd = $('<td>').text(articles);
	
	let date = new Date(order.dateAndTime);
	let dateAndTime = $('<td>').text(date.getHours() + ':' + date.getMinutes() + " " + date.getDate() + "." + (date.getMonth()+1) + "." + date.getFullYear());
		
	newRow.append(id).append(articlesTd).append(restaurant).append(buyer).append(dateAndTime).append(price).append(status);
	newRow.click(selectedRow());
	tableBody.append(newRow);
}

function logout() {
	$.get({
			url: '../rest/administrators/logout',
			success: function(){
				window.location.href='index.html';
			}
	})
}

function selectedRow() {
	return function() {
		$('tr.selected').removeClass('selected');
		$(this).addClass('selected');
	};
}

function loadPageForAdministrator() {
	$.get({
			url: '../rest/administrators/loggedInAdministrator',
			success: function(administrator){
				if(administrator.username !== "") {
					$('#buyerType').hide();
					$('#buyerTypeInput').hide();
					$('#managerBtn').hide();
					$('#promoText').html("&nbsp" + administrator.firstName + " " + administrator.lastName);
					$('#username').val(administrator.username);
					$('#email').val(administrator.email);
					
					let date = new Date(administrator.dateOfBirth);
					var day = date.getDate();
				    var month = date.getMonth() + 1;
				    var year = date.getFullYear();
					$('#dateOfBirth').val([day, month, year].join('.'));
					
					if(administrator.gender === "MALE")
						$('#gender').val("Muško");
					else
						$('#gender').val("Žensko");
					$('#mojePorudzbineTitleDiv').hide();
					$('#filterDiv').hide();
					$('#articlesTable').hide();
					$('#divBetweenProfileAndOrders').hide();
					$('#filterDiv2').hide();
					$('#searchDiv').hide();
					$('#ordersOnWaitTitleDiv').hide();
					$('#ordersOnWaitTable').hide();
					$('#actionBtnsDiv').hide();
					$('#actionBtnsDiv2').hide();
					$('#actionBtnsDiv3').hide();
					$('#divBetweenButtonAndMyOrders2').hide();
					$('#divBetweenButtonAndOrders').hide();
					$('#divBetweenButtonAndMyOrders').hide();
				}
			}
	})
}

function loadPageForManager() {
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				if(manager.username !== "") {
					$('#buyerType').hide();
					$('#buyerTypeInput').hide();
					$('#users').hide();
					$('#promoText').html("&nbsp" + manager.firstName + " " + manager.lastName);
					$('#username').val(manager.username);
					$('#email').val(manager.email);
					
					let date = new Date(manager.dateOfBirth);
					var day = date.getDate();
				    var month = date.getMonth() + 1;
				    var year = date.getFullYear();
					$('#dateOfBirth').val([day, month, year].join('.'));
					
					if(manager.gender === "MALE")
						$('#gender').val("Muško");
					else
						$('#gender').val("Žensko");
					$('#mojePorudzbineTitleDiv').hide();
					$('#filterDiv').hide();
					$('#articlesTable').hide();
					$('#divBetweenProfileAndOrders').hide();
					$('#filterDiv2').hide();
					$('#searchDiv').hide();
					$('#ordersOnWaitTitleDiv').hide();
					$('#ordersOnWaitTable').hide();
					$('#actionBtnsDiv').hide();
					$('#actionBtnsDiv2').hide();
					$('#actionBtnsDiv3').hide();
					$('#divBetweenButtonAndMyOrders2').hide();
					$('#divBetweenButtonAndOrders').hide();
					$('#divBetweenButtonAndMyOrders').hide();
				}
			}
	})
}

function loadPageForDeliverer() {
	$.get({
			url: '../rest/deliverers/loggedInDeliverer',
			success: function(deliverer){
				if(deliverer.username !== "") {
					$('#notDeliveredOrders').hide();
					$('#labelNotDeliveredOrders').hide();
					$('#buyerType').hide();
					$('#buyerTypeInput').hide();
					$('#managerBtn').hide();
					$('#actionBtnsDiv3').hide();
					$('#divBetweenButtonAndMyOrders2').hide();
					$('#users').hide();
					$('#promoText').html("&nbsp" + deliverer.firstName + " " + deliverer.lastName);
					$('#username').val(deliverer.username);
					$('#email').val(deliverer.email);
					
					let date = new Date(deliverer.dateOfBirth);
					var day = date.getDate();
				    var month = date.getMonth() + 1;
				    var year = date.getFullYear();
					$('#dateOfBirth').val([day, month, year].join('.'));
					
					if(deliverer.gender === "MALE")
						$('#gender').val("Muško");
					else
						$('#gender').val("Žensko");
						
					$.get({
							url: '../rest/orders/',
							success: function(orders){
								for(let order of orders)
									if(order.deliverer.username === deliverer.username && (order.status === "TRANSPORTING" || order.status === "DELIVERED"))
										addOrderToTableDelivererMine(order);
							}
					})	
					
					$.get({
							url: '../rest/orders/',
							success: function(orders){
								for(let order of orders)
									if(order.deliverer.username === "" && order.status === "WAITING_FOR_DELIVERER")
										addOrderToTableDelivererWaiting(order);
							}
					})	
				}
			}
	})
}

function loadPageForBuyer() {
	$.get({
			url: '../rest/buyers/loggedInBuyer',
			success: function(buyer){
				if(buyer.username !== "") {
					$('#managerBtn').hide();
					$('#users').hide();
					$('#promoText').html("&nbsp" + buyer.firstName + " " + buyer.lastName);
					$('#username').val(buyer.username);
					$('#email').val(buyer.email);
					if(buyer.type.name === "GOLDEN")
						$('#buyerTypeInput').val("Zlatni");
					else if(buyer.type.name === "SILVER")
						$('#buyerTypeInput').val("Srebrni");
					else if(buyer.type.name === "BRONZE")
						$('#buyerTypeInput').val("Bronzani");
					
					let date = new Date(buyer.dateOfBirth);
					var day = date.getDate();
				    var month = date.getMonth() + 1;
				    var year = date.getFullYear();
					$('#dateOfBirth').val([day, month, year].join('.'));
					
					if(buyer.gender === "MALE")
						$('#gender').val("Muško");
					else
						$('#gender').val("Žensko");
					$('#filterDiv2').hide();
					$('#actionBtnsDiv2').hide();
					$('#ordersOnWaitTitleDiv').hide();
					$('#actionBtnsDiv').hide();
					$('#divBetweenButtonAndOrders').hide();
					$('#ordersOnWaitTable').hide();
					$("#articlesTable th:eq(3)").remove();
					
					$.get({
							url: '../rest/orders/',
							success: function(orders){
								for(let order of orders)
									if(order.buyer.username === buyer.username)
										addOrderToTableBuyer(order);
							}
					})	
				}
			}
	})
}

function sortTable(n) {
	var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	table = document.getElementById("restaurantsTable");
	switching = true;
	dir = "asc";
	while (switching) {
		switching = false;
		rows = table.rows;
		for (i = 1; i < (rows.length - 1); i++) {
			shouldSwitch = false;
			x = rows[i].getElementsByTagName("TD")[n];
			y = rows[i + 1].getElementsByTagName("TD")[n];

			if (dir == "asc") {
				if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
					shouldSwitch = true;
					break;
				}
			} else if (dir == "desc") {
				if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
					shouldSwitch = true;
					break;
				}
			}
		}
		if (shouldSwitch) {
			rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			switching = true;
			switchcount ++;
		} else {
			if (switchcount == 0 && dir == "asc") {
				dir = "desc";
				switching = true;
			}
		}
	}
}