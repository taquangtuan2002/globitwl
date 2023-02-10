/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Settings').controller('SettingsController', SettingsController);

    SettingsController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings'
    ];
    
    function SettingsController ($rootScope, $scope, $http, $timeout,settings) {
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
