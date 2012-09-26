define(
	[
		'lib/jquery',
		'lib/underscore',
		'view/generic',
		'view/editors'
	],
	function($, _, openhmis) {
		openhmis.BillLineItemView = openhmis.GenericListItemView.extend({
			initialize: function(options) {
				this.events = _.extend(this.events, {
					'keypress': 'onKeyPress'
				});
				openhmis.GenericListItemView.prototype.initialize.call(this, options);
				_.bindAll(this);
				this.form.on('price:change', this.update);
				this.form.on('quantity:change', this.update);
				this.form.on('item:change', this.updateItem);
				this.form.validate = function() { return null; }
			},
			
			updateItem: function(form, itemEditor) {
				var item = itemEditor.getValue();
				form.fields.price.setValue(item.get('defaultPrice').price);
				if (form.fields.quantity.getValue() === 0)
					form.fields.quantity.setValue(1);
				this.update();
				this.$('.field-quantity input').focus();
			},
			
			update: function() {
				if (this.updateTimeout !== undefined) clearTimeout(this.updateTimeout);
				var view = this;
				var update = function() {
					var price = view.form.getValue("price");
					var quantity = view.form.getValue("quantity");
					view.form.setValue({ total: price * quantity });
				}
				this.updateTimeout = setTimeout(update, 200);
			},
			
			onKeyPress: function(event) {
				if (event.charCode === 13) {
					var errors = this.form.commit();
					if (!errors) {
						this.model.trigger('validated', this.model);
					}
				}
			},
			
			focus: function(event) {
				openhmis.GenericListItemView.prototype.focus.call(this, event);
				var $department = this.$('.department');
				if ($department.val() === "")
					$department.focus();
				else
					this.$('.item-name').focus();
			},
			
			removeModel: function() {
				this.model.collection.remove(this.model);
			},
			
			render: function() {
				openhmis.GenericListItemView.prototype.render.call(this);
				this.$(".field-price input, .field-total input").attr("readonly", "readonly");
				return this;
			}
		});

		openhmis.BillView = openhmis.GenericListView.extend({
			initialize: function(options) {
				openhmis.GenericListView.prototype.initialize.call(this, options);
				this.itemView = openhmis.BillLineItemView;
			},
			itemActions: ['remove', 'inlineEdit'],
			schema: {
				item: { type: 'Item' },
				quantity: { type: 'CustomNumber', nonNegative: true },
				price: { type: 'BasicNumber', readOnly: true }
			},
			
			addOne: function(model, schema) {
				var view = openhmis.GenericListView.prototype.addOne.call(this, model, schema);
				if (view.model.cid === this.newItem.cid)
					this.selectedItem = view;
				return view;
			},
			
			itemRemoved: function(item) {
				openhmis.GenericListView.prototype.itemRemoved.call(this, item);
				if (item === this.newItem) {
					this.setupNewItem();
				}
			},
			
			setupNewItem: function(lineItem) {
				var dept_uuid;
				if (lineItem !== undefined) {
					lineItem.off('validated', this.setupNewItem);
					this.deselectAll();
					dept_uuid = lineItem.get('item').get('department');
				}
				this.newItem = new openhmis.LineItem({ item: new openhmis.Item({ department: dept_uuid }) });
				this.newItem.on('validated', this.setupNewItem);
				this.model.add(this.newItem);
				if (this.$('p.empty').length > 0)
					this.render();
				else {
					var view = this.addOne(this.newItem);
					view.focus();
				}
			},
			
			render: function() {
				openhmis.GenericListView.prototype.render.call(this, {
					listTitle: ""
				});
				this.$("table").addClass("bill");
				this.$('#showRetired').remove();
				return this;
			}
		});
		
		return openhmis;
	}	
);