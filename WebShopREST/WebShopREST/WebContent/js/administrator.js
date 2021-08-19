$(document).ready(function(){
	loadBuyers();
	
	$('#users').click(function(){
			window.location.href='administrator.html';
	});
	$('#restaurants').click(function(){
		window.location.href='index.html';
	});
	$('#profile').click(function(){
		window.location.href='userProfile.html';
	});
	$('#delivererAdd').click(function(){
		window.location.href='delivererRegister.html';
	});
	$('#managerAdd').click(function(){
		window.location.href='managerRegister.html';
	});
	
	$('#buyers').click(function(){
		loadBuyers();
	});
	
	$('#managers').click(function(){
		loadManagers();
	});
	
	$('#deliverers').click(function(){
		loadDeliverers();
	});
	
	$('#administrators').click(function(){
		loadAdministrators();
	});
	
	$("#buyerType").change(function() {
		filter();
	});
	
	$("#searchIcon").click(function() {
		search();
	});
	
	$("#removeUser").click(function() {
		removeUser();
	})
});

function removeUser() {
	$('tr.selected').remove();
}

function selectedRow() {
	return function() {
		$('tr.selected').removeClass('selected');
		$(this).addClass('selected');
	};
}

function loadBuyers() {
	$.get({
		url: '../rest/buyers/',
		success: function(buyers){
			$('#searchName').val("");
			$('#searchSurname').val("");
			$('#searchUsername').val("");
			$("#buyerType").attr('disabled', false);
			$("#buyers").attr('style', 'background-color: rgb(140, 140, 140)');
			$("#managers").attr('style', 'background-color: rgb(190, 190, 190)');
			$("#deliverers").attr('style', 'background-color: rgb(190, 190, 190)');
			$("#administrators").attr('style', 'background-color: rgb(190, 190, 190)');
			$('#tableHeader').empty();
			$('#tableBody').empty();
			let tableHeader = $('#tableHeader');
			let newRow = $('<tr>');
			let usernameTh = $('<th onclick="sortTable(0)"></th>');
			usernameTh.text("Korisničko ime");
			let passwordTh = $('<th>');
			passwordTh.text("Lozinka");
			let emailTh = $('<th>');
			emailTh.text("Email");
			let nameTh = $('<th onclick="sortTable(3)"></th>');
			nameTh.text("Ime");
			let surnameTh = $('<th onclick="sortTable(4)"></th>');
			surnameTh.text("Prezime");
			let genderTh = $('<th>');
			genderTh.text("Pol");
			let dateOfBirthTh = $('<th>');
			dateOfBirthTh.text("Datum rođenja");
			let typeTh = $('<th>');
			typeTh.text("Tip");
			let pointsTh = $('<th onclick="sortTable(8)"></th>');
			pointsTh.text("Broj bodova");
			newRow.append(usernameTh).append(passwordTh).append(emailTh).append(nameTh).append(surnameTh).append(genderTh).append(dateOfBirthTh).append(typeTh).append(pointsTh);
			tableHeader.append(newRow);
			for(let buyer of buyers)
				addBuyerToTable(buyer);
		}
	})
}

function addBuyerToTable(buyer) {
	let tableBody = $('#tableBody');
	let newRow = $('<tr>');
	
	let username = $('<td>').text(buyer.username);
	let password = $('<td>').text(buyer.password);
	let email = $('<td>').text(buyer.email);
	let name = $('<td>').text(buyer.firstName);
	let surname = $('<td>').text(buyer.lastName);
	let gender = '';
	if(buyer.gender == "MALE")
		gender = $('<td>').text('Muški');
	else
		gender = $('<td>').text('Ženski');
	var date = new Date(buyer.dateOfBirth);
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
	let dateOfBirth = $('<td>').text([day, month, year].join('.'));
	let type = '';
	if(buyer.type.name == "GOLDEN")
		type = $('<td>').text('Zlatni');
	else if(buyer.type.name == "SILVER")
		type = $('<td>').text('Srebrni');
	else
		type = $('<td>').text('Bronzani');
	let points = $('<td>').text(buyer.points);
	newRow.append(username).append(password).append(email).append(name).append(surname).append(gender).append(dateOfBirth).append(type).append(points);
	newRow.click(selectedRow());
	tableBody.append(newRow);
}

