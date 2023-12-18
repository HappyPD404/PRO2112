var baseUrl = new URL(window.location.href).origin;
showCountOrderDate();
showRevenueDate();
showCustomersDate();
showTotailOrderDate();
showSalerDate();

function formatVND(n, currency) {
	return n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,') + currency;
}

async function showCountOrderDate() {
	let showFilter = document.getElementById('showFilterCountOrder');
	showFilter.innerText = ``;
	showFilter.innerText = `| Theo ngày`;
	let countOrder = document.getElementById('countHomeOrder');
	let countOrderDateNow = await axios.get(baseUrl + "/home/count-order-date-now");
	let countOrderDateYe = await axios.get(baseUrl + "/home/count-order-date-ye");
	countOrder.innerHTML = ``;
	let percent = 0;
	if (countOrderDateNow.data > countOrderDateYe.data) {
		if (countOrderDateYe.data === 0 || countOrderDateYe.data === null) {
			countOrder.innerHTML = `<h6>` + countOrderDateNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">100%</span>
						<span class="text-muted small pt-2 ps-1">Không có đơn hàng ngày hôm qua</span>`;
		} else {
			percent = (countOrderDateYe.data / countOrderDateNow.data) * 100;
			countOrder.innerHTML = `<h6>` + countOrderDateNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Tăng so với hôm qua</span>`;
		}

	} else if (countOrderDateNow.data <= countOrderDateYe.data) {
		if (countOrderDateNow.data === 0 || countOrderDateNow.data === null) {
			countOrder.innerHTML = `<h6>0</h6>
						<span class="text-danger small pt-1 fw-bold">100%</span> 
						<span class="text-muted small pt-2 ps-1">Không có đơn hàng ngày hôm nay</span>`;
		} else {
			percent = (countOrderDateNow.data / countOrderDateYe.data) * 100;
			countOrder.innerHTML = `<h6>` + countOrderDateNow.data + `</h6>
						<span class="text-danger small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Giảm so với hôm qua</span>`;
		}
	}
}

async function showCountOrderMonth() {
	let showFilter = document.getElementById('showFilterCountOrder');
	showFilter.innerText = ``;
	showFilter.innerText = `| Theo tháng`;
	let countOrder = document.getElementById('countHomeOrder');
	let countOrderMonthNow = await axios.get(baseUrl + "/home/count-order-month-now");
	let countOrderMonthYe = await axios.get(baseUrl + "/home/count-order-month-ye");
	countOrder.innerHTML = ``;
	let percent = 0;
	if (countOrderMonthNow.data > countOrderMonthYe.data) {
		if (countOrderMonthYe.data === 0 || countOrderMonthYe.data === null) {
			countOrder.innerHTML = `<h6>` + countOrderMonthNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">100%</span>
						<span class="text-muted small pt-2 ps-1">Không có đơn hàng tháng trước</span>`;
		} else {
			percent = (countOrderMonthYe.data / countOrderMonthNow.data) * 100;
			countOrder.innerHTML = `<h6>` + countOrderMonthNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Tăng so với tháng trước</span>`;
		}

	} else if (countOrderMonthNow.data <= countOrderMonthYe.data) {
		if (countOrderMonthNow.data === 0 || countOrderMonthNow.data === null) {
			countOrder.innerHTML = `<h6>0</h6>
						<span class="text-danger small pt-1 fw-bold">100%</span> 
						<span class="text-muted small pt-2 ps-1">Không có đơn hàng tháng nay</span>`;
		} else {
			percent = (countOrderMonthNow.data / countOrderMonthYe.data) * 100;
			countOrder.innerHTML = `<h6>` + countOrderMonthNow.data + `</h6>
						<span class="text-danger small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Giảm so với tháng trước</span>`;
		}
	}
}

