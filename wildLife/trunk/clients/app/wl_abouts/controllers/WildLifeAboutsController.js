/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.WildLifeAbouts').controller('WildLifeAboutsController', WildLifeAboutsController);

    WildLifeAboutsController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        'WildLifeAboutsService',
        '$translate'
    ];


    function WildLifeAboutsController($rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, service,$translate) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;

    }

})();