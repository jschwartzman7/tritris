const Trail = {
    trailLength: 100,
    trailAngle,
    trailRadius: 0.015,
    trailInterval: 0.05,
    red: 25,
    green: 73,
    blue: 79,
    alpha: 255,


    updateAndDrawTrail : function(){
        if(Gun.placed){
            trailAngle = Gun.barrelAngle;
             curX = Gun.x;
             curY = Gun.y;
            updatedPosition = new [2];
            alpha = 255;
            for(i = 0; i < trailLength; ++i){
                curX += (Math.cos(trailAngle))*trailInterval;
                curY += (Math.sin(trailAngle))*trailInterval;
                updatedPosition = BallGame.checkMovingObjectWallCollision(curX, curY, trailAngle, trailRadius, false);
                if(trailAngle != updatedPosition[2]){
                    curX = updatedPosition[0];
                    curY = updatedPosition[1];
                    trailAngle = updatedPosition[2];
                }
                StdDraw.setPenColor(new Color(red, green, blue, alpha));
                StdDraw.filledCircle(curX, curY, trailRadius);
                alpha -= 255/trailLength;
            }
        }
        
    }

}