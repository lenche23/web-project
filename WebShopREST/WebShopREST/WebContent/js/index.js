$(document).ready(function(){
	loadRestaurants();
	loadPageForAdministrator();
	loadPageForManager();
	loadPageForBuyer();

	$('#restaurantAdd').click(function(){
		window.location.href='restaurantAdd.html';
	});
	
	$('#logoutBtn').click(function(){
		logout();
	});
	
	$('#managerBtn').click(function(){
		window.location.href='manager.html';
	});
	
	$('#restaurantsBtn').click(function(){
		window.location.href='index.html';
	});
	
	$('#usersBtn').click(function(){
		window.location.href='administrator.html';
	});

	$('#profileBtn').click(function(){
		window.location.href='userProfile.html';
	});
	$('#registerBtn').click(function(){
		window.location.href='register.html';
	});
	$('#loginBtn').click(function(){
		window.location.href='login.html';
	});
	
	$("#filterType").change(function() {
		filter();
	});
	
	$("#filterOpen").change(function() {
		filter();
	});
	
	$("#searchIcon").click(function() {
		search();
	});
	
	$("#removeRestaurant").click(function() {
		removeRestaurant();
	});
	
	$("#restaurantOverview").click(function() {
		viewRestaurant();
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

function loadPageForAdministrator() {
	$.get({
			url: '../rest/administrators/loggedInAdministrator',
			success: function(administrator){
				if(administrator.username !== "") {
					$('#actionBtnsDiv').css("width", "600px");
					$('#usersBtn').show();
					$('#profileBtn').show();
					$('#removeRestaurant').show();
					$('#restaurantAdd').show();
					$('#registerBtn').hide();
					$('#loginBtn').hide();
					$('#logoutBtn').show();
				}
			}
	})
}

function loadPageForBuyer() {
	$.get({
			url: '../rest/buyers/loggedInBuyer',
			success: function(buyer){
				if(buyer.username !== "") {
					$('#profileBtn').show();
					$('#registerBtn').hide();
					$('#loginBtn').hide();
					$('#logoutBtn').show();
				}
			}
	})
}

function loadPageForManager() {
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				if(manager.username !== "") {
					$('#profileBtn').show();
					if(manager.restaurant.name)
						$('#managerBtn').show();
					$('#registerBtn').hide();
					$('#loginBtn').hide();
					$('#logoutBtn').show();
				}
			}
	})
}

function removeRestaurant() {
	let name = $('tr.selected').find('td:eq(1)').text();
	$('tr.selected').remove();
	
	$.ajax({
			type: 'PUT',
			url: '../rest/restaurants/delete/' + name,
			contentType: 'application/json',
			dataType: 'json'
	});	
}

function viewRestaurant() {
	let name = $('tr.selected').find('td:eq(1)').text();
	
	$.get({
			url: '../rest/restaurants/setViewedRestaurant/' + name,
			contentType: 'application/json',
			dataType: 'json'
	});	
	
	if(name !== "")
		window.location.href='restaurant.html';
}

function selectedRow() {
	return function() {
		$('tr.selected').removeClass('selected');
		$(this).addClass('selected');
	};
}

function loadRestaurants() {
	$('#usersBtn').hide();
	$('#profileBtn').hide();
	$('#removeRestaurant').hide();
	$('#restaurantAdd').hide();
	$('#managerBtn').hide();
	$('#logoutBtn').hide();
	$.get({
		url: '../rest/restaurants/',
		success: function(restaurants){
			for(let restaurant of restaurants){
				if(!restaurant.deleted)
					addRestaurantToTable(restaurant);
			}
			sortTable(5);
		}
	})
}

function addRestaurantToTable(restaurant){
	let tableBody = $('#tableBody');
	let newRow = $('<tr>');
	
	let logoTd = $('<td>');
	let logo = $('<img class="photo" src="../images/' + restaurant.logo + '" alt="Slika">');
	logoTd.append(logo);
	let name = $('<td>').text(restaurant.name);
	let address = $('<td>').text(restaurant.location.address);
	let type = $('<td>').text(restaurant.type);
	let grade = $('<td>').text(restaurant.grade);
	let status = '';
	if(restaurant.status == "OPEN")
		status = $('<td>').text('Otvoren');
	else
		status = $('<td>').text('Zatvoren');
	
	newRow.append(logoTd).append(name).append(address).append(type).append(grade).append(status);
	newRow.click(selectedRow());
	tableBody.append(newRow);
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

function filter() {
	let type = $('#filterType').val();
	let open = $('#filterOpen').is(':checked'); 
	$.get({
		url: '../rest/restaurants/filter?filterType=' + type + '&filterOpen=' + open,
		success: function(restaurants){
			removeRestaurantsFromTable();
			for(let restaurant of restaurants)
				if(!restaurant.deleted)
					addRestaurantToTable(restaurant);
		}
	})
}

function removeRestaurantsFromTable() {
	$('#tableBody').empty();
}

function search() {
	let name = $('#searchName').val();
	let location = $('#searchLocation').val();
	let grade = $('#searchGrade').val();
	let type = $('#searchType').val();
	if(isNaN(grade)) {
		$('#searchGrade').attr('placeholder', 'Ocena mora biti broj');
		$('#searchGrade').addClass('red');
	}
	else {
		$('#searchGrade').attr('placeholder', 'Ocena restorana');
		$('#searchGrade').removeClass('red');
		$.get({
			url: '../rest/restaurants/search?searchName=' + name + '&searchLocation=' + location + '&searchGrade=' + grade + '&searchType=' + type,
			success: function(restaurants){
				removeRestaurantsFromTable();
				for(let restaurant of restaurants)
					if(!restaurant.deleted)
						addRestaurantToTable(restaurant);
			}
		})
		$('#searchName').val("");
		$('#searchLocation').val("");
		$('#searchGrade').val("");
		$('#searchType').val("Tip restorana");
	}
}