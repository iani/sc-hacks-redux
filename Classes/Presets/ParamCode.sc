/* 18 Aug 2023 09:35
Helper for creating value, code, ctl from specs in dict of Params.
*/

ParamCode {
	var <spec, <val, <code = "", <ctl = \off;

	*new { | spec, val, code, ctl |
		^this.newCopyArgs(spec, val, code, ctl).init;
	}

	init {
		val ?? { val = spec.asSpec.asSpec.default }; // sic! catch invalid spec names.
		code ?? { code = "" };
		ctl ?? { ctl = \off };
	}

	vcc { ^[val, code, ctl] }
}