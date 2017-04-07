(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('AssureDialogController', AssureDialogController);

  AssureDialogController.$inject = ['$http','$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Assure', 'Police', 'Groupe'];

    function AssureDialogController ($http,$timeout, $scope, $stateParams, $uibModalInstance, entity, Assure, Police, Groupe) {
        var vm = this;

        vm.assure = entity;
        vm.clear = clear;
        vm.save = save;
        vm.loadgroup=loadgroup;
        vm.police = Police.query();
        //vm.groupes1 = Groupe.query();
        function loadgroup(){
        	$http.get('api/groupes').then(function(result){
        		vm.groupes1=result.data;
//        		vm.groupes=[];
//        		var i=0;
//        		console.log(vm.groupes1.length);
//        		for(i=0;i<vm.groupes1.length;i++){
//        			console.log(vm.assure.assure_police.id);
//        			console.log(vm.groupes1[i].police.id);
//        			if(vm.groupes1[i].police.id==vm.assure.assure_police.id)
//        				vm.groupes.push(vm.groupes1[i]);
//        		}
        		vm.groupes = vm.groupes1.filter(function(item) {
    				return item.police.id==vm.assure.assure_police.id;
    			});
        	});
        	
        	
        }
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.assure.id !== null) {
                Assure.update(vm.assure, onSaveSuccess, onSaveError);
            } else {
                Assure.save(vm.assure, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mutuplexApp:assureUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