async function showCountOrderYear() {
	let showFilter = document.getElementById('showFilterCountOrder');
	showFilter.innerText = ``;
	showFilter.innerText = `| Theo năm`;
	let countOrder = document.getElementById('countHomeOrder');
	let countOrderYearNow = await axios.get(baseUrl + "/home/count-order-year-now");
	let countOrderYearYe = await axios.get(baseUrl + "/home/count-order-year-ye");
	countOrder.innerHTML = ``;
	let percent = 0;
	if (countOrderYearNow.data > countOrderYearYe.data) {
		if (countOrderYearYe.data === 0 || countOrderYearYe.data === null) {
			countOrder.innerHTML = `<h6>` + countOrderYearNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">100%</span>
						<span class="text-muted small pt-2 ps-1">Không có đơn hàng năm trước</span>`;
		} else {
			percent = (countOrderYearYe.data / countOrderYearNow.data) * 100;
			countOrder.innerHTML = `<h6>` + countOrderYearNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Tăng so với năm trước</span>`;
		}

	} else if (countOrderYearNow.data <= countOrderYearYe.data) {
		if (countOrderYearNow.data === 0 || countOrderYearNow.data === null) {
			countOrder.innerHTML = `<h6>0</h6>
						<span class="text-danger small pt-1 fw-bold">100%</span> 
						<span class="text-muted small pt-2 ps-1">Không có đơn hàng năm nay</span>`;
		} else {
			percent = (countOrderYearNow.data / countOrderYearYe.data) * 100;
			countOrder.innerHTML = `<h6>` + countOrderYearNow.data + `</h6>
						<span class="text-danger small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Giảm so với năm trước</span>`;
		}
	}
}

async function showRevenueDate() {
	let showFilter = document.getElementById('showFilterRevenue');
	showFilter.innerText = ``;
	showFilter.innerText = `| Theo ngày`;
	let revenue = document.getElementById('homeRevenue');
	let revenueDateNow = await axios.get(baseUrl + "/home/revenue-date-now");
	let revenueDateYe = await axios.get(baseUrl + "/home/revenue-date-ye");
	revenue.innerHTML = ``;
	let percent = 0;
	if (revenueDateNow.data > revenueDateYe.data) {
		if (revenueDateYe.data === 0 || revenueDateYe.data === null) {
			revenue.innerHTML = `<h6>` + formatVND(revenueDateNow.data, 0, 'POINT', 3, 'POINT') + 'VND' +`</h6>
						<span class="text-success small pt-1 fw-bold">100%</span>
						<span class="text-muted small pt-2 ps-1">Không có tổng tiền ngày hôm qua</span>`;
		} else {
			percent = (revenueDateYe.data / revenueDateNow.data) * 100;
			revenue.innerHTML = `<h6>` + formatVND(revenueDateNow.data, 0, 'POINT', 3, 'POINT') + 'VND' +`</h6>
						<span class="text-success small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Tăng so với hôm qua</span>`;
		}

	} else if (revenueDateNow.data <= revenueDateYe.data) {
		if (revenueDateNow.data === 0 || revenueDateNow.data === null) {
			revenue.innerHTML = `<h6>0</h6>
						<span class="text-danger small pt-1 fw-bold">100%</span> 
						<span class="text-muted small pt-2 ps-1">Không có tổng tiền ngày hôm nay</span>`;
		} else {
			percent = (revenueDateNow.data / revenueDateYe.data) * 100;
			revenue.innerHTML = `<h6>` + formatVND(revenueDateNow.data, 0, 'POINT', 3, 'POINT') + 'VND' +`</h6>
						<span class="text-danger small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Giảm so với hôm qua</span>`;
		}
	}
}

async function showRevenueMonth() {
	let showFilter = document.getElementById('showFilterRevenue');
	showFilter.innerText = ``;
	showFilter.innerText = `| Theo tháng`;
	let revenue = document.getElementById('homeRevenue');
	let revenueMonthNow = await axios.get(baseUrl + "/home/revenue-month-now");
	let revenueMonthYe = await axios.get(baseUrl + "/home/revenue-month-ye");
	revenue.innerHTML = ``;
	let percent = 0;
	if (revenueMonthNow.data > revenueMonthYe.data) {
		if (revenueMonthYe.data === 0 || revenueMonthYe.data === null) {
			revenue.innerHTML = `<h6>` + formatVND(revenueMonthNow.data, 0, 'POINT', 3, 'POINT') + 'VND' +`</h6>
						<span class="text-success small pt-1 fw-bold">100%</span>
						<span class="text-muted small pt-2 ps-1">Không có tổng tiền tháng trước</span>`;
		} else {
			percent = (revenueMonthYe.data / revenueMonthNow.data) * 100;
			revenue.innerHTML = `<h6>` + formatVND(revenueMonthNow.data, 0, 'POINT', 3, 'POINT') + 'VND' + `</h6>
						<span class="text-success small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Tăng so với tháng trước</span>`;
		}

	} else if (revenueMonthNow.data <= revenueMonthYe.data) {
		if (revenueMonthNow.data === 0 || revenueMonthNow.data === null) {
			revenue.innerHTML = `<h6>0</h6>
						<span class="text-danger small pt-1 fw-bold">100%</span> 
						<span class="text-muted small pt-2 ps-1">Không có tổng tiền tháng nay</span>`;
		} else {
			percent = (revenueMonthNow.data / revenueMonthYe.data) * 100;
			revenue.innerHTML = `<h6>` + formatVND(revenueMonthNow.data, 0, 'POINT', 3, 'POINT') + 'VND' +`</h6>
						<span class="text-danger small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Giảm so với tháng trước</span>`;
		}
	}
}

