var baseUrl = new URL(window.location.href).origin;

function deleteCategory(id, name) {
	Swal.fire({
		title: 'Bạn có muốn xoá?',
		text: "Xoá danh mục " + name + " sẽ không khôi phục được!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Xác nhận!'
	}).then((result) => {
		if (result.isConfirmed) {
			axios({
				method: 'DELETE',
				url: baseUrl + "/categories/delete/" + id,
				responseType: 'stream'
			})
				.then(function(response) {
					Swal.fire(
						'Đã xoá!',
						'Đã xoá danh mục thành công.',
						'success'
					)
					location.reload(true);
				});
		}
	})
}

var bodyViewCategory = document.getElementById('bodyViewCategory');

function viewCategory(id) {
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/categories/view/" + id
	})
		.then(function(response) {
			var wrapper = document.createElement('div');
			wrapper.innerHTML = ``;
			bodyViewCategory.innerHTML = ``;
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
											<th>Tên danh mục</th>
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
			bodyViewCategory.append(wrapper);
		});
}