const Gun = {
    radius: 0.1,
    barrelLength: 0.35,
    barrelWidth: 0.064,
    sideLength: 0.15,
    sideAngleWidth: .37,
    x,
    y,
    barrelAngle: 0,
    animationFrame: -1,
    animationFrames: [1.2, 1.3, 1.5, 1.4, 1.3, 1.2, 1.1],
    cooldown: false,
    placed: false,
    alpha: 255,
    gunRed: 0,
    gunGreen: 0,
    gunBlue: 0,
    barrelRed: 0,
    barrelGreen: 0,
    barrelBlue: 255,

    updateBarrelAngle: function (){
        if(placed){
            dy = StdDraw.mouseY() - y;
            dx = StdDraw.mouseX() - x;
            barrelAngle = Math.atan2(dy, dx);
        }
        else{
            barrelAngle = Math.atan2(-y, -x);
        }
    },


    draw: function (){
        curBarrelLength = barrelLength;
        curRadius = radius;
        curSideLength = sideLength;
        xdif = barrelWidth*Math.cos(Math.PI/2 - barrelAngle)/2;
        ydif = barrelWidth*Math.sin(Math.PI/2 - barrelAngle)/2;
        if(animationFrame != -1){
            xdif *= animationFrames[animationFrame];
            ydif *= animationFrames[animationFrame];
            //curBarrelLength *= (2.0 - animationFrames[animationFrame]);
            curBarrelLength /= animationFrames[animationFrame];
            curRadius *= animationFrames[animationFrame];
            //curSideLength *= (2.0 - animationFrames[animationFrame]);
            curSideLength /= animationFrames[animationFrame];
        }
        StdDraw.setPenColor(new Color(barrelRed, barrelGreen, barrelBlue, alpha));
        StdDraw.filledPolygon(new  {x-xdif, x+xdif, x+curBarrelLength*Math.cos(barrelAngle)+xdif, x+curBarrelLength*Math.cos(barrelAngle)-xdif}, new  {y+ydif, y-ydif, y+curBarrelLength*Math.sin(barrelAngle)-ydif, y+curBarrelLength*Math.sin(barrelAngle)+ydif});
        StdDraw.setPenColor(new Color(gunRed, gunGreen, gunBlue, alpha));
        StdDraw.filledCircle(x, y, curRadius);
        perpAngle = barrelAngle + Math.PI/2;
        sideOneXCords = [x+Math.cos(perpAngle)*curRadius, x+Math.cos(barrelAngle+sideAngleWidth)*curRadius, x+Math.cos(barrelAngle+sideAngleWidth)*curRadius+Math.cos(barrelAngle)*curSideLength];
        sideOneYCords = [y+Math.sin(perpAngle)*curRadius, y+Math.sin(barrelAngle+sideAngleWidth)*curRadius, y+Math.sin(barrelAngle+sideAngleWidth)*curRadius+Math.sin(barrelAngle)*curSideLength];
        sideTwoXCords = [x-Math.cos(perpAngle)*curRadius, x+Math.cos(barrelAngle-sideAngleWidth)*curRadius, x+Math.cos(barrelAngle-sideAngleWidth)*curRadius+Math.cos(barrelAngle)*curSideLength];
        sideTwoYCords = [y-Math.sin(perpAngle)*curRadius, y+Math.sin(barrelAngle-sideAngleWidth)*curRadius, y+Math.sin(barrelAngle-sideAngleWidth)*curRadius+Math.sin(barrelAngle)*curSideLength];
        //StdDraw.setPenColor(Color.DARK_GRAY);
        StdDraw.filledPolygon(sideOneXCords, sideOneYCords);
        StdDraw.filledPolygon(sideTwoXCords, sideTwoYCords);
        animationFrame = (animationFrame == animationFrames.length-1) || (animationFrame == -1) ? -1 : animationFrame + 1;
        
    }

}