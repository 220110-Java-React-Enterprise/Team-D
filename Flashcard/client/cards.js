
// Set event listeners for all the navigation buttons
const buttonElements = document.getElementsByClassName('nav-button');
for (let i=0; i<buttonElements.length; i++) {
	buttonElements[i].addEventListener('click', getCard());
}


// Put JSON representation of a card into page
function populateCard(card_data) {
	// Handle the ID somewhere on page

	document.getElementById('question').value = card_data.question;
	document.getElementById('answer1').value = card_data.answer1;
	document.getElementById('answer2').value = card_data.answer2;
	document.getElementById('answer3').value = card_data.answer3;
	document.getElementById('answer4').value = card_data.answer4;
	document.getElementById(card_data.correct_answer).checked = true;
	//document.getElementById('user_id').value = card_data.creator_id;
}

/*
async function getCard() {
	console.log('clicked');
	let card = 2;
	let url = "http://localhost:8080/Flashcard/card&card_id=" + card;
	let response = await fetch(url, {
		headers: {
			'Content-Type': 'text/plain'
		}
	});
	//console.log((await response).ok);

	
	if (response.ok) {
		let json = await response.json();
		alert(json);
	} else {
		alert("HTTP-Error: " + response.status);
	}
} */