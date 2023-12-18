baseUrl = new URL(window.location.href).origin;
var menuData = document.getElementById('menu__item__data');
var menuMobileData = document.getElementById('menu__mobile__item__data');
var menuMobileTabData = document.getElementById('menu__mobile__tab__item__data');
var menuTitleCategoryData = document.getElementById('menu_title_category');
var menuTitleBrandData = document.getElementById('menu_title_brand');
var menuMobileBrandData = document.getElementById('menu__mobile__item__data__brand');
var menuCart = document.getElementById('menu__cart');

$(document).ready(function () {
	'use strict';

	showCategory();
	showBrand();
	getCountCart();

	menuData.innerHTML = ``;
	menuMobileData.innerHTML = ``;
	menuMobileTabData.innerHTML = ``;
	menuTitleCategoryData.innerHTML = ``;

	function showCategory() {
		axios({
			method: 'GET',
			contentType: "application/json",
			url: baseUrl + "/beauty/layout/category"
		})
			.then(response => {
				response.data.forEach(item => {
					var itemMenu = document.createElement('li');
					itemMenu.innerHTML = `<a href="/beauty/shop?category_id=` + item.id + `">` + item.name + `</a>`;

					menuData.appendChild(itemMenu);

					var itemMenuMobile = document.createElement('li');
					itemMenuMobile.innerHTML = `<a href="/beauty/shop?category_id=` + item.id + `">` + item.name + `</a>`;

					menuMobileData.appendChild(itemMenuMobile);

					var itemMenuMobileTab = document.createElement('li');
					itemMenuMobileTab.innerHTML = `<a href="/beauty/shop?category_id=` + item.id + `">` + item.name + `</a>`;

					menuMobileTabData.appendChild(itemMenuMobileTab);

					var itemMenuTitleCategory = document.createElement('li');
					itemMenuTitleCategory.innerHTML = `<a href="/beauty/shop?category_id=` + item.id + `">` + item.name + `</a>`;

					menuTitleCategoryData.appendChild(itemMenuTitleCategory);
				});
			});
	}

	menuTitleBrandData.innerHTML = ``;
	menuMobileBrandData.innerHTML = ``;

	function showBrand() {
		axios({
			method: 'GET',
			contentType: "application/json",
			url: baseUrl + "/beauty/layout/brand"
		})
			.then(response => {
				response.data.forEach(item => {
					var itemTitleBrand = document.createElement('li');
					itemTitleBrand.innerHTML = `<a href="/beauty/shop?brand_id=` + item.id + `">` + item.name + `</a>`;

					menuTitleBrandData.appendChild(itemTitleBrand);

					var itemTitleMobileBrand = document.createElement('li');
					itemTitleMobileBrand.innerHTML = `<a href="/beauty/shop?brand_id=` + item.id + `">` + item.name + `</a>`;

					menuMobileBrandData.appendChild(itemTitleMobileBrand);
				});
			});
	}

	function getCountCart() {
		axios({
			method: 'GET',
			contentType: "application/json",
			url: baseUrl + "/beauty/layout/get-cart-items"
		})
			.then(response => {
				var totalPrice = 0;
				for (let i = 0; i < response.data.length; i++) {
					var priceDiscount;
					if (response.data[i].discount <= 0) {
						totalPrice += response.data[i].price * response.data[i].quantity;
					} else {
						totalPrice += (response.data[i].price - (response.data[i].price * response.data[i].discount * 0.01)) * response.data[i].quantity;
					}
				}
				menuCart.innerHTML = `
					<div class="dropdown-menu dropdown-menu-right">
						<div class="dropdown-cart-products" id="menu__cart__items"></div>
						<div class="dropdown-cart-total">
							<span>Tổng tiền</span> 
							<span class="cart-total-price">`+ formatVND(totalPrice, 0, 'POINT', 3, 'POINT') + 'VND' + `</span>
						</div>
						<div class="dropdown-cart-action">
							<a href="/beauty/cart" class="btn btn-primary">Giỏ hàng</a> 
							<a href="/beauty/check-out" class="btn btn-outline-primary-2">
								<span>Thanh toán</span>
								<i class="icon-long-arrow-right"></i>
							</a>
						</div>
					</div>`;

				var cart = document.createElement('div');
				if (response.data.length === 0) {
					cart.innerHTML = `
						<a href="#" class="dropdown-toggle"> 
							<i class="icon-shopping-cart"></i> 
							<span class="cart-count">`+ response.data.length + `</span>
							<span class="cart-txt">Giỏ hàng</span>
						</a>`;
				} else {
					var menuCartItems = document.getElementById('menu__cart__items');
					menuCartItems.innerHTML = ``;
					for (let i = 0; i < response.data.length; i++) {
						var priceDiscount;
						if (response.data[i].discount <= 0) {
							priceDiscount = formatVND(response.data[i].price, 0, 'POINT', 3, 'POINT') + 'VND';
						} else {
							priceDiscount = formatVND(response.data[i].price - (response.data[i].price * response.data[i].discount * 0.01), ' VND');
						}
						var productItem = document.createElement('div');
						productItem.className = "product";
						productItem.innerHTML += `
									<div class="product-cart-details">
										<h4 class="product-title">
											<a href="/beauty/product-detail?id=`+ response.data[i].id + `">` + response.data[i].name + `</a>
										</h4>
										<span class="cart-product-info"> 
											<span class="cart-product-qty">`+ response.data[i].quantity + `</span> 
											x `+ priceDiscount + `
										</span>
									</div>
									<figure class="product-image-container">
										<a href="/beauty/product-detail?id=`+ response.data[i].id + `" class="product-image"> 
											<img src="/uploads/`+ response.data[i].image + `" alt="product">
										</a>
									</figure>
									<a onclick="removeCartItem(` + response.data[i].id + `, '` + response.data[i].name + `')"
											class="btn-remove" title="Remove Product">
										<i class="icon-close"></i>
									</a>`;
						menuCartItems.appendChild(productItem);
					}

					cart.innerHTML = `
						<a href="#" class="dropdown-toggle" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" data-display="static"> 
							<i class="icon-shopping-cart"></i> 
							<span class="cart-count">`+ response.data.length + `</span>
							<span class="cart-txt">Giỏ hàng</span>
						</a>`;
				}
				menuCart.appendChild(cart);
			});
	}

	function formatVND(n, currency) {
		return n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,') + currency;
	}
})

function removeCartItem(id, name) {
	Swal.fire({
		title: 'Xoá sản phẩm?',
		text: "Xoá sản phẩm " + name + " ra khỏi giỏ hàng ?",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Xác nhận!'
	}).then((result) => {
		if (result.isConfirmed) {
			axios.delete(baseUrl + '/beauty/cart/remove/' + id)
				.then(function (response) {
					Swal.fire({
						title: 'Thông báo',
						text: response.data,
						icon: 'success',
						confirmButtonColor: '#3085d6',
						confirmButtonText: 'Xác nhận'
					}).then((result) => {
						if (result.isConfirmed) {
							location.reload(true);
						}
					})
				})
		}
	})
}