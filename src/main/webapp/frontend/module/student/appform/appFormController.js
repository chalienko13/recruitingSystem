/**
 * Created by dima on 02.05.16.
 */

function appFormController($scope,ngToast, $http, appFormService,  Upload ) {

	function loadAppForm() {
		 appFormService.loadAppFormData().then(function success(data) {
				$scope.id = data.id;
		        $scope.questions = data.questions;
		        $scope.user = data.user;
		        $scope.status = data.status;
		        $scope.data = data;
		        console.log($scope.data);

		    }, function error() {
		        console.log("error");
		    });
	}
	loadAppForm();
   

    $scope.submit = function () {
        var req =  $http({
            method : 'POST',
            url : '/student/saveApplicationForm',
            contentType: 'application/json',
            data : {
                status : $scope.data.status,
                user : $scope.data.user,
                questions : $scope.data.questions
            }
        });
       var response; 
       req.success(function(data) {
			console.log(data);
			$scope.resultMessage =  data;
//			$scope.resultMessage
			
		});
       return response;
    };

    $scope.toggle = function (item, list){
        var idx=-1;
        for(var i=0; i<list.length; i++){
            if(list[i].answer==item)
                idx=i;
        }
        if (idx >-1){
            list.splice(idx,1);
        }
        else {
            list.push({answer: item});
        }
        console.log(list);
    };

    $scope.exists = function (item, list){
        for(var i=0; i<list.length; i++){
            if(list[i].answer==item)
                return true;
        }
        return false;
    };
    function ge(id) {
    	return document.getElementById(id);
    }
    $scope.saveForm = function() {
    	if($scope.myForm.$invalid) {
    		return;
    	}
    	for(var i = 0; i < $scope.questions.length; i++) {
    		if($scope.questions[i].isMandatory && !checkAnswersSelected($scope.questions[i])) {
    			return;
    		}
    	}
		var fileInp = ge('file');
		var fd = new FormData();
		var fileVal = document.getElementById("file");
		fd.append('applicationForm', angular.toJson($scope.data));
		var fileData = fileInp.files[0];
		var blobData;
		if(fileData == null) {
			fileData = '';
			blobData = [""];
		}
		else {
			blobData = [ fileInp.files[0] ];
		}
		fd.append("file", new Blob(blobData, {
			type : undefined
		}), fileInp.value.substring(fileInp.value.lastIndexOf('\\')));

		var req = $http.post('/student/saveApplicationForm', fd, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		});
		req.success(function(data) {
			console.log(data);
			$scope.resultMessage =  data;
			var toastMessage = {
	                content: $scope.resultMessage.message,
	                timeout: 5000,  //TODO : Change color, position
	                horizontalPosition: 'center',
	                verticalPosition: 'bottom',
	                dismissOnClick: true,
	                combineDuplications: true,
	                maxNumber: 2
	            };
			if ($scope.resultMessage.type == 'ERROR') {
				var myToastMsg = ngToast.warning(toastMessage);
			}
			else if ($scope.resultMessage.type == 'SUCCESS') {
				var myToastMsg = ngToast.success(toastMessage);
				loadAppForm();
			}
		});

	}
    $scope.uploadPic = function (file) {
    	
    	
        file.upload = Upload.upload({
            url: '/student/uploadPhoto',
//            fields: {'username': 'test'}, // additional data to send
            file: file
        }).progress(function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
        }).success(function (data, status, headers, config) {
            console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
        });
    }

    var _URL = window.URL || window.webkitURL;

    $("#file").change(function() {

        var image, file;
        console.log(this.files[0]);

        if ((file = this.files[0])) {

            file.type.match("/.png/");
            image = new Image();

            image.onload = function() {

               if(this.width !=300 && this.height !=400){
                alert("Wrong size");

               }
                else {
                   image.src = _URL.createObjectURL(file);
               }
            }}
        //image.src = _URL.createObjectURL(file);
    });



    $scope.exportAppForm = function(){
        var config = {
            method: 'GET',
            url: "./../student/appform/"+ $scope.id,
            headers: {
                'Accept': 'application/pdf'
            }
        };
        $http(config)
            .success(function(){
                window.location = "./../student/appform/"+ $scope.id;
            })
            .error(function(){
                var myToastMsg = ngToast.warning({
                    content: 'Error exporting Application Form ',
                    timeout: 5000,  //TODO : Change color, position
                    horizontalPosition: 'center',
                    verticalPosition: 'bottom',
                    dismissOnClick: true,
                    combineDuplications: true,
                    maxNumber: 2
                });
            });
    };
    $scope.uploadImage = function(file) {
    	
//    	 Upload.upload({
//    	        url: '../student/upload',
//    	        fields: {'username': 'zouroto'}, // additional data to send
//    	        file: file
//    	    }).progress(function (evt) {
//    	        var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
//    	        console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
//    	    }).success(function (data, status, headers, config) {
//    	        console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
//    	    });
//    	 
//    	 return;
    	file.upload = Upload.upload({
    	      url: '../student/upload',
    	      data: {username: $scope.data.questions, file: file}
    	    });

    	    file.upload.then(function (response) {
    	      $timeout(function () {
    	        file.result = response.data;
    	      });
    	    }, function (response) {
    	      if (response.status > 0)
    	        $scope.errorMsg = response.status + ': ' + response.data;
    	    }, function (evt) {
    	      // Math.min is to fix IE which reports 200% sometimes
    	      file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
    	    });
    }
    checkAnswersSelected = function(question) {
    	for(var i = 0; i < question.answers.length; i++) {
    		if(question.answers[i].answer != null && question.answers[i].answer != '') {
    			return true;
    		}
    	}
    	return false;
    }
    
    $scope.checkAnswersSelected = checkAnswersSelected;

}



angular.module('appStudentForm')
    .controller('appFormController', ['$scope','ngToast','$http', 'appFormService', 'Upload', appFormController]);