async function showRevenueYear() {
	let showFilter = document.getElementById('showFilterRevenue');
	showFilter.innerText = ``;
	showFilter.innerText = `| Theo năm`;
	let revenue = document.getElementById('homeRevenue');
	let revenueYearNow = await axios.get(baseUrl + "/home/revenue-year-now");
	let revenueYearYe = await axios.get(baseUrl + "/home/revenue-year-ye");
	revenue.innerHTML = ``;
	let percent = 0;
	if (revenueYearNow.data > revenueYearYe.data) {
		if (revenueYearYe.data === 0 || revenueYearYe.data === null) {
			revenue.innerHTML = `<h6>` + formatVND(revenueYearNow.data, 0, 'POINT', 3, 'POINT') + 'VND' +`</h6>
						<span class="text-success small pt-1 fw-bold">100%</span>
						<span class="text-muted small pt-2 ps-1">Không có tổng tiền năm trước</span>`;
		} else {
			percent = (revenueYearYe.data / revenueYearNow.data) * 100;
			revenue.innerHTML = `<h6>` + formatVND(revenueYearNow.data, 0, 'POINT', 3, 'POINT') +  'VND' +`</h6>
						<span class="text-success small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Tăng so với năm trước</span>`;
		}

	} else if (revenueYearNow.data <= revenueYearYe.data) {
		if (revenueYearNow.data === 0 || revenueYearNow.data === null) {
			revenue.innerHTML = `<h6>0</h6>
						<span class="text-danger small pt-1 fw-bold">100%</span> 
						<span class="text-muted small pt-2 ps-1">Không có tổng tiền năm nay</span>`;
		} else {
			percent = (revenueYearNow.data / revenueYearYe.data) * 100;
			revenue.innerHTML = `<h6>` + formatVND(revenueYearNow.data, 0, 'POINT', 3, 'POINT') + 'VND' +`</h6>
						<span class="text-danger small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Giảm so với năm trước</span>`;
		}
	}
}

async function showCustomersDate() {
	let showFilter = document.getElementById('showFilterCustomers');
	showFilter.innerText = ``;
	showFilter.innerText = `| Theo ngày`;
	let customers = document.getElementById('homeCustomers');
	let customersDateNow = await axios.get(baseUrl + "/home/customers-date-now");
	let customersDateYe = await axios.get(baseUrl + "/home/customers-date-ye");
	customers.innerHTML = ``;
	let percent = 0;
	if (customersDateNow.data > customersDateYe.data) {
		if (customersDateYe.data === 0 || customersDateYe.data === null) {
			customers.innerHTML = `<h6>` + customersDateNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">100%</span>
						<span class="text-muted small pt-2 ps-1">Không có người dùng ngày hôm qua</span>`;
		} else {
			percent = (customersDateYe.data / customersDateNow.data) * 100;
			customers.innerHTML = `<h6>` + customersDateNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Tăng so với hôm qua</span>`;
		}

	} else if (customersDateNow.data <= customersDateYe.data) {
		if (customersDateNow.data === 0 || customersDateNow.data === null) {
			customers.innerHTML = `<h6>0</h6>
						<span class="text-danger small pt-1 fw-bold">100%</span> 
						<span class="text-muted small pt-2 ps-1">Không có người dùng ngày hôm nay</span>`;
		} else {
			percent = (customersDateNow.data / customersDateYe.data) * 100;
			customers.innerHTML = `<h6>` + customersDateNow.data + `</h6>
						<span class="text-danger small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Giảm so với hôm qua</span>`;
		}
	}
}

