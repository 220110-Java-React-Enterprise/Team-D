

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