function loadManagers() {
	$.get({
		url: '../rest/managers/',
		success: function(managers){
			$('#searchName').val("");
			$('#searchSurname').val("");
			$('#searchUsername').val("");
			$("#buyerType").attr('disabled', true);
			$("#buyerType").val("Tip kupca");
			$("#buyers").attr('style', 'background-color: rgb(190, 190, 190)');
			$("#managers").attr('style', 'background-color: rgb(140, 140, 140)');
			$("#deliverers").attr('style', 'background-color: rgb(190, 190, 190)');
			$("#administrators").attr('style', 'background-color: rgb(190, 190, 190)');
			$('#tableHeader').empty();
			$('#tableBody').empty();
			let tableHeader = $('#tableHeader');
			let newRow = $('<tr>');
			let usernameTh = $('<th onclick="sortTable(0)"></th>');
			usernameTh.text("Korisničko ime");
			let passwordTh = $('<th>');
			passwordTh.text("Lozinka");
			let emailTh = $('<th>');
			emailTh.text("Email");
			let nameTh = $('<th onclick="sortTable(3)"></th>');
			nameTh.text("Ime");
			let surnameTh = $('<th onclick="sortTable(4)"></th>');
			surnameTh.text("Prezime");
			let genderTh = $('<th>');
			genderTh.text("Pol");
			let dateOfBirthTh = $('<th>');
			dateOfBirthTh.text("Datum rođenja");
			newRow.append(usernameTh).append(passwordTh).append(emailTh).append(nameTh).append(surnameTh).append(genderTh).append(dateOfBirthTh);
			tableHeader.append(newRow);
			for(let manager of managers)
				addManagerToTable(manager);
		}
	})
}

function addManagerToTable(manager) {
	let tableBody = $('#tableBody');
	let newRow = $('<tr>');
	
	let username = $('<td>').text(manager.username);
	let password = $('<td>').text(manager.password);
	let email = $('<td>').text(manager.email);
	let name = $('<td>').text(manager.firstName);
	let surname = $('<td>').text(manager.lastName);
	let gender = '';
	if(manager.gender == "MALE")
		gender = $('<td>').text('Muški');
	else
		gender = $('<td>').text('Ženski');
	var date = new Date(manager.dateOfBirth);
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
	let dateOfBirth = $('<td>').text([day, month, year].join('.'));
	newRow.append(username).append(password).append(email).append(name).append(surname).append(gender).append(dateOfBirth);
	newRow.click(selectedRow());
	tableBody.append(newRow);
}

function loadDeliverers() {
	$.get({
		url: '../rest/deliverers/',
		success: function(deliverers){
			$('#searchName').val("");
			$('#searchSurname').val("");
			$('#searchUsername').val("");
			$("#buyerType").attr('disabled', true);
			$("#buyerType").val("Tip kupca");
			$("#buyers").attr('style', 'background-color: rgb(190, 190, 190)');
			$("#managers").attr('style', 'background-color: rgb(190, 190, 190)');
			$("#deliverers").attr('style', 'background-color: rgb(140, 140, 140)');
			$("#administrators").attr('style', 'background-color: rgb(190, 190, 190)');
			$('#tableHeader').empty();
			$('#tableBody').empty();
			let tableHeader = $('#tableHeader');
			let newRow = $('<tr>');
			let usernameTh = $('<th onclick="sortTable(0)"></th>');
			usernameTh.text("Korisničko ime");
			let passwordTh = $('<th>');
			passwordTh.text("Lozinka");
			let emailTh = $('<th>');
			emailTh.text("Email");
			let nameTh = $('<th onclick="sortTable(3)"></th>');
			nameTh.text("Ime");
			let surnameTh = $('<th onclick="sortTable(4)"></th>');
			surnameTh.text("Prezime");
			let genderTh = $('<th>');
			genderTh.text("Pol");
			let dateOfBirthTh = $('<th>');
			dateOfBirthTh.text("Datum rođenja");
			newRow.append(usernameTh).append(passwordTh).append(emailTh).append(nameTh).append(surnameTh).append(genderTh).append(dateOfBirthTh);
			tableHeader.append(newRow);
			for(let deliverer of deliverers)
				addDelivererToTable(deliverer);
		}
	})
}

function addDelivererToTable(deliverer) {
	let tableBody = $('#tableBody');
	let newRow = $('<tr>');
	
	let username = $('<td>').text(deliverer.username);
	let password = $('<td>').text(deliverer.password);
	let email = $('<td>').text(deliverer.email);
	let name = $('<td>').text(deliverer.firstName);
	let surname = $('<td>').text(deliverer.lastName);
	let gender = '';
	if(deliverer.gender == "MALE")
		gender = $('<td>').text('Muški');
	else
		gender = $('<td>').text('Ženski');
	var date = new Date(deliverer.dateOfBirth);
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
	let dateOfBirth = $('<td>').text([day, month, year].join('.'));
	newRow.append(username).append(password).append(email).append(name).append(surname).append(gender).append(dateOfBirth);
	newRow.click(selectedRow());
	tableBody.append(newRow);
}

