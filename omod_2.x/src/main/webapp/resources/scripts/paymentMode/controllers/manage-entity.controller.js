/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 *
 */

(function() {
    'use strict';

    var base = angular.module('app.genericManageController');
    base.controller("ManagePaymentModesController", ManagePaymentModesController);
    ManagePaymentModesController.$inject = ['$injector', '$scope', '$filter', 'EntityRestFactory', 'CssStylesFactory',
        'PaginationService', 'PaymentModeModel', 'CookiesService'];

    function ManagePaymentModesController($injector, $scope, $filter, EntityRestFactory, CssStylesFactory, PaginationService, PaymentModeModel, CookiesService) {

        var self = this;

        var module_name = 'cashier';
        var entity_name = emr.message("openhmis.cashier.paymentMode.name");
        var rest_entity_name = emr.message("openhmis.cashier.paymentMode.rest_name");

        // @Override
        self.getModelAndEntityName = self.getModelAndEntityName || function() {
                self.bindBaseParameters(module_name, rest_entity_name, entity_name);
            }

        /* ENTRY POINT: Instantiate the base controller which loads the page */
        $injector.invoke(base.GenericManageController, self, {
            $scope: $scope,
            $filter: $filter,
            EntityRestFactory: EntityRestFactory,
            PaginationService: PaginationService,
            CssStylesFactory: CssStylesFactory,
            GenericMetadataModel: PaymentModeModel,
            CookiesService: CookiesService
        });
    }
})();
