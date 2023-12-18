var baseUrl = new URL(window.location.href).origin;

function formatVND(n, currency) {
	return n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,') + currency;
}

function viewOrder(id) {
	var viewId = document.getElementById('orderViewId');
	var viewDay = document.getElementById('orderViewCreactDay');
	var viewNote = document.getElementById('orderViewNote');
	var viewPay = document.getElementById('orderViewPay');
	var viewShip = document.getElementById('orderViewShip');
	var viewStatus = document.getElementById('orderViewStatus');
	var viewAddress = document.getElementById('orderViewAddress');
	var viewEmail = document.getElementById('orderViewEmail');
	var viewFirstName = document.getElementById('orderViewFirstName');
	var viewLastname = document.getElementById('orderViewLastName');
	var viewPhone = document.getElementById('orderViewPhone');
	var viewBody = document.getElementById('bodyViewOrder');

	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/orders/view/" + id
	})
		.then(function (response) {
			viewId.innerText = response.data.id;
			viewDay.innerText = response.data.createdDate;
			viewNote.innerText = response.data.note;

			if (response.data.pay === 'UNPAID') {
				viewPay.innerHTML = `<span class="badge bg-danger">Chưa thanh toán</span>`;
			} else {
				viewPay.innerHTML = `<span class="badge bg-success">Đã thanh toán</span>`;
			}

			if (response.data.ship === 'FREE' || response.data.ship === null) {
				viewShip.innerText = 'Miễn phí - 0 VND';
			} else if (response.data.ship === 'STANDART') {
				viewShip.innerText = 'Tiêu chuẩn - 10.000 VND';
			} else if (response.data.ship === 'EXPRESS') {
				viewShip.innerText = 'Chính xác - 20.000 VND';
			}

			if (response.data.status === 'AWAITING_CONFIRMATION' || response.data.status === null) {
				viewStatus.innerHTML = `<span class="badge bg-danger">Đang chờ xác nhận</span>`;
			} else if (response.data.status === 'CONFIRMED') {
				viewStatus.innerHTML = `<span class="badge bg-info text-dark">Đã xác nhận</span>`;
			} else if (response.data.status === 'BEING_TRANSPORTED') {
				viewStatus.innerHTML = `<span class="badge bg-warning text-dark">Đang vận chuyển</span>`;
			} else if (response.data.status === 'HAS_RECEIVED_THE_GOODS') {
				viewStatus.innerHTML = `<span class="badge bg-success">Đã giao hàng</span>`;
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
		url: baseUrl + "/orders/view-order-detail/" + id
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
					<td>
						<div class="card" style="max-width: 150px;">
							<img src="/uploads/`+ item.banner + `"
								class="card-img-top" style="max-width: 150px;" alt="...">
						</div>
					</td>
					<td>`+ item.name + `</td>
					<td>`+ formatVND(item.price, 0, 'POINT', 3, 'POINT') + 'VND' + `</td>
					<td>`+ item.quantity + `</td>
					<td>`+ item.discount + ` %</td>
					<td>`+ formatVND(totailPrice, 0, 'POINT', 3, 'POINT') + 'VND' + `</td>
				`;
				viewBody.appendChild(tr);
			})
			var tr1 = document.createElement('tr');
			tr1.innerHTML = `
					<th>Tổng tiền</th>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>`+ formatVND(totailCart, 0, 'POINT', 3, 'POINT') + 'VND' + `</td>
				`;
			viewBody.appendChild(tr1);
		})
}

function viewEditOrder(id) {
	document.getElementById("hiddenId").value = id;
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/orders/view/" + id
	})
		.then(function (response) {

			if (response.data.pay === 'UNPAID') {
				document.getElementById("pay1").checked = true;
				document.getElementById("pay2").checked = false;
			} else {
				document.getElementById("pay1").checked = false;
				document.getElementById("pay2").checked = true;
			}

			if (response.data.status === 'AWAITING_CONFIRMATION' || response.data.status === null) {
				document.getElementById("status1").checked = true;
				document.getElementById("status2").checked = false;
				document.getElementById("status3").checked = false;
				document.getElementById("status4").checked = false;
			} else if (response.data.status === 'CONFIRMED') {
				document.getElementById("status1").checked = false;
				document.getElementById("status2").checked = true;
				document.getElementById("status3").checked = false;
				document.getElementById("status4").checked = false;
			} else if (response.data.status === 'BEING_TRANSPORTED') {
				document.getElementById("status1").checked = false;
				document.getElementById("status2").checked = false;
				document.getElementById("status3").checked = true;
				document.getElementById("status4").checked = false;
			} else if (response.data.status === 'HAS_RECEIVED_THE_GOODS') {
				document.getElementById("status1").checked = false;
				document.getElementById("status2").checked = false;
				document.getElementById("status3").checked = false;
				document.getElementById("status4").checked = true;
			}
		});
}

function updateInvoice() {
	let id = document.getElementById("hiddenId").value;
	let status = document.querySelector('input[name="status"]:checked').value;
	let pay = document.querySelector('input[name="pay"]:checked').value;
	axios.put(baseUrl + "/orders/update", {
		id: id,
		pay: pay,
		status: status
	})
		.then(function (response) {
			Swal.fire({
				title: 'Thông báo',
				text: 'Đã cập nhật đơn hàng thành công',
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
