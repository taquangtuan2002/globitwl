(function () {
    'use strict';

    angular.module('Education.LookUpTheSignature').controller('LookUpTheSignatureController', LookUpTheSignatureController);

    LookUpTheSignatureController.$inject = [
        '$rootScope',
        '$scope',
        'toastr',
        '$state',
        '$stateParams',
        '$uibModal',
        'LookUpTheSignatureService'
    ];

    function LookUpTheSignatureController($rootScope, $scope, toastr, $state, $stateParams, modal, service) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
        });

        let vm = this;

        vm.textSearch = "";
        vm.pageIndex = 1;
        vm.pageSize = 10;
        vm.totalItem = 0;
        vm.maxSize = 5;

        vm.searchByPage = function () {
            var searchObject = {};
            searchObject.title = vm.textSearch;
            searchObject.pageIndex = vm.pageIndex;
            searchObject.pageSize = vm.pageSize;

            service.searchByPage(searchObject).then(function (data) {
                vm.listUserAttachment = data.content;
                vm.totalItem = data.totalElements;
                console.log(vm.listUserAttachment);
            });
        }

        vm.searchByPage();

        vm.search = function () {
            var searchObject = {};
            searchObject.title = vm.textSearch;
            searchObject.pageIndex = 1;
            searchObject.pageSize = vm.pageSize;

            service.searchByPage(searchObject).then(function (data) {
                vm.listUserAttachment = data.content;
                vm.totalItem = data.totalElements;
                console.log(vm.listUserAttachment);
            });
        }

        vm.openFile = function (item) {
            if (item && item.file) {
                vm.fileOpen = item;
                vm.modalInstanceOpenImage = modal.open({
                    animation: true,
                    templateUrl: 'open_file_images_user_attachment.html',
                    scope: $scope,
                    size: 'lg'
                });
            }
        }
    
        vm.closeShowImage = function(){
            if (vm.modalInstanceOpenImage != null) {
                vm.modalInstanceOpenImage.close();
            }
        }

    }

})();