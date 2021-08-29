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
});

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
					$('#managerBtn').hide();
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