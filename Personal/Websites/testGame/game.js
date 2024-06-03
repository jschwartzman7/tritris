let mouseX = 0;
let mouseY = 0;
let ballLife = setInterval(null, 50);

const Gun = {
    html: document.querySelector("#gun"),
    x: 0,
    y: 0,
    aimAngle: 0,
    placed: false,
    updateAimAngle: function (e){
        dy = e.clientY - Gun.y;
        dx =  e.clientX - Gun.x;
        barrelAngle = Math.atan2(dy, dx);
    },
}

const Ball  = {
    html: document.querySelector("#ball"),
    radius: document.querySelector("#ball").clientHeight/2,
    dx: 0,
    dy: 0,
    x: 0,
    y: 0,
    speed: 15,
    updatePosition: function (){
        let nextX = Ball.x + Ball.dx*Ball.speed;
        let nextY = Ball.y + Ball.dy*Ball.speed;
        console.log(Ball.radius);
        if(nextX - Ball.radius < 400 || nextX + Ball.radius > 1000){
            nextX -= 2*(Ball.dx*Ball.speed);
            Ball.dx *= -1;
        }
        if(nextY -Ball.radius < 100 || nextY + Ball.radius > 700){
            nextY -= 2*(Ball.dy*Ball.speed);
            Ball.dy *= -1;
        }
        Ball.html.style.left = nextX + "px";
        Ball.html.style.top = nextY + "px";
        Ball.x = nextX
        Ball.y = nextY
    }
}

document.addEventListener('keydown', (e) =>{
    if ((e.code === 'Space' || e.key === ' ') && Gun.placed) {
        Ball.html.style.left = Gun.x-Ball.radius + "px";
        Ball.html.style.top = Gun.y-Ball.radius + "px";
        Ball.x = Gun.x-Ball.radius;
        Ball.y = Gun.y-Ball.radius;
        let dy = mouseY - Gun.y
        let dx = mouseX - Gun.x
        Ball.dy = Math.sin(Math.atan2(dy, dx))
        Ball.dx = Math.cos(Math.atan2(dy, dx))
        clearInterval(ballLife);
        ballLife = setInterval(Ball.updatePosition, 50);
    }
});

document.addEventListener("mousemove", (e) => {
    mouseX = e.clientX;
    mouseY = e.clientY;
    if(mouseX > 400 && mouseX < 1000 && mouseY > 100 && mouseY < 700){
        if(!Gun.placed){
            Gun.html.style.transform = 'rotate(0rad)';
            Gun.html.style.left = mouseX + "px";
            Gun.x = mouseX;
            Gun.html.style.top = mouseY + "px";
            Gun.y = mouseY;
            Gun.html.style.backgroundColor = 'green';
        }
        else{
            let dy = mouseY - Gun.y;
            let dx = mouseX - Gun.x;
            let angle = Math.atan2(dy, dx);
            let r = angle + "rad";
            Gun.html.style.transform = 'rotate('+r+')';
            Gun.html.style.backgroundColor = 'red';
        }
    }
});

document.addEventListener("click", (e) => {
    Gun.placed = Gun.placed === false;
});


