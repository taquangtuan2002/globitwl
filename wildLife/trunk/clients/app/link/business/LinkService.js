(function () {
    'use strict';

    angular.module('Education.Link').service('LinkService', LinkService);

    LinkService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function LinkService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'link/';

        self.getTableDefinition = getTableDefinition;
        self.getAllLink = getAllLink;
        self.getLinkById = getLinkById;
        self.saveLink = saveLink;
        self.deleteServiceByIds = deleteServiceByIds;
        self.deleteLinkById = deleteLinkById;
        self.getAllLinkDto = getAllLinkDto;
		self.getPageSearchLink=getPageSearchLink;
		self.checkDuplicateCode=checkDuplicateCode;

		function checkDuplicateCode(code) {
            var url = baseUrl + 'checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAllLinkDto(){
            var url = baseUrl + 'getalldtos';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteLinkById(id,successCallback,errorCallback){
            var url = baseUrl + 'delete/'+id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteServiceByIds(ids,successCallback, errorCallback){
            var url = baseUrl + 'delete/ids';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function saveLink(dto,successCallback,errorCallback){
            var url = baseUrl + 'save';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getLinkById(id){
            var url = baseUrl +id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllLink(pageIndex, pageSize) {
            var url = baseUrl + 'pagination/getall/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		
		function getPageSearchLink(dto,pageIndex, pageSize, successCallback, errorCallback) {
            if(dto != null){

                var url = baseUrl + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }
  
        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editLink(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"original.edit" | translate}}</a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteLinkRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"original.delete" | translate}}</a>';
            };
            var _tableInput = function (value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '120px' }
                };
            };

            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '90px', 'text-align': 'center' }
                };
            };

            var hyperLinkFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                if(value!= null || value.length > 0){
                    if(value.includes("http://") || value.includes("https://")){
                        return '<a href =" '+ value +'" target="_blank">' +value+ '</a>';
                    }else{
                        return '<a href ="http://'+ value +'" target="_blank">' +value+ '</a>';
                    }
                }
            };

            return [
                {
                    field: 'state',
                    checkbox: true
                },
                {
                    field: '',
                    title: '{{"link.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
                {
                    field: 'code',
                    title: '{{"link.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                ,{
                    field: 'name',
                    title: '{{"link.name" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'hyperLink',
                    title: '{{"link.hyperLink" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter:hyperLinkFormatter
                }, {
                    field: 'description',
                    title: '{{"link.description" | translate}}',
                    sortable: true,
                    // formatter: _tableInput,
                    cellStyle: _cellNowrap,
                    switchable: true,

                }
            ];
        }
    }
})();