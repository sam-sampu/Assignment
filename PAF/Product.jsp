<%@page import="Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>GadgetBadget</title>
<link rel="stylesheet" href="bootstrap.min.css">
<link rel="stylesheet" href="formStyle.css">
<script src="jquery-3.2.1.min.js"></script>
<script src="Product.js"></script>
</head>
<body>
	<div class="container m-5">
		<center>
			<div id="alertSuccess" class="alert alert-success" style="width:300px"></div>
			<div id="alertError" class="alert alert-danger" style="width:300px"></div>
		</center>
		<div class="row">
			<div class="col-4">
			<h3 class="heading">Add Product</h3>
			<fieldset>
				<form id="formProduct" name="formProduct">		
					<input id="prodID" name="prodID" type="text"
						class="form-control form-control-sm" hidden> <br> 
										
					Product Name:
					<input id="prodName" name="prodName" type="text"
						class="form-control form-control-sm"> <br>
						
					Description: 
					 <input id="prodDes" name="prodDes" type="text"
						class="form-control form-control-sm"> <br> 
						
					Product Type: 
					 <input id="prodType" name="prodType" type="text"
						class="form-control form-control-sm"> <br> 
						
					Product Amount: 
					 <input id="prodAmount" name="prodAmount" type="text"
						class="form-control form-control-sm"> <br> 
					
						
						<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidprodNOSave" name="hidprodNOSave" value="">
						
				</form>
			</fieldset>
			</div>
			<div class="col-8" style="height:500px">
				<h3><center>Product Detail</center></h3>
					<div class="my-custom-scrollbar" id="divProductGrid">
						<%
							Product productObj = new Product();
						    out.print(productObj.getProducts());
						%>
					</div>
			</div>
		</div>
	</div>
</body>
</html>