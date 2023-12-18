var baseUrl = new URL(window.location.href).origin;

function addCart() {
	var id = document.getElementById("cart_id").value;
	var qty = document.getElementById("cart_qty").value;
	var size = document.getElementById("cart_size").value;
	add(id, qty, size);
}

function addCartDetail(id) {
	var qty = document.getElementById("cart_detail_qty").value;
	var size = document.getElementById("cart_detail_size").value;
	add(id, qty, size);
}

function add(id, qty, size) {
	axios.post(baseUrl + '/beauty/cart/add', {
		id: id,
		qty: qty,
		size: size,
	})
		.then(function(response) {
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
		.catch(function(error) {
			Swal.fire({
				icon: 'error',
				title: 'Thông báo lỗi',
				text: error.response.data,
			})
		});
}
