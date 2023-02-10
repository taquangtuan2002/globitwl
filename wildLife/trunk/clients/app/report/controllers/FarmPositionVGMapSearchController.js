/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('FarmPositionVGMapSearchController', FarmPositionVGMapSearchController);

    FarmPositionVGMapSearchController.$inject = [
        '$rootScope',
        '$scope',
        "$sce"
    ];

    function FarmPositionVGMapSearchController($rootScope, $scope,$sce) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });
        let ts = new Date().getTime();
        $rootScope.iframeUrlSearch=$sce.trustAsResourceUrl("https://fao.gisgo.vn/samples/search.html"+ "?ts="+ts);
    }
})();