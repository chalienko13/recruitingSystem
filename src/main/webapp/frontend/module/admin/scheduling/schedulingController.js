/**
 * Created by dima on 30.04.16.
 */
function schedulingController($scope, ngToast, schedulingService) {

    $scope.activeDate;
    $scope.selectedDates = [];
    $scope.timeResult = [];
    $scope.type = 'individual';

    $scope.recruiting = false;
    
    $scope.softAmountSet;
    $scope.techAmountSet;

    $scope.roleSoft = 5;
    $scope.roleTech = 2;
    $scope.roleStudent = 3;
    $scope.roleToShow = 2;
    $scope.timePointToChange;

    // $scope.softArray = {};
    // $scope.techArray = {};
    // $scope.studentArray = {};

    $scope.map = {};
    $scope.softMap = {};
    $scope.techMap = {};
    $scope.studentMap = {};

    $scope.currentStatus = '';

    var getSuccessToast = function (message) {
        var myToastMsg = ngToast.success({
            content: message,
            timeout: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            dismissOnClick: true,
            combineDuplications: true,
            maxNumber: 2
        });
    };

    var getWarningToast = function (message) {
        var myToastMsg = ngToast.warning({
            content: message,
            timeout: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            dismissOnClick: true,
            combineDuplications: true,
            maxNumber: 2
        });
    };

    $scope.removeFromSelected = function (dt) {
        $scope.selectedDates.splice(this.selectedDates.indexOf(dt), 1);
        if ($scope.map[dt].id != -1) {
            schedulingService.deleteSelectedDayService($scope.map[dt].id)
                .then(function (response) {
                    console.log(response);
                })
                .catch(function (response) {
                    getWarningToast('Cannot delete selected day');
                });

        }
    };

    $scope.getCurrentStatus = function () {
        schedulingService.getCurrentStatusService()
            .then(function (response) {
                $scope.currentStatus = response.data.id;
                schedulingService.getCurrentRecruitmentCountStudents().then(function success(data) {
                    $scope.countStudent = data.amountOfStudents;
                    $scope.amountOfTech = data.amountOfTech;
                    $scope.amountOfSoft = data.amountOfSoft;
                });

                schedulingService.getSelectedDayService()
                    .then(function (response) {
                        $scope.selectedDates.length = 0;
                        $scope.timeResult.length = 0;
                        angular.forEach(response.data, function (value, key) {
                            var foo = new Date(value.startDate).setHours(0, 0, 0, 0);
                            $scope.selectedDates.push(foo);
                            $scope.map[foo] = {
                                id: value.id,
                                startTime: new Date(value.startDate).getHours(),
                                endTime: new Date(value.endDate).getHours()
                            };
                            console.log(response.data);
                        });
                        console.log($scope.map);
                    })
                    .catch(function (response) {
                        getWarningToast('Error! Cannot get selected day');
                    });


                schedulingService.getCurrentSchedule().then(function success(data) {
                    console.log(data);
                    $scope.schedulePoints = data.data;
                    $scope.collapsed = [];
                    $scope.collapsed[1] = [];
                    $scope.collapsed[2] = [];
                    $scope.collapsed[3] = [];
                    for (var i = 0; i < $scope.schedulePoints.length; i++) {
                        $scope.collapsed[1][i] = false;
                        $scope.collapsed[2][i] = false;
                        $scope.collapsed[3][i] = false;
                    }
                });

                schedulingService.getUsersWithoutInterview($scope.roleSoft)
                    .then(function success(data) {
                        $scope.possibleToAdd[$scope.roleSoft] = data;
                        console.log($scope.possibleToAdd);
                    })
                    .catch(function (response) {
                        getWarningToast('Cannot get possible Soft');
                    });

                schedulingService.getUsersWithoutInterview($scope.roleTech)
                    .then(function success(data) {
                        $scope.possibleToAdd[$scope.roleTech] = data;
                    })
                    .catch(function (response) {
                        getWarningToast('Cannot get possible Tech');
                    });

                schedulingService.getUsersWithoutInterview($scope.roleStudent)
                    .then(function success(data) {
                        $scope.possibleToAdd[$scope.roleStudent] = data;
                        console.log($scope.possibleToAdd);
                    })
                    .catch(function (response) {
                        getWarningToast('Cannot get possible Students');
                    });

                schedulingService.getInterviewParametersService().then(function (response) {
                    if (response.status === 200) {
                        if (response.data.softDuration === undefined){
                            $scope.softDuration = '';    
                        }else {
                            $scope.softDuration = response.data.softDuration;
                        }
                        if (response.data.techDuration === undefined){
                            $scope.techDuration = '';
                        }else{
                            $scope.techDuration = response.data.techDuration;    
                        }
                    }
                    else {
                        getWarningToast('Error! Cannot get interview parameters')
                    }
                })

            })
            .catch(function (response) {
                $scope.recruiting = true;
                if (response.status === 409) {
                    getWarningToast(response.data.message);
                }
            });
    };

    $scope.edit = function (object, date) {
        var temp = {
            id: object.id,
            day: date,
            hourStart: object.startTime,
            hourEnd: object.endTime,
            changed: false
        };
        schedulingService.editSelectedDayService(temp)
            .then(function (response) {
                console.log(response);
            })
            .catch(function (response) {
                getWarningToast('Error! Day has not been edited');
            });

    };

    var currentSoft;
    var currentTech;
    var currentStudent;
    var currentTimePoint;

    $scope.setCurrentSoft = function (soft, timePoint) {
        currentSoft = soft;
        currentTimePoint = timePoint;
    };

    $scope.setCurrentTech = function (tech, timePoint) {
        currentTech = tech;
        currentTimePoint = timePoint;
    };

    $scope.setCurrentStudent = function (student, timePoint) {
        currentStudent = student;
        currentTimePoint = timePoint;
    };

    $scope.deleteSoftTimeFinal = function () {
        schedulingService.deleteUserTimeFinal(currentSoft.id, currentTimePoint.id)
            .then(function (response) {
                if (response.status === 200) {
                    var index = $scope.softMap[currentTimePoint.id].indexOf(currentSoft);
                    $scope.softMap[currentTimePoint.id].splice(index, 1);
                }
            })
            .catch(function (response) {
                getWarningToast('Error! Soft has not been deleted');
            });
        console.log("delete Soft TimeFinal with id = " + currentSoft.id + " and timePoint id = " + currentTimePoint.id);
    };

    $scope.deleteTechTimeFinal = function () {
        schedulingService.deleteUserTimeFinal(currentTech.id, currentTimePoint.id)
            .then(function (response) {
                if (response.status === 200) {
                    var index = $scope.techMap[currentTimePoint.id].indexOf(currentTech);
                    $scope.techMap[currentTimePoint.id].splice(index, 1);
                }
            })
            .catch(function (response) {
                getWarningToast('Error! Tech has not been deleted');
            });
        console.log("delete Tech TimeFinal with id = " + currentTech.id + " and timePoint id = " + currentTimePoint.id);
    };

    $scope.deleteStudentTimeFinal = function () {
        schedulingService.deleteUserTimeFinal(currentStudent.id, currentTimePoint.id)
            .then(function (response) {
                if (response.status === 200) {
                    var index = $scope.studentMap[currentTimePoint.id].indexOf(currentStudent);
                    $scope.studentMap[currentTimePoint.id].splice(index, 1);
                }
            })
            .catch(function (response) {
                getWarningToast('Error! Student has not been deleted');
            });
        console.log("delete Student TimeFinal with id = " + currentStudent.id + " and timePoint id = " + currentTimePoint.id);
    };

    $scope.setTime = function (object, date) {
        if (object.id === -1) {
            var temp = {
                day: date,
                hourStart: object.startTime,
                hourEnd: object.endTime,
                changed: false
            };
            schedulingService.saveSelectedDayService(temp)
                .then(function (response) {
                    $scope.map[date].id = response.data;
                })
                .catch(function (response) {
                    getWarningToast(response.data.message);
                });
        }
    };

    $scope.saveSelectedDay = function () {
        console.log($scope.timeResult);
        schedulingService.saveSelectedDayService($scope.timeResult).then(function (response) {
            
        }, function (response) {
            if (response === 409){
                getWarningToast(response.data.message);
            }
        })
    };

    $scope.init = function () {

    };

    $scope.setStartHours = function (date, startTime) {

    };

    $scope.createMap = function (date) {
        if ($scope.map[date] === undefined) {
            $scope.map[date] = {
                id: -1,
                startTime: 0,
                endTime: 0
            }
        }
    };

    $scope.startScheduling = function () {
        schedulingService.startSchedulingService()
            .then(function (response) {
                console.log(response);
            })
            .catch(function (response) {
                getWarningToast(response.data.message);
            });
    };


    $scope.getSoft = function (idTimePoint) {
        schedulingService.getSoftService(idTimePoint)
            .then(function (response) {
                $scope.softMap[idTimePoint] = response.data;
            })
            .catch(function (response) {
                getWarningToast('Error! Cannot get soft by timepoint');
            });
    };

    $scope.getTech = function (idTimePoint) {
        schedulingService.getTechService(idTimePoint)
            .then(function (response) {
                $scope.techMap[idTimePoint] = response.data;
            })
            .catch(function (response) {
                getWarningToast('Error! Cannot get tech by timepoint');
            });
    };

    $scope.getStudent = function (idTimePoint) {
        schedulingService.getStudentService(idTimePoint)
            .then(function (response) {
                $scope.studentMap[idTimePoint] = response.data;
            })
            .catch(function (response) {
                getWarningToast('Error! Cannot get student by timepoint');
            });
    };

    $scope.submitDaysSelection = function () {
        schedulingService.confirmDaysSelectionService().then(function (response) {
            if (response.status === 200) {
                $scope.currentStatus = schedulingService.getConfirmDaysSelectionStatus();
                getSuccessToast('Days selection confirmed');
            }
            else {
                getWarningToast('Error! Days selection has not been confirmed');
            }

        })
    };

    $scope.cancelDaysSelection = function () {
        schedulingService.cancelDaysSelectionService().then(function (response) {
            if (response.status == 200) {
                $scope.map.length = 0;
                $scope.selectedDates.length = 0;
                $scope.currentStatus = 5;
                getSuccessToast('Days selection has been cancelled');
            }
            else {
                getWarningToast('Error! Days selection has not been cancelled');
            }
        })

    };

    $scope.saveInterviewParameters = function (softDuration, techDuration) {
        if(softDuration==techDuration){
            techDuration= techDuration+1;
        }
        schedulingService.saveInterviewParametersService(softDuration, techDuration).then(function (response) {
            if (response.status == 200) {
                getSuccessToast('Interview parameters have been saved')
            }
            else {
                getWarningToast('Error! Interview parameters have not been saved')
            }

        })
    };


    $scope.submitInterviewParameters = function () {
        schedulingService.confirmInterviewParametersService().then(function (response) {
            if (response.status === 200) {
                $scope.currentStatus = schedulingService.getConfirmInterviewParametersStatus();
                getSuccessToast('Interview parameters have been confirmed');
            }
            else {
                getWarningToast('Interview parameters have not been confirmed')
            }
        })
    };

    $scope.possibleToAdd = [];


    $scope.addUserToTimepoint = function addUserToTimepoint(user, TimePoint) {
        schedulingService.addUserToTimepoint(user.id, TimePoint.id)
            .then(function () {
                var index = findInPossible(user.id, $scope.possibleToAdd[$scope.roleToShow].data);
                $scope.possibleToAdd[$scope.roleToShow].data.splice(index, 1);
                switch ($scope.roleToShow) {
                    case $scope.roleSoft:
                        $scope.schedulePoints[findInPossible(TimePoint.id, $scope.schedulePoints)].amountOfSoft++;
                        break;
                    case $scope.roleTech:
                        $scope.schedulePoints[findInPossible(TimePoint.id, $scope.schedulePoints)].amountOfTech++;
                        break;
                    case $scope.roleStudent:
                        $scope.schedulePoints[findInPossible(TimePoint.id, $scope.schedulePoints)].amountOfStudents++;
                        break;
                }
            })
            .catch(function (response) {
                getWarningToast('Cannot add user to timepoint');
            });
    };

    var findInPossible = function findInPossible(id, list) {
        for (var i = 0; i < list.length; i++) {
            if (list[i].id === id)
                return i;
        }
        return -1;
    };

    $scope.modalShown = false;
    $scope.toggleModal = function (idRole, timePointToChange) {
        $scope.timePointToChange = timePointToChange;
        $scope.roleToShow = idRole;
        $scope.modalShown = !$scope.modalShown;
    };

    //modal pagination
    $scope.currentPage = 0;
    $scope.itemsPerPage = 10;
    $scope.firstPage = function() {
        return $scope.currentPage == 0;
    };
    $scope.lastPage = function() {
        var lastPageNum = Math.ceil($scope.possibleToAdd[$scope.roleToShow].data.length / $scope.itemsPerPage - 1);
        return $scope.currentPage == lastPageNum;
    };
    $scope.numberOfPages = function(){
        return Math.ceil($scope.possibleToAdd[$scope.roleToShow].data.length / $scope.itemsPerPage);
    };
    $scope.startingItem = function() {
        return $scope.currentPage * $scope.itemsPerPage;
    };
    $scope.pageBack = function() {
        $scope.currentPage = $scope.currentPage - 1;
    };
    $scope.pageForward = function() {
        $scope.currentPage = $scope.currentPage + 1;
    }

}

