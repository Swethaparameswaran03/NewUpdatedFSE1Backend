function generateRandomText() {
var text = "";
var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
for (var i = 0; i < 8; i++)
text += possible.charAt(Math.floor(Math.random() * possible.length));
return text;
}
generateRandomText();