async function showCustomersMonth() {
	let showFilter = document.getElementById('showFilterCustomers');
	showFilter.innerText = ``;
	showFilter.innerText = `| Theo tháng`;
	let customers = document.getElementById('homeCustomers');
	let customersMonthNow = await axios.get(baseUrl + "/home/customers-month-now");
	let customersMonthYe = await axios.get(baseUrl + "/home/customers-month-ye");
	customers.innerHTML = ``;
	let percent = 0;
	if (customersMonthNow.data > customersMonthYe.data) {
		if (customersMonthYe.data === 0 || customersMonthYe.data === null) {
			customers.innerHTML = `<h6>` + customersMonthNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">100%</span>
						<span class="text-muted small pt-2 ps-1">Không có người dùng tháng trước</span>`;
		} else {
			percent = (customersMonthYe.data / customersMonthNow.data) * 100;
			customers.innerHTML = `<h6>` + customersMonthNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Tăng so với tháng trước</span>`;
		}

	} else if (customersMonthNow.data <= customersMonthYe.data) {
		if (customersMonthNow.data === 0 || customersMonthNow.data === null) {
			customers.innerHTML = `<h6>0</h6>
						<span class="text-danger small pt-1 fw-bold">100%</span> 
						<span class="text-muted small pt-2 ps-1">Không có người dùng tháng này</span>`;
		} else {
			percent = (customersMonthNow.data / customersMonthYe.data) * 100;
			customers.innerHTML = `<h6>` + customersMonthNow.data + `</h6>
						<span class="text-danger small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Giảm so với tháng trước</span>`;
		}
	}
}

async function showCustomersYear() {
	let showFilter = document.getElementById('showFilterCustomers');
	showFilter.innerText = ``;
	showFilter.innerText = `| Theo năm`;
	let customers = document.getElementById('homeCustomers');
	let customersYearNow = await axios.get(baseUrl + "/home/customers-year-now");
	let customersYearYe = await axios.get(baseUrl + "/home/customers-year-ye");
	customers.innerHTML = ``;
	let percent = 0;
	if (customersYearNow.data > customersYearYe.data) {
		if (customersYearYe.data === 0 || customersYearYe.data === null) {
			customers.innerHTML = `<h6>` + customersYearNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">100%</span>
						<span class="text-muted small pt-2 ps-1">Không có người dùng năm trước</span>`;
		} else {
			percent = (customersYearYe.data / customersYearNow.data) * 100;
			customers.innerHTML = `<h6>` + customersYearNow.data + `</h6>
						<span class="text-success small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Tăng so với năm trước</span>`;
		}

	} else if (customersYearNow.data <= customersYearYe.data) {
		if (customersYearNow.data === 0 || customersYearNow.data === null) {
			customers.innerHTML = `<h6>0</h6>
						<span class="text-danger small pt-1 fw-bold">100%</span> 
						<span class="text-muted small pt-2 ps-1">Không có người dùng năm này</span>`;
		} else {
			percent = (customersYearNow.data / customersYearYe.data) * 100;
			customers.innerHTML = `<h6>` + customersYearNow.data + `</h6>
						<span class="text-danger small pt-1 fw-bold">`+ percent + ` %</span>
						<span class="text-muted small pt-2 ps-1">Giảm so với năm trước</span>`;
		}
	}
}

function showTotailOrderDate() {
	let showFilter = document.getElementById('showFilterTotailOrder');
	showFilter.innerText = ``;
	showFilter.innerText = `| Ngày hiện tại`;
	let homeTotailOrder = document.getElementById('homeTotailOrder');
	homeTotailOrder.innerHTML = ``;
	axios.get(baseUrl + "/home/totail-order-date-now")
		.then(function(response) {
			response.data.forEach(item => {
				var wrapper = document.createElement('tr');
				wrapper.innerHTML = `<td>` + item[0] + `</td>
										<td>`+ item[1] + `</td>
										<td>`+ item[2] + `</td>
										<td>`+ item[3] + `</td>
										<td>`+ formatVND(item[4], 0, 'POINT', 3, 'POINT') + 'VND' +`</td>`;
				homeTotailOrder.append(wrapper);
			});
		});
}

