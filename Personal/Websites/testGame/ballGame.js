const BallGame = {
    scale: 1,
    // Gun gun,
    // Trail gunTrail,
    // Ball ball,
    
    // LevelBarriers levelBarrierData: new LevelBarriers(),
    collisionPoints: "Linked list of collision points",
    curScore: 0,
    totalScore: 0,
    curLevel: 1,
    numLevels: 2,
    backgroundRed: 103,
    backgroundGreen: 116,
    backgroundBlue: 133,
   
    /*public BallGame(LinkedList<Barrier> levelBarriers){
        this.barriers = levelBarriers;
    }*/

    checkMovingObjectWallCollision: function(nextX,  nextY,  angle,  radius, ball){
        if(Math.abs(nextX) + radius > BallGame.scale){
            if(angle > 0){
                angle = Math.PI - angle;
            }
            else{
                angle = -Math.PI - angle;
            }
            if(nextX - radius < -BallGame.scale){
                nextX += 2*(radius-nextX-BallGame.scale);
            }
            else{
                nextX -= 2*(nextX+radius-BallGame.scale);
            }
        }
        if(Math.abs(nextY) + radius > BallGame.scale){
            angle *= -1;
            if(nextY - radius < -BallGame.scale){
                nextY += 2*(radius-nextY-BallGame.scale);
            }
            else{
                nextY -= 2*(nextY+radius-BallGame.scale);
            }
        }
        [] norm = Barrier.overlaps(new []{nextX, nextY}, radius);
        if(norm.length == 2){
             edgeAngle = Math.atan(norm[0]/norm[1]);
            angle = -angle - 2*(edgeAngle);
            nextX -= 2*norm[0];
            nextY -= 2*norm[1];
        }
        return new []{nextX, nextY, angle};
    },

    gunOutOfBounds : function(){
        if(StdDraw.mouseX() + Gun.radius > scale || StdDraw.mouseX() - Gun.radius < -scale || StdDraw.mouseY() + Gun.radius > scale || StdDraw.mouseY() - Gun.radius < -scale){
            return true;
        }
        return false;
    },

    drawBorder: function(){
        StdDraw.setPenColor(Color.white);
        StdDraw.line(scale, -scale, scale, scale);
        StdDraw.line(scale, scale, -scale, scale);
        StdDraw.line(-scale, -scale, -scale, scale);
        StdDraw.line(-scale, -scale, scale, -scale);
        StdDraw.text(0, 1.1*scale, "Score " + (totalScore + curScore) + " - Space to launch ball - \"r\" to reset", 0);
    },

    updateAndDrawShapes : function(){
        StdDraw.setPenColor(38, 38, 38);
        StdDraw.filledSquare(0, 0, 2*scale);
        StdDraw.setPenColor(backgroundRed, backgroundGreen, backgroundBlue);
        StdDraw.filledSquare(0, 0, scale);
        drawBorder();
        Gun.updateBarrelAngle();
        Trail.updateAndDrawTrail();
        if(Ball.isActive){
            Ball.updateBall();
            Ball.drawBall();
        }
        Collision.drawCollisions();
        Barrier.drawBarriers();
        Gun.draw();
    },

    playLevels: function(){
        StdDraw.enableBuffering();
        StdDraw.setScale(-1.3*scale, 1.3*scale);
        boolean mousePressed = false;
        boolean resetBoardCooldown = false;
        Barrier.getLevelBarriers(curLevel);
        while(true){
                StdDraw.clear();
                updateAndDrawShapes();
                if(Barrier.aliveBarrier()){
                    if(Gun.placed){
                        if(StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && !Gun.cooldown){
                            curScore = 0;
                            Barrier.resetBarriers();
                            new Ball(Gun.x, Gun.y, Gun.barrelAngle);
                            Gun.cooldown = true;
                            Gun.animationFrame = 0;
                        }
                        if(!StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){
                            Gun.cooldown = false;
                        }
                        if(StdDraw.isMousePressed()){
                            mousePressed = true;
                        }
                        if(!StdDraw.isMousePressed() && mousePressed){
                            mousePressed = false;
                             mouseX = StdDraw.mouseX();
                             mouseY = StdDraw.mouseY();
                            if(Barrier.overlaps(new []{mouseX, mouseY}, Gun.radius).length == 3 && !gunOutOfBounds()){
                                Gun.x = mouseX;
                                Gun.y = mouseY;
                            }
                        }
                    }
                    else{
                        Gun.alpha = 128;
                         mouseX = StdDraw.mouseX();
                         mouseY = StdDraw.mouseY();
                        if(Barrier.overlaps(new []{mouseX, mouseY}, Gun.radius).length == 3 && !gunOutOfBounds()){
                            Gun.x = mouseX;
                            Gun.y = mouseY;
                        }
                        if(StdDraw.isMousePressed()){
                            Gun.placed = true;
                            Gun.alpha = 255;
                        }
                    }
                }
                else{
                    StdDraw.setPenColor(30, 30, 30);
                    StdDraw.filledRectangle(0, 0, .6, .3);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.text(0, 0, "Score: " + (totalScore + curScore), 0);
                    StdDraw.text(0, -.15, "Press Space to play next level", 0);
                    if(StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){
                        totalScore += curScore;
                        curScore = 0;
                        Gun.cooldown = true;
                        Barrier.resetBarriers();
                        Ball.isActive = false;
                        ++curLevel;
                        if(curLevel > numLevels){
                            StdDraw.text(0, 0, "Game over, score: " + totalScore, 0);
                            System.out.println("nice");
                        }
                        else{
                            Barrier.getLevelBarriers(curLevel);
                        }
                        Gun.placed = false;
                    }
                    StdDraw.show();
                }
                if(StdDraw.isKeyPressed(KeyEvent.VK_R) && !resetBoardCooldown){
                    resetBoardCooldown = true;
                    Ball.isActive = false;
                    curScore = 0;
                    Gun.placed = false;
                    Barrier.resetBarriers();
                }
                if(!StdDraw.isKeyPressed(KeyEvent.VK_R)){
                    resetBoardCooldown = false;
                }
                StdDraw.show(20);
        }
        
    }



}