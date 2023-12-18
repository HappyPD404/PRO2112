var baseUrl = new URL(window.location.href).origin;

var registerUsername = document.getElementById('register_username');
var registerEmail = document.getElementById('register_email');
var registerPassword = document.getElementById('register_password');

var loginUsername = document.getElementById('login_username');
var loginPassword = document.getElementById('login_password');

function registerSubmit() {
	axios.post(baseUrl + '/beauty/auth/register', {
		username: registerUsername.value,
		email: registerEmail.value,
		password: registerPassword.value,
	})
		.then(function(response) {
			Swal.fire({
				title: 'Thông báo đã đăng ký',
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

function loginSubmit() {
	axios.post(baseUrl + '/beauty/auth/login', {
		username: loginUsername.value,
		password: loginPassword.value,
	})
		.then(function(response) {
			Swal.fire({
				title: 'Thông báo đã đăng nhập',
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

function logout() {
	Swal.fire({
		title: 'Đăng xuất?',
		text: "Bạn có muốn đăng xuất!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Xác nhận!'
	}).then((result) => {
		if (result.isConfirmed) {
			axios.get(baseUrl + '/beauty/auth/logout')
				.then(function(response) {
					Swal.fire({
						title: 'Thông báo',
						text: 'Đã đăng xuất thành công',
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
	})
}