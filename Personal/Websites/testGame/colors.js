document.querySelector("body").style.backgroundColor = localStorage.getItem("background");
let buttons = document.querySelectorAll(".btn");
for(let obj of buttons){
    obj.style.backgroundColor = localStorage.getItem("background");
}
document.querySelector(".buttonContainer").style.backgroundColor = localStorage.getItem("accent");
if(document.querySelector("#goHome")){
    document.querySelector("#goHome").style.backgroundColor = localStorage.getItem("accent");
}

