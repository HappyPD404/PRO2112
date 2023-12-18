var baseUrl = new URL(window.location.href).origin;

function deleteUser(id, name) {
	Swal.fire({
		title: 'Bạn có muốn xoá?',
		text: "Xoá người dùng " + name + " sẽ không khôi phục được!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Xác nhận!'
	}).then((result) => {
		if (result.isConfirmed) {
			axios({
				method: 'DELETE',
				url: baseUrl + "/users/delete/" + id,
				responseType: 'stream'
			})
				.then(function(response) {
					Swal.fire(
						'Đã xoá!',
						'Đã xoá người dùng thành công.',
						'success'
					)
					location.reload(true);
				});
		}
	})
}

var bodyViewUser = document.getElementById('bodyViewUser');

function viewUser(id) {
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/users/view/" + id
	})
		.then(function(response) {
			var avatar = response.data.avatar == null ? '/admin/assets/img/no-image.jpg' : '/uploads/' + response.data.avatar;
			var wrapper = document.createElement('div');
			wrapper.innerHTML = ``;
			bodyViewUser.innerHTML = ``;
			wrapper.innerHTML = `
			<div class="row">
							<div class="col-sm-12 col-lg-4">
								<div class="card">
									<img src="`+ avatar + `" class="card-img-top"
										alt="...">
								</div>
							</div>
							<div class="col-sm-12 col-lg-8">
								<table class="table table-borderless">
									<tbody>
										<tr>
											<th>Id</th>
											<td id="viewUserId">`+ response.data.id + `</td>
										</tr>
										<tr>
											<th>Tên đăng nhập</th>
											<td id="viewUserUsername">`+ response.data.username + `</td>
										</tr>
										<tr>
											<th>Email</th>
											<td id="viewUserEmail">`+ response.data.email + `</td>
										</tr>
										<tr>
											<th>Họ</th>
											<td id="viewUserFirstName">`+ response.data.firstName + `</td>
										</tr>
										<tr>
											<th>Tên</th>
											<td id="viewUserLastName">`+ response.data.lastName + `</td>
										</tr>
										<tr>
											<th>Ngày sinh</th>
											<td id="viewUserBirthDay">`+ response.data.birthDay + `</td>
										</tr>
										<tr>
											<th>Địa chỉ</th>
											<td id="viewUserAddress">`+ response.data.address + `</td>
										</tr>
										<tr>
											<th>Giới tính</th>
											<td id="viewUserGender">`+ response.data.gender + `</td>
										</tr>
										<tr>
											<th>Truy cập</th>
											<td id="viewUserLogin">`+ response.data.login + `</td>
										</tr>
										<tr>
											<th>Chức vụ</th>
											<td id="viewUserRole">`+ response.data.role + `</td>
										</tr>
										<tr>
											<th>Ngày tạo</th>
											<td id="viewUserCreactDay">`+ response.data.createdDate + `</td>
										</tr>
										<tr>
											<th>Cập nhật lần cuối</th>
											<td id="viewUserLastUpdate">`+ response.data.lastModifiedDate + `</td>
										</tr>
										<tr>
											<th>Truy cập lần cuối</th>
											<td id="viewUserLastLogin">`+ response.data.lastLoginDate + `</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
			`;
			bodyViewUser.append(wrapper);
		});
}