/**
 * Created by dima on 30.04.16.
 */

function formSettingsController($scope, ngToast, $sce, formAppService) {

    $scope.questionRole;


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

    var getInfoToast = function (message) {
        var myToastMsg = ngToast.info({
            content: message,
            timeout: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            dismissOnClick: true,
            combineDuplications: true,
            maxNumber: 2
        });
    };


    $scope.getMandatory = function () {
        var role = "ROLE_STUDENT";
        $scope.questionRole = "Student question";
        $scope.questions = [];
        getAllQuestion(role);
    };

    $scope.getSoft = function () {
        var role = "ROLE_SOFT";
        $scope.questionRole = "Soft question";
        $scope.questions = [];
        getAllQuestion(role);
    };

    $scope.getTech = function () {
        var role = "ROLE_TECH";
        $scope.questionRole = "Tech question";
        getAllQuestion(role);
    };

    function getAllQuestion(role) {
        formAppService.getAllQuestion(role).then(function success(data) {
            console.log(data);
            for (var i = 0, len = data.length; i < len; i++) {
                if (typeof data[i].formAnswerVariants !== undefined
                    && data[i].formAnswerVariants !== undefined) {
                    data[i].variants = data[i].formAnswerVariants;
                    $scope.questions = data;
                }
            }
            console.log($scope.questions);
        });
    }

    $scope.types = [];
    formAppService.getAllQuestionType().then(function success(data) {
        angular.forEach(data, function (item, i) {
            console.log(item.typeTitle);
            $scope.types.push(item.typeTitle);
        });
    });


    $scope.showQuestion = function (question) {

        $scope.question = question;

        $scope.text = question.question;

        $scope.type = question.type;

        $scope.questionOrder = question.order;

        $scope.editVariant = [];
        angular.forEach(question.variants, function (item, i) {
            if (i == 0) {
                $scope.editVariant += item;
            } else {
                $scope.editVariant += " , " + item;
            }
        });
    };

    $scope.roles = ["ROLE_STUDENT", "ROLE_TECH", "ROLE_SOFT"];
    $scope.sce = $sce;

    var selectedValue;
    $scope.showSelectValue = function (mySelect) {
        console.log(mySelect);
        selectedValue = mySelect;
        if (selectedValue == "input" || selectedValue == "textarea") {
            $scope.canMoveForward = true;
            $scope.addVariant = null;
        } else {
            getInfoToast("You have to input variants separated by comma");
            $scope.canMoveForward = false;
        }
    };


    $scope.currentQuestion;
    $scope.getQuestion = function (question) {
        console.log(question);
        $scope.currentQuestion = question;
    };

    $scope.deleteQuestion = function () {
        console.log("delete" + $scope.currentQuestion.id);
        formAppService.deleteQuestion($scope.currentQuestion.id).then(function (response) {
            if (response.status === 200) {
                getInfoToast("Question deleted");
                angular.forEach($scope.questions, function (item, i) {
                    if (item.id === $scope.currentQuestion.id) {
                        $scope.questions.splice(i, 1);
                    }
                });

            }
        }).catch(function () {
            getInfoToast("Question not deleted");
        });
    };

    $scope.selectActiveValue;
    $scope.showSelectActiveValue = function (myActiveSelect) {
        $scope.selectActiveValue = myActiveSelect;
    };

    $scope.selectMandatoryValue;
    $scope.showSelectMandatoryValue = function (myMandatorySelect) {
        $scope.selectMandatoryValue = myMandatorySelect;
    };

    function splitString(stringToSplit, separator) {
        return stringToSplit.split(separator);
    };

    $scope.changeStatus = function (question) {
        console.log(question);
        formAppService.changeQuestionStatus(question.id).success(function (data) {
            console.log(data);
            question.enable = data;
        })
    };

    $scope.changeMandatoryStatus = function (question) {
        console.log(question);
        formAppService.changeQuestionMandatoryStatus(question.id).success(function (data) {
            console.log(data);
            question.mandatory = data;
        })
    };


    $scope.changeOrder = function (addOrder) {
        var x = 0;
        var length = $scope.questions.length;
        while (x < length) {
            console.log($scope.questions[x].order);
            if (addOrder == $scope.questions[x].order) {
                console.log("error");
                getInfoToast("Duplicate order number");
                $scope.cantAddQuestion = true;
                break;
            } else {
                $scope.addOrder = addOrder;
                $scope.cantAddQuestion = false;
                x++;
            }
            console.log("break");
        }

    };

    $scope.changeEditOrder = function (questionOrder) {
        var x = 0;
        var length = $scope.questions.length;
        while (x < length) {
            console.log($scope.questions[x].order);
            if (questionOrder == $scope.questions[x].order) {
                console.log("error");
                getInfoToast("Duplicate order number");
                $scope.cantEditQuestion = true;
                break;
            } else {
                $scope.cantEditQuestion = false;
                x++;
            }
            console.log("break");
        }
    };

    $scope.changeEditType = function (type) {
        if (type == "input" || type == "textarea") {
            console.log("input Error");
            $scope.cantEditVariant = true;
            $scope.editVariant = null;
        } else {
            $scope.cantEditVariant = false;
        }
    };


    $scope.saveForm = function () {
        var comma = ',';
        if ($scope.addVariant != null) {
            var variantArray = splitString($scope.addVariant, comma);
        } else {
            variantArray = [];
        }
        var role;
        if ($scope.questionRole == "Student question") {
            role = "ROLE_STUDENT";
        }
        if ($scope.questionRole == "Tech question") {
            role = "ROLE_TECH";
        }
        if ($scope.questionRole == "Soft question") {
            role = "ROLE_SOFT";
        }

        console.log($scope.addText + " " + selectedValue + " " + $scope.selectActiveValue + " " +
            $scope.selectMandatoryValue + " " + variantArray + " " + role + " " + $scope.addOrder);

        formAppService.addQuestion($scope.addText, selectedValue, $scope.selectActiveValue,
            $scope.selectMandatoryValue, variantArray, role, $scope.addOrder).then(function (response) {
            if (response.status === 200) {
                var questionTmp = {
                    enable: $scope.selectActiveValue, mandatory: $scope.selectMandatoryValue, order: $scope.addOrder,
                    title: $scope.addText, type: selectedValue, formAnswerVariants: variantArray
                };
                getInfoToast("Question added");
                $scope.questions.push(questionTmp);
            }
        }).catch(function () {
            getInfoToast("Question not added");
        });
    };


    $scope.saveEditQuestion = function () {

        $scope.question.title = $scope.text;

        $scope.question.type = $scope.type;

        var comma = ',';
        if ($scope.question.type == "input" || $scope.question.type == "textarea") {
            console.log("input Error");
        } else {
            $scope.cantEditVariant = false;
            var variantArray = splitString($scope.editVariant, comma);
        }
        console.log(variantArray);

        $scope.question.order = $scope.questionOrder;

        formAppService.editQuestion($scope.question.id, $scope.question.title, $scope.question.type,
            $scope.question.enable, variantArray, "ROLE_STUDENT", $scope.question.order, $scope.question.mandatory);
    };

    $scope.finalMarks = [0, 1, 2, 3];

    formAppService.getDecisionMatrix().then(function success(data) {
        $scope.decisionMatrix = data.data;
        console.log($scope.decisionMatrix);
    });

    $scope.saveDecisionMatrix = function () {
        formAppService.saveDecisionMatrix($scope.decisionMatrix).then(function success(data) {
            console.log('Updating decision');
            console.log(data);
            if (data.status === 200) {
                getInfoToast("Matrix updated");
            }
        }).catch(function () {
            getInfoToast("Question not added");
        });
    };
}

angular.module('appAdminForm')
    .controller('formSettingsController', ['$scope', 'ngToast', '$sce', 'formSettingsService', formSettingsController]);