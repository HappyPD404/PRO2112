var baseUrl = new URL(window.location.href).origin;
var bodyQuickView = document.getElementById('bodyQuickView');

function formatVND(n, currency) {
	return n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,') + currency;
}

function quickView(id) {
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/beauty/product-detail/quick-view/" + id
	})
		.then(function (response) {
			var wrapper = document.createElement('div');
			wrapper.innerHTML = ``;
			bodyQuickView.innerHTML = ``;
			var priceDiscount;
			var startDate = new Date(response.data.startDayDiscount);
			var endDate = new Date(response.data.endDayDiscount);
			var now = new Date();
			if (response.data.discount <= 0 || response.data.startDayDiscount == null || response.data.endDayDiscount == null) {
				priceDiscount = `<h3 class="product-price"><span class="new-price">` + formatVND(response.data.price, 0, 'POINT', 3, 'POINT') + 'VND' + `</span></h3>`;
			} else {
				if (now >= startDate && now <= endDate) {
					priceDiscount = `<h3 class="product-price"><span class="new-price">`
						+ formatVND(response.data.price - (response.data.price * response.data.discount * 0.01), ' VND') +
						`</span><span class="old-price"><del>` + formatVND(response.data.price, 0, 'POINT', 3, 'POINT') + 'VND' + `<del></span></h3>`;
				} else {
					priceDiscount = `<h3 class="product-price"><span class="new-price">` + formatVND(response.data.price, 0, 'POINT', 3, 'POINT') + 'VND' + `</span></h3>`;
				}
			}
			wrapper.innerHTML = `
				 <input type="hidden" id="cart_id" name="cart_id" value="`+ id + `">
				<h2 class="product-title">`+ response.data.name + `</h2>` + priceDiscount;
			bodyQuickView.append(wrapper);
		});
	viewImages(id);
}

let images;
const mainView = document.getElementById('main-view');
const thumbnails = document.getElementById('thumbnails');

async function viewImages(id) {
	images = [];
	const response = await axios.get(baseUrl + "/beauty/product-detail/quick-view/view-image/" + id);
	images = response.data;

	for (let i = 0; i < images.length; i++) {
		let image = images[i];
		let img = document.createElement('img');
		img.src = '/uploads/' + images[i].name;
		img.setAttribute('width', 170);
		img.setAttribute('data-index', i);
		img.addEventListener('click', changeImage);
		thumbnails.appendChild(img);
	}

	initGallery();
	setTimeout(slideImage, 3000);
}

function initGallery() {
	loadImage(0);
};

function slideImage() {
	let currentIndex = parseInt(mainView.getAttribute('data-index'));
	currentIndex = currentIndex + 1 == images.length ? 1 : currentIndex + 1;
	loadImage(currentIndex);
	setTimeout(slideImage, 3000);

}

function changeImage(event) {
	let target = event.currentTarget;
	let index = target.getAttribute('data-index');
	loadImage(index);
}

function loadImage(index) {
	let image = images[index];
	mainView.src = '/uploads/' + image.name;
	mainView.setAttribute('data-index', index);
	mainView.setAttribute('id', 'image-' + index);
	mainView.style.opacity = 1;
}

function fullScreenImage() {
	toggleFullscreen(mainView);
}

function toggleFullscreen(el) {
	if (document.fullscreenElement || document.mozFullScreenElement
		|| document.webkitFullscreenElement || document.msFullscreenElement) {
		if (document.exitFullscreen) {
			document.exitFullscreen();
		}
		else if (document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		}
		else if (document.webkitExitFullscreen) {
			document.webkitExitFullscreen();
		}
		else if (document.msExitFullscreen) {
			document.msExitFullscreen();
		}
	}
	else {
		if (document.documentElement.requestFullscreen) {
			el.requestFullscreen();
		}
		else if (document.documentElement.mozRequestFullScreen) {
			el.mozRequestFullScreen();
		}
		else if (document.documentElement.webkitRequestFullscreen) {
			el.webkitRequestFullscreen();
		}
		else if (document.documentElement.msRequestFullscreen) {
			el.msRequestFullscreen();
		}
	}
}