function loadAdministrators() {
	$.get({
		url: '../rest/administrators/',
		success: function(administrators){
			$('#searchName').val("");
			$('#searchSurname').val("");
			$('#searchUsername').val("");
			$("#buyerType").attr('disabled', true);
			$("#buyerType").val("Tip kupca");
			$("#buyers").attr('style', 'background-color: rgb(190, 190, 190)');
			$("#managers").attr('style', 'background-color: rgb(190, 190, 190)');
			$("#deliverers").attr('style', 'background-color: rgb(190, 190, 190)');
			$("#administrators").attr('style', 'background-color: rgb(140, 140, 140)');
			$('#tableHeader').empty();
			$('#tableBody').empty();
			let tableHeader = $('#tableHeader');
			let newRow = $('<tr>');
			let usernameTh = $('<th onclick="sortTable(0)"></th>');
			usernameTh.text("Korisničko ime");
			let passwordTh = $('<th>');
			passwordTh.text("Lozinka");
			let emailTh = $('<th>');
			emailTh.text("Email");
			let nameTh = $('<th onclick="sortTable(3)"></th>');
			nameTh.text("Ime");
			let surnameTh = $('<th onclick="sortTable(4)"></th>');
			surnameTh.text("Prezime");
			let genderTh = $('<th>');
			genderTh.text("Pol");
			let dateOfBirthTh = $('<th>');
			dateOfBirthTh.text("Datum rođenja");
			newRow.append(usernameTh).append(passwordTh).append(emailTh).append(nameTh).append(surnameTh).append(genderTh).append(dateOfBirthTh);
			tableHeader.append(newRow);
			for(let administrator of administrators)
				addAdministratorToTable(administrator);
		}
	})
}

function addAdministratorToTable(administrator) {
	let tableBody = $('#tableBody');
	let newRow = $('<tr>');
	
	let username = $('<td>').text(administrator.username);
	let password = $('<td>').text(administrator.password);
	let email = $('<td>').text(administrator.email);
	let name = $('<td>').text(administrator.firstName);
	let surname = $('<td>').text(administrator.lastName);
	let gender = '';
	if(administrator.gender == "MALE")
		gender = $('<td>').text('Muški');
	else
		gender = $('<td>').text('Ženski');
	var date = new Date(administrator.dateOfBirth);
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
	let dateOfBirth = $('<td>').text([day, month, year].join('.'));
	newRow.append(username).append(password).append(email).append(name).append(surname).append(gender).append(dateOfBirth);
	newRow.click(selectedRow());
	tableBody.append(newRow);
}

function filter() {
	let type = $('#buyerType').val();
	$.get({
		url: '../rest/buyers/filter?buyerType=' + type,
		success: function(buyers){
			$('#tableBody').empty();
			for(let buyer of buyers)
				addBuyerToTable(buyer);
		}
	})
}

function search() {
	let name = $('#searchName').val();
	let surname = $('#searchSurname').val();
	let username = $('#searchUsername').val();
	
	if($('#buyers').attr('style') == 'background-color: rgb(140, 140, 140)') {
		$.get({
			url: '../rest/buyers/search?searchName=' + name + '&searchSurname=' + surname + '&searchUsername=' + username,
			success: function(buyers){
				$('#tableBody').empty();
				for(let buyer of buyers)
					addBuyerToTable(buyer);
			}
		})
	}
	else if($('#managers').attr('style') == 'background-color: rgb(140, 140, 140)') {
		$.get({
			url: '../rest/managers/search?searchName=' + name + '&searchSurname=' + surname + '&searchUsername=' + username,
			success: function(managers){
				$('#tableBody').empty();
				for(let manager of managers)
					addManagerToTable(manager);
			}
		})
	}
	else if($('#deliverers').attr('style') == 'background-color: rgb(140, 140, 140)') {
		$.get({
			url: '../rest/deliverers/search?searchName=' + name + '&searchSurname=' + surname + '&searchUsername=' + username,
			success: function(deliverers){
				$('#tableBody').empty();
				for(let deliverer of deliverers)
					addDelivererToTable(deliverer);
			}
		})
	}
	else if($('#administrators').attr('style') == 'background-color: rgb(140, 140, 140)') {
		$.get({
			url: '../rest/administrators/search?searchName=' + name + '&searchSurname=' + surname + '&searchUsername=' + username,
			success: function(administrators){
				$('#tableBody').empty();
				for(let administrator of administrators)
					addAdministratorToTable(administrator);
			}
		})
	}
	$('#searchName').val("");
	$('#searchSurname').val("");
	$('#searchUsername').val("");
}

function sortTable(n) {
	var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	table = document.getElementById("usersTable");
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