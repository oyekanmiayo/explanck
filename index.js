const inputElement = document.getElementById('eval-input')
const outputElement = document.getElementById('output')

async function solve() {
    console.log(inputElement.value)
    // console.log(outputElement)

    DTO = {
        expression: inputElement.value
    }

    let api = `https://explanck.herokuapp.com/interpret`
    let params = {
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(DTO),
        method: 'POST'
    }
    const response = await fetch(api, params);
    const data = await response.json();
    outputElement.style.color = 'black';
    outputElement.innerText = data.value;

}