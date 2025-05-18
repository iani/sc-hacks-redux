//VA 240909 Map Text to num
//日 18  5 2025 11:24 Redo by IZ

//:
LiveText2 {
    classvar text, ints, floats;
    classvar <intArray, <floatArray;

    *initClass {
	  StartUp add: {
		this.makeArrays;
	  }
    }

    *makeArrays {
	  var w, x, y, z, wxyz;
	  intArray = (0..127) collect: Rest(0.1);
	  (($a.ascii)..($z.ascii)) do: { | n | intArray[n] = n - 96 };
	  (($A.ascii)..($Z.ascii)) do: { | n | intArray[n] = n - 64 };
	  w = (0.1, 0.2..0.9);
	  x = (0.1, 0.21..0.98);
	  y = [0.19];
	  z = (0.220, 0.321..0.827);
	  wxyz =  [w, x, y, z].flat;
	  floatArray = Rest(0.1) ! 127;
	  (($a.ascii)..($z.ascii)) do: { | n, i |
		floatArray[n] = wxyz[i];
	  };
	  (($A.ascii)..($Z.ascii)) do: { | n, i |
		floatArray[n] = wxyz[i];
	  };
    }

    *text { | argText |
	    text = argText.asString;
    }

    *ints { | repeats = 1 |
	  ^text.ascii.collect({ | a | intArray[a] }).pseq(repeats);
    }

    *floats { | repeats = 1 |
	  ^text.ascii.collect({ | a | floatArray[a] }).pseq(repeats);
    }

}
