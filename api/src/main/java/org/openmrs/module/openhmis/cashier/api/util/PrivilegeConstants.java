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
 */
package org.openmrs.module.openhmis.cashier.api.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.Privilege;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.jasperreport.util.JasperReportPrivilegeConstants;
import org.openmrs.module.openhmis.commons.web.PrivilegeConstantsCompatibility;

/**
 * Constants class for module privilege constants.
 */
public class PrivilegeConstants {
	public static final String MANAGE_BILLS = "Manage Cashier Bills";
	public static final String ADJUST_BILLS = "Adjust Cashier Bills";
	public static final String VIEW_BILLS = "View Cashier Bills";
	public static final String PURGE_BILLS = "Purge Cashier Bills";

	public static final String REFUND_MONEY = "Refund Money";
	public static final String REPRINT_RECEIPT = "Reprint Receipt";

	public static final String MANAGE_METADATA = "Manage Cashier Metadata";
	public static final String VIEW_METADATA = "View Cashier Metadata";
	public static final String PURGE_METADATA = "Purge Cashier Metadata";

	public static final String MANAGE_TIMESHEETS = "Manage Cashier Timesheets";
	public static final String VIEW_TIMESHEETS = "View Cashier Timesheets";
	public static final String PURGE_TIMESHEETS = "Purge Cashier Timesheets";

	public static final String APP_VIEW_CASHIER_APP = "App: View Cashier App";
	public static final String TASK_CREATE_NEW_BILL_PAGE = "Task: Create new bill";
	public static final String TASK_CASHIER_TIMESHEETS_PAGE = "Task: Cashier Timesheets";
	public static final String TASK_MANAGE_CASHIER_MODULE_PAGE = "Task: Manage Cashier Module";
	public static final String TASK_CASHIER_ROLE_CREATION_PAGE = "Task: Cashier Role Creation";
	public static final String TASK_MANAGE_CASH_POINTS_PAGE = "Task: Manage Cash Points";
	public static final String TASK_MANAGE_PAYMENT_MODES_PAGE = "Task: Manage Payment Modes";
	public static final String TASK_MANAGE_RECEIPT_NUMBER_GENERATOR_PAGE = "Task: Manage Receipt Number Generator";
	public static final String TASK_MANAGE_SETTINGS_PAGE = "Task: Access Cashier Settings";

	public static final String[] PRIVILEGE_NAMES = new String[] { MANAGE_BILLS, ADJUST_BILLS, VIEW_BILLS, PURGE_BILLS,
	        REFUND_MONEY, REPRINT_RECEIPT, MANAGE_TIMESHEETS, VIEW_TIMESHEETS, PURGE_TIMESHEETS, MANAGE_METADATA,
	        VIEW_METADATA, PURGE_METADATA, APP_VIEW_CASHIER_APP, TASK_CREATE_NEW_BILL_PAGE, TASK_CASHIER_TIMESHEETS_PAGE,
	        TASK_MANAGE_CASHIER_MODULE_PAGE, TASK_CASHIER_ROLE_CREATION_PAGE, TASK_MANAGE_CASH_POINTS_PAGE,
	        TASK_MANAGE_PAYMENT_MODES_PAGE, TASK_MANAGE_RECEIPT_NUMBER_GENERATOR_PAGE, TASK_MANAGE_SETTINGS_PAGE };

	protected PrivilegeConstants() {}

	/**
	 * Gets all the privileges defined by the module.
	 * @return The module privileges.
	 */
	public static Set<Privilege> getModulePrivileges() {
		Set<Privilege> privileges = new HashSet<Privilege>(PRIVILEGE_NAMES.length);

		UserService service = Context.getUserService();
		if (service == null) {
			throw new IllegalStateException("The OpenMRS user service cannot be loaded.");
		}

		for (String name : PRIVILEGE_NAMES) {
			privileges.add(service.getPrivilege(name));
		}

		return privileges;
	}

	/**
	 * Gets the default privileges needed to fully use the module.
	 * @return A set containing the default set of privileges.
	 */
	public static Set<Privilege> getDefaultPrivileges() {
		Set<Privilege> privileges = getModulePrivileges();

		UserService service = Context.getUserService();
		if (service == null) {
			throw new IllegalStateException("The OpenMRS user service cannot be loaded.");
		}

		List<String> names = new ArrayList<String>();
		// Add other required cashier privileges
		names.add(org.openmrs.module.openhmis.inventory.api.util.PrivilegeConstants.VIEW_ITEMS);
		names.add(org.openmrs.module.openhmis.inventory.api.util.PrivilegeConstants.VIEW_METADATA);
		names.add(JasperReportPrivilegeConstants.VIEW_JASPER_REPORTS);

		PrivilegeConstantsCompatibility privilegeConstantsCompatibility = new PrivilegeConstantsCompatibility();

		names.add(privilegeConstantsCompatibility.getAddEncountersPrivilege());
		names.add(privilegeConstantsCompatibility.getAddVisitsPrivilege());
		names.add(privilegeConstantsCompatibility.getEditEncountersPrivilege());
		names.add(privilegeConstantsCompatibility.getEditPatientsPrivilege());
		names.add(privilegeConstantsCompatibility.getEditVisitsPrivilege());
		names.add(privilegeConstantsCompatibility.getDashboardSummaryPrivilege());
		names.add(privilegeConstantsCompatibility.getDashboardDemographicsPrivilege());
		names.add(privilegeConstantsCompatibility.getDashboardOverviewPrivilege());
		names.add(privilegeConstantsCompatibility.getDashboardVisitsPrivilege());
		names.add(privilegeConstantsCompatibility.getViewAdminFunctionsPrivilege());
		names.add(privilegeConstantsCompatibility.getViewConceptsPrivilege());
		names.add(privilegeConstantsCompatibility.getViewEncountersPrivilege());
		names.add(privilegeConstantsCompatibility.getViewNavigationMenuPrivilege());
		names.add(privilegeConstantsCompatibility.getViewObsPrivilege());
		names.add(privilegeConstantsCompatibility.getViewPatientsPrivilege());
		names.add(privilegeConstantsCompatibility.getViewProvidersPrivilege());
		names.add(privilegeConstantsCompatibility.getViewVisitPrivilege());

		for (String name : names) {
			privileges.add(service.getPrivilege(name));
		}

		return privileges;

	}
}
