/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.InternalStatistics').controller('InternalStatisticsController', InternalStatisticsController);

    InternalStatisticsController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities'
    ];
    
    function InternalStatisticsController ($rootScope, $scope, $http, $timeout,settings,modal,toastr,$state,blockUI,$stateParams,utils) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
        // blockUI.stop();
        // blockUI.start();
        

    }

})();