angular.module('appScheduling').filter('startFrom', function(){
    return function(input, start){
        start = +start;
        return input.slice(start);
    }
});

angular.module('appScheduling').directive('modalDialog', function () {
    return {
        restrict: 'E',
        scope: {
            show: '='
        },
        replace: true,
        transclude: true,
        link: function (scope, element, attrs) {
            scope.dialogStyle = {};

            if (attrs.width) {
                scope.dialogStyle.width = attrs.width;
            }

            if (attrs.height) {
                scope.dialogStyle.height = attrs.height;
            }

            scope.hideModal = function () {
                scope.show = false;
            };
        },
        template: "<div class='ng-modal' ng-show='show'>" +
        "<div class='ng-modal-dialog' ng-style='dialogStyle'>" +
        "<div class='ng-modal-close' ng-click='hideModal()'>X</div>" +
        "<div class='ng-modal-dialog-content' ng-transclude>" +
        "</div>" +
        "</div>" +
        "</div>"
    };
});

angular.module('appScheduling').directive('tooltip', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            $(element).hover(function () {
                $(element).tooltip('show');
            }, function () {
                $(element).tooltip('hide');
            });
        }
    };
});

angular.module('appScheduling')
    .controller('schedulingController', ['$scope', 'ngToast', 'schedulingService', schedulingController]);