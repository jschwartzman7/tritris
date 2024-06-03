const Ball  = {

    defaultRadius: 0.05,
    radius: defaultRadius,
    defaultSpeed: 0.032,
    speed: defaultSpeed,
    x: 0,
    y: 0,
    angle: 0,
    trailLength: 10,
    red: 0,
    green: 255,
    blue: 0,
    alpha: 255,
    boolean: isActive,
    
    Ball = function(){
        angle = ang;
        x = xCord;
        y = yCord;
        for(double[] curTrail : trail){
            curTrail[0] = xCord;
            curTrail[1] = yCord;
            curTrail[2] = defaultRadius;
        }
        isActive = true;
    },

    updateBall = function(){
            if(animationFrame != -1){
                speed = defaultSpeed*animationFrames[animationFrame];
                radius = defaultRadius/animationFrames[animationFrame];
            }
            x = x + speed*Math.cos(angle);
            y = y + speed*Math.sin(angle);
            updatedPosition = BallGame.checkMovingObjectWallCollision(x, y, angle, radius, true);
            if(angle != updatedPosition[2]){
                x = updatedPosition[0];
                y = updatedPosition[1];
                angle = updatedPosition[2];
                BallGame.collisionPoints.add(new Collision(x, y));
                if(Barrier.aliveBarrier()){
                    ++BallGame.curScore;
                    if(!(Barrier.overlappingBarrier == null)){
                        Barrier.overlappingBarrier.touched = true;
                    }
                }
            }
            for(int i = trailLength-1; i > 0; --i){
                trail[i][0] = trail[i-1][0];
                trail[i][1] = trail[i-1][1];
                trail[i][2] = trail[i-1][2];
            }
            trail[0][0] = x;
            trail[0][1] = y;
            trail[0][2] = radius;
        
    },

    drawBall = function(){
        alpha = 255;
        for(int i = 0; i < trailLength; ++i){
            StdDraw.setPenColor(new Color(0, 255, 0, alpha));
            StdDraw.filledCircle(trail[i][0], trail[i][1], trail[i][2]);
            alpha /= 2;
        }
        animationFrame = (animationFrame == animationFrames.length-1) || (animationFrame == -1) ? -1 : animationFrame + 1;
    },
}


