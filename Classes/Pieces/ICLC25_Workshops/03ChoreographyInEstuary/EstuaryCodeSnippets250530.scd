//:https://github.com/dktr0/LocoMotion
//:LocoMotion
dancer { url = "cactus" , animation = 15, dur = 5, size = 2, sx = 1.2, x = range -2 -1 (osc 0.5) + 5, y = -3.5 , ry = 30 };
camera { y = 10, z = 10, lx = 0, ly = 0, lz = 0 };
dancer { url = "ant", dur = 10, animation = 10, x = range -5 -2 (osc 0.2), z = 0.4 };
-- box { x = -3, ry = 45, colour = hsv (osc 1) 1.5 0.5, size = 0.5 };
box { sy = 0.01, sx = 100, sz = 10 }
//:multiple dancer copies with function
f n = dancer { url = "cactus", animation = 5, dur = 5, x = (n - (2)) * 2 };
for [1 .. 5] f;
//:alternative:
for [1..10] (\n -> dancer { x = (n - (6)) * 2 })
//:specify figure:
for [1..10] (\n -> dancer { url = "cactus",  x = (n - (6)) * 2 })
