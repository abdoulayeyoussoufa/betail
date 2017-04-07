(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .directive('activeLink', activeLink)
        .directive('sideNavigation', sideNavigation)
        .directive('minimalizaSidebar', minimalizaSidebar)
        .directive('iboxTools', iboxTools);
    sideNavigation.$inject = ['$timeout'];
    minimalizaSidebar.$inject = ['$timeout'];
    fullScroll.$inject = ['$timeout'];
    iboxTools.$inject = ['$timeout'];
    
    MinimalizaSidebarController.$inject = ['$scope', '$element'];
    iboxToolsController.$inject = ['$scope', '$element','$timeout'];
    
    function activeLink() {
        var directive = {
            restrict: 'A',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            var clazz = attrs.activeLink;
            var path = attrs.href;
            path = path.substring(1); //hack because path does bot return including hashbang
            scope.location = location;
            scope.$watch('location.path()', function(newPath) {
                if (path === newPath) {
                    element.addClass(clazz);
                } else {
                    element.removeClass(clazz);
                }
            });
        }
    }
    function sideNavigation($timeout) {
        return {
            restrict: 'A',
            link: linkside
        };
        
        function linkside(scope, element) {
            // Call the metsiMenu plugin and plug it to sidebar navigation
            $timeout(function(){
                element.metisMenu();

            });

            // Colapse menu in mobile mode after click on element
            var menuElement = $('#side-menu a:not([href$="\\#"])');
            menuElement.click(function(){
                if ($(window).width() < 769) {
                    $("body").toggleClass("mini-navbar");
                }
            });

            // Enable initial fixed sidebar
            if ($("body").hasClass('fixed-sidebar')) {
                var sidebar = element.parent();
                sidebar.slimScroll({
                    height: '100%',
                    railOpacity: 0.9,
                });
            }
        }
    }
    /**
     * minimalizaSidebar - Directive for minimalize sidebar
    */
    function minimalizaSidebar($timeout) {
        return {
            restrict: 'A',
            template: '<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="" ng-click="minimalize()"><i class="fa fa-bars"></i></a>',
            controller: MinimalizaSidebarController,
            controllerAs: 'vm',
            bindToController: true
        };
    }
    function MinimalizaSidebarController ($scope, $element) {
        $scope.minimalize = function () {
            $("body").toggleClass("mini-navbar");
            if (!$('body').hasClass('mini-navbar') || $('body').hasClass('body-small')) {
                // Hide menu in order to smoothly turn on when maximize menu
                $('#side-menu').hide();
                // For smoothly turn on menu
                setTimeout(
                    function () {
                        $('#side-menu').fadeIn(400);
                    }, 200);
            } else if ($('body').hasClass('fixed-sidebar')){
                $('#side-menu').hide();
                setTimeout(
                    function () {
                        $('#side-menu').fadeIn(400);
                    }, 100);
            } else {
                // Remove all inline style from jquery fadeIn function to reset menu state
                $('#side-menu').removeAttr('style');
            }
        }
    }
    /**
     * fullScroll - Directive for slimScroll with 100%
     */
    function fullScroll($timeout){
        return {
            restrict: 'A',
            link: linkScrol
        };
        
        function linkScrol(scope, element) {
            $timeout(function(){
                element.slimscroll({
                    height: '100%',
                    railOpacity: 0.9
                });

            });
        }
    }
    
    /**
     * iboxTools - Directive for iBox tools elements in right corner of ibox
     */
    function iboxTools($timeout) {
        return {
            restrict: 'A',
            scope: true,
            templateUrl: 'app/components/common/ibox_tools.html',
            controller: iboxToolsController
        };
    }
    function iboxToolsController ($scope, $element,$timeout) {
        // Function for collapse ibox
        $scope.showhide = function () {
            var ibox = $element.closest('div.ibox');
            var icon = $element.find('i:first');
            var content = ibox.children('.ibox-content');
            content.slideToggle(200);
            // Toggle icon from up to down
            icon.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
            ibox.toggleClass('').toggleClass('border-bottom');
            $timeout(function () {
                ibox.resize();
                ibox.find('[id^=map-]').resize();
            }, 50);
        };
            // Function for close ibox
            $scope.closebox = function () {
                var ibox = $element.closest('div.ibox');
                ibox.remove();
            }
    }
    
    
})();
