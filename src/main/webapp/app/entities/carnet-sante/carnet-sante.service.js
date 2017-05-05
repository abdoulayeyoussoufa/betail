(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('CarnetSante', CarnetSante);

    CarnetSante.$inject = ['$resource', 'DateUtils'];

    function CarnetSante ($resource, DateUtils) {
        var resourceUrl =  'api/carnet-santes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datVaccination = DateUtils.convertDateTimeFromServer(data.datVaccination);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
