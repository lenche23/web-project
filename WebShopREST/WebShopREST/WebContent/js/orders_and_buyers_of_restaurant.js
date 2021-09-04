$(document).ready(function(){
	loadPage();

	$('#logoutBtn').click(function(){
		logout();
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

	$("#orderStatusFilter").change(function() {
		filter();
	});
	
	$("#searchIcon").click(function() {
		search();
	});
	
	$('#searchIcon2').click(function(){
		searchBuyerByUsername();
	});
	
	$('#prepareBtn').click(function(){
		changeOrderToPrepare();
	});
	
	$('#waitDelivererBtn').click(function(){
		changeOrderToWaitingForDeliverer();
	});
	
	$('#declineDelivererBtn').click(function(){
		declineDeliverer();
	});
	
	$('#acceptDelivererBtn').click(function(){
		changeOrderToInTransport();
	});
});

function changeOrderToInTransport() {
	let status = $('tr.selected').find("td:eq(6)").text();
	let deliverer = $('tr.selected').find("td:eq(3)").text();
	let id = $('tr.selected').find("td:eq(0)").text();
	
	if(status === "Čeka dostavljača" && deliverer) {
		$.ajax({
			type: 'PUT',
			url: "../rest/orders/changeToInTransport/" + id,
			contentType: 'application/json',
			dataType: 'json',
			success: function(){
				$('tr.selected').find("td:eq(6)").text('U transportu');
			}
		});
	}
}

function declineDeliverer() {
	let id = $('tr.selected').find("td:eq(0)").text();
	let deliverer = $('tr.selected').find("td:eq(3)").text();
	let status = $('tr.selected').find("td:eq(6)").text();
	
	if(status === "Čeka dostavljača" && deliverer) {
		$.ajax({
			type: 'PUT',
			url: "../rest/orders/removeDeliverer/" + id,
			contentType: 'application/json',
			dataType: 'json',
			success: function(){
				$('tr.selected').find("td:eq(3)").text('-');
			}
		});
	}
}

function changeOrderToWaitingForDeliverer() {
	let status = $('tr.selected').find("td:eq(6)").text();
	let id = $('tr.selected').find("td:eq(0)").text();
	
	if(status === "U pripremi"){
		$.ajax({
			type: 'PUT',
			url: "../rest/orders/changeToWaitingForDeliverer/" + id,
			contentType: 'application/json',
			dataType: 'json',
			success: function(){
				$('tr.selected').find("td:eq(6)").text('Čeka dostavljača');
			}
		});
	}
}

function changeOrderToPrepare() {
	let status = $('tr.selected').find("td:eq(6)").text();
	let id = $('tr.selected').find("td:eq(0)").text();
	
	if(status === "Obrada"){
		$.ajax({
			type: 'PUT',
			url: "../rest/orders/changeToPreparing/" + id,
			contentType: 'application/json',
			dataType: 'json',
			success: function(){
				$('tr.selected').find("td:eq(6)").text('U pripremi');
			}
		});
	}
}

function filter() {	
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				$.get({
						url: '../rest/orders/',
						success: function(orders){
							let ordersInTable = [];
							let i = 0;
							for(let order of orders){
								if(order.restaurant.name === manager.restaurant.name){
									ordersInTable[i] = order.id;
									i++;
								}
							}
							
							$.get({
								url: '../rest/orders/filter?orderStatusFilter=' + $('#orderStatusFilter').val(),
								success: function(orders){
									$('#tableBody').empty();
									for(let order of orders)
										if(ordersInTable.includes(order.id))
											addOrderToTable(order);
										$('#inputBox').val("");
								}
							})
						}
				})			
			}
	})
}

function search() {
	let price = $('#searchPrice').val();
	let date = $('#searchDateAndTime').val();

	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				$.get({
						url: '../rest/orders/',
						success: function(orders){
							let ordersInTable = [];
							let i = 0;
							for(let order of orders){
								if(order.restaurant.name === manager.restaurant.name){
									ordersInTable[i] = order.id;
									i++;
								}
							}
							
							$.get({
								url: '../rest/orders/search?searchPrice=' + price + '&searchDateAndTime=' + date,
								success: function(orders){
									$('#tableBody').empty();
									for(let order of orders)
										if(ordersInTable.includes(order.id))
											addOrderToTable(order);
									$('#inputBox').val("");
								}
							})
						}
				})			
			}
	})
}					


function searchBuyerByUsername() {
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				$.get({
						url: '../rest/orders/',
						success: function(orders){
							let buyersInTable = [];
							let i = 0;
							for(let order of orders){
								if(!buyersInTable.includes(order.buyer.username) && order.restaurant.name === manager.restaurant.name){
									buyersInTable[i] = order.buyer.username;
									i++;
								}
							}
							
							$.get({
									url: '../rest/buyers/getBuyersByUsername?searchUsername=' + $('#inputBox').val(),
									success: function(buyers){
										$('#tableBody2').empty();
										for(let buyer of buyers)
											if(buyersInTable.includes(buyer.username))
												addBuyerToTable(buyer);
										$('#inputBox').val("");
									}
							})
						}
				})		
			}
	})
}

function loadPage() {
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				$.get({
						url: '../rest/orders/',
						success: function(orders){
							for(let order of orders)
								if(order.restaurant.name === manager.restaurant.name)
									addOrderToTable(order);
									
							let buyersInTable = [];
							let i = 0;
							for(let order of orders){
								if(!buyersInTable.includes(order.buyer.username) && order.restaurant.name === manager.restaurant.name){
									buyersInTable[i] = order.buyer.username;
									addBuyerToTable(order.buyer);
									i++;
								}
							}
						}
				})			
			}
	})
}

function addOrderToTable(order) {
	let tableBody = $('#tableBody');
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
	let buyer = $('<td>').text(order.buyer.firstName + ' ' + order.buyer.lastName);
	let deliverer;
	if(order.deliverer.username !== "")
		deliverer = $('<td>').text(order.deliverer.firstName + ' ' + order.deliverer.lastName);
	else
		deliverer = $('<td>').text('-');
	
	articles = "";
	for(let article of order.articles)
		articles = articles.concat(article.name + ', ');
	articles = articles.substring(0, articles.length-2);
	let articlesTd = $('<td>').text(articles);
	
	let date = new Date(order.dateAndTime);
	let dateAndTime = $('<td>').text(date.getHours() + ':' + date.getMinutes() + " " + date.getDate() + "." + (date.getMonth()+1) + "." + date.getFullYear());
		
	newRow.append(id).append(articlesTd).append(buyer).append(deliverer).append(dateAndTime).append(price).append(status);
	newRow.click(selectedRow());
	tableBody.append(newRow);
}

function addBuyerToTable(buyer) {
	let tableBody = $('#tableBody2');
	let newRow = $('<tr>');
	
	let username = $('<td>').text(buyer.username);
	let email = $('<td>').text(buyer.email);
	let name = $('<td>').text(buyer.firstName);
	let surname = $('<td>').text(buyer.lastName);
	let type = '';
	if(buyer.type.name == "GOLDEN")
		type = $('<td>').text('Zlatni');
	else if(buyer.type.name == "SILVER")
		type = $('<td>').text('Srebrni');
	else
		type = $('<td>').text('Bronzani');
		
	newRow.append(name).append(surname).append(username).append(email).append(type);
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

function selectedRow() {
	return function() {
		$('tr.selected').removeClass('selected');
		$(this).addClass('selected');
	};
}