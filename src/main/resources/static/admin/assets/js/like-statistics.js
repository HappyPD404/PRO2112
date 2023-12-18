loadLikeChart(2023);
loadLikeCategoryChart();

function loadLikeChart(year) {
	var dataLike = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/statistics/like/like-year/" + year
	})
		.then(response => {
			response.data.forEach(item => {
				dataLike[parseInt(item[0] - 1)] = parseInt(item[1]);
			});
			echarts.init(document.querySelector("#likeStatisticsYear")).setOption({
				xAxis: {
					type: 'category',
					data: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
						"Tháng 10", "Tháng 11", "Tháng 12"]
				},
				yAxis: {
					type: 'value'
				},
				series: [{
					data: dataLike,
					type: 'bar'
				}]
			});
		});
}

function handleYearLike() {
	var year = document.getElementById('yearLike').value;
	loadLikeChart(year);
}

function loadLikeCategoryChart() {
	var dataLikeCategory = [];
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/statistics/like/like-category"
	})
		.then(response => {
			response.data.forEach(item => {
				dataLikeCategory.push({
					value: item[1],
					name: item[0]
				})
			});
			echarts.init(document.querySelector("#likeStatisticsCategory")).setOption({
				tooltip: {
					trigger: 'item'
				},
				legend: {
					orient: 'vertical',
					left: 'left'
				},
				series: [{
					name: 'Access From',
					type: 'pie',
					radius: '50%',
					data: dataLikeCategory,
					emphasis: {
						itemStyle: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				}]
			});
		});
}