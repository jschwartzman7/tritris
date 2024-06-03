const Collision = {
    x,
    y,
    frameNum: 0,
    startingRadius: 0.05,
    collisionFrames: [1.05, 1.15, 1.35, 1.75],

    drawCollisions: function(){
        HashSet<Collision> toRemove = new HashSet<Collision>();
        for(Collision collision : BallGame.collisionPos){
            StdDraw.setPenColor(Color.white);
            StdDraw.circle(collision.x, collision.y, startingRadius*collision.collisionFrames[collision.frameNum]);
            if(collision.frameNum == collision.collisionFrames.length-1){
                toRemove.add(collision);
            }
            else{
                ++collision.frameNum;
            }
        }
        for(Collision collision : toRemove){
            BallGame.collisionPos.remove(collision);
        }
    }
}