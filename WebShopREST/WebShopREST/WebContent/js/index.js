$(document).ready(function(){
	loadRestaurants();

	$('#profileBtn').click(function(){
		// if (korisnik ulogovan) {
			window.location.href='userProfile.html';
		// } else {
		// 	window.location.href='login.html';
		// }
	});
	$('#registerBtn').click(function(){
		window.location.href='register.html';
	});
	$('#loginBtn').click(function(){
		window.location.href='login.html';
	});
	
	$("#tableBody tr").click(function() {
	    var selected = $(this).hasClass("selected");
	    $("#tableBody tr").removeClass("selected");
	    if(!selected)
	            $(this).addClass("selected");
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
});

function loadRestaurants() {
	$.get({
		url: '../rest/restaurants/',
		success: function(restaurants){
			for(let restaurant of restaurants)
				addRestaurantToTable(restaurant);
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
	let grade = $('<td>').text(4.5);
	let status = '';
	if(restaurant.status == "OPEN")
		status = $('<td>').text('Da');
	else
		status = $('<td>').text('Ne');
	
	newRow.append(logoTd).append(name).append(address).append(type).append(grade).append(status);
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
					addRestaurantToTable(restaurant);
			}
		})
	}
}