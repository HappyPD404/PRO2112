loadViewChart(2023);
loadViewCategoryChart();

function loadViewChart(year) {
	var dataView = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/statistics/view/view-year/" + year
	})
		.then(response => {
			response.data.forEach(item => {
				dataView[parseInt(item[0] - 1)] = parseInt(item[1]);
			});
			echarts.init(document.querySelector("#viewStatisticsYear")).setOption({
				xAxis: {
					type: 'category',
					boundaryGap: false,
					data: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
						"Tháng 10", "Tháng 11", "Tháng 12"]
				},
				yAxis: {
					type: 'value'
				},
				series: [{
					data: dataView,
					type: 'line',
					smooth: true,
					areaStyle: {}
				}]
			});
		});
}

function handleYearView() {
	var year = document.getElementById('yearView').value;
	loadViewChart(year);
}

function loadViewCategoryChart() {
	var dataViewCategory = [];
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/statistics/view/view-category"
	})
		.then(response => {
			response.data.forEach(item => {
				dataViewCategory.push({
					value: item[1],
					name: item[0]
				})
			});
			echarts.init(document.querySelector("#viewStatisticsCategory")).setOption({
				tooltip: {
					trigger: 'item'
				},
				legend: {
					orient: 'vertical',
					right: 'right',
				},
				series: [{
					 name: 'Access From',
					 type: 'pie',
					radius: '25%',
					// // left: '0',
					data: dataViewCategory,
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