function showTotailOrderMonth() {
	let showFilter = document.getElementById('showFilterTotailOrder');
	showFilter.innerText = ``;
	showFilter.innerText = `| Tháng hiện tại`;
	let homeTotailOrder = document.getElementById('homeTotailOrder');
	homeTotailOrder.innerHTML = ``;
	axios.get(baseUrl + "/home/totail-order-month-now")
		.then(function(response) {
			response.data.forEach(item => {
				var wrapper = document.createElement('tr');
				wrapper.innerHTML = `<td>` + item[0] + `</td>
										<td>`+ item[1] + `</td>
										<td>`+ item[2] + `</td>
										<td>`+ item[3] + `</td>
										<td>`+ formatVND(item[4], 0, 'POINT', 3, 'POINT') + 'VND' + `</td>`;
				homeTotailOrder.append(wrapper);
			});
		});
}

function showTotailOrderYear() {
	let showFilter = document.getElementById('showFilterTotailOrder');
	showFilter.innerText = ``;
	showFilter.innerText = `| Năm hiện tại`;
	let homeTotailOrder = document.getElementById('homeTotailOrder');
	homeTotailOrder.innerHTML = ``;
	axios.get(baseUrl + "/home/totail-order-year-now")
		.then(function(response) {
			response.data.forEach(item => {
				var wrapper = document.createElement('tr');
				wrapper.innerHTML = `<td>` + item[0] + `</td>
										<td>`+ item[1] + `</td>
										<td>`+ item[2] + `</td>
										<td>`+ item[3] + `</td>
										<td>`+ formatVND(item[4], 0, 'POINT', 3, 'POINT') + 'VND' +`</td>`;
				homeTotailOrder.append(wrapper);
			});
		});
}

function showSalerDate() {
	let showFilter = document.getElementById('showFilterSaler');
	showFilter.innerText = ``;
	showFilter.innerText = `| Ngày hiện tại`;
	let homeSaler = document.getElementById('homeSaler');
	homeSaler.innerHTML = ``;
	axios.get(baseUrl + "/home/saler-date-now")
		.then(function(response) {
			response.data.forEach(item => {
				var wrapper = document.createElement('tr');
				wrapper.innerHTML = `<td>
											<div class="card" style="max-width: 150px;">
												<img src="/uploads/`+ item[0] + `"
													class="card-img-top" style="max-width: 150px;" alt="...">
											</div>
										</td>
										<td>`+ item[1] + `</td>
										<td>`+ item[2] + `</td>
										<td>`+ formatVND(item[3], 0, 'POINT', 3, 'POINT') + 'VND' +`</td>`;
				homeSaler.append(wrapper);
			});
		});
}

function showSalerMonth(){
	let showFilter = document.getElementById('showFilterSaler');
	showFilter.innerText = ``;
	showFilter.innerText = `| Tháng hiện tại`;
	let homeSaler = document.getElementById('homeSaler');
	homeSaler.innerHTML = ``;
	axios.get(baseUrl + "/home/saler-month-now")
		.then(function(response) {
			response.data.forEach(item => {
				var wrapper = document.createElement('tr');
				wrapper.innerHTML = `<td>
											<div class="card" style="max-width: 150px;">
												<img src="/uploads/`+ item[0] + `"
													class="card-img-top" style="max-width: 150px;" alt="...">
											</div>
										</td>
										<td>`+ item[1] + `</td>
										<td>`+ item[2] + `</td>
										<td>`+ formatVND(item[3], 0, 'POINT', 3, 'POINT') + 'VND' +`</td>`;
				homeSaler.append(wrapper);
			});
		});
}

function showSalerYear(){
	let showFilter = document.getElementById('showFilterSaler');
	showFilter.innerText = ``;
	showFilter.innerText = `| Năm hiện tại`;
	let homeSaler = document.getElementById('homeSaler');
	homeSaler.innerHTML = ``;
	axios.get(baseUrl + "/home/saler-year-now")
		.then(function(response) {
			response.data.forEach(item => {
				var wrapper = document.createElement('tr');
				wrapper.innerHTML = `<td>
											<div class="card" style="max-width: 150px;">
												<img src="/uploads/`+ item[0] + `"
													class="card-img-top" style="max-width: 150px;" alt="...">
											</div>
										</td>
										<td>`+ item[1] + `</td>
										<td>`+ item[2] + `</td>
										<td>`+ formatVND(item[3], 0, 'POINT', 3, 'POINT') + 'VND' + `</td>`;
				homeSaler.append(wrapper);
			});
		});
}