var baseUrl = new URL(window.location.href).origin;

function formatVND(n, currency) {
	return n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,') + currency;
}

function orderView(id) {
	var viewId = document.getElementById('order__view__id');
	var viewDay = document.getElementById('order__view__creact__day');
	var viewNote = document.getElementById('order__view__note');
	var viewPay = document.getElementById('order__view__pay');
	var viewShip = document.getElementById('order__view__ship');
	var viewStatus = document.getElementById('order__view__status');
	var viewAddress = document.getElementById('order__view__address');
	var viewEmail = document.getElementById('order__view__email');
	var viewFirstName = document.getElementById('order__view__first__name');
	var viewLastname = document.getElementById('order__view__last__name');
	var viewPhone = document.getElementById('order__view__phone');
	var viewBody = document.getElementById('order__view__body');

	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/beauty/order/view/" + id
	})
		.then(function (response) {
			viewId.innerText = response.data.id;
			viewDay.innerText = response.data.createdDate;
			viewNote.innerText = response.data.note;

			if (response.data.pay === 'UNPAID') {
				viewPay.innerText = 'Chưa thanh toán';
				viewPay.className = 'text-danger';
			} else {
				viewPay.innerText = 'Đã thanh toán';
				viewPay.className = 'text-success';
			}

			if (response.data.ship === 'FREE' || response.data.ship === null) {
				viewShip.innerText = 'Miễn phí - 0 VND';
			} else if (response.data.ship === 'STANDART') {
				viewShip.innerText = 'Tiêu chuẩn - 10.000 VND';
			} else if (response.data.ship === 'EXPRESS') {
				viewShip.innerText = 'Chính xác - 20.000 VND';
			}

			if (response.data.status === 'AWAITING_CONFIRMATION' || response.data.status === null) {
				viewStatus.innerText = 'Đang chờ xác nhận';
				viewStatus.className = 'text-danger';
			} else if (response.data.status === 'CONFIRMED') {
				viewStatus.innerText = 'Đã xác nhận';
				viewStatus.className = 'text-info';
			} else if (response.data.status === 'BEING_TRANSPORTED') {
				viewStatus.innerText = 'Đang vận chuyển';
				viewStatus.className = 'text-warning';
			} else if (response.data.status === 'HAS_RECEIVED_THE_GOODS') {
				viewStatus.innerText = 'Đã giao hàng';
				viewStatus.className = 'text-success';
			}

			viewAddress.innerText = response.data.orderAddress.address;
			viewEmail.innerText = response.data.orderAddress.email;
			viewFirstName.innerText = response.data.orderAddress.firstName;
			viewLastname.innerText = response.data.orderAddress.lastName;
			viewPhone.innerText = response.data.orderAddress.phone;

		});
	viewBody.innerHTML = ``;
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/beauty/order/view-order-detail/" + id
	})
		.then(function (response) {
			var totailCart = 0;
			response.data.forEach(item => {
				var tr = document.createElement('tr');
				var totailPrice = 0;
				if (item.discount > 0) {
					totailPrice += (item.price - (item.price * item.discount * 0.01)) * item.quantity;
				} else {
					totailPrice += item.price * item.quantity;
				}
				totailCart += totailPrice;
				tr.innerHTML = `
					<td class="product-col">
						<div class="product">
							<figure class="product-media">
								<img src="/uploads/`+ item.banner + `" alt="Product image">
							</figure>
							<h3 class="product-title">`+ item.name + `</h3>
						</div>
					</td>
					<td class="price-col">`+ formatVND(item.price, 0, 'POINT', 3, 'POINT') + 'VND' + `</td>
					<td class="price-col">`+ item.quantity + `</td>
					<td class="price-col">`+ item.discount + ` %</td>
					<td class="price-col">`+ formatVND(totailPrice, 0, 'POINT', 3, 'POINT') + 'VND' + `</td>
				`;
				viewBody.appendChild(tr);
			})
			var tr1 = document.createElement('tr');
			tr1.innerHTML = `
					<th>Tổng tiền</th>
					<td class="price-col"></td>
					<td class="price-col"></td>
					<td class="price-col"></td>
					<td class="price-col">`+ formatVND(totailCart, 0, 'POINT', 3, 'POINT') + 'VND' + `</td>
				`;
			viewBody.appendChild(tr1);
		})
}

function deleteoder(id) {
	Swal.fire({
		title: 'Thông báo?',
		text: "Bạn có muốn xoá sản phẩm ra khỏi đơn hàng!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Xác nhận!'
	}).then((result) => {
		if (result.isConfirmed) {
			axios.delete(baseUrl + '/beauty/order/delete/' + id)
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
				.catch(function (error) {
					Swal.fire({
						icon: 'error',
						title: 'Thông báo lỗi',
						text: error.response.data,
					})
				});
		}
	})
}
