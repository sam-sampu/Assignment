/**
 * 
 */
$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});


// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidprodNOSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url: "ProductAPI",
		type: type,
		data: $("#formProduct").serialize(),
		dataType: "text",
		complete: function(response, status) {
			onProductSaveComplete(response.responseText, status);
		}
	});
});

function onProductSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divProductGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidprodNOSave").val("");
	$("#formProduct")[0].reset();
}

// UPDATE==========================================
$(document).on(
	"click",
	".btnUpdate",
	function(event) {
		$("#hidprodNOSave").val(
			$(this).closest("tr").find('#hidprodNOUpdate').val());
		$("#prodID").val($(this).closest("tr").find('td:eq(0)').text());
		$("#prodName").val($(this).closest("tr").find('td:eq(1)').text());
		$("#prodDes").val($(this).closest("tr").find('td:eq(2)').text());
		$("#prodType").val($(this).closest("tr").find('td:eq(3)').text());
		$("#prodAmount").val($(this).closest("tr").find('td:eq(4)').text());
	});

// REMOVE ====================================================

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url: "ProductAPI",
		type: "DELETE",
		data: "prodID=" + $(this).data("productno"),
		dataType: "text",
		complete: function(response, status) {
			onProductDeleteComplete(response.responseText, status);
		}
	});
});
function onProductDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divProductGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENTMODEL=========================================================================
function validateForm() {

	// NAME
	if ($("#prodName").val().trim() == "") {
		return "Insert product Name.";
	}
	//DESCRIPTION
	if ($("#prodDes").val().trim() == "") {
		return "Insert product description.";
	}

	// PRICE-------------------------------
	if ($("#prodAmount").val().trim() == "") {
		return "Insert product amount.";
	}
	// is numerical value
	var tmpCharge = $("#prodAmount").val().trim();
	if (!$.isNumeric(tmpCharge)) {
		return "Insert a numerical value for product amount.";
	}
	// convert to decimal price
	$("#prodAmount").val(parseFloat(tmpCharge).toFixed(2));


	return true;

}
