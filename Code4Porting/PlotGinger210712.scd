// from discord post by histoires
// 12 Jul 2021 21:32
(
~plotginger = {

    var window = Window.new;
    var layout;
    var canvas = UserView.new;
    var size = 100000;
    var tx = 0!size;
    var ty = 0!size;
    var x0 = rrand(-1.0,1.0)*2;
    var y0 = rrand(-1.0,1.0)*2;
    var scale = 50;
    var translate = scale*4;
    var ginger;

    ginger = {
        [x0,y0].debug("x, y");
        tx = 0!size;
        ty = 0!size;
        x0 = rrand(-1.0,1.0);
        y0 = rrand(-1.0,1.0);
        size.do { arg i;
            if(i <= 0) {
                tx[i] = 1 - y0 + abs(x0);
                ty[i] = x0;
            } {
                tx[i] = 1 - ty[i-1] + abs(tx[i-1]);
                ty[i] = tx[i-1];
            }
        };
    };
    ginger.();

    Tdef(\ginger, {
        loop {
            ginger.();
            defer{  window.refresh;};
            1.wait;
        }
    }).play;

    layout = VLayout(
        canvas.drawFunc = {
            Pen.fillColor = Color.blue;
            Pen.translate(translate,translate);
            size.do { arg i;
                Pen.fillRect( Rect(tx[i]*scale, ty[i]*scale, 1,1) );
            };
        };
        canvas.background_(Color.grey);

    );
    window.layout = layout;
    //window.alwaysOnTop = true;
    window.front;
};
~plotginger.();
)