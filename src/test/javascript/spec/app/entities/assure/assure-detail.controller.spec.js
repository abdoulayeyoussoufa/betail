'use strict';

describe('Controller Tests', function() {

    describe('Assure Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAssure, MockPolice, MockGroupe;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAssure = jasmine.createSpy('MockAssure');
            MockPolice = jasmine.createSpy('MockPolice');
            MockGroupe = jasmine.createSpy('MockGroupe');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Assure': MockAssure,
                'Police': MockPolice,
                'Groupe': MockGroupe
            };
            createController = function() {
                $injector.get('$controller')("AssureDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mutuplexApp:assureUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
