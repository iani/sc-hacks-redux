// shortcuts for RokokoData2

+ Symbol {
	rdata { ^RokokoData2(this) }
	rdataDate {
		^RokokoData2((this ++ Date.localtime.dayStamp).asSymbol)
	}
}