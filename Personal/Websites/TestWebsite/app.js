
function displayProduct(){
    var num1 = parseInt(document.getElementById("num1").innerHTML);
    var num2 = parseInt(document.getElementById("num2").innerHTML);

    var product = num1 * num2;
    document.getElementById("result").innerHTML = `${product}`;
}

document.getElementById("button").addEventListener("click", displayProduct);