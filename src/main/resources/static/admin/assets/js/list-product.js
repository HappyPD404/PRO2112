var baseUrl = new URL(window.location.href).origin;

function deleteProduct(id, name) {
	Swal.fire({
		title: 'Bạn có muốn sản phẩm?',
		text: "Xoá sản phẩm " + name + " sẽ không khôi phục được!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Xác nhận!'
	}).then((result) => {
		if (result.isConfirmed) {
			axios({
				method: 'DELETE',
				url: baseUrl + "/products/delete/" + id,
				responseType: 'stream'
			})
				.then(function(response) {
					Swal.fire(
						'Đã xoá!',
						'Đã xoá sản phẩm thành công.',
						'success'
					)
					location.reload(true);
				});
		}
	})
}

var bodyViewProduct = document.getElementById('bodyViewProduct');

function viewProduct(id) {
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/products/view/" + id
	})
		.then(function(response) {
			var wrapper = document.createElement('div');
			wrapper.innerHTML = ``;
			bodyViewProduct.innerHTML = ``;
			wrapper.innerHTML = `
								<table class="table table-borderless">
									<tbody>
										<tr>
											<th>Id</th>
											<td>`+ response.data.id + `</td>
										</tr>
										<tr>
											<th>Tên sản phẩm</th>
											<td>`+ response.data.name + `</td>
										</tr>
										<tr>
											<th>Số lượng</th>
											<td>`+ response.data.quantity + `</td>
										</tr>
										<tr>
											<th>Màu sắc</th>
											<td>`+ response.data.color + `</td>
										</tr>
										<tr>
											<th>Giá</th>
											<td>`+ response.data.price + `</td>
										</tr>
										<tr>
											<th>Giảm giá</th>
											<td>`+ response.data.discount + `</td>
										</tr>
										<tr>
											<th>Ngày bắt đầu</th>
											<td>`+ response.data.startDayDiscount + `</td>
										</tr>
										<tr>
											<th>Ngày kết thúc</th>
											<td>`+ response.data.endDayDiscount + `</td>
										</tr>
										<tr>
											<th>Lượt xem</th>
											<td>`+ response.data.views + `</td>
										</tr>
										<tr>
											<th>Mô tả</th>
											<td>`+ response.data.description + `</td>
										</tr>
										<tr>
											<th>Thông tin</th>
											<td>`+ response.data.info + `</td>
										</tr>
										<tr>
											<th>Danh mục</th>
											<td>`+ response.data.category.name + `</td>
										</tr>
										<tr>
											<th>Thương hiệu</th>
											<td>`+ response.data.brand.name + `</td>
										</tr>
										<tr>
											<th>Ngày tạo</th>
											<td>`+ response.data.createdDate + `</td>
										</tr>
										<tr>
											<th>Cập nhật lần cuối</th>
											<td>`+ response.data.lastModifiedDate + `</td>
										</tr>
									</tbody>
								</table>
			`;
			bodyViewProduct.append(wrapper);
		});
	viewImages(id);
}
const bodyViewProductImages = document.getElementById('bodyViewProductImages');
function viewImages(id) {
	bodyViewProductImages.innerHTML = ``;
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/products/view-image/" + id
	})
		.then(response => {
			response.data.forEach(image => {
				var wrapperImage = document.createElement('div');
				wrapperImage.classList.add("col-sm-6", "col-lg-6");
				wrapperImage.innerHTML = `<div class="card">
												<img src="/uploads/`+ image.name + `" class="card-img-top"
											alt="...">
											</div>`;
				bodyViewProductImages.appendChild(wrapperImage);
			});
		});
}