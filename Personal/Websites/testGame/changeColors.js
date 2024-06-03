const root = document.querySelector(":root");
const body = document.querySelector("body");
let buttons = document.querySelectorAll(".btn");
const blueButton = document.querySelector("#blueBtn");
const purpleButton = document.querySelector("#purpleBtn");
const greenButton = document.querySelector("#greenBtn");
blueButton.addEventListener("click", (e) => {
    localStorage.setItem("background", getComputedStyle(root).getPropertyValue("--background1"));
    localStorage.setItem("accent", getComputedStyle(root).getPropertyValue("--accent1"));
});

purpleButton.addEventListener("click", (e) => {
    localStorage.setItem("background", getComputedStyle(root).getPropertyValue("--background2"));
    localStorage.setItem("accent", getComputedStyle(root).getPropertyValue("--accent2"));
    console.log("purple");
});

greenButton.addEventListener("click", (e) => {
    localStorage.setItem("background", getComputedStyle(root).getPropertyValue("--background3"));
    localStorage.setItem("accent", getComputedStyle(root).getPropertyValue("--accent3"));
});