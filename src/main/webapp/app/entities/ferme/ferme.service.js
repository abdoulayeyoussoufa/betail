(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('Ferme', Ferme);

    Ferme.$inject = ['$resource'];

    function Ferme ($resource) {
        var resourceUrl =  'api/fermes/:id';

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
