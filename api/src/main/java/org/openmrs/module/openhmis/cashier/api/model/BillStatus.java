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
package org.openmrs.module.openhmis.cashier.api.model;

/**
 * The allowable statuses that a {@link Bill} can have.
 */
public enum BillStatus {
	PENDING(0), POSTED(4), PAID(1), OVERPAID(5), CANCELLED(2), ADJUSTED(3);

	private int value;

	private BillStatus(int value) {
		this.value = value;
	}
}
