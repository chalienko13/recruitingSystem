/**
 * Created by dima on 30.04.16.
 */
function staffManagementController($scope, $rootScope, ngToast, $filter, $http, staffManagementService) {

    $scope.sort = {
        sortingOrder: 1,
        reverse: true
    };

    $scope.gap = 5;
    $scope.filtered = false;
    $scope.filteredItems = [];
    $scope.groupedItems = [];
    $scope.itemsPerPage = 15;
    $scope.currentPage = 1;
    $scope.items = [];
    $scope.amount = 0;
    $scope.sortingDir = 1;
    $scope.startId = 0;
    $scope.finishId = 1000;
    $scope.rolesChoosen = [1, 2, 5];
    $scope.interviewer = true;
    $scope.notInterviewer = true;
    $scope.notEvaluated = true;
    $scope.assignedStudents = [];
    $scope.notMarked = [];

    function countAmountOfPages(allNum) {
        $scope.amount = Math.ceil(allNum / $scope.itemsPerPage);
    }

    // init the sorted items
    $scope.$watch("sort.reverse", function () {
        $scope.currentPage = 1;
        if ($scope.filtered)
            $scope.showFilteredEmployees($scope.currentPage);
        else
            $scope.showAllEmployees($scope.currentPage);
    });

    $scope.$watch("sort.sortingOrder", function () {
        $scope.currentPage = 1;
        if ($scope.filtered)
            $scope.showFilteredEmployees($scope.currentPage);
        else
            $scope.showAllEmployees($scope.currentPage);
    });

    $scope.$watch("itemsPerPage", function () {
        $scope.currentPage = 1;
        $scope.getCountOfEmployee();
        if ($scope.filtered)
            $scope.showFilteredEmployees($scope.currentPage);
        else
            $scope.showAllEmployees($scope.currentPage);
    });

    staffManagementService.showFilteredEmployees(makeFiltrationObj($scope.currentPage)).success(function (data) {
        angular.forEach(data, function (value1, key1) {
            angular.forEach(value1.roles, function (value2, key2) {
                value2.roleName = value2.roleName.slice(5);
            })
        });
        $scope.allEmployee = data;
    }, function error() {
        console.log("error");
    });

    $scope.confirmStaff = function () {
        staffManagementService.confirmStaffService().then(function (response) {
            if (response.status == 200){
                
            }else{
                //TODO toast
            }
        })
    };

    staffManagementService.showAllEmployees(1, 10, $scope.sort.sortingOrder, $scope.sort.reverse).success(function (data) { //TODO
        angular.forEach(data, function (value1, key1) {
            angular.forEach(value1.roles, function (value2, key2) {
                value2.roleName = value2.roleName.slice(5);
            })
        });
        $scope.allEmployee = data;
        console.log(data);

    }, function error() {
        console.log("error");
    });

    $scope.roleEdit = function(roleName){
        return roleName.slice(5);
    };

    staffManagementService.getCountOfEmployee().success(function (data) {
        countAmountOfPages(data)
    });

    $scope.getCountOfEmployee = function () {
        staffManagementService.getCountOfEmployee().success(function (data) {
            countAmountOfPages(data)
        });
    };

    $scope.showFilteredEmployees = function showFilteredEmployees(pageNum) {
        staffManagementService.showFilteredEmployees(makeFiltrationObj(pageNum)).success(function (data) { //TODO
            angular.forEach(data, function (value1, key1) {
                angular.forEach(value1.roles, function (value2, key2) {
                    value2.roleName = value2.roleName.slice(5);
                })
            });
            $scope.allEmployee = data;
            console.log(data);
        }, function error() {
            console.log("error");
        });
    };

    function makeFiltrationObj(pageNum){
        var filtrationParams = {
            pageNum:pageNum,
            rowsNum:$scope.itemsPerPage,
            sortingCol:$scope.sort.sortingOrder,
            increase:$scope.sort.reverse,
            idStart:$scope.startId,
            idFinish:$scope.finishId,
            rolesId:$scope.rolesChoosen,
            interviewer:$scope.interviewer,
            notInterviewer:$scope.notInterviewer,
            notEvaluated:$scope.notEvaluated
        };
        return filtrationParams;
    }

    $scope.showAllEmployees = function showAllEmployees(pageNum) {

        staffManagementService.showAllEmployees(pageNum, $scope.itemsPerPage, $scope.sort.sortingOrder, $scope.sort.reverse).success(function (data) { //TODO
            $scope.users = [];
            angular.forEach(data, function (value1, key1) {
                angular.forEach(value1.roles, function (value2, key2) {
                    value2.roleName = value2.roleName.slice(5);
                });
                console.log(value1);
                $scope.users.push(value1.email);
            });
            $scope.allEmployee = data;
            console.log(data);

            staffManagementService.hasNotMarked($scope.users).success(function (data) {
                $scope.notMarked = data;
                console.log($scope.notMarked);
            }, function error() {
                console.log("error");
            });

        }, function error() {
            console.log("error");
        });
    };


    $scope.employees =
        [{roleName: 'ROLE_ADMIN'},
            {roleName: 'ROLE_SOFT'},
            {roleName: 'ROLE_TECH'}];

    $scope.selection = [];


    $scope.toggleSelection = function toggleSelection(employeeName) {
        var idx = $scope.selection.indexOf(employeeName);

        if (idx > -1) {
            $scope.selection.splice(idx, 1);
        }

        else {
            $scope.selection.push(employeeName);
        }
        console.log($scope.selection);
    };

    $scope.addEmployee = function () {

        var roleArray = [];

        angular.forEach($scope.selection, function (item, i) {
            roleArray.push({roleName: item})
        });
        
        

        staffManagementService.addEmployee($scope.firstName, $scope.secondName,
            $scope.lastName, $scope.email, roleArray);
    };

    $scope.range = function (size, start, end) {
        var ret = [];
        console.log(size, start, end);


        if (size < end) {
            end = size;
            start = size - $scope.gap;
            end++;
            start++;
        }
        for (var i = start; i < end; i++) {
            if (i > 0)
                ret.push(i);
        }
        return ret;
    };

    $scope.prevPage = function () {
        if ($scope.currentPage > 0) {
            $scope.currentPage--;
        }
    };

    $scope.nextPage = function () {
        if ($scope.currentPage < countAmountOfPages($scope.amount) - 1) {
            $scope.currentPage++;
        }
    };

    $scope.setPage = function () {
        $scope.currentPage = this.n;
        if ($scope.filtered)
            $scope.showFilteredEmployees($scope.currentPage);
        else
            $scope.showAllEmployees($scope.currentPage);
    };

    //var editRoles = [];
    $scope.editRoles = [];
    $scope.showUserData = function (employee) {
        $scope.adminEdit = false;
        $scope.softEdit = false;
        $scope.techEdit = false;
        $scope.id = employee.id;
        $scope.emailEdit = employee.email;
        $scope.firstNameEdit = employee.firstName;
        $scope.secondNameEdit = employee.secondName;
        $scope.lastNameEdit = employee.lastName;
        console.log(employee.roles);
        angular.forEach(employee.roles, function (item, i) {
            if (item.roleName == "ADMIN") {
                $scope.adminEdit = true;
                //$scope.editRoles.push({roleName: item.roleName});
            }
            if (item.roleName == "SOFT") {
                $scope.softEdit = true;
                // $scope.editRoles.push({roleName: item.roleName});
            }
            if (item.roleName == "TECH") {
                $scope.techEdit = true;
                //$scope.editRoles.push({roleName: item.roleName});
            }
            //TODO change logic
        });

        // editRoles = [];
        // editRoles.push({roleName: "ADMIN"});
    };

    $scope.checkRole = function () {
        if ($scope.adminEdit) $scope.editRoles.push({roleName: "ADMIN"});
        if ($scope.softEdit)$scope.editRoles.push({roleName: "SOFT"});
        if ($scope.techEdit)$scope.editRoles.push({roleName: "TECH"});
    };

    $scope.editEmployee = function () {
        $scope.checkRole();
        var roleEditCheck;

        angular.forEach($scope.editRoles, function (item, i) {
            roleEditCheck += item.roleName;
        });
        
        if ($rootScope.id == $scope.id && roleEditCheck.toLowerCase().indexOf("admin") <= 0) {
            var myToastMsg = ngToast.warning({
                content: 'You can not delete admin role',
                timeout: 5000,
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                dismissOnClick: true,
                combineDuplications: true,
                maxNumber: 2
            });
        } else {
            staffManagementService.editEmployee($scope.id, $scope.firstNameEdit, $scope.secondNameEdit,
                $scope.lastNameEdit, $scope.emailEdit, $scope.editRoles);
        }
        $scope.editRoles = [];
    };

    $scope.changeEmployeeStatus = function (employee) {
        console.log($rootScope.id);
        if ($rootScope.id == employee.id) {
            var myToastMsg = ngToast.warning({
                content: 'You can not make themselves not active',
                timeout: 5000,
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                dismissOnClick: true,
                combineDuplications: true,
                maxNumber: 2
            });
        } else {
            staffManagementService.changeEmployeeStatus(employee.email).success(function (data) {
                employee.active = data;
            });
        }
    };

    $scope.showAssigned = function (employee) {
        $http({
            method: 'POST',
            url: '/admin/showAssignedStudent',
            params: {email: employee.email}
        }).success(function (data, status, headers) {
            console.log(data);
            $scope.assignedStudents = data;
            return data;
        }).error(function (data, status, headers) {
            console.log(status);
        });
    };

    var currentEmployee;
    $scope.getEmployee = function (employee) {
        currentEmployee = employee;
    };

    $scope.deleteEmployee = function () {
        if ($rootScope.id == currentEmployee.id) {
            var myToastMsg = ngToast.warning({
                content: 'You can not remove yourself',
                timeout: 5000,
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                dismissOnClick: true,
                combineDuplications: true,
                maxNumber: 2
            });
        } else {
            console.log(currentEmployee);
            staffManagementService.deleteEmployee(currentEmployee.email);
            var index = $scope.allEmployee.indexOf(currentEmployee);
            $scope.allEmployee.splice(index, 1);
        }
    };

    $scope.deleteAssignedStudent = function () {
        console.log('deleteAssignedStudent');
        console.log(currentEmployee);
        staffManagementService.deleteAssignedStudent(currentEmployee);
        var index = $scope.assignedStudents.indexOf(currentEmployee);
        $scope.assignedStudents.splice(index, 1);
    };

    $scope.showFiltration = function () {
        // Slider
        $scope.getMaxId();
    };

    $scope.getMaxId = function getMaxId() {
        staffManagementService.getMaxId().success(function (data) {
            $scope.max = data;
            console.log(data);
            $scope.slider = {
                minValue: 0,
                maxValue: $scope.max,
                options: {
                    floor: 0,
                    ceil: $scope.max,
                    step: 1,
                    noSwitching: true
                }
            }
        }, function error() {
            console.log("error");
        });
    };

    $scope.searchEmployee = function (employeeName) {
        console.log(employeeName);
        staffManagementService.searchEmployee(employeeName).success(function (data) {
            console.log(data);
            $scope.allEmployee = data;
        }, function error() {
            console.log("error");
        });
    };

    $scope.toggle = function (item, list) {
        var idx = -1;
        for (var i = 0; i < list.length; i++) {
            if (list[i] == item)
                idx = i;
        }
        if (idx > -1) {
            list.splice(idx, 1);
        }
        else {
            list.push(item);
        }
        console.log(list);
    };

    $scope.existingRoles =
        [{
            roleName: 'ADMIN',
            id: 1
        },
            {
                roleName: 'SOFT',
                id: 5
            },
            {
                roleName: 'TECH',
                id: 2
            }];

    $scope.filter = function () {
        $scope.startId = $scope.slider.minValue;
        $scope.finishId = $scope.slider.maxValue;
        $scope.currentPage = 1;
        $scope.showFilteredEmployees($scope.currentPage);

        staffManagementService.getCountOfEmployeeFiltered(makeFiltrationObj($scope.currentPage)).success(function (data) {
            countAmountOfPages(data)
        });
        $scope.filtered = true;
    };

    staffManagementService.showActiveEmployee().success(function (data) {
        console.log("ACTIVE" + data);
        $scope.activeTech = data[0];
        $scope.activeSoft = data[1];
        $scope.activeTechSoft = data[2];
    });

    $scope.exists = function (item, list) {
        // console.log(item + " === " + list);
        for (var i = 0; i < list.length; i++) {
            if (list[i] === item)
                return true;
        }
        return false;
    }


}

angular.module('appStaffManagement').$inject = ['$scope', '$filter'];

angular.module('appStaffManagement').directive("customSort", function () {
    return {
        restrict: 'A',
        transclude: true,
        scope: {
            order: '=',
            sort: '='
        },
        template: ' <a ng-click="sort_by(order)" style="color: #555555;">' +
        '    <span ng-transclude></span>' +
        '    <i ng-class="selectedCls(order)"></i>' +
        '</a>',
        link: function (scope) {

            // change sorting order
            scope.sort_by = function (newSortingOrder) {
                var sort = scope.sort;

                if (sort.sortingOrder == newSortingOrder) {
                    sort.reverse = !sort.reverse;
                }

                sort.sortingOrder = newSortingOrder;
            };


            scope.selectedCls = function (column) {
                if (column == scope.sort.sortingOrder) {
                    return (' glyphicon glyphicon-chevron-' + ((scope.sort.reverse) ? 'down' : 'up'));
                }
                else {
                    return 'glyphicon glyphicon-sort'
                }
            };
        }
    };
    
    
});


angular.module('appStaffManagement')
    .controller('staffManagementController', ['$scope', '$rootScope', 'ngToast', '$filter', '$http', 'staffManagementService', staffManagementController]);