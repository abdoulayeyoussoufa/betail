(function() {
    'use strict';
    angular
        .module('mutuplexApp')
        .factory('Ayant_droit', Ayant_droit);

    Ayant_droit.$inject = ['$resource', 'DateUtils'];

    function Ayant_droit ($resource, DateUtils) {
        var resourceUrl =  'api/ayant-droits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_naissance = DateUtils.convertLocalDateFromServer(data.date_naissance);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date_naissance = DateUtils.convertLocalDateToServer(copy.date_naissance);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date_naissance = DateUtils.convertLocalDateToServer(copy.date_naissance);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
