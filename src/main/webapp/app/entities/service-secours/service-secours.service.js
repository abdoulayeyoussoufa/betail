(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('ServiceSecours', ServiceSecours);

    ServiceSecours.$inject = ['$resource'];

    function ServiceSecours ($resource) {
        var resourceUrl =  'api/service-secours/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
