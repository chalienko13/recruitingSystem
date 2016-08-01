/**
 * Created by dima on 30.04.16.
 */
'use strict';

function reportController($scope, $http) {
	$scope.reportType = "approved";
	$scope.question = 0;
	$http.get('../../admin/reports/answers/questions').success(function(data) {
		$scope.questions = data;
		$scope.question = $scope.questions[0].id;
		console.log($scope.questions);
	});
	$scope.exportType = 'xls';
	$scope.generateReport = function() {
		var url;
		if ($scope.reportType == "approved") {
			url = './../admin/reports/approved.json';
		} else if ($scope.reportType == "answers") {
			url = './../admin/reports/answers.json/' + $scope.question;
		}
		$http.get(url).success(function(data) {
			$scope.report = data;
			var ctx = document.getElementById("report-chart");
			var chartData = {
				labels : $scope.report.header.cells.slice(1),
				datasets : []
			};
			function getRandomColor() {
				var letters = '0123456789ABCDEF'.split('');
				var color = '#';
				for (var i = 0; i < 6; i++) {
					color += letters[Math.floor(Math.random() * 16)];
				}
				return color;
			}
			for (var i = 0; i < $scope.report.lines.length; i++) {
				var line = $scope.report.lines[i].cells;
				chartData.datasets.push({
					label : line[0],
					backgroundColor : getRandomColor(),
					data : line.slice(1)
				});
			}
			if ($scope.reportChart != null) {
				$scope.reportChart.destroy();
			}

			$scope.reportChart = new Chart(ctx, {
				type : 'bar',

				data : chartData,
				options : {
					responsive : false,
					scales : {
						yAxes : [ {
							ticks : {
								beginAtZero : false
							}
						} ]
					}
				}
			});

		});
	}

	$scope.exportReport = function() {
		var url;
		var type = $scope.exportType;
		if ($scope.reportType == "approved") {
			url = './../admin/reports/approved.' + type;
		} else if ($scope.reportType == "answers") {
			url = './../admin/reports/answers.' + type + '/' + $scope.question;
		}
		window.location = url;
	}
}

angular.module('appReport')
    .controller('reportController', ['$scope','$http', reportController]);