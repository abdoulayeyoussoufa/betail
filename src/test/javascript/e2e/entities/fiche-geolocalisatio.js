'use strict';

describe('FicheGeolocalisatio e2e test', function () {

    var username = element(by.id('username'));
    var password = element(by.id('password'));
    var entityMenu = element(by.id('entity-menu'));
    var accountMenu = element(by.id('account-menu'));
    var login = element(by.id('login'));
    var logout = element(by.id('logout'));

    beforeAll(function () {
        browser.get('/');

        accountMenu.click();
        login.click();

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
    });

    it('should load FicheGeolocalisatios', function () {
        entityMenu.click();
        element.all(by.css('[ui-sref="fiche-geolocalisatio"]')).first().click().then(function() {
            element.all(by.css('h2')).first().getAttribute('data-translate').then(function (value) {
                expect(value).toMatch(/volBetailApp.ficheGeolocalisatio.home.title/);
            });
        });
    });

    it('should load create FicheGeolocalisatio dialog', function () {
        element(by.css('[ui-sref="fiche-geolocalisatio.new"]')).click().then(function() {
            element(by.css('h4.modal-title')).getAttribute('data-translate').then(function (value) {
                expect(value).toMatch(/volBetailApp.ficheGeolocalisatio.home.createOrEditLabel/);
            });
            element(by.css('button.close')).click();
        });
    });

    afterAll(function () {
        accountMenu.click();
        logout.click();
    });
});
