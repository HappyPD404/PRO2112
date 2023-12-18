var baseUrl = new URL(window.location.href).origin;

function deleteBrand(id, name) {
	Swal.fire({
		title: 'Bạn có muốn xoá?',
		text: "Xoá thương hiệu " + name + " sẽ không khôi phục được!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Xác nhận!'
	}).then((result) => {
		if (result.isConfirmed) {
			axios({
				method: 'DELETE',
				url: baseUrl + "/brands/delete/" + id,
				responseType: 'stream'
			})
				.then(function(response) {
					Swal.fire(
						'Đã xoá!',
						'Đã xoá thương hiệu thành công.',
						'success'
					)
					location.reload(true);
				});
		}
	})
}

var bodyViewBrand = document.getElementById('bodyViewBrand');

function viewBrand(id) {
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/brands/view/" + id
	})
		.then(function(response) {
			var wrapper = document.createElement('div');
			wrapper.innerHTML = ``;
			bodyViewBrand.innerHTML = ``;
			wrapper.innerHTML = `
			<div class="row">
							<div class="col-sm-12 col-lg-12">
								<table class="table table-borderless">
									<tbody>
										<tr>
											<th>Id</th>
											<td>`+ response.data.id + `</td>
										</tr>
										<tr>
											<th>Tên thương hiệu</th>
											<td>`+ response.data.name + `</td>
										</tr>
										<tr>
											<th>Ngày tạo</th>
											<td>`+ response.data.createdDate + `</td>
										</tr>
										<tr>
											<th>Ngày cập nhật</th>
											<td>`+ response.data.lastModifiedDate + `</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
			`;
			bodyViewBrand.append(wrapper);
		});
}