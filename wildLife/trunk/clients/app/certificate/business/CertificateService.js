(function () {
    'use strict';

    angular.module('Education.Certificate').service('CertificateService', CertificateService);

    CertificateService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function CertificateService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'certificate/';

        self.getTableDefinition = getTableDefinition;
        self.getAllCertificate = getAllCertificate;
        self.getCertificateById = getCertificateById;
        self.createCertificate = createCertificate;
        self.updateCertificate = updateCertificate;
        self.deleteCertificate = deleteCertificate;
        self.deleteCertificates = deleteCertificates;
        self.getAllCertificateDto = getAllCertificateDto;
		self.getPageSearchCertificate=getPageSearchCertificate;
		self.checkDuplicateCode=checkDuplicateCode;

		function checkDuplicateCode(code) {
            var url = baseUrl + 'checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAllCertificateDto(){
            var url = baseUrl + 'getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function deleteCertificates(ids, successCallback, errorCallback) {
            var url = baseUrl + 'delete';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteCertificate(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function updateCertificate(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function createCertificate(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getCertificateById(id) {
            var url = baseUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllCertificate(pageIndex, pageSize) {
            var url = baseUrl + 'page/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getPageSearchCertificate(dto,pageIndex, pageSize, successCallback, errorCallback) {
            if(dto != null){

                var url = baseUrl + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

       
        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editCertificate(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"animalType.edit" | translate}}</a>'
                 
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteCertificateRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"animalType.delete" | translate}}</a>';
            };
         

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            

           
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '90px', 'text-align': 'center' }
                };
            };
               

            return [
                {
                    field: 'state',
                    checkbox: true
                },
                {
                    field: '',
                    title: ' {{"certificate.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
            

                {
                    field: 'code',
                    title:'{{"certificate.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'name',
                    title: '{{"certificate.name" | translate}}' ,
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }, {
                    field: 'description',
                    title:'{{"certificate.description" | translate}}',
                    sortable: true,
                   
                    switchable: false,
                    cellStyle: _cellNowrap

                }
            ];
        }
    }
})();