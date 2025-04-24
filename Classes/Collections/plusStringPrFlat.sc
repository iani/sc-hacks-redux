// Old prFlat - for porting
+ String {
	flat {
		if (FlattenString.mode) {
			^this.prDoFlatten
		}{
			^this;
		};
		// ^this.prFlat(this.species.new(this.flatSize))
	}
	prFlat { | list |
		if (FlattenString.mode) {
			^this.prDoFlatten(list);
		}{
			^this.prDoNotFlatten(list);
		};
	}

	prDoNotFlatten { | list |
		^list add: this
	}

	prDoFlatten { |list|
		this.do({ arg item, i;
			if (item.respondsTo('prFlat'), {
				list = item.prFlat(list);
			},{
				list = list.add(item);
			});
		});
		^list
	}
}
