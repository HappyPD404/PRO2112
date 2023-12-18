var baseUrl = new URL(window.location.href).origin;

function addLikeShop(id) {
	like(id);
}

function addLike(){
	var id = document.getElementById("cart_id").value;
	like(id);
}

function like(id) {
	Swal.fire({
		title: 'Thông báo?',
		text: "Bạn có muốn thêm sản phẩm vào danh sách yêu thích!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Xác nhận!'
	}).then((result) => {
		if (result.isConfirmed) {
			axios.post(baseUrl + '/beauty/like/add/' + id)
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
	})
}

function deleteLike(id) {
	Swal.fire({
		title: 'Thông báo?',
		text: "Bạn có muốn xoá sản phẩm ra khỏi danh sách yêu thích!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Xác nhận!'
	}).then((result) => {
		if (result.isConfirmed) {
			axios.delete(baseUrl + '/beauty/like/delete/' + id)
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
	})
}
