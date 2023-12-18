var baseUrl = new URL(window.location.href).origin;

function formatVND(n, currency) {
	return n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,') + currency;
}

function updateValueDisplay(id) {
	const inputNumber = document.querySelector('#inputNumberCart' + id);
	const qty = inputNumber.value;

	axios.post(baseUrl + '/beauty/cart/update?id=' + id + "&sst=" + qty)
		.then(function (response) {
			let price = 0;
			if (response.data.discount > 0) {
				price = (response.data.price - (response.data.price * response.data.discount * 0.01)) * response.data.quantity;
			} else {
				price = response.data.price * response.data.quantity;
			}
			document.getElementById('viewTotailPrice' + id).innerHTML = formatVND(price, 0, 'POINT', 3, 'POINT') + 'VND';
		})

	axios.get(baseUrl + '/beauty/cart/totail-cart')
		.then(function (response) {
			let totailCart = 0;
			totailCart = response.data;
			document.getElementById('totailViewCart').innerHTML = formatVND(totailCart, 0, 'POINT', 3, 'POINT') + 'VND';
			const ship = document.querySelector('input[name="shipping"]:checked').value;
			if (ship === 1) {
				totailCart = totailCart + 10;
			} else if (ship === 2) {
				totailCart = totailCart + 20;
			} else {
				totailCart = totailCart + 0;
			}
			document.getElementById('totailViewOrder').innerHTML = formatVND(totailCart, 0, 'POINT', 3, 'POINT') + 'VND';
		})
}

function handleShipingClick() {
	axios.get(baseUrl + '/beauty/cart/totail-cart')
		.then(function (response) {
			let totailViewOrder = 0;
			totailViewOrder = response.data;
			const ship = document.querySelector('input[name="shipping"]:checked').value;
			let shipping = 0;
			if (ship === '1') {
				totailViewOrder = totailViewOrder + 10;
				shipping = 1;
			} else if (ship === '2') {
				totailViewOrder = totailViewOrder + 20;
				shipping = 2;
			} else {
				totailViewOrder = totailViewOrder + 0;
			}
			axios.post(baseUrl + '/beauty/cart/shipping?ship=' + shipping);
			document.getElementById('totailViewOrder').innerHTML = formatVND(totailViewOrder,0, 'POINT', 3, 'POINT') + 'VND';
		});
}