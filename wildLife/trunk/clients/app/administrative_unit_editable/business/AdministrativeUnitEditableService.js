/**
 * Created by bizic on 30/8/2016.
 */
 (function () {
    'use strict';

    angular.module('Education.AdministrativeUnitEditable').service('AdministrativeUnitEditableService', AdministrativeUnitEditableService);

    AdministrativeUnitEditableService.$inject = [
        '$http',
        '$q',
        'settings',
        'Utilities',
		 '$translate'
    ];

    function AdministrativeUnitEditableService($http, $q, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;

        var urlUserAttachments = settings.api.baseUrl + settings.api.apiPrefix + "AdministrativeUnitEditable";

        self.getTableDefinition = getTableDefinition;
        self.getTableDefinitionFmsAdmin = getTableDefinitionFmsAdmin;
        self.deleteAdministrativeUnitEditables = deleteAdministrativeUnitEditables;
        self.getAllRoles = getAllRoles;
       // self.usernameDuplicates = usernameDuplicates;
       // self.findUserByUserName = findUserByUserName;
		self.getCurrentUser=getCurrentUser;
		self.getRolesByAccount=getRolesByAccount;
		self.getStream=getStream;
		self.saveFiles=saveFiles;
        self.getStreamDocument=getStreamDocument;
        self.searchByPage=searchByPage;
        self.getFmsAdmin = getFmsAdmin;
        self.getAdminstrativeUnitsCity=getAdminstrativeUnitsCity;
        self.saveOrUpdate = saveOrUpdate;
        self.deleteById = deleteById;
        self.getById = getById;
        self.getAdministrativeUnitDtoByLoginUser=getAdministrativeUnitDtoByLoginUser;
        self.getAllByParentId=getAllByParentId;
        
        function getAllByParentId(parentId) {
            var url = baseUrl + 'administrative/getAllByParentId/' + parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAdministrativeUnitDtoByLoginUser() {
            var url = baseUrl + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAdminstrativeUnitsCity() {
            var url = baseUrl + 'administrative/getall/city';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function searchByPage(dto) {
            var url = urlUserAttachments + '/searchByPage';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function saveOrUpdate(dto){
            var url = urlUserAttachments + '/save';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function deleteById(id,successCallback,errorCallback){
            var url = urlUserAttachments +'/delete/'+id;
            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function getById(id,successCallback,errorCallback){
            var url = urlUserAttachments +'/getAdministrativeUnitEditableById/'+id;
            return utils.resolveAlt(url, 'GET', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function getFmsAdmin(searchDto,pageIndex,pageSize){
            var url = baseUrl + '/administrative/'+'searchDto/'+pageIndex+'/'+pageSize;
            return utils.resolveAlt(url, 'POST', null, searchDto,pageIndex, pageSize, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getStream(dto) {
            console.log("RUNNING");
            var deferred = $q.defer();

            $http({
                url: settings.api.baseUrl + settings.api.apiPrefix + 'file/exportSearchUser',
                method: "POST",//you can use also GET or POST
                data: dto,
                headers: { 'Content-type': 'application/json' },
                responseType: 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                    console.error("ERROR");
                    deferred.reject(data);
                });

            return deferred.promise;
        };
		
		function getRolesByAccount() {
            var url = baseUrl + 'fms_users/getRolesByAccount';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		
		function getCurrentUser() {
            var url = baseUrl + 'fms_users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
	
		function saveFiles(files) {
            var url = baseUrl + 'user_attachments/save/';
            return utils.resolveAlt(url, 'POST', null, files, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        } 

        function getAllRoles() {
            var url = baseUrl + 'roles/all';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

      
        function deleteAdministrativeUnitEditables(dtos, successCallback, errorCallback) {
            if (!dtos || dtos.length <= 0) {
                return $q.when(null);
            }

            var url = baseUrl + 'fms_users/remove';
            return utils.resolveAlt(url, 'DELETE', null, dtos, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function findUserByUserName(username, pageIndex, pageSize) {
            var url = baseUrl + 'fms_users/username/' + username + '/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        //tran huu dat tạo hàm xuất file word start
        function getStreamDocument(tranhuudat) {
            console.log("RUNNING");
            var deferred = $q.defer();

            $http({
                url: settings.api.baseUrl + settings.api.apiPrefix + 'file/downloadForestList',
                method: "POST",//you can use also GET or POST
                data: tranhuudat,
                headers: { 'Content-type': 'application/json' },
                responseType: 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                    console.error("ERROR");
                    deferred.reject(data);
                });

            return deferred.promise;
        };
        //tran huu dat tạo hàm xuất file word end
        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a ng-show="!$parent.vm.isSdah" class="green-dark margin-right-5" href="#" data-ng-click="$parent.editAdministrativeUnitEditable(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i>{{"animal.edit" | translate}}</a>'
				 + '<a ng-show="!$parent.vm.isSdah" class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteAdministrativeUnitEditableRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"animal.delete" | translate}}</a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space': 'nowrap','width' : '70px'}
                };
            };

            var _cellNowrap2 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space': 'nowrap', 'width' : '110px'}
                };
            };
           
            var _cellNowrap3 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space': 'nowrap', 'width' : '230px'}
                };
            };

            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };

            var _cellState = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space' : 'nowrap', 'width': '1%'}
                };
            };

            var _cellActions = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space' : 'nowrap', 'width' : '10%'}
                }
            };
            var _createDateFormatter = function (value, row, index) {
                
                if (!value) {
                    return '';
                } else {
                    var year = value[0];
                    var month = value[1];
                    var date = value[2];
                    var hour = value[3];
                    var minutes = value[4];
                    var seconds = value[5];
                    return date + '/' + month + '/' + year + ' | ' + hour + ':' + minutes + ':' + seconds;
                }
            };

            return [
                {
                    field: 'state',
                    checkbox: true,
                    cellStyle: _cellState
                }
                , {
                    field: '',
                    title: '{{"user.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _cellActions
                }
                , {
                    field: 'adminUnit.name',
                    title: 'Đơn vị',
                    sortable: true,
                    switchable: true,
                    cellStyle: _cellNowrap3
                }
                , {
                    field: 'roles',
                    title: 'Role',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap3
                }
                , {
                    field: 'editable',
                    title: 'Có thể chỉnh sửa',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap3
                }
                , {
                    field: 'quater',
                    title: 'Quý',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap3
                }
                , {
                    field: 'year',
                    title: 'Năm',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap3
                }
                
            ]
        }
        function getTableDefinitionFmsAdmin() {

            var _tableOperation = function (value, row, index) {
             
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
                    css: { 'white-space': 'nowrap'}
                };
            };
            var _formatterName = function (value, row, index, field){
                if(value && value.name){
                    return value.name;
                }
                return '';
            }
            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };

            return [
                {
                    field: 'state',
                    checkbox: true,
                }
                ,
                {
                    field: 'code',
                    title: 'Mã đơn vị hành chính',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'name',
                    title: 'Tên đơn vị hành chính',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }

            ];
        }
    }
})();