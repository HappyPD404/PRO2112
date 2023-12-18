const gallery = document.getElementById('gallery');
const files = [];
const input = document.getElementById('files');

input.addEventListener('change', () => {
	let file = input.files;
	/* if (file.length == 0 || file.length > 4) return; */
	for (let i = 0; i < file.length; i++) {
		if (file[i].type.split("/")[0] != 'image') continue;
		if (!files.some(e => e.name == file[i].name)) files.push(file[i])
	}
	showImages();
});

function showImages() {
	gallery.innerHTML = '';
	const card = document.createElement('div');
	card.classList.add('row');
	/* onclick="delImage(${index})" */
	card.innerHTML = files.reduce((prev, curr, index) => {
		return `${prev}
       			 <div class="col-sm-2">
                     <div class="card">
       			 		<button type="button" onclick="delImage(${index})" class="btn btn-danger" style="display: inline-block;position: absolute;">
       			 			<i class="bi bi-x-circle"></i>
       			 		</button>
                         <img src="${URL.createObjectURL(curr)}" class="card-img-top" alt="...">
                     </div>
                </div>`
	}, '');
	gallery.appendChild(card);
}

function delImage(index) {
	files.splice(index, 1);
	showImages();
}
