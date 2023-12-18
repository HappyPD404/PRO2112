loadRevenueChart(2023);
loadRevenueCategoryChart(2023);

function loadRevenueChart(year) {
	var dataRevenue = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/statistics/revenue/revenue-year/" + year
	})
		.then(response => {
			response.data.forEach(item => {
				dataRevenue[parseInt(item[0] - 1)] = parseInt(item[1]);
			});
			echarts.init(document.querySelector("#revenueStatisticsYear")).setOption({
				xAxis: {
					type: 'category',
					data: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
						"Tháng 10", "Tháng 11", "Tháng 12"]
				},
				yAxis: {
					type: 'value'
				},
				series: [{
					data: dataRevenue,
					type: 'line',
					smooth: true
				}]
			});
		});
}

function handleYearRevenue() {
	var year = document.getElementById('yearRevenue').value;
	loadRevenueChart(year);
}

function loadRevenueCategoryChart(year) {
	var dataRevenueCategory = [];
	axios({
		method: 'GET',
		contentType: "application/json",
		url: baseUrl + "/statistics/revenue/revenue-category-year/" + year
	})
		.then(response => {
			response.data.forEach(item => {
				dataRevenueCategory.push({
					value: item[1],
					name: item[0]
				})
			});
			echarts.init(document.querySelector("#revenueCategoryStatisticsYear")).setOption({
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
					data: dataRevenueCategory,
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

function handleYearRevenueCategory() {
	var year = document.getElementById('yearRevenueCategory').value;
	loadRevenueCategoryChart(year);
}
